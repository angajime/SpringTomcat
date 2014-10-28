package hello;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Curl {
    private String username;
    private String password;
    private URL url;
    private String body;
    private String hdr = "WWW-Authenticate";

    public Curl(String stringUrl, String name, String pass, String... args) {
        username = name;
        password = pass;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            str.append(args[i]);
            if (i != args.length - 1) {
                str.append('&');
            }
        }
        body = str.toString();
        try {
            url = new URL("10.95.20.162:8181/" + stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getBody(String... params) {
        try {
            //Creamos el esquema que se utilizará para procesar el challenge.
            DigestScheme md5Auth = new DigestScheme();
            //Realizamos una petición vacia contra el servidor para obtener la informacion digest.
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost hg = new HttpPost(url.toURI());
            HttpResponse badResponse = client.execute(hg); //Ejecutamos la petición
            //EntityUtils.consumeQuietly(badResponse.getEntity()); //Workaround to avoid error when executing client.execute next time
            String strContent = inputStreamToString(badResponse.getEntity().getContent()); // Para visualizar el badResponse
            if (badResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                if (badResponse.containsHeader(hdr)) {
                    //Obtenemos el challenge recibido en la respuesta
                    Header challenge = badResponse.getHeaders(hdr)[0];
                    //Procesamos dicha cabecera.
                    md5Auth.processChallenge(challenge);
                    //Creamos la solucion y la mandamos en la cabecera de nuestra verdadera peticion
                    Header solution = md5Auth.authenticate(
                            new UsernamePasswordCredentials(username, password),
                            new BasicHttpRequest(HttpPost.METHOD_NAME,
                                    new URL(url.toString()).getPath()));
                    Header contentType = new BasicHeader("Content-Type", "application/vnd.ericsson.m2m.SB+xml;version=1.0");
                    HttpPost httpPostFinal = new HttpPost(url.toURI());
                    Header[] headers = new Header[2];
                    headers[0] = contentType;
                    headers[1] = solution;
                    httpPostFinal.setHeaders(headers);
                    //Creada la cabecera, establecemos el body (payload en este caso)
                    httpPostFinal.setEntity(new StringEntity(body));
                    //Ejecutamos y recibimos la respuesta.
                    HttpResponse goodResponse = client.execute(httpPostFinal);
                    body = inputStreamToString(goodResponse.getEntity().getContent());
                }
            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedChallengeException e) {
            e.printStackTrace();
        }
        return body;
    }

    private String inputStreamToString(InputStream is) {
        String line;
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null)
                total.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total.toString();
    }
}
