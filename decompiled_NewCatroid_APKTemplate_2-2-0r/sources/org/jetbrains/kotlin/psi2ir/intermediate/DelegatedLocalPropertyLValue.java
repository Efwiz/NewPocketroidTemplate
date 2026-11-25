package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: DelegatedLocalPropertyLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002BE\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u001c\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00180\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0018H\u0016J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u0018H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\f\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/DelegatedLocalPropertyLValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/LValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setterSymbol", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "assign", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "withLValue", "Lkotlin/Function1;", "load", "store", "irExpression", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatedLocalPropertyLValue implements AssignmentReceiver, LValue {
    private final IrGeneratorContext context;
    private final int endOffset;
    private final IrSimpleFunctionSymbol getterSymbol;
    private final IrStatementOrigin origin;
    private final IrSimpleFunctionSymbol setterSymbol;
    private final int startOffset;
    private final IrType type;

    public DelegatedLocalPropertyLValue(IrGeneratorContext context, int i, int i2, IrType type, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        this.context = context;
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.getterSymbol = irSimpleFunctionSymbol;
        this.setterSymbol = irSimpleFunctionSymbol2;
        this.origin = irStatementOrigin;
    }

    public /* synthetic */ DelegatedLocalPropertyLValue(IrGeneratorContext irGeneratorContext, int i, int i2, IrType irType, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, i, i2, irType, irSimpleFunctionSymbol, irSimpleFunctionSymbol2, (i3 & 64) != 0 ? null : irStatementOrigin);
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }

    public final IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int i = this.startOffset;
        int i2 = this.endOffset;
        IrType type = getType();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.getterSymbol;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        return companion.fromSymbolDescriptor(i, i2, type, irSimpleFunctionSymbol, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbol.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbol.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbol.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : this.origin, (176 & 128) != 0 ? null : null);
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int i = this.startOffset;
        int i2 = this.endOffset;
        IrType unitType = this.context.getIrBuiltIns().getUnitType();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.setterSymbol;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        IrCallImpl irCallImplFromSymbolDescriptor = companion.fromSymbolDescriptor(i, i2, unitType, irSimpleFunctionSymbol, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbol.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbol.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbol.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : this.origin, (176 & 128) != 0 ? null : null);
        irCallImplFromSymbolDescriptor.putValueArgument(0, irExpression);
        return irCallImplFromSymbolDescriptor;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver
    public IrExpression assign(Function1<? super LValue, ? extends IrExpression> withLValue) {
        Intrinsics.checkNotNullParameter(withLValue, "withLValue");
        return withLValue.invoke(this);
    }
}
