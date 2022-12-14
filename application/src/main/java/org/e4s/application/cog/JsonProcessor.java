package org.e4s.application.cog;

import java.io.File;
import java.util.UUID;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.e4s.model.Timestamped;
import org.e4s.stream.CompiledScript;
import org.e4s.stream.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

@Component
public class JsonProcessor {

    @Value("${kafka.topic:default}")
    private String inputTopic;

    @Autowired
    private ScriptService scriptService;


    JsonDeserializer<Timestamped> meterReadJsonDeserializer = new JsonDeserializer<>(Timestamped.class);
    JsonSerializer<Timestamped> meterReadJsonSerializer = new JsonSerializer<>();
    Serde<Timestamped> READ_SERDE = Serdes.serdeFrom(meterReadJsonSerializer, meterReadJsonDeserializer);

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<UUID, Timestamped> messageStream = streamsBuilder
            .stream(inputTopic, Consumed.with(Serdes.UUID(), READ_SERDE));
        // compile js
        CompiledScript script = scriptService.compile("greeting", new File("/Users/eric/Workspaces/eric4hy/js/greeting.js"),
                                                                     false);
        messageStream.foreach((key, value) -> {
            script.execute("greet", value);
        });

    }
}
