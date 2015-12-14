package coupon.web.service;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

import db.Income;
import db.IncomeType;

@XmlRootElement
public class IncomeWrapper {
	
	private long id;
	private String name;
	private String date;
	private String description;
	private double amount;

	public IncomeWrapper(){}
	
	public IncomeWrapper(Income i){
		this.id=i.getId();
		this.name=i.getName();
		this.date=i.getDate()+"";
		this.description=i.getDescription().getDescription();
		this.amount=i.getAmount();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
