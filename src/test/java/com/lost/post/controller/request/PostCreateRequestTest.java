package com.lost.post.controller.request;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

class PostCreateRequestTest {

    @Test
    void deserialize_test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ImageCreateRequest.class, new ImageCreateRequestDeserializer());
        objectMapper.registerModule(module);

        String content = "{\"title\":\"This is new title\", \"content\":\"This is new content\",\"tradeType\":{\"direct\":true,\"delivery\":false},\"reward\":1000,\"itemName\":\"airpods\",\"address\":{\"street\":\"서울시 광진구 자양동 123-123\",\"latitude\":38.123456,\"longitude\":126.123456},\"images\": [{\"url\": \"http://localhost:8080/images/upload/abcdef_abc.jpg\", \"originalFileName\": \"abc.jpg\", \"fileName\": \"abcdef_abc.jpg\"}, {\"url\": \"http://localhost:8080/images/upload/abcdef2_bca.jpg\", \"originalFileName\": \"bca.jpg\", \"fileName\": \"asdfasdf_bca.jpg\"}]}";
        PostCreateRequest request = objectMapper.readValue(content, PostCreateRequest.class);

        assertThat(request).isNotNull();
        assertThat(request.getImageCreateRequest()).isNotNull();
        assertThat(request.getImageCreateRequest().isEmpty()).isFalse();
    }
}