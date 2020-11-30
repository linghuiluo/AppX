package com.github.appx.bl.dao;

import com.github.appx.api.Request;
import com.github.appx.bl.TagsResponse;

public interface TagsDao {

    TagsResponse getTags(Request request);

}
