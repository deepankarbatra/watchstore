package com.nagarro.watchstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dao.CartRepository;
import com.nagarro.watchstore.entity.Cart;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.CartService;
import com.nagarro.watchstore.service.WatchService;


/**
 * The CartServiceImpl class provides an implementation of the CartService interface.
 * It handles the logic for managing shopping carts.
 * 
 * @author yogesh04
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;

	private final WatchService watchService;

	@Autowired
	public CartServiceImpl(final WatchService watchService, final CartRepository cartRepository) {
		this.cartRepository = cartRepository;
		this.watchService = watchService;
	}

	@Override
	public List<Cart> retrieveCartByUserId(final String userId) {
		final List<Cart> carts = cartRepository.findByUserId(userId);
		if (carts.isEmpty()) {
			throw new NotFoundException("UserId", Constant.CART_NOT_FOUND + userId);
		}
		return carts;
	}

	@Override
	public Cart addCart(String userId, String watchModel) {
		Cart cart = null;
		try {
			Watch watch = watchService.getWatchByModel(watchModel);
			Optional<Cart> cartOptional = cartRepository.findByUserIdAndWatch(userId, watch);
			if (cartOptional.isEmpty()) {
				cart = new Cart();
				cart.setWatch(watch);
				cart.setUserId(userId);
				cart.setWatchQty(1);
			} else {
				cart = cartOptional.get();
				validateWatchStock(watch, cart.getWatchQty() + 1);
				cart.setWatchQty(cart.getWatchQty() + 1);
			}
			cart = cartRepository.save(cart);
		} catch (NotFoundException exception) {
			throw new BadRequestException("watchModel", Constant.WATCH_NOT_FOUND + watchModel);
		}

		return cart;
	}

	@Override
	public void deleteCartById(final int cartId) throws NotFoundException {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		cartOptional.ifPresentOrElse(cart -> cartRepository.deleteById(cart.getCartId()), () -> {
			throw new NotFoundException("CartId", Constant.CART_NOT_FOUND + cartId);
		});
	}

	@Override
	public Cart updateCartQty(final int cartId, final int watchQty) throws NotFoundException {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		cartOptional.ifPresentOrElse(cart -> {
			validateWatchStock(cart.getWatch(), watchQty);
			cart.setWatchQty(watchQty);
		}, () -> {
			throw new NotFoundException("CartId", Constant.CART_NOT_FOUND + cartId);
		});
		return cartRepository.save(cartOptional.get());
	}

	private void validateWatchStock(final Watch watch, final int quantity) {
		final int updatedStockQuantity = watch.getStockQuantity() - quantity;
		if (updatedStockQuantity < 0)
			throw new BadRequestException("Quantity", Constant.QUANTITY_GREATER_THAN_STOCKS);
	}
}
