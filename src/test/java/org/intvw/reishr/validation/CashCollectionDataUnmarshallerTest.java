package org.intvw.reishr.validation;

import org.intvw.reishr.exceptions.InvalidInputException;
import org.intvw.reishr.model.input.generated.CmfoodchainType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by padhash on 05-08-2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CashCollectionDataUnmarshallerTest {

    public static final String EOD_CASH_COLLECTION_XSD_PATH_TEST_FILES_PROPER = "/test_proper_files/eod_cash_collection.xsd";
    public static final String CASH_COLLECTION_DROPBOX_PATH_TEST_FILES_PROPER = "/test_proper_files/cash_collection_dropbox";

    public static final String EOD_CASH_COLLECTION_XSD_PATH_TEST_FILES_IMPROPER = "/test_improper_files/eod_cash_collection.xsd";
    public static final String CASH_COLLECTION_DROPBOX_PATH_TEST_FILES_IMPROPER = "/test_improper_files/cash_collection_dropbox";

    @Autowired
    CashCollectionDataUnmarshaller cashCollectionDataUnmarshaller;

    @Test
    public void unmarshal_test_with_proper_files() {
        List<CmfoodchainType> collectionData = cashCollectionDataUnmarshaller.unmarshal(EOD_CASH_COLLECTION_XSD_PATH_TEST_FILES_PROPER, CASH_COLLECTION_DROPBOX_PATH_TEST_FILES_PROPER);
        Assert.assertTrue(collectionData!=null && !collectionData.isEmpty());
    }

    @Test(expected = InvalidInputException.class)
    public void unmarshal_test_with_improper_files() {
        cashCollectionDataUnmarshaller.unmarshal(EOD_CASH_COLLECTION_XSD_PATH_TEST_FILES_IMPROPER, CASH_COLLECTION_DROPBOX_PATH_TEST_FILES_IMPROPER);
    }
}
