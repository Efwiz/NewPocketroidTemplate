package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"checkCallInvariants", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "checkReceiverInvariants", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallKt {

    /* JADX INFO: compiled from: KotlinCall.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinCallKind.values().length];
            try {
                iArr[KotlinCallKind.FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinCallKind.INVOKE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinCallKind.VARIABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinCallKind.CALLABLE_REFERENCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinCallKind.UNSUPPORTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final void checkReceiverInvariants(SimpleKotlinCallArgument simpleKotlinCallArgument) {
        simpleKotlinCallArgument.isSpread();
        simpleKotlinCallArgument.getArgumentName();
    }

    public static final void checkCallInvariants(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "<this>");
        if (!(kotlinCall.getExplicitReceiver() instanceof LambdaKotlinCallArgument)) {
            boolean z = kotlinCall.getExplicitReceiver() instanceof CallableReferenceKotlinCallArgument;
        }
        ReceiverKotlinCallArgument explicitReceiver = kotlinCall.getExplicitReceiver();
        SimpleKotlinCallArgument simpleKotlinCallArgument = explicitReceiver instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) explicitReceiver : null;
        if (simpleKotlinCallArgument != null) {
            checkReceiverInvariants(simpleKotlinCallArgument);
        }
        ReceiverKotlinCallArgument dispatchReceiverForInvokeExtension = kotlinCall.getDispatchReceiverForInvokeExtension();
        SimpleKotlinCallArgument simpleKotlinCallArgument2 = dispatchReceiverForInvokeExtension instanceof SimpleKotlinCallArgument ? (SimpleKotlinCallArgument) dispatchReceiverForInvokeExtension : null;
        if (simpleKotlinCallArgument2 != null) {
            checkReceiverInvariants(simpleKotlinCallArgument2);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kotlinCall.getCallKind().ordinal()];
        if (i == 1 || i == 2) {
            if (kotlinCall.getExternalArgument() != null) {
                KotlinCallArgument externalArgument = kotlinCall.getExternalArgument();
                Intrinsics.checkNotNull(externalArgument);
                externalArgument.isSpread();
            }
            KotlinCallArgument externalArgument2 = kotlinCall.getExternalArgument();
            if (externalArgument2 != null) {
                externalArgument2.getArgumentName();
            }
            if (kotlinCall.getDispatchReceiverForInvokeExtension() != null) {
                ReceiverKotlinCallArgument dispatchReceiverForInvokeExtension2 = kotlinCall.getDispatchReceiverForInvokeExtension();
                Intrinsics.checkNotNull(dispatchReceiverForInvokeExtension2);
                dispatchReceiverForInvokeExtension2.getIsSafeCall();
                return;
            }
            return;
        }
        if (i == 3) {
            kotlinCall.getExternalArgument();
            kotlinCall.getArgumentsInParenthesis().isEmpty();
            kotlinCall.getDispatchReceiverForInvokeExtension();
        } else if (i != 4) {
            if (i == 5) {
                throw new IllegalStateException("Call with UNSUPPORTED kind".toString());
            }
        } else {
            kotlinCall.getArgumentsInParenthesis().isEmpty();
            kotlinCall.getTypeArguments().isEmpty();
            kotlinCall.getExternalArgument();
        }
    }
}
