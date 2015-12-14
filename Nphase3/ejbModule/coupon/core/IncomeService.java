package coupon.core;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;

import db.Income;



@Local
public interface IncomeService {
	public void storeIncome(Income i);
	public Collection<Income> viewAllIncome();
	public Collection<Income> viewAllIncomeByName(String name);
}
