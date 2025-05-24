package core.modelo.loader;

import core.modelo.Passenger;
import core.modelo.storage.StoragePassenger;
import java.time.LocalDate;
import java.time.Month;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jlaza
 */
public class PassengerLoader {

    public static void loadPassengerAndStorage() {
        long id;
        String firstname;
        String lastname;
        String birthDate;
        int countryPhoneCode;
        long phone;
        String country;
        int year;
        int month;
        int day;

        JSONArray array = JsonLoader.load("core/data/passengers.json");
        StoragePassenger storage = StoragePassenger.getInstance();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            id = object.getLong("id");
            firstname = object.getString("firstname");
            lastname = object.getString("lastname");
            birthDate = object.getString("birthDate");
            countryPhoneCode = object.getInt("countryPhoneCode");
            phone = object.getLong("phone");
            country = object.getString("country");
            String[] arrayBirthDate = birthDate.split("-");
            year = Integer.parseInt(arrayBirthDate[0]);
            month = Integer.parseInt(arrayBirthDate[1]);
            day = Integer.parseInt(arrayBirthDate[2]);
            LocalDate birthDate2 = LocalDate.of(year, month, day);

            Passenger passenger = new Passenger(id, firstname, lastname, birthDate2, countryPhoneCode, phone, country);
            
            storage.addPassenger(passenger);
        }

    }
}
