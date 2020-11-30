package com.github.appx.bl.dao.impl;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.github.appx.api.Request;
import com.github.appx.bl.TagsResponse;
import com.github.appx.bl.dao.TagsDao;
import com.github.appx.utils.DynamoFactory;
import com.github.appx.utils.exceptions.DatasourceException;

public class TagsDaoImpl implements TagsDao {

    private static final String TABLE_NAME = "tags";
    private static final String PRIMARY_KEY = "language";
    private static final String TAGS_KEY = "common_tags";

    @Override
    public TagsResponse getTags(Request request) {
        try {
            DynamoDB dynamoDB = new DynamoDB(DynamoFactory.getDynamoInstance());
            Table table = dynamoDB.getTable(TABLE_NAME);
            String brandKey = "brand_" + request.getBrandId();
            GetItemSpec spec = new GetItemSpec().
                    withPrimaryKey(PRIMARY_KEY, request.getLanguage()).
                    withAttributesToGet(brandKey, TAGS_KEY);
            Item outcome = table.getItem(spec);

            String brands = outcome.getJSONPretty(brandKey);
            String tags = outcome.getJSON(TAGS_KEY);
            return new TagsResponse(brands, tags);
        } catch (Exception e) {
            throw new DatasourceException("failed to get TAGS from DB", e);
        }
    }

}
