package com.ecommerce.service;
import com.ecommerce.common.Result;
import com.ecommerce.dto.address.AddressRequest;
import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import java.util.List;

public interface AddressService {
    Result<Address> createAddress(User user, AddressRequest request);
    Result<List<Address>> getUserAddresses(Long userId);
}