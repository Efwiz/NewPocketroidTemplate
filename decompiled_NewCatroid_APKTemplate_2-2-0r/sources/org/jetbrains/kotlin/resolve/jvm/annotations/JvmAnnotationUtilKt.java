package org.jetbrains.kotlin.resolve.jvm.annotations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.util.DeclarationUtilKt;

/* JADX INFO: compiled from: jvmAnnotationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018\u001a\f\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u001b\u001a\f\u0010\u001c\u001a\u0004\u0018\u00010\u001a*\u00020\u001b\u001a\u000e\u0010\u001d\u001a\u0004\u0018\u00010\u001a*\u00020\u001eH\u0002\u001a\f\u0010\u001f\u001a\u0004\u0018\u00010\u001a*\u00020\u001b\u001a\f\u0010 \u001a\u0004\u0018\u00010\u001a*\u00020\u001b\u001a\n\u0010!\u001a\u00020\u0015*\u00020\u0016\u001a\n\u0010\"\u001a\u00020\u0015*\u00020\u001b\u001a\n\u0010#\u001a\u00020\u0015*\u00020\u001b\u001a\n\u0010$\u001a\u00020\u0015*\u00020\u001b\u001a\n\u0010%\u001a\u00020\u0015*\u00020\u001b\u001a\n\u0010&\u001a\u00020\u0015*\u00020\u0016\u001a\u0012\u0010'\u001a\u00020\u0015*\u00020\u001b2\u0006\u0010(\u001a\u00020\u0018\u001a\u0012\u0010)\u001a\u00020\u0015*\u00020\u00162\u0006\u0010(\u001a\u00020\u0018\u001a\n\u0010*\u001a\u00020\u0015*\u00020+\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0011\u0010\b\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0003\"\u0010\u0010\f\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\r\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000e\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000f\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0010\u001a\u00020\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0012\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0013\u001a\u00020\u00018\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"JVM_DEFAULT_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_DEFAULT_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME", "getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME", "JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME", "getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME", "JVM_OVERLOADS_FQ_NAME", "getJVM_OVERLOADS_FQ_NAME", "JVM_RECORD_ANNOTATION_FQ_NAME", "getJVM_RECORD_ANNOTATION_FQ_NAME", "JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME", "JVM_SYNTHETIC_ANNOTATION_FQ_NAME", "STRICTFP_ANNOTATION_FQ_NAME", "SYNCHRONIZED_ANNOTATION_FQ_NAME", "TRANSIENT_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "TRANSIENT_ANNOTATION_FQ_NAME", "VOLATILE_ANNOTATION_FQ_NAME", "checkIsImplementationCompiledToJvmDefault", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "findJvmFieldAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "findJvmOverloadsAnnotation", "findJvmSyntheticAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "findStrictfpAnnotation", "findSynchronizedAnnotation", "hasJvmDefaultAnnotation", "hasJvmDefaultNoCompatibilityAnnotation", "hasJvmDefaultWithCompatibilityAnnotation", "hasJvmFieldAnnotation", "hasJvmSyntheticAnnotation", "hasPlatformDependentAnnotation", "isCallableMemberCompiledToJvmDefault", "jvmDefault", "isCompiledToJvmDefault", "isJvmRecord", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmAnnotationUtilKt {
    public static final FqName JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME;
    public static final ClassId TRANSIENT_ANNOTATION_CLASS_ID;
    public static final FqName TRANSIENT_ANNOTATION_FQ_NAME;
    private static final FqName JVM_DEFAULT_FQ_NAME = new FqName("kotlin.jvm.JvmDefault");
    private static final FqName JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME = new FqName("kotlin.jvm.JvmDefaultWithoutCompatibility");
    private static final FqName JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME = new FqName("kotlin.jvm.JvmDefaultWithCompatibility");
    private static final FqName JVM_OVERLOADS_FQ_NAME = new FqName("kotlin.jvm.JvmOverloads");
    public static final FqName JVM_SYNTHETIC_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmSynthetic");
    private static final FqName JVM_RECORD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmRecord");
    public static final FqName SYNCHRONIZED_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.Synchronized");
    public static final FqName STRICTFP_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.Strictfp");
    public static final FqName VOLATILE_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.Volatile");

    static {
        FqName fqName = new FqName("kotlin.jvm.Transient");
        TRANSIENT_ANNOTATION_FQ_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        TRANSIENT_ANNOTATION_CLASS_ID = classId;
        JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmSerializableLambda");
    }

    public static final FqName getJVM_DEFAULT_FQ_NAME() {
        return JVM_DEFAULT_FQ_NAME;
    }

    public static final FqName getJVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME() {
        return JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME;
    }

    public static final FqName getJVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME() {
        return JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME;
    }

    public static final FqName getJVM_OVERLOADS_FQ_NAME() {
        return JVM_OVERLOADS_FQ_NAME;
    }

    public static final FqName getJVM_RECORD_ANNOTATION_FQ_NAME() {
        return JVM_RECORD_ANNOTATION_FQ_NAME;
    }

    public static final AnnotationDescriptor findJvmOverloadsAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(JVM_OVERLOADS_FQ_NAME);
    }

    public static final AnnotationDescriptor findJvmFieldAnnotation(DeclarationDescriptor declarationDescriptor) {
        FieldDescriptor backingField;
        Annotations annotations;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        PropertyDescriptor propertyDescriptor = declarationDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptor : null;
        if (propertyDescriptor == null || (backingField = propertyDescriptor.getBackingField()) == null || (annotations = backingField.getAnnotations()) == null) {
            return null;
        }
        return annotations.mo6752findAnnotation(JvmAbi.JVM_FIELD_ANNOTATION_FQ_NAME);
    }

    public static final boolean hasJvmFieldAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return findJvmFieldAnnotation(declarationDescriptor) != null;
    }

    public static final boolean isCallableMemberCompiledToJvmDefault(DeclarationDescriptor declarationDescriptor, JvmDefaultMode jvmDefault) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefault, "jvmDefault");
        return (declarationDescriptor instanceof CallableMemberDescriptor) && isCompiledToJvmDefault((CallableMemberDescriptor) declarationDescriptor, jvmDefault);
    }

    public static final boolean isCompiledToJvmDefault(CallableMemberDescriptor callableMemberDescriptor, JvmDefaultMode jvmDefault) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefault, "jvmDefault");
        CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableMemberDescriptor);
        Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
        DeclarationDescriptor containingDeclaration = directMember.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (directMember.getAnnotations().hasAnnotation(JVM_DEFAULT_FQ_NAME)) {
            return true;
        }
        return !(containingDeclaration instanceof DeserializedClassDescriptor) ? jvmDefault.getForAllMethodsWithBody() : JvmProtoBufUtil.isNewPlaceForBodyGeneration(((DeserializedClassDescriptor) containingDeclaration).getClassProto());
    }

    public static final boolean checkIsImplementationCompiledToJvmDefault(CallableMemberDescriptor callableMemberDescriptor, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        CallableMemberDescriptor callableMemberDescriptorFindImplementationFromInterface = callableMemberDescriptor.getKind().isReal() ? callableMemberDescriptor : DeclarationUtilKt.findImplementationFromInterface(callableMemberDescriptor);
        if (callableMemberDescriptorFindImplementationFromInterface == null) {
            throw new IllegalStateException(("Can't find actual implementation for " + callableMemberDescriptor).toString());
        }
        return isCallableMemberCompiledToJvmDefault(callableMemberDescriptorFindImplementationFromInterface, jvmDefaultMode);
    }

    public static final boolean hasJvmDefaultAnnotation(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return DescriptorUtils.getDirectMember(callableMemberDescriptor).getAnnotations().hasAnnotation(JVM_DEFAULT_FQ_NAME);
    }

    public static final boolean hasJvmDefaultNoCompatibilityAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().hasAnnotation(JVM_DEFAULT_NO_COMPATIBILITY_FQ_NAME);
    }

    public static final boolean hasJvmDefaultWithCompatibilityAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().hasAnnotation(JVM_DEFAULT_WITH_COMPATIBILITY_FQ_NAME);
    }

    public static final boolean hasPlatformDependentAnnotation(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return DescriptorUtils.getDirectMember(callableMemberDescriptor).getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
    }

    private static final AnnotationDescriptor findJvmSyntheticAnnotation(Annotated annotated) {
        FieldDescriptor backingField;
        Annotations annotations;
        Annotations annotations2 = annotated.getAnnotations();
        FqName fqName = JVM_SYNTHETIC_ANNOTATION_FQ_NAME;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = annotations2.mo6752findAnnotation(fqName);
        if (annotationDescriptorMo6752findAnnotation != null) {
            return annotationDescriptorMo6752findAnnotation;
        }
        PropertyDescriptor propertyDescriptor = annotated instanceof PropertyDescriptor ? (PropertyDescriptor) annotated : null;
        if (propertyDescriptor == null || (backingField = propertyDescriptor.getBackingField()) == null || (annotations = backingField.getAnnotations()) == null) {
            return null;
        }
        return annotations.mo6752findAnnotation(fqName);
    }

    public static final boolean hasJvmSyntheticAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return findJvmSyntheticAnnotation(declarationDescriptor) != null;
    }

    public static final AnnotationDescriptor findStrictfpAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(STRICTFP_ANNOTATION_FQ_NAME);
    }

    public static final AnnotationDescriptor findSynchronizedAnnotation(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getAnnotations().mo6752findAnnotation(SYNCHRONIZED_ANNOTATION_FQ_NAME);
    }

    public static final boolean isJvmRecord(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return classDescriptor.getAnnotations().hasAnnotation(JVM_RECORD_ANNOTATION_FQ_NAME);
    }
}
