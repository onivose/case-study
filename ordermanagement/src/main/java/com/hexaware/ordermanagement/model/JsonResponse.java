package com.hexaware.ordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResponse {
    private Boolean success;
    private String message;
    private Object data;
}
