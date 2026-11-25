package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0004H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutorByConstructorMap;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "map", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Ljava/util/Map;)V", "isEmpty", "", "()Z", "getMap", "()Ljava/util/Map;", "substituteNotNullTypeWithConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewTypeSubstitutorByConstructorMap implements NewTypeSubstitutor {
    private final Map<TypeConstructor, UnwrappedType> map;

    /* JADX WARN: Multi-variable type inference failed */
    public NewTypeSubstitutorByConstructorMap(Map<TypeConstructor, ? extends UnwrappedType> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    public final Map<TypeConstructor, UnwrappedType> getMap() {
        return this.map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.map.get(constructor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
}
