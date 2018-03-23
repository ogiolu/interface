package com.systemspecs.payrollfileinterface.itemProcessor;

import com.systemspecs.payrollfileinterface.model.TransferInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class TransferItemProcessor implements ItemProcessor<TransferInterface, TransferInterface> {

    private static final Logger log = LoggerFactory.getLogger(PromotionItemProcessor.class);

    @Override
    public TransferInterface process(final TransferInterface transferInterface) throws Exception {

        return transferInterface;
    }
}



