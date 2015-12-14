package db;

public enum IncomeType {
	CUSTOMER_PURCHASE("Customer purchased coupon"),
	COMPANY_NEW_COUPON("Company created new coupon"),
	COMPANY_UPDATE_COUPON("Company updated coupon details");
	
	private String description;
	
	private  IncomeType(String description){
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

}
