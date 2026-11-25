package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0082\u0004¨\u0006\u0004"}, d2 = {"isEqualsTo", "", "Lorg/jetbrains/kotlin/fir/FirElement;", "other", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DfaVariablesKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isEqualsTo(FirElement firElement, FirElement firElement2) {
        if (!(firElement instanceof FirResolvedQualifier) || !(firElement2 instanceof FirResolvedQualifier)) {
            return Intrinsics.areEqual(firElement, firElement2);
        }
        FirResolvedQualifier firResolvedQualifier = (FirResolvedQualifier) firElement;
        FirResolvedQualifier firResolvedQualifier2 = (FirResolvedQualifier) firElement2;
        return Intrinsics.areEqual(firResolvedQualifier.getPackageFqName(), firResolvedQualifier2.getPackageFqName()) && Intrinsics.areEqual(firResolvedQualifier.getClassId(), firResolvedQualifier2.getClassId());
    }
}
