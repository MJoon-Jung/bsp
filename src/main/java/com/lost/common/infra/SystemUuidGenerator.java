package com.lost.common.infra;

import com.lost.common.service.UuidGenerator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SystemUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
