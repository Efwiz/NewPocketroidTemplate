package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ResolvedAtomCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001\u000fB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes;", "", "returnType", "Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;", "parameterTypes", "", "receiverType", "contextReceiverTypes", "(Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;Ljava/util/List;)V", "getContextReceiverTypes", "()Ljava/util/List;", "getParameterTypes", "getReceiverType", "()Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;", "getReturnType", "ProcessedType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionLiteralTypes {
    private final List<ProcessedType> contextReceiverTypes;
    private final List<ProcessedType> parameterTypes;
    private final ProcessedType receiverType;
    private final ProcessedType returnType;

    public FunctionLiteralTypes(ProcessedType returnType, List<ProcessedType> parameterTypes, ProcessedType processedType, List<ProcessedType> contextReceiverTypes) {
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.receiverType = processedType;
        this.contextReceiverTypes = contextReceiverTypes;
    }

    public final ProcessedType getReturnType() {
        return this.returnType;
    }

    public final List<ProcessedType> getParameterTypes() {
        return this.parameterTypes;
    }

    public final ProcessedType getReceiverType() {
        return this.receiverType;
    }

    public final List<ProcessedType> getContextReceiverTypes() {
        return this.contextReceiverTypes;
    }

    /* JADX INFO: compiled from: ResolvedAtomCompleter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionLiteralTypes$ProcessedType;", "", "substitutedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "approximatedType", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getApproximatedType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getSubstitutedType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProcessedType {
        private final KotlinType approximatedType;
        private final KotlinType substitutedType;

        public ProcessedType(KotlinType substitutedType, KotlinType approximatedType) {
            Intrinsics.checkNotNullParameter(substitutedType, "substitutedType");
            Intrinsics.checkNotNullParameter(approximatedType, "approximatedType");
            this.substitutedType = substitutedType;
            this.approximatedType = approximatedType;
        }

        public final KotlinType getApproximatedType() {
            return this.approximatedType;
        }

        public final KotlinType getSubstitutedType() {
            return this.substitutedType;
        }
    }
}
