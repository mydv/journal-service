package com.mohity.journalservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private JournalService journalService;

    @KafkaListener(topics = "user-events", groupId = "journaling-group")
    public void consumeUserEvent(UserEvent event) throws JsonProcessingException {
        System.out.println("Received message: " + event);
        journalService.saveJournalEntry(event);
    }
}
