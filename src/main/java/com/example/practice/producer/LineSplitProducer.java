package com.example.practice.producer;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.example.practice.PracticeUtil;
import com.example.practice.topic.LineSplitTopic;

@Component
public class LineSplitProducer {
    @Autowired
    private PracticeUtil practiceUtil;

    @Autowired
    KafkaTemplate<String, String> template;

    public void send(String message) {
        template.send(LineSplitTopic.INPUT_TOPIC, message);
    }

    public void startSending() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                this.send(this.practiceUtil.randomPhrase());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
