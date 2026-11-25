package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: BuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ComposedSubstitutor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "left", "right", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;)V", "isEmpty", "", "()Z", "getLeft", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "getRight", "substituteNotNullTypeWithConstructor", "Lorg/jetbrains/kotlin/types/UnwrappedType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComposedSubstitutor implements NewTypeSubstitutor {
    private final NewTypeSubstitutor left;
    private final NewTypeSubstitutor right;

    public ComposedSubstitutor(NewTypeSubstitutor left, NewTypeSubstitutor right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.left = left;
        this.right = right;
    }

    public final NewTypeSubstitutor getLeft() {
        return this.left;
    }

    public final NewTypeSubstitutor getRight() {
        return this.right;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
        TypeConstructor constructor2;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        UnwrappedType unwrappedTypeSubstituteNotNullTypeWithConstructor = this.right.substituteNotNullTypeWithConstructor(constructor);
        NewTypeSubstitutor newTypeSubstitutor = this.left;
        if (unwrappedTypeSubstituteNotNullTypeWithConstructor != null && (constructor2 = unwrappedTypeSubstituteNotNullTypeWithConstructor.getConstructor()) != null) {
            constructor = constructor2;
        }
        UnwrappedType unwrappedTypeSubstituteNotNullTypeWithConstructor2 = newTypeSubstitutor.substituteNotNullTypeWithConstructor(constructor);
        return unwrappedTypeSubstituteNotNullTypeWithConstructor2 == null ? unwrappedTypeSubstituteNotNullTypeWithConstructor : unwrappedTypeSubstituteNotNullTypeWithConstructor2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public boolean isEmpty() {
        return this.left.isEmpty() && this.right.isEmpty();
    }
}
