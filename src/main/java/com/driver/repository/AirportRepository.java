package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    private Map<String,Airport> airportMap = new HashMap<>(); // airportName : Airport object
    private Map<Integer, Flight> flightMap = new HashMap<>(); // flightID: Flight object
    private Map<Integer, Passenger> passengerMap = new HashMap<>(); // passId : Passenger object
    private Map<Integer, List<Integer>> ticketDB = new HashMap<>(); // ticketID : listof passengerId

    public String addAirport(Airport airport) {
            airportMap.put(airport.getAirportName(),airport);
    }

    public Map<String,Airport> getLargestAirportName() {
        return new HashMap<>(airportMap);

    }

    public Map<Integer,Flight> getShortestDurationOfPossibleBetweenTwoCities() {
        return new HashMap<>(flightMap);
    }

    public void addFlight(Flight flight) {

            flightMap.put(flight.getFlightId(),flight);

    }

    public void addPassenger(Passenger passenger) {
        if(!passengerMap.containsKey(passenger.getPassengerId())){
            passengerMap.put(passenger.getPassengerId(),passenger);
        }
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int ans = 0;
        if(airportMap.containsKey(airportName)){
            City city = airportMap.get(airportName).getCity();
            for(Integer flightId : ticketDB.keySet()){
                Flight flight = flightMap.get(flightId);
                if(flight.getFlightDate().equals(date) && flight.getToCity().equals(city) || flight.getFromCity().equals(city)){
                    ans += ticketDB.get(flightId).size();
                }
            }
        }
        return ans;
    }

    public int calculateFlightFare(Integer flightId) {
        int len = ticketDB.get(flightId).size();
        return 3000 + (len * 50);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(ticketDB.containsKey(flightId)){
            List<Integer> list = ticketDB.get(flightId);
            Flight flight = flightMap.get(flightId);
            if(list.size() == flight.getMaxCapacity()){
                return "FAILURE";
            }
            if(list.contains(passengerId)) return "FAILURE";
            list.add(passengerId);
            ticketDB.put(flightId,list);
            return "SUCCESS";
        }
        else{
            List<Integer> list = new ArrayList<>();
            list.add(passengerId);
            ticketDB.put(flightId,list);
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(ticketDB.containsKey(flightId)){
            boolean removed = false;
            List<Integer> passList = ticketDB.get(flightId);
            if(passList == null) return "FAILURE";
            if(passList.contains(passengerId)){
                passList.remove(passengerId);
                removed = true;
            }
            if(removed){
                ticketDB.put(flightId,passList);
                return "SUCCESS";
            }
            else return "FAILURE";
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0;
        for(List<Integer> list : ticketDB.values()){
            for(Integer lis : list){
                if(lis == passengerId) count++;
            }
        }
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        for(Flight flight : flightMap.values()){
            if(flight.getFlightId() == flightId){
                City city = flight.getFromCity();
                for(Airport airport : airportMap.values()){
                    if(airport.getCity().equals(city)){
                        return airport.getAirportName();
                    }
                }
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        if(ticketDB.containsKey(flightId)){
            int count = ticketDB.get(flightId).size();
            int revenue = 0;
            for(int i=0;i<count;i++){
                revenue += 3000 + (i * 50);
            }
            return revenue;
        }
        return 0;
    }
}
