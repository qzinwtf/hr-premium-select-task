package ru.nkuzin.generator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.nkuzin.model.News;
import ru.nkuzin.util.SerializationUtil;

@Slf4j
@Setter
public class TcpNewsSender implements NewsSender {

    private String host;
    private int port;
    private SocketChannel socketChannel;

    public TcpNewsSender(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            log.info("Connected to {}:{}", host, port);
        } catch (IOException e) {
            log.error("Error when connecting", e);
        }
    }

    @Override
    public void send(final News news) {
        try {
            final byte[] bytes = SerializationUtil.serialize(news);
            final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        } catch (IOException e) {
            log.error("Error when sending news", e);
        }

    }
}
