package ca.bcit.ass3.regan_perry;

import android.app.Activity;
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

public class MainActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list_continents = (ListView) findViewById(R.id.list_events);

        String[] events = getEvents();
//        Log.d("Events", events[0]);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, events
        );

        list_continents.setAdapter(arrayAdapter);

        list_continents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String continent = tv.getText().toString();

//                Intent intent = new Intent(MainActivity.this, CountryActivity.class);
//                intent.putExtra("continent", continent);
//
//                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private String[] getEvents() {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        String[] events = null;
        try {
            db = helper.getReadableDatabase();
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Halloween Party", "Oct 30th, 2017", "6:30 PM"));
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("Christmas Party", "Dec 20th, 2017", "12:30 PM"));
            ((MyPartyDbHelper) helper).insertEvent(db, new EventMaster("New Years Party", "Dec 31st, 2017", "8:00 PM"));

            Cursor cursor= db.rawQuery("select DISTINCT name from Event_Master", null);

            int count = cursor.getCount();
            events = new String[count];

            if (cursor.moveToFirst()) {
                int ndx=0;
                do {
                    events[ndx++] = cursor.getString(0);
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
