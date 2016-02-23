package com.espoc.imagesearch;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class ImageSearchUtils {

	public static Configuration getConfiguration(String defaulthdfs) throws Exception{
		Configuration conf=new Configuration();
	 	conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
	 	 conf.set("fs.hdfs.impl", 
			        org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
			        conf.set("fs.defaultFS", defaulthdfs);
			        return conf;
		
	}
}
