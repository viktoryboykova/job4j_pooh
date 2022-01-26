package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CASMap {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    public boolean add(Req req) {
        return queue.computeIfAbsent(req.getSourceName(), key -> new ConcurrentLinkedQueue<>()).add(req.getParam());
    }

    public String extract(Req req) {
        return queue.get(req.getSourceName()).poll();
    }
}
