package com.ecommerce.service.impl;

import com.ecommerce.common.Result;
import com.ecommerce.dto.address.AddressRequest;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Result<Address> createAddress(User user, AddressRequest request) {
        Address address = new Address();
        address.setTitle(request.getTitle());
        address.setCountry(request.getCountry());
        address.setCity(request.getCity());
        address.setDetails(request.getDetails());
        address.setUser(user);
        return Result.success(addressRepository.save(address));
    }

    @Override
    public Result<List<Address>> getUserAddresses(Long userId) {
        return Result.success(addressRepository.findByUserIdAndIsDeletedFalse(userId));
    }
}