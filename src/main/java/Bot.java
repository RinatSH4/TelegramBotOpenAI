import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private final String TOKEN = "6326380987:AAEE1WmU91Jo3pPbFXrLhotAygEbzMO6jqY";
    private final String BOT_NAME = "RSHGTPBOT";
    private OpenAIAPI openAIAPI = new OpenAIAPI();


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            //Извлекаем из объекта сообщение пользователя
            Message inMess = update.getMessage();
            //Достаем из inMess id чата пользователя
            String chatId = inMess.getChatId().toString();

            //Получаем текст сообщения пользователя, отправляем
            // в написанный нами обработчик
            String responce = parseMessage(inMess.getText());

            //Создаем объект класса SendMessage
            // - наш будущий ответ пользователю
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(responce);

            //отправка в чат
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String parseMessage(String msg) {
        System.out.println(msg);
        if(msg.equals("/start"))
            return "Бот запущен!";
        else
            return openAIAPI.request(msg);
    }
    @Override
    public String getBotUsername () {
        return BOT_NAME;
    }

    @Override
    public String getBotToken () {
        return TOKEN;
    }
}
