/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.ricardobaumann.contentu.model.ApiGatewayResponse;
import com.github.ricardobaumann.contentu.model.CreateMediaLinkCommand;
import com.github.ricardobaumann.contentu.service.MediaLinkService;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.Map;

@Log4j2
public class CreateLinkHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final MediaLinkService mediaLinkService = BeanFactory.FACTORY.getMediaLinkService();

    @Override
    @SuppressWarnings("unchecked")
    public ApiGatewayResponse handleRequest(Map<String, Object> params, Context context) {

        Map<String, String> pathParameters = (Map<String, String>) params.get("pathParameters");

        log.info("Path parameters: {}", pathParameters);

        mediaLinkService.create(
                new CreateMediaLinkCommand(
                        pathParameters.get("content-id"),
                        pathParameters.get("media-id")
                ));

        return ApiGatewayResponse.builder()
                .setStatusCode(204)
                .setHeaders(Collections.emptyMap())
                .build();
    }
}
