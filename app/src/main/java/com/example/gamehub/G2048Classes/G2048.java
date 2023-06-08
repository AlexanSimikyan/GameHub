package com.example.gamehub.G2048Classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Date;

public class G2048 implements Runnable{
    private final float FPS = 60;
    private final float SECOND = 1000000000;
    private final float UPDATE_TIME = SECOND / FPS;
    private long Timer = 0;

    private boolean Running = false;
    private Thread GameThread = null;
    private Bitmap FrameBuffer;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Rect rect;
    private SurfaceView surfaceView;

    public G2048(Bitmap bitmap, SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        holder = surfaceView.getHolder();
        this.rect = new Rect();
        this.canvas = new Canvas();
        FrameBuffer = bitmap;
    }

    @Override
    public void run() {

        float lastTime = System.nanoTime();
        float delta = 0;
        Timer = System.currentTimeMillis();


        while (Running) {
            float nowTime = System.nanoTime();
            float elapsedTime = nowTime - lastTime;
            lastTime = nowTime;
            delta += elapsedTime / UPDATE_TIME;
            if (delta > 1) {
                drawingGame();
                delta--;
            }
            if (System.currentTimeMillis() - Timer > 1000) {
                Date date = new Date();
                //System.out.println("UPDATES = " + mUpdates + " DRAWING " + mDrawing + " " + date.toString() + " " + x + " " + y);
                Timer += 1000;
            }
        }
    }

    private void drawingGame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.getClipBounds(rect);
            canvas.drawBitmap(FrameBuffer, null, rect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void startGame() {
        if (Running) {
            return;
        }
        Running = true;
        GameThread = new Thread(this);
        GameThread.start();
    }
}
