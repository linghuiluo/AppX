package appx.db;

import appx.serialize.JsonUtils;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		args = new String[] { "HelloTable" };
		final String USAGE = "\n" + "Usage:\n" + "    CreateTable <table>\n\n" + "Where:\n"
				+ "    table - the table to create.\n\n" + "Example:\n" + "    CreateTable HelloTable\n";

		if (args.length < 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		/* Read the name from command args */
		String table_name = args[0];
		//PutItem(new String[] { "HelloTable", "Pau", "Language=ca", "Born=1876" });
		//PutItem(new String[] { "HelloTable", "Anna", "Language=ca", "Born=1776", "City=New York" });
		//PutItem(new String[] { "HelloTable", "Jonas", "Language=ca", "Born=1889", "City=San Diego" });
		// CreateTable(table_name);
		// waitForTableActive(table_name, 20);
		JsonUtils.musicianToJsonFile("bornList.json", "HelloTable", "Anna");
	}
}
