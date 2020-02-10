package org.apache.http.examples.client;

import java.io.IOException;
import java.net.URL;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

        public String fun1() throws Exception {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String res="";
            try {
                String s = "getUsersSrvUrl";

                // Create a custom response handler
                ObjectMapper objectMapper = new ObjectMapper();
                URL url = new URL("https://vpp.itunes.apple.com/WebObjects/MZFinance.woa/wa/VPPServiceConfigSrv");
                ClientWithResponseHandler hndl = objectMapper.readValue(url, ClientWithResponseHandler.class);
                System.out.println(hndl);
                String Jparse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hndl);
                System.out.println(Jparse);
                String str = "";
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(Jparse);
                while (!parser.isClosed()) {
                    JsonToken jsonToken = parser.nextToken();
                    if (jsonToken.FIELD_NAME.equals(jsonToken)) {
                        String FieldName = parser.getCurrentName();
                        System.out.println(FieldName);
                        jsonToken = parser.nextToken();
                        if (s.equals(FieldName)) {
                            str = parser.getValueAsString();
                        }
                    }
                }
                ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                    @Override
                    public String handleResponse(
                            final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }

                };
                HttpGet httpget = new HttpGet(str);
                System.out.println("Executing request " + httpget.getRequestLine());
                String response = httpclient.execute(httpget, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(response);
                res=str;
            } finally {
                httpclient.close();
            }
        return res;
        }
    }

