package com.mohity.journalservice;

import java.util.List;

public interface JournalService {
    void saveJournalEntry(UserEvent event);

    List<JournalEntry> getAllJournalEntries();

    List<JournalEntry> getJournalEntriesByUserId(long id);
}
