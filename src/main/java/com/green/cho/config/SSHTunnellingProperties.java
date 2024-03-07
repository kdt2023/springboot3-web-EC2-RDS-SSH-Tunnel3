package com.green.cho.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.ssh.tunnel")
public class SSHTunnellingProperties {
	
	private String sshHost;   //ec2 주소
	private int sshPort;  //ec2 ssh  22
	private String username;  //아마존리눅스 유저 
	private String privateKey;//개인키
	private int localPort;//
	private String rdsHost;   //aws-RDS 엔드포인트
	private int rdsPort;  //3306

}
