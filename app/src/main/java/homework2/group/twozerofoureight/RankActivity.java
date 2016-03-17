package homework2.group.twozerofoureight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ykk on 16/3/16.
 */
public class RankActivity extends AppCompatActivity {

    //db
    private static final String DBName = "Rank.db";
    private static final int DBversion = 1;
    private CompDBHper dbHper;
    private ArrayList<String> recSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initViews();
        initDB();
        showRec();

    }

    private TextView showScore1;
    private TextView showScore2;
    private TextView showScore3;
    private TextView showScore4;
    private TextView showScore5;

    private TextView showPlayer1;
    private TextView showPlayer2;
    private TextView showPlayer3;
    private TextView showPlayer4;
    private TextView showPlayer5;

    private TextView showDate1;
    private TextView showDate2;
    private TextView showDate3;
    private TextView showDate4;
    private TextView showDate5;

    private void initViews(){
        showScore1 = (TextView) findViewById(R.id.show_score1);
        showScore2 = (TextView) findViewById(R.id.show_score2);
        showScore3 = (TextView) findViewById(R.id.show_score3);
        showScore4 = (TextView) findViewById(R.id.show_score4);
        showScore5 = (TextView) findViewById(R.id.show_score5);

        showPlayer1 = (TextView) findViewById(R.id.show_player1);
        showPlayer2 = (TextView) findViewById(R.id.show_player2);
        showPlayer3 = (TextView) findViewById(R.id.show_player3);
        showPlayer4 = (TextView) findViewById(R.id.show_player4);
        showPlayer5 = (TextView) findViewById(R.id.show_player5);

        showDate1 = (TextView) findViewById(R.id.show_date1);
        showDate2 = (TextView) findViewById(R.id.show_date2);
        showDate3 = (TextView) findViewById(R.id.show_date3);
        showDate4 = (TextView) findViewById(R.id.show_date4);
        showDate5 = (TextView) findViewById(R.id.show_date5);

    }

    private void initDB(){
        if(dbHper==null)
            dbHper = new CompDBHper(this, DBName, null, DBversion);
        //dbHper.creatTB();
        recSet = dbHper.getRecSet();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(dbHper == null){
            dbHper = new CompDBHper(this, DBName, null, DBversion);
        }
        recSet = dbHper.getRecSet();
        showRec();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(dbHper != null){
            dbHper.close();
            dbHper = null;
        }
        recSet.clear();
    }

    private void showRec(){
        if(recSet.size() != 0){
            for(int i=0; i<recSet.size(); i++) {
                String stHead = "共" + recSet.size() + "筆";//------

                String[] fld = recSet.get(i).split("#");
                switch (i) {
                    case 0:
                        showPlayer1.setText(fld[0]);
                        showScore1.setText(fld[1]);
                        showDate1.setText(fld[2]);
                        break;
                    case 1:
                        showPlayer2.setText(fld[0]);
                        showScore2.setText(fld[1]);
                        showDate2.setText(fld[2]);
                        break;
                    case 2:
                        showPlayer3.setText(fld[0]);
                        showScore3.setText(fld[1]);
                        showDate3.setText(fld[2]);
                        break;
                    case 3:
                        showPlayer4.setText(fld[0]);
                        showScore4.setText(fld[1]);
                        showDate4.setText(fld[2]);
                        break;
                    case 4:
                        showPlayer5.setText(fld[0]);
                        showScore5.setText(fld[1]);
                        showDate5.setText(fld[2]);
                        break;
                }
                Toast.makeText(this, stHead, Toast.LENGTH_SHORT).show();//----
            }
        }else {
            showScore1.setText("");
            showPlayer1.setText("");
            showDate1.setText("");
            showScore2.setText("");
            showPlayer2.setText("");
            showDate2.setText("");
            showScore3.setText("");
            showPlayer3.setText("");
            showDate3.setText("");
            showScore4.setText("");
            showPlayer4.setText("");
            showDate4.setText("");
            showScore5.setText("");
            showPlayer5.setText("");
            showDate5.setText("");
        }
    }


}
