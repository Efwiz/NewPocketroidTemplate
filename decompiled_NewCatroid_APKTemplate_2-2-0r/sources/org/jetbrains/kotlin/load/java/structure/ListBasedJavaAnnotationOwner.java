package org.jetbrains.kotlin.load.java.structure;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/ListBasedJavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "findAnnotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ListBasedJavaAnnotationOwner extends JavaAnnotationOwner {
    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    default JavaAnnotation mo7251findAnnotation(FqName fqName) {
        Object obj;
        Object next;
        ClassId classId;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = mo7252getAnnotations().iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                classId = ((JavaAnnotation) next).getClassId();
            }
            return (JavaAnnotation) obj;
        } while (!Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName));
        obj = next;
        return (JavaAnnotation) obj;
    }
}
