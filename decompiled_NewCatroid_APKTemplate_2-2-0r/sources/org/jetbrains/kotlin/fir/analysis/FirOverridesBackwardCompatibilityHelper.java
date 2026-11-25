package org.jetbrains.kotlin.fir.analysis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirOverridesBackwardCompatibilityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014¢\u0006\u0002\u0010\tJ.\u0010\n\u001a\u00020\u00062\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0010\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\"\u0010\u0011\u001a\u00020\u00062\u0010\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "platformDependentAnnotation", "Lorg/jetbrains/kotlin/name/ClassId;", "additionalCheck", "", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;", "isPlatformSpecificSymbolThatCanBeImplicitlyOverridden", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "visitedSymbols", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "overrideCanBeOmitted", "overriddenMemberSymbols", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirOverridesBackwardCompatibilityHelper implements FirSessionComponent {
    private final ClassId platformDependentAnnotation;

    protected Boolean additionalCheck(FirCallableDeclaration member) {
        Intrinsics.checkNotNullParameter(member, "member");
        return null;
    }

    public FirOverridesBackwardCompatibilityHelper() {
        ClassId classIdFromString = ClassId.fromString("kotlin/internal/PlatformDependent");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        this.platformDependentAnnotation = classIdFromString;
    }

    public boolean overrideCanBeOmitted(List<? extends FirCallableSymbol<?>> overriddenMemberSymbols, CheckerContext context) {
        Intrinsics.checkNotNullParameter(overriddenMemberSymbols, "overriddenMemberSymbols");
        Intrinsics.checkNotNullParameter(context, "context");
        HashSet hashSet = new HashSet();
        List<? extends FirCallableSymbol<?>> list = overriddenMemberSymbols;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!isPlatformSpecificSymbolThatCanBeImplicitlyOverridden((FirCallableSymbol) it.next(), hashSet, context)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isPlatformSpecificSymbolThatCanBeImplicitlyOverridden(FirCallableSymbol<?> symbol, Set<FirCallableSymbol<?>> visitedSymbols, CheckerContext context) {
        FirRegularClassSymbol regularClassSymbol;
        FirTypeScope firTypeScopeUnsubstitutedScope;
        List directOverriddenProperties$default;
        Modality modality = symbol.getResolvedStatus().getModality();
        if (modality == null || modality == Modality.FINAL) {
            return false;
        }
        if (!visitedSymbols.add(symbol)) {
            return true;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) symbol.getFir();
        while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration.getSymbol();
        if (symbol2 != null) {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol2, FirResolvePhase.BODY_RESOLVE);
            FirCallableDeclaration fir = symbol2.getFir();
            if (FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) fir, this.platformDependentAnnotation, context.getSession())) {
                return true;
            }
            Boolean boolAdditionalCheck = additionalCheck(fir);
            if (boolAdditionalCheck != null) {
                return boolAdditionalCheck.booleanValue();
            }
            if (fir.getStatus().getModality() != Modality.ABSTRACT) {
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(fir);
                FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
                if (firRegularClassSymbol != null && firRegularClassSymbol.getClassKind() != ClassKind.INTERFACE) {
                    return false;
                }
            }
            ConeClassLikeType coneClassLikeTypeDispatchReceiverClassTypeOrNull = ClassMembersKt.dispatchReceiverClassTypeOrNull(symbol);
            if (coneClassLikeTypeDispatchReceiverClassTypeOrNull == null || (regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneClassLikeTypeDispatchReceiverClassTypeOrNull, context.getSession())) == null || (firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(regularClassSymbol, context)) == null) {
                return false;
            }
            if (fir instanceof FirSimpleFunction) {
                directOverriddenProperties$default = FirTypeScopeKt.getDirectOverriddenFunctions$default(firTypeScopeUnsubstitutedScope, ((FirSimpleFunction) fir).getSymbol(), false, 2, null);
            } else {
                if (!(fir instanceof FirProperty)) {
                    return false;
                }
                directOverriddenProperties$default = FirTypeScopeKt.getDirectOverriddenProperties$default(firTypeScopeUnsubstitutedScope, ((FirProperty) fir).getSymbol(), false, 2, null);
            }
            if (directOverriddenProperties$default.isEmpty()) {
                return false;
            }
            List list = directOverriddenProperties$default;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (!isPlatformSpecificSymbolThatCanBeImplicitlyOverridden((FirCallableSymbol) it.next(), visitedSymbols, context)) {
                        return false;
                    }
                }
            }
            return true;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
    }
}
