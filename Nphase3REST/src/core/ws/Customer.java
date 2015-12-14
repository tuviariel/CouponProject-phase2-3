package core.ws;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.derby.tools.sysinfo;

import DAO.CouponDAO;
import DBDAO.CouponDBDAO;
import base.Coupon;
import base.CouponType;
import exceptionForCouponSystem.CouponException;
import facade.CustomerFacade;

@Path("customer")
public class Customer {

	@Context private HttpServletRequest request;
	
	private Coupon coupon= new Coupon();
	private CouponDAO couponDB= new CouponDBDAO();
	private CustomerFacade cuf;
	private List<Coupon> coupons;
	
	//constructor for throwing exceptions from facade...
	public Customer() throws CouponException{
		cuf = new CustomerFacade();
	}
	
	public ArrayList<CouponHTML> wrap(List<Coupon> coupons2){
		//to Date toString (wrapper)
		ArrayList<CouponHTML> couponHTML=new ArrayList<>();
		for(int i=0;i<coupons2.size();i++){
			couponHTML.add(new CouponHTML(coupons2.get(i)));
		}
		return couponHTML;
	}
	
	//login customer:
	@POST
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("name") String name,  @QueryParam("password") String password){
		try {
			if(cuf.login(name, password)){
				HttpSession session=request.getSession(true);
				session.setAttribute("cf", cuf);
				return "Successful Customer Login!! Welcome "+name+"!!";
			}
			return null;
		} catch (CouponException e) {
			return e.getMessage()+". Try again..";
		}
	}
	
//logout customer:
	@POST
	@Path("logout")
	public String login(){

		HttpSession session=request.getSession();
		
		session.invalidate();
		return null;
	}
	
//Show all Purchased:	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> allCoupons(){
		try{
			cuf=(CustomerFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cuf.getAllPurchasesCoupons());
			//adding two coupons which will not be shown in order to export array in the JSON

			Coupon c = new Coupon("zzzzzzzzz",new Date(0),0,CouponType.CAMPING,"",19,"",new Date(0));
			coupons.add(c);
			coupons.add(c);
			
			return wrap(coupons);
		}catch  (CouponException e) {
			//this code sign to the java script that there is an error

			coupon=new Coupon();
			coupon.setId(-1);
			coupon.setMessage(e.getMessage());
			coupons=new ArrayList<>();
			coupons.add(coupon);
			
			return wrap(coupons);
		}
		
	}

//Show all Coupons:	
	@GET
	@Path("store")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> allStore(){
		try{
		cuf=(CustomerFacade)request.getSession().getAttribute("cf");
		coupons=new ArrayList<>(couponDB.getAllCoupon());
		//adding two coupons which will not be shown in order to export array in the JSON

		Coupon c = new Coupon("zzzzzzzzz",new Date(0),0,CouponType.CAMPING,"",19,"",new Date(0));
		coupons.add(c);
		coupons.add(c);
		
		
		return wrap(coupons);
		}catch  (CouponException e) {
			//this code sign to the java script that there is an error

			coupon=new Coupon();
			coupon.setId(-1);
			coupon.setMessage(e.getMessage());
			coupons=new ArrayList<>();
			coupons.add(coupon);
			return wrap(coupons);
		}
	}
		
	//buy:
	@GET
	@Path("purchase")
	@Produces(MediaType.TEXT_PLAIN)
	public String purchase(@QueryParam("cid") long id){
		HttpSession session=request.getSession(true);
		try {
			
			cuf=(CustomerFacade)session.getAttribute("cf");
			coupon=couponDB.getCoupon(id);
			
			cuf.purchaseCoupon(coupon);
			return "ok";
		}catch (CouponException e) {
			return e.getMessage();
		}
	}
	
//specify show coupons:	

	@GET
	@Path("type")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> type(@QueryParam("type") CouponType type){
		try {
			cuf=(CustomerFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cuf.getAllPurchasesCouponsByType(type));
			return wrap(coupons);
		} catch (CouponException e){
			return null;
		}
	}
	
	@GET
	@Path("price")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> price(@QueryParam("price") double price){
		try {
			cuf=(CustomerFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cuf.getAllPurchasesCouponsByPrice(price));
			return wrap(coupons);
		} catch (CouponException e) {
			return null;
		}
	}	
}
