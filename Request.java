/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sapan
 */
public class Request {

    public static String http_method, uri, http_version, get_params, body = "";
    HashMap<String, Boolean> http_verbs = new HashMap<String, Boolean>();
    HashMap<String, String> parameters = new HashMap<String, String>();
    HashMap<String, String> header = new HashMap<String, String>();
    String initialLine, line;
    public int content_length;

    public Request(BufferedReader in) throws IOException {
        http_verbs.put("PUT", true);
        http_verbs.put("GET", true);
        http_verbs.put("POST", true);
        http_verbs.put("HEAD", true);
        http_verbs.put("DELETE", true);
        initialLine = in.readLine();
        //System.out.println(initialLine);
        parseInitialLine(initialLine);
        //System.out.println(isFirstLineValid());
        line = in.readLine();
        //System.out.println("Headers:");
        while (!line.equals("")) {
            parseHeaderLine(line);
            line = in.readLine();
        }
        //check if we have a content-length header
        if (header.containsKey("CONTENT_LENGTH")) {
            if (!header.get("CONTENT_LENGTH").equals("0")) {
                for (int i = 0; i < Integer.parseInt(header.get("CONTENT_LENGTH")); i++) {
                    line = in.readLine();
                    body = parseBodyLine(line);
                }
            }
        }

    }

    public String getRemoteUser() throws UnsupportedEncodingException {
        if (!header.get("Authorization").equals("")) {
            String encoded_string = header.get("Authorization");
            String[] encoded_parts = encoded_string.split("\\s+");
            byte[] decodedValue = Base64.getDecoder().decode(encoded_string);
            String decodedString = new String(decodedValue, "UTF-8");
            String[] decoded_parts = decodedString.split(":");
            return decoded_parts[0];
        } else {
            return "";
        }
    }

    public void parseInitialLine(String initialLine) {
        try {
            String[] parts = initialLine.split("\\s+");
            http_method = parts[0];
            uri = parts[1];
            http_version = parts[2];
            if (http_method.equals("GET") && uri.contains("?")) {
                String[] http_parts = uri.split("\\?");
                uri = http_parts[0];
                if (http_parts.length == 2) {
                    System.out.println("Http method = " + http_method + "; Uri = " + uri + "; Http version = " + http_version + "; Query string = " + http_parts[1]);
                    parseGetParams(http_parts[1]);

                } else {

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void parseHeaderLine(String line) {
        try {
            String[] hdr_parts = line.split(": ");
            if (hdr_parts.length == 2) {
                header.put(hdr_parts[0], hdr_parts[1]);
                //System.out.println(hdr_parts[0] + " : " + hdr_parts[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String parseBodyLine(String line) {
        body = body + line;
        return body = body + line;
    }

    public void parseGetParams(String get_params) {
        try {
            String[] param_parts = get_params.split("\\&");
            for (int i = 0; i < param_parts.length; i++) {
                String[] params = param_parts[i].split("\\=");
                parameters.put(params[0], params[1]);
                //System.out.println("Key = " + params[0] + "\nValue = " + params[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isFirstLineValid() {
        Pattern pat = Pattern.compile("/(\\w+|\\W+)");
        Matcher matcher = pat.matcher(uri);

        if ((http_version.equals("HTTP/1.1") || http_version.equals("HTTP/1.0")) && http_verbs.containsKey(http_method) && matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

}
