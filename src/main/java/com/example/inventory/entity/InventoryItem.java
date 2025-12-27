package com.example.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = "stocks")
@Table(name = "INVENTORY_ITEM", indexes = { @Index(name = "idx_inventory_name", columnList = "name") })
public class InventoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_stock_seq_gen")
	@SequenceGenerator(name = "inventory_stock_seq_gen", sequenceName = "INVENTORY_STOCK_SEQ", allocationSize = 1)
	private Long id;

	private String name;
	private int stock;
	private double price;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	@JsonManagedReference
	private List<InventoryStock> stocks = new ArrayList<>();

}
