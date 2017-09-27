/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Response;

import webserver.Resource;

/**
 *
 * @author Sapan
 */
public class Forbidden extends ResponseBase {

    public Forbidden(Resource resource, int code_option) {
        super(resource, 403);
        body = "<h1> 403 </h1> <h2> Forbidden access </h2>";
        default_headers.put("Content-Length", String.valueOf(body.length()));
        default_headers.put("Content-Type", "text/html");
    }

    public String getBody() {
        return body;
    }

}
