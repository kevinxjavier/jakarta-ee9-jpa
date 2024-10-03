package com.kevinpina.model;

import java.util.Objects;

import com.kevinpina.model.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemCart {

	private Integer quantity;
	private Product product;

	public Double getAmount() {
		return quantity * product.getPrice();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCart other = (ItemCart) obj;
		return Objects.equals(product.getId(), other.product.getId())
				&& Objects.equals(product.getName(), other.product.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(product);
	}

}
