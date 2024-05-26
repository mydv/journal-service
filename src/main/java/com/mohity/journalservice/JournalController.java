package com.mohity.journalservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retrieve all journal entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JournalEntry.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> journalEntries = journalService.getAllJournalEntries();
        return ResponseEntity.ok(journalEntries);
    }

    @Operation(summary = "Retrieve journal entries by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved journal entries for the user",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JournalEntry.class)))),
            @ApiResponse(responseCode = "404", description = "No journal entries found for the user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesByUserId(@PathVariable Long userId) {
        List<JournalEntry> journalEntries = journalService.getJournalEntriesByUserId(userId);
        if (journalEntries.isEmpty()) {
            throw new ResourceNotFoundException("No journal entries found for user ID: " + userId);
        }
        return ResponseEntity.ok(journalEntries);
    }
}
