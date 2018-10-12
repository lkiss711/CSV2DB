package hu.lacikiss.csv2db;



import java.util.HashSet;
import java.util.Set;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ad_company",
 uniqueConstraints = { @UniqueConstraint(columnNames = { "company_id" }) })
public class AdCompany {
	
	
	@Id
//	@GenericGenerator(name = "sequence_company_id", strategy = "hu.lacikiss.postgrehib.CompanyIdGen")
//	@GeneratedValue(generator = "sequence_dep_id")  
	@Column(name="company_id")
	private int company_id;
	
	
	
	private String company_name;
	private String company_date_format;
	private boolean company_is_lead_zero_month;
	private String  company_url;
	private Set<AdCompany> companies = new HashSet<AdCompany>(0);

	
	
	public AdCompany() {
	}

	public AdCompany(int company_id, String company_name, String company_date_format) {
		this.company_id = company_id;
		this.company_name = company_name;
		this.company_date_format = company_date_format;
	}

	@Id
	@Column(name = "company_id")
	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	@Column(name = "company_name", length = 50, nullable = false)
	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Column(name = "company_date_format", length = 50, nullable = false)
	public String getCompany_date_format() {
		return company_date_format;
	}

	public void setCompany_date_format(String company_date_format) {
		this.company_date_format = company_date_format;
	}

	public boolean isCompany_is_lead_zero_month() {
		return company_is_lead_zero_month;
	}

	public void setCompany_is_lead_zero_month(boolean company_is_lead_zero_month) {
		this.company_is_lead_zero_month = company_is_lead_zero_month;
	}

	public String getCompany_url() {
		return company_url;
	}

	public void setCompany_url(String company_url) {
		this.company_url = company_url;
	}

	

}