package com.example.mapactivity;

import com.google.android.gms.maps.model.Marker;

public class MarkerDTO {
    double latitude, longitude;
    String title;

    // 생성자, 필드 초기화
    public MarkerDTO(double latitude, double longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }

    // 접근자
    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // 설정자
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
