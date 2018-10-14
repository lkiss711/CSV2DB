package hu.lacikiss.csv2db;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public class InsertCsv2Db {
	
	
	public static void main(String[] args) throws ParseException, IOException{
		
		String url = Utils.getUrl(args[0], args[1]);
		Utils.readCsv(url);
	    List<CSVRecord> csvRecordList = Utils.readCsv(url);
	    
	    int rec_counter = 0;
	    
	    for(CSVRecord csvrec : csvRecordList){

	    	if(Utils.isThisDateValid(csvrec.get("Date"), "dd/mm/yyyy") == true) {	
	    		HibernateUtils.insertCsvRecToDB(csvrec);
	    		rec_counter++;
	    	}

	    }
	    System.out.println("Row To DB: "+rec_counter);
	    System.exit(0);

	}

}

