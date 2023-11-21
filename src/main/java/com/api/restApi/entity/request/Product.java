package com.api.restApi.entity.request;

import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @NotNull(message = "Id is required.")
    private int id;
    private UUID uuid;
    @NotBlank(message = "The country is required.")
    private String name;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}