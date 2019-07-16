package com.ucx.training.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ucx.training.shop.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LineItem extends BaseEntity<Integer> {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private BigDecimal lineItemTotal; //Product quantity * Product unitPrice


}