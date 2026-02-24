package com.mynk.controller;

import com.mynk.domain.BookingStatus;
import com.mynk.dto.*;
import com.mynk.mapper.BookingMapper;
import com.mynk.modal.Booking;
import com.mynk.modal.SalonReport;
import com.mynk.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest){

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonId);
        salonDTO.setOpenTime(LocalTime.now());
        salonDTO.setCloseTime(LocalTime.now().plusHours(12));

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for men");

        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest,userDTO,salonDTO,serviceDTOSet);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(){

        List<Booking> bookings = bookingService.getBookingByCustomer(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon(){

        List<Booking> bookings = bookingService.getBookingBySalonId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    public Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream().map(BookingMapper::toDTO).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId){

        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));

    }

    @PutMapping("/update/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status){

        Booking booking = bookingService.updateBooking(bookingId, status);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookingSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date){

        List<Booking> bookings = bookingService.getBookingByDate(date,salonId);

        List<BookingSlotDTO> slotDTOS = bookings.stream().map(booking -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking.getStartTime());
            slotDTO.setEndTime(booking.getEndTime());
            return slotDTO;
        }).toList();

        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(){
        SalonReport report = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(report);
    }

}
