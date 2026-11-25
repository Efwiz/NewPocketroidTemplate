package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ReflectJavaAnnotationOwner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b*\b\u0012\u0004\u0012\u00020\u00030\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"findAnnotation", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotation;", "", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "([Ljava/lang/annotation/Annotation;Lorg/jetbrains/kotlin/name/FqName;)Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotation;", "getAnnotations", "", "([Ljava/lang/annotation/Annotation;)Ljava/util/List;", "descriptors.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaAnnotationOwnerKt {
    public static final List<ReflectJavaAnnotation> getAnnotations(Annotation[] annotationArr) {
        Intrinsics.checkNotNullParameter(annotationArr, "<this>");
        ArrayList arrayList = new ArrayList(annotationArr.length);
        for (Annotation annotation : annotationArr) {
            arrayList.add(new ReflectJavaAnnotation(annotation));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002e  */
    /* JADX WARN: Code duplicated, block: B:15:? A[RETURN, SYNTHETIC] */
    public static final ReflectJavaAnnotation findAnnotation(Annotation[] annotationArr, FqName fqName) {
        Intrinsics.checkNotNullParameter(annotationArr, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        for (Annotation annotation : annotationArr) {
            if (Intrinsics.areEqual(ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation))).asSingleFqName(), fqName)) {
                if (annotation != null) {
                    return new ReflectJavaAnnotation(annotation);
                }
                return null;
            }
        }
        annotation = null;
        if (annotation != null) {
            return new ReflectJavaAnnotation(annotation);
        }
        return null;
    }
}
