package com.systemspecs.payrollfileinterface.itemProcessor;

import com.systemspecs.payrollfileinterface.model.NewStaffInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class NewStaffItemProcessor implements ItemProcessor<NewStaffInterface, NewStaffInterface> {

    private static final Logger log = LoggerFactory.getLogger(PromotionItemProcessor.class);

    @Override
    public NewStaffInterface process(final NewStaffInterface newStaffInterface) throws Exception {

        return newStaffInterface;
    }

}
