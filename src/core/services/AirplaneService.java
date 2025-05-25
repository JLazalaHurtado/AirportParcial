/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;
import core.modelo.Plane;
import core.modelo.storage.StoragePlane;
/**
 *
 * @author Juan Manuel
 */
public class AirplaneService {
    public static boolean addAirplane(Plane plane) {
        StoragePlane storage = StoragePlane.getInstance();
        return storage.addPlane(plane);
    }
}
