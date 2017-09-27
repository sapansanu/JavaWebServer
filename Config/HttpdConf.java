/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.util.HashMap;
import webserver.WebServer;

/**
 *
 * @author Sapan
 */
public class HttpdConf {

    public HashMap<String, String> ScriptAliases = new HashMap<String, String>();
    public HashMap<String, String> Aliases = new HashMap<String, String>();

    public HttpdConf(String config_content) {

        //System.out.println(config_content);
        String[] content = config_content.split("\\r?\\n"); //splitting by new line
        for (int i = 0; i < content.length; i++) {
            //System.out.println(content[i]+" A");
            String[] parts = content[i].split("\\s+", 2);   //splitting by space
            try {
                String key = parts[0];
                parts[1] = parts[1].replaceAll("\"", "");   //removing quotes
                String value = parts[1];
                switch (key) {
                    case "ServerRoot":
                        WebServer.SERVER_ROOT = value;
                        //System.out.println(key+"  " +value);
                        break;
                    case "DocumentRoot":
                        value = value.substring(0, value.length() - 1);
                        WebServer.DOCUMENT_ROOT = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "Listen":
                        WebServer.DEFAULT_PORT = Integer.parseInt(value);
                        //System.out.println(key+"  "+value);
                        break;
                    case "LogFile":
                        WebServer.LOG_FILE = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "ScriptAlias":
                        String[] sa_parts = value.split("\\s+");
                        //sa_parts[1] = sa_parts[1].substring(1, sa_parts[1].length());
                        ScriptAliases.put(sa_parts[0], sa_parts[1]);
                        for (HashMap.Entry<String, String> e1 : ScriptAliases.entrySet()) {
                            String salias_key = e1.getKey();
                            String salias_value = e1.getValue();
                            //System.out.println(salias_key+"  "+salias_value);
                        }
                        break;
                    case "Alias":
                        String[] a_parts = value.split("\\s+");
                        //a_parts[1] = a_parts[1].substring(1, a_parts[1].length());
                        Aliases.put(a_parts[0], a_parts[1]);
                        for (HashMap.Entry<String, String> e2 : Aliases.entrySet()) {
                            String alias_key = e2.getKey();
                            String alias_value = e2.getValue();
                            //System.out.println(alias_key+"  "+alias_value);
                        }
                        break;
                    default:
                        System.out.println("Invalid");

                }
            } catch (Exception e) {
            }
        }
    }
}
