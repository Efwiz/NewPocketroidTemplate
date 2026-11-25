package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrWhileLoopImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrWhileLoopImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "condition", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getCondition", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setCondition", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrWhileLoopImpl extends IrWhileLoop {
    public IrExpression condition;
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrLoop
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLoop
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrWhileLoopImpl(int i, int i2, IrType type, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.origin = irStatementOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLoop
    public IrExpression getCondition() {
        IrExpression irExpression = this.condition;
        if (irExpression != null) {
            return irExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("condition");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLoop
    public void setCondition(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.condition = irExpression;
    }
}
