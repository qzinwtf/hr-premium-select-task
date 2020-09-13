package ru.nkuzin.analyzer;

import static java.util.concurrent.TimeUnit.SECONDS;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import ru.nkuzin.model.News;

@Slf4j
public class NewsAnalyzerImpl implements NewsAnalyzer {
    private static final String[] POSITIVE_WORDS = new String[] {
            "up", "rise", "good", "success", "high", "über"
    };
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        Arrays.sort(POSITIVE_WORDS);
        final NewsProcessor newsProcessor = new NewsProcessor();
        scheduler.scheduleAtFixedRate(newsProcessor, 10, 10, SECONDS);
    }

    private static final ArrayBlockingQueue<News> newsQueue = new ArrayBlockingQueue<>(10000);


    @Override
    public void addNews(final News news) {
        final String[] headLineWords = news.getHeadline().split(" ");
        final int totalNumberOfWords = headLineWords.length;
        final long numberOfPositiveWords = Arrays
                .stream(headLineWords)
                .filter(item -> Arrays.binarySearch(POSITIVE_WORDS, item) >= 0)
                .count();
        final double positiveRatio = (double) numberOfPositiveWords / (double) totalNumberOfWords;
        if (positiveRatio > 0.5) {
            newsQueue.offer(news);
        }
    }

    private static class NewsProcessor implements Runnable {

        @Override
        public void run() {
            List<News> allNews = new ArrayList<>();
            newsQueue.drainTo(allNews);
            log.info("Number of positive news in last 10 seconds: {}", allNews.size());
            final String headLines =
                    allNews
                            .stream()
                            .sorted(Comparator.comparing(News::getPriority).reversed())
                            .map(News::getHeadline)
                            .distinct()
                            .limit(3)
                            .collect(Collectors.joining(", "));
            log.info("the unique headlines of up to three of the highest-priority positive news items seen during the last 10 seconds");
            log.info(headLines);
        }
    }
}
