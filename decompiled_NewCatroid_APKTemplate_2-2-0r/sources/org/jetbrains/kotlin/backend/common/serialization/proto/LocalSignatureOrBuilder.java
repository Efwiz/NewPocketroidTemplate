package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface LocalSignatureOrBuilder extends MessageLiteOrBuilder {
    int getDebugInfo();

    int getLocalFqName(int i);

    int getLocalFqNameCount();

    List<Integer> getLocalFqNameList();

    long getLocalHash();

    boolean hasDebugInfo();

    boolean hasLocalHash();
}
