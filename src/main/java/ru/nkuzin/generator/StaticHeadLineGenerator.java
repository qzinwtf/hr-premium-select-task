package ru.nkuzin.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class StaticHeadLineGenerator implements HeadLineGenerator {
    private static final int DEFAULT_LOWER_BOUND = 3;
    private static final int DEFAULT_UPPER_BOUND = 5;

    private int lowerBound = DEFAULT_LOWER_BOUND;
    private int upperBound = DEFAULT_UPPER_BOUND;

    private final HeadlineSource headlineSource;
    private final Random random = new Random();

    public String generateHeadline() {
        final int numberOfWords = random.nextInt(upperBound + 1 - lowerBound) + lowerBound;
        List<String> words = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            words.add(headlineSource.getNextWord());
        }
        return String.join(" ", words);
    }
}
