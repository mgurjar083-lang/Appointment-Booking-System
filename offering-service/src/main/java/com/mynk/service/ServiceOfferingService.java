package com.mynk.service;

import com.mynk.dto.CategoryDTO;
import com.mynk.dto.SalonDTO;
import com.mynk.dto.ServiceDTO;
import com.mynk.modal.OfferingService;

import java.util.Set;

public interface ServiceOfferingService {

    OfferingService createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    OfferingService updateService(Long serviceId, OfferingService offeringService);

    Set<OfferingService> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<OfferingService> getServicesByIds(Set<Long> ids);

    OfferingService getServiceById(Long id);
}
