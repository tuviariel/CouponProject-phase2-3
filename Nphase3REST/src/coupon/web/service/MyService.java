package coupon.web.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import client.BusinessDelegate;

import db.Income;
import db.IncomeType;

@Singleton
@Path("income")
public class MyService {

	private BusinessDelegate bd;
	
	@PostConstruct
	public void init(){
		bd=new BusinessDelegate();
	}
	
	@Path("add/{name}")
	@GET
	public String addNewCoupon(@PathParam("name")String name){
		//income = 100
		Income i=new Income();
		i.setAmount(100);
		i.setDate(new Date(System.currentTimeMillis()));
		i.setName(name);
		i.setDescription(IncomeType.COMPANY_NEW_COUPON);
		bd.storeIncome(i);
		return "success";
	}
	
	@Path("update/{name}")
	@GET
	public String updateCoupon(@PathParam("name")String name){
		//income = 100
		Income i=new Income();
		i.setAmount(10);
		i.setDate(new Date(System.currentTimeMillis()));
		i.setName(name);
		i.setDescription(IncomeType.COMPANY_UPDATE_COUPON);
		bd.storeIncome(i);
		return "suc";
	}
	
	@Path("purchase/{name}/{amount}")
	@GET
	public String addNewCoupon(@PathParam("name")String name,
			@PathParam("amount")String amount){
		//income = 100
		Income i=new Income();
		i.setAmount(Double.parseDouble(amount));
		i.setDate(new Date(System.currentTimeMillis()));
		i.setName(name);
		i.setDescription(IncomeType.CUSTOMER_PURCHASE);
		bd.storeIncome(i);
		return "s";
	}
	
	@Path("view/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<IncomeWrapper> getAllIncome(){
		List<IncomeWrapper> result=new ArrayList<>();
		for(Income i:bd.viewAllIncome())
				result.add(new IncomeWrapper(i));
		Income i = new Income();
		i.setName("zzzzzzzzzz");
		i.setAmount(0);
		i.setId(2000000000);
		i.setDescription(IncomeType.COMPANY_NEW_COUPON);
		result.add(new IncomeWrapper(i));
		result.add(new IncomeWrapper(i));

		return result;
	}
	
	@Path("view/byName/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<IncomeWrapper> getIncomeByName(@PathParam("name")String name){
		List<IncomeWrapper> result=new ArrayList<>();
		for(Income i:bd.viewIncomeByName(name))
			result.add(new IncomeWrapper(i));
		
		Income i = new Income();
		i.setName("zzzzzzzzzz");
		i.setAmount(0);
		i.setId(2000000000);
		i.setDescription(IncomeType.COMPANY_NEW_COUPON);

		result.add(new IncomeWrapper(i));
		result.add(new IncomeWrapper(i));
	return result;
	}
	
	
	
	
}
