package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import com.inventoryService.inventoryService.enums.Department;
import com.inventoryService.inventoryService.enums.Gender;
import com.inventoryService.inventoryService.enums.Role;
import com.inventoryService.inventoryService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String fullName;
    private String mobileNumber;
    private String password;
    private String email;
    private String profilePicture;
    private Department department;
    private Status status;
    private  String organizationId;
    private Role role;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public User convertToEntity() {
        Organization org = Organization.builder()
                .organizationId(this.organizationId)
                .build();
        return User.builder()
                .fullName(this.fullName)
                .mobileNumber(this.mobileNumber)
                .password(this.password)
                .email(this.email)
                .profilePicture(this.profilePicture)
                .department(this.department)
                .status(Status.ACTIVE)
                .role(this.role)
                .gender(this.gender)
                .organization(org)
                .build();
    }
    public static UserDto convertToDto(User user) {

            return

                    UserDto.builder()
                            .fullName(user.getFullName())
                            .mobileNumber(user.getMobileNumber())
                            .password(null)
                            .email(user.getEmail())
                            .profilePicture(user.getProfilePicture())
                            .department(user.getDepartment())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .status(user.getStatus())
                            .role(user.getRole())
                            .gender(user.getGender())
                            .organizationId(
                                    user.getOrganization() != null ? user.getOrganization().getOrganizationId() : null
                            )
                            .build();
        }



    public void updateEntity(User user) {
        if (this.fullName!= null) {
            user.setFullName(this.fullName);
        }
        if (this.mobileNumber!= null) {
           user.setMobileNumber(this.mobileNumber);
        }
        if (this.password != null) {
            user.setPassword(this.password);
        }
        if (this.email != null) {
            user.setEmail(this.email);
        }
        if (this.profilePicture!= null) {
            user.setProfilePicture(this.profilePicture);
        }
        if (this.department!= null) {
            user.setDepartment(this.department);
        }
        if (this.status != null) {
            user.setStatus(Status.ACTIVE);
        }
        if (this.role!= null) {
            user.setRole(this.role);
        }
        if (this.gender != null) {
            user.setGender(this.gender);
        }


    }
}
