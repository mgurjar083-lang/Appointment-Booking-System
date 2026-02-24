package com.mynk.service;

import com.mynk.domain.BookingStatus;
import com.mynk.dto.BookingRequest;
import com.mynk.dto.SalonDTO;
import com.mynk.dto.ServiceDTO;
import com.mynk.dto.UserDTO;
import com.mynk.modal.Booking;
import com.mynk.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest bookingRequest, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet);

    List<Booking> getBookingByCustomer(Long customerId);

    List<Booking> getBookingBySalonId(Long salonId);

    Booking getBookingById(Long id);

    Booking updateBooking(Long bookingId, BookingStatus bookingStatus);

    List<Booking> getBookingByDate(LocalDate date, Long salonId);

    SalonReport getSalonReport(Long salonId);

}
