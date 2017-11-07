package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        int eventDetailId = 0;
        Intent i = getIntent();
        ListView list_event_details = (ListView) findViewById(R.id.list_event_details);

        final EventDetail[] eventDetail = getEventDetail((Integer) i.getExtras().get("eventid"));

        String[] eventStrings = new String[eventDetail.length];
        for(int j = 0; j < eventDetail.length; j++) {
            eventStrings[j] = eventDetail[j].getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, eventStrings
        );

        list_event_details.setAdapter(arrayAdapter);

        list_event_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
//                intent.putExtra("id", events[i].getId());
//                startActivity(intent);
            }
        });


    }


    private EventDetail[] getEventDetail(int eventId) {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        EventDetail[] events = null;
        try {
            db = helper.getReadableDatabase();
            ((MyPartyDbHelper) helper).insertEventDetail(db, new EventDetail("Beer", "Cans", 12), eventId);
//            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Christmas Party", "Dec 20th, 2017", "12:30 PM"));
//            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("New Years Party", "Dec 31st, 2017", "8:00 PM"));

//            Cursor cursor= db.rawQuery("delete from Event_Master", null);
            Cursor cursor= db.rawQuery("select DISTINCT * from Event_Detail", null);

            Log.d("getEventDetail", "got readable database");

            int count = cursor.getCount();
            events = new EventDetail[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    EventDetail newEvent =  new EventDetail(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(0));
                    events[ndx++] = newEvent;
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetailActivity / getEventDetail] DB unavailable";


            msg += "\n\n" + sqlex.toString();
            Log.d("message", msg);
            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return events;
    }

}
