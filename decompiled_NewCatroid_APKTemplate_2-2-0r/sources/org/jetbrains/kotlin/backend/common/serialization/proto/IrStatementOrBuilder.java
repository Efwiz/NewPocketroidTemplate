package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrStatementOrBuilder extends MessageLiteOrBuilder {
    IrBlockBody getBlockBody();

    IrBranch getBranch();

    IrCatch getCatch();

    long getCoordinates();

    IrDeclaration getDeclaration();

    IrExpression getExpression();

    IrSyntheticBody getSyntheticBody();

    boolean hasBlockBody();

    boolean hasBranch();

    boolean hasCatch();

    boolean hasCoordinates();

    boolean hasDeclaration();

    boolean hasExpression();

    boolean hasSyntheticBody();
}
