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
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent i = getIntent();

        ListView list_event_details = (ListView) findViewById(R.id.list_event_details);
        TextView name = (TextView) findViewById(R.id.nameText);
        TextView date = (TextView) findViewById(R.id.dateText);
        TextView time = (TextView) findViewById(R.id.timeText);

        int eventId = (Integer) i.getExtras().get("eventid");
        EventMaster event = getEvent(eventId);

        final EventDetail[] eventDetails = getEventDetail(eventId);
        String[] eventStrings = new String[eventDetails.length];
        for(int j = 0; j < eventDetails.length; j++) {
            eventStrings[j] = eventDetails[j].getName();
        }

        name.setText(event.getName());
        date.setText(event.getDate());
        time.setText(event.getTime());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, eventStrings
        );
        list_event_details.setAdapter(arrayAdapter);
        list_event_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(EventDetailActivity.this, DetailActivity.class);
                intent.putExtra("id", eventDetails[i].getDetailId());
                startActivity(intent);
            }
        });
    }

    private EventDetail[] getEventDetail(int eventId) {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        EventDetail[] events = null;
        try {
            db = helper.getReadableDatabase();

            Cursor cursor= db.rawQuery("select DISTINCT * from Event_Detail where eventId=" + eventId, null);

            int count = cursor.getCount();
            events = new EventDetail[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    EventDetail newEvent =  new EventDetail(cursor.getString(1),
                            cursor.getString(2), cursor.getInt(3), cursor.getInt(0));
                    events[ndx++] = newEvent;
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetailActivity / getEventDetail] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Log.d("message", msg);
        }
        return events;
    }

    private EventMaster getEvent(int eventId) {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        EventMaster newEvent = null;
        try {
            db = helper.getReadableDatabase();

            Cursor cursor= db.rawQuery("select DISTINCT * from Event_Master where _eventId=" + eventId, null);
            if (cursor.moveToFirst()) {
                newEvent = new EventMaster(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getInt(0));
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetailActivity / getEventDetail] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Log.d("message", msg);
        }
        return newEvent;
    }
}
