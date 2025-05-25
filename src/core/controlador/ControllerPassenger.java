/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.controlador.validator.PassengerValidator;
import core.modelo.Passenger;
import core.modelo.storage.StoragePassenger;
import java.time.LocalDate;
import javax.swing.JComboBox; 

public class ControllerPassenger {

    public static Response registerPassenger(String idPassenger, String firstname, String lastname, String year, String day, String month, String phoneCode, String phone, String country) {
        try {
            if (idPassenger == null || idPassenger.trim().isEmpty()) {
                return new Response("Id no puede estar vacío", Status.BAD_REQUEST);
            }

            long idAux;
            try {
                idAux = Long.parseLong(idPassenger);
            } catch (NumberFormatException e) {
                return new Response("Id debe ser numérico", Status.BAD_REQUEST);
            }

            if (idAux < 0) {
                return new Response("Id debe ser mayor o igual a cero", Status.BAD_REQUEST);
            }

            if (String.valueOf(idAux).length() > 15) {
                return new Response("Id debe tener menos de 15 dígitos", Status.BAD_REQUEST);
            }

            if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
                return new Response("La fecha de nacimiento no puede estar vacía", Status.BAD_REQUEST);
            }

            int yearAux, monthAux, dayAux;
            try {
                yearAux = Integer.parseInt(year);
                monthAux = Integer.parseInt(month);
                dayAux = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                return new Response("La fecha de nacimiento debe ser numérica", Status.BAD_REQUEST);
            }

            if (year.length() > 4) {
                return new Response("Fecha del año inválida", Status.BAD_REQUEST);
            }

            if (monthAux < 1 || monthAux > 12) {
                return new Response("Mes inválido", Status.BAD_REQUEST);
            }

            int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (verificationLeapYear(yearAux)) {
                dayPerMonth[1] = 29;
            }

            if (dayAux < 1 || dayAux > dayPerMonth[monthAux - 1]) {
                return new Response("Día inválido para el mes ingresado", Status.BAD_REQUEST);
            }

            if (phoneCode.isEmpty() || phone.isEmpty()) {
                return new Response("El código de número y el número no pueden estar vacíos", Status.BAD_REQUEST);
            }

            int phoneCodeAux;
            long phoneAux;
            try {
                phoneCodeAux = Integer.parseInt(phoneCode);
                phoneAux = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return new Response("El código y el número deben ser numéricos", Status.BAD_REQUEST);
            }

            if (phoneCode.length() > 3) {
                return new Response("Código del número celular inválido", Status.BAD_REQUEST);
            }

            if (phone.length() > 10) {
                return new Response("Número inválido", Status.BAD_REQUEST);
            }

            if (firstname.isEmpty() || lastname.isEmpty()) {
                return new Response("El nombre y el apellido no pueden estar vacíos", Status.BAD_REQUEST);
            }

            LocalDate birthDate = LocalDate.of(yearAux, monthAux, dayAux);
            StoragePassenger storagePassenger = StoragePassenger.getInstance();

            if (!storagePassenger.addPassenger(new Passenger(idAux, firstname, lastname, birthDate, phoneCodeAux, phoneAux, country))) {
                return new Response("Pasajero ya existe con ese ID", Status.BAD_REQUEST);
            }

            return new Response("Pasajero registrado de forma exitosa", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean verificationLeapYear(int year) {
        return PassengerValidator.verificationLeapYear(year);
    }
    
}
