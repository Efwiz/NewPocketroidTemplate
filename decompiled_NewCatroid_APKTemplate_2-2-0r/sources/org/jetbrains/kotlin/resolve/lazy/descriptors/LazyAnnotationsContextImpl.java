package org.jetbrains.kotlin.resolve.lazy.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContextImpl;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "(Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;)V", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyAnnotationsContextImpl extends LazyAnnotationsContext {
    private final LexicalScope scope;

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContext
    public LexicalScope getScope() {
        return this.scope;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyAnnotationsContextImpl(AnnotationResolver annotationResolver, StorageManager storageManager, BindingTrace trace, LexicalScope scope) {
        super(annotationResolver, storageManager, trace);
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
    }
}
