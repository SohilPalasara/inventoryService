package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.dto.WareHouseDto;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.service.impl.WareHouseServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/WareHouse")
public class WareHouseController {
    @Autowired
    private WareHouseServiceImpl wareHouseServiceimpl;

    @PostMapping("/register")
    public ResponseModel registerWareHouse(@RequestBody WareHouseDto wareHouseDto) {

        return wareHouseServiceimpl.registerWareHouse(wareHouseDto);
    }
    @GetMapping
    public ResponseEntity<?> getAllWareHouse() {
        return wareHouseServiceimpl.getAllWareHouse();
    }

    @GetMapping("/{wareHouseId}")
    public ResponseEntity<?> getWareHouseById(@PathVariable String wareHouseId) {
        return wareHouseServiceimpl.getByWareHouse(wareHouseId);
    }
    @DeleteMapping("/{wareHouseId}")
    public ResponseModel deleteWareHouse(@PathVariable String wareHouseId) {

        return wareHouseServiceimpl.deleteWareHouse(wareHouseId);
    }

    @PutMapping("/{wareHouseId}")
    public ResponseModel updateWareHouse(@PathVariable String wareHouseId, @RequestBody WareHouseDto wareHouseDto) {

        return wareHouseServiceimpl.updateWareHouse(wareHouseId,wareHouseDto);
    }
    @GetMapping("/wareHouse")
    public ResponseEntity<?> searchWareHouse(@RequestParam("search") String value) {

        return wareHouseServiceimpl.searchWareHouse(value);
    }
    @PutMapping("/{userId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable String wareHouseId, @PathVariable Status status) {

        return wareHouseServiceimpl.updateWareHouseStatus(wareHouseId, status);
    }
}
