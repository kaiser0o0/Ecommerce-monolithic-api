package com.ecommerce.controller;

import com.ecommerce.common.Result;
import com.ecommerce.dto.address.AddressRequest;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @PostMapping
    public Result<Address> addAddress(@Valid @RequestBody AddressRequest request, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return Result.success(addressService.createAddress(user, request));
    }

    @GetMapping
    public Result<List<Address>> getMyAddresses(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        return Result.success(addressService.getUserAddresses(user.getId()));
    }
}