package com.mynk.modal;

import lombok.Data;

@Data
public class SalonReport {

    private Long salonId;

    private String salonName;

    private int totalEarnings;

    private Integer totalBooking;

    private Integer cancelledBooking;

    private Double totalRefund;
}
