package org.jetbrains.kotlin.load.java.components;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaAnnotationMapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\"\u0010\u0015\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0019R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/JavaAnnotationMapper;", "", "()V", "DEPRECATED_ANNOTATION_MESSAGE", "Lorg/jetbrains/kotlin/name/Name;", "getDEPRECATED_ANNOTATION_MESSAGE$descriptors_jvm", "()Lorg/jetbrains/kotlin/name/Name;", "RETENTION_ANNOTATION_VALUE", "getRETENTION_ANNOTATION_VALUE$descriptors_jvm", "TARGET_ANNOTATION_ALLOWED_TARGETS", "getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm", "kotlinToJavaNameMap", "", "Lorg/jetbrains/kotlin/name/FqName;", "findMappedJavaAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "kotlinName", "annotationOwner", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "mapOrResolveJavaAnnotation", "annotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "isFreshlySupportedAnnotation", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaAnnotationMapper {
    private static final Name DEPRECATED_ANNOTATION_MESSAGE;
    public static final JavaAnnotationMapper INSTANCE = new JavaAnnotationMapper();
    private static final Name RETENTION_ANNOTATION_VALUE;
    private static final Name TARGET_ANNOTATION_ALLOWED_TARGETS;
    private static final Map<FqName, FqName> kotlinToJavaNameMap;

    private JavaAnnotationMapper() {
    }

    static {
        Name nameIdentifier = Name.identifier(CrashHianalyticsData.MESSAGE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DEPRECATED_ANNOTATION_MESSAGE = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("allowedTargets");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        TARGET_ANNOTATION_ALLOWED_TARGETS = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        RETENTION_ANNOTATION_VALUE = nameIdentifier3;
        kotlinToJavaNameMap = MapsKt.mapOf(TuplesKt.to(StandardNames.FqNames.target, JvmAnnotationNames.TARGET_ANNOTATION), TuplesKt.to(StandardNames.FqNames.retention, JvmAnnotationNames.RETENTION_ANNOTATION), TuplesKt.to(StandardNames.FqNames.mustBeDocumented, JvmAnnotationNames.DOCUMENTED_ANNOTATION));
    }

    public final Name getDEPRECATED_ANNOTATION_MESSAGE$descriptors_jvm() {
        return DEPRECATED_ANNOTATION_MESSAGE;
    }

    public final Name getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm() {
        return TARGET_ANNOTATION_ALLOWED_TARGETS;
    }

    public final Name getRETENTION_ANNOTATION_VALUE$descriptors_jvm() {
        return RETENTION_ANNOTATION_VALUE;
    }

    public static /* synthetic */ AnnotationDescriptor mapOrResolveJavaAnnotation$default(JavaAnnotationMapper javaAnnotationMapper, JavaAnnotation javaAnnotation, LazyJavaResolverContext lazyJavaResolverContext, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return javaAnnotationMapper.mapOrResolveJavaAnnotation(javaAnnotation, lazyJavaResolverContext, z);
    }

    public final AnnotationDescriptor mapOrResolveJavaAnnotation(JavaAnnotation annotation, LazyJavaResolverContext c, boolean isFreshlySupportedAnnotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c, "c");
        ClassId classId = annotation.getClassId();
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JvmAnnotationNames.TARGET_ANNOTATION))) {
            return new JavaTargetAnnotationDescriptor(annotation, c);
        }
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JvmAnnotationNames.RETENTION_ANNOTATION))) {
            return new JavaRetentionAnnotationDescriptor(annotation, c);
        }
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JvmAnnotationNames.DOCUMENTED_ANNOTATION))) {
            return new JavaAnnotationDescriptor(c, annotation, StandardNames.FqNames.mustBeDocumented);
        }
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JvmAnnotationNames.DEPRECATED_ANNOTATION))) {
            return null;
        }
        return new LazyJavaAnnotationDescriptor(c, annotation, isFreshlySupportedAnnotation);
    }

    public final AnnotationDescriptor findMappedJavaAnnotation(FqName kotlinName, JavaAnnotationOwner annotationOwner, LazyJavaResolverContext c) {
        JavaAnnotation javaAnnotationMo7251findAnnotation;
        Intrinsics.checkNotNullParameter(kotlinName, "kotlinName");
        Intrinsics.checkNotNullParameter(annotationOwner, "annotationOwner");
        Intrinsics.checkNotNullParameter(c, "c");
        if (Intrinsics.areEqual(kotlinName, StandardNames.FqNames.deprecated)) {
            FqName DEPRECATED_ANNOTATION = JvmAnnotationNames.DEPRECATED_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(DEPRECATED_ANNOTATION, "DEPRECATED_ANNOTATION");
            JavaAnnotation javaAnnotationMo7251findAnnotation2 = annotationOwner.mo7251findAnnotation(DEPRECATED_ANNOTATION);
            if (javaAnnotationMo7251findAnnotation2 != null || annotationOwner.getIsDeprecatedInJavaDoc()) {
                return new JavaDeprecatedAnnotationDescriptor(javaAnnotationMo7251findAnnotation2, c);
            }
        }
        FqName fqName = kotlinToJavaNameMap.get(kotlinName);
        if (fqName == null || (javaAnnotationMo7251findAnnotation = annotationOwner.mo7251findAnnotation(fqName)) == null) {
            return null;
        }
        return mapOrResolveJavaAnnotation$default(INSTANCE, javaAnnotationMo7251findAnnotation, c, false, 4, null);
    }
}
