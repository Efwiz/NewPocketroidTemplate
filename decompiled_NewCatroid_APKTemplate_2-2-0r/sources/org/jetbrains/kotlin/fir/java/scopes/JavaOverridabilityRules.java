package org.jetbrains.kotlin.fir.java.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.scopes.PlatformSpecificOverridabilityRules;

/* JADX INFO: compiled from: JavaOverridabilityRules.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J\f\u0010\u0011\u001a\u00020\b*\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverridabilityRules;", "Lorg/jetbrains/kotlin/fir/scopes/PlatformSpecificOverridabilityRules;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "javaOverrideChecker", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverrideChecker;", "isOverriddenFunction", "", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "baseDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Ljava/lang/Boolean;", "isOverriddenProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;", "isFromJava", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaOverridabilityRules implements PlatformSpecificOverridabilityRules {
    private final JavaOverrideChecker javaOverrideChecker;

    public JavaOverridabilityRules(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.javaOverrideChecker = new JavaOverrideChecker(session, JavaTypeParameterStack.INSTANCE.getEMPTY(), null, true);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.PlatformSpecificOverridabilityRules
    public Boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (isFromJava(overrideCandidate) && isFromJava(baseDeclaration)) {
            return Boolean.valueOf(this.javaOverrideChecker.isOverriddenFunction(overrideCandidate, baseDeclaration));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.PlatformSpecificOverridabilityRules
    public Boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (isFromJava(overrideCandidate) && isFromJava(baseDeclaration)) {
            return Boolean.valueOf(this.javaOverrideChecker.isOverriddenProperty(overrideCandidate, baseDeclaration));
        }
        return null;
    }

    private final boolean isFromJava(FirCallableDeclaration firCallableDeclaration) {
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                return Intrinsics.areEqual(firCallableDeclaration.getOrigin(), FirDeclarationOrigin.Enhancement.INSTANCE);
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
    }
}
