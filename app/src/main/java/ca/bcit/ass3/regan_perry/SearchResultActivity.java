package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class SearchResultActivity extends AppCompatActivity {

    String eventName;
    ListView searchResults;
    private MyPartyDbHelper helper;
    private SQLiteDatabase db;
    private EventMaster[] eventResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent i = getIntent();
        eventName = i.getStringExtra("eventName");
        searchResults = findViewById(R.id.search_results);
        helper = new MyPartyDbHelper(this);
        db = helper.getReadableDatabase();

        eventResults = helper.findEvent(db, eventName);
        for(int j = 0; j < eventResults.length; j++) {
            Log.d("event Name", eventResults[j].getName());
        }
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, eventResults
//        );
//        list_event_details.setAdapter(arrayAdapter);
//        list_event_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(EventDetailActivity.this, DetailActivity.class);
//                intent.putExtra("id", eventDetails[i].getDetailId());
//                startActivity(intent);
//            }
//        });

    }

}
