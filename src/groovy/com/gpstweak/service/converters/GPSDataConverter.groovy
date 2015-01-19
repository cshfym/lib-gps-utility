package com.gpstweak.service.converters

import com.gpstweak.model.GPSData
import com.gpstweak.model.GPSTrack
import com.gpstweak.model.GPSTrackPoint
import com.gpstweak.model.GPSTrackSequence
import com.gpstweak.topograpix.GpxType
import com.gpstweak.topograpix.TrkType
import com.gpstweak.topograpix.TrksegType
import com.gpstweak.topograpix.WptType
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Converts from the Topograpix GpxType object to a GPSData object.
 */
@Component
class GPSDataConverter {

    //DateTimeFormatter xmlGregorianFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss ")

    public GPSDataConverter() { }

    public GPSData convertFromGpxType(GpxType gpxType) {
        new GPSData(
            timestamp: gpxType.metadata.time.toGregorianCalendar().getTime(),
            tracks: convertTrackFromGpxType(gpxType.getTrk())
        )
    }

    protected List<GPSTrack> convertTrackFromGpxType(List<TrkType> trackList) {
        List<GPSTrack> tracks = []
        trackList.each { track ->
            tracks << new GPSTrack(
                    name: track.name,
                    trackSequences: convertTrackSequencesFromGpxType(track.trkseg)
            )
        }
        tracks
    }

    protected List<GPSTrackSequence> convertTrackSequencesFromGpxType(List<TrksegType> trkSeqTypeList) {
        List<GPSTrackSequence> trackSequences = []
        trkSeqTypeList.each { trkSeqType ->
            trackSequences << new GPSTrackSequence(
                    trackPoints: convertTrackPointsFromGpxType(trkSeqType.trkpt)
            )
        }
        trackSequences
    }

    protected List<GPSTrackPoint> convertTrackPointsFromGpxType(List<WptType> wptTypeList) {
        List<GPSTrackPoint> gpsTrackPoints = []
        wptTypeList.each { wptType ->
            gpsTrackPoints << new GPSTrackPoint(
                    timestamp: wptType.time.toGregorianCalendar().getTime(),
                    latitude: wptType.lat,
                    longitude: wptType.lon,
                    elevationMeters: wptType.ele,
                    distanceMeters: 0.0, // TODO: Calculate?
                    heartRate: 0 // TODO: Resolve
            )
        }
        gpsTrackPoints
    }
}
