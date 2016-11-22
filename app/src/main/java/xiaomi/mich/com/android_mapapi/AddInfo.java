package xiaomi.mich.com.android_mapapi;

import com.amap.api.maps.model.LatLng;

/**
 * Created by aaron on 2016/11/15.
 */

public class AddInfo {

    private LatLng latLng;
    private String address;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
