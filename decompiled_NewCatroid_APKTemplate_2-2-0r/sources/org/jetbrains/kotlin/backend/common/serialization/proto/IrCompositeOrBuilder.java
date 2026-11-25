package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrCompositeOrBuilder extends MessageLiteOrBuilder {
    int getOriginName();

    IrStatement getStatement(int i);

    int getStatementCount();

    List<IrStatement> getStatementList();

    boolean hasOriginName();
}
