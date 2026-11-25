package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrWhenOrBuilder extends MessageLiteOrBuilder {
    IrStatement getBranch(int i);

    int getBranchCount();

    List<IrStatement> getBranchList();

    int getOriginName();

    boolean hasOriginName();
}
