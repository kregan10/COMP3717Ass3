package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddEventDetailActivity extends AppCompatActivity {
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventId = (Integer) getIntent().getExtras().get("eventId");

        Log.d("AddEventDetailEactivity", "Adding detail to event id=" + eventId);

        final EditText detailName = (EditText) findViewById(R.id.setDetailName);
        final EditText detailUnit = (EditText) findViewById(R.id.setDetailUnit);
        final EditText detailQuantity = (EditText) findViewById(R.id.setDetailQuantity);
        final Button button = (Button) findViewById(R.id.add_detail_button);
        final TextView errorLabel = (TextView) findViewById(R.id.addDetailErrorLabel);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = detailName.getText().toString().trim();
                String unit = detailUnit.getText().toString().trim();
                String quantity = detailQuantity.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(unit) || TextUtils.isEmpty(quantity)) {
                    Log.d("AddEventDetailActivity", "User didn't fill in form");
                    errorLabel.setText(R.string.addDetailErrorLabel);
                } else {
                    Log.d("AddEventDetailActivity", "Valid data. Attempting to add to database");
                    MyPartyDbHelper helper = new MyPartyDbHelper(AddEventDetailActivity.this);
                    SQLiteDatabase db = helper.getReadableDatabase();
                    helper.insertEventDetail(db, new EventDetail(name, unit, Integer.parseInt(quantity)), eventId);
                    Intent i = new Intent(AddEventDetailActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
}
