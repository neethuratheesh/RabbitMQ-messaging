package com.example.messagingrabbitmq;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.messagingrabbitmq.model.Award;

@Component
@Scope("prototype")


public class MyThread implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(MyThread.class);
	
	private String eachStore;
	
	private String fileName;

	private String message;
	
	@Autowired
	private Award catalina;
	
	 @Value("${path.file.write}")
     private String path;
	
	public MyThread() {
		
	}
	public MyThread(String eachStore,String fileName,String message) {
		this.eachStore=eachStore;
		this.fileName=fileName;
		this.message=message;
		
	}
	    
	    @Override
	    public void run() {
	    	 
	    		   File file = new File(path+eachStore);
	    		   LOGGER.debug("Checking directory " +Thread.currentThread().getName());
	    		   if (!file.exists()) {
	    		   file.mkdir();
	    		   BufferedWriter bw;
	    		try {
	    			bw = new BufferedWriter(new FileWriter(path+eachStore+"\\"+fileName+".xml"));
	    			LOGGER.debug("Writing by : " +Thread.currentThread().getName());
	    			//bw.write(catalina.getMessage());
	    			bw.write(message);
	    			
	    			
	    			   bw.flush();
	    			   bw.close();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
	        
	    
	}
	    }

		

		public String getEachStore() {
			return eachStore;
		}
		public void setEachStore(String eachStore) {
			this.eachStore = eachStore;
		}
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
}

