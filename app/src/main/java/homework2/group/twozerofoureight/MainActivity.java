package homework2.group.twozerofoureight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView view11, view12, view13, view14;
    private TextView view21, view22, view23, view24;
    private TextView view31, view32, view33, view34;
    private TextView view41, view42, view43, view44;

    private TextView Text_appname, Text_score, Text_bestscore;
    private Button btn_newgame, btn_rank;

    private LinearLayout TouchSet;

    private int [][]view_record = new int[5][5];
    private int GameOver;
    private boolean random_flag,gameover_flag;

    //Sound
    private static final int SOUND_COUNT = 3;
    private int when_slide;
    private int when_plus;
    private int ui_click;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListeners();
        initValue();
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

        Text_appname = (TextView)findViewById(R.id.textView);
        Text_score = (TextView)findViewById(R.id.textView_score);
        Text_bestscore = (TextView)findViewById(R.id.textView_bestscore);

        btn_newgame = (Button)findViewById(R.id.btn_newgame);
        btn_rank = (Button)findViewById(R.id.btn_rank);

        TouchSet = (LinearLayout)findViewById(R.id.TouchLayout);

        //Sound
        soundPool = new SoundPool(SOUND_COUNT, AudioManager.STREAM_MUSIC, 0);
        ui_click = soundPool.load(this, R.raw.ui_click, 1);
        when_slide = soundPool.load(this, R.raw.slide, 1);
        when_plus = soundPool.load(this, R.raw.plus, 1);

    }

    private void setListeners(){
        TouchSet.setOnTouchListener(touch_event);
    }

    private void initValue(){
        random_flag = false;
        gameover_flag = false;
        GameOver = 0;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                view_record[i][j] = 0;
            }
        }
        RandomView();
        RandomView();
        showView();
    }

    private void RandomView(){
        Random random = new Random();
        int num = random.nextInt(16)+1;
        setRandomView(num);
    }

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
                        GameOverJudge();
                        showView();
                        Log.i(TAG, "LEFT");
                    }
                    else if (initX - finalX < -100) {  //right
                        for(int i=1; i<5; i++){
                            TouchRight(i);
                            SwapRight(i);
                        }
                        GameOverJudge();
                        showView();
                        Log.i(TAG, "RIGHT");
                    }
                    else if (initY - finalY > 100){    //up
                        for(int i=1; i<5; i++){
                            TouchUp(i);
                            SwapUp(i);
                        }
                        GameOverJudge();
                        showView();
                        Log.i(TAG, "UP");
                    }
                    else if (initY - finalY < -100){   //down
                        for(int i=1; i<5; i++){
                            TouchDown(i);
                            SwapDown(i);
                        }
                        GameOverJudge();
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

            while(i < 5 &&view_record[index][i] == 0){
                i++;
            }
            j = i + 1;
            while(j < 5 && view_record[index][j] == 0){
                j++;
            }

            if(j < 5 && view_record[index][i] == view_record[index][j]){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[index][i] += view_record[index][j];
                view_record[index][j] = 0;
                j++;
                i = j;

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

            if(j < 5 && view_record[i][index] == view_record[j][index]){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[i][index] += view_record[j][index];
                view_record[j][index] = 0;
                j++;
                i = j;

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

            if(j > 0 && view_record[index][i] == view_record[index][j]){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[index][i] += view_record[index][j];
                view_record[index][j] = 0;
                j--;
                i = j;

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

            if(j > 0 && view_record[i][index] == view_record[j][index]){
                soundPool.play(when_plus, 1, 1, 0, 0, 1);//sound

                view_record[i][index] += view_record[j][index];
                view_record[j][index] = 0;
                j--;
                i = j;

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
        view11.setText(String.valueOf(view_record[1][1]));
        view12.setText(String.valueOf(view_record[1][2]));
        view13.setText(String.valueOf(view_record[1][3]));
        view14.setText(String.valueOf(view_record[1][4]));

        view21.setText(String.valueOf(view_record[2][1]));
        view22.setText(String.valueOf(view_record[2][2]));
        view23.setText(String.valueOf(view_record[2][3]));
        view24.setText(String.valueOf(view_record[2][4]));

        view31.setText(String.valueOf(view_record[3][1]));
        view32.setText(String.valueOf(view_record[3][2]));
        view33.setText(String.valueOf(view_record[3][3]));
        view34.setText(String.valueOf(view_record[3][4]));

        view41.setText(String.valueOf(view_record[4][1]));
        view42.setText(String.valueOf(view_record[4][2]));
        view43.setText(String.valueOf(view_record[4][3]));
        view44.setText(String.valueOf(view_record[4][4]));
    }

    private void setRandomView(int num) {
        switch (num){
            case 1:
                setView(1,1);
                break;
            case 2:
                setView(1,2);
                break;
            case 3:
                setView(1,3);
                break;
            case 4:
                setView(1,4);
                break;

            case 5:
                setView(2,1);
                break;
            case 6:
                setView(2,2);
                break;
            case 7:
                setView(2,3);
                break;
            case 8:
                setView(2,4);
                break;

            case 9:
                setView(3,1);
                break;
            case 10:
                setView(3,2);
                break;
            case 11:
                setView(3,3);
                break;
            case 12:
                setView(3,4);
                break;

            case 13:
                setView(4,1);
                break;
            case 14:
                setView(4,2);
                break;
            case 15:
                setView(4,3);
                break;
            case 16:
                setView(4,4);
                break;
        }
    }

    private  void setView(int i, int j){
        if(view_record[i][j] != 0){
            RandomView();
        }
        else{
            GameOver++;
            view_record[i][j] = 2;
            view11.setText(String.valueOf(view_record[i][j]));
            gameover_flag = true;
            Judgement();
            if(gameover_flag){
                GameOverDialog();
            }
        }
    }

    private void Judgement(){
        for(int i=1; i<4; i++){
            for(int j=1; j<4; j++){
                if(view_record[i][j] == view_record[i][j+1] || view_record[i][j] == view_record[i+1][j] || view_record[i][j] == 0){
                    gameover_flag = false;
                }
            }
        }
        for(int i=1; i<4; i++){
            if(view_record[i][4] == view_record[i+1][4] || view_record[i][4] == 0){
                gameover_flag = false;
            }
            if(view_record[4][i] == view_record[4][i+1] || view_record[4][i] == 0){
                gameover_flag = false;
            }
            if(view_record[4][4] == 0){
                gameover_flag = false;
            }
        }
    }

    private void GameOverJudge(){
        if(random_flag && GameOver <= 15){
            RandomView();
            random_flag = false;
        } else if (gameover_flag && GameOver == 16){
            GameOverDialog();
        }
    }

    private void GameOverDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.dia_gameover);
        dialog.setMessage(getString(R.string.dia_msg));
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
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
}

