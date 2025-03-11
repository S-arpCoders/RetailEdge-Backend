package com.coders.RetailEdge.services.authentication;

public class JsonUtil {

    public String extractItemFromJson(String keyWord,String json) {
        try {
            String key = "\"" + keyWord +"\":\"";
            int startIndex = json.indexOf(key);

            if (startIndex == -1) {
                return null;
            }

            startIndex += key.length();
            int endIndex = json.indexOf("\"", startIndex);

            if (endIndex == -1) {
                return null;
            }

            return json.substring(startIndex, endIndex);

        } catch (Exception e) {
            return null;
        }
    }
}
