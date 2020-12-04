package appx.bl.dao;

import appx.api.Request;
import appx.bl.TagsResponse;
import appx.db.Data;

public interface TagsDao {

    static Data create(String id) {
        return Data.builder().id(id).build();
    }

    TagsResponse getTags(Request request);

    void save();
}
