package coupon.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import db.Income;



@Stateless(name="incomeService")
public class IncomeServiceBean implements IncomeService{
	@PersistenceContext(unitName="couponPU") private EntityManager em;

	@Override
	public void storeIncome(Income i) {
		em.persist(i);
		
	}

	@Override
	public Collection<Income> viewAllIncome() {
		Query q=em.createQuery("SELECT i FROM Income AS i");
		return (Collection<Income>)q.getResultList();
	}

	@Override
	public Collection<Income> viewAllIncomeByName(String name) {
		Query q=em.createQuery("SELECT i FROM Income AS i WHERE i.name = :clientName");
		q.setParameter("clientName", name);
		return (Collection<Income>)q.getResultList();
	}
}
