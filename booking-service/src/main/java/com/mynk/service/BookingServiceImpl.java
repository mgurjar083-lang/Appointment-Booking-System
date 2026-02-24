package com.mynk.service;

import com.mynk.domain.BookingStatus;
import com.mynk.dto.BookingRequest;
import com.mynk.dto.SalonDTO;
import com.mynk.dto.ServiceDTO;
import com.mynk.dto.UserDTO;
import com.mynk.modal.Booking;
import com.mynk.modal.SalonReport;
import com.mynk.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest bookingRequest, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> serviceDTOSet) {

       int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime = bookingRequest.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salonDTO, bookingStartTime, bookingEndTime);

         int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

         Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

         Booking newBooking = new Booking();
         newBooking.setCustomerId(userDTO.getId());
         newBooking.setSalonId(salonDTO.getId());
         newBooking.setServiceIds(idList);
         newBooking.setStatus(BookingStatus.PENDING);
         newBooking.setStartTime(bookingStartTime);
         newBooking.setEndTime(bookingEndTime);
         newBooking.setTotalPrice(totalPrice);
         newBooking.setTotalServices(serviceDTOSet.size());

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime){

        List<Booking> existingBookings = getBookingBySalonId(salonDTO.getId());

        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if(salonOpenTime.isAfter(bookingStartTime) && salonCloseTime.isBefore(bookingEndTime)){
            throw new RuntimeException("Booking time must be within salon working hours");
        }

        for(Booking existingBooking : existingBookings){

            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if(bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new RuntimeException("Slot not available, choose different time.");
            }

            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
                throw new RuntimeException("Slot not available, choose different time.");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus bookingStatus) {

        Booking booking = getBookingById(bookingId);
        booking.setStatus(bookingStatus);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {

        List<Booking> allBooking = getBookingBySalonId(salonId);

        if(date == null){
            return allBooking;
        }

        return allBooking.stream().filter(booking -> isSameDate(booking.getStartTime(),date) || isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {

        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {

        List<Booking> bookings = getBookingBySalonId(salonId);

        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();

        Integer totalBooking = bookings.size();

        List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).toList();

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport report = new SalonReport();

        report.setSalonId(salonId);
        report.setCancelledBooking(cancelledBookings.size());
        report.setTotalBooking(totalEarnings);
        report.setTotalEarnings(totalEarnings);
        report.setTotalRefund(totalRefund);
        report.setTotalBooking(totalBooking);

        return report;
    }
}
