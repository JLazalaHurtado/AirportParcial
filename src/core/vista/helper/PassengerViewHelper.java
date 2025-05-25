/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.vista.helper;

import core.modelo.Passenger;
import core.modelo.storage.StoragePassenger;
import javax.swing.JComboBox;

/**
 *
 * @author Juan Manuel
 */
public class PassengerViewHelper {
    public static void loadPassengerIdsIntoComboBox(JComboBox<String> comboBox) {
        StoragePassenger storage = StoragePassenger.getInstance();
        for (Passenger p : storage.getAllPassengers()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

}

