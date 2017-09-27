/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static webserver.Request.uri;

/**
 *
 * @author Sapan
 */
public class Resource {

    public static Boolean isProtected = false;
    public static Boolean containsScriptAliased = false;
    public static Boolean hasExtension = false;
    public static String uri, resolved_path, abs_path;
    public static HashMap<String, String> Script_Aliases = new HashMap<String, String>();
    public static HashMap<String, String> Alias = new HashMap<String, String>();
    Request request;

    Resource(Request request, HashMap<String, String> Aliases, HashMap<String, String> ScriptAliases) {
        uri = request.uri;
        this.request = request;
        for (HashMap.Entry<String, String> e : Aliases.entrySet()) {
            Alias.put(e.getKey(), e.getValue());
        }

        for (HashMap.Entry<String, String> e : ScriptAliases.entrySet()) {
            Script_Aliases.put(e.getKey(), e.getValue());
        }
        resolved_path = resolve(uri);
        abs_path = findAbsolutePath(resolved_path);
        //System.out.println("Absolute path " + abs_path);
        isProtected = isProtected(abs_path);
    }

    public static String findAbsolutePath(String resolved_path) {
        File file = new File(resolved_path);
        if (file.exists()) {
            String[] resolved_path_parts = resolved_path.split("/");
            abs_path = resolved_path.replace(resolved_path_parts[resolved_path_parts.length - 1], "");
            return abs_path;
        } else {
            String dir_index = resolved_path + "\\index.html";
            String[] dir_index_parts = dir_index.split("/");
            abs_path = dir_index.replace(dir_index_parts[dir_index_parts.length - 1], "");
            return abs_path;
        }

    }

    public static Boolean isProtected(String abs_path) {

        File file = new File(abs_path + "\\.htacess");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static String resolve(String uri) {  //check for Alias and ScriptAlias and resolve path
        //check for aliases
        String sub_path = "";
        String extension = uri.substring(uri.lastIndexOf(".") + 1, uri.length());
        if (!extension.isEmpty()) {
            hasExtension = true;
        }
        for (HashMap.Entry<String, String> e1 : Alias.entrySet()) {
            if (uri.indexOf(e1.getKey()) != -1) {
                sub_path = uri.replaceAll(e1.getKey(), e1.getValue());
            }

        }
        //check for scriptAliases
        for (HashMap.Entry<String, String> e2 : Script_Aliases.entrySet()) {
            if (uri.indexOf(e2.getKey()) != -1) {
                containsScriptAliased = true;
                sub_path = uri.replaceAll(e2.getKey(), e2.getValue());
            }

        }
        if (!sub_path.equals("")) {
            resolved_path = sub_path;
        } else {
            resolved_path = WebServer.DOCUMENT_ROOT + uri;
        }
        //System.out.println("Resolved path:" + resolved_path);
        return resolved_path;
    }

    public static Boolean isScriptAliased() {
        if ((containsScriptAliased == true)) {
            return true;
        } else {
            return false;
        }
    }

}
