package ru.nkuzin.server;

import com.beust.jcommander.JCommander;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import ru.nkuzin.analyzer.NewsAnalyzerImpl;
import ru.nkuzin.generator.TcpNewsReceiver;

@Slf4j
public class ServerApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        log.info("Starting server");
        final ServerArgs serverArgs = new ServerArgs();
        JCommander
                .newBuilder()
                .addObject(serverArgs)
                .build()
                .parse(args);
        log.info("Args provided {}", serverArgs);
        final TcpNewsReceiver tcpNewsReceiver = new TcpNewsReceiver(serverArgs.getPort(), new NewsAnalyzerImpl());
        tcpNewsReceiver.start();
    }
}
