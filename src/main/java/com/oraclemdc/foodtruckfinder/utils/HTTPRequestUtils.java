package com.oraclemdc.foodtruckfinder.utils;


import com.oraclemdc.foodtruckfinder.dto.HttpResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HTTPRequestUtils {


    /**
     * @param params Map of key,value query parameters
     * @return String of the required format
     * @throws UnsupportedEncodingException
     */
    public static String buildQueryFromParameters(Map<String, String> params)
            throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();

        if (params.size() > 0)
            result.append("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * This function builds and executes the API request,
     * maps the response to a HTTPResponse DTO, performs exception handling.
     *
     * @param baseUrl         the base url of the service
     * @param queryParameters Query parameters Map
     * @param headers         Map containing the desired headers
     * @param method          HTTP Method
     * @param body            Body of the request
     * @param timeout         Timeout of the request
     * @return HTTPResponse wrapper
     * @throws IOException
     */
    public static HttpResponse request(String baseUrl, Map<String, String> queryParameters,
                                       Map<String, String> headers, String method, String body,
                                       int timeout) throws IOException {

        baseUrl += buildQueryFromParameters(queryParameters);
        URL url = new URL(baseUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        HttpResponse httpResponse = new HttpResponse();

        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod(method);

        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.addRequestProperty(header.getKey(), header.getValue());
            }
        }

        if (body != null) {
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();
        }

        int responseCode;

        try {
            responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder buffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }
            in.close();

            httpResponse.setPayload(buffer.toString());
            httpResponse.setResponseCode(responseCode);


        } catch (IOException e) {
            httpResponse.setPayload((e.getMessage() != null ? e.getMessage() : e.getLocalizedMessage()));
            httpResponse.setResponseCode(502);

        } finally {
            connection.disconnect();
        }
        return httpResponse;
    }

}
