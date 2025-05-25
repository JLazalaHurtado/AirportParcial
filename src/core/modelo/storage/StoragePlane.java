/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.storage;

import core.modelo.Passenger;
import core.modelo.Plane;
import java.util.ArrayList;
import java.util.List;

public class StoragePlane {

    private static StoragePlane instance;
    private ArrayList<Plane> planes;

    public StoragePlane() {
        this.planes = new ArrayList<>();
    }

    public static StoragePlane getInstance() {
        if (instance == null) {
            instance = new StoragePlane();
        }
        return instance;
    }

  

    public Plane getPlane(String id) {
        for (Plane plane : this.planes) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return null;
    }

    public List<Plane> getAllPlanes() {
        return this.planes;
    }
    public boolean addPlane(Plane plane) {
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                return false;
            }
        }
        this.planes.add(plane);
        return true;
    }
}
