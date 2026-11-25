package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface MemberAccessCommonOrBuilder extends MessageLiteOrBuilder {
    IrExpression getDispatchReceiver();

    IrExpression getExtensionReceiver();

    int getTypeArgument(int i);

    int getTypeArgumentCount();

    List<Integer> getTypeArgumentList();

    NullableIrExpression getValueArgument(int i);

    int getValueArgumentCount();

    List<NullableIrExpression> getValueArgumentList();

    boolean hasDispatchReceiver();

    boolean hasExtensionReceiver();
}
