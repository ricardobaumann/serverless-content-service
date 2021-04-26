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

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class DeleteHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final ContentService contentService = BeanFactory.FACTORY.getContentService();

    @Override
    @SuppressWarnings("unchecked")
    public ApiGatewayResponse handleRequest(Map<String, Object> params, Context context) {
        Optional.ofNullable(params.get("pathParameters"))
                .map(o -> (Map<String, String>) o)
                .map(pathParams -> pathParams.get("id"))
                .ifPresent(contentService::delete);

        return ApiGatewayResponse.builder()
                .setStatusCode(204)
                .setHeaders(Collections.emptyMap())
                .build();
    }
}
