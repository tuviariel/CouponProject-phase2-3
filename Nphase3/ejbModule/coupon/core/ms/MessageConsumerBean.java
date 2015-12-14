package coupon.core.ms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import coupon.core.IncomeService;
import db.Income;

@MessageDriven(activationConfig={
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="queue/Coupon-Queue")
})
public class MessageConsumerBean implements MessageListener{

	@EJB private IncomeService incomeServiceStub;
	
	@Override
	public void onMessage(Message msg) {
		try{
			ObjectMessage om=(ObjectMessage)msg;
			Income data=(Income)om.getObject();
			incomeServiceStub.storeIncome(data);
		}catch(JMSException e){
			e.printStackTrace();
		}
	}

}
