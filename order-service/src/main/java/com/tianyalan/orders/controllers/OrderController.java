package com.tianyalan.orders.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.orders.model.Order;
import com.tianyalan.orders.services.OrderService;

@RestController
@RequestMapping(value="v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
	
	@RequestMapping(value = "/{accountId}/{productCode}", method = RequestMethod.POST)
	public Order saveOrder( @PathVariable("accountId") Long accountId,
            @PathVariable("productCode") String productCode) {
		Order order = orderService.addOrder(accountId, productCode);		
		
		return order;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		
    	return order;
    }
	
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public List<Order> getOrderList( @PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
		List<Order> orders = orderService.getOrders(pageIndex, pageSize);
		
		return orders;
	}
	
}
