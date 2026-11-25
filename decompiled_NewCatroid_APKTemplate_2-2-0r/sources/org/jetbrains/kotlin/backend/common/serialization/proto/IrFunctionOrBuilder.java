package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrFunctionOrBuilder extends MessageLiteOrBuilder {
    IrFunctionBase getBase();

    long getOverridden(int i);

    int getOverriddenCount();

    List<Long> getOverriddenList();

    boolean hasBase();
}
