package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0015*\u00020\u0002H\u0000\u001a\f\u0010\u0016\u001a\u00020\n*\u00020\u0002H\u0000\u001a\u0014\u0010\u0017\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\",\u0010\u000f\u001a\u0004\u0018\u00010\u000e*\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u000e8@@@X\u0080\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001b"}, d2 = {"argumentsCount", "", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "getArgumentsCount", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;)I", "dispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDispatchReceiverType", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;)Lorg/jetbrains/kotlin/types/KotlinType;", "hasExtensionReceiver", "", "getHasExtensionReceiver", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;)Z", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lastArgument", "getLastArgument", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setLastArgument", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getValueArgumentsInParameterOrder", "", "isValueArgumentReorderingRequired", "setExplicitReceiverValue", "", "explicitReceiverValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallBuilderKt {
    public static final int getArgumentsCount(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        return callBuilder.getIrValueArgumentsByIndex().length;
    }

    public static final IrExpression getLastArgument(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        return (IrExpression) ArraysKt.last(callBuilder.getIrValueArgumentsByIndex());
    }

    public static final void setLastArgument(CallBuilder callBuilder, IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        callBuilder.getIrValueArgumentsByIndex()[getArgumentsCount(callBuilder) - 1] = irExpression;
    }

    public static final List<IrExpression> getValueArgumentsInParameterOrder(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        List<ValueParameterDescriptor> valueParameters = callBuilder.getDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(callBuilder.getIrValueArgumentsByIndex()[((ValueParameterDescriptor) it.next()).getIndex()]);
        }
        return arrayList;
    }

    public static final boolean isValueArgumentReorderingRequired(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        if (!KotlinUtilsKt.isValueArgumentReorderingRequired(callBuilder.getOriginal())) {
            return false;
        }
        for (IrExpression irExpression : callBuilder.getIrValueArgumentsByIndex()) {
            if (irExpression != null && !IrExpressionsKt.hasNoSideEffects(irExpression)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean getHasExtensionReceiver(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        return callBuilder.getDescriptor().getExtensionReceiverParameter() != null;
    }

    public static final KotlinType getDispatchReceiverType(CallBuilder callBuilder) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callBuilder.getDescriptor().mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null) {
            return receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType();
        }
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt$setExplicitReceiverValue$1, reason: invalid class name */
    /* JADX INFO: compiled from: CallBuilder.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/jetbrains/kotlin/psi2ir/intermediate/CallBuilderKt$setExplicitReceiverValue$1", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallExpressionBuilder;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements CallReceiver {
        final /* synthetic */ IntermediateValue $explicitReceiverValue;
        final /* synthetic */ CallReceiver $previousCallReceiver;
        final /* synthetic */ CallBuilder $this_setExplicitReceiverValue;

        AnonymousClass1(CallReceiver callReceiver, CallBuilder callBuilder, IntermediateValue intermediateValue) {
            this.$previousCallReceiver = callReceiver;
            this.$this_setExplicitReceiverValue = callBuilder;
            this.$explicitReceiverValue = intermediateValue;
        }

        @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver
        public IrExpression call(final CallExpressionBuilder builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            CallReceiver callReceiver = this.$previousCallReceiver;
            final CallBuilder callBuilder = this.$this_setExplicitReceiverValue;
            final IntermediateValue intermediateValue = this.$explicitReceiverValue;
            return callReceiver.call(new CallExpressionBuilder() { // from class: org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt$setExplicitReceiverValue$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.psi2ir.intermediate.CallExpressionBuilder
                public final IrExpression withReceivers(IntermediateValue intermediateValue2, IntermediateValue intermediateValue3, List list) {
                    return CallBuilderKt.AnonymousClass1.call$lambda$0(callBuilder, intermediateValue, builder, intermediateValue2, intermediateValue3, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final IrExpression call$lambda$0(CallBuilder this_setExplicitReceiverValue, IntermediateValue explicitReceiverValue, CallExpressionBuilder builder, IntermediateValue intermediateValue, IntermediateValue intermediateValue2, List contextReceiverValues) {
            Intrinsics.checkNotNullParameter(this_setExplicitReceiverValue, "$this_setExplicitReceiverValue");
            Intrinsics.checkNotNullParameter(explicitReceiverValue, "$explicitReceiverValue");
            Intrinsics.checkNotNullParameter(builder, "$builder");
            Intrinsics.checkNotNullParameter(contextReceiverValues, "contextReceiverValues");
            if (!CallBuilderKt.getHasExtensionReceiver(this_setExplicitReceiverValue)) {
                intermediateValue = explicitReceiverValue;
            }
            if (!CallBuilderKt.getHasExtensionReceiver(this_setExplicitReceiverValue)) {
                explicitReceiverValue = null;
            }
            return builder.withReceivers(intermediateValue, explicitReceiverValue, contextReceiverValues);
        }
    }

    public static final void setExplicitReceiverValue(CallBuilder callBuilder, IntermediateValue explicitReceiverValue) {
        Intrinsics.checkNotNullParameter(callBuilder, "<this>");
        Intrinsics.checkNotNullParameter(explicitReceiverValue, "explicitReceiverValue");
        callBuilder.setCallReceiver(new AnonymousClass1(callBuilder.getCallReceiver(), callBuilder, explicitReceiverValue));
    }
}
