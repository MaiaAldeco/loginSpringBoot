
package com.tutorial.crud.dto;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProductoDto {
    
    @NotBlank
    private String nombre;
    @Min(0)
    private Float precio;
}
