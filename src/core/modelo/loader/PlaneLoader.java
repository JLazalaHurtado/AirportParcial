/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.loader;

import core.modelo.Plane;
import core.modelo.storage.StoragePlane;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jlaza
 */
public class PlaneLoader {

    public static void loadPlanesAndStorage() {
        JSONArray array = JsonLoader.load("core/data/planes.json");

        StoragePlane storage = StoragePlane.getInstance();

        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);

            Plane plane = new Plane(object.getString("id"),object.getString("brand"),object.getString("model"),object.getInt("maxCapacity"),object.getString("airline"));
            storage.addPlane(plane); 
        }
    }
}
