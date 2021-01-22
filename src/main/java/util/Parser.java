package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URL;


public class Parser {

    private static final String ANTECEDENT_SERVICE_NAME = "telegram_input_service";

    public static String parseInputForFileID(String jsonInput) throws JsonProcessingException {
        return getAntecedentServiceDataNode(jsonInput).get("message").get("document").get("file_id").asText();
    }

    public static String parseInputForBotToken(String jsonInput) throws JsonProcessingException {
        return getAntecedentServiceDataNode(jsonInput).get("botdata").get("token").asText();
    }

    public static String getTelegramResponseFromURLAndParseImagePath(URL jsonURL) throws IOException {
        return JsonHandler.getJsonFromURL(jsonURL).get("result").get("file_path").asText();
    }

    private static JsonNode getAntecedentServiceDataNode(String jsonInput) throws JsonProcessingException {
        return JsonHandler.jsonStringToNode(jsonInput).get(ANTECEDENT_SERVICE_NAME);
    }

}
