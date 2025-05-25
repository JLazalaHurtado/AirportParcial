/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;
import core.modelo.Passenger;
import core.modelo.storage.StoragePassenger;
/**
 *
 * @author Juan Manuel
 */


public class PassengerService {
   
    public static boolean addPassenger(Passenger passenger) {
        StoragePassenger storagePassenger = StoragePassenger.getInstance();
        return storagePassenger.addPassenger(passenger);
    }
    
}
