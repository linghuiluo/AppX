package appx.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.services.lambda.runtime.Client;

import appx.db.credentials.ClientUtils;

/**
 * Create a DynamoDB table.
 *
 * Takes the name of the table to create. The table will contain a single
 * primary key, "Name".
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class CreateTable {

	protected static void createTable(String table_name) {
		System.out.format("Creating table \"%s\" with a simple primary key: \"Name\".\n", table_name);

		CreateTableRequest request = new CreateTableRequest()
				.withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
				.withTableName(table_name);

		try {
			CreateTableResult result = ClientUtils.getDBB().createTable(request);
			System.out.println(result.getTableDescription().getTableName());
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println("Done!");
	}
}
