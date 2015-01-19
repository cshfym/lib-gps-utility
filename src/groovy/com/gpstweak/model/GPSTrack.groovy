package com.gpstweak.model


class GPSTrack {

    String name
    List<GPSTrackSequence> trackSequences

    public GPSTrack() {
        trackSequences = []
    }
}
