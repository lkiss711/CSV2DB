package hu.lacikiss.csv2db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {

	public static boolean isValidArgs(String args0, String args1) {
		boolean isValid = false;

		List<String> companyNameList = new ArrayList<String>();
		companyNameList = HibernateUtils.getCompanyNameList();

		if (((companyNameList.contains(args0) == true)) && (isThisDateValid(args1, "yyyy-mm-dd") == true)) {
			isValid = true;
		}
		return (isValid);
	}

	public static String getUrl(String companyName, String dateString) throws ParseException {
		String url = "";
		String url1 = "";
		String url2 = "";
		String date_format = "";
		boolean is_lead_zero = false;

		List<AdCompany> companyList = new ArrayList<AdCompany>();
		companyList = HibernateUtils.getCompanyList();

		outerbreak: for (AdCompany com : companyList) {
			if (companyName.equals(com.getCompany_name())) {
				url1 = com.getCompany_url();
				date_format = com.getCompany_date_format();
				is_lead_zero = com.isCompany_is_lead_zero_month();
				break outerbreak;
			}
		}

		LocalDate localDate = LocalDate.parse(dateString);
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();

		String yearString = Integer.toString(year);
		String monthString = Integer.toString(month);
		String dayString = Integer.toString(day);

		url2 = Utils.getUlrDateString(yearString, monthString, dayString, date_format, is_lead_zero);
		url = url1 + url2 + ".csv";

		return (url);
	}

	public static boolean isThisDateValid(String dateToValidate, String dateFormat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);

		} catch (ParseException e) {

			//e.printStackTrace();
			System.out.println("Not valid date in csv record: "+dateToValidate);
			return false;
		}

		return true;
	}

	public static String getUlrDateString(String yearString, String monthString, String dayString, String date_format,
			boolean lead_zero_in_month) {
		String dateString = "";

		char separator = Utils.getDateSeparator(date_format.toLowerCase());

		if ((monthString.length() == 1) && (lead_zero_in_month == false)) {
			monthString = "0" + monthString;
		}

		if (dayString.length() == 1) {
			dayString = "0" + dayString;
		}

		int year_pos = date_format.indexOf('Y');
		int month_pos = date_format.indexOf('M');
		int day_pos = date_format.indexOf('D');

		if ((year_pos < month_pos) && (year_pos < day_pos)) {
			if (day_pos < month_pos) {
				dateString = (yearString + separator + dayString + separator + monthString);
			} else {
				dateString = (yearString + separator + monthString + separator + dayString);
			}
		}

		if ((month_pos < year_pos) && (month_pos < day_pos)) {
			if (day_pos < year_pos) {
				dateString = (monthString + separator + dayString + separator + yearString);
			} else {
				dateString = (monthString + separator + monthString + separator + dayString);
			}
		}

		if ((day_pos < year_pos) && (day_pos < month_pos)) {
			if (month_pos < year_pos) {
				dateString = (dayString + separator + monthString + separator + yearString);
			} else {
				dateString = (dayString + separator + yearString + separator + monthString);
			}
		}
		return (dateString);
	}

	public static List<CSVRecord> readCsv(String urlString) throws IOException

	{
		URL url = new URL(urlString);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		CSVParser csvParser = new CSVParser(reader,
				CSVFormat.DEFAULT.withHeader("Date", "App", "Platform", "Requests", "Impressions", "Revenue")
						.withIgnoreHeaderCase().withTrim());
		List<CSVRecord> csvRecordsList = csvParser.getRecords();
		csvRecordsList.remove(0);
		csvParser.close();
		return (csvRecordsList);
	}
	public static Date StringToDate(String dateInString, String dateformat) throws ParseException{
	    DateFormat formatter = new SimpleDateFormat(dateformat);
	    Date date = formatter.parse(dateInString);
		return date;
	}

	public static char getDateSeparator(String date_format) {
		char separator = ' ';
		char Char;
		
			char[] data_array = date_format.toCharArray();
			
			int i = 0;
			boolean isGet = false;
			
			while((i < data_array.length) && (isGet == false)){
				Char = data_array[i];
				if(Character.toString(Char).matches("[^a-z]") == true){
					isGet = true;
					separator = Char;
				}
				i++;
			}
			
		
		return(separator);
	}
}
