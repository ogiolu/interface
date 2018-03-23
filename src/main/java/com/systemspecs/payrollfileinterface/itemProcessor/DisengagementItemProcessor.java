package com.systemspecs.payrollfileinterface.itemProcessor;

import com.systemspecs.payrollfileinterface.model.DisengagementInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DisengagementItemProcessor implements ItemProcessor<DisengagementInterface, DisengagementInterface> {
    private static final Logger log = LoggerFactory.getLogger(DisengagementInterface.class);

    @Override
    public DisengagementInterface process(final DisengagementInterface disengageInterface) throws Exception {

        return disengageInterface;
    }
}
