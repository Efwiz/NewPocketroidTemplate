package org.jetbrains.kotlin.container;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Dsl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/container/UnresolvedServiceException;", "Ljava/lang/IllegalArgumentException;", "Lkotlin/IllegalArgumentException;", "container", "Lorg/jetbrains/kotlin/container/ComponentProvider;", "request", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/ComponentProvider;Ljava/lang/Class;)V"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnresolvedServiceException extends IllegalArgumentException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnresolvedServiceException(ComponentProvider container, Class<?> request) {
        super("Unresolved service: " + request + " in " + container);
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(request, "request");
    }
}
