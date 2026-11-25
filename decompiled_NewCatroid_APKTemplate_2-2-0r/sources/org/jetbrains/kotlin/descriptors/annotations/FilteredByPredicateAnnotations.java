package org.jetbrains.kotlin.descriptors.annotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u0096\u0002R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/FilteredByPredicateAnnotations;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "filter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "", "(Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lkotlin/jvm/functions/Function1;)V", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "isEmpty", "iterator", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilteredByPredicateAnnotations implements Annotations {
    private final Annotations delegate;
    private final Function1<AnnotationDescriptor, Boolean> filter;

    /* JADX WARN: Multi-variable type inference failed */
    public FilteredByPredicateAnnotations(Annotations delegate, Function1<? super AnnotationDescriptor, Boolean> filter) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.delegate = delegate;
        this.filter = filter;
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

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return !iterator().hasNext();
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationDescriptor> iterator() {
        Annotations annotations = this.delegate;
        Function1<AnnotationDescriptor, Boolean> function1 = this.filter;
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            if (function1.invoke(annotationDescriptor).booleanValue()) {
                arrayList.add(annotationDescriptor);
            }
        }
        return arrayList.iterator();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    /* JADX INFO: renamed from: findAnnotation */
    public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        AnnotationDescriptor annotationDescriptorFindAnnotation = Annotations.DefaultImpls.findAnnotation(this, fqName);
        if (annotationDescriptorFindAnnotation == null || !this.filter.invoke(annotationDescriptorFindAnnotation).booleanValue()) {
            return null;
        }
        return annotationDescriptorFindAnnotation;
    }
}
