package com.mynk.repository;

import com.mynk.modal.OfferingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OfferingServiceRepository extends JpaRepository<OfferingService,Long> {

    Set<OfferingService> findBySalonId(Long salonId);
}
