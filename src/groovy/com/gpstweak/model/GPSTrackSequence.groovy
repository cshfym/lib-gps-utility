package com.gpstweak.model

/**
 * Track sequence - treated as a "Lap"
 */
class GPSTrackSequence {

    Date timestamp // Start Time
    BigDecimal totalTimeSeconds
    BigDecimal totalMeters
    BigDecimal maximumSpeed
    Long calories
    Long averageHeartRate
    Long maximumHeartrate

    List<GPSTrackPoint> trackPoints

    public GPSTrackSequence() {
        trackPoints = []
    }

}
