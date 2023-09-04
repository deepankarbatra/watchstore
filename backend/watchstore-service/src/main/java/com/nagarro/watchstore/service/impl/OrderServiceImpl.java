package com.nagarro.watchstore.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.nagarro.watchstore.service.OrderService;
import com.nagarro.watchstore.service.UserService;
import com.nagarro.watchstore.service.WatchService;


/**
 * 
 * Implementation of the OrderService interface that provides methods for managing orders.
 * 
 * @author tushar01
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	private final WatchService watchService;
	private final UserService userService;
	private final AddressService addressService;
	private final DeliveryAddressService deliveryAddressService;
	private final OrderDao orderDao;

	@Autowired
	public OrderServiceImpl(final OrderDao orderDao, final WatchService watchService, final UserService userService,
			final AddressService addressService, final DeliveryAddressService deliveryAddressService) {
		this.orderDao = orderDao;
		this.watchService = watchService;
		this.addressService = addressService;
		this.userService = userService;
		this.deliveryAddressService = deliveryAddressService;
	}

	@Override
	public Order add(final String watchModel,final long deliveryAddressId, final String userEmailId, final int quantity) {

		Order order=null;
		try {
			final Watch watch = this.watchService.getWatchByModel(watchModel);
			final Address address = this.addressService.findByIdAndUserId(deliveryAddressId, userEmailId);
			final User user = this.userService.findUserById(userEmailId);

			updateWatch(watch, quantity);
			
			order=new Order();
			order.setAmount(watch.getPrice());
			order.setQuantiy(quantity);
			order.setStatus(OrderStatus.BOOKED);
			order.setWatch(watch);
			order.setUser(user);

			DeliveryAddress deliveryAddress = addDeliveryAddress(address);
			Optional<DeliveryAddress> optionalDeliveryAddress = this.deliveryAddressService
					.findByAddressId(deliveryAddress.getId());
			if (optionalDeliveryAddress.isEmpty()) {
				this.deliveryAddressService.save(deliveryAddress);
			}
			order.setDeliveryAddress(deliveryAddress);

		} catch (NotFoundException exception) {
			throw new BadRequestException(exception.getFieldName(), exception.getMessage());
		}
		return this.orderDao.save(order);
	}

	@Override
	public List<Order> findAllByUser(final String userEmailId, final UserRole role) {
		final List<Order> orders=getAllOrdersBasedOnUserRole(userEmailId,role);
		if(orders.isEmpty())
			throw new NotFoundException("No Orders", Constant.EMPTY_ORDERS);
		return orders;
	}


	@Override
	public boolean updateOrderStatus(final String id, final OrderStatus orderStatus) {
		final Optional<Order> order = this.orderDao.findById(id);
		if (order.isEmpty())
			throw new BadRequestException("orderId", Constant.ORDER_NOT_FOUND + id);

		if (!this.isValidOrderStatus(orderStatus, order.get())) {
			throw new BadRequestException("status", Constant.INVALID_STATUS);
		}
		order.get().setStatus(orderStatus);
		order.get().setStatusTimestamp(LocalDateTime.now());
		this.orderDao.save(order.get());
		return true;
	}


	@Override
	public Order findById(final String orderId, final String userEmailId,final UserRole role) {
		final Optional<Order> order=getOrderBasedOnUserRole(orderId, userEmailId,role);
		if(order.isEmpty())
			throw new NotFoundException("orderId", Constant.ORDER_NOT_FOUND);
		return order.get();
	}


	private DeliveryAddress addDeliveryAddress(final Address address) {
		final DeliveryAddress deliverAddress = new DeliveryAddress();
		deliverAddress.setCity(address.getCity());
		deliverAddress.setCountry(address.getCountry());
		deliverAddress.setId(address.getAddressId());
		deliverAddress.setLandmark(address.getLandmark());
		deliverAddress.setPhoneNumber(address.getPhoneNumber());
		deliverAddress.setPincode(address.getPincode());
		deliverAddress.setState(address.getState());
		deliverAddress.setStreetName(address.getStreetName());
		return deliverAddress;
	}

	private void updateWatch(final Watch watch, final int quantity) {
		final int updatedStockQuantity = watch.getStockQuantity() - quantity;
		if (updatedStockQuantity < 0)
			throw new BadRequestException("quantity", Constant.QUANTITY_GREATER_THAN_STOCKS);
		watch.setStockQuantity(updatedStockQuantity);
		if (updatedStockQuantity == 0)
			watch.setAvailableStatus(false);
	}

	private boolean isValidOrderStatus(final OrderStatus orderStatus, final Order order) {
		boolean isStatusValid = false;
		switch (orderStatus) {
		case IN_PROGRESS:
			if (order.getStatus() == OrderStatus.BOOKED)
				isStatusValid = true;
			break;
		case IN_SHIPMENT:
			if (order.getStatus() == OrderStatus.IN_PROGRESS)
				isStatusValid = true;
			break;
		case DELIVERED:
			if (order.getStatus() == OrderStatus.IN_SHIPMENT)
				isStatusValid = true;
			break;
		default:
			isStatusValid = false;
		}
		return isStatusValid;
	}

	private Optional<Order> getOrderBasedOnUserRole(final String orderId,final String userEmailId,final UserRole role)
	{
		Optional<Order> order=null;
		if(role==UserRole.CUSTOMER)
		{	
			order=this.orderDao.findByIdAndUserEmailId(orderId,userEmailId);
		}
		else
		{
			order=this.orderDao.findById(orderId);
		}
		
		return order;
	}
	
	private List<Order> getAllOrdersBasedOnUserRole(final String userEmailId,final UserRole role) {
		List<Order> orders=null;
		if(role==UserRole.ADMIN)
		{
			orders=this.orderDao.findAllByOrderByTimestampDesc();
		}
		else
		{
			orders=this.orderDao.findByUserEmailIdOrderByTimestampDesc(userEmailId);
		}
		return orders;
	}
}
