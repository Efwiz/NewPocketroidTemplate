package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;

/* JADX INFO: compiled from: IrConstantValue.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0000H&J\b\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "contentEquals", "", "other", "contentHashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstantValue extends IrExpression {
    public abstract boolean contentEquals(IrConstantValue other);

    public abstract int contentHashCode();
}
