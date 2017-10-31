package ca.bcit.ass3.regan_perry;

/**
 * Created by mini_ on 2017-10-31.
 */

public class EventContribution {
    private String itemName;
    private String itemUnit;
    private int itemQuantity;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public EventContribution(String itemName, String itemUnit, int itemQuantity) {
        this.itemName = itemName;
        this.itemUnit = itemUnit;
        this.itemQuantity = itemQuantity;
    }
}
