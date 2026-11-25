package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrStatementOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "debugName", "", "(Ljava/lang/String;)V", "getDebugName", "()Ljava/lang/String;", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrStatementOriginImpl implements IrStatementOrigin {
    private final String debugName;

    public IrStatementOriginImpl(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.debugName = debugName;
    }

    public final String getDebugName() {
        return this.debugName;
    }

    public String toString() {
        return this.debugName;
    }
}
