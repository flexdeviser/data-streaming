package org.e4s.reference;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import java.util.Collection;
import java.util.UUID;
import org.e4s.reference.dto.Device;
import org.e4s.reference.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableCaching
public class App {


    @Autowired
    private HazelcastInstance instance;

    public static void main(String[] args) {
        SpringApplication.run(App.class).close();
    }

    @Bean
    @Profile("default") // Don't run from test(s)
    public ApplicationRunner runner(final DeviceService deviceService) {
        return args -> {

            Device device = deviceService.get("meter.001");
            if (device == null) {
                device = deviceService.add("meter.001", "meter", "meter 001");
            }
            Collection<DistributedObject> distributedObjects = instance.getDistributedObjects();
            for (DistributedObject object : distributedObjects)
            {
                if (object instanceof IMap)
                {
                    instance.getMap(object.getName()).destroy();
                    System.out.println("Map destroyed=" + instance.getMap(object.getName()).getName());
                }
            }


            System.out.println("Hit Enter to terminate...");
            System.in.read();
        };
    }

}
