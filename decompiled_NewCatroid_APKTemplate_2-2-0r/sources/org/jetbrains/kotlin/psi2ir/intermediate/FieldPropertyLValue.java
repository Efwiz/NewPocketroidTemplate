package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;

/* JADX INFO: compiled from: PropertyLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0000\u0018\u00002\u00020\u0001BY\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\u0002\u0010\u0015J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001bH\u0016J*\u0010\u001e\u001a\u00020\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020 0#H\u0014R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/FieldPropertyLValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/PropertyLValueBase;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "field", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "callReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "superQualifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getField", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "load", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "store", "irExpression", "withReceiver", "dispatchReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "extensionReceiver", "contextReceivers", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FieldPropertyLValue extends PropertyLValueBase {
    private final PropertyDescriptor descriptor;
    private final IrFieldSymbol field;

    public final IrFieldSymbol getField() {
        return this.field;
    }

    public final PropertyDescriptor getDescriptor() {
        return this.descriptor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FieldPropertyLValue(GeneratorContext context, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, IrFieldSymbol field, PropertyDescriptor descriptor, IrType type, CallReceiver callReceiver, IrClassSymbol irClassSymbol) {
        super(context, scope, i, i2, irStatementOrigin, type, callReceiver, irClassSymbol);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(callReceiver, "callReceiver");
        this.field = field;
        this.descriptor = descriptor;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        return getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.FieldPropertyLValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return FieldPropertyLValue.load$lambda$3(this.f$0, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression load$lambda$3(FieldPropertyLValue this$0, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        contextReceiverValues.isEmpty();
        IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(this$0.getStartOffset(), this$0.getEndOffset(), this$0.field, this$0.getType(), intermediateValue != null ? intermediateValue.load() : null, this$0.getOrigin(), this$0.getSuperQualifier());
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irGetFieldImpl, this$0.descriptor);
        return irGetFieldImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(final IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        return getCallReceiver().call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.FieldPropertyLValue$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return FieldPropertyLValue.store$lambda$7(this.f$0, irExpression, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression store$lambda$7(FieldPropertyLValue this$0, IrExpression irExpression, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(irExpression, "$irExpression");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        contextReceiverValues.isEmpty();
        IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(this$0.getStartOffset(), this$0.getEndOffset(), this$0.field, intermediateValue != null ? intermediateValue.load() : null, irExpression, this$0.getContext().getIrBuiltIns().getUnitType(), this$0.getOrigin(), this$0.getSuperQualifier());
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irSetFieldImpl, this$0.descriptor);
        return irSetFieldImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.PropertyLValueBase
    protected PropertyLValueBase withReceiver(VariableLValue dispatchReceiver, VariableLValue extensionReceiver, List<VariableLValue> contextReceivers) {
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        return new FieldPropertyLValue(getContext(), getScope(), getStartOffset(), getEndOffset(), getOrigin(), this.field, this.descriptor, getType(), new SimpleCallReceiver(dispatchReceiver, extensionReceiver, contextReceivers), getSuperQualifier());
    }
}
