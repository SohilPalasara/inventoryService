//package com.inventoryService.inventoryService.dto;
//
//import com.inventoryService.inventoryService.entity.Organization;
//import com.inventoryService.inventoryService.entity.User;
//import com.inventoryService.inventoryService.enums.Status;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class wareHouseDto {
//    private String wareHouseId;
//    private String locationOrArea;
//    private String title;
//    private String description;
//
//    private Status status;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    private User userId;          // only the ID of User
//    private Organization organizationId;  // only the ID of Organization
////    public WareHouse convertToEntity(WareHouseDto dto, User user, Organization organization) {
////        WareHouse wareHouse = new WareHouse();
////        wareHouse.setWareHouseId(dto.getWareHouseId());
////        wareHouse.setLocationOrArea(dto.getLocationOrArea());
////        wareHouse.setTitle(dto.getTitle());
////        wareHouse.setDescription(dto.getDescription());
////        wareHouse.setStatus(dto.getStatus());
////        wareHouse.setDeleted(dto.isDeleted());
////        wareHouse.setUserId(user);
////        wareHouse.setOrganization(organization);
////        return wareHouse;
////    }
//}
