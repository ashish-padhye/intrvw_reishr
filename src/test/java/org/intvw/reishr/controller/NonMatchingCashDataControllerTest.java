package org.intvw.reishr.controller;

import org.intvw.reishr.service.DataProcessingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by padhash on 05-08-2018.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(NonMatchingCashDataController.class)
public class NonMatchingCashDataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DataProcessingService dataProcessingService;

    @Test
    public void test_fetch__non_matching_collection_branches() throws  Exception{
        mockMvc.perform(get("/nonmatching")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.branch[*].locationId", containsInAnyOrder("BOM-1234-456","PUN-1234-456")))
                .andExpect(jsonPath("$.branch[*].sumOfOrder", notNullValue()));
    }

}
