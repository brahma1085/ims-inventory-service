package com.example.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInventoryStockRequestDto {

	/*
	 * @NotNull private Long itemId;
	 */

	@NotBlank
	private String skuCode;

	@NotNull
	@Positive
	private Integer quantity;

}
