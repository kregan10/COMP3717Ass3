package ca.bcit.ass3.regan_perry;

/**
 * Created by mini_ on 2017-10-31.
 */

public class EventMaster {

    private String Name;
    private String Date;
    private String Time;

    public EventMaster(String name, String date, String time) {
        Name = name;
        Date = date;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
