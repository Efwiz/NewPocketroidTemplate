package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.references.FirSuperReference;

/* JADX INFO: compiled from: referenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"hadExplicitTypeInSource", "", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReferenceUtilsKt {
    public static final boolean hadExplicitTypeInSource(FirSuperReference firSuperReference) {
        Intrinsics.checkNotNullParameter(firSuperReference, "<this>");
        KtSourceElement source = firSuperReference.getSuperTypeRef().getSource();
        return !Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.SuperCallImplicitType.INSTANCE);
    }
}
