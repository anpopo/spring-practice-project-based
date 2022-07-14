package anpopo.powerboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Disabled("spring data rest 통합 테스트는 불필요")
@DisplayName("data rest library api 테스트")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DataRestTest {

    @Autowired
    private MockMvc mvc;


    @DisplayName("[api] 게시글  리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/articles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/articles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleArticleComment_thenReturnsArticleComments() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/articles/1/articleComments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleComments() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/articleComments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[api] 게시글 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleComment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/articleComments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(MockMvcResultHandlers.print());
    }
}


