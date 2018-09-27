package org.simple.autorun.command;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.simple.autorun.common.LogWrapper;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by sliang on 10/25/17.
 */
public class HttpCommand {

    public static String httpGet(String url) {
        return httpRequest("GET", url, null, null, null);
    }

    public static String httpPost(String url, Map headers, String body) {
        return httpRequest("POST", url, null, headers, body);
    }

    public static String httpRequest(String method, String url, Map<String, Object> parameters, Map<String, Object> headers, String body) {

        // Build URI
        URI uri = buildUri(url, parameters);

        // Build method
        boolean isPostMethod = method.equalsIgnoreCase("POST");
        Request request = isPostMethod ? Request.Post(uri) : Request.Get(uri);

        // Build headers
        if (headers != null) {
            for (Object key : headers.keySet()) {
                Object value = headers.get(key);
                if (value != null) {
                    request.setHeader(key.toString(), value.toString());
                }
            }
        }

        // Build body with content type
        if (isPostMethod) {
            request = request.bodyString(body, buildContentType(body));
        }

        // Send request
        try {
            return request.execute().returnContent().asString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            LogWrapper.current().fatal(e);
            return null;
        }
    }

    private static URI buildUri(String url, Map<String, Object> parameters) {
        try {
            URIBuilder builder = new URIBuilder(url);

            if (parameters != null) {
                for (String key : parameters.keySet()) {
                    Object value = parameters.get(key);
                    if (value != null) {
                        builder.setParameter(key, value.toString());
                    }
                }
            }

            return builder.build();
        } catch (Exception e) {
            LogWrapper.current().fatal(e);
            return null;
        }
    }

    private static ContentType buildContentType(String body) {
        ContentType contentType = null;

        if(body.matches("^<\\?xml(.|\\n)+>$")) { // xml
            contentType = ContentType.TEXT_XML;
        } else if (body.matches("^[{\\[](.|\\n)+[}\\]]$")) { // json
            contentType = ContentType.APPLICATION_JSON;
        }

        return contentType;
    }
}
