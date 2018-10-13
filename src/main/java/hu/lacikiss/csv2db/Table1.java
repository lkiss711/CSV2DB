package hu.lacikiss.csv2db;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Table1 {

	@Id 
	private int table1_id;
    
    @Temporal(TemporalType.DATE)
    private Date table_date;
    
    private String table_app;

    @Temporal(TemporalType.TIMESTAMP)
    private Date table_timestamp;
    
	public Table1() {
	}

	public Table1(int table1_id, Date table_date, String table_app, Date table_timestamp) {
		super();
		this.table1_id = table1_id;
		this.table_date = table_date;
		this.table_app = table_app;
		this.table_timestamp = table_timestamp;
	}

	public int getTable1_id() {
		return table1_id;
	}

	public void setTable1_id(int table1_id) {
		this.table1_id = table1_id;
	}

	public Date getTable_date() {
		return table_date;
	}

	public void setTable_date(Date table_date) {
		this.table_date = table_date;
	}

	public String getTable_app() {
		return table_app;
	}

	public void setTable_app(String table_app) {
		this.table_app = table_app;
	}

	public Date getTable_timestamp() {
		return table_timestamp;
	}

	public void setTable_timestamp(Date table_timestamp) {
		this.table_timestamp = table_timestamp;
	}

    

    
    
    
    
	
}
