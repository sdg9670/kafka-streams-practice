package com.example.practice.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.practice.topic.WordCountTopic;

@Component
public class WordCountStream {
    @Autowired
    private KafkaStreamsConfig streamsConfig;

    private KafkaStreams streams;

    private KStream<String, String> convertToLowercase(KStream<String, String> source) {
        return source.mapValues(value -> value.toLowerCase());
    }

    private Topology build() {
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> input = builder.stream(WordCountTopic.INPUT_TOPIC);

        KStream<String, Long> output = this.convertToLowercase(input)
                .groupBy((key, value) -> value)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("WordCountStore"))
                .toStream();
        output.to(WordCountTopic.OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        System.out.println(topology.describe());
        return topology;
    }

    private void createStreams() {
        this.streams = this.streamsConfig.createStream(this.build());
    }

    public void start() {
        if (this.streams == null)
            this.createStreams();
        this.streams.start();
    }
}