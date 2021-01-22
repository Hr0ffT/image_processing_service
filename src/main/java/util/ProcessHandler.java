package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.NotFoundException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import rabbit.Rabbit;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class ProcessHandler {

    private static final Logger log = Logger.getLogger(ProcessHandler.class);

    private static final String START_MESSAGE = " ----- fetcher_service started -----";

    private static Rabbit rabbit;

    public static void initProgram() throws IOException, TimeoutException {
        System.out.println(START_MESSAGE);
        rabbit = new Rabbit();

    }

    public static void messageReceived(String receivedJson) throws JSONException {

        try {

            String imageURL = getImageURL(receivedJson);
            String barcode = readBarcodeFromImage(imageURL);
            String jsonOutput = prepareOutputJson(receivedJson, barcode);

            sendOutputJson(jsonOutput);


        } catch (IOException | NotFoundException e) {
            log.error(e);
        }

    }

    private static void sendOutputJson(String outputJson) {
        rabbit.send(outputJson);
    }

    private boolean isCorrectJson() {
        //TODO         check input is correct
        return false;
    }

    private static String getImageURL(String receivedJson) throws IOException {


        try {
            String fileID = Parser.parseInputForFileID(receivedJson);
            String botToken = Parser.parseInputForBotToken(receivedJson);
            String apiTelegramURL = "https://api.telegram.org";

            URL telegramGetFileURL = new URL(String.format(apiTelegramURL + "/bot%s/getFile?file_id=%s", botToken, fileID));

            String imagePath = Parser.getTelegramResponseFromURLAndParseImagePath(telegramGetFileURL);

            return String.format(apiTelegramURL + "/file/bot%s/%s", botToken, imagePath);

        } catch (JsonProcessingException | NullPointerException e) {
            return "-2";
        }

    }

    private static String readBarcodeFromImage(String imageURL) throws IOException, NotFoundException {

        if (imageURL.equals("-2")) {
            return "-2";
        } else {
            try {
                return BarcodeReader.readBarcodeFromRemoteImage(imageURL);
            } catch (NotFoundException e) {
                return "-1";
            }
        }

    }

    private static String prepareOutputJson(String receivedJson, String barcode) throws JsonProcessingException, JSONException {
        String jsonBarcodeObject = JsonHandler.serializeToJson(new BarcodeObject(barcode));
        return JsonHandler.putDataInJson(receivedJson, jsonBarcodeObject);

    }
}
