package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    MyPartyDbHelper helper;

    EventDetail newEventDetail = null;
    String eventIdString = null;
    int detailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        detailId = i.getIntExtra("id", 0 );
        TextView nameView = (TextView) findViewById(R.id.name_text);
        TextView quantityView = (TextView) findViewById(R.id.quantity);
        TextView unitView = (TextView) findViewById(R.id.units);
        EventDetail event = getEventDetail(detailId);
        nameView.setText(event.getName());
        quantityView.setText("" + event.getQuantity());
        unitView.setText(event.getUnit());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
            case R.id.action_edit:
                return true;
            case R.id.action_delete:
                MyPartyDbHelper helper = new MyPartyDbHelper(this);
                db = helper.getReadableDatabase();
                helper.deleteEventDetail(db, newEventDetail);
                Log.d("inDetailActivity", "" + newEventDetail.getDetailId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private EventDetail getEventDetail(int eventId) {
        SQLiteOpenHelper helper = new MyPartyDbHelper(this);
        newEventDetail = null;
        try {
            db = helper.getReadableDatabase();

            Cursor cursor= db.rawQuery("select DISTINCT * from Event_Detail where _detailId=" + eventId, null);
            if (cursor.moveToFirst()) {
                newEventDetail = new EventDetail(cursor.getString(1), cursor.getString( 2),
                        cursor.getInt(3));
                newEventDetail.setDetailId(cursor.getInt(0));
                Log.d("inGetEventDetail", "" + newEventDetail.getDetailId());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[EventDetailActivity / getEventDetail] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Log.d("message", msg);
        }
        return newEventDetail;
    }
}
