package com.garagemanagement.carrepairservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RevenueCustomerDTO {
    @JsonProperty(value = "customer_id", access = JsonProperty.Access.READ_ONLY)
    private String customerId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fullname;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String address;

    @JsonProperty("start_date")
    @NotNull(message = "start date must be not empty")
    private Date startDate;

    @JsonProperty("end_date")
    @NotNull(message = "end date must be not empty")
    private Date endDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;
}
