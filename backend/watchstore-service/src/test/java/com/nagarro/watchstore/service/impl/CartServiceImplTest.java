package com.nagarro.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.dao.CartRepository;
import com.nagarro.watchstore.entity.Cart;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.WatchService;

/**
 * Unit tests for the {@link CartServiceImpl} class.
 * 
 * @author yogesh04
 */
public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private WatchService watchService;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    /**
     * Test case for retrieving carts by user ID when carts exist.
     */
    @Test
    public void retrieveCartByUserId_WhenCartsExist_ReturnsCarts() {
        String userId = "Yogesh123";
        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart());
        carts.add(new Cart());

        when(cartRepository.findByUserId(userId)).thenReturn(carts);

        List<Cart> result = cartService.retrieveCartByUserId(userId);

        assertNotNull(result);
        assertEquals(carts.size(), result.size());

        verify(cartRepository).findByUserId(userId);
    }

    /**
     * Test case for retrieving carts by user ID when no carts exist.
     */
    @Test
    public void retrieveCartByUserId_WhenNoCartsExist_ThrowsNotFoundException() {
        String userId = "Yogesh123";

        when(cartRepository.findByUserId(userId)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> cartService.retrieveCartByUserId(userId));

        verify(cartRepository).findByUserId(userId);
    }

    /**
     * Test case for adding a cart when the cart exists.
     */
    @Test
    public void addCart_WhenCartExists_ReturnsCart() {
        String userId = "Yogesh123";
        String watchModel = "Analog-123";
        Watch watch = new Watch();
        watch.setStockQuantity(10);
        
        Cart expectedCart=new Cart();
        expectedCart.setWatch(watch);
        expectedCart.setUserId("Yogesh123");
        expectedCart.setWatchQty(1);

        when(watchService.getWatchByModel(watchModel)).thenReturn(watch);
        when(cartRepository.findByUserIdAndWatch("Yogeshs", watch)).thenReturn(Optional.of(expectedCart));
        when(cartRepository.save(expectedCart)).thenReturn(expectedCart);
        
        Cart cart = cartService.addCart(userId, watchModel);
        
        assertNotNull(cart);
        assertEquals(watch, cart.getWatch());
        assertEquals(userId, cart.getUserId());
        assertEquals(1, cart.getWatchQty());

        verify(cartRepository).save(cart);
    }
    
    /**
     * Test case for adding a cart when the cart is not exists.
     */
    @Test
    public void addCart_WhenCartIsNotExists_ReturnsCart() {
        String userId = "Yogesh";
        String watchModel = "Analog-123";
        Watch watch = new Watch();
        watch.setStockQuantity(10);
        
        Cart expectedCart=new Cart();
        expectedCart.setWatch(watch);
        expectedCart.setUserId("Yogesh");
        expectedCart.setWatchQty(1);

        when(watchService.getWatchByModel(watchModel)).thenReturn(watch);
        when(cartRepository.findByUserIdAndWatch(userId, watch)).thenReturn(Optional.of(expectedCart));
        when(cartRepository.save(expectedCart)).thenReturn(expectedCart);
        
        Cart cart = cartService.addCart(userId, watchModel);
        
        assertNotNull(cart);
        assertEquals(watch, cart.getWatch());
        assertEquals(userId, cart.getUserId());
        assertEquals(2, cart.getWatchQty());

        verify(cartRepository).save(cart);
    }

    /**
     * Test case for adding a cart when the watch does not exist.
     */
    @Test
    public void addCart_WhenWatchDoesNotExist_ThrowsBadRequestException() {
        String userId = "Yogesh";
        String watchModel = "Analog-123";

        when(watchService.getWatchByModel(watchModel)).thenThrow(new NotFoundException("Watch model number", "Watch not found"));

        assertThrows(BadRequestException.class, () -> cartService.addCart(userId, watchModel));

        verify(cartRepository, never()).save(any());
    }

    /**
     * Test case for updating the cart quantity when the cart exists and a valid quantity is provided.
     */
    @Test
    public void updateCartQty_WhenCartExistsAndValidQuantity_UpdatesCart() {
        int cartId = 1;
        int watchQty = 5;
        Cart cart = new Cart();
        Watch watch = new Watch();
        watch.setStockQuantity(10);
        cart.setWatch(watch);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        
        Cart updatedCart = cartService.updateCartQty(cartId, watchQty);

        assertNotNull(updatedCart);
        assertEquals(watchQty, updatedCart.getWatchQty());

        verify(cartRepository).save(cart);
    }
    
    /**
     * Test case for updating the cart quantity when the cart exists and an invalid quantity is provided.
     */
    @Test
    public void updateCartQty_WhenCartExistsAndInValidQuantity_ThrowsBadRequestException() {
        int cartId = 1;
        int watchQty = 5;
        Cart cart = new Cart();
        Watch watch = new Watch();
        watch.setStockQuantity(4);
        cart.setWatch(watch);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        assertThrows(BadRequestException.class, () -> cartService.updateCartQty(cartId, watchQty));
    }

    /**
     * Test case for updating the cart quantity when the cart does not exist.
     */
    @Test
    public void updateCartQty_WhenCartDoesNotExist_ThrowsNotFoundException() {
        int cartId = 1;
        int watchQty = 5;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cartService.updateCartQty(cartId, watchQty));

        verify(cartRepository, never()).save(any());
    }
}
