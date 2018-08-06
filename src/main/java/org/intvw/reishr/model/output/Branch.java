package org.intvw.reishr.model.output;

/**
 * Created by padhash on 04-08-2018.
 */
public class Branch {

    private String location;
    private float totalCollection;
    private float sumOfOrder;
    private String locationId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(float totalCollection) {
        this.totalCollection = totalCollection;
    }

    public float getSumOfOrder() {
        return sumOfOrder;
    }

    public void setSumOfOrder(float sumOfOrder) {
        this.sumOfOrder = sumOfOrder;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
