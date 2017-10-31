package ca.bcit.ass3.regan_perry;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by A00127241 on 2017-10-19.
 */

public class MyPartyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyParty.sqlite.db";
    private static final int DB_VERSION = 2;
    private Context context;

    public MyPartyDbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("OnCreate", "MyPArtyDB");
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
//                db.execSQL(getCreateCountryTableSql());
                db.execSQL(getEventMasterTableSql());
            }
            if (oldVersion < 2)
                db.execSQL("ALTER TABLE COUNTRY ADD COLUMN POPULATION NUMERIC;");
        } catch (SQLException sqle) {
            String msg = "[MyPlanetDbHelper / updateMyDatabase/insertCountry] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    private String getCreateCountryTableSql() {
        String sql = "";
        sql += "CREATE TABLE COUNTRY (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "CONTINENT TEXT, ";
        sql += "COUNTRY TEXT, ";
        sql += "DESCRIPTION TEXT, ";
        sql += "IMAGE_RESOURCE_ID INTEGER);";

        return sql;
    }

    private String getEventMasterTableSql() {
        String sql = "";
        sql += "CREATE TABLE Event_Master (";
        sql += "_eventId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "Name TEXT, ";
        sql += "Date TEXT, ";
        sql += "Time TEXT);";
        return sql;
    }

    private String getEventDetailTableSql() {
        String sql = "";
        sql += "CREATE TABLE Event_Detail (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "itemName TEXT, ";
        sql += "itemUnit TEXT, ";
        sql += "itemQuantity INTEGER, ";
        sql += "FOREIGN KEY(_eventId) REFERENCES Event_Master(_eventId)); ";
        return sql;
    }

    private String getContributionTableSql() {
        String sql = "";
        sql += "CREATE TABLE Contribution (";
        sql += "_contributionId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "name TEXT, ";
        sql += "quantity INTEGER, ";
        sql += "date TEXT, ";
        sql += "FOREIGN KEY(_detailId) REFERENCES Event_Detail(_detailId)); ";
        return sql;
    }

//    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


    public void insertEvent(SQLiteDatabase db, EventMaster eventMaster) {
        ContentValues values = new ContentValues();
        values.put("name", eventMaster.getName());
        values.put("date", eventMaster.getDate());
        values.put("time", eventMaster.getTime());
        db.insert("Event_Master", null, values);
    }


}

