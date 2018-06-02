package com.tianyalan.account.events.source;

import com.tianyalan.account.event.models.AccountChangedEvent;
import com.tianyalan.account.model.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountChangeSource {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(AccountChangeSource.class);
  
    @Autowired
    public AccountChangeSource(Source source){
        this.source = source;
    }

    private void publishAccountChange(String action, Account account){
    	logger.debug("Sending message for Account Id: {}", account.getId());
    	
        AccountChangedEvent change =  new AccountChangedEvent(
    		   AccountChangedEvent.class.getTypeName(),
               action,
               account);

        source.output().send(MessageBuilder.withPayload(change).build());
    }
    
    public void publishAccountAddedEvent(Account account) {
    	publishAccountChange("SAVE", account);
    }
    
    public void publishAccountUpdatedEvent(Account account) {
    	publishAccountChange("UPDATE", account);
    }
    
    public void publishAccountDeletedEvent(Account account) {
    	publishAccountChange("DELETE", account);
    }
}
