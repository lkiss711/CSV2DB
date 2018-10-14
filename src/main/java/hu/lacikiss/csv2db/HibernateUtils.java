package hu.lacikiss.csv2db;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

 
public class HibernateUtils {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
                    .configure("hibernate.cfg.xml").build();
 
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
 
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
         
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {

    	getSessionFactory().close();

    }
    public static List<AdCompany> getCompanyList(){
 	   SessionFactory factory = HibernateUtils.getSessionFactory();

 	   List<AdCompany> companyList = new ArrayList<AdCompany>();
 	  
       Session session = factory.getCurrentSession();
 
       try {
            
           session.getTransaction().begin();
    
           String sql = "FROM hu.lacikiss.csv2db.AdCompany";
 
           Query<AdCompany> query = session.createQuery(sql);
 
    
           List<AdCompany> companies = query.getResultList();
           
           for (AdCompany com : companies) {
               companyList.add(com);
           }
  
           session.getTransaction().commit();
           session.close();
       } catch (Exception e) {
           e.printStackTrace();
           // Rollback in case of an error occurred.
           session.getTransaction().rollback();
       }
       return(companyList);
    }
    public static List<String> getCompanyNameList(){
  	   SessionFactory factory = HibernateUtils.getSessionFactory();

  	   List<String> companyNameList = new ArrayList<String>();
  	  
        Session session = factory.getCurrentSession();
  
        try {
            session.getTransaction().begin();
            String sql = "FROM hu.lacikiss.csv2db.AdCompany";
  
            Query<AdCompany> query = session.createQuery(sql);
     
            List<AdCompany> companies = query.getResultList();
            
            for (AdCompany com : companies) {
                companyNameList.add(com.getCompany_name());
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return(companyNameList);
     }

	public static String getCompanyDateFormat(String companyName){
	
		String date_format = "";
	
		List<AdCompany> companyList = new ArrayList<AdCompany>();
		companyList = HibernateUtils.getCompanyList();
		outerbreak:
		for (AdCompany com : companyList) {
			if(companyName.equals(com.getCompany_name())){
				date_format = com.getCompany_date_format();
				break outerbreak;
			}
		}
		
		return date_format.toLowerCase();
	}

	public static void insertCsvRecToDB(CSVRecord csvrec){

		  	SessionFactory factory = HibernateUtils.getSessionFactory();
		    Session session = factory.getCurrentSession();
		    Transaction tx = session.beginTransaction();
	
		    String dateString = csvrec.get("Date");
		    System.out.println(dateString);
		    Date now = new Date();
		    Date date;
			try {
				date = Utils.StringToDate(dateString, "dd/mm/yyyy");
			} catch (ParseException e) {
				//e.printStackTrace();
				return;
			}
	
		    Daily_Report dr = new Daily_Report();
		    
		    dr.setReport_id(now.getTime()+csvrec.getRecordNumber());
		    dr.setReport_app(csvrec.get("App"));
		    dr.setReport_date(date);
		    
		    int imp = Integer.parseInt(csvrec.get("Impressions"));
		    
		    dr.setReport_impressions(imp);
		    dr.setReport_platform(csvrec.get("Platform"));
	
		    int req = Integer.parseInt(csvrec.get("Requests"));
		    
		    dr.setReport_requests(req);
		    dr.setReport_revenue(csvrec.get("Revenue"));
		    dr.setReport_timestamp(now);
		    
		    
		    session.save(dr);
		    tx.commit();
		    session.close();
    	
	}
}