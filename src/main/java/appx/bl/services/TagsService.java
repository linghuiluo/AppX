package appx.bl.services;

import appx.api.Request;
import com.google.gson.JsonObject;

public interface TagsService {

    JsonObject getTags(Request request);

}
