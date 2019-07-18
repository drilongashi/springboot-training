package com.ucx.training.shop.controller;

import com.ucx.training.shop.dto.AddressDTO;
import com.ucx.training.shop.dto.CustomerDTO;
import com.ucx.training.shop.entity.Address;
import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.exception.ResponseException;
import com.ucx.training.shop.service.CostumerService;
import com.ucx.training.shop.util.CustomerUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("costumers")
public class CostumerController {

    private CostumerService costumerService;

    private CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @PostMapping
    public CustomerDTO create(@RequestBody Costumer costumer) throws ResponseException {
        try {
            Costumer customer = costumerService.save(costumer);
            return CustomerUtil.getCustomer(customer);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public CustomerDTO update(@RequestBody Costumer costumer, @PathVariable Integer id) throws ResponseException {
        CustomerDTO customerDTO = null;
        try {
            Costumer updatedCustomer = costumerService.update(costumer, id);
            customerDTO = CustomerUtil.getCustomer(updatedCustomer);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return customerDTO;
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) throws ResponseException {
        try {
            costumerService.remove(id);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<CustomerDTO> findAllSorted(@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(defaultValue = "id") String... properties) throws ResponseException {
        try {
            List<Costumer> costumers = costumerService.findAllSorted(direction, properties);
            List<CustomerDTO> customerDTOList = CustomerUtil.getCustomers(costumers);
            return customerDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paged")
    public List<CustomerDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) throws ResponseException {
        try {
            Page<Costumer> costumerPage = costumerService.findAllPaged(pageNumber, pageSize);
            List<Costumer> costumers = costumerPage.getContent();
            List<CustomerDTO> customerDTOList = CustomerUtil.getCustomers(costumers);
            return customerDTOList;
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/addresses/{addressId}")
    public AddressDTO updateAddress(@RequestBody Address address, @PathVariable("addressId") Integer addressId) throws ResponseException {
        try {
            return costumerService.updateAddress(address, addressId);
        } catch (Exception e) {
            throw new ResponseException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
