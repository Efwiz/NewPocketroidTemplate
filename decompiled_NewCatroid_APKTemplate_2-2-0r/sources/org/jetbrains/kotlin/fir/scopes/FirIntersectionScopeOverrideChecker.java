package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: FirIntersectionScopeOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000fH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirIntersectionScopeOverrideChecker;", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "platformSpecificOverridabilityRules", "Lorg/jetbrains/kotlin/fir/scopes/PlatformSpecificOverridabilityRules;", "standardOverrideChecker", "isOverriddenFunction", "", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "baseDeclaration", "isOverriddenProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIntersectionScopeOverrideChecker implements FirOverrideChecker {
    private final PlatformSpecificOverridabilityRules platformSpecificOverridabilityRules;
    private final FirOverrideChecker standardOverrideChecker;

    public FirIntersectionScopeOverrideChecker(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.standardOverrideChecker = FirOverrideCheckerKt.getFirOverrideChecker(session);
        this.platformSpecificOverridabilityRules = PlatformSpecificOverridabilityRulesKt.getPlatformSpecificOverridabilityRules(session);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration) {
        Boolean boolIsOverriddenFunction;
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        PlatformSpecificOverridabilityRules platformSpecificOverridabilityRules = this.platformSpecificOverridabilityRules;
        return (platformSpecificOverridabilityRules == null || (boolIsOverriddenFunction = platformSpecificOverridabilityRules.isOverriddenFunction(overrideCandidate, baseDeclaration)) == null) ? this.standardOverrideChecker.isOverriddenFunction(overrideCandidate, baseDeclaration) : boolIsOverriddenFunction.booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration) {
        Boolean boolIsOverriddenProperty;
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        PlatformSpecificOverridabilityRules platformSpecificOverridabilityRules = this.platformSpecificOverridabilityRules;
        return (platformSpecificOverridabilityRules == null || (boolIsOverriddenProperty = platformSpecificOverridabilityRules.isOverriddenProperty(overrideCandidate, baseDeclaration)) == null) ? this.standardOverrideChecker.isOverriddenProperty(overrideCandidate, baseDeclaration) : boolIsOverriddenProperty.booleanValue();
    }
}
