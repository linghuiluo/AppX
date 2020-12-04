package appx.bl.dao;

import appx.api.Request;
import appx.bl.TagsResponse;
import appx.db.Data;

public interface TagsDao {

     void useData(Data data);

    TagsResponse getTags(Request request);

    void save();
}
