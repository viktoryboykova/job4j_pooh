package ru.job4j.pooh;

import java.util.Objects;

public class QueueService implements Service {

    private final CASMap queue = new CASMap();

    @Override
    public Resp process(Req req) {
        int status = 404;
        String answer = "";
        String requestType = req.httpRequestType();

        switch (requestType) {
            case "POST" -> {
                if (queue.add(req)) {
                    status = 200;
                }
            }
            case "GET" -> {
                answer = queue.extract(req);
                if (!Objects.equals(answer, "")) {
                    status = 200;
                }
            }
            default -> throw new IllegalArgumentException("Неправильно введен тип запроса");
        }
        return new Resp(answer, Integer.toString(status));
    }
}