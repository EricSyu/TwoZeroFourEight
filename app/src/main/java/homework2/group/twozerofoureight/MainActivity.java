package homework2.group.twozerofoureight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView view11, view12, view13, view14;
    private TextView view21, view22, view23, view24;
    private TextView view31, view32, view33, view34;
    private TextView view41, view42, view43, view44;

    private TextView text_appname, text_score, text_bestscore, show_score, show_bestscore;
    private Button btn_newgame, btn_rank,btn_test;
    private ImageButton  btn_star, btn_exchange, btn_music;

    private LinearLayout TouchSet;

    private int [][]view_record = new int[5][5];
    private int score, min_score;
    private int GameOver;
    private boolean random_flag, gameover_flag;

    //Database
    private static final String DBName = "Rank.db";
    private static final int DBVersion = 1;
    private CompDBHper dbHper;
    private ArrayList<String> recSet;

    //exchange
    private int ox, oy, nx, ny;

    //music
    private MediaPlayer mp;
    private boolean isStoped = true;
    private boolean music_stop = true;

    //Sound
    private static final int SOUND_COUNT = 5;
    private int when_slide;
    private int when_plus;
    private int ui_click;
    private int click_star;
    private int when_gameover;
    private SoundPool soundPool;

    //Preference
    public static final String pref = "num_pref";
    public static final String pre_score = "prescore";
    public static final String PRE_record11 = "pref11";
    public static final String PRE_record12 = "pref12";
    public static final String PRE_record13 = "pref13";
    public static final String PRE_record14 = "pref14";
    public static final String PRE_record21 = "pref21";
    public static final String PRE_record22 = "pref22";
    public static final String PRE_record23 = "pref23";
    public static final String PRE_record24 = "pref24";
    public static final String PRE_record31 = "pref31";
    public static final String PRE_record32 = "pref32";
    public static final String PRE_record33 = "pref33";
    public static final String PRE_record34 = "pref34";
    public static final String PRE_record41 = "pref41";
    public static final String PRE_record42 = "pref42";
    public static final String PRE_record43 = "pref43";
    public static final String PRE_record44 = "pref44";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();
        initValue();
        restorePrefs();
        playMusic();
    }


    @Override
    public void onResume(){
        super.onResume();
        if(!music_stop)
            playMusic();
        if(dbHper == null)//Database
            dbHper = new CompDBHper(this, DBName, null, DBVersion);
        recSet = dbHper.getRecSet();
        getMinMaxscore();
    }

    private void getMinMaxscore(){
        if(recSet.size() != 0){
            String[] rank_1 = recSet.get(0).split("#");
            String[] rank_last;
            if(recSet.size()<=5){
                rank_last = recSet.get(recSet.size()-1).split("#");
            }
            else{
                rank_last = recSet.get(4).split("#");
            }
            min_score = Integer.valueOf(rank_last[1]);
            show_bestscore.setText(rank_1[1]);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        music_stop = isStoped;
        stopMusic();//Stop music
        if(dbHper != null) { //Database Close dbHper
            dbHper.close();
            dbHper = null;
        }


        Log.v(TAG, "onPause");
        SharedPreferences record = getSharedPreferences(pref, 0);
        Editor editor = record.edit();
        editor.putString(pre_score, show_score.getText().toString());
        editor.putString(PRE_record11, view11.getText().toString());
        editor.putString(PRE_record12, view12.getText().toString());
        editor.putString(PRE_record13, view13.getText().toString());
        editor.putString(PRE_record14, view14.getText().toString());
        editor.putString(PRE_record21, view21.getText().toString());
        editor.putString(PRE_record22, view22.getText().toString());
        editor.putString(PRE_record23, view23.getText().toString());
        editor.putString(PRE_record24, view24.getText().toString());
        editor.putString(PRE_record31, view31.getText().toString());
        editor.putString(PRE_record32, view32.getText().toString());
        editor.putString(PRE_record33, view33.getText().toString());
        editor.putString(PRE_record34, view34.getText().toString());
        editor.putString(PRE_record41, view41.getText().toString());
        editor.putString(PRE_record42, view42.getText().toString());
        editor.putString(PRE_record43, view43.getText().toString());
        editor.putString(PRE_record44, view44.getText().toString());
        editor.commit();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopMusic();//Stop music
    }

    private void initView(){
        view11 = (TextView)findViewById(R.id.view11);
        view12 = (TextView)findViewById(R.id.view12);
        view13 = (TextView)findViewById(R.id.view13);
        view14 = (TextView)findViewById(R.id.view14);

        view21 = (TextView)findViewById(R.id.view21);
        view22 = (TextView)findViewById(R.id.view22);
        view23 = (TextView)findViewById(R.id.view23);
        view24 = (TextView)findViewById(R.id.view24);

        view31 = (TextView)findViewById(R.id.view31);
        view32 = (TextView)findViewById(R.id.view32);
        view33 = (TextView)findViewById(R.id.view33);
        view34 = (TextView)findViewById(R.id.view34);

        view41 = (TextView)findViewById(R.id.view41);
        view42 = (TextView)findViewById(R.id.view42);
        view43 = (TextView)findViewById(R.id.view43);
        view44 = (TextView)findViewById(R.id.view44);

        text_appname = (TextView)findViewById(R.id.textView);
        text_score = (TextView)findViewById(R.id.textView_score);
        show_score = (TextView)findViewById(R.id.show_score);
        text_bestscore = (TextView)findViewById(R.id.textView_bestscore);
        show_bestscore = (TextView)findViewById(R.id.show_best);

        btn_newgame = (Button)findViewById(R.id.btn_newgame);
        btn_rank = (Button)findViewById(R.id.btn_rank);
        btn_star = (ImageButton)findViewById(R.id.btn_star);
        btn_exchange = (ImageButton)findViewById(R.id.btn_exchange);
        btn_test = (Button)findViewById(R.id.test);

        btn_music = (ImageButton)findViewById(R.id.btn_music_on);

        TouchSet = (LinearLayout)findViewById(R.id.TouchLayout);

        //Sound
        soundPool = new SoundPool(SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        ui_click = soundPool.load(this, R.raw.ui_click, 1);
        when_slide = soundPool.load(this, R.raw.slide, 1);
        when_plus = soundPool.load(this, R.raw.plus, 1);
        when_gameover = soundPool.load(this, R.raw.gameover, 1);
        click_star = soundPool.load(this, R.raw.star, 1);

    }

    private void setListeners(){
        TouchSet.setOnTouchListener(touch_event);
        btn_newgame.setOnClickListener(reset_game);
        btn_rank.setOnClickListener(show_rank);
        btn_star.setOnClickListener(set_star);
        btn_exchange.setOnClickListener(exchange_view);
        btn_test.setOnClickListener(bttest);
        btn_music.setOnClickListener(music_control);
    }

    private void initValue(){
        score = 0;
        random_flag = false;
        gameover_flag = false;
        GameOver = 0;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                view_record[i][j] = 0;
            }
        }
        RandomView(2);
        RandomView(2);
        showView();
    }

    private void RandomView(int setvalue){
        Random random = new Random();
        int num = random.nextInt(16)+1;
        setRandomView(num,setvalue);
    }

    private Button.OnClickListener reset_game = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            score = 0;
            soundPool.play(ui_click, 1, 1, 0, 0, 1);//sound
            ResetDialog();
        }
    };

    private  Button.OnClickListener show_rank = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            soundPool.play(ui_click, 1, 1, 0, 0, 1);//sound
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, RankActivity.class);
            startActivity(intent);
        }
    };
    private Button.OnClickListener set_star = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            soundPool.play(click_star, 1, 1, 0, 0, 1);//sound
            random_flag = true;
            GameOverJudge(1);
            showView();
        }
    };

    private Button.OnClickListener exchange_view = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            soundPool.play(ui_click, 1, 1, 0, 0, 1);//sound
            ExchangeDialog();
        }
    };

    private LinearLayout.OnTouchListener touch_event = new LinearLayout.OnTouchListener(){
        float initX, initY;
        float finalX, finalY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    initX = event.getX();
                    initY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    finalX = event.getX();
                    finalY = event.getY();
                    if (initX - finalX > 100){    //left
                        for(int i=1; i<5; i++){
                            TouchLeft(i);
                            SwapLeft(i);
                        }
                        GameOverJudge(2);
                        showView();
                        Log.i(TAG, "LEFT");
                    }
                    else if (initX - finalX < -100) {  //right
                        for(int i=1; i<5; i++){
                            TouchRight(i);
                            SwapRight(i);
                        }
                        GameOverJudge(2);

                        showView();
                        Log.i(TAG, "RIGHT");
                    }
                    else if (initY - finalY > 100){    //up
                        for(int i=1; i<5; i++){
                            TouchUp(i);
                            SwapUp(i);
                        }
                        GameOverJudge(2);
                        showView();
                        Log.i(TAG, "UP");
                    }
                    else if (initY - finalY < -100){   //down
                        for(int i=1; i<5; i++){
                            TouchDown(i);
                            SwapDown(i);
                        }
                        GameOverJudge(2);
                        showView();
                        Log.i(TAG, "DOWN");
                    }
                    break;
            }
            return true;
        }
    };

    private void TouchLeft(int index){
        int i = 1, j = 2;
        while(j < 5){

            while(i < 5 && view_record[index][i] == 0){
                i++;
            }
            j = i + 1;
            while(j < 5 && view_record[index][j] == 0){
                j++;
            }

            if(j < 5 && (view_record[index][i] == view_record[index][j] || view_record[index][j] == 1)){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[index][i] *= 2;
                view_record[index][j] = 0;
                score += view_record[index][i];
                j++;
                i = j;

                show_score.setText(""+score);

                GameOver--;
                random_flag = true;
            }
            else{
                i++;
            }
        }
    }

    private void TouchUp(int index){
        int i = 1, j = 2;
        while(j < 5){

            while(i < 5 && view_record[i][index] == 0){
                i++;
            }
            j = i + 1;
            while(j < 5 && view_record[j][index] == 0){
                j++;
            }

            if(j < 5 && (view_record[i][index] == view_record[j][index] || view_record[j][index] == 1)){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[i][index] *= 2;
                view_record[j][index] = 0;
                score += view_record[i][index];
                j++;
                i = j;

                show_score.setText(""+score);

                GameOver--;
                random_flag = true;
            }
            else{
                i++;
            }
        }
    }

    private void TouchRight(int index){
        int i = 4, j = 3;
        while(j > 0){

            while(i > 0 &&view_record[index][i] == 0){
                i--;
            }
            j = i - 1;
            while(j > 0 && view_record[index][j] == 0){
                j--;
            }

            if(j > 0 && (view_record[index][i] == view_record[index][j] || view_record[index][j] == 1)){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[index][i] *= 2;
                view_record[index][j] = 0;
                score += view_record[index][i];
                j--;
                i = j;

                show_score.setText("" + score);

                GameOver--;
                random_flag = true;
            }
            else{
                i--;
            }
        }
    }

    private void TouchDown(int index){
        int i = 4, j = 3;
        while(j > 0){

            while(i > 0 &&view_record[i][index] == 0){
                i--;
            }
            j = i - 1;
            while(j > 0 && view_record[j][index] == 0){
                j--;
            }

            if(j > 0 && (view_record[i][index] == view_record[j][index] || view_record[j][index] == 1)){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[i][index] *= 2;
                view_record[j][index] = 0;
                score += view_record[i][index];

                j--;
                i = j;

                show_score.setText(""+score);

                GameOver--;
                random_flag = true;
            }
            else{
                i--;
            }
        }
    }

    private void SwapLeft(int index){
        for(int i=0; i<4; i++){
            for(int j=1; j<4; j++){
                if(view_record[index][j]==0 && view_record[index][j+1]!=0){
                    soundPool.play(when_slide, 0.5F, 0.5F, 0, 0, 0.5F);//sound

                    int tmp = view_record[index][j];
                    view_record[index][j] = view_record[index][j+1];
                    view_record[index][j+1] = tmp;
                    random_flag = true;
                }
            }
        }
    }

    private void SwapRight(int index){
        for(int i=0; i<4; i++){
            for(int j=4; j>1; j--){
                if(view_record[index][j]==0 && view_record[index][j-1]!=0){
                    soundPool.play(when_slide, 0.5F, 0.5F, 0, 0, 0.5F);//sound

                    int tmp = view_record[index][j];
                    view_record[index][j] = view_record[index][j-1];
                    view_record[index][j-1] = tmp;
                    random_flag = true;
                }
            }
        }
    }

    private void SwapUp(int index){
        for(int i=0; i<4; i++){
            for(int j=1; j<4; j++){
                if(view_record[j][index]==0 && view_record[j+1][index]!=0){
                    soundPool.play(when_slide, 0.5F, 0.5F, 0, 0, 0.5F);//sound

                    int tmp = view_record[j][index];
                    view_record[j][index] = view_record[j+1][index];
                    view_record[j+1][index] = tmp;
                    random_flag = true;
                }
            }
        }
    }

    private void SwapDown(int index){
        for(int i=0; i<4; i++){
            for(int j=4; j>1; j--){
                if(view_record[j][index]==0 && view_record[j-1][index]!=0){
                    soundPool.play(when_slide, 0.5F, 0.5F, 0, 0, 0.5F);//sound

                    int tmp = view_record[j][index];
                    view_record[j][index] = view_record[j-1][index];
                    view_record[j-1][index] = tmp;
                    random_flag = true;
                }
            }
        }
    }

    private void showView(){

        if(view_record[1][1]!=0){

            view11.setText(String.valueOf(view_record[1][1]));
            switch (view_record[1][1]){
                case 1:
                    view11.setText("");
                    view11.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view11.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view11.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view11.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view11.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view11.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view11.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view11.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view11.setText("");
            view11.setBackgroundResource(R.drawable.btn_view);
        }
        if (view_record[1][2]!=0){

            view12.setText(String.valueOf(view_record[1][2]));
            switch (view_record[1][2]){
                case 1:
                    view12.setText("");
                    view12.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view12.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view12.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view12.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view12.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view12.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view12.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view12.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view12.setText("");
            view12.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[1][3]!=0){

            view13.setText(String.valueOf(view_record[1][3]));
            switch (view_record[1][3]){
                case 1:
                    view13.setText("");
                    view13.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view13.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view13.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view13.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view13.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view13.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view13.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view13.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view13.setText("");
            view13.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[1][4]!=0){

            view14.setText(String.valueOf(view_record[1][4]));
            switch (view_record[1][4]){
                case 1:
                    view14.setText("");
                    view14.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view14.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view14.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view14.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view14.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view14.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view14.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view14.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view14.setText("");
            view14.setBackgroundResource(R.drawable.btn_view);
        }

        if(view_record[2][1]!=0){

            view21.setText(String.valueOf(view_record[2][1]));
            switch (view_record[2][1]){
                case 1:
                    view21.setText("");
                    view21.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view21.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view21.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view21.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view21.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view21.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view21.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view21.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view21.setText("");
            view21.setBackgroundResource(R.drawable.btn_view);
        }
        if (view_record[2][2]!=0){

            view22.setText(String.valueOf(view_record[2][2]));
            switch (view_record[2][2]){
                case 1:
                    view22.setText("");
                    view22.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view22.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view22.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view22.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view22.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view22.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view22.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view22.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view22.setText("");
            view22.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[2][3]!=0){

            view23.setText(String.valueOf(view_record[2][3]));
            switch (view_record[2][3]){
                case 1:
                    view23.setText("");
                    view23.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view23.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view23.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view23.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view23.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view23.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view23.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view23.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view23.setText("");
            view23.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[2][4]!=0){

            view24.setText(String.valueOf(view_record[2][4]));
            switch (view_record[2][4]){
                case 1:
                    view24.setText("");
                    view24.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view24.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view24.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view24.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view24.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view24.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view24.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view24.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view24.setText("");
            view24.setBackgroundResource(R.drawable.btn_view);
        }

        if (view_record[3][1]!=0){

            view31.setText(String.valueOf(view_record[3][1]));
            switch (view_record[3][1]){
                case 1:
                    view31.setText("");
                    view31.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view31.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view31.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view31.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view31.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view31.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view31.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view31.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view31.setText("");
            view31.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[3][2]!=0){

            view32.setText(String.valueOf(view_record[3][2]));
            switch (view_record[3][2]){
                case 1:
                    view32.setText("");
                    view32.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view32.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view32.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view32.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view32.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view32.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view32.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view32.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view32.setText("");
            view32.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[3][3]!=0){

            view33.setText(String.valueOf(view_record[3][3]));
            switch (view_record[3][3]){
                case 1:
                    view33.setText("");
                    view33.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view33.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view33.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view33.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view33.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view33.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view33.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view33.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view33.setText("");
            view33.setBackgroundResource(R.drawable.btn_view);
        }
        if (view_record[3][4]!=0) {

            view34.setText(String.valueOf(view_record[3][4]));
            switch (view_record[3][4]){
                case 1:
                    view34.setText("");
                    view34.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view34.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view34.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view34.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view34.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view34.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view34.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view34.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view34.setText("");
            view34.setBackgroundResource(R.drawable.btn_view);
        }

        if(view_record[4][1]!=0){

            view41.setText(String.valueOf(view_record[4][1]));
            switch (view_record[4][1]){
                case 1:
                    view41.setText("");
                    view41.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view41.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view41.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view41.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view41.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view41.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view41.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view41.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view41.setText("");
            view41.setBackgroundResource(R.drawable.btn_view);
        }
        if (view_record[4][2]!=0){

            view42.setText(String.valueOf(view_record[4][2]));
            switch (view_record[4][2]){
                case 1:
                    view42.setText("");
                    view42.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view42.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view42.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view42.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view42.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view42.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view42.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view42.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view42.setText("");
            view42.setBackgroundResource(R.drawable.btn_view);
        }
        if(view_record[4][3]!=0){

            view43.setText(String.valueOf(view_record[4][3]));
            switch (view_record[4][3]){
                case 1:
                    view43.setText("");
                    view43.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view43.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view43.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view43.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view43.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view43.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view43.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view43.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view43.setText("");
            view43.setBackgroundResource(R.drawable.btn_view);
        }
        if (view_record[4][4]!=0){

            view44.setText(String.valueOf(view_record[4][4]));
            switch (view_record[4][4]){
                case 1:
                    view44.setText("");
                    view44.setBackgroundResource(R.drawable.star_icon);
                    break;
                case 2:
                    view44.setBackgroundColor(0xffeee4da);
                    break;
                case 4:
                    view44.setBackgroundColor(0xffede0c8);
                    break;
                case 8:
                    view44.setBackgroundColor(0xfff2b179);
                    break;
                case 16:
                    view44.setBackgroundColor(0xfff59563);
                    break;
                case 32:
                    view44.setBackgroundColor(0x80ff0000);
                    break;
                case 64:
                    view44.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    view44.setBackgroundResource(R.drawable.btn_view);
                    break;
            }
        }else{
            view44.setText("");
            view44.setBackgroundResource(R.drawable.btn_view);
        }
    }

    private void setRandomView(int num, int setvalue) {
        Log.i(TAG, String.valueOf(num));
        switch (num){
            case 1:
                setView(1,1,setvalue);
                break;
            case 2:
                setView(1,2,setvalue);
                break;
            case 3:
                setView(1,3,setvalue);
                break;
            case 4:
                setView(1,4,setvalue);
                break;

            case 5:
                setView(2,1,setvalue);
                break;
            case 6:
                setView(2,2,setvalue);
                break;
            case 7:
                setView(2,3,setvalue);
                break;
            case 8:
                setView(2,4,setvalue);
                break;

            case 9:
                setView(3,1,setvalue);
                break;
            case 10:
                setView(3,2,setvalue);
                break;
            case 11:
                setView(3,3,setvalue);
                break;
            case 12:
                setView(3,4,setvalue);
                break;

            case 13:
                setView(4,1,setvalue);
                break;
            case 14:
                setView(4,2,setvalue);
                break;
            case 15:
                setView(4,3,setvalue);
                break;
            case 16:
                setView(4,4,setvalue);
                break;
        }
    }

    private void setView(int i, int j, int setvalue){
        if(view_record[i][j] != 0){
            RandomView(setvalue);
        }
        else{
            GameOver++;
            view_record[i][j] = setvalue;
            view11.setText(String.valueOf(view_record[i][j]));
            gameover_flag = true;
            Judgement();
            if(gameover_flag){
                if(recSet.size()>=5){
                    if(score>min_score){
                        SetRankDialog();
                    }
                    else{
                        GameOverDialog();
                    }
                }
                else{
                    SetRankDialog();
                }
            }
        }
    }

    private void Judgement(){
        for(int i=1; i<4; i++){
            for(int j=1; j<4; j++){
                if(view_record[i][j] == view_record[i][j+1] || view_record[i][j] == view_record[i+1][j]){
                    gameover_flag = false;
                }
                if(view_record[i][j] == 0 || view_record[i][j] == 1){
                    gameover_flag = false;
                }
            }
        }
        for(int i=1; i<4; i++){
            if(view_record[i][4] == view_record[i+1][4]){
                gameover_flag = false;
            }
            if(view_record[4][i] == view_record[4][i+1]){
                gameover_flag = false;
            }
            if(view_record[i][4] == 0 || view_record[4][i] == 0 || view_record[4][4] == 0){
                gameover_flag = false;
            }
            if(view_record[i][4] == 1 || view_record[4][i] == 1 || view_record[4][4] == 1){
                gameover_flag = false;
            }
        }
    }

    private void GameOverJudge(int setvalue){
        if(random_flag && GameOver <= 15){
            RandomView(setvalue);
            random_flag = false;
        } else if (gameover_flag && GameOver == 16){
            soundPool.play(when_gameover, 1, 1, 0, 0, 1);//sound
            GameOverDialog();
        }
    }

    private void GameOverDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.dia_gameover);
        dialog.setMessage("遊戲結束");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void SetRankDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View v = inflater.inflate(R.layout.dialog_gameover, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.dia_gameover);
        dialog.setMessage(getString(R.string.dia_gameover_msg) + score + "分");
        dialog.setView(v);
        final EditText user_name = (EditText)v.findViewById(R.id.username);
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String gamePlayer = user_name.getText().toString();
                SimpleDateFormat now_month = new SimpleDateFormat("MM");
                SimpleDateFormat now_date = new SimpleDateFormat("dd");
                String date= now_month.format(new java.util.Date()) + "/" + now_date.format(new java.util.Date());

                long rowID = dbHper.insertRec(gamePlayer, score, date);

            }
        });
        dialog.show();
    }

    private void ResetDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.dia_reset);
        dialog.setMessage(getString(R.string.dia_reset_msg));
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initValue();
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void ExchangeDialog(){

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View v = inflater.inflate(R.layout.dialog_exchange, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.title_exchange);
        dialog.setView(v);
        final EditText old_x = (EditText)v.findViewById(R.id.old_x);
        final EditText old_y = (EditText)v.findViewById(R.id.old_y);
        final EditText new_x = (EditText)v.findViewById(R.id.new_x);
        final EditText new_y = (EditText)v.findViewById(R.id.new_y);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ox = Integer.valueOf(old_x.getText().toString());
                oy = Integer.valueOf(old_y.getText().toString());
                nx = Integer.valueOf(new_x.getText().toString());
                ny = Integer.valueOf(new_y.getText().toString());
                if(ox > 4 || oy > 4 || nx > 4 || ny > 4){
                    ExchangeDialog();
                    Toast.makeText(MainActivity.this, "請輸入1~4到之間", Toast.LENGTH_SHORT).show();
                }
                else{
                    Swap(ox, oy, nx, ny);
                    showView();
                }
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void Swap(int i, int j, int m, int n){
        int tmp = view_record[i][j];
        view_record[i][j] = view_record[m][n];
        view_record[m][n] = tmp;
    }

    private void restorePrefs(){
        SharedPreferences record = getSharedPreferences(pref,0);

        String scored = record.getString(pre_score, "0");
        if(!"".equals(scored)){
            show_score.setText(""+scored);
            score = Integer.valueOf(scored);
        }
        String pre_record11 = record.getString(PRE_record11,"");
        if(!"".equals(pre_record11)) {
            view11.setText(pre_record11);
            view_record[1][1] = Integer.valueOf(pre_record11);
        }
        String pre_record12 = record.getString(PRE_record12,"");
        if(!"".equals(pre_record12)) {
            view12.setText(pre_record12);
            view_record[1][2] = Integer.valueOf(pre_record12);
        }
        String pre_record13 = record.getString(PRE_record13,"");
        if(!"".equals(pre_record13)) {
            view13.setText(pre_record13);
            view_record[1][3] = Integer.valueOf(pre_record13);
        }
        String pre_record14 = record.getString(PRE_record14,"");
        if(!"".equals(pre_record14)) {
            view14.setText(pre_record14);
            view_record[1][4] = Integer.valueOf(pre_record14);
        }

        String pre_record21 = record.getString(PRE_record21,"");
        if(!"".equals(pre_record21)) {
            view21.setText(pre_record21);
            view_record[2][1] = Integer.valueOf(pre_record21);
        }
        String pre_record22 = record.getString(PRE_record22,"");
        if(!"".equals(pre_record22)) {
            view22.setText(pre_record22);
            view_record[2][2] = Integer.valueOf(pre_record22);
        }
        String pre_record23 = record.getString(PRE_record23,"");
        if(!"".equals(pre_record23)) {
            view23.setText(pre_record23);
            view_record[2][3] = Integer.valueOf(pre_record23);
        }
        String pre_record24 = record.getString(PRE_record24,"");
        if(!"".equals(pre_record24)) {
            view24.setText(pre_record24);
            view_record[2][4] = Integer.valueOf(pre_record24);
        }

        String pre_record31 = record.getString(PRE_record31,"");
        if(!"".equals(pre_record31)) {
            view31.setText(pre_record31);
            view_record[3][1] = Integer.valueOf(pre_record31);
        }
        String pre_record32 = record.getString(PRE_record32,"");
        if(!"".equals(pre_record32)) {
            view32.setText(pre_record32);
            view_record[3][2] = Integer.valueOf(pre_record32);
        }
        String pre_record33 = record.getString(PRE_record33,"");
        if(!"".equals(pre_record33)) {
            view33.setText(pre_record33);
            view_record[3][3] = Integer.valueOf(pre_record33);
        }
        String pre_record34 = record.getString(PRE_record34,"");
        if(!"".equals(pre_record34)) {
            view34.setText(pre_record34);
            view_record[3][4] = Integer.valueOf(pre_record34);
        }

        String pre_record41 = record.getString(PRE_record41,"");
        if(!"".equals(pre_record41)) {
            view41.setText(pre_record41);
            view_record[4][1] = Integer.valueOf(pre_record41);
        }
        String pre_record42 = record.getString(PRE_record42,"");
        if(!"".equals(pre_record42)) {
            view42.setText(pre_record42);
            view_record[4][2] = Integer.valueOf(pre_record42);
        }
        String pre_record43 = record.getString(PRE_record43,"");
        if(!"".equals(pre_record43)) {
            view43.setText(pre_record43);
            view_record[4][3] = Integer.valueOf(pre_record43);
        }
        String pre_record44 = record.getString(PRE_record44,"");
        if(!"".equals(pre_record44)) {
            view44.setText(pre_record44);
            view_record[4][4] = Integer.valueOf(pre_record44);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //----------------------   Music  ----------------------//
    private void playMusic(){
        if( mp == null || isStoped){
            mp = create(MainActivity.this, R.raw.maple);
            isStoped = false;
        }
        mp.setLooping(true);
        mp.start();
    }
    private void stopMusic(){
        if( mp == null || isStoped)
            return;
        mp.stop();
        isStoped = true;

    }
    //Build environment for playing music
    public static MediaPlayer create(Context context, int resid){
        AssetFileDescriptor afd;
        afd = context.getResources().openRawResourceFd(resid);
        if(afd == null)
            return null;
        try{
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            return mp;
        }catch (Exception e){
            Log.e("Play music error!", e.toString());
            return null;
        }
    }

    private Button.OnClickListener music_control = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Log.i(TAG, String.valueOf(isStoped));
            if(!isStoped){
                soundPool.play(ui_click, 1, 1, 0, 0, 1);//sound
                stopMusic();
                btn_music.setImageResource(R.drawable.music_off1);
            }
            else{
                soundPool.play(ui_click, 1, 1, 0, 0, 1);//sound
                playMusic();
                btn_music.setImageResource(R.drawable.music_on1);
            }
        }
    };

    //Database  add record
    private Button.OnClickListener bttest = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(recSet.size()>=5){
                if(score>min_score){
                    SetRankDialog();
                }
                else{
                    GameOverDialog();
                }
            }
            else{
                SetRankDialog();
            }
        }
    };
}
