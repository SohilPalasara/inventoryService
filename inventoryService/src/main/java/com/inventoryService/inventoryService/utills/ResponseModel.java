package com.inventoryService.inventoryService.utills;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private long code;
    private String status;
    private String data;
    private String message;

    public static ResponseModel create(HttpStatus httpStatus, String data, String message) {
        return ResponseModel
                .builder()
                .code(httpStatus.value())
                .status(httpStatus.name())
                .data(data)
                .message(message)
                .build();
    }
}
