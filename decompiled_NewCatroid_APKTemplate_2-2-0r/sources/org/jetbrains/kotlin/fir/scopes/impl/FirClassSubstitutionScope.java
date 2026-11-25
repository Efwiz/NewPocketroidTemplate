package org.jetbrains.kotlin.fir.scopes.impl;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCacheWithPostComputeKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideSubstitution;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirClassSubstitutionScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001HBW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u001c\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001cH\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\u001f\u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020#J\u000e\u0010$\u001a\u00020%2\u0006\u0010\u001f\u001a\u00020%J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J.\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020(2\u001c\u0010-\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030/\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020+0.H\u0016J\u001c\u00100\u001a\u00020+2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020+01H\u0016J*\u00102\u001a\u0002032\u0006\u00104\u001a\u00020#2\u0018\u0010-\u001a\u0014\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002030.H\u0016J*\u00105\u001a\u0002032\u0006\u00106\u001a\u00020%2\u0018\u0010-\u001a\u0014\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002030.H\u0016J\u008d\u0001\u00107\u001a\u000203\"\u000e\b\u0000\u00108\u0018\u0001*\u0006\u0012\u0002\b\u0003092\u0006\u0010:\u001a\u0002H82\u001a\b\b\u0010-\u001a\u0014\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002030.25\u0010;\u001a1\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H8\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002030.\u0012\u0004\u0012\u0002030<¢\u0006\u0002\b=2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u00020\f01H\u0082\b¢\u0006\u0002\u0010?J$\u0010@\u001a\u00020+2\u0006\u0010,\u001a\u00020(2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020+01H\u0016J(\u0010A\u001a\u00020+2\u0006\u0010,\u001a\u00020(2\u0016\u0010-\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030B\u0012\u0004\u0012\u00020+01H\u0016J\b\u0010C\u001a\u00020DH\u0016J\u000e\u0010E\u001a\u0004\u0018\u00010F*\u00020FH\u0002J\u0016\u0010E\u001a\u0004\u0018\u00010F*\u00020F2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010E\u001a\u0004\u0018\u00010G*\u00020G2\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassSubstitutionScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "useSiteMemberScope", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "dispatchReceiverTypeForSubstitutedMembers", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "skipPrivateMembers", "", "makeExpect", "derivedClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;ZZLorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$SubstitutionOverride;)V", "newOwnerClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "substitutionOverrideCache", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage$SubstitutionOverrideCache;", "createSubstitutedData", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassSubstitutionScope$SubstitutedData;", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "symbolForOverride", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "createSubstitutionOverrideConstructor", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "original", "createSubstitutionOverrideField", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "createSubstitutionOverrideFunction", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "createSubstitutionOverrideProperty", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "processDeclaredConstructors", "Lkotlin/Function1;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "processDirectOverriddenWithBaseScope", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "callableSymbol", "processDirectOverriddenCallablesWithBaseScope", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "originalInCache", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "toString", "", "substitute", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "SubstitutedData", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassSubstitutionScope extends FirTypeScope {
    private final ConeClassLikeLookupTag derivedClassLookupTag;
    private final ConeClassLikeType dispatchReceiverTypeForSubstitutedMembers;
    private final boolean makeExpect;
    private final ClassId newOwnerClassId;
    private final FirDeclarationOrigin.SubstitutionOverride origin;
    private final FirSession session;
    private final boolean skipPrivateMembers;
    private final FirSubstitutionOverrideStorage.SubstitutionOverrideCache substitutionOverrideCache;
    private final ConeSubstitutor substitutor;
    private final FirTypeScope useSiteMemberScope;

    public /* synthetic */ FirClassSubstitutionScope(FirSession firSession, FirTypeScope firTypeScope, ScopeSessionKey scopeSessionKey, ConeSubstitutor coneSubstitutor, ConeClassLikeType coneClassLikeType, boolean z, boolean z2, ConeClassLikeLookupTag coneClassLikeLookupTag, FirDeclarationOrigin.SubstitutionOverride substitutionOverride, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firTypeScope, scopeSessionKey, coneSubstitutor, coneClassLikeType, z, (i & 64) != 0 ? false : z2, coneClassLikeLookupTag, substitutionOverride);
    }

    public FirClassSubstitutionScope(FirSession session, FirTypeScope useSiteMemberScope, ScopeSessionKey<?, ?> key, ConeSubstitutor substitutor, ConeClassLikeType dispatchReceiverTypeForSubstitutedMembers, boolean z, boolean z2, ConeClassLikeLookupTag derivedClassLookupTag, FirDeclarationOrigin.SubstitutionOverride origin) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(useSiteMemberScope, "useSiteMemberScope");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(dispatchReceiverTypeForSubstitutedMembers, "dispatchReceiverTypeForSubstitutedMembers");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.session = session;
        this.useSiteMemberScope = useSiteMemberScope;
        this.substitutor = substitutor;
        this.dispatchReceiverTypeForSubstitutedMembers = dispatchReceiverTypeForSubstitutedMembers;
        this.skipPrivateMembers = z;
        this.makeExpect = z2;
        this.derivedClassLookupTag = derivedClassLookupTag;
        this.origin = origin;
        this.substitutionOverrideCache = (FirSubstitutionOverrideStorage.SubstitutionOverrideCache) FirClassSubstitutionScopeKt.getSubstitutionOverrideStorage(session).getSubstitutionOverrideCacheByScope().getValue(key, null);
        this.newOwnerClassId = dispatchReceiverTypeForSubstitutedMembers.getLookupTag().getClassId();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol original) {
                Intrinsics.checkNotNullParameter(original, "original");
                processor.invoke(FirClassSubstitutionScope.this.substitutionOverrideCache.getOverridesForFunctions().getValue(original, FirClassSubstitutionScope.this));
            }
        });
        super.processFunctionsByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope.processPropertiesByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> original) {
                Intrinsics.checkNotNullParameter(original, "original");
                if ((original instanceof FirPropertySymbol) || (original instanceof FirFieldSymbol)) {
                    original = FirClassSubstitutionScope.this.substitutionOverrideCache.getOverridesForVariables().getValue(original, FirClassSubstitutionScope.this);
                }
                processor.invoke(original);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, final Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope.processClassifiersByNameWithSubstitution.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                invoke2(firClassifierSymbol, coneSubstitutor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutor) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                processor.invoke(symbol, SubstitutorsKt.chain(substitutor, this.substitutor));
            }
        });
    }

    private final ConeKotlinType substitute(ConeKotlinType coneKotlinType) {
        return this.substitutor.substituteOrNull(coneKotlinType);
    }

    private final ConeKotlinType substitute(ConeKotlinType coneKotlinType, ConeSubstitutor coneSubstitutor) {
        return coneSubstitutor.substituteOrNull(coneKotlinType);
    }

    private final ConeSimpleKotlinType substitute(ConeSimpleKotlinType coneSimpleKotlinType, ConeSubstitutor coneSubstitutor) {
        ConeKotlinType coneKotlinTypeSubstituteOrNull = coneSubstitutor.substituteOrNull(coneSimpleKotlinType);
        if (coneKotlinTypeSubstituteOrNull != null) {
            return ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinTypeSubstituteOrNull);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0110  */
    /* JADX WARN: Code duplicated, block: B:43:0x011a  */
    /* JADX WARN: Code duplicated, block: B:48:0x0129  */
    /* JADX WARN: Code duplicated, block: B:49:0x0130  */
    /* JADX WARN: Code duplicated, block: B:51:0x0133  */
    /* JADX WARN: Code duplicated, block: B:53:0x013b  */
    /* JADX WARN: Code duplicated, block: B:54:0x0141  */
    /* JADX WARN: Code duplicated, block: B:65:0x0123 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x0157 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:? A[LOOP:2: B:41:0x0114->B:67:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirNamedFunctionSymbol createSubstitutionOverrideFunction(FirNamedFunctionSymbol original) {
        ArrayList arrayList;
        Iterator it;
        ConeSimpleKotlinType dispatchReceiverType;
        ConeSimpleKotlinType coneSimpleKotlinTypeSubstitute;
        ConeSimpleKotlinType coneSimpleKotlinType;
        Intrinsics.checkNotNullParameter(original, "original");
        if (Intrinsics.areEqual(this.substitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return original;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(original, FirResolvePhase.TYPES);
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) original.getFir();
        if (this.skipPrivateMembers && Intrinsics.areEqual(firSimpleFunction.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
            return original;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbolCreateSymbolForSubstitutionOverride = FirFakeOverrideGenerator.INSTANCE.createSymbolForSubstitutionOverride(original, this.newOwnerClassId);
        SubstitutedData substitutedDataCreateSubstitutedData = createSubstitutedData(firSimpleFunction, firNamedFunctionSymbolCreateSymbolForSubstitutionOverride);
        List<FirTypeParameterRef> listComponent1 = substitutedDataCreateSubstitutedData.component1();
        ConeSimpleKotlinType dispatchReceiverType2 = substitutedDataCreateSubstitutedData.getDispatchReceiverType();
        ConeKotlinType receiverType = substitutedDataCreateSubstitutedData.getReceiverType();
        ConeKotlinType returnType = substitutedDataCreateSubstitutedData.getReturnType();
        ConeSubstitutor substitutor = substitutedDataCreateSubstitutedData.getSubstitutor();
        FakeOverrideSubstitution fakeOverrideSubstitution = substitutedDataCreateSubstitutedData.getFakeOverrideSubstitution();
        List<FirValueParameter> valueParameters = firSimpleFunction.getValueParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it2 = valueParameters.iterator();
        while (it2.hasNext()) {
            arrayList2.add(substitute(FirTypeUtilsKt.getConeType(((FirValueParameter) it2.next()).getReturnTypeRef()), substitutor));
        }
        ArrayList arrayList3 = arrayList2;
        List<FirContextReceiver> contextReceivers = firSimpleFunction.getContextReceivers();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it3 = contextReceivers.iterator();
        while (it3.hasNext()) {
            arrayList4.add(substitute(FirTypeUtilsKt.getConeType(((FirContextReceiver) it3.next()).getTypeRef()), substitutor));
        }
        ArrayList arrayList5 = arrayList4;
        if (receiverType == null && returnType == null) {
            ArrayList arrayList6 = arrayList3;
            if ((arrayList6 instanceof Collection) && arrayList6.isEmpty()) {
                if (listComponent1 == firSimpleFunction.getTypeParameters()) {
                    arrayList = arrayList5;
                    if (arrayList instanceof Collection) {
                        it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (((ConeKotlinType) it.next()) == null) {
                                }
                            }
                        }
                    } else {
                        it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (((ConeKotlinType) it.next()) == null) {
                                }
                            }
                        }
                    }
                    dispatchReceiverType = original.getDispatchReceiverType();
                    if (dispatchReceiverType != null) {
                        coneSimpleKotlinTypeSubstitute = substitute(dispatchReceiverType, this.substitutor);
                    } else {
                        coneSimpleKotlinTypeSubstitute = null;
                    }
                    if (coneSimpleKotlinTypeSubstitute != null) {
                        return original;
                    }
                    FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
                    FirSession firSession = this.session;
                    ConeClassLikeLookupTag coneClassLikeLookupTag = this.derivedClassLookupTag;
                    if (dispatchReceiverType2 == null) {
                        coneSimpleKotlinType = this.dispatchReceiverTypeForSubstitutedMembers;
                    } else {
                        coneSimpleKotlinType = dispatchReceiverType2;
                    }
                    return firFakeOverrideGenerator.createSubstitutionOverrideFunction(firSession, firNamedFunctionSymbolCreateSymbolForSubstitutionOverride, firSimpleFunction, coneClassLikeLookupTag, coneSimpleKotlinType, this.origin, (6080 & 64) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 128) != 0 ? null : null), (6080 & 256) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 512) != 0 ? null : null), (List<? extends FirTypeParameter>) ((6080 & 1024) != 0 ? null : null), (6080 & 2048) != 0 ? firSimpleFunction.getStatus().isExpect() : this.makeExpect, (6080 & 4096) != 0 ? null : null);
                }
            } else {
                Iterator it4 = arrayList6.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        if (((ConeKotlinType) it4.next()) == null) {
                        }
                    } else if (listComponent1 == firSimpleFunction.getTypeParameters() && fakeOverrideSubstitution == null) {
                        arrayList = arrayList5;
                        if ((arrayList instanceof Collection) || !arrayList.isEmpty()) {
                            it = arrayList.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (((ConeKotlinType) it.next()) == null) {
                                    }
                                }
                            }
                        }
                        dispatchReceiverType = original.getDispatchReceiverType();
                        if (dispatchReceiverType != null) {
                            coneSimpleKotlinTypeSubstitute = substitute(dispatchReceiverType, this.substitutor);
                        } else {
                            coneSimpleKotlinTypeSubstitute = null;
                        }
                        if (coneSimpleKotlinTypeSubstitute != null) {
                            return original;
                        }
                        FirFakeOverrideGenerator firFakeOverrideGenerator2 = FirFakeOverrideGenerator.INSTANCE;
                        FirSession firSession2 = this.session;
                        ConeClassLikeLookupTag coneClassLikeLookupTag2 = this.derivedClassLookupTag;
                        if (dispatchReceiverType2 == null) {
                            coneSimpleKotlinType = this.dispatchReceiverTypeForSubstitutedMembers;
                        } else {
                            coneSimpleKotlinType = dispatchReceiverType2;
                        }
                        return firFakeOverrideGenerator2.createSubstitutionOverrideFunction(firSession2, firNamedFunctionSymbolCreateSymbolForSubstitutionOverride, firSimpleFunction, coneClassLikeLookupTag2, coneSimpleKotlinType, this.origin, (6080 & 64) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 128) != 0 ? null : null), (6080 & 256) != 0 ? null : null, (List<? extends ConeKotlinType>) ((6080 & 512) != 0 ? null : null), (List<? extends FirTypeParameter>) ((6080 & 1024) != 0 ? null : null), (6080 & 2048) != 0 ? firSimpleFunction.getStatus().isExpect() : this.makeExpect, (6080 & 4096) != 0 ? null : null);
                    }
                }
            }
        }
        FirFakeOverrideGenerator firFakeOverrideGenerator3 = FirFakeOverrideGenerator.INSTANCE;
        FirSession firSession3 = this.session;
        ConeClassLikeLookupTag coneClassLikeLookupTag3 = this.derivedClassLookupTag;
        ConeSimpleKotlinType coneSimpleKotlinType2 = dispatchReceiverType2 == null ? this.dispatchReceiverTypeForSubstitutedMembers : dispatchReceiverType2;
        FirDeclarationOrigin.SubstitutionOverride substitutionOverride = this.origin;
        Intrinsics.checkNotNull(listComponent1, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.declarations.FirTypeParameter>");
        return firFakeOverrideGenerator3.createSubstitutionOverrideFunction(firSession3, firNamedFunctionSymbolCreateSymbolForSubstitutionOverride, firSimpleFunction, coneClassLikeLookupTag3, coneSimpleKotlinType2, substitutionOverride, receiverType, arrayList5, returnType, arrayList3, listComponent1, this.makeExpect, fakeOverrideSubstitution);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:37:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:40:0x0103  */
    /* JADX WARN: Code duplicated, block: B:52:0x010d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:? A[LOOP:2: B:38:0x00fd->B:53:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirConstructorSymbol createSubstitutionOverrideConstructor(FirConstructorSymbol original) {
        ArrayList arrayList;
        Iterator it;
        Intrinsics.checkNotNullParameter(original, "original");
        if (Intrinsics.areEqual(this.substitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return original;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(original, FirResolvePhase.TYPES);
        FirConstructor firConstructor = (FirConstructor) original.getFir();
        FirConstructorSymbol firConstructorSymbol = new FirConstructorSymbol(original.getCallableId());
        SubstitutedData substitutedDataCreateSubstitutedData = createSubstitutedData(firConstructor, firConstructorSymbol);
        List<FirTypeParameterRef> listComponent1 = substitutedDataCreateSubstitutedData.component1();
        ConeKotlinType returnType = substitutedDataCreateSubstitutedData.getReturnType();
        ConeSubstitutor substitutor = substitutedDataCreateSubstitutedData.getSubstitutor();
        FakeOverrideSubstitution fakeOverrideSubstitution = substitutedDataCreateSubstitutedData.getFakeOverrideSubstitution();
        ConeSimpleKotlinType dispatchReceiverType = original.getDispatchReceiverType();
        ConeSimpleKotlinType coneSimpleKotlinTypeSubstitute = dispatchReceiverType != null ? substitute(dispatchReceiverType, this.substitutor) : null;
        List<FirValueParameter> valueParameters = firConstructor.getValueParameters();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it2 = valueParameters.iterator();
        while (it2.hasNext()) {
            arrayList2.add(substitute(FirTypeUtilsKt.getConeType(((FirValueParameter) it2.next()).getReturnTypeRef()), substitutor));
        }
        ArrayList arrayList3 = arrayList2;
        List<FirContextReceiver> contextReceivers = firConstructor.getContextReceivers();
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it3 = contextReceivers.iterator();
        while (it3.hasNext()) {
            arrayList4.add(substitute(FirTypeUtilsKt.getConeType(((FirContextReceiver) it3.next()).getTypeRef()), substitutor));
        }
        ArrayList arrayList5 = arrayList4;
        if (returnType == null) {
            ArrayList arrayList6 = arrayList3;
            if ((arrayList6 instanceof Collection) && arrayList6.isEmpty()) {
                if (listComponent1 == firConstructor.getTypeParameters()) {
                    arrayList = arrayList5;
                    if (arrayList instanceof Collection) {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ConeKotlinType) it.next()) == null) {
                            }
                        }
                    } else {
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ConeKotlinType) it.next()) == null) {
                            }
                        }
                    }
                    return original;
                }
            } else {
                Iterator it4 = arrayList6.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        if (((ConeKotlinType) it4.next()) == null) {
                        }
                    } else if (listComponent1 == firConstructor.getTypeParameters()) {
                        arrayList = arrayList5;
                        if ((arrayList instanceof Collection) || !arrayList.isEmpty()) {
                            it = arrayList.iterator();
                            while (it.hasNext()) {
                                if (((ConeKotlinType) it.next()) == null) {
                                }
                            }
                        }
                        return original;
                    }
                }
            }
        }
        return FirFakeOverrideGenerator.INSTANCE.createCopyForFirConstructor(firConstructorSymbol, this.session, firConstructor, this.derivedClassLookupTag, this.origin, coneSimpleKotlinTypeSubstitute, returnType != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(returnType) : null, arrayList3, arrayList5, listComponent1, this.makeExpect, fakeOverrideSubstitution).getSymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirPropertySymbol createSubstitutionOverrideProperty(FirPropertySymbol original) {
        Intrinsics.checkNotNullParameter(original, "original");
        if (Intrinsics.areEqual(this.substitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return original;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(original, FirResolvePhase.TYPES);
        FirProperty firProperty = (FirProperty) original.getFir();
        if (this.skipPrivateMembers && Intrinsics.areEqual(firProperty.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
            return original;
        }
        FirPropertySymbol firPropertySymbolCreateSymbolForSubstitutionOverride = FirFakeOverrideGenerator.INSTANCE.createSymbolForSubstitutionOverride(original, this.newOwnerClassId);
        SubstitutedData substitutedDataCreateSubstitutedData = createSubstitutedData(firProperty, firPropertySymbolCreateSymbolForSubstitutionOverride);
        List<FirTypeParameterRef> listComponent1 = substitutedDataCreateSubstitutedData.component1();
        ConeSimpleKotlinType dispatchReceiverType = substitutedDataCreateSubstitutedData.getDispatchReceiverType();
        ConeKotlinType receiverType = substitutedDataCreateSubstitutedData.getReceiverType();
        ConeKotlinType returnType = substitutedDataCreateSubstitutedData.getReturnType();
        FakeOverrideSubstitution fakeOverrideSubstitution = substitutedDataCreateSubstitutedData.getFakeOverrideSubstitution();
        List<FirContextReceiver> contextReceivers = firProperty.getContextReceivers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            arrayList.add(substitute(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()), this.substitutor));
        }
        ArrayList arrayList2 = arrayList;
        if (receiverType == null && returnType == null && listComponent1 == firProperty.getTypeParameters() && fakeOverrideSubstitution == null) {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it2 = arrayList3.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((ConeKotlinType) it2.next()) == null) {
                        }
                    }
                }
            }
            ConeSimpleKotlinType dispatchReceiverType2 = original.getDispatchReceiverType();
            if ((dispatchReceiverType2 != null ? substitute(dispatchReceiverType2, this.substitutor) : null) != null) {
                return FirFakeOverrideGenerator.INSTANCE.createSubstitutionOverrideProperty(this.session, firPropertySymbolCreateSymbolForSubstitutionOverride, firProperty, this.derivedClassLookupTag, dispatchReceiverType == null ? this.dispatchReceiverTypeForSubstitutedMembers : dispatchReceiverType, this.origin, (XKeySym.XK_overbar & 64) != 0 ? null : null, (XKeySym.XK_overbar & 128) != 0 ? null : null, (XKeySym.XK_overbar & 256) != 0 ? null : null, (XKeySym.XK_overbar & 512) != 0 ? null : null, (XKeySym.XK_overbar & 1024) != 0 ? firProperty.getStatus().isExpect() : this.makeExpect, (XKeySym.XK_overbar & 2048) != 0 ? null : null);
            }
            return original;
        }
        FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
        FirSession firSession = this.session;
        ConeClassLikeLookupTag coneClassLikeLookupTag = this.derivedClassLookupTag;
        ConeSimpleKotlinType coneSimpleKotlinType = dispatchReceiverType == null ? this.dispatchReceiverTypeForSubstitutedMembers : dispatchReceiverType;
        FirDeclarationOrigin.SubstitutionOverride substitutionOverride = this.origin;
        Intrinsics.checkNotNull(listComponent1, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.declarations.FirTypeParameter>");
        return firFakeOverrideGenerator.createSubstitutionOverrideProperty(firSession, firPropertySymbolCreateSymbolForSubstitutionOverride, firProperty, coneClassLikeLookupTag, coneSimpleKotlinType, substitutionOverride, receiverType, arrayList2, returnType, listComponent1, this.makeExpect, fakeOverrideSubstitution);
    }

    /* JADX INFO: compiled from: FirClassSubstitutionScope.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001BC\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\bHÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\bHÆ\u0003J\t\u0010\u001e\u001a\u00020\u000bHÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\rHÆ\u0003JS\u0010 \u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rHÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassSubstitutionScope$SubstitutedData;", "", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "receiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "returnType", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "fakeOverrideSubstitution", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;)V", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getFakeOverrideSubstitution", "()Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "getReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getReturnType", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "getTypeParameters", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class SubstitutedData {
        private final ConeSimpleKotlinType dispatchReceiverType;
        private final FakeOverrideSubstitution fakeOverrideSubstitution;
        private final ConeKotlinType receiverType;
        private final ConeKotlinType returnType;
        private final ConeSubstitutor substitutor;
        private final List<FirTypeParameterRef> typeParameters;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ SubstitutedData copy$default(SubstitutedData substitutedData, List list, ConeSimpleKotlinType coneSimpleKotlinType, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, ConeSubstitutor coneSubstitutor, FakeOverrideSubstitution fakeOverrideSubstitution, int i, Object obj) {
            if ((i & 1) != 0) {
                list = substitutedData.typeParameters;
            }
            if ((i & 2) != 0) {
                coneSimpleKotlinType = substitutedData.dispatchReceiverType;
            }
            ConeSimpleKotlinType coneSimpleKotlinType2 = coneSimpleKotlinType;
            if ((i & 4) != 0) {
                coneKotlinType = substitutedData.receiverType;
            }
            ConeKotlinType coneKotlinType3 = coneKotlinType;
            if ((i & 8) != 0) {
                coneKotlinType2 = substitutedData.returnType;
            }
            ConeKotlinType coneKotlinType4 = coneKotlinType2;
            if ((i & 16) != 0) {
                coneSubstitutor = substitutedData.substitutor;
            }
            ConeSubstitutor coneSubstitutor2 = coneSubstitutor;
            if ((i & 32) != 0) {
                fakeOverrideSubstitution = substitutedData.fakeOverrideSubstitution;
            }
            return substitutedData.copy(list, coneSimpleKotlinType2, coneKotlinType3, coneKotlinType4, coneSubstitutor2, fakeOverrideSubstitution);
        }

        public final List<FirTypeParameterRef> component1() {
            return this.typeParameters;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ConeSimpleKotlinType getDispatchReceiverType() {
            return this.dispatchReceiverType;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final ConeKotlinType getReceiverType() {
            return this.receiverType;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final ConeKotlinType getReturnType() {
            return this.returnType;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final ConeSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final FakeOverrideSubstitution getFakeOverrideSubstitution() {
            return this.fakeOverrideSubstitution;
        }

        public final SubstitutedData copy(List<? extends FirTypeParameterRef> typeParameters, ConeSimpleKotlinType dispatchReceiverType, ConeKotlinType receiverType, ConeKotlinType returnType, ConeSubstitutor substitutor, FakeOverrideSubstitution fakeOverrideSubstitution) {
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            return new SubstitutedData(typeParameters, dispatchReceiverType, receiverType, returnType, substitutor, fakeOverrideSubstitution);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SubstitutedData)) {
                return false;
            }
            SubstitutedData substitutedData = (SubstitutedData) other;
            return Intrinsics.areEqual(this.typeParameters, substitutedData.typeParameters) && Intrinsics.areEqual(this.dispatchReceiverType, substitutedData.dispatchReceiverType) && Intrinsics.areEqual(this.receiverType, substitutedData.receiverType) && Intrinsics.areEqual(this.returnType, substitutedData.returnType) && Intrinsics.areEqual(this.substitutor, substitutedData.substitutor) && Intrinsics.areEqual(this.fakeOverrideSubstitution, substitutedData.fakeOverrideSubstitution);
        }

        public int hashCode() {
            int iHashCode = this.typeParameters.hashCode() * 31;
            ConeSimpleKotlinType coneSimpleKotlinType = this.dispatchReceiverType;
            int iHashCode2 = (iHashCode + (coneSimpleKotlinType == null ? 0 : coneSimpleKotlinType.hashCode())) * 31;
            ConeKotlinType coneKotlinType = this.receiverType;
            int iHashCode3 = (iHashCode2 + (coneKotlinType == null ? 0 : coneKotlinType.hashCode())) * 31;
            ConeKotlinType coneKotlinType2 = this.returnType;
            int iHashCode4 = (((iHashCode3 + (coneKotlinType2 == null ? 0 : coneKotlinType2.hashCode())) * 31) + this.substitutor.hashCode()) * 31;
            FakeOverrideSubstitution fakeOverrideSubstitution = this.fakeOverrideSubstitution;
            return iHashCode4 + (fakeOverrideSubstitution != null ? fakeOverrideSubstitution.hashCode() : 0);
        }

        public String toString() {
            return "SubstitutedData(typeParameters=" + this.typeParameters + ", dispatchReceiverType=" + this.dispatchReceiverType + ", receiverType=" + this.receiverType + ", returnType=" + this.returnType + ", substitutor=" + this.substitutor + ", fakeOverrideSubstitution=" + this.fakeOverrideSubstitution + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SubstitutedData(List<? extends FirTypeParameterRef> typeParameters, ConeSimpleKotlinType coneSimpleKotlinType, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, ConeSubstitutor substitutor, FakeOverrideSubstitution fakeOverrideSubstitution) {
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            this.typeParameters = typeParameters;
            this.dispatchReceiverType = coneSimpleKotlinType;
            this.receiverType = coneKotlinType;
            this.returnType = coneKotlinType2;
            this.substitutor = substitutor;
            this.fakeOverrideSubstitution = fakeOverrideSubstitution;
        }

        public final List<FirTypeParameterRef> getTypeParameters() {
            return this.typeParameters;
        }

        public final ConeSimpleKotlinType getDispatchReceiverType() {
            return this.dispatchReceiverType;
        }

        public final ConeKotlinType getReceiverType() {
            return this.receiverType;
        }

        public final ConeKotlinType getReturnType() {
            return this.returnType;
        }

        public final ConeSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        public final FakeOverrideSubstitution getFakeOverrideSubstitution() {
            return this.fakeOverrideSubstitution;
        }
    }

    private final SubstitutedData createSubstitutedData(FirCallableDeclaration member, FirBasedSymbol<?> symbolForOverride) {
        ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull;
        FirTypeRef typeRef;
        if (member instanceof FirConstructor) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(member.getReturnTypeRef());
            Intrinsics.checkNotNull(coneType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ((ConeClassLikeType) coneType).getLookupTag();
        } else {
            coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(member);
        }
        FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
        FirSession firSession = this.session;
        Intrinsics.checkNotNull(member, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner");
        Pair<List<FirTypeParameterRef>, ConeSubstitutor> pairCreateNewTypeParametersAndSubstitutor = firFakeOverrideGenerator.createNewTypeParametersAndSubstitutor(firSession, member, symbolForOverride, this.substitutor, this.origin, !Intrinsics.areEqual(this.dispatchReceiverTypeForSubstitutedMembers.getLookupTag(), coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull));
        List<FirTypeParameterRef> listComponent1 = pairCreateNewTypeParametersAndSubstitutor.component1();
        ConeSubstitutor coneSubstitutorComponent2 = pairCreateNewTypeParametersAndSubstitutor.component2();
        FirReceiverParameter receiverParameter = member.getReceiverParameter();
        ConeKotlinType coneType2 = (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
        ConeKotlinType coneKotlinTypeSubstitute = coneType2 != null ? substitute(coneType2, coneSubstitutorComponent2) : null;
        ConeSimpleKotlinType coneSimpleKotlinTypeSubstitute = substitute((ConeSimpleKotlinType) this.dispatchReceiverTypeForSubstitutedMembers, coneSubstitutorComponent2);
        FirTypeRef returnTypeRef = member.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        return new SubstitutedData(listComponent1, coneSimpleKotlinTypeSubstitute, coneKotlinTypeSubstitute, type != null ? substitute(type, coneSubstitutorComponent2) : null, coneSubstitutorComponent2, type == null ? new FakeOverrideSubstitution(coneSubstitutorComponent2, member.getSymbol()) : null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirFieldSymbol createSubstitutionOverrideField(FirFieldSymbol original) {
        ConeKotlinType coneKotlinTypeSubstitute;
        Intrinsics.checkNotNullParameter(original, "original");
        if (Intrinsics.areEqual(this.substitutor, ConeSubstitutor.Empty.INSTANCE)) {
            return original;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(original, FirResolvePhase.TYPES);
        FirField firField = (FirField) original.getFir();
        if (this.skipPrivateMembers && Intrinsics.areEqual(firField.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
            return original;
        }
        FirTypeRef returnTypeRef = firField.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        return (coneKotlinType == null || (coneKotlinTypeSubstitute = substitute(coneKotlinType)) == null) ? original : FirFakeOverrideGenerator.INSTANCE.createSubstitutionOverrideField(this.session, firField, this.derivedClassLookupTag, coneKotlinTypeSubstitute, this.origin);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassSubstitutionScope.processDeclaredConstructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol original) {
                Intrinsics.checkNotNullParameter(original, "original");
                processor.invoke(FirClassSubstitutionScope.this.substitutionOverrideCache.getOverridesForConstructors().getValue(original, FirClassSubstitutionScope.this));
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.useSiteMemberScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.useSiteMemberScope.getClassifierNames();
    }

    public String toString() {
        return "Substitution scope for [" + this.useSiteMemberScope + "] for type " + this.dispatchReceiverTypeForSubstitutedMembers;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirNamedFunctionSymbol firNamedFunctionSymbol = functionSymbol;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
        if (firNamedFunctionSymbol2 != null) {
            if (FirCacheWithPostComputeKt.contains(this.substitutionOverrideCache.getOverridesForFunctions(), firNamedFunctionSymbol2)) {
                return processor.invoke(firNamedFunctionSymbol2, this.useSiteMemberScope).not() ? ProcessorAction.STOP : ProcessorAction.NONE;
            }
        }
        return this.useSiteMemberScope.processDirectOverriddenFunctionsWithBaseScope(firNamedFunctionSymbol, processor);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirPropertySymbol firPropertySymbol = propertySymbol;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firPropertySymbol.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
        if (firPropertySymbol2 != null) {
            if (FirCacheWithPostComputeKt.contains(this.substitutionOverrideCache.getOverridesForVariables(), firPropertySymbol2)) {
                return processor.invoke(firPropertySymbol2, this.useSiteMemberScope).not() ? ProcessorAction.STOP : ProcessorAction.NONE;
            }
        }
        return this.useSiteMemberScope.processDirectOverriddenPropertiesWithBaseScope(firPropertySymbol, processor);
    }
}
