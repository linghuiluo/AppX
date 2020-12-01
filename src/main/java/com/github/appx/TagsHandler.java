package com.github.appx;

import static com.github.appx.api.ResponseStatus.BAD_REQUEST;
import static com.github.appx.api.ResponseStatus.SERVER_ERROR;

import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.appx.api.ApiGatewayResponse;
import com.github.appx.api.Request;
import com.github.appx.bl.services.InputService;
import com.github.appx.bl.services.TagsService;
import com.github.appx.utils.TagsInjector;
import com.github.appx.utils.exceptions.DatasourceException;
import com.github.appx.utils.exceptions.InvalidInputException;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TagsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Injector INJECTOR = Guice.createInjector(new TagsInjector());

	private static final Logger LOG = Logger.getLogger(TagsHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		try {
			Request request = INJECTOR.getInstance(InputService.class).getInputParams(input);

			JsonObject tags = getTagsService().getTags(request);

			LOG.info(":handleRequest: received=" + input + " result=" + tags);

			return ApiGatewayResponse.builder().setObjectBody(tags).build();
		} catch (InvalidInputException e) {
			LOG.error(":handleRequest: " + e.getMessage(), e);
			return ApiGatewayResponse.builder().setStatusCode(BAD_REQUEST.getStatus())
					.setObjectBody(getErrMsg(e.getMessage())).build();
		} catch (DatasourceException e) {
			LOG.error(":handleRequest: " + e.getMessage(), e);
			return ApiGatewayResponse.builder().setStatusCode(SERVER_ERROR.getStatus())
					.setObjectBody(getErrMsg(e.getMessage())).build();
		} catch (Exception e) {
			String errMsg = "failed to retrieve tags content";
			LOG.error(":handleRequest: " + errMsg, e);
			return ApiGatewayResponse.builder().setStatusCode(SERVER_ERROR.getStatus()).setObjectBody(getErrMsg(errMsg))
					.build();
		}
	}

	TagsService getTagsService() {
		return INJECTOR.getInstance(TagsService.class);
	}

	JsonObject getErrMsg(String errMsg) {
		JsonObject response = new JsonObject();
		response.addProperty("errMsg", errMsg);
		return response;
	}
}
