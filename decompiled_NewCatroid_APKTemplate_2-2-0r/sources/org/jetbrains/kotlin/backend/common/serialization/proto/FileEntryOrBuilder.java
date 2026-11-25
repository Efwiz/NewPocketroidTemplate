package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface FileEntryOrBuilder extends MessageLiteOrBuilder {
    int getLineStartOffset(int i);

    int getLineStartOffsetCount();

    List<Integer> getLineStartOffsetList();

    String getName();

    ByteString getNameBytes();

    boolean hasName();
}
