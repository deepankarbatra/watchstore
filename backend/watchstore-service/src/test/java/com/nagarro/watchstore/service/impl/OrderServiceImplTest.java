package com.nagarro.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dao.OrderDao;
import com.nagarro.watchstore.entity.Address;
import com.nagarro.watchstore.entity.DeliveryAddress;
import com.nagarro.watchstore.entity.Order;
import com.nagarro.watchstore.entity.User;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.enums.OrderStatus;
import com.nagarro.watchstore.enums.UserRole;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.AddressService;
import com.nagarro.watchstore.service.DeliveryAddressService;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.service.WatchService;


/**
 * This class is used for testing methods of OrderServiceImpl class 
 * 
 * @author tushar01
 *
 */

public class OrderServiceImplTest {

	@Mock
	private WatchService watchService;

	@Mock
	private UserService userService;

	@Mock
	private AddressService addressService;

	@Mock
	private DeliveryAddressService deliveryAddressService;

	@Mock
	private OrderDao orderDao;

	@InjectMocks
	private OrderServiceImpl orderService;

	private Watch watch;
	private Address address;
	private User user;
	private DeliveryAddress deliveryAddress;
	private int quantity;
	private Order actualOrder;
	private Order expectedOrder;

	private String orderId;

	private String userEmailId;

	private UserRole role;
	private OrderStatus orderStatus;

