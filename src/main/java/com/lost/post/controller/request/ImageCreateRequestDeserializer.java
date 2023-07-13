package com.lost.post.controller.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageCreateRequestDeserializer extends JsonDeserializer<ImageCreateRequest> {

    @Override
    public ImageCreateRequest deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        List<ImageCreate> imageCreateList = new ArrayList<>();
        if (node.isArray()) {
            ArrayNode imagesNode = (ArrayNode) node;
            for (JsonNode imageNode : imagesNode) {
                String url = imageNode.get("url").asText();
                String originalFileName = imageNode.get("originalFileName").asText();
                String fileName = imageNode.get("fileName").asText();
                ImageCreate imageCreate = ImageCreate.builder()
                        .url(url)
                        .originalFileName(originalFileName)
                        .fileName(fileName)
                        .build();
                imageCreateList.add(imageCreate);
            }
        }

        return ImageCreateRequest.builder()
                .imageCreate(imageCreateList)
                .build();
    }
}