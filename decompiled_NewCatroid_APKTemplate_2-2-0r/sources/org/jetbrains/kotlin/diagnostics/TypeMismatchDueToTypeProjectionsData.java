package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: diagnosticUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/TypeMismatchDueToTypeProjectionsData;", "", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionType", "receiverType", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "getCallableDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getExpressionType", "getReceiverType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeMismatchDueToTypeProjectionsData {
    private final CallableDescriptor callableDescriptor;
    private final KotlinType expectedType;
    private final KotlinType expressionType;
    private final KotlinType receiverType;

    public TypeMismatchDueToTypeProjectionsData(KotlinType expectedType, KotlinType expressionType, KotlinType receiverType, CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(receiverType, "receiverType");
        Intrinsics.checkNotNullParameter(callableDescriptor, "callableDescriptor");
        this.expectedType = expectedType;
        this.expressionType = expressionType;
        this.receiverType = receiverType;
        this.callableDescriptor = callableDescriptor;
    }

    public final KotlinType getExpectedType() {
        return this.expectedType;
    }

    public final KotlinType getExpressionType() {
        return this.expressionType;
    }

    public final KotlinType getReceiverType() {
        return this.receiverType;
    }

    public final CallableDescriptor getCallableDescriptor() {
        return this.callableDescriptor;
    }
}
