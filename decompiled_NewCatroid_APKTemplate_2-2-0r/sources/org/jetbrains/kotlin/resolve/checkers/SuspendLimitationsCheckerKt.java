package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: SuspendLimitationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0002\u001a\u00020\u0003*\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"KOTLIN_TEST_TEST_FQNAME", "Lorg/jetbrains/kotlin/name/FqName;", "isKotlinTestAnnotation", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SuspendLimitationsCheckerKt {
    private static final FqName KOTLIN_TEST_TEST_FQNAME = new FqName("kotlin.test.Test");

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isKotlinTestAnnotation(AnnotationDescriptor annotationDescriptor) {
        FqName fqName = annotationDescriptor.getFqName();
        FqName fqName2 = KOTLIN_TEST_TEST_FQNAME;
        return Intrinsics.areEqual(fqName, fqName2) || Intrinsics.areEqual(AnnotationDescriptorKt.getAbbreviationFqName(annotationDescriptor), fqName2);
    }
}
