package com.mynk.modal;

import com.mynk.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ElementCollection
    private Set<Long>  serviceIds;

    private BookingStatus status = BookingStatus.PENDING;

    private int totalPrice;

    @Column(name = "total_services", nullable = false)
    private int totalServices;
}
