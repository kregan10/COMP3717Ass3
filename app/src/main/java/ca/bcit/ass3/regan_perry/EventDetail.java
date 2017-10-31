package ca.bcit.ass3.regan_perry;

/**
 * Created by mini_ on 2017-10-31.
 */

public class EventDetail {

    private String name;
    private int quantity;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EventDetail(String name, int quantity, String date) {
        this.name = name;

        this.quantity = quantity;
        this.date = date;
    }

}
