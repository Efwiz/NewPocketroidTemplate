package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u001c\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0000¨\u0006\u0014"}, d2 = {"computeSubstitutedSyntheticAccessor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "accessorFunctionDescriptor", "substitutedExtensionAccessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "copyTypesFromExtensionAccessor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "extensionAccessorDescriptor", "adjustForCallee", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "callee", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "loadAt", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "startOffset", "", "endOffset", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrUtilsKt {
    public static final IrExpression loadAt(IrVariable irVariable, int i, int i2) {
        Intrinsics.checkNotNullParameter(irVariable, "<this>");
        return new IrGetValueImpl(i, i2, irVariable.getType(), irVariable.getSymbol(), null, 16, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi2ir.intermediate.IrUtilsKt$adjustForCallee$1, reason: invalid class name */
    /* JADX INFO: compiled from: IrUtils.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/jetbrains/kotlin/psi2ir/intermediate/IrUtilsKt$adjustForCallee$1", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements CallReceiver {
        final /* synthetic */ CallableMemberDescriptor $callee;
        final /* synthetic */ CallReceiver $this_adjustForCallee;

        AnonymousClass1(CallReceiver callReceiver, CallableMemberDescriptor callableMemberDescriptor) {
            this.$this_adjustForCallee = callReceiver;
            this.$callee = callableMemberDescriptor;
        }

        @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
        public IrExpression call(final CallExpressionBuilder builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            CallReceiver callReceiver = this.$this_adjustForCallee;
            final CallableMemberDescriptor callableMemberDescriptor = this.$callee;
            return callReceiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.IrUtilsKt$adjustForCallee$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
                public final IrExpression withReceivers(IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List list) {
                    return IrUtilsKt.AnonymousClass1.call$lambda$0(callableMemberDescriptor, builder, intermediateValue, intermediateValue2, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final IrExpression call$lambda$0(CallableMemberDescriptor callee, CallExpressionBuilder builder, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
            IntermediateValue intermediateValue3;
            Intrinsics.checkNotNullParameter(callee, "$callee");
            Intrinsics.checkNotNullParameter(builder, "$builder");
            Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
            int size = CollectionsKt.listOfNotNull((Object[]) new IntermediateValue[]{intermediateValue, intermediateValue2}).size();
            int size2 = CollectionsKt.listOfNotNull((Object[]) new ReceiverParameterDescriptor[]{callee.mo6753getDispatchReceiverParameter(), callee.getExtensionReceiverParameter()}).size();
            if (size != size2) {
                throw new AssertionError("Mismatching receivers for " + callee + ": " + size + ", expected: " + size2);
            }
            if (callee.mo6753getDispatchReceiverParameter() == null) {
                intermediateValue3 = null;
            } else {
                intermediateValue3 = intermediateValue == null ? intermediateValue2 : intermediateValue;
            }
            if (callee.getExtensionReceiverParameter() == null) {
                intermediateValue = null;
            } else if (intermediateValue == null || callee.mo6753getDispatchReceiverParameter() != null) {
                intermediateValue = intermediateValue2;
            }
            return builder.withReceivers(intermediateValue3, intermediateValue, contextReceiverValues);
        }
    }

    public static final CallReceiver adjustForCallee(CallReceiver callReceiver, CallableMemberDescriptor callee) {
        Intrinsics.checkNotNullParameter(callReceiver, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        return new AnonymousClass1(callReceiver, callee);
    }

    public static final FunctionDescriptor computeSubstitutedSyntheticAccessor(PropertyDescriptor propertyDescriptor, FunctionDescriptor accessorFunctionDescriptor, PropertyAccessorDescriptor substitutedExtensionAccessorDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(accessorFunctionDescriptor, "accessorFunctionDescriptor");
        Intrinsics.checkNotNullParameter(substitutedExtensionAccessorDescriptor, "substitutedExtensionAccessorDescriptor");
        return (!Intrinsics.areEqual(propertyDescriptor.getOriginal(), propertyDescriptor) && (propertyDescriptor instanceof SyntheticPropertyDescriptor) && propertyDescriptor.getExtensionReceiverParameter() != null && propertyDescriptor.mo6753getDispatchReceiverParameter() == null && (accessorFunctionDescriptor instanceof SimpleFunctionDescriptor)) ? copyTypesFromExtensionAccessor((SimpleFunctionDescriptor) accessorFunctionDescriptor, substitutedExtensionAccessorDescriptor) : accessorFunctionDescriptor;
    }

    private static final FunctionDescriptor copyTypesFromExtensionAccessor(SimpleFunctionDescriptor simpleFunctionDescriptor, PropertyAccessorDescriptor propertyAccessorDescriptor) {
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(simpleFunctionDescriptor.getContainingDeclaration(), simpleFunctionDescriptor.getAnnotations(), simpleFunctionDescriptor.getShortName(), simpleFunctionDescriptor.getKind(), simpleFunctionDescriptor.getSource());
        ReceiverParameterDescriptor extensionReceiverParameter = propertyAccessorDescriptor.getExtensionReceiverParameter();
        ReceiverParameterDescriptor receiverParameterDescriptorMo7203copy = extensionReceiverParameter != null ? extensionReceiverParameter.mo7203copy(simpleFunctionDescriptorImplCreate) : null;
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<? extends TypeParameterDescriptor> listEmptyList2 = CollectionsKt.emptyList();
        List<ValueParameterDescriptor> valueParameters = propertyAccessorDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Intrinsics.checkNotNull(simpleFunctionDescriptorImplCreate);
            Name name2 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(valueParameterDescriptor.mo7204copy(simpleFunctionDescriptorImplCreate, name2, valueParameterDescriptor.getIndex()));
        }
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, receiverParameterDescriptorMo7203copy, listEmptyList, listEmptyList2, (List<ValueParameterDescriptor>) arrayList, propertyAccessorDescriptor.getReturnType(), simpleFunctionDescriptor.getModality(), simpleFunctionDescriptor.getVisibility());
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "apply(...)");
        return simpleFunctionDescriptorImplCreate;
    }
}
