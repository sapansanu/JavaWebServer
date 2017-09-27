/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import Config.MimeTypes;
import Config.HttpdConf;
import Config.htaccess;
import Config.htpasswd;
import Response.ResponseBase;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference.Metadata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Main WebServer class
 * @author Sapan
 */
public class WebServer {
    public static int DEFAULT_PORT = 8080;
    public static String  SERVER_ROOT = null;
    public static String DOCUMENT_ROOT = null;
    public static String LOG_FILE = null;
    public static String abc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        File config_file = new File("/Config/httpd.conf");
        FileInputStream httpd = new FileInputStream(config_file);
        byte[] httpd_data = new byte[(int) config_file.length()];
        httpd.read(httpd_data);
        httpd.close();
        String config_content = new String(httpd_data, "UTF-8");
        HttpdConf httpd_object = new HttpdConf(config_content);
            
        File mime_file = new File("/Config/mime.types");
        FileInputStream mime = new FileInputStream(mime_file);
        byte[] mime_data = new byte[(int) mime_file.length()];
        mime.read(mime_data);
        mime.close();
        String mime_content = new String(mime_data, "UTF-8");
        MimeTypes mime_object = new MimeTypes(mime_content);
        
        ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
        Socket clientSocket = null;
        while(true) {
        System.out.println("Server is now accepting connections from Port 8096");
        clientSocket = serverSocket.accept();
        Thread t = new Thread(new Worker(clientSocket, httpd_object, mime_object));
        t.start();
   
    }
    }
         
}
   
