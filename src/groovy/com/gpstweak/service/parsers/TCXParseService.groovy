package com.gpstweak.service.parsers

import com.gpstweak.trainingcenterdatabase_v2.TrainingCenterDatabaseT
import org.springframework.stereotype.Component

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller

@Component
class TCXParseService {

    public TCXParseService() { }

    public TrainingCenterDatabaseT parseTcxType(File input) throws JAXBException {

        TrainingCenterDatabaseT trainingCenterDatabase

        try {
            JAXBContext jc = JAXBContext.newInstance(TrainingCenterDatabaseT.class);
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<TrainingCenterDatabaseT> root = (JAXBElement<TrainingCenterDatabaseT>) u.unmarshal(input);
            trainingCenterDatabase = root.getValue();
        } catch(JAXBException ex) {
            throw ex
        }

        trainingCenterDatabase
    }

}
