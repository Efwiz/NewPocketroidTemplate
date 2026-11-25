package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrTypeOperatorCallImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\t\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0019\"\u0004\b\u001d\u0010\u001b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrTypeOperatorCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "typeOperand", "argument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getArgument", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setArgument", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getOperator", "()Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "setOperator", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getTypeOperand", "setTypeOperand", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeOperatorCallImpl extends IrTypeOperatorCall {
    private IrExpression argument;
    private final int endOffset;
    private IrTypeOperator operator;
    private final int startOffset;
    private IrType type;
    private IrType typeOperand;

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

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public IrTypeOperator getOperator() {
        return this.operator;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public void setOperator(IrTypeOperator irTypeOperator) {
        Intrinsics.checkNotNullParameter(irTypeOperator, "<set-?>");
        this.operator = irTypeOperator;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public IrType getTypeOperand() {
        return this.typeOperand;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public void setTypeOperand(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.typeOperand = irType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public IrExpression getArgument() {
        return this.argument;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall
    public void setArgument(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.argument = irExpression;
    }

    public IrTypeOperatorCallImpl(int i, int i2, IrType type, IrTypeOperator operator, IrType typeOperand, IrExpression argument) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(typeOperand, "typeOperand");
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.operator = operator;
        this.typeOperand = typeOperand;
        this.argument = argument;
    }
}
