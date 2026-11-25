package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.types.expressions.CoercionStrategy;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001BC\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0016\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010R\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R!\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceAdaptation;", "", "argumentTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "coercionStrategy", "Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;", "defaults", "", "mappedArguments", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceMappedArguments;", "suspendConversionStrategy", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;", "([Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;ILjava/util/Map;Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;)V", "getArgumentTypes", "()[Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "[Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getCoercionStrategy", "()Lorg/jetbrains/kotlin/types/expressions/CoercionStrategy;", "getDefaults", "()I", "getMappedArguments", "()Ljava/util/Map;", "getSuspendConversionStrategy", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceConversionStrategy;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceAdaptation {
    private final ConeKotlinType[] argumentTypes;
    private final CoercionStrategy coercionStrategy;
    private final int defaults;
    private final Map<FirValueParameter, ResolvedCallArgument> mappedArguments;
    private final CallableReferenceConversionStrategy suspendConversionStrategy;

    /* JADX WARN: Multi-variable type inference failed */
    public CallableReferenceAdaptation(ConeKotlinType[] argumentTypes, CoercionStrategy coercionStrategy, int i, Map<FirValueParameter, ? extends ResolvedCallArgument> mappedArguments, CallableReferenceConversionStrategy suspendConversionStrategy) {
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

    public final ConeKotlinType[] getArgumentTypes() {
        return this.argumentTypes;
    }

    public final CoercionStrategy getCoercionStrategy() {
        return this.coercionStrategy;
    }

    public final int getDefaults() {
        return this.defaults;
    }

    public final Map<FirValueParameter, ResolvedCallArgument> getMappedArguments() {
        return this.mappedArguments;
    }

    public final CallableReferenceConversionStrategy getSuspendConversionStrategy() {
        return this.suspendConversionStrategy;
    }
}
