package com.mohity.journalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    @Override
    public List<JournalEntry> getJournalEntriesByUserId(long id) {
        return journalEntryRepository.findByUserId(id);
    }
}
