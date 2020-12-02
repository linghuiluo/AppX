package com.github.appx.bl.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.github.appx.api.Request;
import com.github.appx.bl.TagsResponse;
import com.github.appx.bl.dao.TagsDao;
import com.github.appx.bl.services.TagsService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;

public class TagsServiceImpl implements TagsService {

    private static final Logger LOG = Logger.getLogger(TagsServiceImpl.class);

    private TagsDao dao;

    @Inject
    void setDao(TagsDao dao) {
        this.dao = dao;
    }

    @Override
    public JsonObject getTags(Request request) {
        TagsResponse tagsResponse = dao.getTags(request);
        LOG.debug(":getTags: retrieved from DB " + tagsResponse);

        if (tagsResponse.getBrandTags() == null) {
            return getDefaultTags(tagsResponse);
        }

        return mergeTags(tagsResponse);
    }

    private JsonObject mergeTags(TagsResponse tagsResponse) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject defaultTags = parser.parse(tagsResponse.getDefaultTags()).getAsJsonObject();
            JsonObject brandTags = parser.parse(tagsResponse.getBrandTags()).getAsJsonObject();

            brandTags.entrySet().forEach(entry -> defaultTags.add(entry.getKey(), entry.getValue()));
            writeToFile(brandTags);
            return defaultTags;
        } catch (JsonSyntaxException e) {
            LOG.error(":mergeTags: failed to merge common tags with brand tags, using only common. JsonSyntaxException="
                      + e.getMessage());
            return getDefaultTags(tagsResponse);
        } catch (IOException e) {
            LOG.error(":mergeTags: failed to log brandTags" + e.getMessage());
            return getDefaultTags(tagsResponse);
        }
    }

    private void writeToFile(JsonObject brandTags) throws IOException {
        File temp = File.createTempFile("tags", ".log");
        OutputStream out = new FileOutputStream(temp);
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        writer.write(brandTags.toString());
        writer.close();
    }

    private JsonObject getDefaultTags(TagsResponse tagsResponse) {
        return new JsonParser().parse(tagsResponse.getDefaultTags()).getAsJsonObject();
    }

}
