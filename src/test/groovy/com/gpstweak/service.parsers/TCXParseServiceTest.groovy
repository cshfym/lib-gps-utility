package com.gpstweak.parsers

import com.gpstweak.service.parsers.TCXParseService
import com.gpstweak.trainingcenterdatabase_v2.TrainingCenterDatabaseT
import org.junit.Test


class TCXParseServiceTest {

    @Test
    void testParseGpxType() {

        TCXParseService service = new TCXParseService()
        TrainingCenterDatabaseT trainingCenter = service.parseTcxType(new File("src/resources/sample_data.tcx"))

        assert null != trainingCenter
        assert null != trainingCenter.activities

        trainingCenter.activities.activity.each { activity ->
            println "Sport: ${activity.sport}"
            activity.lap.each { lap ->
                println lap
            }
        }
    }
}
