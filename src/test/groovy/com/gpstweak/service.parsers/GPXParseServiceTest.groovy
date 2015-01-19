package com.gpstweak.parsers

import com.gpstweak.parsers.parsers.GPXParseService
import com.gpstweak.topograpix.GpxType
import org.junit.Test

class GPXParseServiceTest {

    @Test
    void testParseGpxType() {

        GPXParseService t = new GPXParseService()
        GpxType gpx = t.parseGpxType(new File("src/resources/sample_data.gpx"))

        assert null != gpx
        assert gpx.getTrk().size() == 1

        gpx.getTrk().each { track ->
            println track.getName()
            track.getTrkseg().each { segment ->
                segment.getTrkpt().each { trackPoint ->
                    println trackPoint
                }
            }
        }
    }
}
