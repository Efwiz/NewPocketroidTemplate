package org.jetbrains.kotlin.load.java.structure;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t0\u00070\u0006*\u00020\n\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getClassId", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)Lorg/jetbrains/kotlin/name/ClassId;", "buildLazyValueForMap", "Lkotlin/Lazy;", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaElementsKt {
    public static final Lazy<Map<FqName, JavaAnnotation>> buildLazyValueForMap(final JavaAnnotationOwner javaAnnotationOwner) {
        Intrinsics.checkNotNullParameter(javaAnnotationOwner, "<this>");
        return LazyKt.lazy(new Function0<Map<FqName, ? extends JavaAnnotation>>() { // from class: org.jetbrains.kotlin.load.java.structure.JavaElementsKt.buildLazyValueForMap.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends JavaAnnotation> invoke() {
                Collection<JavaAnnotation> collectionMo7252getAnnotations = javaAnnotationOwner.mo7252getAnnotations();
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collectionMo7252getAnnotations, 10)), 16));
                for (Object obj : collectionMo7252getAnnotations) {
                    ClassId classId = ((JavaAnnotation) obj).getClassId();
                    linkedHashMap.put(classId != null ? classId.asSingleFqName() : null, obj);
                }
                return linkedHashMap;
            }
        });
    }

    public static final ClassId getClassId(JavaClass javaClass) {
        ClassId classId;
        ClassId classIdCreateNestedClassId;
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        JavaClass javaClassMo7256getOuterClass = javaClass.mo7256getOuterClass();
        if (javaClassMo7256getOuterClass != null && (classId = getClassId(javaClassMo7256getOuterClass)) != null && (classIdCreateNestedClassId = classId.createNestedClassId(javaClass.getName())) != null) {
            return classIdCreateNestedClassId;
        }
        FqName fqName = javaClass.getFqName();
        if (fqName != null) {
            return ClassId.topLevel(fqName);
        }
        return null;
    }
}
