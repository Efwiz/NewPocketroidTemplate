package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: OptionalAnnotationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/OptionalAnnotationUtil;", "", "()V", "OPTIONAL_EXPECTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getOPTIONAL_EXPECTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "isOptionalAnnotationClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "shouldGenerateExpectClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptionalAnnotationUtil {
    public static final OptionalAnnotationUtil INSTANCE = new OptionalAnnotationUtil();
    private static final FqName OPTIONAL_EXPECTATION_FQ_NAME = new FqName("kotlin.OptionalExpectation");

    private OptionalAnnotationUtil() {
    }

    public final FqName getOPTIONAL_EXPECTATION_FQ_NAME() {
        return OPTIONAL_EXPECTATION_FQ_NAME;
    }

    @JvmStatic
    public static final boolean shouldGenerateExpectClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        descriptor.get$isExpect();
        ClassDescriptor classDescriptor = descriptor;
        if (isOptionalAnnotationClass(classDescriptor)) {
            return ExpectedActualResolverKt.findCompatibleActualsForExpected$default(descriptor, DescriptorUtilsKt.getModule(classDescriptor), null, 2, null).isEmpty();
        }
        return false;
    }

    @JvmStatic
    public static final boolean isOptionalAnnotationClass(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS && classDescriptor.get$isExpect() && descriptor.getAnnotations().hasAnnotation(OPTIONAL_EXPECTATION_FQ_NAME)) {
                return true;
            }
        }
        return false;
    }
}
