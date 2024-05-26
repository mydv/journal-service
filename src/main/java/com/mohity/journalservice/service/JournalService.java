package com.mohity.journalservice.service;

import com.mohity.journalservice.model.JournalEntry;
import com.mohity.journalservice.dto.UserEvent;

import java.util.List;

public interface JournalService {
    void saveJournalEntry(UserEvent event);

    List<JournalEntry> getAllJournalEntries();

    List<JournalEntry> getJournalEntriesByUserId(long id);
}
