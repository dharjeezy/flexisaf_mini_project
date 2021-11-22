package com.example.flexisaf.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Damilare
 * 22/11/2021
 **/
@Getter
@Setter
@NoArgsConstructor
public class Response {
    public boolean status;
    public boolean success;
    public String message;
    public Object data;

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
