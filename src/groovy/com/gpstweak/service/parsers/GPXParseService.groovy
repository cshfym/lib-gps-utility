package com.gpstweak.service.parsers

import com.gpstweak.topograpix.GpxType
import org.springframework.stereotype.Component

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller

@Component
public class GPXParseService {

    public GPXParseService() { }

    public GpxType parseGpxType(File input) throws JAXBException {

        GpxType gpx

        try {
            JAXBContext jc = JAXBContext.newInstance(GpxType.class);
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<GpxType> root = (JAXBElement<GpxType>) u.unmarshal(input);
            gpx = root.getValue();
        } catch(JAXBException ex) {
            throw ex
        }

        gpx
    }
}
