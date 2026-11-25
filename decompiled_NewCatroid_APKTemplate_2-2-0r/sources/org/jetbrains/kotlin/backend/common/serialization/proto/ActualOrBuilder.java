package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface ActualOrBuilder extends MessageLiteOrBuilder {
    long getActualSymbol();

    long getExpectSymbol();

    boolean hasActualSymbol();

    boolean hasExpectSymbol();
}
