package com.mynk.controller;

import com.mynk.dto.CategoryDTO;
import com.mynk.dto.SalonDTO;
import com.mynk.dto.ServiceDTO;
import com.mynk.modal.OfferingService;
import com.mynk.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonOfferingServiceController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/create")
    public ResponseEntity<OfferingService> createService(@RequestBody ServiceDTO serviceDTO){

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        OfferingService offeringService = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);

        return ResponseEntity.ok(offeringService);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OfferingService> updateService(@PathVariable Long id, @RequestBody OfferingService offService){

        OfferingService offeringService = serviceOfferingService.updateService(id,offService);

        return ResponseEntity.ok(offeringService);
    }

}
