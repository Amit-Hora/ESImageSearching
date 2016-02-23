package com.espoc.imagesearch;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigReader {

	 @Value("${app.directory}")
	public String directory;
	 
	 @Value("${app.defaulthdfs}")
	 public String defaulthdfs;
	 
	 @Value("${app.size}")
	 public String size;
	 
	 @Value("${app.elasticsearchnode}")
	 public String elasticsearchnode;
	 	
}
