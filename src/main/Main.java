/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.modelo.loader.FlightLoader;
import core.modelo.loader.JsonLoader;
import core.modelo.loader.LocationLoader;
import core.modelo.loader.PassengerLoader;
import core.modelo.loader.PlaneLoader;
import core.vista.AirportFrame;
import javax.swing.UIManager;

/**
 *
 * @author jlaza
 */
  public class Main {
    public static void main(String[] args) {
   LocationLoader.loadLocationAndStorage();
    PassengerLoader.loadPassengerAndStorage();
    PlaneLoader.loadPlanesAndStorage();
    FlightLoader.loadFlightAndStorage();     
        

        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AirportFrame().setVisible(true);
            }
        });
    }   
 }
