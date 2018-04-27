package com.l9e.transaction.aop;

/**
 * @author meizs
 * @create 2018-04-10 17:02
 **/

/*
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.l9e.transaction.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisDbConfig {


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/xml/*.xml"));
        return factoryBean.getObject();
    }


    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
        return template;
    }

}
*/

