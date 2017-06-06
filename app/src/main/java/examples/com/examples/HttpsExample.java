package examples.com.examples;

/**
 * Created by Rajesh on 14-May-17.
 */

public class HttpsExample {

    //libraries needed
    compile files('libs/httpclient-4.3.6.jar')
    compile files('libs/httpmime-4.3.6.jar')
    compile files('libs/httpcore-4.3.3.jar')



    JSONObject object = new JSONObject();
                        object.put("", "");
    OBJECT = object.toString();

   new sendForStafCheck().execute();


    //API class
    private class sendForStafCheck extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                result = getInputStream(OBJECT);
                //result = streamToString(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }


    private String getInputStream(String request) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, URISyntaxException {
        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(HttpsExample.this.getAssets().open("ssl.cer"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
               /* HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                hv.verify(hostname, session);*/
                    return true;
                }
            };
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("Url here");
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            urlConnection.setHostnameVerifier(hostnameVerifier);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            os.writeBytes(request);

            int responseCode = urlConnection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            // Read Server Response
            while ((inputLine = in.readLine()) != null) {
                // Append server response in string
                response.append(inputLine);
            }
            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

    }
}
