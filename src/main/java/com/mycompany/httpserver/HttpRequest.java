package com.mycompany.httpserver;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    URI requestUri;
    HttpRequest(URI reqUri) {
        requestUri = reqUri;
    }
    
    public String getValue(String paramName){
        String query = requestUri.getQuery();
        if (query == null){
            return "";
        }
        String[] queryParams = query.split("&");
        Map<String, String> queryParam = new HashMap<>();
        for (String param : queryParams){
            String[] nameValue = param.split("=");
            if (nameValue.length == 1){
                queryParam.put(nameValue[0], "");
            }
            else {
                queryParam.put(nameValue[0], nameValue[1]);
            }
        }
        return queryParam.get(paramName) != null ? queryParam.get(paramName) : "";
    }
}
