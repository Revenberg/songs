package info.revenberg.song.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.revenberg.song.domain.Bundle;

import javax.ws.rs.core.MediaType;
import org.junit.Test;
import java.util.Random;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BundleTest {
    private final String USER_AGENT = "Mozilla/5.0";

    private String responseMSG;
    private int responseCode;

    // HTTP POST request
    private void sendPost(String url, byte[] r1Json, String applicationJson) throws Exception {

        URL obj = new URL(url);
        // HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", applicationJson);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(new String(r1Json));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + r1Json);
        System.out.println("Response Code : " + responseCode);
        this.responseCode = responseCode;

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            this.responseMSG = response.toString();
        } else {
            this.responseMSG = null;
        }
    }
   
    private Bundle mockBundle(String prefix) {
        Bundle r = new Bundle();
        r.setName(prefix + "_name");
        r.setBundleid(new Random().nextInt(100));
        r.setMnemonic(prefix + "_mnemonic");        
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    @Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        Bundle r1 = mockBundle("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        System.out.println(new String(r1Json));
        //CREATE
        sendPost("http://localhost:8090/rest/v1/bundle", r1Json, MediaType.APPLICATION_JSON);  
        System.out.println(responseMSG);
        System.out.println(responseCode);             
    }

}
