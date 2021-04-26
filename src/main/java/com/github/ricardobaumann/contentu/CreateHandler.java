package com.github.ricardobaumann.contentu;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.ricardobaumann.contentu.model.ApiGatewayResponse;
import com.github.ricardobaumann.contentu.model.Content;
import com.github.ricardobaumann.contentu.model.CreateContentCommand;
import com.github.ricardobaumann.contentu.model.Response;
import com.github.ricardobaumann.contentu.service.ContentService;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Map;

@Log4j2
public class CreateHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final ContentService contentService = BeanFactory.FACTORY.getContentService();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        log.info("received: {}", input);

        Content content = contentService.create(
                new CreateContentCommand(input.get("body").toString()));

        Response responseBody = new Response("Content Created",
                Collections.singletonMap("id", content.getId()));

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(responseBody)
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }
}
