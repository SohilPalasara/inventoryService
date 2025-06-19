package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.OrganizationDto;

import com.inventoryService.inventoryService.service.impl.OrganizationServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationServiceImpl organizationServiceImpl;

    @PostMapping("/register")
    public ResponseModel registerOrganization(@RequestBody OrganizationDto organizationDto) {
        return organizationServiceImpl.registerOrganization(organizationDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrganizations() {
        return organizationServiceImpl.getAllOrganization();
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable String organizationId) {
        return organizationServiceImpl.getByOrganization(organizationId);
    }

    @DeleteMapping("/{organizationId}")
    public ResponseModel deleteOrganization(@PathVariable String organizationId) {

        return organizationServiceImpl.deleteOrganization(organizationId);
    }
    @PutMapping("/{organizationId}")
    public ResponseModel updateOrganization(@PathVariable String organizationId , @RequestBody OrganizationDto organizationDto) {

        return organizationServiceImpl.updateOrganization(organizationId,organizationDto);
    }

//    @GetMapping("/{type}/{value}")
//    @GetMapping("/{value}") // search on each column
}
