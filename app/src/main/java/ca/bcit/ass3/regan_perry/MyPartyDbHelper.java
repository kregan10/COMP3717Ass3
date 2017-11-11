package ca.bcit.ass3.regan_perry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyPartyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyParty.sqlite.db";
    private static final int DB_VERSION = 3;
    private final String EVENT_MASTER = "EVENT_MASTER";
    private final String EVENT_DETAIL = "EVENT_DETAIL";

    private Context context;
    private Cursor cursor;

    public MyPartyDbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        Log.d("MyPartyDBHelper", "Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("MyPartyDBHelper", "onCreate");
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
        Log.d("MyPartyDBHelper", "onUpgrade");

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyPartyDBHelper", "updateMyDatabase called");
        try {
            if (oldVersion < 1) {
                Log.d("MyPartyDBHelper", "updateMyDatabase Table sql called");
                db.execSQL(getEventMasterTableSql());
                db.execSQL(getEventDetailTableSql());
            }
        } catch (SQLException sqle) {
            String msg = "[MyPartyDbHelper / updateMyDatabase] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Log.d("MyPartyDbHelper", msg);
            System.out.println(msg);
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
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
        Log.d("inGetEventDetail", "Asdf");
        String sql = "";
        sql += "CREATE TABLE Event_Detail (";
        sql += "_detailId INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "itemName TEXT, ";
        sql += "itemUnit TEXT, ";
        sql += "itemQuantity INTEGER, ";
        sql += "eventId INTEGER, ";
        sql += "FOREIGN KEY(eventId) REFERENCES Event_Master(_eventId)); ";
        return sql;
    }

//    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


    public void insertEvent(SQLiteDatabase db, EventMaster eventMaster) {
        ContentValues values = new ContentValues();
        values.put("name", eventMaster.getName());
        values.put("date", eventMaster.getDate());
        values.put("time", eventMaster.getTime());
        eventMaster.setId((int)db.insert(EVENT_MASTER, null, values));
        Log.d("hellpo", "hello");
    }

    public void insertEventDetail(SQLiteDatabase db, EventDetail eventDetail, int id) {
        ContentValues values = new ContentValues();
        values.put("itemName", eventDetail.getName());
        values.put("itemUnit", eventDetail.getUnit());
        values.put("itemQuantity", eventDetail.getQuantity());
        values.put("eventId", id);
        eventDetail.setDetailId((int)db.insert(EVENT_DETAIL, null, values));
        Log.d("eventDelailID", "" + eventDetail.getDetailId());


    }

    public void editEvent(SQLiteDatabase db, EventMaster event) {
        ContentValues values = new ContentValues();
        values.put("name", event.getName());
        values.put("date", event.getDate());
        values.put("time", event.getTime());
        db.update(EVENT_MASTER, values, "_id=" + event.getId(), null);
    }
    //https://stackoverflow.com/questions/9798473/sqlite-in-android-how-to-update-a-specific-row

    public void editEventDetail(SQLiteDatabase db, EventDetail eventDetail) {
        ContentValues values = new ContentValues();
        values.put("itemName", eventDetail.getName());
        values.put("itemUnit", eventDetail.getUnit());
        values.put("itemQuantity", eventDetail.getQuantity());
        db.update(EVENT_DETAIL, values, "_id=" + eventDetail.getDetailId(), null);
    }

    public void deleteEvent(SQLiteDatabase db, EventMaster event) {
        cursor=db.rawQuery("DELETE FROM Event_Master" + " WHERE _eventId = " + event.getId() +";", null);
        db.close();
    }

    public void deleteEventDetail(SQLiteDatabase db, EventDetail eventDetail) {
        cursor=db.rawQuery("DELETE FROM Event_Detail" + " WHERE _detailId = " + eventDetail.getDetailId() +";", null);
        db.close();
    }

    public EventMaster[] findEvent(SQLiteDatabase db, String eventName) {
        cursor=db.rawQuery("SELECT * FROM Event_Master" + " WHERE name = '" + eventName +"';", null);
        int count = cursor.getCount();
        EventMaster[] events = new EventMaster[count];
        if (cursor.moveToFirst()) {
            int ndx=0;
            do {
                EventMaster newEvent =  new EventMaster(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(0));
                events[ndx++] = newEvent;
            } while (cursor.moveToNext());
        }
        return events;
    }
}

