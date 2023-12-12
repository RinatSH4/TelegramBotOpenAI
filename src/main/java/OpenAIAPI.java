import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;


public class OpenAIAPI {
    private final String URL = "https://api.air.fail/public/text/chatgpt";
    private final String API_KEY = "YOU_TOKEN"; // ваш токен

    String request(String text) {
        String body = "{\"content\": \"" + text + "\", \"info\": {\"temperature\": 0.8}}";
        try {
            Document doc = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .requestBody(body)
                    .header("Authorization", API_KEY)
                    .header("Content-Type", "application/json")
                    .post();

            String html = doc.getElementsByClass("prettyprint").text();
            return parseDoc(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Ошибка запроса";
    }

    private String parseDoc(String html) {
        String json = html.substring(html.indexOf("[") + 1, html.indexOf("]"));
        JSONObject jsonObject = new JSONObject(json);
        String content = jsonObject.getString("content");
        return content;
    }

    void isValid() {
        try {
            Document doc = Jsoup.connect("https://api.air.fail/public/me")
                    .header("Authorization", API_KEY).get();
            System.out.println(doc.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
