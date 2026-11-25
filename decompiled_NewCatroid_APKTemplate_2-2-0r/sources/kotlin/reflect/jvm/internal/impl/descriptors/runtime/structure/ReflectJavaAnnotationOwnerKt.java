package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: ReflectJavaAnnotationOwner.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class ReflectJavaAnnotationOwnerKt {
    public static final List<ReflectJavaAnnotation> getAnnotations(Annotation[] annotationArr) {
        Intrinsics.checkNotNullParameter(annotationArr, "<this>");
        ArrayList arrayList = new ArrayList(annotationArr.length);
        int length = annotationArr.length;
        int i = 0;
        while (i < length) {
            Annotation annotation = annotationArr[i];
            i++;
            arrayList.add(new ReflectJavaAnnotation(annotation));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002e  */
    /* JADX WARN: Code duplicated, block: B:16:? A[RETURN, SYNTHETIC] */
    public static final ReflectJavaAnnotation findAnnotation(Annotation[] annotationArr, FqName fqName) {
        Annotation annotation;
        Intrinsics.checkNotNullParameter(annotationArr, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        int length = annotationArr.length;
        int i = 0;
        while (i < length) {
            annotation = annotationArr[i];
            i++;
            if (Intrinsics.areEqual(ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation))).asSingleFqName(), fqName)) {
                if (annotation == null) {
                    return null;
                }
                return new ReflectJavaAnnotation(annotation);
            }
        }
        annotation = null;
        if (annotation == null) {
            return null;
        }
        return new ReflectJavaAnnotation(annotation);
    }
}
