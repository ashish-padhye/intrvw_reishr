package org.intvw.reishr.controller;

import org.intvw.reishr.model.output.CmFoodChain;
import org.intvw.reishr.service.DataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by padhash on 05-08-2018.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan( basePackages = {"org.intvw.reishr"})
public class MatchingCashDataController {
    @Autowired
    private DataProcessingService dataProcessingService;

    @RequestMapping("/matching")
    public CmFoodChain fetchMatchingCollectionBranches() {
        return dataProcessingService.fetchMatchingData();
    }

    public DataProcessingService getDataProcessingService() {
        return dataProcessingService;
    }

    public void setDataProcessingService(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

}
