package org.jetbrains.kotlin.load.java.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;

/* JADX INFO: compiled from: LazyJavaAnnotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"resolveAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "annotationsOwner", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaAnnotationsKt {
    public static final Annotations resolveAnnotations(LazyJavaResolverContext lazyJavaResolverContext, JavaAnnotationOwner annotationsOwner) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "<this>");
        Intrinsics.checkNotNullParameter(annotationsOwner, "annotationsOwner");
        return new LazyJavaAnnotations(lazyJavaResolverContext, annotationsOwner, false, 4, null);
    }
}
