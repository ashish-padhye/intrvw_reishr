package org.intvw.reishr.validation;

import org.intvw.reishr.exceptions.InvalidInputException;
import org.intvw.reishr.model.input.generated.CmfoodchainType;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by padhash on 04-08-2018.
 */
@Component
public class CashCollectionDataUnmarshaller {

    private static Logger log = Logger.getLogger("CashCollectionDataUnmarshaller");

    public List<CmfoodchainType> unmarshal(String schemalocation, String inputDirectory) {
        List<CmfoodchainType> cashCollectionDataList = null;
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL schemaUrl = this.getClass().getResource(schemalocation);
        if (schemaUrl == null) {
            throw new InvalidInputException("Input Schema File not found or is corrupted");
        }
        log.info("Schema URL is : "+schemaUrl.toString());
        Schema schema = null;
        try {
            schema = sf.newSchema(new File(schemaUrl.toURI()));
        } catch (SAXException e) {
            throw new InvalidInputException("Error Instantiating Schema Factory for the Schema Definition File");
        } catch (URISyntaxException e) {
            throw new InvalidInputException("Input Schema Definition File not found or is corrupted");
        }
        Unmarshaller unmarshaller = null;
        File folder = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(CmfoodchainType.class);
            unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new CashCollectionValidationEventHandler());
            URL inputFileUrl = this.getClass().getResource(inputDirectory);
            if (inputFileUrl == null) {
                throw new InvalidInputException("Input File not found or is corrupted");
            }
            log.info("Input file directory URL is : " + inputFileUrl.toString());
            try {
                folder = new File(inputFileUrl.toURI());
            } catch (URISyntaxException e) {
                throw new InvalidInputException("Input File Directory not found or is corrupted");
            }
        } catch (JAXBException je) {
            throw new InvalidInputException("Error loading schema definition : "+je.getMessage());
        }
        File[] listOfFiles = folder.listFiles();
        for(File cashDataFile : listOfFiles) {
                CmfoodchainType cashCollectionData = null;
                try {
                    cashCollectionData = (CmfoodchainType) unmarshaller.unmarshal(cashDataFile);
                } catch (JAXBException e) {
                    log.severe("Error unmarshalling file "+cashDataFile.getName()+" : "+e.getMessage());
                    throw new InvalidInputException("\"Error unmarshalling file \"+cashDataFile.getName()+\" Please correct or remove the file and restart the process : \"+e.getMessage()");
                }
                log.info("Unmarshalled CmfoodchainType Object : "+cashCollectionData);
            if(cashCollectionDataList == null) {
                cashCollectionDataList = new ArrayList<CmfoodchainType>();
            }
            cashCollectionDataList.add(cashCollectionData);
        }
        return cashCollectionDataList;
    }

}
