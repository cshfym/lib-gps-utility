package com.gpstweak.service.converters

import com.gpstweak.model.GPSData
import com.gpstweak.parsers.converters.GPSDataConverter
import com.gpstweak.topograpix.GpxType
import com.gpstweak.parsers.parsers.GPXParseService
import org.junit.Before
import org.junit.Test

class GPSDataConverterTest {

    GPSDataConverter converter

    @Before
    void setUp() {
        converter = new GPSDataConverter()
    }

    @Test
    void testGPXConversion() {

        // Get instance of GpxType
        GPXParseService t = new GPXParseService()
        GpxType gpx = t.parseGpxType(new File("src/resources/sample_data.gpx"))

        GPSData gpsData = converter.convertFromGpxType(gpx)

        assert null != gpsData
        assert null != gpsData.timestamp
        assert null != gpsData.tracks
        assert gpsData.tracks.size() > 0
        assert gpsData.tracks[0].trackSequences.size() > 0
    }
}
