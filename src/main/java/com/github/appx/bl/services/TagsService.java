package com.github.appx.bl.services;

import com.github.appx.api.Request;
import com.google.gson.JsonObject;

public interface TagsService {

    JsonObject getTags(Request request);

}
