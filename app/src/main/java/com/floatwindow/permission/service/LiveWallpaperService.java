package com.floatwindow.permission.service;

import android.graphics.PixelFormat;
import android.os.Build;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        Log.e("1234", "-----------onCreateEngine---------------");
        return new LiveWallpaperEngine();
    }

    private class LiveWallpaperEngine extends Engine {

        private LiveWallpaperView liveWallpaperView;
        private final SurfaceHolder surfaceHolder = getSurfaceHolder();

        LiveWallpaperEngine() {
            this.liveWallpaperView = new LiveWallpaperView(LiveWallpaperService.this.getBaseContext());
            this.liveWallpaperView.loadWallpaperBitmap();
            drawView();
            int sdkInt = Build.VERSION.SDK_INT;
            if (sdkInt >= 15) {
                setOffsetNotificationsEnabled(true);
            }
        }

        private void drawView() {
            if (this.liveWallpaperView != null) {
                this.liveWallpaperView.surfaceChanged(
                        this.surfaceHolder,
                        PixelFormat.OPAQUE,
                        this.liveWallpaperView.getWidth(),
                        this.liveWallpaperView.getHeight());
                if (isVisible()) {
                    this.liveWallpaperView.loadWallpaperBitmap();
                }
            }
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            drawView();
            if (this.liveWallpaperView != null) {
                this.liveWallpaperView.surfaceCreated(holder);
            }
        }

        public void onSurfaceChanged(SurfaceHolder holder,
                                     int format,
                                     int width,
                                     int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawView();
        }

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (this.liveWallpaperView != null) {
                this.liveWallpaperView.surfaceDestroyed(holder);
            }
        }

        public void onDestroy() {
            super.onDestroy();
        }

    }


}
