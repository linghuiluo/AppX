package com.github.appx.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class DynamoFactory {

	private static final String ACCESS_KEY = System.getenv("DYNAMO_ACCESS_KEY");
	private static final String SECRET_KEY = System.getenv("DYNAMO_SECRET_KEY");
	private static final String REGION = System.getenv("APP_AWS_REGION");

	private static AmazonDynamoDB DYNAMO_INSTANCE = null;

	private DynamoFactory() {
	}

	public static AmazonDynamoDB getDynamoInstance() {
		if (DYNAMO_INSTANCE == null) {
			AmazonDynamoDBClientBuilder amazonDynamoDBClientBuilder = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(getAwsCredentials()).withRegion(REGION);
			DYNAMO_INSTANCE = amazonDynamoDBClientBuilder.build();
		}
		return DYNAMO_INSTANCE;
	}

	private static AWSCredentialsProvider getAwsCredentials() {	
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
	
	}

	public static void write(final String filePath, final String tableName, final String expression,
			final Map<String, AttributeValue> attrs) throws JsonIOException, IOException {
		final QueryRequest request = new QueryRequest(tableName);
		request.setKeyConditionExpression(expression);
		request.setExpressionAttributeValues(attrs);
		request.setConsistentRead(true);
		final QueryResult result = DYNAMO_INSTANCE.query(request);
		final List<Map<String, AttributeValue>> results = result.getItems();
		JsonArray arr = new JsonArray();
		for (Map<String, AttributeValue> entry : results) {
			JsonObject obj = new JsonObject();
			for (Entry<String, AttributeValue> e : entry.entrySet()) {
				obj.addProperty(e.getKey(), e.getValue().toString());
			}
			arr.add(obj);
		}
		JsonObject o = new JsonObject();
		o.add(tableName, arr);
		new Gson().toJson(o, new FileWriter(filePath));
	}

	public static List<String> selectIDs(QueryResult res, String idAttributeName) {
		final List<Map<String, AttributeValue>> items = res.getItems();
		final List<String> ids = items.stream().map(attMap -> attMap.get(idAttributeName).getS())
				.collect(Collectors.toList());
		return ids;
	}
}
