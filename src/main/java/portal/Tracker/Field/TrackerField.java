package portal.Tracker.Field;

import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import lombok.Data;
import portal.Tracker.Tracker;

@Data
@Entity
@Table(name = "portal_tracker_field")
public class TrackerField {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min=3, message="Name must be at least 3 characters long")
	private String name;
	
	@NotNull
	@Size(min=3, message="Label must be at least 3 characters long")
	private String label;
	
	private String fieldType;
	private String fieldWidget;
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "tracker_id" )
	private Tracker tracker; 
	
	@Transient
	public String[] typeOptions = {"String","Text","Integer","Number","Date","DateTime","Checkbox"};
	
	@Transient
	public String[] widgetOptions = {"Default"};
	
	public String display(SqlRowSet datas) {
		if(fieldType.equals("String") || fieldType.equals("Text")) {
			return datas.getString(name);
		}
		else if(fieldType.equals("Integer")) {
			return String.valueOf(datas.getInt(name));
		}
		else if(fieldType.equals("Number")) {
			return String.valueOf(datas.getDouble(name));
		}
		else if(fieldType.equals("Date")) {
			return datas.getDate(name).toString();
		}
		else if(fieldType.equals("DateTime")) {
			return datas.getDate(name).toString();
		}
		return "";
	}
	
	public void updateDb(JdbcTemplate jdbctemplate) {
		String sqltype = "varchar(256)";
		switch(this.fieldType) {
		case "String":			 
			break;
		case "Text":
			sqltype = "text";
			break;
		case "Integer":
			sqltype = "numeric(24,0)";
			break;
		case "Number":
			sqltype = "decimal(24,6)";
			break;
		case "Date":
			sqltype = "date";
			break;
		case "DateTime":
			sqltype = "datetime";
			break;
		case "Checkbox":
			sqltype = "bit";
			break;
		}
		SqlRowSet trythis = jdbctemplate.queryForRowSet("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'dbo' "
				+ "and TABLE_NAME = '" + this.tracker.getDataTable() + "' and COLUMN_NAME = '" + this.name + "'");
		if(!trythis.next()) {
			System.out.println("Creating field:" + this.name);
			jdbctemplate.execute("alter table " + this.tracker.getDataTable() + " add [" + this.name + "] " + sqltype + " NULL");
		}
	}
	
	@PreRemove
	void beforeDelete() {
		System.out.println("This is going to remove the " + this.name + " field");
	}
}