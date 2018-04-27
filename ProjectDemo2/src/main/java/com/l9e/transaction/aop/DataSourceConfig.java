package com.l9e.transaction.aop;

/**
 * @author meizs
 * @create 2018-04-10 16:59
 **/
/*

@Configuration
public class DataSourceConfig {


    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.master.datasource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.slave.datasource")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.masterDatasource, masterDataSource);
        targetDataSources.put(DatabaseType.slaveDatasource, slaveDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);          //该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(masterDataSource);    // 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }


}
*/

