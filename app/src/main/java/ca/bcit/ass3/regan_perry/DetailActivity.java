package ca.bcit.ass3.regan_perry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    String eventIdString = null;
    int eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
//        eventId = Integer.parseInt(i.getStringExtra("id", 0));

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
