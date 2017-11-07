package ca.bcit.ass3.regan_perry;

/**
 * Created by mini_ on 2017-10-31.
 */

public class EventMaster {

    private String Name;
    private String Date;
    private String Time;
    private int id;

    public EventMaster(String name, String date, String time) {
        Name = name;
        Date = date;
        Time = time;
    }

    public EventMaster(String name, String date, String time, int id) {
        Name = name;
        Date = date;
        Time = time;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
