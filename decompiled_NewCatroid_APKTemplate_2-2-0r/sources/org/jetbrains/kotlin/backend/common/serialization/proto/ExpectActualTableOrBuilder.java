package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface ExpectActualTableOrBuilder extends MessageLiteOrBuilder {
    ExpectToActual getExpectToActuals(int i);

    int getExpectToActualsCount();

    List<ExpectToActual> getExpectToActualsList();
}
