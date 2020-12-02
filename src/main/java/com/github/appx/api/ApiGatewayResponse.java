package com.github.appx.api;

import static com.github.appx.api.ResponseStatus.OK;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

@SuppressWarnings("unused")
public class ApiGatewayResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    ApiGatewayResponse(int statusCode, JsonObject body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body.toString();
        this.headers = headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static class Builder {

        private static final Logger LOG = Logger.getLogger(ApiGatewayResponse.Builder.class);

        private static final ObjectMapper objectMapper = new ObjectMapper();
        private final Map<String, String> headers = Collections.singletonMap("Content-Type", "application/json");
        private int statusCode = OK.getStatus();
        private JsonObject objectBody;

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setObjectBody(JsonObject objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        public ApiGatewayResponse build() {
            return new ApiGatewayResponse(statusCode, objectBody, headers);
        }
    }

}
