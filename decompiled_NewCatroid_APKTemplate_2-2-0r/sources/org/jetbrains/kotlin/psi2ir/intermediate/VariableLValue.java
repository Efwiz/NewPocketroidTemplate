package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: VariableLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B7\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fB9\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u0011J\u001c\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001c0\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001cH\u0016J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u001cH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "irVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;II)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;IILorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "assign", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "withLValue", "Lkotlin/Function1;", "load", "store", "irExpression", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableLValue implements AssignmentReceiver, LValue {
    private final IrGeneratorContext context;
    private final int endOffset;
    private final IrStatementOrigin origin;
    private final int startOffset;
    private final IrValueSymbol symbol;
    private final IrType type;

    public VariableLValue(IrGeneratorContext context, int i, int i2, IrValueSymbol symbol, IrType type, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        this.context = context;
        this.startOffset = i;
        this.endOffset = i2;
        this.symbol = symbol;
        this.type = type;
        this.origin = irStatementOrigin;
    }

    public /* synthetic */ VariableLValue(IrGeneratorContext irGeneratorContext, int i, int i2, IrValueSymbol irValueSymbol, IrType irType, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, i, i2, irValueSymbol, irType, (i3 & 32) != 0 ? null : irStatementOrigin);
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final IrValueSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }

    public final IrStatementOrigin getOrigin() {
        return this.origin;
    }

    public /* synthetic */ VariableLValue(IrGeneratorContext irGeneratorContext, IrVariable irVariable, IrStatementOrigin irStatementOrigin, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, irVariable, (i3 & 4) != 0 ? null : irStatementOrigin, (i3 & 8) != 0 ? irVariable.getStartOffset() : i, (i3 & 16) != 0 ? irVariable.getEndOffset() : i2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VariableLValue(IrGeneratorContext context, IrVariable irVariable, IrStatementOrigin irStatementOrigin, int i, int i2) {
        this(context, i, i2, irVariable.getSymbol(), irVariable.getType(), irStatementOrigin);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(irVariable, "irVariable");
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        return new IrGetValueImpl(this.startOffset, this.endOffset, getType(), this.symbol, this.origin);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        return new IrSetValueImpl(this.startOffset, this.endOffset, this.context.getIrBuiltIns().getUnitType(), this.symbol, irExpression, this.origin);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(Function1<? super LValue, ? extends IrExpression> withLValue) {
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        return withLValue.invoke(this);
    }
}
