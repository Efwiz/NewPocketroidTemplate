package org.jetbrains.kotlin.resolve.lazy.descriptors;

import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationWithTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;

/* JADX INFO: compiled from: LazyAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015H\u0096\u0002R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotations;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "Lorg/jetbrains/kotlin/resolve/lazy/LazyEntity;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "annotationEntries", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "(Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;Ljava/util/List;)V", "annotation", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAnnotationEntries", "()Ljava/util/List;", "getC", "()Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "forceResolveAllContents", "", "isEmpty", "", "iterator", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyAnnotations implements Annotations, LazyEntity {
    private final MemoizedFunctionToNotNull<KtAnnotationEntry, AnnotationDescriptor> annotation;
    private final List<KtAnnotationEntry> annotationEntries;
    private final LazyAnnotationsContext c;

    /* JADX WARN: Multi-variable type inference failed */
    public LazyAnnotations(LazyAnnotationsContext c, List<? extends KtAnnotationEntry> annotationEntries) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(annotationEntries, "annotationEntries");
        this.c = c;
        this.annotationEntries = annotationEntries;
        this.annotation = c.getStorageManager().createMemoizedFunction(new Function1<KtAnnotationEntry, AnnotationDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotations$annotation$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final AnnotationDescriptor invoke(KtAnnotationEntry entry) {
                Intrinsics.checkNotNullParameter(entry, "entry");
                AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) this.this$0.getC().getTrace().get(BindingContext.ANNOTATION, entry);
                return annotationDescriptor == null ? new LazyAnnotationDescriptor(this.this$0.getC(), entry) : annotationDescriptor;
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    /* JADX INFO: renamed from: findAnnotation */
    public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.findAnnotation(this, fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
    public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
        return Annotations.DefaultImpls.getUseSiteTargetedAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean hasAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.hasAnnotation(this, fqName);
    }

    public final LazyAnnotationsContext getC() {
        return this.c;
    }

    public final List<KtAnnotationEntry> getAnnotationEntries() {
        return this.annotationEntries;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return this.annotationEntries.isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationDescriptor> iterator() {
        return SequencesKt.map(CollectionsKt.asSequence(this.annotationEntries), this.annotation).iterator();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
    public void forceResolveAllContents() {
        for (AnnotationDescriptor annotationDescriptor : this) {
        }
    }
}
