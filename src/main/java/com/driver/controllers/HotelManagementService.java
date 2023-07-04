package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    HotelManagementRepository hotelRepository = new HotelManagementRepository();
    public String addHotel(Hotel hotel) {
        return hotelRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelRepository.addUser(user);
    }

    public String getHotelWithFacilities() {
        return hotelRepository.getHotelWithFacilities();
    }

    public int bookRoom(Booking booking) {
        return hotelRepository.bookRoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelRepository.getBooking(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelRepository.updateFacilities(newFacilities,hotelName);
    }
}
