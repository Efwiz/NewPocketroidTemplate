package org.jetbrains.kotlin.backend.common.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: ValueRemapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H$J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/AbstractValueRemapper;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "()V", "remapValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "oldValue", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractValueRemapper extends IrElementTransformerVoid {
    protected abstract IrValueSymbol remapValue(IrValueSymbol oldValue);

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueSymbol irValueSymbolRemapValue = remapValue(expression.getSymbol());
        return irValueSymbolRemapValue == null ? expression : new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueSymbolRemapValue, expression.getOrigin());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        IrValueSymbol irValueSymbolRemapValue = remapValue(expression.getSymbol());
        if (irValueSymbolRemapValue == null) {
            return expression;
        }
        irValueSymbolRemapValue.getOwner().isAssignable();
        return new IrSetValueImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irValueSymbolRemapValue, expression.getValue(), expression.getOrigin());
    }
}
