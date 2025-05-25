/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jlaza
 */
package core.controlador;

import core.modelo.Flight;
import core.modelo.Passenger;
import core.modelo.storage.StorageFlight;
import core.modelo.storage.StoragePassenger;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControllerTables {
    public  void showFlights(JTable table) {
        try {
        List<Flight> flights = StorageFlight.getInstance().getFlights();

        DefaultTableModel tm = new DefaultTableModel(
                new Object[]{"ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"}, 0);

        for (Flight flight : flights) {
            tm.addRow(new Object[]{flight.getId(),flight.getDepartureLocation().getAirportId(),flight.getArrivalLocation().getAirportId(),(flight.getScaleLocation() == null ? "-" : flight.getScaleLocation().getAirportId()),flight.getDepartureDate(),flight.calculateArrivalDate(), flight.getPlane().getId(), flight.getNumPassengers()});
        }

        table.setModel(tm);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }
    }
    public void showPassengers(JTable table) {
        try {
            List<Passenger> passengers = StoragePassenger.getInstance().getAllPassengers();
            DefaultTableModel tm = new DefaultTableModel(new Object []{"Id","Name","BirthDate","Age","Phone","Country","NumFlight"},0);
            for (Passenger passenger : passengers) {
                tm.addRow(new Object[]{passenger.getId(),passenger.getFullname(),passenger.getBirthDate(),passenger.calculateAge(),passenger.getPhone(),passenger.getCountry(),passenger.getNumFlights()});
            }
         table.setModel(tm);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }
  
    }

}
