/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.loader;

import core.modelo.Location;
import core.modelo.storage.StorageLocation;
import org.json.JSONArray;
import org.json.JSONObject;

public class LocationLoader {
  public static void loadLocationAndStorage(){
      JSONArray array = JsonLoader.load("core/data/locations.json");
      StorageLocation storage = StorageLocation.getInstance();
      
      for (int i = 0; i < array.length(); i++) {
          JSONObject object = array.getJSONObject(i);
          Location location = new Location(object.getString("airportId"),object.getString("airportName"),object.getString("airportCity"),object.getString("airportCountry"),object.getDouble("airportLatitude"),object.getDouble("airportLongitude"));
           storage.addLocation(location);
      }
  }      
}
