package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirportService {
    AirportRepository airportRepository = new AirportRepository();
    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName() {
        Airport ap = null;
        for(Airport airport :airportRepository.getAllAirports()){
            if(ap == null)
                ap = airport;
            else{
                if(ap.getNoOfTerminals() < airport.getNoOfTerminals())
                    ap = airport;
                else if(ap.getNoOfTerminals() == airport.getNoOfTerminals()
                        && ap.getAirportName().toLowerCase().compareTo(airport.getAirportName().toLowerCase()) > 0){
                    ap = airport;
                }
            }

        }
        return ap.getAirportName();
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public Double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = Double.MAX_VALUE;
        for(Flight flight : airportRepository.getAllFlights()){
            if(flight.getFromCity()==fromCity && flight.getToCity() == toCity ){
                if(Double.compare(ans, flight.getDuration()) > 0)
                    ans = flight.getDuration();
            }
        }
        return (ans == Double.MAX_VALUE)?-1:ans;
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int ans = 0;
        Airport airport = airportRepository.getAirportByName(airportName);
        if(airport == null)return 0;
        for(Flight flight : airportRepository.getAllFlights()){
            if(flight.getFlightDate().compareTo(date) == 0 && (flight.getFromCity()==airport.getCity() ||
                    flight.getToCity() == airport.getCity())){
                ans++;
            }
        }
        return ans;
    }

    public int calculateFlightFare(Integer flightId) {
        int base = 3000;
        int currbookings = airportRepository.getCurrBookingsOfFlight(flightId);
        return base + currbookings * 50;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        boolean booked = airportRepository.passengerAlreadyBookedThisFlight(flightId,passengerId);

        if(booked)return "FAILURE";


        Flight flight = airportRepository.getFlightById(flightId);
        if(flight == null)return "FAILURE";
        int currbookings = airportRepository.getCurrBookingsOfFlight(flightId);
        if(currbookings >= flight.getMaxCapacity())return "FAILURE";


        airportRepository.bookATicket(flightId,passengerId);

        return "SUCCESS";
    }


    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(!airportRepository.passengerFlightMapHasKeyValuePair(flightId,passengerId))
            return "FAILURE";
        airportRepository.cancelATicket(flightId, passengerId);

        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        Flight flight = airportRepository.getFlightById(flightId);
        if(flight == null) return null;
        City city = flight.getFromCity();
        if(city == City.CHANDIGARH)return "CA";
//        else if(city = City.BANGLORE)return "";
//        else if(city = City.BANGLORE)return "";
//        else if(city = City.BANGLORE)return "";
//        else if(city = City.BANGLORE)return "";
//        else if(city = City.BANGLORE)return "";
//        else if(city = City.BANGLORE)return "";
        return city.name();

        // return flight.getFromCity().name();

    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        Map<Integer,Integer> customerFareMap = airportRepository.getCustFareMapForFlight(flightId);
        int fare = 0;
        for(Integer i : customerFareMap.values())fare+=i;

        return fare;
    }
}
