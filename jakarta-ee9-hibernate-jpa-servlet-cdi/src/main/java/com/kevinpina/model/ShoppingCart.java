package com.kevinpina.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.kevinpina.configs.Cart;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

@Cart
//@SessionScoped	// Always must be a default Constructor in CDI. we use @Inject if there is a
					// parametric Construtor. Also implement Serializable.
//@Named("shoppingCart")	// In CDI by default the name is cammel case so it not necessary define this:
							// @Named("shoppingCart") just with @Named is enough.
@Getter
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = -5036215984875903085L;

	@Inject
	private transient Logger log; // We use transient because Logger is not Serializable and can not be stored in Session
								  // We use it also when it is @ConversationalScope or the class implement Serializable
	
	private List<ItemCart> itemsCart;

//	public ShoppingCart() {
//		this.itemsCart = new ArrayList<>();
//	}

	/**
	 * For a Bean or Component it's recommended to use @PostConstruct instead of a Constructor unles we need to initialize 
	 * the Bean or Component through a Constructor passing arguments to initiatlize some attributes 
	 */
	// Will execute once because is @SessionScoped
	// If this were @RequestScope will call everytime we invoke this class  
	@PostConstruct
	public void initialize() {
		this.itemsCart = new ArrayList<>();
		log.info("Initializing " + this.getClass().getName());
	}

	// Will execute everytime when we invalidate the session "close the session" or redeploy the app; because is @SessionScoped
	// If this were @RequestScope will call everytime we invoke this class
	@PreDestroy
	public void destroy() {
		log.info("Destroiyng " + this.getClass().getName());
	}

	public void addItemCar(ItemCart itemCart) {
		if (itemsCart.contains(itemCart)) {

			Optional<ItemCart> itemCarOptional = itemsCart.stream().filter(item -> item.equals(itemCart)).findAny();

			if (itemCarOptional.isPresent()) {
				ItemCart existingItemCart = itemCarOptional.get();
				existingItemCart.setQuantity(existingItemCart.getQuantity() + 1);
			}

		} else {
			itemsCart.add(itemCart);
		}
	}

	public Double getTotal() {
		return itemsCart.stream().mapToDouble(ItemCart::getAmount).sum();
	}

}
