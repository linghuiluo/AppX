package com.github.appx.utils;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Result {

    private String tableName;

    private String accountId;

    private Integer maxResults;
 
    private Map<String, AttributeValue> exclusiveStartKey;
}