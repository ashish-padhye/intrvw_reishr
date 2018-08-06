package org.intvw.reishr.service.impl;

import org.intvw.reishr.model.input.generated.CmfoodchainType;
import org.intvw.reishr.model.input.generated.BranchType;
import org.intvw.reishr.model.input.generated.OrderdetailType;
import org.intvw.reishr.model.input.generated.OrdersType;
import org.intvw.reishr.model.output.Branch;
import org.intvw.reishr.model.output.CmFoodChain;
import org.intvw.reishr.service.DataProcessingService;
import org.intvw.reishr.validation.CashCollectionDataUnmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by padhash on 04-08-2018.
 */
@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    public static final String EOD_CASH_COLLECTION_XSD_PATH = "/eod_cash_collection.xsd";
    public static final String CASH_COLLECTION_DROPBOX_PATH = "/cash_collection_dropbox";

    private static Logger log = Logger.getLogger("DataProcessingServiceImpl");

    @Autowired
    CashCollectionDataUnmarshaller cashCollectionDataUnmarshaller;

    @Override
    public CmFoodChain fetchMatchingData() {
        CmFoodChain cmFoodChain = null;
        List<Branch> matchingBranches = processData(true);
        if(matchingBranches != null && !matchingBranches.isEmpty()){
            cmFoodChain = new CmFoodChain();
            cmFoodChain.setBranch(matchingBranches);
        }
        return cmFoodChain;
    }

    @Override
    public CmFoodChain fetchNonMatchingData() {
        CmFoodChain cmFoodChain = null;
        List<Branch> nonMatchingBranches = processData(false);
        if(nonMatchingBranches != null && !nonMatchingBranches.isEmpty()){
            cmFoodChain = new CmFoodChain();
            cmFoodChain.setBranch(nonMatchingBranches);
        }
        return cmFoodChain;
    }

    private List<Branch> processData(boolean matchingBranchesRequired) {
        List<Branch> result = null;
        List<CmfoodchainType> cashDataBranches = cashCollectionDataUnmarshaller.unmarshal(EOD_CASH_COLLECTION_XSD_PATH, CASH_COLLECTION_DROPBOX_PATH);
        CmFoodChain cmFoodChain = null;
        if(cashDataBranches != null) {
            cmFoodChain = new CmFoodChain();
            for (CmfoodchainType cmfoodchainType : cashDataBranches) {
                OrdersType orders = cmfoodchainType.getOrders();
                BranchType branch = cmfoodchainType.getBranch();
                float sumOfOrder = 0;
                if (orders != null && branch != null) {
                    List<OrderdetailType> orderDetails = orders.getOrderdetail();
                    if (orderDetails != null && !orderDetails.isEmpty()) {
                        for (OrderdetailType orderDetail : orderDetails) {
                            sumOfOrder += orderDetail.getBillamount();
                        }
                        if (matchingBranchesRequired && sumOfOrder == branch.getTotalcollection()) {
                            Branch br = new Branch();
                            br.setLocation(branch.getLocation());
                            br.setLocationId(branch.getLocationid());
                            br.setSumOfOrder(sumOfOrder);
                            br.setTotalCollection(branch.getTotalcollection());
                            if (result == null) {
                                result = new ArrayList<Branch>();
                            }
                            result.add(br);
                        } else if(!matchingBranchesRequired && sumOfOrder != branch.getTotalcollection()) {
                            Branch br = new Branch();
                            br.setLocation(branch.getLocation());
                            br.setLocationId(branch.getLocationid());
                            br.setSumOfOrder(sumOfOrder);
                            br.setTotalCollection(branch.getTotalcollection());
                            if (result == null) {
                                result = new ArrayList<Branch>();
                            }
                            result.add(br);
                        }
                    }
                }
            }
        }
        return result;
    }

    public CashCollectionDataUnmarshaller getCashCollectionDataUnmarshaller() {
        return cashCollectionDataUnmarshaller;
    }

    public void setCashCollectionDataUnmarshaller(CashCollectionDataUnmarshaller cashCollectionDataUnmarshaller) {
        this.cashCollectionDataUnmarshaller = cashCollectionDataUnmarshaller;
    }
}
