package com.example.practice.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class WordCountTopic {
    static public final String INPUT_TOPIC = "word-count-input";
    static public final String OUTPUT_TOPIC = "word-count-output";

    @Bean
    NewTopic wordCountInputTopic() {
        return TopicBuilder.name(INPUT_TOPIC).partitions(3).build();
    }

    @Bean
    NewTopic wordCountOutputTopic() {
        return TopicBuilder.name(OUTPUT_TOPIC).partitions(3).build();
    }
}