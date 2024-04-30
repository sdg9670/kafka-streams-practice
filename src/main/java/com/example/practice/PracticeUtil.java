package com.example.practice;

import org.springframework.stereotype.Component;

@Component
public class PracticeUtil {
    public String randomWord() {
        int size = (int) (Math.random() * 8) + 3;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            // lowercase a to uppercase Z
            final int upperCaseOffset = 65;
            final int lowerCaseOffset = 97;
            final int alphabetSize = 26;
            char c = (char) (Math.random() * alphabetSize + (Math.random() < 0.5 ? upperCaseOffset : lowerCaseOffset));
            sb.append(c);
        }

        return sb.toString();
    }

    public String randomPhrase() {
        int size = (int) (Math.random() * 8) + 3;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(this.randomWord());
            if (i < size - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}
