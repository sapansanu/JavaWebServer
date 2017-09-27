/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Sapan
 */
public class htaccess {

    public static String auth_user_file, auth_name, auth_type, require;

    public htaccess(String htaccess_content) {
        String[] content = htaccess_content.split("\\r?\\n"); //splitting by new line
        for (int i = 0; i < content.length; i++) {
            String[] parts = content[i].split("\\s+", 2);   //splitting by space
            try {
                String key = parts[0];
                if (parts[1].startsWith("\"")) {
                    parts[1] = parts[1].replaceAll("\"", "");    //removing quotes
                }
                String value = parts[1];
                switch (key) {
                    case "AuthUserFile":
                        auth_user_file = value;
                        //System.out.println(key+"  " +value);
                        break;
                    case "AuthType":
                        auth_name = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "AuthName":
                        auth_type = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "Require":
                        require = value;
                        //System.out.println(key+"  "+value);
                        break;
                    default:
                        System.out.println("Invalid");
                }
            } catch (Exception e) {

            }
        }
    }

    public Boolean isAuthenticated(String encrypted_string) throws FileNotFoundException, IOException {
        if (htpasswd.isAuthorized(encrypted_string)) {
            File htpasswd_file = new File("/Config/.htpasswd");
            if (htpasswd_file.exists()) {
                FileInputStream htpswd = new FileInputStream(htpasswd_file);
                byte[] htpasswd_data = new byte[(int) htpasswd_file.length()];
                htpswd.read(htpasswd_data);
                htpswd.close();
                String htpasswd_content = new String(htpasswd_data, "UTF-8");
                htpasswd htpasswd_object = new htpasswd(htpasswd_content);
                return htpasswd.isAuthenticated(encrypted_string);
            } else {
                System.out.print("Could not find file at " + auth_user_file);
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean isAuthorized(String encrypted_string) throws FileNotFoundException, IOException {
        File htpasswd_file = new File("/Config/.htpasswd");
        FileInputStream htpswd = new FileInputStream(htpasswd_file);
        byte[] htpasswd_data = new byte[(int) htpasswd_file.length()];
        htpswd.read(htpasswd_data);
        htpswd.close();
        String htpasswd_content = new String(htpasswd_data, "UTF-8");
        htpasswd htpasswd_object = new htpasswd(htpasswd_content);
        return htpasswd.isAuthorized(encrypted_string);
    }
}
