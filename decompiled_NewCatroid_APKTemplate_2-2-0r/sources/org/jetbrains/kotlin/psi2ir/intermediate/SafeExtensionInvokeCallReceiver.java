package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorWithScope;

/* JADX INFO: compiled from: SafeExtensionInvokeCallReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/SafeExtensionInvokeCallReceiver;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "generator", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "startOffset", "", "endOffset", "callBuilder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "functionReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "extensionInvokeReceiver", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;IILorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;)V", "getEndOffset", "()I", "getGenerator", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "getStartOffset", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SafeExtensionInvokeCallReceiver implements CallReceiver {
    private final CallBuilder callBuilder;
    private final int endOffset;
    private final IntermediateValue extensionInvokeReceiver;
    private final IntermediateValue functionReceiver;
    private final GeneratorWithScope generator;
    private final int startOffset;

    public SafeExtensionInvokeCallReceiver(GeneratorWithScope generator, int i, int i2, CallBuilder callBuilder, IntermediateValue functionReceiver, IntermediateValue extensionInvokeReceiver) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(callBuilder, "callBuilder");
        Intrinsics.checkNotNullParameter(functionReceiver, "functionReceiver");
        Intrinsics.checkNotNullParameter(extensionInvokeReceiver, "extensionInvokeReceiver");
        this.generator = generator;
        this.startOffset = i;
        this.endOffset = i2;
        this.callBuilder = callBuilder;
        this.functionReceiver = functionReceiver;
        this.extensionInvokeReceiver = extensionInvokeReceiver;
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

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
    public IrExpression call(CallExpressionBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Scope scope = this.generator.getScope();
        IrExpression irExpressionLoad = this.extensionInvokeReceiver.load();
        IrVariable irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irExpressionLoad, (108 & 2) != 0 ? null : "safe_receiver", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionLoad.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionLoad.getEndOffset() : 0);
        VariableLValue variableLValue = new VariableLValue(this.generator.getContext(), irVariableCreateTemporaryVariable, null, 0, 0, 28, null);
        IrExpression irExpression = this.callBuilder.getIrValueArgumentsByIndex()[0];
        this.callBuilder.getIrValueArgumentsByIndex()[0] = variableLValue.load();
        IrExpression irExpressionWithReceivers = builder.withReceivers(this.functionReceiver, null, CollectionsKt.emptyList());
        IrType irTypeMakeNullable = IrTypesKt.makeNullable(irExpressionWithReceivers.getType());
        GeneratorWithScope generatorWithScope = this.generator;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(generatorWithScope.getContext(), generatorWithScope.getScope(), this.startOffset, this.endOffset, IrStatementOrigin.SAFE_CALL.INSTANCE, irTypeMakeNullable, false, 64, null);
        irBlockBuilder.unaryPlus(irVariableCreateTemporaryVariable);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, irTypeMakeNullable, variableLValue.load(), ExpressionHelpersKt.irNull(irBlockBuilder2), irExpressionWithReceivers));
        return irBlockBuilder.getIrBlockBody();
    }
}
