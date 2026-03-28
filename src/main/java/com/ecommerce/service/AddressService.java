package com.ecommerce.service;

import com.ecommerce.dto.address.AddressRequest;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address createAddress(User user, AddressRequest request) {
        Address address = new Address();
        address.setTitle(request.getTitle());
        address.setCity(request.getCity());
        address.setDetails(request.getDetails());
        address.setUser(user);
        return addressRepository.save(address);
    }

    public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUserIdAndIsDeletedFalse(userId);
    }
}