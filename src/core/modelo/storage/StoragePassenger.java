/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.storage;

import core.modelo.Passenger;
import java.util.ArrayList;
import java.util.List;

public class StoragePassenger {

    private static StoragePassenger instance;
    ArrayList<Passenger> passengers;

    private StoragePassenger() {
        this.passengers = new ArrayList<>();
    }

    public static StoragePassenger getInstance() {
        if (instance == null) {
            instance = new StoragePassenger();
        }
        return instance;
    }

    public boolean addPassenger(Passenger passenger) {
        for (Passenger pass : this.passengers) {
            if (pass.getId() == passenger.getId()) {
                return false;
            }
        }
        this.passengers.add(passenger);
        return true;
    }

    public Passenger getPassenger(long id) {
        for (Passenger passenger : this.passengers) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        return null;
    }

    public List<Passenger> getAllPassengers() {
        return this.passengers;
    }

    public List<Passenger> passengerOrderById() {
        for (int i = 0; i < passengers.size(); i++) {
            for (int j = 0; j < passengers.size() - i - 1; j++) {
              Passenger currentId = passengers.get(j);
              Passenger nextId = passengers.get(j+1);
              if(currentId.getId()>nextId.getId()) {
                passengers.set(j, nextId);
                passengers.set(j+1, currentId);
              }
            }

        }
        return passengers;
    }
}
