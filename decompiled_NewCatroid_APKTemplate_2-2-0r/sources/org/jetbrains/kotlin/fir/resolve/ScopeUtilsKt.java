package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeRawScopeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirIntersectionScopeOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirUnstableSmartcastTypeScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDynamicScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirScopeWithFakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ScopeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0006\u001a\u0004\u0018\u00010\u0003*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\n\u0010\u0010\u001a\u00020\u0007*\u00020\u0011\u001a\u000e\u0010\u0010\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\u0012\u001a\u0018\u0010\u0010\u001a\u00020\u0007*\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015\u001a$\u0010\u0016\u001a\u0004\u0018\u00010\u0003*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000f\u001a(\u0010\u0018\u001a\u0004\u0018\u00010\u0003*\u00020\u00192\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002\u001a.\u0010\u0018\u001a\u0004\u0018\u00010\u0003*\u00020\u00192\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\f\u001a\u0004\u0018\u00010\r\u001a(\u0010\u001c\u001a\u0004\u0018\u00010\u0003*\u00020\u001d2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\"\u001d\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u001e"}, d2 = {"TYPE_PARAMETER_SCOPE_KEY", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getTYPE_PARAMETER_SCOPE_KEY", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "classScope", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "requiredMembersPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "memberOwnerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "defaultType", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/name/ClassId;", "parameters", "", "delegatingConstructorScope", "derivedClassLookupTag", "scope", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "fakeOverrideTypeCalculator", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideTypeCalculator;", "smartcastScope", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopeUtilsKt {
    private static final ScopeSessionKey<FirTypeParameterSymbol, FirTypeScope> TYPE_PARAMETER_SCOPE_KEY = new ScopeSessionKey<FirTypeParameterSymbol, FirTypeScope>() { // from class: org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt$special$$inlined$scopeSessionKey$1
    };

    public static /* synthetic */ FirTypeScope smartcastScope$default(FirSmartCastExpression firSmartCastExpression, FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase, int i, Object obj) {
        if ((i & 4) != 0) {
            firResolvePhase = null;
        }
        return smartcastScope(firSmartCastExpression, firSession, scopeSession, firResolvePhase);
    }

    public static final FirTypeScope smartcastScope(FirSmartCastExpression firSmartCastExpression, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
        ConeKotlinType coneType;
        FirTypeScope firTypeScopeScope;
        Intrinsics.checkNotNullParameter(firSmartCastExpression, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirTypeRef smartcastTypeWithoutNullableNothing = firSmartCastExpression.getSmartcastTypeWithoutNullableNothing();
        if (smartcastTypeWithoutNullableNothing == null || (coneType = FirTypeUtilsKt.getConeType(smartcastTypeWithoutNullableNothing)) == null) {
            coneType = FirTypeUtilsKt.getConeType(firSmartCastExpression.getSmartcastType());
        }
        FirTypeScope firTypeScopeScope2 = scope(coneType, useSiteSession, scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, firResolvePhase);
        if (firSmartCastExpression.isStable() || (firTypeScopeScope = scope(FirTypeUtilsKt.getConeType(firSmartCastExpression.getOriginalExpression().getTypeRef()), useSiteSession, scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, firResolvePhase)) == null) {
            return firTypeScopeScope2;
        }
        return firTypeScopeScope2 == null ? firTypeScopeScope : new FirUnstableSmartcastTypeScope(firTypeScopeScope2, firTypeScopeScope);
    }

    public static final FirTypeScope delegatingConstructorScope(ConeClassLikeType coneClassLikeType, FirSession useSiteSession, ScopeSession scopeSession, ConeClassLikeLookupTag derivedClassLookupTag) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        return classScope(coneClassLikeType, useSiteSession, scopeSession, FirResolvePhase.INSTANCE.getDECLARATIONS(), derivedClassLookupTag);
    }

    public static final FirTypeScope scope(ConeKotlinType coneKotlinType, FirSession useSiteSession, ScopeSession scopeSession, FakeOverrideTypeCalculator fakeOverrideTypeCalculator, FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(fakeOverrideTypeCalculator, "fakeOverrideTypeCalculator");
        FirTypeScope firTypeScopeScope = scope(coneKotlinType, useSiteSession, scopeSession, firResolvePhase);
        if (firTypeScopeScope == null) {
            return null;
        }
        return Intrinsics.areEqual(fakeOverrideTypeCalculator, FakeOverrideTypeCalculator.DoNothing.INSTANCE) ? firTypeScopeScope : new FirScopeWithFakeOverrideTypeCalculator(firTypeScopeScope, fakeOverrideTypeCalculator);
    }

    private static final FirTypeScope scope(ConeKotlinType coneKotlinType, FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
        if (coneKotlinType instanceof ConeErrorType) {
            return null;
        }
        if (coneKotlinType instanceof ConeClassLikeType) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneKotlinType;
            return classScope(coneClassLikeType, firSession, scopeSession, firResolvePhase, coneClassLikeType.getLookupTag());
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            FirTypeParameterSymbol symbol = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getSymbol();
            ScopeSessionKey<FirTypeParameterSymbol, FirTypeScope> scopeSessionKey = TYPE_PARAMETER_SCOPE_KEY;
            HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
            HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
            if (map == null) {
                map = new HashMap<>();
                mapScopes.put(symbol, map);
            }
            HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
            Object obj = map2.get(scopeSessionKey);
            if (obj == null) {
                ConeTypeIntersector coneTypeIntersector = ConeTypeIntersector.INSTANCE;
                ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
                List<FirResolvedTypeRef> resolvedBounds = symbol.getResolvedBounds();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
                Iterator<T> it = resolvedBounds.iterator();
                while (it.hasNext()) {
                    arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
                }
                FirTypeScope.Empty emptyScope = scope(coneTypeIntersector.intersectTypes(typeContext, arrayList), firSession, scopeSession, firResolvePhase);
                if (emptyScope == null) {
                    emptyScope = FirTypeScope.Empty.INSTANCE;
                }
                obj = emptyScope;
                map2.put(scopeSessionKey, obj);
            }
            if (obj != null) {
                return (FirTypeScope) obj;
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.scopes.FirTypeScope");
        }
        if (coneKotlinType instanceof ConeRawType) {
            return scope(((ConeRawType) coneKotlinType).getLowerBound(), firSession, scopeSession, firResolvePhase);
        }
        if (coneKotlinType instanceof ConeDynamicType) {
            return FirDynamicScopeKt.getDynamicMembersStorage(firSession).getDynamicScopeFor(scopeSession);
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return scope(((ConeFlexibleType) coneKotlinType).getLowerBound(), firSession, scopeSession, firResolvePhase);
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            FirTypeIntersectionScope.Companion companion = FirTypeIntersectionScope.INSTANCE;
            FirIntersectionScopeOverrideChecker firIntersectionScopeOverrideChecker = new FirIntersectionScopeOverrideChecker(firSession);
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it2 = intersectedTypes.iterator();
            while (it2.hasNext()) {
                FirTypeScope firTypeScopeScope = scope((ConeKotlinType) it2.next(), firSession, scopeSession, firResolvePhase);
                if (firTypeScopeScope != null) {
                    arrayList2.add(firTypeScopeScope);
                }
            }
            return companion.prepareIntersectionScope(firSession, firIntersectionScopeOverrideChecker, arrayList2, (ConeSimpleKotlinType) coneKotlinType);
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return scope(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), firSession, scopeSession, firResolvePhase);
        }
        if (coneKotlinType instanceof ConeIntegerConstantOperatorType) {
            return FirIntegerConstantOperatorScopeKt.getOrBuildScopeForIntegerConstantOperatorType(scopeSession, firSession, (ConeIntegerConstantOperatorType) coneKotlinType);
        }
        if (coneKotlinType instanceof ConeIntegerLiteralConstantType) {
            throw new IllegalStateException("ILT should not be in receiver position".toString());
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirTypeScope classScope(ConeClassLikeType coneClassLikeType, FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, firSession, null, 2, null);
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), firSession);
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirClass firClass = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
        if (firClass == null) {
            return null;
        }
        return FirKotlinScopeProviderKt.scopeForClass(firClass, coneClassLikeType.getAttributes().contains(CompilerConeAttributes.RawType.INSTANCE) ? new ConeRawScopeSubstitutor(firSession) : SubstitutorsKt.substitutorByMap(SupertypeUtilsKt.createSubstitutionForScope(firClass.getTypeParameters(), coneClassLikeTypeFullyExpandedType$default, firSession), firSession), firSession, scopeSession, coneClassLikeLookupTag, firResolvePhase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeClassLikeType defaultType(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return defaultType((FirClass) firClassSymbol.getFir());
    }

    public static final ConeClassLikeType defaultType(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        ConeClassLikeLookupTag lookupTag = firClass.getSymbol().getLookupTag();
        List<FirTypeParameterRef> typeParameters = firClass.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null));
        }
        return new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null);
    }

    public static final ConeClassLikeType defaultType(ClassId classId, List<FirTypeParameterSymbol> parameters) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(classId);
        List<FirTypeParameterSymbol> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConeTypeParameterTypeImpl(((FirTypeParameterSymbol) it.next()).getLookupTag(), false, null, 4, null));
        }
        return new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeParameterTypeImpl[0]), false, null, 8, null);
    }

    public static final ScopeSessionKey<FirTypeParameterSymbol, FirTypeScope> getTYPE_PARAMETER_SCOPE_KEY() {
        return TYPE_PARAMETER_SCOPE_KEY;
    }
}
