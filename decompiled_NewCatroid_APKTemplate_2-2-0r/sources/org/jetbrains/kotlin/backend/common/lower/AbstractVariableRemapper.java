package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: LowerUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H$J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/AbstractVariableRemapper;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "()V", "remapVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "value", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractVariableRemapper extends IrElementTransformerVoid {
    protected abstract IrValueDeclaration remapVariable(IrValueDeclaration value);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueDeclaration irValueDeclarationRemapVariable = remapVariable(expression.getSymbol().getOwner());
        if (irValueDeclarationRemapVariable != null) {
            return new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueDeclarationRemapVariable.getType(), irValueDeclarationRemapVariable.getSymbol(), expression.getOrigin());
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        IrValueDeclaration irValueDeclarationRemapVariable = remapVariable(expression.getSymbol().getOwner());
        if (irValueDeclarationRemapVariable != null) {
            return new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irValueDeclarationRemapVariable.getSymbol(), expression.getValue(), expression.getOrigin());
        }
        return expression;
    }
}
