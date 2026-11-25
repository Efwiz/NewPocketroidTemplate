package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi2ir.generators.GeneratorContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PropertyLValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0010\u0012\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\u0002\u0010\u0019J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020$H\u0016J*\u0010'\u001a\u00020\u00012\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020)0\u0014H\u0014J\u0010\u0010,\u001a\u00020-*\u0006\u0012\u0002\b\u00030.H\u0002R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0019\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/AccessorPropertyLValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/PropertyLValueBase;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getterDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setter", "setterDescriptor", "typeArguments", "", "callReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "superQualifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getGetterDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getSetter", "getSetterDescriptor", "getTypeArguments", "()Ljava/util/List;", "typeArgumentsCount", "load", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "store", "irExpression", "withReceiver", "dispatchReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "extensionReceiver", "contextReceivers", "putTypeArguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessorPropertyLValue extends PropertyLValueBase {
    private final IrSimpleFunctionSymbol getter;
    private final FunctionDescriptor getterDescriptor;
    private final IrSimpleFunctionSymbol setter;
    private final FunctionDescriptor setterDescriptor;
    private final List<IrType> typeArguments;
    private final int typeArgumentsCount;

    public final IrSimpleFunctionSymbol getGetter() {
        return this.getter;
    }

    public final FunctionDescriptor getGetterDescriptor() {
        return this.getterDescriptor;
    }

    public final IrSimpleFunctionSymbol getSetter() {
        return this.setter;
    }

    public final FunctionDescriptor getSetterDescriptor() {
        return this.setterDescriptor;
    }

    public final List<IrType> getTypeArguments() {
        return this.typeArguments;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AccessorPropertyLValue(GeneratorContext context, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType type, IrSimpleFunctionSymbol irSimpleFunctionSymbol, FunctionDescriptor functionDescriptor, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, FunctionDescriptor functionDescriptor2, List<? extends IrType> list, CallReceiver callReceiver, IrClassSymbol irClassSymbol) {
        super(context, scope, i, i2, irStatementOrigin, type, callReceiver, irClassSymbol);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(callReceiver, "callReceiver");
        this.getter = irSimpleFunctionSymbol;
        this.getterDescriptor = functionDescriptor;
        this.setter = irSimpleFunctionSymbol2;
        this.setterDescriptor = functionDescriptor2;
        this.typeArguments = list;
        this.typeArgumentsCount = list != 0 ? list.size() : 0;
    }

    private final void putTypeArguments(IrMemberAccessExpression<?> irMemberAccessExpression) {
        List<IrType> list = this.typeArguments;
        if (list != null) {
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                irMemberAccessExpression.putTypeArgument(i, (IrType) obj);
                i = i2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrExpression load() {
        CallReceiver callReceiver = getCallReceiver();
        FunctionDescriptor functionDescriptor = this.getterDescriptor;
        Intrinsics.checkNotNull(functionDescriptor);
        return IrUtilsKt.adjustForCallee(callReceiver, functionDescriptor).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.AccessorPropertyLValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return AccessorPropertyLValue.load$lambda$2(this.f$0, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression load$lambda$2(AccessorPropertyLValue this$0, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        int startOffset = this$0.getStartOffset();
        int endOffset = this$0.getEndOffset();
        IrType type = this$0.getType();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this$0.getter;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, type, irSimpleFunctionSymbol, this$0.typeArgumentsCount, contextReceiverValues.size(), this$0.getOrigin(), this$0.getSuperQualifier());
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImpl, this$0.getterDescriptor);
        this$0.putTypeArguments(irCallImpl);
        irCallImpl.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irCallImpl.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        Iterator it = contextReceiverValues.iterator();
        int i = 0;
        while (it.hasNext()) {
            irCallImpl.putValueArgument(i, ((IntermediateValue) it.next()).load());
            i++;
        }
        return irCallImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.LValue
    public IrExpression store(final IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "irExpression");
        CallReceiver callReceiver = getCallReceiver();
        FunctionDescriptor functionDescriptor = this.setterDescriptor;
        Intrinsics.checkNotNull(functionDescriptor);
        return IrUtilsKt.adjustForCallee(callReceiver, functionDescriptor).call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.AccessorPropertyLValue$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
            public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                return AccessorPropertyLValue.store$lambda$5(this.f$0, irExpression, intermediateValue, intermediateValue2, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression store$lambda$5(AccessorPropertyLValue this$0, IrExpression irExpression, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
        IrType unitType;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(irExpression, "$irExpression");
        Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
        KotlinType returnType = this$0.setterDescriptor.getReturnType();
        if (returnType == null || (unitType = this$0.getContext().getTypeTranslator().translateType(returnType)) == null) {
            unitType = this$0.getContext().getIrBuiltIns().getUnitType();
        }
        int startOffset = this$0.getStartOffset();
        int endOffset = this$0.getEndOffset();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this$0.setter;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, unitType, irSimpleFunctionSymbol, this$0.typeArgumentsCount, contextReceiverValues.size() + 1, this$0.getOrigin(), this$0.getSuperQualifier());
        this$0.getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImpl, this$0.setterDescriptor);
        this$0.putTypeArguments(irCallImpl);
        irCallImpl.setDispatchReceiver(intermediateValue != null ? intermediateValue.load() : null);
        irCallImpl.setExtensionReceiver(intermediateValue2 != null ? intermediateValue2.load() : null);
        Iterator it = contextReceiverValues.iterator();
        int i = 0;
        while (it.hasNext()) {
            irCallImpl.putValueArgument(i, ((IntermediateValue) it.next()).load());
            i++;
        }
        irCallImpl.putValueArgument(contextReceiverValues.size(), irExpression);
        return irCallImpl;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.PropertyLValueBase
    protected PropertyLValueBase withReceiver(VariableLValue dispatchReceiver, VariableLValue extensionReceiver, List<VariableLValue> contextReceivers) {
        Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
        return new AccessorPropertyLValue(getContext(), getScope(), getStartOffset(), getEndOffset(), getOrigin(), getType(), this.getter, this.getterDescriptor, this.setter, this.setterDescriptor, this.typeArguments, new SimpleCallReceiver(dispatchReceiver, extensionReceiver, contextReceivers), getSuperQualifier());
    }
}
