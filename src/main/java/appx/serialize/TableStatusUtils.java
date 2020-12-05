package appx.serialize;

import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

import appx.db.credentials.ClientUtils;

public class TableStatusUtils {

	public static TableDescription waitForTableActive(String tableName, long waitTimeSeconds)
			throws InterruptedException {
		if (waitTimeSeconds < 0) {
			throw new IllegalArgumentException("Invalid waitTimeSeconds " + waitTimeSeconds);
		}

		long startTimeMs = System.currentTimeMillis();
		long elapsedMs = 0;
		do {
			DescribeTableResult describe = ClientUtils.getDBB().describeTable(new DescribeTableRequest().withTableName(tableName));
			String status = describe.getTable().getTableStatus();
			if (TableStatus.ACTIVE.toString().equals(status)) {
				System.out.println("Active Table:"+ describe.getTable().getTableName());
				return describe.getTable();
			}
			if (TableStatus.DELETING.toString().equals(status)) {
				throw new ResourceInUseException("Table " + tableName + " is " + status
						+ ", and waiting for it to become ACTIVE is not useful.");
			}
			Thread.sleep(10 * 1000);
			elapsedMs = System.currentTimeMillis() - startTimeMs;
		} while (elapsedMs / 1000.0 < waitTimeSeconds);

		throw new ResourceInUseException(
				"Table " + tableName + " did not become ACTIVE after " + waitTimeSeconds + " seconds.");
	}
}
