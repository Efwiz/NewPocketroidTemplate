package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExpectActualCompatibility.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0005\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004ò\u0001\b\n\u0006\u0012\u0002\b\u00030\u0006¨\u0006\u0007"}, d2 = {"compatible", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "getCompatible", "(Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;)Z", "isIncompatible", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectActualCompatibilityKt {
    public static final boolean getCompatible(ExpectActualCompatibility<?> expectActualCompatibility) {
        Intrinsics.checkNotNullParameter(expectActualCompatibility, "<this>");
        return Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Compatible.INSTANCE);
    }

    public static final boolean isIncompatible(ExpectActualCompatibility<?> expectActualCompatibility) {
        Intrinsics.checkNotNullParameter(expectActualCompatibility, "<this>");
        return !getCompatible(expectActualCompatibility);
    }
}
