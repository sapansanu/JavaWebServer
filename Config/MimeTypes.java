/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sapan
 */
public class MimeTypes {

    public static HashMap<String, String> mime_types = new HashMap<String, String>();

    public MimeTypes(String mime_content) {
        //System.out.print(mime_content);
        String[] content = mime_content.split("\\r?\\n");
        Pattern pat = Pattern.compile("#(\\w+|\\W+)");
        for (int i = 0; i < content.length; i++) {
            Matcher matcher = pat.matcher(content[i]);
            if (matcher.find()) {
                continue;
            } else {

                String[] parts = content[i].split("\\s+", 2);
                String value = parts[0];
                try {
                    if (parts.length == 2) {
                        String[] ext = parts[1].split("\\s+");
                        for (int j = 0; j < ext.length; j++) {
                            mime_types.put(ext[j], value);
                            //System.out.println(ext[j]+" "+value);

                        }

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
        for (HashMap.Entry<String, String> e : mime_types.entrySet()) {
            String mime_key = e.getKey();
            String mime_value = e.getValue();
            //System.out.println(mime_key+"  "+mime_value);
        }
    }

}
