package WebService;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by A on 01/04/2017.
 */

 public class RestClient {
        private ArrayList<NameValuePair> params;
        private ArrayList<NameValuePair> headers;
        public static JSONArray jsondata = null;
        private String url;
        public boolean success=false;
        private int responseCode;
        private String message;

        private String response;

        public String getResponse() {
            return response;
        }

        public String getErrorMessage() {
            return message;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public RestClient(String url) {
            Log.i("info", url);
            this.url = url;
            params = new ArrayList<NameValuePair>();
            headers = new ArrayList<NameValuePair>();
        }

        public void clearAddParam() {
            params.clear();
        }

        public void clearAddHeader() {
            headers.clear();
        }

        public void AddParam(String name, String value) {
            params.add(new BasicNameValuePair(name, value));

        }

        //Crea el encabezado de la consulta
        public void AddHeader(String name, String value) {
            headers.add(new BasicNameValuePair(name, value));
        }

        //Método para formar la consulta
        private String getQuery(ArrayList<NameValuePair> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        public void Execute(int method) throws Exception {

            switch (method) {

                //unicamente usamos un método post
                case RequestMethod.POST: {

                    //ThreadPolicy controla problemas y errores al realizar ciertas acciones en red
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL Url = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) Url
                            .openConnection();

                    // Set to POST
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    Writer writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(getQuery(params));
                    writer.flush();
                    writer.close();
                    connection.connect();
                    response = convertStreamToString(connection.getInputStream());
                    if(connection.getResponseCode()==201 || connection.getResponseCode()==200)
                    {
                        //Si se obtuvo una respuesta de que nuestros datos han sido consultados,
                        //el servidor devuelve un código 200 o 201
                        success = true;
                    }

                }
            }

        }

        private static String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }



