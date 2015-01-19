package com.gpstweak.model


class GPSData {

    Date timestamp
    List<GPSTrack> tracks

    public GPSData() {
        tracks = []
    }
}
