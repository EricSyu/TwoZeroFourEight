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
    private  int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initViews();
        initDB();
        showRec();

    }

    private TextView showRank1;
    private TextView showRank2;
    private TextView showRank3;
    private TextView showRank4;
    private TextView showRank5;

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
        showRank1 = (TextView) findViewById(R.id.show_score1);
        showRank2 = (TextView) findViewById(R.id.show_score2);
        showRank3 = (TextView) findViewById(R.id.show_score3);
        showRank4 = (TextView) findViewById(R.id.show_score4);
        showRank5 = (TextView) findViewById(R.id.show_score5);

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
                String stHead = "共" + recSet.size() + "筆";

                String[] fld = recSet.get(i).split("#");
                switch (index) {
                    case 0:
                        showRank1.setText(fld[0]);
                        showPlayer1.setText(fld[1]);
                        showDate1.setText(fld[2]);
                    case 1:
                        showRank2.setText(fld[0]);
                        showPlayer2.setText(fld[1]);
                        showDate2.setText(fld[2]);
                    case 2:
                        showRank3.setText(fld[0]);
                        showPlayer3.setText(fld[1]);
                        showDate3.setText(fld[2]);
                    case 3:
                        showRank4.setText(fld[0]);
                        showPlayer4.setText(fld[1]);
                        showDate4.setText(fld[2]);
                    case 4:
                        showRank5.setText(fld[0]);
                        showPlayer5.setText(fld[1]);
                        showDate5.setText(fld[2]);
                }
                Toast.makeText(this, stHead, Toast.LENGTH_SHORT).show();
            }
        }else {
            showRank1.setText("");
            showPlayer1.setText("");
            showDate1.setText("");
            showRank2.setText("");
            showPlayer2.setText("");
            showDate2.setText("");
            showRank3.setText("");
            showPlayer3.setText("");
            showDate3.setText("");
            showRank4.setText("");
            showPlayer4.setText("");
            showDate4.setText("");
            showRank5.setText("");
            showPlayer5.setText("");
            showDate5.setText("");
        }
    }




}