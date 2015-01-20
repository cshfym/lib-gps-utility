package com.gpstweak.utility

import com.gpstweak.model.GPSData
import com.gpstweak.service.converters.GPSDataConverter
import com.gpstweak.service.parsers.GPXParseService
import com.gpstweak.service.parsers.TCXParseService
import com.gpstweak.service.utility.GPSCalculationService
import com.gpstweak.topograpix.GpxType
import com.gpstweak.trainingcenterdatabase_v2.TrainingCenterDatabaseT
import groovy.time.TimeDuration
import org.junit.Before
import org.junit.Test

class GPSCalculationServiceTest {

  GPSCalculationService calculator
  GPSDataConverter converter

  @Before
  void setUp() {
    calculator = new GPSCalculationService()
    converter = new GPSDataConverter()
  }

  @Test
  void calculateTotalMovingTimeMinutesFromTCX() {

    GPSData tcxData = getSampleGPSDataFromTCX("src/resources/sample_data.gpx")
    BigDecimal tcxMinutes = calculator.calculateTotalMovingTimeMinutes(tcxData)
    println "Calculated sample TCX total moving time at ${tcxMinutes}"
    assert null != tcxMinutes
    assert tcxMinutes.equals(47.57)
  }

  @Test
  void calculateTotalMovingTimeMinutesFromGPX_1() {

    GPSData gpxData = getSampleGPSDataFromGPX("src/resources/sample_data.tcx")
    BigDecimal gpxMinutes = calculator.calculateTotalMovingTimeMinutes(gpxData)
    println "Calculated sample GPX total moving time at ${gpxMinutes}"
    assert null != gpxMinutes
    assert gpxMinutes.equals(209.3)
  }

  @Test
  void calculateTotalMovingTimeMinutesFromGPX_2() {

    GPSData gpxData = getSampleGPSDataFromGPX("src/resources/sample_data_3.gpx")
    BigDecimal gpxMinutes = calculator.calculateTotalMovingTimeMinutes(gpxData)
    println "Calculated sample GPX total moving time at ${gpxMinutes}"
    assert null != gpxMinutes
    assert gpxMinutes.equals(34.8)
  }

  @Test
  void calculateActivityElapsedTimeMinutesFromGPX() {

    GPSData gpxData = getSampleGPSDataFromGPX("src/resources/sample_data_3.gpx")
    TimeDuration duration = calculator.calculateActivityElapsedTimeMinutes(gpxData)
    println "Calculated sample GPX elapsed time at ${duration}"
    assert null != duration
    assert duration.minutes == 34
    assert duration.seconds == 48
  }

  private GPSData getSampleGPSDataFromTCX(String filename) {
    TCXParseService t = new TCXParseService()
    TrainingCenterDatabaseT trainingCenterDatabaseT = t.parseTcxType(new File(filename))
    converter.convertFromTcxType(trainingCenterDatabaseT)
  }

  private GPSData getSampleGPSDataFromGPX(String filename) {
    GPXParseService t = new GPXParseService()
    GpxType gpx = t.parseGpxType(new File(filename))
    converter.convertFromGpxType(gpx)
  }
}
