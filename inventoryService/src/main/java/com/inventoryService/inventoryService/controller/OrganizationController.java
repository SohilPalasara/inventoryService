package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.service.impl.OrganizationServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationServiceImpl organizationServiceImpl;

    @PostMapping("/register")
    public ResponseModel registerOrganization(@RequestBody OrganizationDto organizationDto) {

        return organizationServiceImpl.registerOrganization(organizationDto);
    }


}
