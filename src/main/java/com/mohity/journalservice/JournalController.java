package com.mohity.journalservice;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/journals")
public class JournalController {
    @Autowired
    private JournalService journalService;

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> journalEntries = journalService.getAllJournalEntries();
        return ResponseEntity.ok(journalEntries);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesByUserId(@PathVariable Long userId) {
        List<JournalEntry> journalEntries = journalService.getJournalEntriesByUserId(userId);
        if (journalEntries.isEmpty()) {
            throw new ResourceNotFoundException("No journal entries found for user ID: " + userId);
        }
        return ResponseEntity.ok(journalEntries);
    }
}
