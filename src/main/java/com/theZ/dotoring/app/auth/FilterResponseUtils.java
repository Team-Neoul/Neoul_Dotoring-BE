package com.theZ.dotoring.app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import com.theZ.dotoring.common.MessageCode;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {

    public static void unAuthorized(HttpServletResponse response) throws IOException {
        ApiResponse<?> apiResponse = ApiResponseGenerator.fail(MessageCode.REQUIRE_LOGIN.getCode(), MessageCode.REQUIRE_LOGIN.getValue(), HttpStatus.UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
    }

    public static void invalidAccessToken(HttpServletResponse response) throws IOException {
        ApiResponse<?> apiResponse = ApiResponseGenerator.fail(MessageCode.INVALID_ACCESS_TOKEN.getCode(), MessageCode.INVALID_ACCESS_TOKEN.getValue(), HttpStatus.BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
    }

    public static void invalidRefreshToken(HttpServletResponse response) throws IOException {
        ApiResponse<?> apiResponse = ApiResponseGenerator.fail(MessageCode.INVALID_REFRESH_TOKEN.getCode(), MessageCode.INVALID_REFRESH_TOKEN.getValue(), HttpStatus.BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
    }


    public static void reRequest(HttpServletResponse response) throws IOException {
        ApiResponse<?> apiResponse = ApiResponseGenerator.fail(MessageCode.EXPIRED_ACCESS_TOKEN.getCode(), MessageCode.EXPIRED_ACCESS_TOKEN.getValue(), HttpStatus.UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse.getBody()));
    }
}
