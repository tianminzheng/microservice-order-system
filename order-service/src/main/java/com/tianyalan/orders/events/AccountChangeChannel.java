package com.tianyalan.orders.events;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AccountChangeChannel {
	
    @Input("inboundAccountChanges")
    SubscribableChannel accountChangeChannel();
}
