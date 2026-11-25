package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes12.dex */
public interface ListBasedJavaAnnotationOwner extends JavaAnnotationOwner {

    /* JADX INFO: compiled from: javaElements.kt */
    public static final class DefaultImpls {
        public static JavaAnnotation findAnnotation(ListBasedJavaAnnotationOwner listBasedJavaAnnotationOwner, FqName fqName) {
            Object obj;
            Object next;
            ClassId classId;
            Intrinsics.checkNotNullParameter(listBasedJavaAnnotationOwner, "this");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Iterator<T> it = listBasedJavaAnnotationOwner.getAnnotations().iterator();
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
}
