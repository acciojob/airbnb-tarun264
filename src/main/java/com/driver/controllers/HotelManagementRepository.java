package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    HashMap<String ,Hotel> HotelDB= new HashMap<>();
    HashMap<Integer,User> UserDB = new HashMap<>();
    HashMap<String,Booking> bookingDB= new HashMap<>();
    HashMap<String,Integer> rentDB= new HashMap<>();
    public String addHotel(Hotel hotel) {
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
        if(hotel.getHotelName()==null || hotel==null){
            return "FAILURE";
        }
        String hotelName=hotel.getHotelName();
        if(!HotelDB.containsKey(hotelName)){
            HotelDB.put(hotelName,hotel);
            return "SUCCESS";
        }
        else{
            return "FAILURE";
        }
    }

    public Integer addUser(User user) {
        //You need to add a User Object to the database
        //Assume that user will always be a valid user and return the aadharCardNo of the user

        int aadhaarNo= user.getaadharCardNo();
        UserDB.put(aadhaarNo,user);
        return aadhaarNo;
    }


    public String getHotelWithFacilities() {
        //Out of all the hotels we have added so far, we need to find the hotelName with most no of facilities
        //Incase there is a tie return the lexicographically smaller hotelName
        //Incase there is not even a single hotel with atleast 1 facility return "" (empty string)

        int maxFacilities=0;

        for(String key: HotelDB.keySet()){
           List<Facility> facilities = HotelDB.get(key).getFacilities();
           maxFacilities=Math.max(maxFacilities,facilities.size());
            }
        if(maxFacilities==0){
            return "";

        }
        List<String> hotelWithFacilites= new ArrayList<>();
        for(Hotel hotel: HotelDB.values()){
            List<Facility> facilities= hotel.getFacilities();
            if(facilities.size()==maxFacilities){
                hotelWithFacilites.add(hotel.getHotelName());
            }
        }
        Collections.sort(hotelWithFacilites);
        return hotelWithFacilites.get(0);

    }


    public int bookRoom(Booking booking) {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
        String hotelName= booking.getHotelName();
        if(!HotelDB.containsKey(hotelName)) return -1;

        //If there arent enough rooms available in the hotel that we are trying to book return -1
        if(HotelDB.get(hotelName).getAvailableRooms()>=booking.getNoOfRooms()){
            Hotel hotel= HotelDB.get(hotelName);
            int roomAvailable= hotel.getAvailableRooms();
            roomAvailable-=booking.getNoOfRooms();
            hotel.setAvailableRooms(roomAvailable);
            HotelDB.put(hotelName,hotel);

            //Have bookingId as a random UUID generated String
            String bookingID= UUID.randomUUID()+"";
            bookingDB.put(bookingID,booking);

            int amountTobePaid= hotel.getPricePerNight();
            rentDB.put(bookingID,amountTobePaid);

            return amountTobePaid;

        }
        return -1;
    }

    public int getBooking(Integer aadharCard) {
        //In this function return the bookings done by a person
        int count=0;
        for(Booking bookings: bookingDB.values()){
            if(bookings.getBookingAadharCard()==aadharCard){
                count++;
            }
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible

        if(!HotelDB.containsKey(hotelName)) return null;

        Hotel hotel= HotelDB.get(hotelName);
        List<Facility> facalities= hotel.getFacilities();
        for(int i=0;i<newFacilities.size();i++){
            if (!facalities.contains(newFacilities.get(i))){
                facalities.add(newFacilities.get(i));
            }
        }
        hotel.setFacilities(facalities);
        HotelDB.put(hotelName,hotel);
        return hotel;

    }
}
