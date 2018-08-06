package org.intvw.reishr.service;

import org.intvw.reishr.model.input.generated.CmfoodchainType;
import org.intvw.reishr.model.output.CmFoodChain;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by padhash on 04-08-2018.
 */
@Service
public interface DataProcessingService {

    CmFoodChain fetchMatchingData();

    CmFoodChain fetchNonMatchingData();

}
