/*
 * Copyright 2020 the original author or authors.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.ricardobaumann.contentu.model;

import lombok.Value;

@Value
public class CreateContentCommand {
    String body;
}
