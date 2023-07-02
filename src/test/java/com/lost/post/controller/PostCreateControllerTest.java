package com.lost.post.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lost.post.controller.request.ImageCreate;
import com.lost.post.controller.request.ImageCreateRequest;
import com.lost.post.controller.request.PostCreateRequest;
import com.lost.post.domain.Address;
import com.lost.post.domain.PostStatus;
import com.lost.post.domain.TradeType;
import com.lost.post.service.repository.PostRepository;
import com.lost.security.userdetails.UserPrincipal;
import com.lost.user.domain.User;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SqlGroup({
        @Sql(value = "/sql/insert-post-controller-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
class PostCreateControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("이미지 없이 게시글 생성 - 성공")
    void should_success_post_without_any_image() throws Exception {
        //given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .build();

        String json = objectMapper.writeValueAsString(postCreateRequest);

        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();
        //when
        //then
        mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .with(user(new UserPrincipal(user))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("This is new title"))
                .andExpect(jsonPath("$.content").value("This is new content"))
                .andExpect(jsonPath("$.reward").value(1_000))
                .andExpect(jsonPath("$.status").value(PostStatus.PENDING.toString()))
                .andExpect(jsonPath("$.lostItem.name").value("airpods"))
                .andExpect(jsonPath("$.lostItem.address.street").value("서울시 광진구 자양동 123-123"))
                .andExpect(jsonPath("$.lostItem.images.length()").value(0))
                .andExpect(jsonPath("$.writer.id").value(1))
                .andDo(print());
    }

    @Test
    @DisplayName("이미지와 함께 게시글 생성 - 성공")
    void should_success_post_with_any_image() throws Exception {
        //given
        ImageCreate imageCreate = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef_abc.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate2 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef2_bac.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();
        ImageCreate imageCreate3 = ImageCreate.builder()
                .url("http://localhost:8080/images/upload/abcdef3_cab.jpg")
                .originalFileName("abc.jpg")
                .fileName("abcdef_abc.jpg")
                .build();

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("This is new title")
                .content("This is new content")
                .tradeType(TradeType.DIRECT)
                .reward(1_000)
                .itemName("airpods")
                .address(Address.builder()
                        .latitude(38.123456)
                        .longitude(126.123456)
                        .street("서울시 광진구 자양동 123-123")
                        .build())
                .imageCreateRequest(ImageCreateRequest.builder()
                        .imageCreate(List.of(imageCreate, imageCreate2, imageCreate3))
                        .build())
                .build();

        String json = objectMapper.writeValueAsString(postCreateRequest);

        User user = User.builder()
                .id(1L)
                .email("example@email.com")
                .password("examplepassword")
                .build();

        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/main/resources/static/images/test.png"));
        //when
        //then
        mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .with(user(new UserPrincipal(user))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("This is new title"))
                .andExpect(jsonPath("$.content").value("This is new content"))
                .andExpect(jsonPath("$.reward").value(1_000))
                .andExpect(jsonPath("$.status").value(PostStatus.PENDING.toString()))
                .andExpect(jsonPath("$.lostItem.name").value("airpods"))
                .andExpect(jsonPath("$.lostItem.address.street").value("서울시 광진구 자양동 123-123"))
                .andExpect(jsonPath("$.lostItem.images.length()").value(3))
                .andExpect(jsonPath("$.writer.id").value(1));
    }
}