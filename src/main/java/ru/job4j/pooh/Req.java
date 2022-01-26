package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static void main(String[] args) {
        String ls = System.lineSeparator();
        of("POST /queue/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls);
    }

    public static Req of(String content) {
        Req req = null;
        String[] lines = content.split(System.lineSeparator());
        String[] firstLine = lines[0].split("/");
        String httpRequestType = firstLine[0].trim();
        String poohMode = firstLine[1];
        String sourceName = firstLine[2].replaceAll(" HTTP", "");
        String param = "";
        if ("POST".equals(httpRequestType)) {
            param = lines[lines.length - 1];
            req = new Req(httpRequestType, poohMode, sourceName, param);
        } else if ("GET".equals(httpRequestType)) {
            if (firstLine.length == 5) {
                param = firstLine[3].replaceAll(" HTTP", "");
            }
            req = new Req(httpRequestType, poohMode, sourceName, param);
        }
        return req;
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}