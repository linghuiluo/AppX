package com.github.appx.utils;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Data
@AllArgsConstructor
@Builder
public class Result {

	private String tableName;

	private String accountId;

	private Integer maxResults;

	private Map<String, AttributeValue> exclusiveStartKey;
}