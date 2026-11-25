package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrSuspendImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\t\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrSuspendableExpressionImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "suspensionPointId", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "result", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "getSuspensionPointId", "setSuspensionPointId", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSuspendableExpressionImpl extends IrSuspendableExpression {
    private final int endOffset;
    private IrExpression result;
    private final int startOffset;
    private IrExpression suspensionPointId;
    private IrType type;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression
    public IrExpression getSuspensionPointId() {
        return this.suspensionPointId;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression
    public void setSuspensionPointId(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.suspensionPointId = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression
    public IrExpression getResult() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression
    public void setResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.result = irExpression;
    }

    public IrSuspendableExpressionImpl(int i, int i2, IrType type, IrExpression suspensionPointId, IrExpression result) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(suspensionPointId, "suspensionPointId");
        Intrinsics.checkNotNullParameter(result, "result");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.suspensionPointId = suspensionPointId;
        this.result = result;
    }
}
