package com.ecommerce.controller;

import com.ecommerce.common.Result;
import com.ecommerce.dto.address.AddressRequest;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Result<Address>> addAddress(@Valid @RequestBody AddressRequest request, @AuthenticationPrincipal User user) {
        Result<Address> result = addressService.createAddress(user, request);
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @GetMapping
    public ResponseEntity<Result<List<Address>>> getMyAddresses(@AuthenticationPrincipal User user) {
        Result<List<Address>> result = addressService.getUserAddresses(user.getId());
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}