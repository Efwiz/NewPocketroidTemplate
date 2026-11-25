package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface ExpectToActualOrBuilder extends MessageLiteOrBuilder {
    long getActual();

    long getExpect();

    boolean hasActual();

    boolean hasExpect();
}
