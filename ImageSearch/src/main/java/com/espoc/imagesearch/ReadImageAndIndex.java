package com.espoc.imagesearch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadImageAndIndex implements Runnable{

	@Autowired
	ConfigReader configReader;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String directorytoRead= configReader.directory;
		final File folder = new File(directorytoRead);
		try{
		listFilesForFolder(folder);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void listFilesForFolder(final File folder) throws Exception {
		int i=0;
		Configuration conf=ImageSearchUtils.getConfiguration(configReader.defaulthdfs);
//		ExecutorService executerservice = Executors.newFixedThreadPool(4);
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	i++;
	        	
	        	Path path = Paths.get(configReader.directory+"/"+fileEntry.getName());
	        	byte[] data = Files.readAllBytes(path);
//	        	executerservice.submit(new Writer(data, fileEntry.getName(), configReader.defaulthdfs, configReader.elasticsearchnode));	
	        	new Writer(data, fileEntry.getName(), configReader.defaulthdfs, configReader.elasticsearchnode).run();
	        	/*
	            System.out.println(fileEntry.getName());
	            String cleanTitle=fileEntry.getName();
				FileSystem hdfs = FileSystem.get(new URI(configReader.defaulthdfs), conf);
				org.apache.hadoop.fs.Path outPath = new org.apache.hadoop.fs.Path("/flickr"+File.separator + fileEntry.getName());
				OutputStream outputStream=hdfs.create(outPath);
				InputStream in = new ByteArrayInputStream(data);
                IOUtils.copyBytes(in, outputStream, conf);
                String imgString = new BASE64Encoder().encode(org.apache.commons.io.IOUtils.toByteArray(in)).replaceAll("[\n\r]", "");
                Client client = new TransportClient()
            	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(configReader.elasticsearchnode), 9300));
                String json="{\"name\":\""+cleanTitle+"\",\"image\":\""+imgString+"\",\"path\":\""+outPath.toUri().toString()+"\" }";
                client.close();
                hdfs.close();
			*/	
				
	        }
	    }
//	    executerservice.shutdown();
	    
	    
	}

	
	
}
