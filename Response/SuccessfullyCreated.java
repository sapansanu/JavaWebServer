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
public class SuccessfullyCreated extends ResponseBase {

    public SuccessfullyCreated(Resource resource, int code_option) {
        super(resource, 201);
        body = "<h1> 201 </h1> <h2>Successfully created</h2>";
        default_headers.put("Content-Type", "text/html");    //add additional data to headers
        default_headers.put("Content-Length", String.valueOf(body.length()));
    }

    public String getBody() {
        return body;
    }
}
