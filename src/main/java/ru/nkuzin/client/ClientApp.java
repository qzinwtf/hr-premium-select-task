package ru.nkuzin.client;

import static java.util.concurrent.TimeUnit.MILLISECONDS;


import com.beust.jcommander.JCommander;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.extern.slf4j.Slf4j;
import ru.nkuzin.generator.NewsGeneratorImpl;
import ru.nkuzin.generator.StaticHeadLineGenerator;
import ru.nkuzin.generator.StaticHeadLineSource;
import ru.nkuzin.generator.TcpNewsSender;

@Slf4j
public class ClientApp {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        log.info("Starting client");
        final ClientArgs clientArgs = new ClientArgs();
        JCommander
                .newBuilder()
                .addObject(clientArgs)
                .build()
                .parse(args);
        final StaticHeadLineSource headlineSource = new StaticHeadLineSource();
        final StaticHeadLineGenerator headLineGenerator = new StaticHeadLineGenerator(headlineSource);
        final NewsGeneratorImpl newsGenerator = new NewsGeneratorImpl(headLineGenerator);
        final TcpNewsSender tcpNewsSender = new TcpNewsSender(clientArgs.getServerHost(), clientArgs.getServerPort());
        tcpNewsSender.start();
        final int period = 1000 / clientArgs.getFrequency();
        log.info("Parameters provided {}", clientArgs);
        scheduler.scheduleAtFixedRate(() -> tcpNewsSender.send(newsGenerator.generate()), 0, period, MILLISECONDS);
    }
}
