package com.l9e.transaction.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author meizs
 * @create 2018-04-10 16:31
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源为{}", DataSourceContextHolder.getDB());

        return DataSourceContextHolder.getDB();
    }

}
