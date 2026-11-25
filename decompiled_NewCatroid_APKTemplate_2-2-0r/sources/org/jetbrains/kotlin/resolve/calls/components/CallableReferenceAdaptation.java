package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.CoercionStrategy;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B?\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fR\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceAdaptation;", "", "argumentTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "coercionStrategy", "Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;", "defaults", "", "mappedArguments", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "suspendConversionStrategy", "Lorg/jetbrains/kotlin/resolve/calls/components/SuspendConversionStrategy;", "([Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;ILjava/util/Map;Lorg/jetbrains/kotlin/resolve/calls/components/SuspendConversionStrategy;)V", "getArgumentTypes", "()[Lorg/jetbrains/kotlin/types/KotlinType;", "[Lorg/jetbrains/kotlin/types/KotlinType;", "getCoercionStrategy", "()Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;", "getDefaults", "()I", "getMappedArguments", "()Ljava/util/Map;", "getSuspendConversionStrategy", "()Lorg/jetbrains/kotlin/resolve/calls/components/SuspendConversionStrategy;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceAdaptation {
    private final KotlinType[] argumentTypes;
    private final CoercionStrategy coercionStrategy;
    private final int defaults;
    private final Map<ValueParameterDescriptor, ResolvedCallArgument> mappedArguments;
    private final SuspendConversionStrategy suspendConversionStrategy;

    /* JADX WARN: Multi-variable type inference failed */
    public CallableReferenceAdaptation(KotlinType[] argumentTypes, CoercionStrategy coercionStrategy, int i, Map<ValueParameterDescriptor, ? extends ResolvedCallArgument> mappedArguments, SuspendConversionStrategy suspendConversionStrategy) {
        Intrinsics.checkNotNullParameter(argumentTypes, "argumentTypes");
        Intrinsics.checkNotNullParameter(coercionStrategy, "coercionStrategy");
        Intrinsics.checkNotNullParameter(mappedArguments, "mappedArguments");
        Intrinsics.checkNotNullParameter(suspendConversionStrategy, "suspendConversionStrategy");
        this.argumentTypes = argumentTypes;
        this.coercionStrategy = coercionStrategy;
        this.defaults = i;
        this.mappedArguments = mappedArguments;
        this.suspendConversionStrategy = suspendConversionStrategy;
    }

    public final KotlinType[] getArgumentTypes() {
        return this.argumentTypes;
    }

    public final CoercionStrategy getCoercionStrategy() {
        return this.coercionStrategy;
    }

    public final int getDefaults() {
        return this.defaults;
    }

    public final Map<ValueParameterDescriptor, ResolvedCallArgument> getMappedArguments() {
        return this.mappedArguments;
    }

    public final SuspendConversionStrategy getSuspendConversionStrategy() {
        return this.suspendConversionStrategy;
    }
}
