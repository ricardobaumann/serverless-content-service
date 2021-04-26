/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu.service;

import com.github.ricardobaumann.contentu.model.Content;
import com.github.ricardobaumann.contentu.model.CreateContentCommand;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ContentService {

    private final DynamoDbTable<Content> contentDynamoDbTable;

    public Content create(CreateContentCommand createContentCommand) {
        Content content = new Content(UUID.randomUUID().toString(),
                createContentCommand.getBody());
        contentDynamoDbTable.putItem(content);
        return content;
    }

    public Optional<Content> getById(String id) {
        return Optional.ofNullable(
                contentDynamoDbTable.getItem(Key.builder().partitionValue(id).build())
        );
    }

    public void delete(String id) {
        contentDynamoDbTable.deleteItem(Key.builder().partitionValue(id).build());
    }


}
