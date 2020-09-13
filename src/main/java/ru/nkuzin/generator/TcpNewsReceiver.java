package ru.nkuzin.generator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nkuzin.analyzer.NewsAnalyzer;
import ru.nkuzin.model.News;
import ru.nkuzin.util.SerializationUtil;

@Slf4j
@RequiredArgsConstructor
public class TcpNewsReceiver implements NewsReceiver {

    private final int port;
    private final NewsAnalyzer newsAnalyzer;

    @Override
    public void start() throws IOException, ClassNotFoundException {
        try (Selector selector = Selector.open()) {
            try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                serverSocketChannel.bind(new InetSocketAddress(port));
                serverSocketChannel.configureBlocking(false);
                final int validOps = serverSocketChannel.validOps();
                final SelectionKey key = serverSocketChannel.register(selector, validOps, null);
                while (true) {
                    selector.select();
                    final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey currentKey = iterator.next();
                        if (currentKey.isAcceptable()) {
                            final SocketChannel client = serverSocketChannel.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            log.info("Connection accepted from: {} ", client.getLocalAddress());
                        } else if (currentKey.isReadable()) {
                            final SocketChannel client = (SocketChannel) currentKey.channel();
                            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            client.read(byteBuffer);
                            try {
                                final News news = (News) SerializationUtil.deserialize(byteBuffer.array());
                                newsAnalyzer.addNews(news);
                            } catch (Exception e) {
                                log.error("Error on reading object", e);
                                client.close();
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }

}
