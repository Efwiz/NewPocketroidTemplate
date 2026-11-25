package org.jetbrains.kotlin.resolve.descriptorUtil;

import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationWithTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: annotationsForResolveUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\b\u0010\n\u001a\u00020\bH\u0016J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/descriptorUtil/AnnotationsWithOnly;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "presentAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getPresentAnnotation", "()Lorg/jetbrains/kotlin/name/FqName;", "hasAnnotation", "", "fqName", "isEmpty", "iterator", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AnnotationsWithOnly implements Annotations {
    private final FqName presentAnnotation;

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return false;
    }

    public AnnotationsWithOnly(FqName presentAnnotation) {
        Intrinsics.checkNotNullParameter(presentAnnotation, "presentAnnotation");
        this.presentAnnotation = presentAnnotation;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    /* JADX INFO: renamed from: findAnnotation */
    public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.findAnnotation(this, fqName);
    }

    public final FqName getPresentAnnotation() {
        return this.presentAnnotation;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
    public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
        return Annotations.DefaultImpls.getUseSiteTargetedAnnotations(this);
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationDescriptor> iterator() {
        return CollectionsKt.emptyList().iterator();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean hasAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return Intrinsics.areEqual(fqName, this.presentAnnotation);
    }
}
