/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Response;

import java.util.HashMap;
import webserver.Resource;

/**
 *
 * @author Sapan
 */
public class NotFound extends ResponseBase {

    public NotFound(Resource resource, int code_option) {
        super(resource, 404);
        body = "<h1>404</h1> <h2>Page not found</h2>";
        default_headers.put("Content-Length", String.valueOf(body.length()));
        default_headers.put("Content-Type", "text/html");
    }

    public String getBody() {
        return body;
    }

}
