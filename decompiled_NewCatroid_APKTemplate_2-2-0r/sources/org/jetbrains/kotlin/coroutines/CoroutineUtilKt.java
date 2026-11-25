package org.jetbrains.kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: coroutineUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\n"}, d2 = {"hasFunctionOrSuspendFunctionType", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getHasFunctionOrSuspendFunctionType", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)Z", "hasSuspendFunctionType", "getHasSuspendFunctionType", "isSuspendLambda", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineUtilKt {
    public static final boolean isSuspendLambda(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return (callableDescriptor instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) callableDescriptor).isSuspend();
    }

    public static final boolean getHasSuspendFunctionType(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        KotlinType returnType = valueParameterDescriptor.getReturnType();
        return returnType != null && FunctionTypesKt.isSuspendFunctionType(returnType);
    }

    public static final boolean getHasFunctionOrSuspendFunctionType(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        KotlinType returnType = valueParameterDescriptor.getReturnType();
        return returnType != null && FunctionTypesKt.isFunctionOrSuspendFunctionType(returnType);
    }
}
