package hello;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
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

    private enum Method {
        GET,
        POST,
        PUT,
        DELETE
    }

    /**
     * This is the constructor for the Curl class. In this constructor you must provide the URL for the DM service, the username and user password
     * and whatever you want to send in the body. Take into account that you don't need to add the location of the DM server. So instead of
     * writing "http://dmserver:8181/m2m/provisioning/..." you only need to write "m2m/provisioning/...".
     * @param stringUrl The URL
     * @param name The user name
     * @param pass The user password
     * @param args The arguments for the petition
     */

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
            url = new URL("http://10.95.20.162:8181/" + stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method executes a POST petition to the DM server and returns an HTML webpage with the result of the petition.
     * If it returns a HTTP 401 then probably the user and password combination is invalid.
     * @param channel The channel (Northbound, Southbound...)
     * @return Answer from server
     */

    public String post(DMChannel channel) {
        return execute(Method.POST, channel);
    }

    /**
     * This method executes a GET petition to the DM server and returns an HTML webpage with the result of the petition.
     * If it returns a HTTP 401 then probably the user and password combination is invalid.
     * @param channel The channel (Northbound, Southbound...)
     * @return Answer from server
     */

    public String get(DMChannel channel) {
        return execute(Method.GET, channel);
    }

    /**
     * This method executes a PUT petition to the DM server and returns an HTML webpage with the result of the petition.
     * If it returns a HTTP 401 then probably the user and password combination is invalid.
     * @param channel The channel (Northbound, Southbound...)
     * @return Answer from server
     */

    public String put(DMChannel channel) {
        return execute(Method.PUT, channel);
    }

    /**
     * This method executes a DELETE petition to the DM server and returns an HTML webpage with the result of the petition.
     * If it returns a HTTP 401 then probably the user and password combination is invalid.
     * @param channel The channel (Northbound, Southbound...)
     * @return Answer from server
     */

    public String delete(DMChannel channel) {
        return execute(Method.DELETE, channel);
    }

    /**
     * This method executes the petition to the DM server and returns an HTML webpage with the result of the petition.
     * If it returns a HTTP 401 then probably the user and password combination is invalid.
     * @param channel The channel (Northbound, Southbound...)
     * @return Answer from server
     */

    private String execute(Method method, DMChannel channel) {
        try {
            //Creamos el esquema que se utilizará para procesar el challenge.
            DigestScheme md5Auth = new DigestScheme();
            //Realizamos una petición vacia contra el servidor para obtener la informacion digest.
            HttpClient client = HttpClientBuilder.create().build();
            HttpUriRequest hg = getHttpRequest(method);
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
                            new BasicHttpRequest(hg.getMethod(),
                                    new URL(url.toString()).getPath()));
                    Header contentType = getHeader(channel);
                    HttpUriRequest httpPostFinal = getHttpRequest(method);
                    Header[] headers = new Header[2];
                    headers[0] = contentType;
                    headers[1] = solution;
                    httpPostFinal.setHeaders(headers);
                    //Creada la cabecera, establecemos el body (payload en este caso)
                    if (method.equals(Method.POST) || method.equals(Method.PUT)) {
                        ((HttpEntityEnclosingRequestBase)httpPostFinal).setEntity(new StringEntity(body));
                    }
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

    private HttpUriRequest getHttpRequest(Method method) throws URISyntaxException {
        if (method.equals(Method.GET)) {
            return new HttpGet(url.toURI());
        } else if (method.equals(Method.POST)) {
            System.out.println("URL: " + url);
            System.out.println("URL.toURI: " + url.toURI());
            return new HttpPost(url.toURI());
        } else if (method.equals(Method.PUT)) {
            return new HttpPut(url.toURI());
        } else if (method.equals(Method.DELETE)) {
            return new HttpDelete(url.toURI());
        } else {
            throw new RuntimeException("Invalid Method");
        }
    }

    private BasicHeader getHeader(DMChannel channel) {
        if (channel.equals(DMChannel.Northbound)) {
            return new BasicHeader("Accept", "application/vnd.ericsson.m2m.NB+xml;version=1.0");
        } else if (channel.equals(DMChannel.Southbound)) {
            return new BasicHeader("Content-Type", "application/vnd.ericsson.m2m.SB+xml;version=1.0");
        } else if (channel.equals(DMChannel.Provisioning)) {
            return new BasicHeader("Content-type", "application/x-www-form-urlencoded");
        } else {
            throw new RuntimeException("Invalid DMChannel");
        }
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
