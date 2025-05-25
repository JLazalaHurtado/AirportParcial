/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.storage;

import core.modelo.Location;
import java.util.ArrayList;
import java.util.List;

public class StorageLocation {

    private static StorageLocation instance;
    private ArrayList<Location> locations;

    private StorageLocation() {
        this.locations = new ArrayList<>();
    }

    public static StorageLocation getInstance() {
        if (instance == null) {
            instance = new StorageLocation();
        }
        return instance;
    }

    public boolean addLocation(Location location) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }

    public Location getLocation(String id) {
     
            for (Location location : this.locations) {
                if (location.getAirportId().equals(id)) {
                    return location;
                }
            }
            return null;
    }
    public List<Location> getAllLocation(){
        return orderLocationById();
    }
    public List<Location> orderLocationById(){
        List<Location> temporalList = new ArrayList<>(locations);
                for (int i = 0; i < temporalList.size(); i++) {
            for (int j = 0; j < temporalList.size() - i - 1; j++) {
              Location currentId = temporalList.get(j);
              Location nextId = temporalList.get(j+1);
              if(currentId.getAirportId().compareTo(nextId.getAirportId())>0) {
                temporalList.set(j, nextId);
                temporalList.set(j+1, currentId);
              }
            }

        }
        
        return temporalList;
    }
}
