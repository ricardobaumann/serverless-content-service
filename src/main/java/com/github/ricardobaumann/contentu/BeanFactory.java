/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class BeanFactory {

    public static final BeanFactory FACTORY = new BeanFactory();

    private static final DynamoDbEnhancedClient DDB_ENHANCED_CLIENT =
            DynamoDbEnhancedClient.create();

    private static final DynamoDbTable<Content> CONTENT_TABLE =
            DDB_ENHANCED_CLIENT.table("contents",
                    TableSchema.fromBean(Content.class));

    private static final ContentService CONTENT_SERVICE = new ContentService(CONTENT_TABLE);

    private BeanFactory() {
    }

    public ContentService getContentService() {
        return CONTENT_SERVICE;
    }
}
