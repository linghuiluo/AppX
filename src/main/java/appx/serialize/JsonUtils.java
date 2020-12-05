package appx.serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import appx.db.credentials.ClientUtils;
import appx.db.data.Musician;

public class JsonUtils {

	public static void musicianToJsonFile(String fileName, String tableName, String name)   {
		int waitSeconds = 20;
		try {
	      	FileOutputStream fileOut = new FileOutputStream(fileName);
			TableDescription table = TableStatusUtils.waitForTableActive(tableName, 20);
			   String partition_alias = "#a";
			   String partition_key_name = "Name";
		      //set up an alias for the partition key name in case it's a reserved word
		        HashMap<String,String> attrNameAlias =
		                new HashMap<String,String>();
		        attrNameAlias.put(partition_alias, partition_key_name);

		        //set up mapping of the partition name with the value
		        HashMap<String, AttributeValue> attrValues =
		                new HashMap<String,AttributeValue>();
		        attrValues.put(":"+partition_key_name, new AttributeValue().withS(name));
			QueryRequest queryReq = new QueryRequest()
	        		.withTableName(tableName)
	        		.withKeyConditionExpression(partition_alias + " = :" + partition_key_name)
	        		.withExpressionAttributeNames(attrNameAlias)
	        		.withExpressionAttributeValues(attrValues);
			System.out.println(queryReq.toString());
			  try {
		        	QueryResult response = ClientUtils.getDBB().query(queryReq);		        
		        	Musician m =Converter.convert(response, "Born");
		        	write(fileOut, m);
		        	
		        	System.out.println(response.getCount());
		        } catch (AmazonDynamoDBException e) {
		            System.err.println(e.getErrorMessage());
		            System.exit(1);
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (InterruptedException e) {
			throw new RuntimeException("table not active");
		} catch( FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write(OutputStream out, Musician m) throws IOException
	{
		String prettyString = new ObjectMapper().convertValue(m, JsonNode.class).toPrettyString();
		OutputStreamWriter osw = new OutputStreamWriter(out);
		osw.append(prettyString);
		osw.flush();
		osw.close();
	}
}
