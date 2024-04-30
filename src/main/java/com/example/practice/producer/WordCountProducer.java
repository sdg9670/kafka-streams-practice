package com.example.practice.producer;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.example.practice.PracticeUtil;
import com.example.practice.topic.WordCountTopic;

@Component
public class WordCountProducer {
    @Autowired
    private PracticeUtil practiceUtil;

    @Autowired
    KafkaTemplate<String, String> template;

    public void send(String message) {
        template.send(WordCountTopic.INPUT_TOPIC, message);
    }

    public void startSending() {
        CompletableFuture.runAsync(() -> {
            String[] words = new String[10];
            for (int i = 0; i < 10; i++) {
                words[i] = this.practiceUtil.randomWord();
            }
            while (true) {
                this.send(words[(int) (Math.random() * 10)]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
