/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

/**
 *
 * @author Juan Manuel
 */
import core.modelo.Location;
import core.modelo.storage.StorageLocation;

public class LocationService {
    public static boolean addLocation(Location location) {
        StorageLocation storage = StorageLocation.getInstance();
        return storage.addLocation(location);
    }
}
 