package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    Map<String,Airport> airportMap = new HashMap<>();
    Map<Integer,Flight> flightMap = new HashMap<>();
    Map<Integer,Passenger> passengerMap = new HashMap<>();
    //Map<Integer, Integer> passengerFlightMap = new HashMap<>();
    Map<Integer,Map<Integer,Integer>> flightCustFareMap = new HashMap<>();
    public void addAirport(Airport airport) {
        airportMap.put(airport.getAirportName(),airport);
    }

    public Collection<Airport> getAllAirports() {
        return airportMap.values();
    }

    public void addFlight(Flight flight) {

        flightMap.put(flight.getFlightId(), flight);
        if(!flightCustFareMap.containsKey(flight.getFlightId()))
            flightCustFareMap.put(flight.getFlightId(),new HashMap<Integer,Integer>());

    }

    public Collection<Flight> getAllFlights() {
        return flightMap.values();
    }

    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(), passenger);
    }

    public Airport getAirportByName(String airportName) {
        return airportMap.get(airportName);
    }


    public Flight getFlightById(Integer flightId) {
        if(flightMap.containsKey(flightId))
            return flightMap.get(flightId);

        return null;
    }

    public int getCurrBookingsOfFlight(Integer flightId) {


        return flightCustFareMap.get(flightId).size();
    }

    public void bookATicket(Integer flightId, Integer passengerId) {
        //flightCustFareMap.computeIfAbsent(flightId, fid -> new HashMap<Integer,Integer>());
        if(flightMap.containsKey(flightId) && passengerMap.containsKey(passengerId)) {
            Map<Integer, Integer> custFareMap = flightCustFareMap.get(flightId);
            custFareMap.put(passengerId, 3000 + getCurrBookingsOfFlight(flightId) * 50);
        }
    }

    public boolean passengerFlightMapHasKeyValuePair(Integer flightId, Integer passengerId) {
        if(!flightCustFareMap.containsKey(flightId) || !flightCustFareMap.get(flightId).containsKey(passengerId))
            return false;
        return true;
    }

    public void cancelATicket(Integer flightId, Integer passengerId) {
        flightCustFareMap.get(flightId).remove(passengerId);

    }

    public boolean passengerAlreadyBookedThisFlight(Integer flightId, Integer passengerId) {
        if(flightCustFareMap.containsKey(flightId) && flightCustFareMap.get(flightId).containsKey(passengerId))
            return true;
        return false;
    }



    public Map<Integer, Integer> getCustFareMapForFlight(Integer flightId) {
        return flightCustFareMap.get(flightId);
    }




    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0;
        for(Map<Integer,Integer> map : flightCustFareMap.values()){
            if(map.containsKey(passengerId))count++;
        }
        return count;
    }
}
