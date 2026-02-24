package com.mynk.controller;

import com.mynk.modal.OfferingService;
import com.mynk.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class OfferingServiceController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<OfferingService>> getServiceBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ){
        Set<OfferingService> offeringServices = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(offeringServices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingService> getServiceById(@PathVariable Long id){
        OfferingService offeringService = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(offeringService);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<OfferingService>> getServiceByIds(@PathVariable Set<Long> ids){
        Set<OfferingService> offeringServices = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(offeringServices);
    }



}
