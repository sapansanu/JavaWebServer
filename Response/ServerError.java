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
public class ServerError extends ResponseBase {

    public ServerError(Resource resource, int code_option) {
        super(resource, 500);
        body = "<h1> 500</h1> <h2> Server Error</h2>";
        default_headers.put("Content-Length", String.valueOf(body.length()));    //add additional data to headers
        default_headers.put("Content-Type", "text/html");
    }

    public String getBody() {
        return body;
    }

}
