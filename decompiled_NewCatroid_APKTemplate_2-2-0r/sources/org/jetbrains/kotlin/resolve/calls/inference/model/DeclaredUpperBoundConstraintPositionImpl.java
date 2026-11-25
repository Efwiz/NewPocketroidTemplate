package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;

/* JADX INFO: compiled from: ConstraintPositionAndErrorsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/DeclaredUpperBoundConstraintPositionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/DeclaredUpperBoundConstraintPosition;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeParameter", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)V", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclaredUpperBoundConstraintPositionImpl extends DeclaredUpperBoundConstraintPosition<TypeParameterDescriptor> {
    private final KotlinCall kotlinCall;

    public final KotlinCall getKotlinCall() {
        return this.kotlinCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeclaredUpperBoundConstraintPositionImpl(TypeParameterDescriptor typeParameter, KotlinCall kotlinCall) {
        super(typeParameter);
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        this.kotlinCall = kotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition
    public String toString() {
        return "DeclaredUpperBound " + getTypeParameter().getName() + " from " + getTypeParameter().getModuleDescriptor();
    }
}
