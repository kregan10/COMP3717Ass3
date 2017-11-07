package ca.bcit.ass3.regan_perry;

/**
 * Created by mini_ on 2017-10-31.
 */

public class EventDetail {

    private String name;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    private String unit;
    private int quantity;
    private int detailId;

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

    public EventDetail(String name, String unit, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public EventDetail(String name, String unit, int quantity, int detailId) {
        this.name = name;
        this.quantity = quantity;
        this.detailId = detailId;
        this.unit = unit;
    }

}
