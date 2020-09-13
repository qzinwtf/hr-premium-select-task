package ru.nkuzin.generator;

import ru.nkuzin.model.News;

public interface NewsSender {
    void start();

    void send(News news);
}
