package com.ibm.sso;

import com.ibm.sso.common.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableCaching
public class IbmSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmSsoApplication.class, args);
	}
}
