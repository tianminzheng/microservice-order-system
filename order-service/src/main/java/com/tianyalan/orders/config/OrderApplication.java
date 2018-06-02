//package com.tianyalan.orders.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootApplication
//@EnableEurekaClient
//@RibbonClient(name = "productservice", configuration = RibbonConfig.class)
//public class OrderApplication {
//
//	@Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(OrderApplication.class, args);
//    }
//    
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
//        
//    public void userLoadBalancerClient() {
//    	ServiceInstance serviceInstance 
//    		= loadBalancerClient.choose("productservice");
//    	
//    	serviceInstance.getServiceId();
//    	serviceInstance.getHost();
//    	serviceInstance.getPort();
//    	serviceInstance.getUri();
//    }
//}
