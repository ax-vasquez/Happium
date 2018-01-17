package io.happium.eureka_registry_service.configuration;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaServer
public class EurekaServerConfiguration {

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfigBean( InetUtils inetUtils ) {

        EurekaInstanceConfigBean eurekaInstanceConfigBean = new EurekaInstanceConfigBean( inetUtils );
        eurekaInstanceConfigBean.setAppname("eurekaRegistryServer");
        return eurekaInstanceConfigBean;

    }

    @Bean
    public EurekaClientConfigBean eurekaClientConfigBean() {

        EurekaClientConfigBean eurekaClientConfigBean = new EurekaClientConfigBean();
        eurekaClientConfigBean.setRegisterWithEureka( false );  // Prevent self-registering
        eurekaClientConfigBean.setFetchRegistry( false );       // Prevent fetching registry
        return eurekaClientConfigBean;

    }

}
