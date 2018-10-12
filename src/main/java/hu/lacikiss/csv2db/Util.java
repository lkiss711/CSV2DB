package hu.lacikiss.csv2db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.DateValidator;

public class Util {

	public static String getCompanyDateFormat(String companyName){

		String date_format = "00";

		List<AdCompany> companyList = new ArrayList<AdCompany>();
		companyList = HibernateUtils.getCompanyList();
		outerbreak:
		for (AdCompany com : companyList) {
			if(companyName.equals(com.getCompany_name())){
				date_format = com.getCompany_date_format();
				break outerbreak;
			}
		}
		
		return date_format;
	}
	public static boolean isValidArgs(String args0, String args1){
		boolean isValid = false;
		
		List<AdCompany> companyList = new ArrayList<AdCompany>();
		companyList = HibernateUtils.getCompanyList();
		
		List<String> companyNameList = new ArrayList<String>();
		companyNameList = HibernateUtils.getCompanyNameList();

		if(companyNameList.contains(args0)){
		
			DateValidator validator = DateValidator.getInstance();
			
			String date_format = Util.getCompanyDateFormat(args0);
			if(validator.isValid(args1,date_format.toLowerCase()) == true){
				isValid = true;
			}
		}		
		return(isValid);
	}
	public static String getUrl(String companyName,String dateString) throws ParseException{
		String url = "";
		String url1 = "";
		String url2 = "";
		String date_format = "";
		boolean is_lead_zero = false;
		
		List<AdCompany> companyList = new ArrayList<AdCompany>();
		companyList = HibernateUtils.getCompanyList();

		outerbreak:
		for (AdCompany com : companyList) {
			if(companyName.equals(com.getCompany_name())){
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

		if(monthString.length() == 1){
			monthString = "0"+monthString;
		}

		if(dayString.length() == 1){
			dayString = "0"+dayString;
		}
		
		
		
		System.out.println(yearString+"-"+monthString+"-"+dayString);
		
		int year_pos = date_format.indexOf('Y');
		int month_pos = date_format.indexOf('M');
		int day_pos = date_format.indexOf('D');

		if((year_pos < month_pos) && (year_pos < day_pos)){
			if(day_pos < month_pos){
				System.out.println(yearString+"-"+dayString+"-"+monthString);
			}
			else{
				System.out.println(yearString+"-"+monthString+"-"+dayString);
			}
		}

		if((month_pos < year_pos) && (month_pos < day_pos)){
			if(day_pos < year_pos){
				System.out.println(monthString+"-"+dayString+"-"+yearString);
			}
			else{
				System.out.println(monthString+"-"+monthString+"-"+dayString);
			}
		}
		
		if((day_pos < year_pos) && (day_pos < day_pos)){
			if(month_pos < year_pos){
				System.out.println(dayString+"-"+monthString+"-"+yearString);
			}
			else{
				System.out.println(dayString+"-"+yearString+"-"+monthString);
			}
		}

		
		return(url);
	}
}
