package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrSingleStatementBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorWithScope;

/* JADX INFO: compiled from: SafeCallReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/SafeCallReceiver;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "generator", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "startOffset", "", "endOffset", "extensionReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "contextReceivers", "", "dispatchReceiver", "isStatement", "", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;IILorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Ljava/util/List;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Z)V", "getContextReceivers", "()Ljava/util/List;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "getEndOffset", "()I", "getExtensionReceiver", "getGenerator", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "()Z", "getStartOffset", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SafeCallReceiver implements CallReceiver {
    private final List<IntermediateValue> contextReceivers;
    private final IntermediateValue dispatchReceiver;
    private final int endOffset;
    private final IntermediateValue extensionReceiver;
    private final GeneratorWithScope generator;
    private final boolean isStatement;
    private final int startOffset;

    /* JADX WARN: Multi-variable type inference failed */
    public SafeCallReceiver(GeneratorWithScope generator, int i, int i2, IntermediateValue intermediateValue, List<? extends IntermediateValue> contextReceivers, IntermediateValue intermediateValue2, boolean z) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        this.generator = generator;
        this.startOffset = i;
        this.endOffset = i2;
        this.extensionReceiver = intermediateValue;
        this.contextReceivers = contextReceivers;
        this.dispatchReceiver = intermediateValue2;
        this.isStatement = z;
    }

    public final GeneratorWithScope getGenerator() {
        return this.generator;
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final IntermediateValue getExtensionReceiver() {
        return this.extensionReceiver;
    }

    public final List<IntermediateValue> getContextReceivers() {
        return this.contextReceivers;
    }

    public final IntermediateValue getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    /* JADX INFO: renamed from: isStatement, reason: from getter */
    public final boolean getIsStatement() {
        return this.isStatement;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
    public IrExpression call(CallExpressionBuilder builder) {
        IrExpression irExpressionLoad;
        VariableLValue variableLValue;
        VariableLValue variableLValue2;
        Intrinsics.checkNotNullParameter(builder, "builder");
        Scope scope = this.generator.getScope();
        IntermediateValue intermediateValue = this.extensionReceiver;
        if (intermediateValue == null || (irExpressionLoad = intermediateValue.load()) == null) {
            IntermediateValue intermediateValue2 = this.dispatchReceiver;
            Intrinsics.checkNotNull(intermediateValue2);
            irExpressionLoad = intermediateValue2.load();
        }
        IrExpression irExpression = irExpressionLoad;
        IrVariable irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : "safe_receiver", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : 0, (108 & 64) != 0 ? irExpression.getEndOffset() : 0);
        VariableLValue variableLValue3 = new VariableLValue(this.generator.getContext(), irVariableCreateTemporaryVariable, null, 0, 0, 28, null);
        if (this.extensionReceiver != null) {
            variableLValue = this.dispatchReceiver;
            variableLValue2 = variableLValue3;
        } else {
            variableLValue = variableLValue3;
            variableLValue2 = null;
        }
        IrExpression irExpressionWithReceivers = builder.withReceivers(variableLValue, variableLValue2, this.contextReceivers);
        IrType unitType = this.isStatement ? this.generator.getContext().getIrBuiltIns().getUnitType() : IrTypesKt.makeNullable(irExpressionWithReceivers.getType());
        IrBlockImpl irBlockImpl = new IrBlockImpl(this.startOffset, this.endOffset, unitType, IrStatementOrigin.SAFE_CALL.INSTANCE);
        irBlockImpl.getStatements().add(irVariableCreateTemporaryVariable);
        GeneratorWithScope generatorWithScope = this.generator;
        IrSingleStatementBuilder irSingleStatementBuilder = new IrSingleStatementBuilder(generatorWithScope.getContext(), generatorWithScope.getScope(), this.startOffset, this.endOffset, IrStatementOrigin.SAFE_CALL.INSTANCE);
        irBlockImpl.getStatements().add(ExpressionHelpersKt.irIfNull(irSingleStatementBuilder, unitType, variableLValue3.load(), ExpressionHelpersKt.irNull(irSingleStatementBuilder), irExpressionWithReceivers));
        return irBlockImpl;
    }
}
