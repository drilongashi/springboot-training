package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.dto.DTOEntity;
import com.ucx.training.shop.entity.Customer;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.AddressService;
import com.ucx.training.shop.service.CustomerService;
import com.ucx.training.shop.util.PaginationUtil;
import com.ucx.training.shop.util.uimapper.DTOMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("v1/customers")
public class CustomerController {

    private CustomerService customerService;
    private AddressService addressService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @PostMapping
    public DTOEntity create(@RequestBody Customer customer) throws ResponseException {
        try {
            Customer createdCustomer = customerService.save(customer);
            return DTOMapper.convertToDto(createdCustomer, CustomerDTO.class);
        } catch (IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/last-address")
    public Map<String, Integer> getLastAddress(@PathVariable Integer id) {
        Map<String, Integer> resultMap = new HashMap<>();
        Integer addressId = addressService.findAllAddressesByCustomerId(id);
        resultMap.put("id", addressId);
        return resultMap;
    }

    @PutMapping("{id}")
    public Map<String, Integer> update(@RequestBody Customer customer, @PathVariable Integer id) throws ResponseException {
        Map<String, Integer> responseMap = new HashMap<>();
        try {
            customerService.updateCostumerWithAddress(customer, id);
            responseMap.put("id", id);
        } catch (NotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseMap;
    }

    @GetMapping("{costumerId}")
    public DTOEntity getById(@PathVariable Integer costumerId) {
        log.info("SERVLET INFO: " + httpServletRequest.getScheme() + " " + httpServletRequest.getServerName() + " " + httpServletRequest.getServerPort());
        Customer foundCustomer = customerService.findById(costumerId);
        return DTOMapper.convertToDto(foundCustomer, CustomerDTO.class);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            customerService.remove(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<DTOEntity> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Customer> customers = customerService.findAllSorted(direction, properties);
            return DTOMapper.converToDTOList(customers, CustomerDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public Map<String, Object> findAllPaged(@PageableDefault Pageable pageable) throws ResponseException {
        try {
            return PaginationUtil.getPage(customerService.findPaged(pageable), CustomerDTO.class);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allActive")
    public List<Customer> findAllActive() {
        return customerService.findAllActive();
    }
}
