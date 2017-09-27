/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import Config.HttpdConf;
import Config.MimeTypes;
import Response.ResponseBase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.net.Socket;

/**
 *
 * @author Sapan
 */
public class Worker implements Runnable {

    PrintWriter out;
    BufferedReader in;
    Socket clientSocket = null;
    MimeTypes mime_object;
    HttpdConf httpd_object;

    Worker(Socket clientSocket, HttpdConf httpd_object, MimeTypes mime_object) {
        this.clientSocket = clientSocket;
        this.httpd_object = httpd_object;
        this.mime_object = mime_object;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Request request = new Request(in);
            Resource resource = new Resource(request, httpd_object.Aliases, httpd_object.ScriptAliases);
            ResponseFactory responseFactory = new ResponseFactory(resource);
            ResponseBase response = responseFactory.create(resource);
            if (response.byteData != null) { //checking if requested file has images
                clientSocket.getOutputStream().write(response.writeString().getBytes());
                clientSocket.getOutputStream().write(response.byteData);
                clientSocket.getOutputStream().flush();
            } 
            else {
                    out.print(response.writeString());
                    out.flush();
                }
            out.close();
            }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
