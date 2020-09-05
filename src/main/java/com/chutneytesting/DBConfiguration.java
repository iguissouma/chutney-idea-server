package com.chutneytesting;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DBConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConfiguration.class);

    public DBConfiguration() {
    }

    @Bean
    @DependsOn({"dbServer"})
    public DataSource dataSource(DataSourceProperties internalDataSourceProperties) {
        return internalDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Configuration
    @Profile({"db-h2"})
    static class H2Configuration {
        H2Configuration() {
        }

        @Bean
        @Primary
        @ConfigurationProperties("spring.datasource")
        public DataSourceProperties internalDataSourceProperties() {
            return new DataSourceProperties() {
                public String determineUsername() {
                    return this.getUsername();
                }

                public String determinePassword() {
                    return this.getPassword();
                }
            };
        }

        @Bean(
                value = {"dbServer"},
                destroyMethod = "stop"
        )
        Server dbServer(@Value("${chutney.db-server.port}") int dbServerPort, @Value("${chutney.db-server.base-dir:~/.chutney}") String baseDir) throws SQLException {
            Server h2Server = Server.createTcpServer(new String[]{"-tcp", "-tcpPort", String.valueOf(dbServerPort), "-tcpAllowOthers", "-baseDir", baseDir}).start();
            DBConfiguration.LOGGER.debug("Started H2 server " + h2Server.getURL());
            return h2Server;
        }
    }
}
