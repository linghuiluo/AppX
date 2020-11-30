package com.github.appx.bl.services;

import com.google.gson.JsonObject;
import com.github.appx.api.Request;

public interface TagsService {

    JsonObject getTags(Request request);

}
