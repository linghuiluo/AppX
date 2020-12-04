package appx.bl.dao.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import appx.api.Request;
import appx.bl.TagsResponse;
import appx.bl.dao.TagsDao;
import appx.db.DynamoFactory;
import appx.utils.exceptions.DatasourceException;
import com.google.gson.JsonIOException;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

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
            GetItemSpec spec = new GetItemSpec().withPrimaryKey(PRIMARY_KEY, request.getLanguage())
                                                .withAttributesToGet(brandKey, TAGS_KEY);
            Item outcome = table.getItem(spec);
            String brands = outcome.getJSONPretty(brandKey);
            String tags = outcome.getJSON(TAGS_KEY);
            return new TagsResponse(brands, tags);
        } catch (Exception e) {
            throw new DatasourceException("Failed to get TAGS from DB");
        }
    }

    @Override
    public void save() {
        Map<String, AttributeValue> attrs = new HashMap<>();
        AttributeValue a1 = new AttributeValue();
        a1.setS("java");
        attrs.put(":l1", a1);
        try {
            DynamoFactory.write("tags.json", TABLE_NAME, "#language = :l1", attrs);
        } catch (JsonIOException e) {
            throw new DatasourceException(e.getMessage(), e);
        } catch (IOException e) {
            throw new DatasourceException(e.getMessage(), e);
        }

    }

}
