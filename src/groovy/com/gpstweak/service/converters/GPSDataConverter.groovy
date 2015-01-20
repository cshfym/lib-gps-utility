package com.gpstweak.service.converters

import com.gpstweak.model.GPSData
import com.gpstweak.model.GPSTrack
import com.gpstweak.model.GPSTrackPoint
import com.gpstweak.model.GPSTrackSequence
import com.gpstweak.topograpix.GpxType
import com.gpstweak.topograpix.TrkType
import com.gpstweak.topograpix.TrksegType
import com.gpstweak.topograpix.WptType
import com.gpstweak.trainingcenterdatabase_v2.ActivityLapT
import com.gpstweak.trainingcenterdatabase_v2.ActivityT
import com.gpstweak.trainingcenterdatabase_v2.TrackT
import com.gpstweak.trainingcenterdatabase_v2.TrainingCenterDatabaseT
import org.springframework.stereotype.Component

/**
 * Converts from the Topograpix GpxType object to a GPSData object.
 */
@Component
class GPSDataConverter {

    public GPSDataConverter() { }

    public GPSData convertFromGpxType(GpxType gpxType) {
        new GPSData(
            timestamp: gpxType.metadata.time.toGregorianCalendar().getTime(),
            tracks: convertTrackFromGpxType(gpxType.getTrk())
        )
    }

    public GPSData convertFromTcxType(TrainingCenterDatabaseT tcxType) {

        // Get first activity.
        Date activityDate
        if(!tcxType.activities?.activity?.isEmpty()) {
          activityDate = tcxType.activities.activity.get(0).id.toGregorianCalendar().getTime()
        }
        new GPSData(
            timestamp: activityDate,
            tracks: convertTrackFromTcxType(tcxType.activities?.activity)
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

    protected List<GPSTrack> convertTrackFromTcxType(List<ActivityT> activities) {
        List<GPSTrack> tracks = []
        activities.each { activity ->
            tracks << new GPSTrack(
                name: "",
                trackSequences: convertTrackSequencesFromTcxType(activity.lap)
            )
        }
        tracks
    }

    protected List<GPSTrackSequence> convertTrackSequencesFromTcxType(List<ActivityLapT> laps) {
        List<GPSTrackSequence> trackSequences = []
        laps.each { lap ->
            trackSequences << new GPSTrackSequence(
              timestamp: lap.getStartTime().toGregorianCalendar().getTime(),
              totalTimeSeconds: lap.getTotalTimeSeconds(),
              totalMeters: lap.getDistanceMeters(),
              maximumSpeed: lap.getMaximumSpeed(),
              calories: lap.getCalories(),
              averageHeartRate: lap.getAverageHeartRateBpm().getValue(),
              maximumHeartrate: lap.getMaximumHeartRateBpm().getValue(),
              trackPoints: convertTrackPointsFromTcxType(lap.getTrack())
            )
        }
      trackSequences
    }

    protected List<GPSTrackPoint> convertTrackPointsFromTcxType(List<TrackT> tracks) {
        List<GPSTrackPoint> trackPoints = []
        tracks.each { track ->
            track.trackpoint.each { trackPoint ->
              trackPoints << new GPSTrackPoint(
                  timestamp: trackPoint.getTime().toGregorianCalendar().getTime(),
                  latitude: (null != trackPoint.getPosition()) ? trackPoint.getPosition().getLatitudeDegrees() : new BigDecimal(0.0),
                  longitude: (null != trackPoint.getPosition()) ? trackPoint.getPosition().getLongitudeDegrees() : new BigDecimal(0.0),
                  elevationMeters: (null != trackPoint.getAltitudeMeters()) ? trackPoint.getAltitudeMeters() : new BigDecimal(0.0),
                  distanceMeters: (null != trackPoint.getDistanceMeters()) ? trackPoint.getDistanceMeters() : new BigDecimal(0.0),
                  heartRate: (null != trackPoint.getHeartRateBpm()) ? trackPoint.getHeartRateBpm().getValue() : new Long(0)
              )
            }
        }
        trackPoints
    }
}
