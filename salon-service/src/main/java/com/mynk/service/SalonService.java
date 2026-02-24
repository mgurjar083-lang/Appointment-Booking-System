package com.mynk.service;

import com.mynk.modal.Salon;
import com.mynk.payload.dto.SalonDTO;
import com.mynk.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId);

    List<Salon> getAllSalon();

    Salon getSalonById(Long salonId);

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}
