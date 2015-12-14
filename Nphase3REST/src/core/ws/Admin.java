package core.ws;

import java.util.ArrayList;
import java.util.HashSet;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import base.Company;
import base.Customer;
import exceptionForCouponSystem.CouponException;
import facade.AdminFacade;


@Path("admin")


public class Admin {
	@Context private HttpServletRequest request;
	
	private Company com=new Company();
	private Customer cus=new Customer();
	private ArrayList<Company> coms;
	private ArrayList<Customer> cuss;
	
	@GET
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("name") String name,
			 @QueryParam("password") String pswd){
		
		
		try{
			AdminFacade facade;
			facade=new AdminFacade();
			if(facade.login(name, pswd))
			{
				
				HttpSession session=request.getSession(true);
				session.setAttribute("facade", facade);
				return "getin";
			}
			else{
				return null;
			}
		}catch (CouponException e) {
			return e.getMessage();
		}
		
	}
	
	@POST
	@Path("logout")
	public String login(){

		
		HttpSession session=request.getSession();
				
		session.invalidate();
		
		
		return null;
	}
	
	@GET
	@Path("getCompanie")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Company> getCompanies(){

		try {
			AdminFacade af=(AdminFacade)request.getSession().getAttribute("facade");
			coms=new ArrayList<>(af.getAllCompanies());	
			//adding two companies which will not be shown in order to export array in the JSON
			Company c = new Company();
			c.setName("zzzzzzzzzzzzz");
			coms.add(c);
			coms.add(c);
			return coms;
			
		}catch (CouponException e) {
			//this code sign to the java script that there is an error
			com.setId(-1);
			com.setName(e.getMessage());
			coms=new ArrayList<>();
			coms.add(com);
			return coms;
	}
	
	}
	@POST
	@Path("addCompany")
	@Produces(MediaType.TEXT_PLAIN)

	public String addCompany(@QueryParam("name") String name,
							 @QueryParam("password") String pswd,
							 @QueryParam("email") String email){
		

		
		HttpSession session=request.getSession(true);
		

		try {
			Company company=new Company(name,pswd,email);
			AdminFacade facade=(AdminFacade)session.getAttribute("facade");

			facade.createCompany(company);
			return "ok";
		} catch (CouponException e) {
			return e.getMessage();
		}
	
	}
	@POST
	@Path("updateCompany")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCompany(@QueryParam("id") long id,
								@QueryParam("name") String name,
								@QueryParam("password") String pswd,
								@QueryParam("email") String email){

		
		
		
		try {
			AdminFacade facade=(AdminFacade)request.getSession().getAttribute("facade");
			Company company=facade.getCompany(id);

			
			company.setPassword(pswd);
			company.setEmail(email);
			
			facade.updateCompany(company);
			return "ok";
		} catch (CouponException e) {
			return e.getMessage();
		}
	
	}
	
	@POST
	@Path("deleteCompany")
	@Produces(MediaType.TEXT_PLAIN)

	public String deleteComapny(@QueryParam("id") long id){

		
		
		
		try {
			AdminFacade facade=(AdminFacade)request.getSession().getAttribute("facade");
			Company company=new Company();
			company.setId(id);
			facade.removeCompany(company);
			return "ok";
		
		} catch (Exception e) {
			return e.getMessage();
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	@GET
	@Path("getcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> getCustomers(){
		try {
			AdminFacade af=(AdminFacade)request.getSession().getAttribute("facade");
			
			cuss=new ArrayList<>(af.getAllCustomer());
			//adding two customers which will not be shown in order to export array in the JSON

			Customer c=new Customer();
			c.setCustName("zzzzzzzzzz");
			cuss.add(c);
			cuss.add(c);
			return cuss;
		} catch (CouponException e) {
			//this code sign to the java script that there is an error

			cus.setId(-1);
			cus.setCustName(e.getMessage());
			cuss=new ArrayList<>();
			cuss.add(cus);
			return cuss;
		}
	
	}
	@POST
	@Path("addCustomer")
	@Produces(MediaType.TEXT_PLAIN)

	public String addCompany(@QueryParam("name") String name,
							 @QueryParam("password") String pswd){
		try {
			Customer customer=new Customer(name,pswd);
			AdminFacade facade=(AdminFacade)request.getSession().getAttribute("facade");
			facade.createCustomer(customer);
			return "ok";
		} catch (CouponException e) {
			return e.getMessage();
		}
	
	}
	@POST
	@Path("updateCustomer")
	@Produces(MediaType.TEXT_PLAIN)

	public String updateCustomer(@QueryParam("id") long id,
								@QueryParam("name") String name,
								@QueryParam("password") String pswd){
		try {
			AdminFacade facade=(AdminFacade)request.getSession().getAttribute("facade");
			Customer customer=facade.getCustomer(id);
			customer.setPassword(pswd);
			facade.updateCustomer(customer);
			return "ok";
		} catch (CouponException e) {
			return e.getMessage();
		}
	
	}
	
	@POST
	@Path("deleteCustomer")
	@Produces(MediaType.TEXT_PLAIN)

	public String deleteCustomer(@QueryParam("id") long id){
		try {
			AdminFacade facade=(AdminFacade)request.getSession().getAttribute("facade");
			Customer customer=new Customer();
			customer.setId(id);
			facade.removeCustomer(customer);
			return "ok";
		
		} catch (Exception e) {
			return e.getMessage();
		}
	
	}
	
}
