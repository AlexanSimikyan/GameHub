package com.example.gamehub;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

public class Graphics {
    private AssetManager assetManager;
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint;
    private Bitmap texture;

    public Graphics(AssetManager assetManager, Bitmap frameBuffer){
        this.assetManager = assetManager;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    public void clearAll(int r, int g, int b){
        canvas.drawRGB(r, g, b);
    }
    public void drawPixel(int x, int y, int color){
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }
    public void drawLine(int x1, int y1, int x2, int y2, int color){
        paint.setColor(color);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }
    public void drawText(String text, int x , int y, int color, int size, Typeface font){
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTypeface(font);
        canvas.drawText(text, x, y, paint);
    }
    public void drawTexture(Bitmap texture, int x , int y){
        canvas.drawBitmap(texture,x, y, null);
    }
    public void drawRect(int left, int top, int right, int bottom, int color){
        paint.setColor(color);
        canvas.drawRect(left,top,right,bottom,paint);
    }
    public int getWidthBuffer(){
        return frameBuffer.getWidth();
    }
    public int getHeightBuffer(){
        return frameBuffer.getHeight();
    }

    public Bitmap setTexture(String file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(file);
            texture = BitmapFactory.decodeStream(inputStream);
            if(texture == null){
                throw new RuntimeException("can't load file " + file);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        if(inputStream != null){
            try{
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return texture;

    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}