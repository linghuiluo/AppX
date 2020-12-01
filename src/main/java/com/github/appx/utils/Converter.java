package com.github.appx.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.appx.utils.Result.ResultBuilder;
import com.google.common.collect.ImmutableList;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

public class Converter {

	public Result convert(final QueryResponse ddbResponse, final String idAttributeName) {
		final List<Map<String, AttributeValue>> items = ddbResponse.hasItems() ? ddbResponse.items()
				: ImmutableList.of();
		final List<String> ids = items.stream().map(attributeValueMap -> attributeValueMap.get(idAttributeName).s())
				.collect(Collectors.toList());

		final ResultBuilder resultBuilder = Result.builder();
		resultBuilder.accountId(ids.get(0));
		if (ddbResponse.hasLastEvaluatedKey()) {
			resultBuilder.exclusiveStartKey(ddbResponse.lastEvaluatedKey());
		}
		return resultBuilder.build();
	}
}
