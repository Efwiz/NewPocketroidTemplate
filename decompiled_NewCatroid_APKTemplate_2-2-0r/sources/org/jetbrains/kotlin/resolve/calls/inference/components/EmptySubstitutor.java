package org.jetbrains.kotlin.resolve.calls.inference.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/EmptySubstitutor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "()V", "isEmpty", "", "()Z", "substituteNotNullTypeWithConstructor", "Lorg/jetbrains/kotlin/types/UnwrappedType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptySubstitutor implements NewTypeSubstitutor {
    public static final EmptySubstitutor INSTANCE = new EmptySubstitutor();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public boolean isEmpty() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return null;
    }

    private EmptySubstitutor() {
    }
}
