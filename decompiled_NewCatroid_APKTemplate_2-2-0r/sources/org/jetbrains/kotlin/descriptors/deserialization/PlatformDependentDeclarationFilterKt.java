package org.jetbrains.kotlin.descriptors.deserialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: PlatformDependentDeclarationFilter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"PLATFORM_DEPENDENT_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlatformDependentDeclarationFilterKt {
    private static final FqName PLATFORM_DEPENDENT_ANNOTATION_FQ_NAME = new FqName("kotlin.internal.PlatformDependent");

    public static final FqName getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME() {
        return PLATFORM_DEPENDENT_ANNOTATION_FQ_NAME;
    }
}
