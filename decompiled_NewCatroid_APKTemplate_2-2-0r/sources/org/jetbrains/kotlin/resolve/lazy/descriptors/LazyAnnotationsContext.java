package org.jetbrains.kotlin.resolve.lazy.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "getAnnotationResolver", "()Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LazyAnnotationsContext {
    private final AnnotationResolver annotationResolver;
    private final StorageManager storageManager;
    private final BindingTrace trace;

    public abstract LexicalScope getScope();

    public LazyAnnotationsContext(AnnotationResolver annotationResolver, StorageManager storageManager, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.annotationResolver = annotationResolver;
        this.storageManager = storageManager;
        this.trace = trace;
    }

    public final AnnotationResolver getAnnotationResolver() {
        return this.annotationResolver;
    }

    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    public final BindingTrace getTrace() {
        return this.trace;
    }
}
