package org.apache.http.examples.client;

import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientWithResponseHandler {

    @JsonProperty("getUsersSrvUrl")
    private String getUsersSrvUrl;

    /*
    @JsonPropertyOrder({
            "clientUserIdStr",
            "inviteCode",
            "status",
            "inviteUrl",
            "userId"
    })

    private String clientUserIdStr;
    private String inviteCode;
    private String status;
    private String inviteUrl;
    private Integer userId;



    @JsonProperty("clientUserIdStr")
    public String getClientUserIdStr() {
        return clientUserIdStr;
    }
    @JsonProperty("clientUserIdStr")
    public void setClientUserIdStr(String clientUserIdStr) {
        this.clientUserIdStr = clientUserIdStr;
    }

    @JsonProperty("inviteCode")
    public String getInviteCode() {
        return inviteCode;
    }

    @JsonProperty("inviteCode")
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("inviteUrl")
    public String getInviteUrl() {
        return inviteUrl;
    }

    @JsonProperty("inviteUrl")
    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
*/
    public String ResponseHandlerFunc() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String response;
        JsonParser parser;
        try {
            String s = "getUsersSrvUrl";
            ObjectMapper objectMapper = new ObjectMapper();
            URL url = new URL("https://vpp.itunes.apple.com/WebObjects/MZFinance.woa/wa/VPPServiceConfigSrv");

            ClientWithResponseHandler hndl = objectMapper.readValue(url, ClientWithResponseHandler.class);
            System.out.println(hndl);
            String Jparse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hndl);
            System.out.println(Jparse);
            String str = "";

            JsonFactory factory = new JsonFactory();
            parser = factory.createParser(Jparse);
            while (!parser.isClosed()) {
                JsonToken jsonToken = parser.nextToken();
                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String FieldName = parser.getCurrentName();
                    parser.nextToken();
                    if (s.equals(FieldName)) {
                        str = parser.getValueAsString();
                    }
                }
            }

            ResponseHandler<String> responseHandler = response1 -> {
                int status = response1.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response1.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            //  String sToken = "?sToken=eyJleHBEYXRlIjoiMjAyMC0wOS0xNVQyMzoxNToxNi0wNzAwIiwidG9rZW4iOiI5c3RtRTcvbWFuckxJZExqcU1DeXpjaXM2S1BxZ0p3blVha1JMditVN0swdlF1RTQvWDIwdkNYeXd2U3pwZXpZQk05d3B0M0Z0bVYrSExXYldlcVRWdUhmaWxzL050ajZ1OTgzdktPckFjbkNBOHlvN0VDV09IQ1o3bm1kSDFMK09zVzdJeThUVlZ5MkNWS0JXZGVOZEE9PSIsIm9yZ05hbWUiOiJOb3ZlbGwifQ==";
            //  str=str + sToken + "&clientUserIdStr=100005";

            HttpGet httpget = new HttpGet(str);
            System.out.println("Executing request " + httpget.getRequestLine());
            response = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(response);
            System.out.println("==================\n\n======================");
        } finally {
            httpclient.close();
        }
        return response;
    }
}