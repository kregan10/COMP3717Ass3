package ca.bcit.ass3.regan_perry;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindEventActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);
        final Intent i = new Intent(this, SearchResultActivity.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitButton = findViewById(R.id.submit_button);
        searchQuery = findViewById(R.id.search_query);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            i.putExtra("eventName", searchQuery.getText().toString());
            startActivity(i);
            }
        });


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
}
