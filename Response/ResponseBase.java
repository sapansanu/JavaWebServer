/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import webserver.Resource;
import static webserver.Resource.Alias;
import static webserver.Resource.uri;
import webserver.ResponseFactory;

/**
 *
 * @author Sapan
 */
public class ResponseBase {

    public int code;
    public String version, reason_phrase, write, body = "";
    public byte[] byteData;
    public String[] commands = new String[3];
    public static HashMap<Integer, String> response_codes = new HashMap<Integer, String>();
    public HashMap<String, String> default_headers = new HashMap<String, String>();
    String currentTime = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(Calendar.getInstance().getTime());

    public ResponseBase(Resource resource, int code_option) {
        response_codes.put(200, "OK");
        response_codes.put(201, "Successfully Created");
        response_codes.put(304, "Not Modified");
        response_codes.put(400, "Bad Request");
        response_codes.put(401, "Unauthorized");
        response_codes.put(403, "Forbidden");
        response_codes.put(404, "Not Found");
        response_codes.put(500, "Internal Server Error");

        default_headers.put("Date", currentTime);
        default_headers.put("Server", "Sapan & Greg CSC867 server");
        for (HashMap.Entry<Integer, String> e : response_codes.entrySet()) {
            if (code_option == e.getKey()) {
                code = e.getKey();
                reason_phrase = e.getValue();
                break;
            }
        }
        version = ResponseFactory.DEFAULT_HTTP_VERSION;
    }

    public String writeString() {
        write = "";
        write = createStatusLine() + "\r\n";
        for (HashMap.Entry<String, String> e : default_headers.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();
            write = write + key + " : " + value + '\n';
        }
        write = write + "\r\n" + getBody();
        return write;
    }

    public String getBody() {
        return body;
    }

    public String createStatusLine() {

        return version + " " + code + " " + reason_phrase;
    }

}
