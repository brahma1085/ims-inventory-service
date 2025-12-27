package com.example.inventory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "item")
/*@Table(name = "INVENTORY_STOCK", uniqueConstraints = @UniqueConstraint(columnNames = "SKU_CODE"), indexes = {
		@Index(name = "idx_stock_sku", columnList = "sku_code") })*/
@Table(name = "INVENTORY_STOCK")
public class InventoryStock {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_stock_seq_gen")
	@SequenceGenerator(name = "inventory_stock_seq_gen", sequenceName = "INVENTORY_STOCK_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "SKU_CODE", nullable = false, unique = true)
	private String skuCode;

	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVENTORY_ITEM_ID", nullable = false)
	@JsonBackReference
	private InventoryItem item;

}
