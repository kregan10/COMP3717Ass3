package ca.bcit.ass3.regan_perry;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends Activity {
    private DatePicker datePicker;
    private TimePicker timePicker1;
    private Calendar calendar;
    private TextView dateView;
    private TextView timeView;
    private Button addEventButton;
    private EventMaster eventToAdd;

    private EditText eventNameView;
    private String eventName;

    private String eventDate;
    private String eventTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private String format = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventNameView = findViewById(R.id.name_entry);
        eventNameView.setHint(R.string.event_hint);

        addEventButton = (Button) findViewById(R.id.add_event_button);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timeView = (TextView) findViewById(R.id.time_View);


        dateView = (TextView) findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                eventName = eventNameView.getText().toString();
                eventTime = timeView.getText().toString();
                eventDate = dateView.getText().toString();
                eventToAdd = new EventMaster(eventName, eventDate, eventTime);
                addNewEvent(eventToAdd);
            }
        });
    }

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeView.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    private void addNewEvent(EventMaster event) {

    }

}