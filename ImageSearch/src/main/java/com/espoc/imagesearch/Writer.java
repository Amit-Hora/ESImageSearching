package com.espoc.imagesearch;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import sun.misc.BASE64Encoder;

public class Writer implements Runnable {

	private byte[] data;
	private String name;
	private String defaulthdfs;
	private String elasticsearchnode;

	public Writer(byte[] data, String name, String defaulthdfs, String elasticsearchnode) {
		this.data = data;
		this.defaulthdfs = defaulthdfs;
		this.name = name;
		this.elasticsearchnode = elasticsearchnode;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			String cleanTitle = this.name;
			Configuration conf = ImageSearchUtils.getConfiguration(this.defaulthdfs);
			FileSystem hdfs = FileSystem.get(new URI(this.defaulthdfs), conf);
			org.apache.hadoop.fs.Path outPath = new org.apache.hadoop.fs.Path("/flickr" + File.separator + this.name);
			OutputStream outputStream = hdfs.create(outPath);
			InputStream in = new ByteArrayInputStream(data);

			IOUtils.copyBytes(in, outputStream, conf);
			in = new ByteArrayInputStream(data);
			String imgString = new BASE64Encoder().encode(org.apache.commons.io.IOUtils.toByteArray(in))
					.replaceAll("[\n\r]", "");
			Client client = new TransportClient().addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName(this.elasticsearchnode), 9300));
			String json = "{\"name\":\"" + cleanTitle + "\",\"image\":\"" + imgString + "\",\"path\":\""
					+ outPath.toUri().toString() + "\" }";
			  IndexRequest indexrequest=new IndexRequest("images", "image");
              indexrequest.source(json);
				client.index(indexrequest).actionGet();
			client.close();
			hdfs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
