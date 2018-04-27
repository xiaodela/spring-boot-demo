package com.l9e.transaction.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author meizs
 * @create 2018-04-10 16:29
 **/
public class DataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final DatabaseType DEFAULT_DS = DatabaseType.masterDatasource;

    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<DatabaseType>();

    // 获取数据源名
    public static DatabaseType getDB() {
        return contextHolder.get() == null ? DatabaseType.masterDatasource : contextHolder.get();
    }

    // 设置数据源名
    public static void setDB(DatabaseType dbType) {
        log.info("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
