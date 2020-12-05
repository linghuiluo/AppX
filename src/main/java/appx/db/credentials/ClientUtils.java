package appx.db.credentials;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class ClientUtils {

	public static AmazonDynamoDB getDBB(){
		return AmazonDynamoDBClientBuilder.standard().withRegion("us-west-2").build();
	}
}
