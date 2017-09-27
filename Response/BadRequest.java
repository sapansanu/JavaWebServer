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
public class BadRequest extends ResponseBase {

    public BadRequest(Resource resource, int code_option) {
        super(resource, 400);
        body = "<h1> 400</h1> <h2>Bad request</h2>";
        default_headers.put("Content-Length", String.valueOf(body.length()));
        default_headers.put("Content-type", "text/html");
    }

    public String getBody() {
        return body;
    }
}
