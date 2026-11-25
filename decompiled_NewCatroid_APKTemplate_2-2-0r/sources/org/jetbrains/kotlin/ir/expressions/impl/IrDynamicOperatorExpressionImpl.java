package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrDynamicOperatorExpressionImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrDynamicOperatorExpressionImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;)V", "arguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getArguments", "()Ljava/util/List;", "getEndOffset", "()I", "getOperator", "()Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "setOperator", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;)V", "receiver", "getReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDynamicOperatorExpressionImpl extends IrDynamicOperatorExpression {
    private final List<IrExpression> arguments;
    private final int endOffset;
    private IrDynamicOperator operator;
    public IrExpression receiver;
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression
    public IrDynamicOperator getOperator() {
        return this.operator;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression
    public void setOperator(IrDynamicOperator irDynamicOperator) {
        Intrinsics.checkNotNullParameter(irDynamicOperator, "<set-?>");
        this.operator = irDynamicOperator;
    }

    public IrDynamicOperatorExpressionImpl(int i, int i2, IrType type, IrDynamicOperator operator) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(operator, "operator");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.operator = operator;
        this.arguments = new SmartList();
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression
    public IrExpression getReceiver() {
        IrExpression irExpression = this.receiver;
        if (irExpression != null) {
            return irExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("receiver");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression
    public void setReceiver(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.receiver = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression
    public List<IrExpression> getArguments() {
        return this.arguments;
    }
}
