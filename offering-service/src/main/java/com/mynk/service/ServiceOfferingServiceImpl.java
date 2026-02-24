package com.mynk.service;

import com.mynk.dto.CategoryDTO;
import com.mynk.dto.SalonDTO;
import com.mynk.dto.ServiceDTO;
import com.mynk.modal.OfferingService;
import com.mynk.repository.OfferingServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService{

    private final OfferingServiceRepository offeringServiceRepository;

    @Override
    public OfferingService createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {

        OfferingService offeringService = new OfferingService();
        offeringService.setName(serviceDTO.getName());
        offeringService.setImage(serviceDTO.getImage());
        offeringService.setSalonId(salonDTO.getId());
        offeringService.setDescription(serviceDTO.getDescription());
        offeringService.setCategoryId(categoryDTO.getId());
        offeringService.setPrice(serviceDTO.getPrice());
        offeringService.setDuration(serviceDTO.getDuration());

        return offeringServiceRepository.save(offeringService);
    }


    @Override
    public OfferingService updateService(Long serviceId, OfferingService service) {

        OfferingService offeringService = offeringServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not exists with id : "+serviceId));

        offeringService.setName(service.getName());
        offeringService.setImage(service.getImage());
        offeringService.setDescription(service.getDescription());
        offeringService.setPrice(service.getPrice());
        offeringService.setDuration(service.getDuration());

        return offeringServiceRepository.save(offeringService);
    }

    @Override
    public Set<OfferingService> getAllServiceBySalonId(Long salonId, Long categoryId) {

        Set<OfferingService> services = offeringServiceRepository.findBySalonId(salonId);

        if(categoryId != null){
            services = services.stream().filter((service) -> service.getCategoryId() != null &&
                    service.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }

        return services;
    }

    @Override
    public Set<OfferingService> getServicesByIds(Set<Long> ids) {

        List<OfferingService> services = offeringServiceRepository.findAllById(ids);

        return new HashSet<>(services);
    }

    @Override
    public OfferingService getServiceById(Long id) {

        return offeringServiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not exist with id : "+id));
    }
}