	private Order order;
	private boolean isOrderStatusUpdated;
	private List<Order> expectedOrders;
	private List<Order> actualOrders;
	

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		watch=null;
		address=null;
		user=null;
		deliveryAddress=null;
		quantity=0;
		actualOrder=null;
		expectedOrder=null;
		orderId=null;
		userEmailId=null;
		role=null;
		orderStatus=null;
		order=null;
		isOrderStatusUpdated=false;
		expectedOrders=new ArrayList<>();
		actualOrders=new ArrayList<>();
	}
	
	/**
	 * This test case is used to test add() method when order is to be
	 * delivered on an address that already exists in database.
	 */
	@Test
	public void testAddWhenDeliveryAddressExistsInDB() {
		givenValues("model",1L,"user@example.com",2);
		whenAddIsCalledAndDeliveryAddressExists();
		thenVerify();
	}

	/**
	 * This test case is used to test add() method when order is to be
	 * delivered on an address that doesn't exists in database.
	 */
	@Test
	public void testAddWhenDeliveryAddressNotExistsInDB() {
		givenValues("model",1L,"user@example.com",2);
		whenAddIsCalledAndDeliveryAddressNotExists();
		thenVerify();
	}
	

	/**
	 * This test case is used to test add() method when order is placed
	 * on a watch that doesn't exists in database.
	 */
	@Test
	public void testAddWhenWatchNotExistsInDB() {
		givenValues("model",1L,"user@example.com",2);
		whenAddIsCalledAndWatchNotExists();
		thenVerifyNull();
	}

	/**
	 * This test case is used to test add() method when an order quantity 
	 * placed on a watch is greater than its stock.
	 */
	@Test
	public void testAddWhenQuantityGreaterThanStock() {
		givenValues("model",1L,"user@example.com",4);
		whenAddIsCalledAndQuantityGreaterThanStock();
		thenVerifyNull();
	}
	

	/**
	 * This test case is used to test add() method when an order quantity 
	 * placed on a watch is equal to its stock.
	 */
	@Test
	public void testAddWhenQuantityEqualsToStock() {
		givenValues("model",1L,"user@example.com",3);
		whenAddIsCalledAndDeliveryAddressExists();
		thenVerify();
	}
	
	/**
	 * This test case is used to test findById() method for user
	 * whose role is customer.
	 */
	@Test
	public void testFindByIdWhenUserIsCustomer() {
		givenValuesForFindById("order id","user@example.com",UserRole.CUSTOMER);
		whenFindByIdCalledByCustomer();
		thenVerify();
	}

	/**
	 * This test case is used to test findById() method for user
	 * whose role is admin.
	 */
	@Test
	public void testFindByIdWhenUserIsAdmin() {
		givenValuesForFindById("order id","user@example.com",UserRole.ADMIN);
		whenFindByIdCalledByAdmin();
		thenVerify();
	}

	/**
	 * This test case is used to test findById() when
	 * an order not found for given id.
	 */
	@Test
	public void testFindByIdWhenOrderNotFound() {
		givenValuesForFindById("order id","user@example.com",UserRole.ADMIN);
		whenFindByIdCalledAndOrderNotFound();
		thenVerifyNull();
	}
	
	/**
	 * This test case is used to test findAllByUser() method for a user
	 * whose role is customer.
	 */
	@Test
	public void testFindAllByUserWhenUserIsCustomer() {
		givenValuesForFindAllByUser("user@example.com",UserRole.CUSTOMER);
		whenFindAllCalledByCustomer();
		thenVerifyList();
	}
	
	/**
	 * This test case is used to test findAllByUser() method for a user
	 * whose role is admin.
	 */
	@Test
	public void testFindAllByUserWhenUserIsAdmin() {
		givenValuesForFindAllByUser("user@example.com",UserRole.ADMIN);
		whenFindAllCalledByAdmin();
		thenVerifyList();
	}
	
	/**
	 * This test case is used to test findAllByUser() method when no orders
	 * are present.
	 */
	@Test
	public void testFindAllByUserWhenNoOrdersPresent() {
		givenValuesForFindAllByUser("user@example.com",UserRole.ADMIN);
		whenFindAllCalledAndOrdersNotFound();
		thenVerifyEmptyList();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from BOOKED to IN_PROGRESS.
	 */
	@Test
	public void testUpdateOrderStatusForSuccessfulInProgress(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.BOOKED,OrderStatus.IN_PROGRESS);
		whenUpdateOrderStatusCalled();
		thenVerifyTrue();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from IN_SHIPMENT to IN_PROGRESS.
	 */
	@Test
	public void testUpdateOrderStatusForUnSuccessfulInProgress(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.IN_SHIPMENT,OrderStatus.IN_PROGRESS);
		whenUpdateOrderStatusCalledForInvalidStatus();
		thenVerifyFalse();
	}

	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from IN_PROGRESS to IN_SHIPMENT.
	 */
	@Test
	public void testUpdateOrderStatusForSuccessfulInShipment(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.IN_PROGRESS,OrderStatus.IN_SHIPMENT);
		whenUpdateOrderStatusCalled();
		thenVerifyTrue();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from BOOKED to IN_SHIPMENT.
	 */
	@Test
	public void testUpdateOrderStatusForUnSuccessfulInShipment(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.BOOKED,OrderStatus.IN_SHIPMENT);
		whenUpdateOrderStatusCalledForInvalidStatus();
		thenVerifyFalse();
	}

	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from IN_SHIPMENT to DELIVERED.
	 */
	@Test
	public void testUpdateOrderStatusForSuccessfulDelivered(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.IN_SHIPMENT,OrderStatus.DELIVERED);
		whenUpdateOrderStatusCalled();
		thenVerifyTrue();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from BOOKED to DELIVERED.
	 */
	@Test
	public void testUpdateOrderStatusForUnSuccessfulDelivered(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.BOOKED,OrderStatus.DELIVERED);
		whenUpdateOrderStatusCalledForInvalidStatus();
		thenVerifyFalse();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order from IN_SHIPMENT to BOOKED.
	 */
	@Test
	public void testUpdateOrderStatusForBooked(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.IN_SHIPMENT,OrderStatus.BOOKED);
		whenUpdateOrderStatusCalledForInvalidStatus();
		thenVerifyFalse();
	}
	
	/**
	 * This test case is used to test updateOrderStatus() method when admin try to change
	 * status of an order by its id and order not found for that id.
	 */
	@Test
	public void testUpdateOrderStatusWhenOrderNotFound(){
		givenValuesForUpdateOrderStatus("order Id",OrderStatus.IN_SHIPMENT,OrderStatus.DELIVERED);
		whenUpdateOrderStatusCalledForInvalidOrderId();
		thenVerifyFalse();
	}

	private void whenUpdateOrderStatusCalledForInvalidOrderId() {
		assertThrows(BadRequestException.class, ()->whenUpdateOrderStatusCalledForIncorrectOrderId(),Constant.ORDER_NOT_FOUND + orderId);
	}
	
	private void whenUpdateOrderStatusCalledForIncorrectOrderId() throws BadRequestException{
		when(this.orderDao.findById(orderId)).thenReturn(Optional.empty());
		this.isOrderStatusUpdated=this.orderService.updateOrderStatus(orderId, orderStatus);
	}
	private void thenVerifyFalse() {
		assertFalse(isOrderStatusUpdated);
	}
	private void whenUpdateOrderStatusCalledForInvalidStatus() {
		assertThrows(BadRequestException.class, ()->whenUpdateOrderStatusCalledForIncorrectStatus(), Constant.INVALID_STATUS);
	}
	
	private void whenUpdateOrderStatusCalledForIncorrectStatus() throws BadRequestException{
		when(this.orderDao.findById(orderId)).thenReturn(Optional.of(order));
		this.isOrderStatusUpdated=this.orderService.updateOrderStatus(orderId, orderStatus);
	
	}
	private void thenVerifyTrue() {
		assertTrue(isOrderStatusUpdated);
	}

	private void whenUpdateOrderStatusCalled() {
		when(this.orderDao.findById(orderId)).thenReturn(Optional.of(order));
		this.isOrderStatusUpdated=this.orderService.updateOrderStatus(orderId, orderStatus);
	}

	private void givenValuesForUpdateOrderStatus(String orderId,OrderStatus previousStatus, OrderStatus status) {
		this.orderId=orderId;
		this.orderStatus=status;
		order=new Order();
		order.setStatus(previousStatus);
		
	}

	private void whenFindAllCalledAndOrdersNotFound() {
		assertThrows(NotFoundException.class,()->whenFindAllCalledAndOrdersAreEmpty(),Constant.ORDERS_NOT_FOUND_FOR_EMAIL_ID);
	}

	private void thenVerifyEmptyList() {
		assertTrue(this.actualOrders.isEmpty());
	}

	private void whenFindAllCalledAndOrdersAreEmpty() throws NotFoundException {
		when(this.orderDao.findAllByOrderByTimestampDesc()).thenReturn(new ArrayList<Order>());
		this.actualOrders=this.orderService.findAllByUser(userEmailId, role);
	}

	private void whenFindAllCalledByAdmin() {
		when(this.orderDao.findAllByOrderByTimestampDesc()).thenReturn(expectedOrders);
		this.actualOrders=this.orderService.findAllByUser(userEmailId, role);
	}

	private void thenVerifyList() {
		assertArrayEquals(expectedOrders.toArray(), actualOrders.toArray());
	}

	private void whenFindAllCalledByCustomer() {
		when(this.orderDao.findByUserEmailIdOrderByTimestampDesc(userEmailId)).thenReturn(expectedOrders);
		this.actualOrders=this.orderService.findAllByUser(userEmailId, role);
	}

	private void givenValuesForFindAllByUser(String userEmailId, UserRole role) {
		this.userEmailId=userEmailId;
		this.role=role;
		
		expectedOrders=new ArrayList<>();
		
		Order order1=new Order();
		order1.setAmount(BigDecimal.valueOf(400));
		order1.setQuantiy(4);
		order1.setStatus(OrderStatus.BOOKED);
		
		DeliveryAddress deliveryAddress=new DeliveryAddress();
		deliveryAddress.setId(1L);
		
		Watch watch=new Watch();
		watch.setModelNumber("model");
		
		User user=new User();
		user.setEmailId("user@example.com");
		
		order1.setDeliveryAddress(deliveryAddress);
		order1.setUser(user);
		order1.setWatch(watch);
		
		expectedOrders.add(order1);
 	}

	private void whenFindByIdCalledAndOrderNotFound() {
		assertThrows(NotFoundException.class, ()->whenFindByIdCalledForIncorrectId(), Constant.ORDER_NOT_FOUND);
	}

	private void whenFindByIdCalledForIncorrectId() throws NotFoundException{
		when(this.orderDao.findById(orderId)).thenReturn(Optional.empty());
		this.actualOrder=this.orderService.findById(orderId, userEmailId, role);
	}
	private void whenFindByIdCalledByAdmin() {
		when(this.orderDao.findById(orderId)).thenReturn(Optional.of(this.expectedOrder));
		this.actualOrder=this.orderService.findById(orderId, userEmailId, role);
		
	}

	private void whenFindByIdCalledByCustomer() {
		when(this.orderDao.findByIdAndUserEmailId(orderId,userEmailId)).thenReturn(Optional.of(this.expectedOrder));
		this.actualOrder=this.orderService.findById(orderId, userEmailId, role);
	}

	private void givenValuesForFindById(String orderId,String userEmailId, UserRole role) {
		this.orderId=orderId;
		this.userEmailId=userEmailId;
		this.role=role;
		givenValues("model", 1L, userEmailId, 1);
	}

	private void whenAddIsCalledAndWatchNotExists() {
		assertThrows(BadRequestException.class,()->whenAddisCalledForInvalidWatchModel(),"Watch Not Found");
	}


	private void thenVerifyNull() {
		assertNull(actualOrder);
	}

	private void whenAddisCalledForInvalidWatchModel() throws BadRequestException{
		when(watchService.getWatchByModel(this.watch.getModelNumber())).thenThrow(new NotFoundException("modelNumber", "Watch Not Found"));
		this.actualOrder=this.orderService.add(this.watch.getModelNumber(), this.deliveryAddress.getId(), this.user.getEmailId(), this.quantity);
	}

	private void whenAddIsCalledAndQuantityGreaterThanStock() {
		assertThrows(BadRequestException.class, ()->whenAddIsCalledForInvalidQuantity(),Constant.QUANTITY_GREATER_THAN_STOCKS);
	}

	private void whenAddIsCalledForInvalidQuantity() throws BadRequestException {
		when(watchService.getWatchByModel(this.watch.getModelNumber())).thenReturn(this.watch);
		when(watchService.getWatchByModel(this.watch.getModelNumber())).thenReturn(this.watch);
		when(addressService.findByIdAndUserId(this.address.getAddressId(),this.user.getEmailId())).thenReturn(this.address);
		when(userService.findUserById(this.user.getEmailId())).thenReturn(this.user);
		this.actualOrder=this.orderService.add(this.watch.getModelNumber(), this.deliveryAddress.getId(), this.user.getEmailId(), this.quantity);
		
	}

	private void thenVerify() {
		assertNotNull(actualOrder);
		assertEquals(OrderStatus.BOOKED, actualOrder.getStatus());
		assertEquals(watch.getModelNumber(), actualOrder.getWatch().getModelNumber());
		assertEquals(user.getEmailId(), actualOrder.getUser().getEmailId());
		assertEquals(deliveryAddress.getId(), actualOrder.getDeliveryAddress().getId());
		assertEquals(watch.getPrice(), actualOrder.getAmount());
		assertEquals(quantity, actualOrder.getQuantiy());
		assertEquals(watch.isAvailableStatus(), actualOrder.getWatch().isAvailableStatus());
	}

	private void whenAddIsCalledAndDeliveryAddressNotExists() {
		
		when(watchService.getWatchByModel(this.watch.getModelNumber())).thenReturn(this.watch);
		when(addressService.findByIdAndUserId(this.address.getAddressId(),this.user.getEmailId())).thenReturn(this.address);
		when(userService.findUserById(this.user.getEmailId())).thenReturn(this.user);
		when(deliveryAddressService.findByAddressId(this.deliveryAddress.getId())).thenReturn(Optional.empty());
		when(deliveryAddressService.save(this.deliveryAddress)).thenReturn(this.deliveryAddress);
		when(orderDao.save(expectedOrder)).thenReturn(expectedOrder);
		
		this.actualOrder=this.orderService.add(this.watch.getModelNumber(), this.deliveryAddress.getId(), this.user.getEmailId(), this.quantity);
	}

	private void whenAddIsCalledAndDeliveryAddressExists() {
		
		when(watchService.getWatchByModel(this.watch.getModelNumber())).thenReturn(this.watch);
		when(addressService.findByIdAndUserId(this.address.getAddressId(),this.user.getEmailId())).thenReturn(this.address);
		when(userService.findUserById(this.user.getEmailId())).thenReturn(this.user);
		when(deliveryAddressService.findByAddressId(this.deliveryAddress.getId())).thenReturn(Optional.of(this.deliveryAddress));
		when(orderDao.save(expectedOrder)).thenReturn(expectedOrder);
		
		this.actualOrder=this.orderService.add(this.watch.getModelNumber(), this.deliveryAddress.getId(), this.user.getEmailId(), this.quantity);
	}

	private void givenValues(String watchModel, long deliveryAddressId, String userEmailId, int quantity) {
		int stockQuantity=3;

		this.quantity=quantity;
		
		this.watch = new Watch();
		watch.setModelNumber(watchModel);
		watch.setStockQuantity(stockQuantity);
		watch.setAvailableStatus(true);
		
		this.address = new Address();
		address.setAddressId(deliveryAddressId);

		this.user = new User();
		user.setEmailId(userEmailId);

		this.deliveryAddress = new DeliveryAddress();
		deliveryAddress.setId(1L);
		
		
		expectedOrder=new Order();
		expectedOrder.setStatus(OrderStatus.BOOKED);
		expectedOrder.setWatch(watch);
		expectedOrder.setUser(user);
		expectedOrder.setDeliveryAddress(deliveryAddress);
		expectedOrder.setAmount(watch.getPrice());
		expectedOrder.setQuantiy(quantity);


	}
}
