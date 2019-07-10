package com.ucx.training.shop.controller;

import com.ucx.training.shop.entity.Costumer;
import com.ucx.training.shop.service.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop/costumers")
public class CostumerController {

    @Autowired
    private CostumerService costumerService;

    @PostMapping
    public Costumer create(@RequestBody Costumer costumer) {
        return costumerService.save(costumer);
    }

    @GetMapping
    public List<Costumer> findAll() {
        return costumerService.findAll();
    }

    @GetMapping("{id}")
    public Costumer findById(@PathVariable Integer id) {
        return costumerService.findById(id);
    }

    @PutMapping("{id}")
    public Costumer update(@RequestBody Costumer costumer) {
        return costumerService.update(costumer);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Integer id) {
        costumerService.remove(id);
    }


}
