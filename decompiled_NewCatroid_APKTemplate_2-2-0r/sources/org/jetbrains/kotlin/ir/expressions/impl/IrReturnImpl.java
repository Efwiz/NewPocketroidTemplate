package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrReturnImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrReturnImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "returnTargetSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getReturnTargetSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "setReturnTargetSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getValue", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrReturnImpl extends IrReturn {
    private final int endOffset;
    private IrReturnTargetSymbol returnTargetSymbol;
    private final int startOffset;
    private IrType type;
    private IrExpression value;

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

    @Override // org.jetbrains.kotlin.ir.expressions.IrReturn
    public IrReturnTargetSymbol getReturnTargetSymbol() {
        return this.returnTargetSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrReturn
    public void setReturnTargetSymbol(IrReturnTargetSymbol irReturnTargetSymbol) {
        Intrinsics.checkNotNullParameter(irReturnTargetSymbol, "<set-?>");
        this.returnTargetSymbol = irReturnTargetSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrReturn
    public IrExpression getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrReturn
    public void setValue(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.value = irExpression;
    }

    public IrReturnImpl(int i, int i2, IrType type, IrReturnTargetSymbol returnTargetSymbol, IrExpression value) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(returnTargetSymbol, "returnTargetSymbol");
        Intrinsics.checkNotNullParameter(value, "value");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.returnTargetSymbol = returnTargetSymbol;
        this.value = value;
    }
}
