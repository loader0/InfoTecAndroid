package com.example.infotec;

import android.util.Log;
import android.widget.Switch;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();


    public HttpHandler(){

    }

    public String makeServiceCall (String reqUrl,String Json,int tipoServicio) throws Exception  {
        String inputLine = "";
        try {

            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            switch(tipoServicio)
            {
                case 1:
                    conn.setRequestMethod("GET");
                    break;
                case 2:
                    conn.setRequestMethod("POST");
                    break;
                default:
                    break;
            }

            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            if(Json != null)
                out.writeBytes(Json);

            out.flush();
            out.close();
            int status = conn.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK ){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer content = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.print(content.toString());
            }
            conn.disconnect();

        }catch (MalformedURLException e){
            Log.e(TAG,"MalformedURLException:" + e.getMessage());
        }catch (ProtocolException e){
            Log.e(TAG,"ProtocolException:" + e.getMessage());
        }catch (IOException e){
            Log.e(TAG,"IOException:" + e.getMessage());
        }catch(Exception e){
            Log.e(TAG,"Exception:" + e.getMessage());
        }

        return inputLine;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while ((line = reader.readLine())!=null){
                sb.append(line).append("\n");
            }
        }catch(IOException e){
            Log.e(TAG,"IOException:" + e.getMessage());
        } finally {
            try{
                is.close();
            }catch(IOException e){
                Log.e(TAG,"IOException:" + e.getMessage());
            }
        }

        return sb.toString();

    }
}
