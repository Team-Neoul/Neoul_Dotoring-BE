package com.theZ.dotoring.app.mento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "jwt.secretKey=yourtestsecretkeydotoring123459393dsafsdfasadf",
        "jwt.accessTokenExp=86400000",
        "jwt.refreshTokenExp=432000000",
        "cloud.aws.credentials.accessKey=yourtestaccesskeydotoring123459393",
        "cloud.aws.credentials.secretKey=yourtestsecretkeydotoring123459393",
        "cloud.aws.s3.bucket=yourtestbucketdotoring123459393",
        "profileUrl=https://your-test-bucket-dotoring-12345-9393.s3.ap-northeast-2.amazonaws.com/"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Sql("classpath:db/init.sql")
class MentoControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private QueryDesiredFieldRepository queryDesiredFieldRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("해당 멘티의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어, 공모전이다." +
            "size는 10이다." +
            "last는 true이다.")
    @WithUserDetails(value = "dotoring1")
    void findAllMento() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/mento")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMentoBySlice_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("도레미1"));
        resultActions.andExpect(jsonPath("$.response.last").value(true));
        resultActions.andExpect(jsonPath("$.response.size").value(10));
    }

    @Test
    @DisplayName("해당 멘티의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어, 공모전이다." +
            "size는 5이다." +
            "last는 false이다.")
    @WithUserDetails(value = "dotoring1")
    void findAllMento_first_size5() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/mento")
                        .param("size","5")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMentoBySlice_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("도레미1"));
        resultActions.andExpect(jsonPath("$.response.last").value(false));
        resultActions.andExpect(jsonPath("$.response.size").value(5));
    }

    @Test
    @DisplayName("해당 멘티의 전공은 소프트웨어공학과, 수학교육과이고, 관심 분야는 진로, 개발_언어, 공모전이다." +
            "size는 5이다." +
            "last는 true이다.")
    @WithUserDetails(value = "dotoring1")
    void findAllMento_last_size5() throws Exception {

        ResultActions resultActions = mockMvc.perform(
                get("/api/mento")
                        .param("size","5")
                        .param("lastMentoId","16")
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findAllMentoBySlice_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.pageable.nickname").value("도레미1"));
        resultActions.andExpect(jsonPath("$.response.last").value(true));
        resultActions.andExpect(jsonPath("$.response.size").value(5));
    }

    @Test
    @WithUserDetails(value = "dotoring1")
    void findMentoById() throws Exception {

        String mentoId = "11";

        ResultActions resultActions = mockMvc.perform(
                get("/api/mento/" + mentoId)
                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("findMentoById_test : " + responseBody);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.success").value(true));
        resultActions.andExpect(jsonPath("$.response").exists());
        resultActions.andExpect(jsonPath("$.response.grade").value(3));
    }

}