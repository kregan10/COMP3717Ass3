package ca.bcit.ass3.regan_perry;

public class EventDetail {

    private String name;
    private String unit;
    private int quantity;
    private int detailId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
}
