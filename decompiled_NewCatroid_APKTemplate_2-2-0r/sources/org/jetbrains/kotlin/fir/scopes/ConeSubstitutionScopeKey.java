package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirKotlinScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B'\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000bJ\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\tHÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005HÆ\u0003J3\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/ConeSubstitutionScopeKey;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassSubstitutionScope;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "isFromExpectClass", "", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "derivedClassLookupTag", "(Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;ZLorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V", "getDerivedClassLookupTag", "()Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "()Z", "getLookupTag", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeSubstitutionScopeKey extends ScopeSessionKey<FirClass, FirClassSubstitutionScope> {
    private final ConeClassLikeLookupTag derivedClassLookupTag;
    private final boolean isFromExpectClass;
    private final ConeClassLikeLookupTag lookupTag;
    private final ConeSubstitutor substitutor;

    public static /* synthetic */ ConeSubstitutionScopeKey copy$default(ConeSubstitutionScopeKey coneSubstitutionScopeKey, ConeClassLikeLookupTag coneClassLikeLookupTag, boolean z, ConeSubstitutor coneSubstitutor, ConeClassLikeLookupTag coneClassLikeLookupTag2, int i, Object obj) {
        if ((i & 1) != 0) {
            coneClassLikeLookupTag = coneSubstitutionScopeKey.lookupTag;
        }
        if ((i & 2) != 0) {
            z = coneSubstitutionScopeKey.isFromExpectClass;
        }
        if ((i & 4) != 0) {
            coneSubstitutor = coneSubstitutionScopeKey.substitutor;
        }
        if ((i & 8) != 0) {
            coneClassLikeLookupTag2 = coneSubstitutionScopeKey.derivedClassLookupTag;
        }
        return coneSubstitutionScopeKey.copy(coneClassLikeLookupTag, z, coneSubstitutor, coneClassLikeLookupTag2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeClassLikeLookupTag getLookupTag() {
        return this.lookupTag;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsFromExpectClass() {
        return this.isFromExpectClass;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ConeClassLikeLookupTag getDerivedClassLookupTag() {
        return this.derivedClassLookupTag;
    }

    public final ConeSubstitutionScopeKey copy(ConeClassLikeLookupTag lookupTag, boolean isFromExpectClass, ConeSubstitutor substitutor, ConeClassLikeLookupTag derivedClassLookupTag) {
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return new ConeSubstitutionScopeKey(lookupTag, isFromExpectClass, substitutor, derivedClassLookupTag);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeSubstitutionScopeKey)) {
            return false;
        }
        ConeSubstitutionScopeKey coneSubstitutionScopeKey = (ConeSubstitutionScopeKey) other;
        return Intrinsics.areEqual(this.lookupTag, coneSubstitutionScopeKey.lookupTag) && this.isFromExpectClass == coneSubstitutionScopeKey.isFromExpectClass && Intrinsics.areEqual(this.substitutor, coneSubstitutionScopeKey.substitutor) && Intrinsics.areEqual(this.derivedClassLookupTag, coneSubstitutionScopeKey.derivedClassLookupTag);
    }

    public int hashCode() {
        int iHashCode = ((((this.lookupTag.hashCode() * 31) + Boolean.hashCode(this.isFromExpectClass)) * 31) + this.substitutor.hashCode()) * 31;
        ConeClassLikeLookupTag coneClassLikeLookupTag = this.derivedClassLookupTag;
        return iHashCode + (coneClassLikeLookupTag == null ? 0 : coneClassLikeLookupTag.hashCode());
    }

    public String toString() {
        return "ConeSubstitutionScopeKey(lookupTag=" + this.lookupTag + ", isFromExpectClass=" + this.isFromExpectClass + ", substitutor=" + this.substitutor + ", derivedClassLookupTag=" + this.derivedClassLookupTag + ')';
    }

    public final ConeClassLikeLookupTag getLookupTag() {
        return this.lookupTag;
    }

    public final boolean isFromExpectClass() {
        return this.isFromExpectClass;
    }

    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final ConeClassLikeLookupTag getDerivedClassLookupTag() {
        return this.derivedClassLookupTag;
    }

    public ConeSubstitutionScopeKey(ConeClassLikeLookupTag lookupTag, boolean z, ConeSubstitutor substitutor, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.lookupTag = lookupTag;
        this.isFromExpectClass = z;
        this.substitutor = substitutor;
        this.derivedClassLookupTag = coneClassLikeLookupTag;
    }
}
