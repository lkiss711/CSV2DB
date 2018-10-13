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
 
    // Hibernate 5:
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the ServiceRegistry from hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()//
                    .configure("hibernate.cfg.xml").build();
 
            // Create a metadata sources using the specified service registry.
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
        // Close caches and connection pools
        getSessionFactory().close();
    }
    public static List<AdCompany> getCompanyList(){
 	   SessionFactory factory = HibernateUtils.getSessionFactory();

 	   List<AdCompany> companyList = new ArrayList<AdCompany>();
 	  
       Session session = factory.getCurrentSession();
 
       try {
            
           // All the action with DB via Hibernate
           // must be located in one transaction.
           // Start Transaction.            
           session.getTransaction().begin();
 
    
            
           // Create an HQL statement, query the object.
           // Equivalent to the SQL statement:
           // Select e.* from EMPLOYEE e order by e.EMP_NAME, e.EMP_NO
           String sql = "FROM hu.lacikiss.csv2db.AdCompany";
 
   
           // Create Query object.
           Query<AdCompany> query = session.createQuery(sql);
 
    
           // Execute query.
           List<AdCompany> companies = query.getResultList();
           
           for (AdCompany com : companies) {
               companyList.add(com);
           }
  
           // Commit data.
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
             
            // All the action with DB via Hibernate
            // must be located in one transaction.
            // Start Transaction.            
            session.getTransaction().begin();
  
     
             
            // Create an HQL statement, query the object.
            // Equivalent to the SQL statement:
            // Select e.* from EMPLOYEE e order by e.EMP_NAME, e.EMP_NO
            String sql = "FROM hu.lacikiss.csv2db.AdCompany";
  
    
            // Create Query object.
            Query<AdCompany> query = session.createQuery(sql);
  
     
            // Execute query.
            List<AdCompany> companies = query.getResultList();
            
            for (AdCompany com : companies) {
                companyNameList.add(com.getCompany_name());
            }
   
            // Commit data.
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback in case of an error occurred.
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
				// TODO Auto-generated catch block
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