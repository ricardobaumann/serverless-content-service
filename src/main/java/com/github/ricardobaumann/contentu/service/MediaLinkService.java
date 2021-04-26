/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu.service;

import com.github.ricardobaumann.contentu.model.CreateMediaLinkCommand;
import com.github.ricardobaumann.contentu.model.MediaLink;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.UUID;

@AllArgsConstructor
public class MediaLinkService {
    private final DynamoDbTable<MediaLink> mediaLinkDynamoDbTable;

    public void create(CreateMediaLinkCommand createMediaLinkCommand) {
        mediaLinkDynamoDbTable.putItem(
                MediaLink.builder()
                        .id(UUID.randomUUID().toString())
                        .mediaId(createMediaLinkCommand.getMediaId())
                        .contentId(createMediaLinkCommand.getContentId())
                        .build());
    }
}
