package com.oraclemdc.foodtruckfinder.dto;

/*

HTTPResponse DTO simplifies manipulation of HTTP response data (payload,code)
by not having to use structure-specific APIs response(i.e., for JSON, XML, etc.)
 */
public class HttpResponse {

    private int responseCode;
    private String payload;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}

