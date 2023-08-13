package com.theZ.dotoring.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String message;
    private String code;
    private String status;

}
