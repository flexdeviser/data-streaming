package org.e4s.reference.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        NetworkConfig network = new NetworkConfig();
        network.getJoin().getMulticastConfig().setEnabled(true);

        return new Config()
            .setInstanceName("e4s-reference")
            .setNetworkConfig(network);
    }

}
