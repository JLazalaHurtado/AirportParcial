/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.loader;

import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author jlaza
 */
public class JsonLoader {

    public static JSONArray load(String path) {
       InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream(path);
        JSONTokener tokener = new JSONTokener(is);
     return new JSONArray(tokener);   
    }
}
