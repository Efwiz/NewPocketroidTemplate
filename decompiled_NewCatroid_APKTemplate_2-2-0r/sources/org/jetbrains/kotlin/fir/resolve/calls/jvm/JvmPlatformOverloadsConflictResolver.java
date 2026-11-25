package org.jetbrains.kotlin.fir.resolve.calls.jvm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;

/* JADX INFO: compiled from: JvmPlatformOverloadsConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u00020\n*\u00020\f2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u001a\u0010\r\u001a\u00020\n*\u00020\u000e2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0014\u0010\u000f\u001a\u00020\n*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/jvm/JvmPlatformOverloadsConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "chooseMaximallySpecificCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidates", "discriminateAbstracts", "", "isShadowedByFieldCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isShadowedByPropertyCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "strictlyDerivedFrom", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "other", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformOverloadsConflictResolver extends ConeCallConflictResolver {
    private final FirSession session;

    public JvmPlatformOverloadsConflictResolver(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver
    public Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.session).supportsFeature(LanguageFeature.PreferJavaFieldOverload)) {
            return candidates;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Candidate candidate : candidates) {
            FirDeclaration fir = candidate.getSymbol().getFir();
            if (fir instanceof FirProperty) {
                if (!isShadowedByFieldCandidate((FirProperty) fir, candidates)) {
                    linkedHashSet.add(candidate);
                }
            } else if (!(fir instanceof FirField)) {
                linkedHashSet.add(candidate);
            } else if (!isShadowedByPropertyCandidate((FirField) fir, candidates)) {
                linkedHashSet.add(candidate);
            }
        }
        return linkedHashSet;
    }

    private final boolean isShadowedByFieldCandidate(FirProperty firProperty, Set<Candidate> set) {
        FirProperty firProperty2 = firProperty;
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firProperty2) || (firProperty2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firProperty2 = originalForSubstitutionOverrideAttr;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(((FirProperty) firProperty2).getSymbol());
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return false;
        }
        Iterator<Candidate> it = set.iterator();
        while (it.hasNext()) {
            FirDeclaration fir = it.next().getSymbol().getFir();
            FirField firField = fir instanceof FirField ? (FirField) fir : null;
            if (firField != null) {
                FirField firField2 = firField;
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firField2) || (firField2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firField2) : null;
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        originalForSubstitutionOverrideAttr2 = ClassMembersKt.isIntersectionOverride(firField2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firField2) : null;
                    }
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        break;
                    }
                    firField2 = originalForSubstitutionOverrideAttr2;
                }
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag(((FirField) firField2).getSymbol());
                if (coneClassLikeLookupTagContainingClassLookupTag2 != null && !strictlyDerivedFrom(coneClassLikeLookupTagContainingClassLookupTag, coneClassLikeLookupTagContainingClassLookupTag2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isShadowedByPropertyCandidate(FirField firField, Set<Candidate> set) {
        FirField firField2 = firField;
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firField2) || (firField2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firField2) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firField2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firField2) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firField2 = originalForSubstitutionOverrideAttr;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(((FirField) firField2).getSymbol());
        if (coneClassLikeLookupTagContainingClassLookupTag == null) {
            return false;
        }
        Iterator<Candidate> it = set.iterator();
        while (it.hasNext()) {
            FirDeclaration fir = it.next().getSymbol().getFir();
            FirProperty firProperty = fir instanceof FirProperty ? (FirProperty) fir : null;
            if (firProperty != null) {
                FirProperty firProperty2 = firProperty;
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firProperty2) || (firProperty2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2) : null;
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        break;
                    }
                    firProperty2 = originalForSubstitutionOverrideAttr2;
                }
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag(((FirProperty) firProperty2).getSymbol());
                if (coneClassLikeLookupTagContainingClassLookupTag2 != null && strictlyDerivedFrom(coneClassLikeLookupTagContainingClassLookupTag2, coneClassLikeLookupTagContainingClassLookupTag)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean strictlyDerivedFrom(ConeClassLikeLookupTag coneClassLikeLookupTag, ConeClassLikeLookupTag coneClassLikeLookupTag2) {
        if (Intrinsics.areEqual(coneClassLikeLookupTag, coneClassLikeLookupTag2)) {
            return false;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, this.session);
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirClass firClass = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
        if (firClass == null) {
            return false;
        }
        return SupertypeUtilsKt.isSubclassOf$default(firClass, coneClassLikeLookupTag2, this.session, true, null, false, 24, null);
    }
}
