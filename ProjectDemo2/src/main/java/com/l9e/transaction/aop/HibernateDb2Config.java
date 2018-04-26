package com.l9e.transaction.aop;

/**
 * @author meizs
 * @create 2018-04-10 17:24
 **/

/*
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDb2",
        transactionManagerRef = "transactionManageDb2",
        basePackages = {"com.l9e.transaction.dao"})
public class HibernateDb2Config {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("slaveDataSource")
    private DataSource slaveDataSource;


    @Bean(name = "entityManagerFactoryDb2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder.dataSource(slaveDataSource)
                .properties(getVendorProperties(slaveDataSource))
                .packages("com.l9e.transaction.bean")
                .persistenceUnit("Db1Unit").build();
        return em;
    }


    @Bean(name = "transactionManageDb2")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }


}
*/


