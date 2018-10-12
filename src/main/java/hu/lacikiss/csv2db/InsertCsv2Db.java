package hu.lacikiss.csv2db;

import java.text.ParseException;

public class InsertCsv2Db {
	
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		System.out.println("nana: "+Util.isValidArgs(args[0], args[1]));
		System.out.println(HibernateUtils.getCompanyNameList());
		Util.getUrl(args[0], args[1]);
		System.exit(0);
	}
}
