package org.jetbrains.kotlin.descriptors.annotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: TargetedAnnotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u0017J\b\u0010\t\u001a\u00020\nH\u0016J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/TargetedAnnotations;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "standardAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "targetedAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationWithTarget;", "(Ljava/util/List;Ljava/util/List;)V", "getUseSiteTargetedAnnotations", "isEmpty", "", "iterator", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TargetedAnnotations implements Annotations {
    private final List<AnnotationDescriptor> standardAnnotations;
    private final List<AnnotationWithTarget> targetedAnnotations;

    /* JADX WARN: Multi-variable type inference failed */
    public TargetedAnnotations(List<? extends AnnotationDescriptor> standardAnnotations, List<AnnotationWithTarget> targetedAnnotations) {
        Intrinsics.checkNotNullParameter(standardAnnotations, "standardAnnotations");
        Intrinsics.checkNotNullParameter(targetedAnnotations, "targetedAnnotations");
        this.standardAnnotations = standardAnnotations;
        this.targetedAnnotations = targetedAnnotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    /* JADX INFO: renamed from: findAnnotation */
    public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.findAnnotation(this, fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean hasAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.hasAnnotation(this, fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return this.standardAnnotations.isEmpty() && this.targetedAnnotations.isEmpty();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
    public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
        return this.targetedAnnotations;
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationDescriptor> iterator() {
        return this.standardAnnotations.iterator();
    }

    public String toString() {
        return CollectionsKt.plus((Collection) this.standardAnnotations, (Iterable) this.targetedAnnotations).toString();
    }
}
