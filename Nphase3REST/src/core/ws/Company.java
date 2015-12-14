package core.ws;

import java.sql.Date;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.derby.tools.sysinfo;

import coupon.web.service.MyService;

import client.BusinessDelegate;
import db.Income;
import db.IncomeType;

import base.Coupon;
import base.CouponType;
import exceptionForCouponSystem.CouponException;
import facade.CompanyFacade;

@Path("company")
public class Company {
	private String myName;
	@Context private HttpServletRequest request;
	
	
	private Coupon coupon= new Coupon();
	private CompanyFacade cf;
	private List<Coupon> coupons;
	//constructor for throwing exceptions from facade...
	public Company() throws CouponException{
		cf = new CompanyFacade();
	}
	public Date dateHTMLtoSQL(String stringDate){
		java.util.Date utilDate = null;
		Date sqlDate;
		if(stringDate=="undefined"){
			stringDate="000 Dec 31 2099";
		}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String splitPattern = " ";
			String[] part = stringDate.split(splitPattern);
		    String textMonth=part[1];
		    String day=part[2];
		    String year=part[3];
			
			String month="month";
			switch (textMonth){
				case "Jan" : month="1"; break;
				case "Feb" : month="2"; break;
				case "Mar" : month="3"; break;
				case "Apr" : month="4"; break;
				case "May" : month="5"; break;
				case "Jun" : month="6"; break;
				case "Jul" : month="7"; break;
				case "Aug" : month="8"; break;
				case "Sep" : month="9"; break;
				case "Oct" : month="10"; break;
				case "Nov" : month="11"; break;
				case "Dec" : month="12"; break;
			}
			String finalDate=year+"-"+month+"-"+day;
			try {
				utilDate = format.parse(finalDate);
			} catch (ParseException e) {
				e.getMessage();
			}
			sqlDate=new Date(utilDate.getTime());
		return sqlDate;
	}
		
	public ArrayList<CouponHTML> wrap(List<Coupon> coupons2){
		ArrayList<CouponHTML> couponHTML=new ArrayList<>();
		for(int i=0;i<coupons2.size();i++){
			
			couponHTML.add(new CouponHTML(coupons2.get(i)));
		}
		return couponHTML;
	}
//login company:
	@POST
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("name") String name,  @QueryParam("password") String password){
		try {
			if(cf.login(name, password)){
				HttpSession session=request.getSession(true);
				session.setAttribute("cf", cf);
				myName=name;
				return "Successful Company Login!! Welcome "+name+"!!";//+name+"!!";
			}
			else{
				return null;
			}
		} catch (CouponException e) {
			return e.getMessage()+". Try again..";
		}
	}
	
//logout company:
	@POST
	@Path("logout")
	public String logout(){

		HttpSession session=request.getSession();
		session.invalidate();
		return null;
	}
	
//Show all:	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CouponHTML> all(){
		try{
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cf.getAllCoupon());
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

	//add:
	@POST
	@Path("add")
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@QueryParam("title") String title,
							 @QueryParam("jsdate") String stringDate,
							 @QueryParam("amount") int amount,
							 @QueryParam("type") CouponType type,
							 @QueryParam("message") String message,
							 @QueryParam("price") double price,
							 @QueryParam("image") String image){
		
		Date startDate=new Date(new java.util.Date().getTime());
		Date endDate=dateHTMLtoSQL(stringDate);
		
		HttpSession session=request.getSession(true);		
		try {
			coupon=new Coupon(title, endDate, amount, type, message, price, image, startDate);
			cf=(CompanyFacade)session.getAttribute("cf");
			cf.createCoupon(coupon);
						
			
			return "ok";
		}catch (CouponException e) {
			return e.getMessage();
		}
	}
	
//update:
	@POST
	@Path("Cupdate")
	@Produces(MediaType.TEXT_PLAIN)
	public String update(@QueryParam("id") long id,
						@QueryParam("title") String title,
			 			@QueryParam("endDate") String stringDate,
						@QueryParam("amount") int amount,
						@QueryParam("type") CouponType type,
						@QueryParam("message") String message,
						@QueryParam("price") double price,
						@QueryParam("image") String image,
						@QueryParam("startDate") Date startDate){
								

		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupon=cf.getCoupon((int) id);
			
			Date endDate;
			endDate=dateHTMLtoSQL(stringDate);
			
			
			coupon.setEndDate(endDate);
			coupon.setAmount(amount);
			coupon.setType(type);
			coupon.setMessage(message);
			coupon.setPrice(price);
			coupon.setImage(image);
			cf.updateCoupon(coupon);			
			
			return "ok";
		} catch (CouponException e) {
			return e.getMessage();
		}
	}
	
//delete:
	@POST
	@Path("del")
	@Produces(MediaType.TEXT_PLAIN)
	public String del(@QueryParam("id") long id){
		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupon.setId(id);
			cf.removeCoupon(coupon);
			return "ok";
		
		} catch (Exception e) {
			return e.getMessage();
		}
	
	}
	
//specify show coupons:	
	@GET
	@Path("byId")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> byId(@QueryParam("id") long id){
		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>();
			coupons.add(cf.getCoupon((int)id));
			return wrap(coupons);
			
		} catch (CouponException e) {
			//this code sign to the java script that there is an error

			coupon=new Coupon();
			coupon.setId(-1);
			coupon.setMessage(e.getMessage());
			coupons=new ArrayList<>();
			coupons.add(coupon);
			return wrap(coupons);
		}
	}
	@GET
	@Path("type")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> type(@QueryParam("type") CouponType type){
		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cf.getCouponByType(type));
			return wrap(coupons);
		} catch (CouponException e){
			return null;
		}
	}
	
	@GET
	@Path("endDate")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> endDate(@QueryParam("jsdate") String stringDate){
		Date endDate=dateHTMLtoSQL(stringDate);
		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cf.getCouponByLateDate(endDate));
			return wrap(coupons);		
		} catch (CouponException e) {
			return null;
		}
	}
	
	@GET
	@Path("price")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<CouponHTML> price(@QueryParam("price") double price){
		try {
			cf=(CompanyFacade)request.getSession().getAttribute("cf");
			coupons=new ArrayList<>(cf.getCouponByMaxPrice(price));
			return wrap(coupons);
		} catch (CouponException e) {
			return null;
		}
	}
}
