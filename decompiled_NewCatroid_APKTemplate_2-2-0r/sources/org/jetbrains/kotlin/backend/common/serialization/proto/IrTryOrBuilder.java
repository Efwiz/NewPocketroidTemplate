package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTryOrBuilder extends MessageLiteOrBuilder {
    IrStatement getCatch(int i);

    int getCatchCount();

    List<IrStatement> getCatchList();

    IrExpression getFinally();

    IrExpression getResult();

    boolean hasFinally();

    boolean hasResult();
}
