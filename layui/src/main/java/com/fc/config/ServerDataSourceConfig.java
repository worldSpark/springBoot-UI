/*
package com.fc.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

*/
/**
 * 辅数据源配置
 *
 * @author 王昊
 *//*

@Configuration
@MapperScan(basePackages = ServerDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "serverSqlSessionFactory")
public class ServerDataSourceConfig {
    static final String PACKAGE = "com/nxt/iot/server";
    static final String MAPPER_LOCATION = "classpath:com/nxt/iot/server/dao/*.xml";

    @Bean(name = "serverDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.server.hikari")
    @Primary
    public DataSource serverDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory serverSqlSessionFactory(@Qualifier("serverDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ServerDataSourceConfig.MAPPER_LOCATION));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager serverTransactionManager(@Qualifier("serverDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
*/
