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
    @Autowired
    AirportRepository airportRepository;
    public String addAirport(Airport airport) {
        return airportRepository.addAirport(airport);

    }

    public String getLargestAirportName() {

        Map<String,Airport> map = airportRepository.getLargestAirportName();

        int max = Integer.MIN_VALUE;
//        String name = "";
        for(String key : map.keySet()){
            if(map.get(key).getNoOfTerminals() > max){
                max = map.get(key).getNoOfTerminals();
//                name = key;
            }

        }
        List<String> list = new ArrayList<>();

        for(Airport airport : map.values()){
            if(airport.getNoOfTerminals() == max){
                list.add(airport.getAirportName());
            }
        }
        Collections.sort(list);
        return list.get(0);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        Map<Integer, Flight> flightMap = airportRepository.getShortestDurationOfPossibleBetweenTwoCities();
        double minDuration = -Integer.MAX_VALUE;
        for(Integer key : flightMap.keySet()){
            if(flightMap.get(key).getFromCity() == fromCity && flightMap.get(key).getToCity() == toCity
                    && flightMap.get(key).getDuration() < minDuration){
                minDuration =flightMap.get(key).getDuration();
            }

        }
        return minDuration == Integer.MAX_VALUE ? -1 : minDuration;
    }

    public String addFlight(Flight flight) {
        return airportRepository.addFlight(flight);

    }

    public void addPassenger(Passenger passenger) {

        airportRepository.addPassenger(passenger);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepository.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepository.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }
}
