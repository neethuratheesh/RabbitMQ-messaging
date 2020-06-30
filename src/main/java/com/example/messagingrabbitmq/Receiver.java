package com.example.messagingrabbitmq;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.example.messagingrabbitmq.model.Award;

@Component
public class Receiver {
	
	@Autowired
	private Award catalina;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private MyThread myThread;
	
	@Autowired
	private ApplicationContext applicationContext;
	
     @Value("${path.file.write}")
     private String path;
	
	//private CountDownLatch latch = new CountDownLatch(1);
	private static final Logger logger = Logger.getLogger(Receiver.class);
	/*private static JSONParser parser;
	
	public void receiveMessage(String message) {
		logger.debug("Received : " +message);
		parser = new JSONParser();
		try {
			//JSONObject obj = new JSONObject(message);
			
			JSONObject obj = (JSONObject)(parser.parse(message));
			logger.debug("Message" +obj.toString());
			String awardNumber = (String) obj.get("award_number");
			logger.debug("Award Number : " +awardNumber);
			String jsonmessage = (String) obj.get("message");
			logger.debug("Message : " +jsonmessage);
			JSONArray stores = (JSONArray) obj.get("stores");
			Iterator  i = stores.iterator();
			while (i.hasNext()) {
				logger.debug("Store Number : " +i.next());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		latch.countDown();
	}*/
	      	
        
            
        
	
	@RabbitListener(queues = MessagingRabbitmqApplication.queueName)
    public void receiveMessage(final Award award)  {
   logger.info("Received message as specific class: {}" +award.toString());
   logger.debug("Award Number : " +award.getAwardNumber());
   List<String> stores = award.getStores();
   String fileName = award.getAwardNumber();
   for (String eachStore:stores) {
	  MyThread myThread = applicationContext.getBean(MyThread.class,eachStore,fileName, award.getMessage());
	  taskExecutor.execute(myThread);
   }
  // MyThread myThread = applicationContext.getBean(MyThread.class,stores,fileName, award.getMessage());
  // taskExecutor.execute(myThread);
  /* for (String eachStore:stores) {
	   File file = new File(path+eachStore);
	   if (!file.exists()) {
	   file.mkdir();
	   BufferedWriter bw;
	try {
		bw = new BufferedWriter(new FileWriter(path+eachStore+"\\"+award.getAwardNumber()+".xml"));
		bw.write(award.getMessage());
		
		   bw.flush();
		   bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	   
	   }
	   //BufferedWriter out = new BufferedWrited(new FileWriter(""))
   
   
       // Logger.info("Received message as specific class: {}", catalina.toString());
    }*/
	
	
	

	

}
}

