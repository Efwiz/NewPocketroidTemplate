package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: DslMarkerUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0002¨\u0006\u0007"}, d2 = {"extractDslMarkerFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "isDslMarker", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DslMarkerUtilsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<FqName> extractDslMarkerFqNames(Annotations annotations) {
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            if (isDslMarker(annotationDescriptor)) {
                arrayList.add(annotationDescriptor);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FqName fqName = ((AnnotationDescriptor) it.next()).getFqName();
            if (fqName != null) {
                arrayList2.add(fqName);
            }
        }
        return arrayList2;
    }

    private static final boolean isDslMarker(AnnotationDescriptor annotationDescriptor) {
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass == null) {
            return false;
        }
        return annotationClass.getAnnotations().hasAnnotation(DslMarkerUtils.INSTANCE.getDSL_MARKER_FQ_NAME());
    }
}
