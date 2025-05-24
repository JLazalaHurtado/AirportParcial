/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.modelo.loader;

import core.modelo.Flight;
import core.modelo.Location;
import core.modelo.Plane;
import core.modelo.storage.StorageFlight;
import core.modelo.storage.StorageLocation;
import core.modelo.storage.StoragePlane;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jlaza
 */
public class FlightLoader {

public static void loadFlightAndStorage() {
    JSONArray array = JsonLoader.load("core/data/flights.json");
    StorageFlight storage = StorageFlight.getInstance();
    StorageLocation storageLocation = StorageLocation.getInstance();
    StoragePlane storagePlane = StoragePlane.getInstance();

    for (int i = 0; i < array.length(); i++) {
        JSONObject object = array.getJSONObject(i);

        String flightId = object.getString("id");
        String planeId = object.getString("plane");
        Plane plane = storagePlane.getPlane(planeId);

        Location departureLocation = storageLocation.getLocation(object.getString("departureLocation"));
        Location arrivalLocation = storageLocation.getLocation(object.getString("arrivalLocation"));

        LocalDateTime departureDate = LocalDateTime.parse(object.getString("departureDate"));
        int hoursDurationArrival = object.getInt("hoursDurationArrival");
        int minutesDurationArrival = object.getInt("minutesDurationArrival");


        if (!object.isNull("scaleLocation")) {
            Location scaleLocation = storageLocation.getLocation(object.getString("scaleLocation"));
            int hoursDurationScale = object.getInt("hoursDurationScale");
            int minutesDurationScale = object.getInt("minutesDurationScale");

            Flight flight = new Flight(
                flightId, plane, departureLocation, scaleLocation, arrivalLocation,
                departureDate, hoursDurationArrival, minutesDurationArrival,
                hoursDurationScale, minutesDurationScale
            );
            storage.addFlight(flight);
        } else {
            Flight flight = new Flight(
                flightId, plane, departureLocation, arrivalLocation,
                departureDate, hoursDurationArrival, minutesDurationArrival
            );
            storage.addFlight(flight);
        }
    }
}
}

