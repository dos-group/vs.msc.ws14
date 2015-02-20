package org.apache.flink.runtime.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nico on 18.02.2015.
 */
public class MiddlewareRestHandler {
	static final Logger LOG = LoggerFactory.getLogger(MiddlewareRestHandler.class);
	private String svcHost = "";

	/**
	 * @param servicehost format: http://127.0.0.1:4567/
	 */
	public MiddlewareRestHandler(String servicehost) {
		svcHost = servicehost;
	}

	public String sendGet(String url) throws Exception {

		URL obj = new URL(svcHost + url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		LOG.info("\nSending 'GET' request to URL : " + svcHost + url);
		LOG.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	public String sendPost(String url, String data) throws Exception {

		URL obj = new URL(svcHost + url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		LOG.info("\nSending 'POST' request to URL : " + svcHost + url);
		LOG.info("Post parameters : " + data);
		LOG.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
