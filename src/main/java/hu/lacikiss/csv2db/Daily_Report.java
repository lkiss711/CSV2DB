package hu.lacikiss.csv2db;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Daily_Report {

	@Id
	private long report_id;

    @Temporal(TemporalType.DATE)
	private Date report_date;
	private String report_app;
	private String report_platform;
	private int report_requests;
	private int report_impressions;
	private String report_revenue;

	@Temporal(TemporalType.TIMESTAMP)
	private Date report_timestamp;

	public long getReport_id() {
		return report_id;
	}

	public void setReport_id(long report_id) {
		this.report_id = report_id;
	}
	public Date getReport_date() {
		return report_date;
	}

	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}

	public String getReport_app() {
		return report_app;
	}

	public void setReport_app(String report_app) {
		this.report_app = report_app;
	}

	public String getReport_platform() {
		return report_platform;
	}

	public void setReport_platform(String report_platform) {
		this.report_platform = report_platform;
	}

	public int getReport_requests() {
		return report_requests;
	}

	public void setReport_requests(int report_requests) {
		this.report_requests = report_requests;
	}

	public int getReport_impressions() {
		return report_impressions;
	}

	public void setReport_impressions(int report_impressions) {
		this.report_impressions = report_impressions;
	}

	public String getReport_revenue() {
		return report_revenue;
	}

	public void setReport_revenue(String report_revenue) {
		this.report_revenue = report_revenue;
	}

	public Date getReport_timestamp() {
		return report_timestamp;
	}

	public void setReport_timestamp(Date report_timestamp) {
		this.report_timestamp = report_timestamp;
	}	
}
