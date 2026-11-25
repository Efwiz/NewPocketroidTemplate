package org.jetbrains.kotlin.container;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Singletons.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/container/DefaultSingletonTypeComponentDescriptor;", "Lorg/jetbrains/kotlin/container/SingletonTypeComponentDescriptor;", "container", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "klass", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/ComponentContainer;Ljava/lang/Class;)V", "toString", ""}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultSingletonTypeComponentDescriptor extends SingletonTypeComponentDescriptor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultSingletonTypeComponentDescriptor(ComponentContainer container, Class<?> klass) {
        super(container, klass);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(klass, "klass");
    }

    @Override // org.jetbrains.kotlin.container.SingletonTypeComponentDescriptor
    public String toString() {
        return "Default: " + getKlass().getSimpleName();
    }
}
