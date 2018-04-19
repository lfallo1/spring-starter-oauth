package com.sinclair.vpreports.spreadsheetrefresh.reports.controller;

import com.sinclair.vpreports.spreadsheetrefresh.config.exception.ReportException;
import com.sinclair.vpreports.spreadsheetrefresh.reports.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/report")
public class ReportController {

    @Autowired
    private ProgramService programService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> refreshReports() throws ReportException {
        this.programService.updateProgramNames();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("RUNNING", HttpStatus.OK);
    }

}
