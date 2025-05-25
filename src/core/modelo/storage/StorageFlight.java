/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.storage;

import core.modelo.Flight;
import core.modelo.Location;
import java.util.ArrayList;
import java.util.List;

public class StorageFlight {

    private static StorageFlight instance;
    ArrayList<Flight> flights;

    private StorageFlight() {
        this.flights = new ArrayList<>();
    }

    public static StorageFlight getInstance() {
        if (instance == null) {
            instance = new StorageFlight();
        }
        return instance;
    }

    public boolean addFlight(Flight flight) {
        for (Flight f : this.flights) {
            if (f.getId().equals(flight.getId())) {
                return false;
            }
        }
        this.flights.add(flight);
        return true;
    }

    public Flight getFlight(String id) {
        for (Flight flight : this.flights) {
            if (flight.getId().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    public List<Flight> getAllFlight() {
        return orderAirplaneById();
    }

    public List<Flight> orderAirplaneById() {
        List<Flight> temporalList = new ArrayList<>(flights);
        for (int i = 0; i < temporalList.size(); i++) {
            for (int j = 0; j < temporalList.size() - i - 1; j++) {
                Flight currentId = temporalList.get(j);
                Flight nextId = temporalList.get(j + 1);
                if (currentId.getId().compareTo(nextId.getId()) > 0) {
                    temporalList.set(j, nextId);
                    temporalList.set(j + 1, currentId);
                }
            }

        }

        return temporalList;
    }

    public List<Location> getAllDepartureLocations() {
        List<Location> departureLocations = new ArrayList<>();
        for (Flight flight : this.flights) {
            departureLocations.add(flight.getDepartureLocation());
        }
        return departureLocations;
    }

    public List<Location> getAllArrivalLocations() {
        List<Location> arrivalLocations = new ArrayList<>();
        for (Flight flight : this.flights) {
            arrivalLocations.add(flight.getArrivalLocation());
        }
        return arrivalLocations;
    }

    public List<Location> getAllScaleLocations() {
        List<Location> scaleLocation = new ArrayList<>();
        for (Flight flight : this.flights) {
            scaleLocation.add(flight.getScaleLocation());
        }
        return scaleLocation;
    }
}
