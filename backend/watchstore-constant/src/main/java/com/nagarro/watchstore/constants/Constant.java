package com.nagarro.watchstore.constants;

/**
 * The Constant interface defines public static final variables
 * that represent constants in the application.
 */
public interface Constant {

	String STATUS_ENUM_NOT_VALID="OrderStatus enum is not valid. Valid are: booked,In_shipment,in_progress,delivered";
	String ADDRESS_NOT_FOUND="There is no address with given address Id: ";
	String WATCH_NOT_FOUND="There is no watch with watch model number: ";
	String USER_NOT_FOUND="There is no user with given email Id: ";
	String ADRESS_ID_REQUIRED="Address Id is required";
	String USER_EMAIL_ID_REQUIRED="User Email Id is required";
	String WATCH_MODEL_REQUIRED="Watch Model no. is required";
	String QUANTITY_REQUIRED="Quantity is required";
	String INVALID_QUANTITY="Quantity can't be less than 0";
	String ORDER_BOOKED="Order is successfully booked against this id: ";
	String EMPTY_ORDERS = "There are no orders present";
	String ORDERS_NOT_FOUND_FOR_EMAIL_ID = "There are no orders found for given user email id";
	String ORDER_NOT_FOUND = "There is no order present for given order id";
	String ORDER_STATUS_REQUIRED = "Order status is required";
	String INVALID_STATUS = "Can't update status to given status";
	String STATUS_UPDATED = "Order Status updated successfully";
	String QUANTITY_GREATER_THAN_STOCKS = "Quantity can't be greater than stock quantity";
	String ERROR_MESSAGE_WATCH="The 'Watch Model' parameter is required.";
	String CART_NOT_FOUND="There is no cart with given Id: ";
	String PHONE_NUMBER_REGEX = "\\d{10}";
	String PIN_CODE_REGEX = "\\d{6}";
	String MODEL_NUMBER = "modelNumber";
	String NO_WATCH = "No watch found with model no";
	String NOTIFICATION_EXISTS = "user have already created notification";
	String ALREADY_IN_STOCK = "watch is already in stock";
	String NOTIFICATION_SENT_SUCCESS = "Sent the notification successfully";
	String NO_NOTIFICATION_FOUND = "No Notification found for this watch";
	String NOT_IN_STOCK = "This watch is not in stock";
	String ADDED_IN_STOCK = " added in stock";
	String NOTIFICATION_UPDATE_SUCCESS = "Notificatin Status updated Successfully";
	String NO_NOTIFICATION_BY_USER = "User have not created any notification";
	String EMAIL_ID = "userId";
	String ADD = "Please add ";
	String IN_STOCK = " in stock ";
	String INVALID_EMAIL_FORMAT = "Invalid Email Format: ";
	String USER_REGISTER_SUCCESSFULLY = "User Registered Succesfully: ";
	String REQUEST_TO_SAVE = "Received request to save notification for modelNumber: {}";
	String RESPONSE_OF_SAVE = "Notification saved successfully for modelNumber: {}";
	String REQUEST_FOR_UPDATE ="Received request to update notification for modelNumber: {}";
	String REQUEST_TO_UPDATE = "Notification updated successfully for modelNumber: {}";
	String REQUEST_TO_RETRIEVE = "Received request to retrieve all notifications for userId: {}";
	String RECIEVED_NOTIFICATIONS = "Retrieved {} notifications for userId: {}";
	String ALL_NOTIFICATION = "Retrieved {} notifications";
	String REQUEST_FOR_ALL_NOTIFICATION = "Received request to retrieve all notifications";
	String INVALID_PASSWORD_FORMAT="Invalid Password Format: ";
	String AUTHENTICATION_FAILED= "Authentication failed due to wrong password: ";
	String INVALID_EMAIL_ARGUMENT="Email argument passed is invalid.";
	String REQUEST_PLACE_ORDER="Received request to place an order for a watch agains model number ";
	String REQUEST_ALL_ORDERS = "Received request for fetching all orders";
	String RESPONSE_ALL_ORDERS="Retrieved all orders";
	String REQUEST_UPDATE_STATUS = "Received request for updating order status";
	String REQUEST_FETCH_ORDER = "Received request to fetch an order against this id ";
	String RESPONSE_FETCH_ORDER = "Retreived order against this id ";
	String REQUEST_FETCH_USER="Received request to fetch an user against its emailId: {}";
	String RESPONSE_FETCH_USER = "Successfully retreived user against this emailId: {}";
	String REQUEST_TO_REGISTER="Received request to register new user: {}";
	String RESPONSE_TO_REGISTER="Response for register new user: {}";
	String USER_ALREADY_EXIST="Already user exist with this email Id {}:";
	String INVALID_USER_ROLE="Invalid value for user role: {}";
	String AUTHENTICATION_FAILED_TOKEN="User details not found in the authentication object.";
	String ADD_ADDRESS_SUCCESSFULL = "Address added successfully!";
	String UPDATE_ADDRESS_SUCCESSFULL = "Address updated successfully!";
	String DELETE_ADDRESS_SUCCESSFULL = "Address deleted successfully!";
}
