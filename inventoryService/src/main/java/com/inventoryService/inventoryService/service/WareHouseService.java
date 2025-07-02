package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.WareHouseDto;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface WareHouseService {
    ResponseModel registerWareHouse(WareHouseDto wareHouseDto);
    public ResponseEntity<?> getAllWareHouse();
    public ResponseEntity<?> getByWareHouse(String wareHouseId);
    public ResponseModel deleteWareHouse(String wareHouseId);
    public ResponseModel updateWareHouse(String wareHouseId, WareHouseDto wareHouseDto);

}
