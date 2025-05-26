/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jlaza
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Flight;
import core.modelo.Location;
import core.modelo.Passenger;
import core.modelo.Plane;
import core.modelo.storage.StorageFlight;
import core.modelo.storage.StorageLocation;
import core.modelo.storage.StoragePassenger;
import core.modelo.storage.StoragePlane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControllerTables {

    public void showFlights(JTable table) {
        try {
            List<Flight> flights = StorageFlight.getInstance().getFlights();

            DefaultTableModel create = new DefaultTableModel(
                    new Object[]{"ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"}, 0);

            for (Flight flight : flights) {
                create.addRow(new Object[]{flight.getId(), flight.getDepartureLocation().getAirportId(), flight.getArrivalLocation().getAirportId(), (flight.getScaleLocation() == null ? "-" : flight.getScaleLocation().getAirportId()), flight.getDepartureDate(), flight.calculateArrivalDate(), flight.getPlane().getId(), flight.getNumPassengers()});
            }

            table.setModel(create);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }
    }

    public void showPassengers(JTable table) {
        try {
            List<Passenger> passengers = StoragePassenger.getInstance().getAllPassengers();
            DefaultTableModel create = new DefaultTableModel(new Object[]{"Id", "Name", "BirthDate", "Age", "Phone", "Country", "NumFlight"}, 0);
            for (Passenger passenger : passengers) {
                create.addRow(new Object[]{passenger.getId(), passenger.getFullname(), passenger.getBirthDate(), passenger.calculateAge(), passenger.getPhone(), passenger.getCountry(), passenger.getNumFlights()});
            }
            table.setModel(create);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }

    }

    public Response showMyFlights(JTable table, String passengerId) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Id", "Departure Date", "Arrival Date"}, 0
        );

        try {
            if (passengerId == null || passengerId.isEmpty()) {
                table.setModel(model);
                return new Response("Id inválido, no puede estar vacío", Status.BAD_REQUEST);
            }

            long passengerIdAux;
            try {
                passengerIdAux = Long.parseLong(passengerId.trim());
            } catch (NumberFormatException e) {
                table.setModel(model);
                return new Response("Id invalido, debe ser numerico", Status.BAD_REQUEST);
            }

            List<Passenger> passengers = StoragePassenger.getInstance().getAllPassengers();
            Passenger passenger = null;

            for (Passenger p : passengers) {
                if (p.getId() == passengerIdAux) {
                    passenger = p;
                    break;
                }
            }

            if (passenger == null) {
                table.setModel(model);
                return new Response("Pasajero no encontrado con ID ", Status.NOT_FOUND);
            }

            for (Flight flight : passenger.getFlights()) {
                model.addRow(new Object[]{
                    flight.getId(),
                    flight.getDepartureDate(),
                    flight.calculateArrivalDate()
                });
            }

            table.setModel(model);
            return new Response("Vuelos mostrados correctamente", Status.CREATED);

        } catch (Exception e) {
            table.setModel(model);
            return new Response("Error, Contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public void showAllLocations(JTable table) {
        try {
            List<Location> locations = StorageLocation.getInstance().getAllLocation();
            DefaultTableModel create = new DefaultTableModel(new Object[]{"Airport ID", "AiportName", "City", "country"}, 0);
            for (Location location : locations) {
                create.addRow(new Object[]{location.getAirportId(), location.getAirportName(), location.getAirportCity(), location.getAirportCountry()});
            }
            table.setModel(create);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }
    }

    public void showAllPlanes(JTable table) {
        try {
            List<Plane> planes = StoragePlane.getInstance().getAllPlanes();
            DefaultTableModel create = new DefaultTableModel(new Object[]{"ID", "Brand", "Model", "Max capacity", "Airline", "Number Flights"}, 0);
            for (Plane plane : planes) {
                create.addRow(new Object[]{plane.getId(), plane.getBrand(), plane.getModel(), plane.getMaxCapacity(), plane.getAirline(), plane.getNumFlights()});
            }
            table.setModel(create);
        } catch (Exception e) {
            System.out.println("Error, contacte al administrador");
        }
    }

}
