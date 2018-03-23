package com.systemspecs.payrollfileinterface.itemProcessor;

import com.systemspecs.payrollfileinterface.model.UpdateStaffInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;



public class UpdateStaffItemProcessor implements ItemProcessor<UpdateStaffInterface, UpdateStaffInterface> {

    private static final Logger log = LoggerFactory.getLogger(PromotionItemProcessor.class);

    @Override
    public UpdateStaffInterface process(final UpdateStaffInterface updateStaffInterface) throws Exception {

        return updateStaffInterface;
    }

}

