package com.l9e.transaction.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author meizs
 * @create 2018-04-04 16:28
 **/
@Component("refundTicketJob")
public class RefundTicketJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendRefund() {

        logger.info("********sendRefund*******");

    }


}
