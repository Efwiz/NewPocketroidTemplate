package org.jetbrains.kotlin.resolve.annotations;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;

/* JADX INFO: compiled from: AnnotationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\"\u001c\u0010\u0000\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0005¨\u0006\u0014"}, d2 = {"JVM_FIELD_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_FIELD_ANNOTATION_FQ_NAME$annotations", "()V", "getJVM_FIELD_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "JVM_STATIC_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getJVM_STATIC_ANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "JVM_STATIC_ANNOTATION_FQ_NAME", "getJVM_STATIC_ANNOTATION_FQ_NAME", "argumentValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "parameterName", "", "hasJvmStaticAnnotation", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationUtilKt {
    private static final FqName JVM_FIELD_ANNOTATION_FQ_NAME;
    private static final ClassId JVM_STATIC_ANNOTATION_CLASS_ID;
    private static final FqName JVM_STATIC_ANNOTATION_FQ_NAME;

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use org.jetbrains.kotlin.load.java.JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME or org.jetbrains.kotlin.resolve.jvm.annotations.hasJvmFieldAnnotation instead.", replaceWith = @ReplaceWith(expression = "org.jetbrains.kotlin.load.java.JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME", imports = {}))
    public static /* synthetic */ void getJVM_FIELD_ANNOTATION_FQ_NAME$annotations() {
    }

    static {
        FqName fqName = new FqName("kotlin.jvm.JvmStatic");
        JVM_STATIC_ANNOTATION_FQ_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_STATIC_ANNOTATION_CLASS_ID = classId;
        JVM_FIELD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmField");
    }

    public static final FqName getJVM_STATIC_ANNOTATION_FQ_NAME() {
        return JVM_STATIC_ANNOTATION_FQ_NAME;
    }

    public static final ClassId getJVM_STATIC_ANNOTATION_CLASS_ID() {
        return JVM_STATIC_ANNOTATION_CLASS_ID;
    }

    public static final boolean hasJvmStaticAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(JVM_STATIC_ANNOTATION_FQ_NAME) != null;
    }

    public static final ConstantValue<?> argumentValue(AnnotationDescriptor annotationDescriptor, String parameterName) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(parameterName, "parameterName");
        ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(Name.identifier(parameterName));
        if (constantValue instanceof ErrorValue) {
            constantValue = null;
        }
        return constantValue;
    }

    public static final FqName getJVM_FIELD_ANNOTATION_FQ_NAME() {
        return JVM_FIELD_ANNOTATION_FQ_NAME;
    }
}
