/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import Config.MimeTypes;
import webserver.Resource;
import webserver.ResponseFactory;

/**
 *
 * @author Sapan
 */
public class Success extends ResponseBase {

    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    String path, extension;
    byte[] file_content;

    public Success(Resource resource, int code_option) throws FileNotFoundException, IOException {
        super(resource, 200);
        path = resource.resolved_path;

        //reading file content
        File file = new File(path);
        if (file.exists()) {
            FileInputStream httpd = new FileInputStream(file);
            file_content = new byte[(int) file.length()];
            httpd.read(file_content);
            httpd.close();
            //body = new String(file_content, "UTF-8");
            byteData = file_content;

        }
        String extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        default_headers.put("Content-Type", MimeTypes.mime_types.get(extension));
        default_headers.put("Last-Modified", sdf.format(file.lastModified()));
        default_headers.put("Content-Length", String.valueOf(byteData.length));
        if (default_headers.containsKey("Content-Type")) {     // //add additional data to headers
            if (default_headers.get("Content-Type").equals("")) {
                default_headers.put("Content-Type", "text/plain");
            }
        }
    }

}

/*public void handleScript(Resource resource) throws FileNotFoundException, IOException {
        path = resource.resolved_path;
        File file = new File(path);
        if(file.exists()) {
        FileInputStream httpd = new FileInputStream(file);
        file_content = new byte[(int) file.length()];        
        httpd.read(file_content);
        httpd.close();
        body = new String(file_content, "UTF-8");
        String[] content = body.split("\\r?\\n"); //splitting by new line
        for(int i=0;i<1;i++) {
            //System.out.println(content[i]+" A");
            commands = content[i].split("\\s+");   //splitting by space
            try {
            String part1 = commands[0];
            String part2 = commands[1];
            part1 = part1.substring(2, part1.length());
            commands[2] = path;
            }
        
        catch(Exception e) {
            }
        }

        }
    }*/
