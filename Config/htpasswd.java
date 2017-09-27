/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

/**
 *
 * @author Sapan
 */
public class htpasswd {

    static String username, received_passwd, encrypted_passwd, sha1;
    public static HashMap<String, String> authorized_users = new HashMap<String, String>();

    public htpasswd(String htpasswd_content) {
        String[] content = htpasswd_content.split("\\r?\\n"); //splitting by new line
        for (int i = 0; i < content.length; i++) {
            String[] parts = content[i].split(":", 2);   //splitting by colon
            authorized_users.put(parts[0], parts[1]);
        }

    }

    public static Boolean isAuthenticated(String encrypted_string) throws UnsupportedEncodingException {
        byte[] decodedValue = Base64.getDecoder().decode(encrypted_string);
        String decodedString = new String(decodedValue, "UTF-8");
        String[] parts = decodedString.split(":", 2);
        username = parts[0];
        if (parts.length == 2) {
            received_passwd = parts[1];
        }
        encrypted_passwd = authorized_users.get(username);
        encrypted_passwd = encrypted_passwd.replace("{SHA}", "");
        try {
            received_passwd = new sun.misc.BASE64Encoder().encode(java.security.MessageDigest.getInstance("SHA1").digest(received_passwd.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encrypted_passwd.equals(sha1);

    }

    public static Boolean isAuthorized(String encrypted_string) throws UnsupportedEncodingException {
        byte[] decodedValue = Base64.getDecoder().decode(encrypted_string);
        String decodedString = new String(decodedValue, "UTF-8");
        String[] parts = decodedString.split(":", 2);
        username = parts[0];
        return authorized_users.containsKey(username);
    }

}
