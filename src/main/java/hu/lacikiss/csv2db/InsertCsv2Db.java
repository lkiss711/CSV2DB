package hu.lacikiss.csv2db;

import java.io.IOException;
import java.text.ParseException;

public class InsertCsv2Db {
	
	
	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub

		System.out.println(Utils.getUrl(args[0], args[1]));
		String url = Utils.getUrl(args[0], args[1]);
		Utils.readCsv(url);
		System.exit(0);
	}
}
