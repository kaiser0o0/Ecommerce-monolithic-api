package com.ecommerce.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String details;
}