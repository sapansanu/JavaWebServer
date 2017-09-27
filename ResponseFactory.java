/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import Response.Forbidden;
import Config.htaccess;
import static Config.htaccess.require;
import Response.BadRequest;
import Response.NotFound;
import Response.NotModified;
import Response.ResponseBase;
import Response.ServerError;
import Response.Success;
import Response.SuccessfullyCreated;
import Response.Unauthorized;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Sapan
 */
public class ResponseFactory {
    public static Boolean isAuthenticated = true, isAuthorized;
    public static String path, encrypted_string, DEFAULT_HTTP_VERSION = "HTTP/1.1";
    String fileLastModified, headerLastModified;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    Date fileDate, headerDate;
    
    public ResponseFactory(Resource resource) throws IOException {
        try {
        File file = new File(resource.resolved_path);
        fileLastModified = sdf.format(file.lastModified());
        fileDate = sdf.parse(fileLastModified);
        if(resource.request.header.containsKey("If-Modified-Since")) {
        headerLastModified = resource.request.header.get("If-Modified-Since");
        headerDate = sdf.parse(headerLastModified);
        System.out.println(fileDate+ " "+ headerDate);
        }
        try {
        isbadRequest(resource);
        }
        catch (Exception e) {
            new BadRequest(resource, 400);
        }}
        catch (Exception e) {
            new ServerError(resource, 500);
        }
    }
    
    public ResponseBase create(Resource resource) throws IOException {
        File file = new File(resource.resolved_path);
         if(resource.request.http_method.equals("PUT")) {
            return new SuccessfullyCreated(resource, 201); 
        }
        else {
        if(!file.exists()) {
            return new NotFound(resource, 404);
        }
        else {
            isAuthorized = isAuthorized(resource);
            if(isAuthorized) {  //if authorized, check if authenticated
                if(isAuthenticated) {   
                return getSuccessResponse(resource);
                }
                else {
                    return new Forbidden(resource, 403);
                }
            }
            else {
                return new Unauthorized(resource, 401);
            }
        }
        }
    }
    
    public ResponseBase getSuccessResponse(Resource resource) throws IOException {
        if(resource.request.http_method.equals("PUT")) {
            return new SuccessfullyCreated(resource, 201); 
        }
        else {
        
        if(resource.request.header.containsKey("If-Modified-Since")&&(!resource.isScriptAliased())) {
            System.out.println("Enter date in this format: EEE, dd MMM yyyy HH:mm:ss zzz");
            if(fileDate.before(headerDate)){
                return new NotModified(resource, 304); 
            }
            else {
                return new Success(resource, 200);
            }
        }
        else {
            return new Success(resource, 200);
        }
        }
          
    }
    
    public static Boolean isAuthorized(Resource resource) throws FileNotFoundException, IOException {
        File htaccess_file = new File("/Config/.htacess");
        FileInputStream htaccess = new FileInputStream(htaccess_file);
        byte[] htaccess_data = new byte[(int) htaccess_file.length()];
        htaccess.read(htaccess_data);
        htaccess.close();
        String htaccess_content = new String(htaccess_data, "UTF-8");
        htaccess htaccess_object = new htaccess(htaccess_content);
        
        if(!resource.isProtected) {
            isAuthenticated = true;
            return true;
        }
        else {
        if(htaccess_object.require.equals("valid-user")) {
            if(!resource.request.header.containsKey("Authorization")) {
                 return false;
            }
        else {
            String[] encrypted_string_parts = resource.request.header.get("Authorization").split("\\s+");
            encrypted_string = encrypted_string_parts[encrypted_string_parts.length-1];
            if(htaccess_object.isAuthorized(encrypted_string)) {
                if(htaccess_object.isAuthenticated(encrypted_string)){
                    isAuthenticated = true;
                }
                else {
                    isAuthenticated = false;
                }
                return true;
            }
            else {
                return false;
            }
        }
        }
        else {
            isAuthenticated = true;
            return true;
        }
        }
    }
    
    
    public Boolean isbadRequest(Resource resource) {
        if(!resource.request.isFirstLineValid()) {
            throw new BadRequestException("Bad request exception");
        }
        else {
            return true;
        }
    }
    
     public class BadRequestException extends RuntimeException
    {
        public BadRequestException(String message)
        {
        super(message);
        }
    }
}
