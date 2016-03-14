package homework2.group.twozerofoureight;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView text11, text12, text13, text14;
    private TextView text21, text22, text23, text24;
    private TextView text31, text32, text33, text34;
    private TextView text41, text42, text43, text44;

    private TextView Text_appname, Text_score, Text_bestscore;
    private Button btn_newgame, btn_rank;

    private LinearLayout TouchSet;

    private int [][]record = new int[5][5];

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
        text11 = (TextView)findViewById(R.id.text11);
        text12 = (TextView)findViewById(R.id.text12);
        text13 = (TextView)findViewById(R.id.text13);
        text14 = (TextView)findViewById(R.id.text14);

        text21 = (TextView)findViewById(R.id.text21);
        text22 = (TextView)findViewById(R.id.text22);
        text23 = (TextView)findViewById(R.id.text23);
        text24 = (TextView)findViewById(R.id.text24);

        text31 = (TextView)findViewById(R.id.text31);
        text32 = (TextView)findViewById(R.id.text32);
        text33 = (TextView)findViewById(R.id.text33);
        text34 = (TextView)findViewById(R.id.text34);

        text41 = (TextView)findViewById(R.id.text41);
        text42 = (TextView)findViewById(R.id.text42);
        text43 = (TextView)findViewById(R.id.text43);
        text44 = (TextView)findViewById(R.id.text44);

        Text_appname = (TextView)findViewById(R.id.textView);
        Text_score = (TextView)findViewById(R.id.textView_score);
        Text_bestscore = (TextView)findViewById(R.id.textView_bestscore);

        btn_newgame = (Button)findViewById(R.id.btn_newgame);
        btn_rank = (Button)findViewById(R.id.btn_rank);

        TouchSet = (LinearLayout)findViewById(R.id.TouchLayout);
    }

    private void setListeners(){
        TouchSet.setOnTouchListener(touch_event);
    }

    private void initValue(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                record[i][j] = 0;
            }
        }
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
                        text11.setText("left");
                        Log.i(TAG, "向左");
                    }
                    if (initX - finalX < -100) {  //right
                        text11.setText("right");
                        Log.i(TAG, "向右");
                    }
                    if (initY - finalY > 100){    //up
                        text11.setText("up");
                        Log.i(TAG, "向上");
                    }
                    if (initY - finalY < -100){   //down
                        text11.setText("down");
                        Log.i(TAG, "向下");
                    }
                    break;
            }
            return true;
        }
    };

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
