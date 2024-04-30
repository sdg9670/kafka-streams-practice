package com.example.practice.streams;

import java.util.Arrays;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.practice.topic.LineSplitTopic;

@Component
public class LineSplitStream {
    @Autowired
    private KafkaStreamsConfig streamsConfig;

    private KafkaStreams streams;

    private KStream<String, String> splitLines(KStream<String, String> source) {
        return source.flatMapValues(value -> Arrays.asList(value.split("\\W+")));
    }

    private Topology build() {
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> input = builder.stream(LineSplitTopic.INPUT_TOPIC);

        KStream<String, String> output = this.splitLines(input);
        output.to(LineSplitTopic.OUTPUT_TOPIC);

        final Topology topology = builder.build();
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