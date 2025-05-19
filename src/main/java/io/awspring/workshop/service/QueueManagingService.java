package io.awspring.workshop.service;


import org.springframework.stereotype.Component;



@Component
public class QueueManagingService {

   //private final HashMap<String, SqsMessageListenerContainer> containerCache = new HashMap<>();

    public String createAndStartListener(String queueName) {
        //Create, start and save container.
       return null;
    }


    public String stopListener(String queueName) {
        // stop listening
        return null;
    }
}
