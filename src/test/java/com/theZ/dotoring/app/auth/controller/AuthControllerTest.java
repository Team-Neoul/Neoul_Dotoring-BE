package com.theZ.dotoring.app.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;
    @Test
    @DisplayName("멘티 로그인 테스트")
    void login_test() throws Exception {
        // given

        String loginId = "dotoring92";
        String password = "dotoring92@@";

        // when

        RequestDTO requestDTO = new RequestDTO(loginId, password);
        String requestBody = om.writeValueAsString(requestDTO);

        // then

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/member/login")
                        .content(requestBody)
                        .contentType("application/json")
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

    }

    @Test
    @DisplayName("잘못된 AccessToken을 줄 때")
    void invalid_accessToken_test() throws Exception {
        // given

        String accessToken = "112e3";


        // then

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/menti/3")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error.code").value(9000));

    }


    class RequestDTO {
        private String loginId;
        private String password;

        public RequestDTO(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}