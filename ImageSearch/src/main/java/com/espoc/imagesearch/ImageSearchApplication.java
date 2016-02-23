package com.espoc.imagesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@SpringBootApplication
public class ImageSearchApplication {
	
	
	public static void main(String[] args) {
		 ApplicationContext app=SpringApplication.run(ImageSearchApplication.class, args);
		 
		ReadImageAndIndex readImageAndIndex=app.getBean(ReadImageAndIndex.class);
	
		Thread t=new Thread(readImageAndIndex);
		t.start();
		
	}
	
	
}
