package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamehub.G2048Classes.G2048;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Game2048 extends AppCompatActivity {


    public char getDirection(double[] first, double[] second){
        double DX = first[0] - second[0];
        double DY = first[1] - second[1];
        double a = getAngle(first, second);
        if (a > - (Math.PI/4) && a < (Math.PI/4))return 'r';
        if (a >= (Math.PI/4) && a < (3*Math.PI/4))return 'd';
        if (a > (3*Math.PI/4) && a < (5*Math.PI/4))return 'l';
        return 'u';
    }
    public double getDistance(double[] first, double[] second){
        return Math.sqrt((first[0] - second[0])*(first[0] - second[0]) + (first[1] - second[1])*(first[1] - second[1]));
    }
    public double getAngle(double[] first, double[] second){
        double DX = first[0] - second[0];
        double DY = first[1] - second[1];
        double angle = 0;
        if(DX != 0 && DY != 0){
            if(DX > 0){
                angle = Math.PI + Math.atan(DY/DX);
            }else{
                angle = Math.atan(DY/DX);
            }
        }if(DX == 0){
            if(DY > 0){
                angle = 1.5 * Math.PI;
            }else{
                angle = 0.5 * Math.PI;
            }
        }if(DY == 0){
            if(DX > 0){
                angle = Math.PI;
            }else{
                angle = 0;
            }
        }
        return angle;
    }


    public void sumRight(int[] arr){
        for(int i = arr.length-1; i > 0; i--){
            if(arr[i] == arr[i-1]){
                arr[i] = arr[i]*2;
                arr[i-1] = 0;
                score += arr[i];
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
                score += arr[i];
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



    int size = 5;
    int score = 0;
    int highScore;
    double[] startPos = new double[2];
    double[] newPos = new double[2];
    int x,y;
    int[][] game_map = new int[size][size];

    ImageView[][] map ;
    TextView scoreText;
    TextView HighScoreText;


    public void drawMap(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                switch (game_map[i][j]){
                    case 0:
                        map[i][j].setBackgroundResource(R.drawable.box0);
                        break;
                    case 2:

                        map[i][j].setBackgroundResource(R.drawable.box2);
                        break;
                    case 4:

                        map[i][j].setBackgroundResource(R.drawable.box4);
                        break;
                    case 8:

                        map[i][j].setBackgroundResource(R.drawable.box8);
                        break;
                    case 16:
                        map[i][j].setBackgroundResource(R.drawable.box16);
                        break;
                    case 32:

                        map[i][j].setBackgroundResource(R.drawable.box32);
                        break;
                    case 64:

                        map[i][j].setBackgroundResource(R.drawable.box64);
                        break;
                    case 128:

                        map[i][j].setBackgroundResource(R.drawable.box128);
                        break;
                    case 256:
                        map[i][j].setBackgroundResource(R.drawable.box256);
                        break;
                    case 512:

                        map[i][j].setBackgroundResource(R.drawable.box512);
                        break;
                    case 1024:

                        map[i][j].setBackgroundResource(R.drawable.box1024);
                        break;
                    case 2048:

                        map[i][j].setBackgroundResource(R.drawable.box2048);
                        break;
                }
            }
        }
    }



    SharedPreferences sharedPreferences;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        for (int[]l: game_map) {
            for (int i:l) {
                i = 0;
            }
        }
        load();

        scoreText = findViewById(R.id.score);
        HighScoreText = findViewById(R.id.highScore);
        HighScoreText.setText("highest score: " + highScore);

        LinearLayout layout = findViewById(R.id.mainLayout);
        RelativeLayout layout_bottom = findViewById(R.id.bottom);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        ViewGroup.LayoutParams params_bottom = layout_bottom.getLayoutParams();
        RelativeLayout layout_top = findViewById(R.id.Top);
        ViewGroup.LayoutParams params_top = layout_top.getLayoutParams();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int t = Math.min(width, height);

        params.height = height; //(int)(1.5 * t);
        params.width = width;//t;
        params_top.height =(height-t)/2;
        params_top.width = t;
        params_bottom.height =(height-t)/2;
        params_bottom.width = t;
        layout_top.setLayoutParams(params_top);
        layout_bottom.setLayoutParams(params_top);
        layout.setLayoutParams(params);
        map = new ImageView[][]{{findViewById(R.id.l1e1), findViewById(R.id.l1e2), findViewById(R.id.l1e3), findViewById(R.id.l1e4),findViewById(R.id.l1e5)},
                {findViewById(R.id.l2e1), findViewById(R.id.l2e2), findViewById(R.id.l2e3), findViewById(R.id.l2e4),findViewById(R.id.l2e5)},
                {findViewById(R.id.l3e1), findViewById(R.id.l3e2), findViewById(R.id.l3e3), findViewById(R.id.l3e4),findViewById(R.id.l3e5)},
                {findViewById(R.id.l4e1), findViewById(R.id.l4e2), findViewById(R.id.l4e3), findViewById(R.id.l4e4),findViewById(R.id.l4e5)},
                {findViewById(R.id.l5e1), findViewById(R.id.l5e2), findViewById(R.id.l5e3), findViewById(R.id.l5e4),findViewById(R.id.l5e5)},};



        x = (int)(Math.random()* game_map.length);
        y = (int)(Math.random()* game_map.length);
        game_map[x][y] = 2;
        x = (int)(Math.random()* game_map.length);
        y = (int)(Math.random()* game_map.length);
        game_map[x][y] = 2;
        drawMap();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startPos[0] = event.getX();
                startPos[1] = event.getY();
                break;
            case MotionEvent.ACTION_UP:

                newPos[0] = event.getX();
                newPos[1] = event.getY();
                makeMove(game_map, getDirection(startPos, newPos));

                //System.out.println(getDirection(startPos, newPos) + " " + startPos[0] + " ");
                boolean end_game = true;
                do {
                    for (int[] line: game_map) {
                        for(int i: line){
                            if(i == 0) {
                                end_game = false;
                                break;
                            }
                        }
                    }
                    x = (int)(Math.random()* game_map.length);
                    y = (int)(Math.random()* game_map.length);
                }while(game_map[x][y] != 0 && !end_game);
                if(end_game){
                    Toast toast = Toast.makeText(this, "you lose", Toast.LENGTH_LONG);
                    toast.show();
                    for (int[]l: game_map) {
                        Arrays.fill(l, 0);
                    }
                    x = (int)(Math.random()* game_map.length);
                    y = (int)(Math.random()* game_map.length);
                    game_map[x][y] = 2;
                    x = (int)(Math.random()* game_map.length);
                    y = (int)(Math.random()* game_map.length);
                    game_map[x][y] = 2;
                    score = 0;
                }else{
                    game_map[x][y] = 2;
                    drawMap();
                    if(score > highScore){
                        HighScoreText.setText("highest score: " + score);
                        highScore = score;
                    }
                    scoreText.setText("score: " + score);
                }



        }
        return super.onTouchEvent(event);
    }

    public void save(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(SAVED_TEXT,highScore);
        ed.apply();
        System.out.println("saved");
    }public void load(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        int hs = sharedPreferences.getInt(SAVED_TEXT, 0);
        highScore = hs;
        System.out.println("loaded");
    }


    @Override
    protected void onStop() {
        save();
        super.onStop();
    }

    @Override
    protected void onPause() {
        save();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}