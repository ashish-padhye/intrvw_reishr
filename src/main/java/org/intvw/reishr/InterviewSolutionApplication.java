package org.intvw.reishr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.intvw.reishr.controller.MatchingCashDataController;
import org.intvw.reishr.controller.NonMatchingCashDataController;
import org.intvw.reishr.validation.CashCollectionDataUnmarshaller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.URISyntaxException;

@SpringBootApplication
public class InterviewSolutionApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(InterviewSolutionApplication.class, args);
		invokeURL("http://localhost:8080/matching", true);
		invokeURL("http://localhost:8080/nonmatching", false);
		/*CashCollectionDataUnmarshaller ccdu = new CashCollectionDataUnmarshaller();
		ccdu.unmarshal();*/
	}

	public static void invokeURL(String url, boolean matchingRequired) {
		BufferedWriter bw = null;
		String filename;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getCollectionData = new HttpGet(url);
			getCollectionData.addHeader("accept", "application/json");
			if(matchingRequired)
				filename="Match.json";
			else
				filename="Mismatch.json";

			HttpResponse response = httpClient.execute(getCollectionData);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			HttpEntity entity = response.getEntity();
			File f = null;
			try {
				f = new File(InterviewSolutionApplication.class.getResource("/cash_collection_output/"+filename).toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			if (entity != null) {
				try (FileOutputStream outstream = new FileOutputStream(f)) {
					entity.writeTo(outstream);
				}
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if(bw!=null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
