package com.systemspecs.payrollfileinterface.itemProcessor;

import com.systemspecs.payrollfileinterface.model.Promotioninterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PromotionItemProcessor implements ItemProcessor<Promotioninterface, Promotioninterface> {

    private static final Logger log = LoggerFactory.getLogger(PromotionItemProcessor.class);

    @Override
    public Promotioninterface process(final Promotioninterface promotionInterface) throws Exception {
        
        return promotionInterface;
    }

}
