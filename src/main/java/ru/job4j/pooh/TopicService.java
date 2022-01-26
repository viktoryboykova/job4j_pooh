package ru.job4j.pooh;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, CASMap>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String topicName = req.getSourceName();
        topics.putIfAbsent(topicName, new ConcurrentHashMap<>());
        int status = 404;
        String answer = "";
        String requestText = req.getParam();
        String requestType = req.httpRequestType();

        switch (requestType) {
            case "GET" -> {
                if (requestText.startsWith("client") && topics.get(topicName).get(requestText) == null) {
                    topics.get(topicName).put(requestText, new CASMap());
                } else {
                    answer = topics.get(topicName).get(requestText).extract(req);
                    if (!Objects.equals(answer, "")) {
                        status = 200;
                    }
                }
            }
            case "POST" -> {
                for (CASMap casMap : topics.get(topicName).values()) {
                    if (casMap.add(req)) {
                        status = 200;
                    }
                }
            }
            default -> throw new IllegalArgumentException("Неправильно введен тип запроса");
        }
        return new Resp(answer, Integer.toString(status));
    }
}
