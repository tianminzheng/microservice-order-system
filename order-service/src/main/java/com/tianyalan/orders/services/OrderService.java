package com.tianyalan.orders.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianyalan.orders.clients.AccountClient;
import com.tianyalan.orders.clients.ProductClient;
import com.tianyalan.orders.model.Account;
import com.tianyalan.orders.model.Order;
import com.tianyalan.orders.model.Product;
import com.tianyalan.orders.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private AccountClient accountClient;

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@HystrixCommand
	private Account getAccount(Long accountId) {

		return accountClient.getAccount(accountId);
	}

	@HystrixCommand
	private Product getProduct(String productCode) {

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return productClient.getProduct(productCode);
	}

	public Order addOrder(Long accountId, String productCode) {
		
		logger.debug("addOrder with account: {} and product: {}", accountId, productCode);
		
		Order order = new Order();

		Product product = getProduct(productCode);
		if (product == null) {
			return order;
		}
		
		logger.debug("get product: {} is successful", productCode);

		Account account = getAccount(accountId);
		if (account == null) {
			return order;
		}
		
		logger.debug("get account: {} is successful", accountId);

		order.setAccountId(accountId);
		order.setItem(product.getProductName());
		order.setCreateTime(new Date());

		orderRepository.save(order);

		return order;
	}

//	@HystrixCommand(commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"),
//			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
//			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
//			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
//			@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5") })
	
	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public List<Order> getOrders(int pageIndex, int pageSize) {
		if (1 == 1) {
			try {
				throw new Exception("An exception has occured!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return orderRepository.findAll(new PageRequest(pageIndex - 1, pageSize)).getContent();
	}

	private List<Order> getOrdersFallback(int pageIndex, int pageSize) {
		List<Order> fallbackList = new ArrayList<>();

		Order order = new Order();
		order.setId(0L);
		order.setAccountId(0L);
		order.setItem("Order list is not available");
		order.setCreateTime(new Date());

		fallbackList.add(order);
		return fallbackList;
	}

	public Order getOrderById(Long id) {
		return orderRepository.findOne(id);
	}
}
