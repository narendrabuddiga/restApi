package com.api.restApi.entity.request;

import jakarta.validation.constraints.NotNull;
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
public class CreateProductReq {
    @NotNull(message = "size is required.")
    private int size;
    private boolean clearExisting;
    private boolean addToExisting;
}
