package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    private HashMap<Integer, String> eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_event:
                Intent i = new Intent(this,AddEventActivity.class);
                this.startActivity(i);
                return true;
            case R.id.action_find_event:
                Intent j = new Intent(this,FindEventActivity.class);
                this.startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private EventMaster[] getEvents() {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        EventMaster[] events = null;
        try {
            db = helper.getReadableDatabase();
            Cursor cursor;


            //Test Data. Remove Later.

//            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Halloween Party", "Oct 30th, 2017", "6:30 PM"));
//            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Christmas Party", "Dec 20th, 2017", "12:30 PM"));
//            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("New Years Party", "Dec 31st, 2017", "8:00 PM"));
//
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Beer", "Cans", 12), 3);
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Chips", "Bags", 6), 3);
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Wine", "Bottles", 12), 1);
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Chips", "Bags", 6), 1);
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Beer", "Cans", 12), 2);
//            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Chips", "Bags", 6), 2);



            cursor = db.rawQuery("select DISTINCT * from Event_Master", null);

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
