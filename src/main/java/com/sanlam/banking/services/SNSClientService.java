package com.sanlam.banking.services;

import com.sanlam.banking.dtos.PublishRequest;
import com.sanlam.banking.dtos.PublishResponse;

public class SNSClientService {

  public PublishResponse publish(PublishRequest publishRequest) {
      return new PublishResponse(1L,"Successfully sent message");
  }
}
