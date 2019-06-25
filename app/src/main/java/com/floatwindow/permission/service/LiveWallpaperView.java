package com.floatwindow.permission.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.util.LruCache;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.floatwindow.permission.R;

import java.util.Map;

public class LiveWallpaperView extends SurfaceView
        implements SurfaceHolder.Callback {
    private Context context;
    private LruCache<String, Bitmap> mMemoryCache;
    private Paint mPaint;
    private Bitmap wallpaperBitmap;

    public LiveWallpaperView(Context context) {
        super(context);
        this.context = context;
        initCacheConfig();
        initPaintConfig();
//        this.wallpaperBitmap = WallpaperUtils.getDefaultWallpaper(context);
    }

    private void initPaintConfig() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(5.0f);
    }

    private void initCacheConfig() {
        this.mMemoryCache = new LruCache<String, Bitmap>(
                ((int) Runtime.getRuntime().maxMemory()) / 8) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        holder.removeCallback(this);
        drawSurfaceView(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawSurfaceView(holder);
    }

    private void drawSurfaceView(SurfaceHolder holder) {
        if (this.wallpaperBitmap != null
                && !this.wallpaperBitmap.isRecycled()) {
            Canvas localCanvas = holder.lockCanvas();
            if (localCanvas != null) {
                Rect rect = new Rect();
                rect.top = 0;
                rect.left = 0;
                rect.bottom = localCanvas.getHeight();
                rect.right = localCanvas.getWidth();
                localCanvas.drawBitmap(this.wallpaperBitmap, null, rect, this.mPaint);
                holder.unlockCanvasAndPost(localCanvas);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseBitmap();
    }

    public void loadWallpaperBitmap() {
//        if (this.wallpaperBitmap == null) {
//            this.wallpaperBitmap = WallpaperUtils.getDefaultWallpaper(this.context);
        if (this.wallpaperBitmap == null) {
            this.wallpaperBitmap =
                    BitmapFactory.decodeResource(
                            getResources(),
                            R.raw.livewallpaper);
        }
//        }

    }

    public void releaseBitmap() {
        try {
            if (this.mMemoryCache != null
                    && this.mMemoryCache.size() > 0) {
                Map<String, Bitmap> stringBitmapMap = this.mMemoryCache.snapshot();
                this.mMemoryCache.evictAll();
                if (stringBitmapMap.size() > 0) {
                    for (Map.Entry<String, Bitmap> entry : stringBitmapMap.entrySet()) {
                        if (!(entry == null
                                || entry.getValue() == null
                                || (entry.getValue()).isRecycled())) {
                            (entry.getValue()).recycle();
                        }
                    }
                    stringBitmapMap.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
