package org.jetbrains.kotlin.fir.scopes;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeRawScopeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirKotlinScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0002\u001a4\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u001aP\u0010\u0017\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00192\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002\u001a.\u0010\u001c\u001a\u0004\u0018\u00010\u000f*\u00020\u001d2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u00102\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u001a,\u0010\u001f\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00192\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u001a0\u0010\u001f\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030!2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00192\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\""}, d2 = {"kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getKotlinScopeProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "kotlinScopeProvider$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "useSiteSession", "scopeForClass", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "memberOwnerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "scopeForClassImpl", "skipPrivateMembers", "", "classFirDispatchReceiver", "isFromExpectClass", "scopeForSupertype", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "derivedClass", "unsubstitutedScope", "withForcedTypeCalculator", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirKotlinScopeProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirKotlinScopeProviderKt.class, "providers"), "kotlinScopeProvider", "getKotlinScopeProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;"))};
    private static final ArrayMapAccessor kotlinScopeProvider$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirKotlinScopeProvider.class), (Object) null, 2, (Object) null);

    public static final FirTypeScope unsubstitutedScope(FirClass firClass, FirSession useSiteSession, ScopeSession scopeSession, boolean z, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirTypeScope useSiteMemberScope = firClass.getScopeProvider().getUseSiteMemberScope(firClass, useSiteSession, scopeSession, firResolvePhase);
        return z ? new FirScopeWithFakeOverrideTypeCalculator(useSiteMemberScope, FakeOverrideTypeCalculator.Forced.INSTANCE) : useSiteMemberScope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirTypeScope unsubstitutedScope(FirClassSymbol<?> firClassSymbol, FirSession useSiteSession, ScopeSession scopeSession, boolean z, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return unsubstitutedScope((FirClass) firClassSymbol.getFir(), useSiteSession, scopeSession, z, firResolvePhase);
    }

    public static final FirTypeScope scopeForClass(FirClass firClass, ConeSubstitutor substitutor, FirSession useSiteSession, ScopeSession scopeSession, ConeClassLikeLookupTag memberOwnerLookupTag, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(memberOwnerLookupTag, "memberOwnerLookupTag");
        return scopeForClassImpl(firClass, substitutor, useSiteSession, scopeSession, false, firClass, false, memberOwnerLookupTag, firResolvePhase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirTypeScope scopeForSupertype(ConeKotlinType coneKotlinType, FirSession useSiteSession, ScopeSession scopeSession, FirClass derivedClass, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(derivedClass, "derivedClass");
        if (!(coneKotlinType instanceof ConeClassLikeType) || (coneKotlinType instanceof ConeErrorType)) {
            return null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneKotlinType;
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), useSiteSession);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null) {
            return null;
        }
        ConeSubstitutor coneRawScopeSubstitutor = coneKotlinType.getType().getAttributes().contains(CompilerConeAttributes.RawType.INSTANCE) ? new ConeRawScopeSubstitutor(useSiteSession) : substitutor(firRegularClassSymbol, coneClassLikeType, useSiteSession);
        FirClass firClass = (FirClass) firRegularClassSymbol.getFir();
        FirRegularClass firRegularClass = derivedClass instanceof FirRegularClass ? (FirRegularClass) derivedClass : null;
        boolean z = false;
        if (firRegularClass != null && firRegularClass.getStatus().isExpect()) {
            z = true;
        }
        return scopeForClassImpl(firClass, coneRawScopeSubstitutor, useSiteSession, scopeSession, true, derivedClass, z, derivedClass.getSymbol().getLookupTag(), firResolvePhase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final ConeSubstitutor substitutor(FirRegularClassSymbol firRegularClassSymbol, ConeClassLikeType coneClassLikeType, FirSession firSession) {
        return coneClassLikeType.getTypeArguments().length == 0 ? ConeSubstitutor.Empty.INSTANCE : SubstitutorsKt.substitutorByMap(SupertypeUtilsKt.createSubstitutionForScope(((FirRegularClass) firRegularClassSymbol.getFir()).getTypeParameters(), coneClassLikeType, firSession), firSession);
    }

    private static final FirTypeScope scopeForClassImpl(FirClass firClass, ConeSubstitutor coneSubstitutor, FirSession firSession, ScopeSession scopeSession, boolean z, FirClass firClass2, boolean z2, ConeClassLikeLookupTag coneClassLikeLookupTag, FirResolvePhase firResolvePhase) {
        FirDeclarationOrigin.SubstitutionOverride.CallSite callSite;
        ConeClassLikeLookupTag lookupTag = coneClassLikeLookupTag;
        FirTypeScope firTypeScopeUnsubstitutedScope = unsubstitutedScope(firClass, firSession, scopeSession, false, firResolvePhase);
        if (Intrinsics.areEqual(coneSubstitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return firTypeScopeUnsubstitutedScope;
        }
        ConeSubstitutionScopeKey coneSubstitutionScopeKey = new ConeSubstitutionScopeKey(firClass2.getSymbol().getLookupTag(), z2, coneSubstitutor, lookupTag);
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(firClass);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(firClass, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        ConeSubstitutionScopeKey coneSubstitutionScopeKey2 = coneSubstitutionScopeKey;
        Object obj = map2.get(coneSubstitutionScopeKey2);
        if (obj == null) {
            ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneSubstitutor.substituteOrSelf(ScopeUtilsKt.defaultType(firClass2)));
            Intrinsics.checkNotNull(coneSimpleKotlinTypeLowerBoundIfFlexible, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible;
            if (lookupTag == null) {
                lookupTag = firClass2.getSymbol().getLookupTag();
            }
            ConeClassLikeLookupTag coneClassLikeLookupTag2 = lookupTag;
            if (!Intrinsics.areEqual(firClass2, firClass)) {
                callSite = FirDeclarationOrigin.SubstitutionOverride.DeclarationSite.INSTANCE;
            } else {
                callSite = FirDeclarationOrigin.SubstitutionOverride.CallSite.INSTANCE;
            }
            FirClassSubstitutionScope firClassSubstitutionScope = new FirClassSubstitutionScope(firSession, firTypeScopeUnsubstitutedScope, coneSubstitutionScopeKey2, coneSubstitutor, coneClassLikeType, z, z2, coneClassLikeLookupTag2, callSite);
            map2.put(coneSubstitutionScopeKey2, firClassSubstitutionScope);
            obj = firClassSubstitutionScope;
        }
        return (FirClassSubstitutionScope) obj;
    }

    public static final FirKotlinScopeProvider getKotlinScopeProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirKotlinScopeProvider) kotlinScopeProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
