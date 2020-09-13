package ru.nkuzin.generator;

import java.util.Random;

public class StaticHeadLineSource implements HeadlineSource {

    private static final String[] WORDS = new String[] {
            "up",
            "down",
            "rise",
            "fall",
            "good",
            "bad",
            "success",
            "failure",
            "high",
            "low",
            "über",
            "unter"
    };

    private final Random random = new Random();

    public String getNextWord() {
        return WORDS[random.nextInt(WORDS.length)];
    }
}
