package com.green.cho.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SSHTunnellingConfig {

	@Primary
	@Bean
	DataSource dataSource(SSHTunnellingProperties sshTunnellingProperties,
			DataSourceProperties dataSourceProperties) throws JSchException {
		JSch jsch=new JSch();
		jsch.addIdentity(sshTunnellingProperties.getPrivateKey());
		
		Session sshSession=jsch.getSession(
				sshTunnellingProperties.getUsername(), 
				sshTunnellingProperties.getSshHost(), 
				sshTunnellingProperties.getSshPort());
		
		sshSession.setConfig("StrictHostKeyChecking", "no");
		sshSession.connect();
		log.info("ssh-tunnel 접속완료!");
		
		int localPort=sshSession.setPortForwardingL(sshTunnellingProperties.getLocalPort(), 
				sshTunnellingProperties.getRdsHost(), sshTunnellingProperties.getRdsPort());
		
		/*
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setUrl(dataSourceProperties.getUrl().replace("[LOCAL_PORT]", String.valueOf(localPort)));
		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());
		
		return dataSource;
		*/
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSourceProperties.getUrl().replace("[LOCAL_PORT]", String.valueOf(localPort)));
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());

        // You can add more configuration to HikariConfig if needed
        // For example: config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
	}
}
