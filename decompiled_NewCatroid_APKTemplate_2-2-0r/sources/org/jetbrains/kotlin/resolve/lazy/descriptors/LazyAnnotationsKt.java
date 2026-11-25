package org.jetbrains.kotlin.resolve.lazy.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: LazyAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"getSourceForArgument", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LazyAnnotationsKt {
    public static final SourceElement getSourceForArgument(AnnotationDescriptor annotationDescriptor, Name name2) {
        SourceElement sourceForArgument;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        LazyAnnotationDescriptor lazyAnnotationDescriptor = annotationDescriptor instanceof LazyAnnotationDescriptor ? (LazyAnnotationDescriptor) annotationDescriptor : null;
        if (lazyAnnotationDescriptor != null && (sourceForArgument = lazyAnnotationDescriptor.getSourceForArgument(name2)) != null) {
            return sourceForArgument;
        }
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }
}
