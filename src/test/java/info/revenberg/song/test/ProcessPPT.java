package info.revenberg.song.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

public class ProcessPPT {
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

        //System.out.println(new String(r1Json));
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(new String(r1Json));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + new String(r1Json));
        //System.out.println("Response Code : " + responseCode);
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

        //System.out.println("Executing request " + postRequest.getRequestLine());

        HttpResponse response = httpclient.execute(postRequest);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        // Create the StringBuffer object and store the response into it.
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        //System.out.println("Response : \n" + result);

        // Throw runtime exception if status code isn't 200
        if ((response.getStatusLine().getStatusCode() < 200) || (response.getStatusLine().getStatusCode() > 299)) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }
        return result.toString();

    }

    public static void search(final String pattern, final File folder, List<String> result, final String pre) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result, pre + f.getName() + "/");
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    if (pre == "") {
                        result.add(f.getName());
                    } else {
                        result.add(pre + f.getName());
                    }
                }
            }
        }
    }

    public ProcessPPT(String location) throws Exception {
        final File folder = new File(location);
        List<String> list = new ArrayList<>();

        ProcessPPT.search(".*", folder, list, "");

        for (String e : list) {
            if (e.toLowerCase().contains(".pptx")) {
                String[] s = e.split("/");
                String bundleName = URLEncoder.encode(s[0].trim(), "UTF-8");
                String songName = URLEncoder.encode(s[1].replace(".pptx", ""), "UTF-8");
               //System.out.println(bundleName + " - " + songName + " - " + folder + "/" + e);
                uploadFile("http://localhost:8090/rest/v1/ppt/" + bundleName + "/" + songName, folder + "/" + e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ProcessPPT("D:/pptx/test");
        //new ProcessPPT("D:/pptx");
    }
}
