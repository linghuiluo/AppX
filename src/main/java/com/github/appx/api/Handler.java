package com.github.appx.api;

import java.util.Date;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.google.gson.JsonObject;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {


	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		JsonObject responseBody = new JsonObject();
		responseBody.addProperty("message", "Hello, the current time is " + new Date());
		responseBody.addProperty("func", input.get("func").toString());
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.build();
	}
}
