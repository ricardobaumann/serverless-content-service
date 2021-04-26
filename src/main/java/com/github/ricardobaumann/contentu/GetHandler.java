/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.log4j.Log4j2;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class GetHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final ContentService contentService = BeanFactory.FACTORY.getContentService();

    @Override
    @SuppressWarnings("unchecked")
    public ApiGatewayResponse handleRequest(Map<String, Object> params,
                                            Context context) {

        return Optional.ofNullable(params.get("pathParameters"))
                .map(o -> (Map<String, String>) o)
                .map(pathParams -> pathParams.get("id"))
                .map(this::loadContent)
                .orElseGet(this::notFound);

    }

    private ApiGatewayResponse notFound() {
        return ApiGatewayResponse.builder()
                .setStatusCode(404)
                .setHeaders(Collections.emptyMap())
                .build();
    }

    private ApiGatewayResponse loadContent(String id) {
        log.info("Locating object: {}", id);

        return contentService.getById(id)
                .map(content -> ApiGatewayResponse.builder()
                        .setStatusCode(200)
                        .setRawBody(
                                Optional.of(content)
                                        .map(Content::getBody)
                                        .map(s -> Base64.getDecoder().decode(s))
                                        .map(String::new)
                                        .orElse(null)
                        )
                        .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                        .build())
                .orElse(notFound());
    }
}
