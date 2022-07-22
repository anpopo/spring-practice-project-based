package anpopo.powerboard.controller;

import anpopo.powerboard.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("메인 페이지 테스트")
@Import(SecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("메인 페이지로 접근하면 리스트 페이지로 리다이렉션 시킨다.")
    @Test
    void mainRootPathRedirection() throws Exception {
        mvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }
}