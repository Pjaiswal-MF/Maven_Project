package org.apache.http.examples.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.*;
import org.apache.http.examples.client.ClientWithResponseHandler;

public class parser {

    private static ObjectMapper objMapper = new ObjectMapper();

    public static String getRegisterVPPUserUrl(byte[] jsonData) throws IOException {
        JsonNode rootnode = objMapper.readTree(jsonData);
        JsonNode tempnode = rootnode.path("registerUserSrvUrl");
        return tempnode.asText();

    }

    public static ClientWithResponseHandler getUser(byte[] jsonData) throws IOException {
        JsonNode rootnode = objMapper.readTree(jsonData);
        JsonNode tempnode = rootnode.path("user");
        ClientWithResponseHandler registerUser = objMapper.readValue(tempnode.toString(), ClientWithResponseHandler.class);
        return registerUser;

    }

}