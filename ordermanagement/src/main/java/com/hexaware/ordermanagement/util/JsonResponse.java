package com.hexaware.ordermanagement.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <h3>Custom standardized response format to give feedback to client after each HTTP request</h3>
 *
 * <p>Includes a success boolean, a feedback message, and optional data</p>
 *
 * If request was not a success, data returned is null
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResponse {
    private Boolean success;
    private String message;
    private Object data;
}
