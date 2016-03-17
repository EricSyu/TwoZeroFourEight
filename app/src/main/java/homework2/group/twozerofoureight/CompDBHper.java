package homework2.group.twozerofoureight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ykk on 16/3/16.
 */
public class CompDBHper extends SQLiteOpenHelper {
    private static final String TBName = "玩家";
    private static final String crTBsql =
            "CREATE TABLE " + TBName + " (" +
                    " gamePlayer VARCHAR(10), " +
                    " gameScore INTEGER NOT NULL, " +
                    " gameDate VARCHAR(10) NOT NULL); " ;

    public CompDBHper(Context context, String DBName, SQLiteDatabase.CursorFactory factory, int DBversion ){
        super(context, "排行榜.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(crTBsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST" + TBName);
        onCreate(db);
    }

    //Add
    public long insertRec(String gamePlayer, int gameScore, String gameTime){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("gamePlayer", gamePlayer);
        rec.put("gameScore", gameScore);
        rec.put("gameDate", gameTime);
        long rowID = db.insert(TBName, null, rec);
        db.close();
        return rowID;
    }

    //Count record
    public int RecCount(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TBName;
        Cursor recSet = db.rawQuery(sql, null);
        return recSet.getCount();
    }

    public ArrayList<String> getRecSet(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TBName + " ORDER BY gameScore DESC;";
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        int columnCount = recSet.getColumnCount();
        while(recSet.moveToNext()){
            String fldSet = "";
            for (int i=0 ; i<columnCount ; i++){
                fldSet += recSet.getString(i) + "#";
            }
            recAry.add(fldSet);
        }
        recSet.close();
        db.close();
        return recAry;
    }

}
