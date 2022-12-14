package org.e4s.datapump;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.sql.Timestamp;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.e4s.model.Timestamped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args).close();
    }


    @Autowired
    private KafkaTemplate<UUID, Timestamped> kafkaTemplate;

    @Bean
    @Profile("default") // Don't run from test(s)
    public ApplicationRunner runner() {
        return args -> {

            while (true) {

                try {
                    String randomName = RandomStringUtils.randomAlphabetic(10, 20);

                    Class cls = Class.forName("org.e4s.model.Foo1");
                    Timestamped foo1 = (Timestamped) cls.getConstructor().newInstance();

                    MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

                    MethodType mgt = MethodType.methodType(void.class, String.class);
                    MethodHandle setFoo = publicLookup.findVirtual(cls, "setFoo", mgt);

                    setFoo.invokeWithArguments(foo1, randomName);

                    MethodType mst = MethodType.methodType(void.class, Timestamp.class);
                    MethodHandle setTimeKey = publicLookup.findVirtual(cls, "setTimeKey", mst);
                    setTimeKey.invokeWithArguments(foo1, new Timestamp(System.currentTimeMillis()));

                    // send message
                    kafkaTemplate.send("default", UUID.randomUUID(), foo1).thenRun(() -> {
                        System.out.println(randomName);
                    });

                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                Thread.sleep(2000);
            }
        };
    }


}
