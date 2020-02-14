package org.apache.http.examples.client;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class ClientWithResponseHandlerTest {
    public static String expected;
    public static StringBuffer sb = new StringBuffer();
    JsonParser parser = null;

    public static void main() throws IOException {

        ClientWithResponseHandlerTest main = new ClientWithResponseHandlerTest();
        File file = main.getFileFromResources("res.json");
        printFile(file);
    }

    public File getFileFromResources(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(filename);
        if (resource != null) {
            return new File(resource.getFile());
        } else {
            throw new IllegalArgumentException("file is not found!");
        }
    }

    public static void printFile(File file) throws IOException {
        if (file == null) return;

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
        }
    }

    public static String getExpected() {
        try {
            main();
        } catch (IOException e) {
            e.printStackTrace();
        }
        expected = sb.toString();
        return expected;
    }

    @Test
    public void JunitTest() throws Exception {
        ClientWithResponseHandler newClient = new ClientWithResponseHandler();
        String result = String.valueOf(newClient.ResponseHandlerFunc());
        assertEquals(getExpected(), result);
    }
}
