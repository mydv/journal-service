package com.mohity.journalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Override
    public void saveJournalEntry(UserEvent event) {
        JournalEntry journalEntry = JournalEntry.builder()
                .userId(event.getUserId())
                .eventType(event.getEventType())
                .timestamp(event.getTimestamp())
                .build();

        journalEntryRepository.save(journalEntry);
    }
}
