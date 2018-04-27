package com.l9e.transaction.aop;

/**
 * @author meizs
 * @create 2018-04-10 17:24
 **/


/*
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDb1",
        transactionManagerRef = "transactionManageDb1",
        basePackages = {"com.l9e.transaction.dao"})

public class HibernateDb1Config {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    //@Primary
    @Bean(name = "entityManagerFactoryDb1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder.dataSource(masterDataSource)
                .properties(getVendorProperties(masterDataSource))
                .packages("com.l9e.transaction.bean")
                .persistenceUnit("Db1Unit").build();
        return em;
    }


    //@Primary
    @Bean(name = "transactionManageDb1")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }


    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }


}
*/

