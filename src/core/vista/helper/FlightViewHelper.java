/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.vista.helper;

import core.modelo.Flight;
import core.modelo.Location;
import core.modelo.storage.StorageFlight;
import javax.swing.JComboBox;

public class FlightViewHelper {

    public static void loadFlightIDsIntoComboBox(JComboBox<String> comboBox) {
        for (Flight p : StorageFlight.getInstance().getAllFlight()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

    public static void loadDepartureLocations(JComboBox<String> comboBox) {
        for (Location p : StorageFlight.getInstance().getAllDepartureLocations()) {
            comboBox.addItem(p.toString());
        }
    }

    public static void loadArrivalLocations(JComboBox<String> comboBox) {
        for (Location p : StorageFlight.getInstance().getAllDepartureLocations()) {
            comboBox.addItem(p.toString());
        }
    }

    public static void loadScaleLocations(JComboBox<String> comboBox) {
        for (Location p : StorageFlight.getInstance().getAllScaleLocations()) {
            if (p != null) {
                comboBox.addItem(p.toString());
            }
        }
    }
}

