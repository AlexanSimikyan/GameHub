package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gamehub.G2048Classes.G2048;

public class Game2048 extends AppCompatActivity {


    public char getDirection(double[] first, double[] second){
        double DX = first[0] - second[0];
        double DY = first[1] - second[1];

        if( Math.abs(DX) <= Math.abs(DY) && DX < 0)return 'l';
        if( Math.abs(DX) <= Math.abs(DY) && DX > 0)return 'r';
        if( Math.abs(DX) >= Math.abs(DY) && DY > 0)return 'u';
        if( Math.abs(DX) >= Math.abs(DY) && DY < 0)return 'd';
        return 'n';
    }


    public void sumRight(int[] arr){
        for(int i = arr.length-1; i > 0; i--){
            if(arr[i] == arr[i-1]){
                arr[i] = arr[i]*2;
                arr[i-1] = 0;
            }
        }
    }

    public void moveRight(int[] line){
        for(int j = 0; j < line.length; j++){
            for (int i = line.length-2; i >= 0; i--) {
                if (line[i+1] == 0){
                    line[i+1] = line[i];
                    line[i] = 0;
                }
            }
        }

    }
    public void sumLeft(int[] arr){
        for(int i = 0; i < arr.length-1; i++){
            if(arr[i] == arr[i+1]){
                arr[i] = arr[i]*2;
                arr[i+1] = 0;
            }
        }
    }

    public void moveLeft(int[] line){
        for(int j = line.length - 1; j > 0; j--){
            for (int i = 1; i < line.length; i++) {
                if (line[i-1] == 0){
                    line[i-1] = line[i];
                    line[i] = 0;
                }
            }
        }

    }

    public void makeMove(int[][] map, char direction){
        switch (direction){
            case 'r':
                for (int[] line : map) {
                    moveRight(line);
                    sumRight(line);
                    moveRight(line);
                }
                break;
            case 'l':
                for(int[] line: map){
                    moveLeft(line);
                    sumLeft(line);
                    moveLeft(line);
                }
                break;
            case 'u':
                for(int j = 0; j < map.length; j++){
                    int[] line = new int[map.length];
                    for (int i = 0; i < map.length; i++) {
                        line[i] = map[i][j];
                    }
                    moveLeft(line);
                    sumLeft(line);
                    moveLeft(line);
                    for (int i = 0; i < map.length; i++){
                        map[i][j] = line[i];
                    }
                }break;
            case 'd':
                for(int j = 0; j < map.length; j++){
                    int[] line = new int[map.length];
                    for (int i = 0; i < map.length; i++) {
                        line[map.length - i - 1] = map[i][j];
                    }
                    moveLeft(line);
                    sumLeft(line);
                    moveLeft(line);
                    for (int i = 0; i < map.length; i++){
                        map[i][j] = line[map.length - i - 1];
                    }
                }break;


        }
    }


    public void drawBoard(int[][] map, SurfaceHolder holder){

    }

    int size = 5;
    double[] startPos = new double[2];
    double[] newPos = new double[2];

    int x,y;
    int[][] game_map = new int[size][size];
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Bitmap bitmap;
    G2048 g2048;
    Graphics graphics;
    int bitmap_height = 100;
    int bitmap_width = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        for (int[]l: game_map) {
            for (int i:l) {
                i = 0;
            }
        }

        surfaceView = findViewById(R.id.G2048Surface);
        surfaceHolder = surfaceView.getHolder();
        bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);
        g2048 = new G2048(bitmap, surfaceView);
        g2048.startGame();
        graphics = new Graphics(getAssets(), bitmap);
        x = (int)(Math.random()* game_map.length);
        y = (int)(Math.random()* game_map.length);
        game_map[x][y] = 2;
        x = (int)(Math.random()* game_map.length);
        y = (int)(Math.random()* game_map.length);
        game_map[x][y] = 2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startPos[0] = event.getX();
                startPos[1] = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newPos[0] = event.getX();
                newPos[1] = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                makeMove(game_map, getDirection(startPos, newPos));

                System.out.println(getDirection(startPos, newPos));
                do {
                    x = (int)(Math.random()* game_map.length);
                    y = (int)(Math.random()* game_map.length);
                }while(game_map[x][y] != 0);

                game_map[x][y] = 2;
                double rectSize = bitmap_height/size;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        switch (game_map[i][j]){
                            case 0:
                                graphics.drawRect((int)(i*rectSize), (int)(j*rectSize), (int)((i+1)*rectSize), (int)((j+1)*rectSize), Color.argb(255, 255, 235,133));
                                break;
                            case 2:
                                graphics.drawRect((int)(i*rectSize), (int)(j*rectSize), (int)((i+1)*rectSize), (int)((j+1)*rectSize), Color.argb(255, 255, 194,133));
                                graphics.drawText("2",(int)(i*rectSize), (int)(j*rectSize), Color.BLACK, 30, Typeface.DEFAULT);
                                break;
                            case 4:
                                graphics.drawRect((int)(i*rectSize), (int)(j*rectSize), (int)((i+1)*rectSize), (int)((j+1)*rectSize), Color.argb(255, 255,  168,133));
                                graphics.drawText("4",(int)(i*rectSize), (int)(j*rectSize), Color.BLACK, 30, Typeface.DEFAULT);
                                break;
                            case 8:
                                graphics.drawRect((int)(i*rectSize), (int)(j*rectSize), (int)((i+1)*rectSize), (int)((j+1)*rectSize), Color.argb(255, 224,  118,99));
                                graphics.drawText("8",(int)(i*rectSize), (int)(j*rectSize), Color.BLACK, 30, Typeface.DEFAULT);
                                break;
                        }

                    }
                }
                for (int[] line: game_map) {
                    for( int i: line){
                        System.out.print(i);
                    }
                }


        }
        return super.onTouchEvent(event);
    }
}