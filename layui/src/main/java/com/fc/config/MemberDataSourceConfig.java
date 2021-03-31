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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

*/
/**
 * 主数据源配置
 *
 * @author 王昊
 *//*

@Configuration
@MapperScan(basePackages = MemberDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "memberSqlSessionFactory")
public class MemberDataSourceConfig {

    static final String PACKAGE = "com/nxt/iot/member";
    static final String MAPPER_LOCATION = "classpath:com/nxt/iot/member/dao/*.xml";

    @Bean(name = "memberDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.member.hikari")
    public DataSource memberDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "memberTransactionManager")
    public DataSourceTransactionManager memberTransactionManager() {
        return new DataSourceTransactionManager(memberDataSource());
    }

    @Bean
    public SqlSessionFactory memberSqlSessionFactory(@Qualifier("memberDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MemberDataSourceConfig.MAPPER_LOCATION));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager memberTransactionManager(@Qualifier("memberDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
*/
