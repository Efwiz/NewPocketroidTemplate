package org.jetbrains.kotlin.container;

import java.lang.reflect.Constructor;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: Resolve.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/container/ConstructorBinding;", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Ljava/lang/reflect/Constructor;", "argumentDescriptors", "", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "(Ljava/lang/reflect/Constructor;Ljava/util/List;)V", "getArgumentDescriptors", "()Ljava/util/List;", "getConstructor", "()Ljava/lang/reflect/Constructor;", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstructorBinding {
    private final List<ValueDescriptor> argumentDescriptors;
    private final Constructor<?> constructor;

    /* JADX WARN: Multi-variable type inference failed */
    public ConstructorBinding(Constructor<?> constructor, List<? extends ValueDescriptor> argumentDescriptors) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(argumentDescriptors, "argumentDescriptors");
        this.constructor = constructor;
        this.argumentDescriptors = argumentDescriptors;
    }

    public final List<ValueDescriptor> getArgumentDescriptors() {
        return this.argumentDescriptors;
    }

    public final Constructor<?> getConstructor() {
        return this.constructor;
    }
}
