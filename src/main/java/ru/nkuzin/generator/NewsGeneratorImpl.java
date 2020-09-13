package ru.nkuzin.generator;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import ru.nkuzin.model.News;

@RequiredArgsConstructor
public class NewsGeneratorImpl implements NewsGenerator {
    private final HeadLineGenerator headLineGenerator;
    private final Random random = new Random();

    @Override
    public News generate() {
        return News
                .builder()
                .headline(headLineGenerator.generateHeadline())
                .priority(calculatePriority())
                .build();
    }

    private int calculatePriority() {
        final int i = random.nextInt(100);
        if (i > 98) {
            return 9;
        } else if (i > 96) {
            return 8;
        } else if (i > 92) {
            return 7;
        } else if (i > 88) {
            return 6;
        } else if (i > 80) {
            return 5;
        } else if (i > 70) {
            return 4;
        } else if (i > 58) {
            return 3;
        } else if (i > 44) {
            return 2;
        } else if (i > 28) {
            return 1;
        }
        return 0;
    }
}
