package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;

/* JADX INFO: compiled from: FirAbstractOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H$¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractOverrideChecker;", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "()V", "buildTypeParametersSubstitutorIfCompatible", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "baseDeclaration", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractOverrideChecker implements FirOverrideChecker {
    protected abstract ConeSubstitutor buildTypeParametersSubstitutorIfCompatible(FirCallableDeclaration overrideCandidate, FirCallableDeclaration baseDeclaration);
}
