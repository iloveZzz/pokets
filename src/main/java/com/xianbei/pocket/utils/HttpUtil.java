package com.xianbei.pocket.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static HttpURLConnection createConnection(String wikiSide)
            throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(
                wikiSide).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("content-type", "application/json;charset=utf-8");
        urlConnection.setRequestProperty("X-Event-Key", "repo:push");
        urlConnection.setRequestProperty("User-Agent", "Bitbucket-Webhooks/2.0");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        return urlConnection;
    }
    public static String getResponseBody(HttpURLConnection urlConnection){
        String responseBody="";
        try {
            InputStream is = urlConnection.getInputStream();
            responseBody = new String(getBytesByInputStream(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
    public static void closeConnection(HttpURLConnection urlConnection) {
        urlConnection.disconnect();
    }
    private static byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

}