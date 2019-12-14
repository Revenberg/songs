package info.revenberg.song.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.revenberg.song.domain.Bundle;
import info.revenberg.song.domain.Song;
import info.revenberg.song.domain.Vers;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

import java.util.Random;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class VersTest {
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

        System.out.println(new String(r1Json));
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(new String(r1Json));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + new String(r1Json));
        System.out.println("Response Code : " + responseCode);
        this.responseCode = responseCode;

        if ((responseCode > 200) && (responseCode < 300)) {
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
        r.setBundleid(0);
        r.setMnemonic(prefix + "_mnemonic");
        return r;
    }

    private Song mockSong(String prefix, Bundle bundle) {
        Song r = new Song();
        r.setName(prefix + "_name");
        r.setSongid(0);
        r.setBundle(bundle);
        r.setsource("Test");
        return r;
    }

    private Vers mockVers(String prefix, Song song) {
        Vers r = new Vers();
        r.setName(prefix + "_name");
        r.setVersid(new Random().nextInt(10000));
        r.setSong(song);
        r.setRank(new Random().nextInt(10));
        r.setTitle("blabla");
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    @Test
    public void uploadFileTest() throws IOException {
        String result = uploadFile("http://localhost:8090/rest/v1/image/upload",
                "C:\\Users\\reven\\Downloads\\four.png");

    }

    public String uploadFile(String postEndpoint, String filename) throws IOException {
        File testUploadFile = new File(filename); // Specify your own location and file

        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                // keep alive for 30 seconds
                return 5 * 1000;
            }

        };

        CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(myStrategy).build();

        // build httpentity object and assign the file that need to be uploaded
        HttpEntity postData = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", testUploadFile, ContentType.DEFAULT_BINARY, testUploadFile.getName()).build();

        // build http request and assign httpentity object to it that we build above
        HttpUriRequest postRequest = RequestBuilder.post(postEndpoint).setEntity(postData).build();

        System.out.println("Executing request " + postRequest.getRequestLine());

        HttpResponse response = httpclient.execute(postRequest);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        // Create the StringBuffer object and store the response into it.
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        System.out.println("Response : \n" + result);

        // Throw runtime exception if status code isn't 200
        if ((response.getStatusLine().getStatusCode() < 200) || (response.getStatusLine().getStatusCode() > 299)) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }
        return result.toString();

    }

    @Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        Bundle b1 = mockBundle("shouldCreateAndUpdate");
        byte[] b1Json = toJson(b1);
        // CREATE Bundle 1
        sendPost("http://localhost:8090/rest/v1/bundle", b1Json, MediaType.APPLICATION_JSON);
        System.out.println(responseMSG);
        System.out.println(responseCode);
        Gson g = new Gson();
        b1 = g.fromJson(responseMSG, Bundle.class);

        Song s1 = mockSong("shouldCreateAndUpdate1", b1);
        byte[] s1Json = toJson(s1);
        // CREATE Song 1
        System.out.println(new String(s1Json));
        sendPost("http://localhost:8090/rest/v1/song", s1Json, MediaType.APPLICATION_JSON);
        System.out.println(responseMSG);
        System.out.println(responseCode);
        s1 = g.fromJson(responseMSG, Song.class);

        System.out.println("!!!!!!!!!!!! 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(s1);
        System.out.println("!!!!!!!!!!!!! 2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Vers v1;
        byte[] v1Json;
        String fileid;
        String fileloc;

        // UPLOAD FILE 1
        // fileloc = "C:\\Users\\reven\\Downloads\\four.png";
        // fileid = uploadFile("http://localhost:8090/rest/v1/image/upload", fileloc);

        // CREATE VERS 1
        v1 = mockVers("shouldCreateAndUpdate", s1);
        v1Json = toJson(v1);
        System.out.println(new String(v1Json));
        System.out.println("!!!!!!!!!!!!!!! 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        sendPost("http://localhost:8090/rest/v1/vers", v1Json, MediaType.APPLICATION_JSON);
        System.out.println(responseMSG);
        System.out.println(responseCode);

        v1 = mockVers("shouldCreateAndUpdate", s1);
        v1Json = toJson(v1);
        System.out.println(new String(v1Json));
        System.out.println("!!!!!!!!!!!!!!! 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // UPLOAD FILE 2
        // fileloc = "C:\\Users\\reven\\Downloads\\four.png";
        // fileid = uploadFile("http://localhost:8090/rest/v1/image/upload", fileloc);

        // CREATE VERS 1
        sendPost("http://localhost:8090/rest/v1/vers", v1Json, MediaType.APPLICATION_JSON);
        System.out.println(responseMSG);
        System.out.println(responseCode);

        v1 = mockVers("shouldCreateAndUpdate", s1);
        v1Json = toJson(v1);
        System.out.println(new String(v1Json));
        System.out.println("!!!!!!!!!!!!!!! 3 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // CREATE
        sendPost("http://localhost:8090/rest/v1/vers", v1Json, MediaType.APPLICATION_JSON);
        System.out.println(responseMSG);
        System.out.println(responseCode);
    }

}
