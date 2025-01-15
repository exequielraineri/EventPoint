/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exeraineri.eventpoint.backend.payload;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Exequiel
 * @param <T>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> {

    private int status;
    private T data;
    private String message;
    private LocalDateTime timestamp;

}
