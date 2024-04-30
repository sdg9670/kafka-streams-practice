package com.example.practice.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class LineSplitTopic {
    static public final String INPUT_TOPIC = "line-split-input";
    static public final String OUTPUT_TOPIC = "line-split-output";

    @Bean
    NewTopic lineSplitInputTopic() {
        return TopicBuilder.name(INPUT_TOPIC).partitions(3).build();
    }

    @Bean
    NewTopic lineSplitOutputTopic() {
        return TopicBuilder.name(OUTPUT_TOPIC).partitions(3).build();
    }
}