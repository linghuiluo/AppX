package appx.bl.dao;

import appx.api.Request;
import appx.bl.TagsResponse;

public interface TagsDao {

    TagsResponse getTags(Request request);

    void save();
}
