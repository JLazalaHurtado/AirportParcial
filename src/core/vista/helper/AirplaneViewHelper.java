/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.vista.helper;

import core.modelo.Plane;
import core.modelo.storage.StoragePlane;
import javax.swing.JComboBox;

public class AirplaneViewHelper {
    
    public static void loadFlightIntoComboBox(JComboBox<String> comboBox) {
        StoragePlane storage = StoragePlane.getInstance();
        for (Plane p : storage.getAllPlanes()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

}
