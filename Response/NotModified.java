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
public class NotModified extends ResponseBase {

    public NotModified(Resource resource, int code_option) {
        super(resource, 304);
        body = "<h1> 304 </h1> <h2>Not modified</h2>";
        default_headers.put("Content-Type", "text/html");
        default_headers.put("Content-Length", String.valueOf(body.length()));   // //add additional data to headers
    }

}
