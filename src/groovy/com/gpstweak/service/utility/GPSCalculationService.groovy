package com.gpstweak.service.utility

import com.gpstweak.model.GPSData
import groovy.time.TimeDuration
import org.springframework.stereotype.Component

@Component
class GPSCalculationService {

  public GPSCalculationService() { }

  public BigDecimal calculateTotalMovingTimeMinutes(GPSData data) {

    def seconds = 0
    data.tracks?.each { track ->
      track.trackSequences?.each { trackSequence ->
        Date lastTimestamp = null
        trackSequence.trackPoints?.each { trackPoint ->
          if(!lastTimestamp) {
            lastTimestamp = trackPoint.timestamp
          } else {
            use(groovy.time.TimeCategory) {
              def duration = trackPoint.timestamp - lastTimestamp
              seconds += duration.seconds
            }
            lastTimestamp = trackPoint.timestamp
          }
        }
      }
    }
    Math.round((new BigDecimal(seconds / 60) * 100)) / 100
  }

  public TimeDuration calculateActivityElapsedTimeMinutes(GPSData data) {

    def elapsedTime

    def lastTrack = data.tracks?.last()
    def lastTrackSequence = lastTrack.trackSequences?.last()
    def lastTrackPoint = lastTrackSequence.trackPoints?.last()

    use(groovy.time.TimeCategory) {
      elapsedTime = lastTrackPoint.timestamp - data.timestamp
    }

    elapsedTime
  }
}
