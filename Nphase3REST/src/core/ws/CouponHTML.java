package core.ws;

import java.sql.Date;
import javax.xml.bind.annotation.XmlRootElement;
import base.Coupon;
import base.CouponType;

@XmlRootElement
public class CouponHTML {
	private long id;
	private String title;
	private String startDate;
	private String endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	public CouponHTML(){}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CouponHTML(Coupon coupon){
		this.id=coupon.getId();
		this.title=coupon.getTitle();
		this.startDate=coupon.getStartDate()+"";
		this.endDate=coupon.getEndDate()+"";
		this.amount=coupon.getAmount();
		this.type=coupon.getType();
		this.message=coupon.getMessage();
		this.price=coupon.getPrice();
		this.image=coupon.getImage();

	}
}
