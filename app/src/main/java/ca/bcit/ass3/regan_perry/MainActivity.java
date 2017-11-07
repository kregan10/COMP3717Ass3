package ca.bcit.ass3.regan_perry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private HashMap<Integer, String> eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list_events = (ListView) findViewById(R.id.list_events);

        final EventMaster[] events = getEvents();
        String[] eventStrings = new String[events.length];
        for(int  i = 0; i < events.length; i++) {
            eventStrings[i] = events[i].getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, eventStrings
        );

        list_events.setAdapter(arrayAdapter);

        list_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                intent.putExtra("eventid", events[i].getId());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private EventMaster[] getEvents() {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        EventMaster[] events = null;
        try {
            db = helper.getReadableDatabase();
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Halloween Party", "Oct 30th, 2017", "6:30 PM"));
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Christmas Party", "Dec 20th, 2017", "12:30 PM"));
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("New Years Party", "Dec 31st, 2017", "8:00 PM"));

//            Cursor cursor= db.rawQuery("delete from Event_Master", null);
            Cursor cursor= db.rawQuery("select DISTINCT * from Event_Master", null);



            int count = cursor.getCount();
            events = new EventMaster[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    EventMaster newEvent =  new EventMaster(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(0));
                    events[ndx++] = newEvent;
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getContinents] DB unavailable";

            msg += "\n\n" + sqlex.toString();
            Log.d("message", msg);
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return events;
    }

}
