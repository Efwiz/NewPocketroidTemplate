package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;

/* JADX INFO: compiled from: FirNestedClassifierScopeWithSubstitution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"wrapNestedClassifierScopeWithSubstitutionForSuperType", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "superType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirNestedClassifierScopeWithSubstitutionKt {
    public static final FirContainingNamesAwareScope wrapNestedClassifierScopeWithSubstitutionForSuperType(FirContainingNamesAwareScope firContainingNamesAwareScope, ConeClassLikeType superType, FirSession session) {
        Intrinsics.checkNotNullParameter(firContainingNamesAwareScope, "<this>");
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(session, "session");
        return new FirNestedClassifierScopeWithSubstitution(firContainingNamesAwareScope, SupertypeUtilsKt.createSubstitutionForSupertype(superType, session));
    }
}
