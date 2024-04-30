package com.example.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.practice.producer.LineSplitProducer;
import com.example.practice.producer.WordCountProducer;
import com.example.practice.streams.LineSplitStream;
import com.example.practice.streams.WordCountStream;

@SpringBootApplication
public class PracticeApplication {
	@Autowired
	LineSplitProducer lineSplitProducer;

	@Autowired
	LineSplitStream lineSplitStream;

	@Autowired
	WordCountProducer wordCountProducer;

	@Autowired
	WordCountStream wordCountStream;

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
	}

	@SuppressWarnings("unused")
	private void runLineSplit() {
		this.lineSplitStream.start();
		this.lineSplitProducer.startSending();
	}

	@SuppressWarnings("unused")
	private void runWordCount() {
		this.wordCountStream.start();
		this.wordCountProducer.startSending();
	}

	@Bean
	ApplicationRunner runner() {
		return args -> {
			this.runWordCount();
		};
	}
}
