package infnet.tattooplace.models;

import java.io.Serializable;

/**
 * Created by thiagocortat on 18/05/13.
 */
public class ParseProxyGeoPoint implements Serializable {

    private double latitude;
    private double longitude;

    public ParseProxyGeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
