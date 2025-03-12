package com.sanlam.banking.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublishRequest {
    private String topicArn;
    private String message;
    private int priority;
    private String timestamp;
}
