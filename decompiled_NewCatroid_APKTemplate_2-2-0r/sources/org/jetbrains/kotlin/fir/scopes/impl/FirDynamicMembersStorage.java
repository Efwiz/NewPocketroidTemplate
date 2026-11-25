package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirOperationNameConventions;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirDynamicScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J\u000e\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R%\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDynamicMembersStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "anyArrayTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "dynamicScopeCacheByScope", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirDynamicScope;", "", "getDynamicScopeCacheByScope$annotations", "()V", "dynamicTypeRef", "functionsCacheByName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "getFunctionsCacheByName", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "propertiesCacheByName", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getPropertiesCacheByName", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "buildPseudoFunctionByName", "name", "buildPseudoPropertyByName", "getDynamicScopeFor", "scopeSession", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDynamicMembersStorage implements FirSessionComponent {
    private final FirResolvedTypeRef anyArrayTypeRef;
    private final FirCachesFactory cachesFactory;
    private final FirCache dynamicScopeCacheByScope;
    private final FirResolvedTypeRef dynamicTypeRef;
    private final FirCache functionsCacheByName;
    private final FirCache propertiesCacheByName;
    private final FirSession session;

    public FirDynamicMembersStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.dynamicScopeCacheByScope = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicMembersStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ScopeSession) obj, (Void) obj2);
            }

            public final FirDynamicScope invoke(ScopeSession key, Void r3) {
                Intrinsics.checkNotNullParameter(key, "key");
                return new FirDynamicScope(this.this$0.getSession(), key);
            }
        });
        this.functionsCacheByName = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicMembersStorage$special$$inlined$createCache$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((Name) obj, (Void) obj2);
            }

            public final FirSimpleFunction invoke(Name key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.buildPseudoFunctionByName(key);
            }
        });
        this.propertiesCacheByName = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDynamicMembersStorage$special$$inlined$createCache$3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((Name) obj, (Void) obj2);
            }

            public final FirProperty invoke(Name key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.buildPseudoPropertyByName(key);
            }
        });
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeUtilsKt.create(ConeDynamicType.INSTANCE, session));
        FirResolvedTypeRef firResolvedTypeRefMo6810build = firResolvedTypeRefBuilder.mo6809build();
        this.dynamicTypeRef = firResolvedTypeRefMo6810build;
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder2.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getArray()), new ConeKotlinType[]{FirTypeUtilsKt.getConeType(firResolvedTypeRefMo6810build)}, false, null, 8, null));
        this.anyArrayTypeRef = firResolvedTypeRefBuilder2.mo6809build();
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirDynamicScope getDynamicScopeFor(ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return (FirDynamicScope) this.dynamicScopeCacheByScope.getValue(scopeSession, null);
    }

    public final FirCache getFunctionsCacheByName() {
        return this.functionsCacheByName;
    }

    public final FirCache getPropertiesCacheByName() {
        return this.propertiesCacheByName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirSimpleFunction buildPseudoFunctionByName(Name name2) {
        FirResolvedTypeRef unitType;
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl.setInfix(true);
        firResolvedDeclarationStatusImpl.setOperator(true);
        firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firSimpleFunctionBuilder.setName(name2);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(StandardNames.DYNAMIC_FQ_NAME, firSimpleFunctionBuilder.getName())));
        firSimpleFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.DynamicScope.INSTANCE);
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        if (FirOperationNameConventions.INSTANCE.getASSIGNMENT_NAMES().containsKey(name2)) {
            unitType = this.session.getBuiltinTypes().getUnitType();
        } else {
            unitType = this.dynamicTypeRef;
        }
        firSimpleFunctionBuilder.setReturnTypeRef(unitType);
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firValueParameterBuilder.setContainingFunctionSymbol(firSimpleFunctionBuilder.getSymbol());
        firValueParameterBuilder.setOrigin(FirDeclarationOrigin.DynamicScope.INSTANCE);
        firValueParameterBuilder.setReturnTypeRef(this.anyArrayTypeRef);
        Name nameIdentifier = Name.identifier("args");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        firValueParameterBuilder.setName(nameIdentifier);
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getName()));
        firValueParameterBuilder.setCrossinline(false);
        firValueParameterBuilder.setNoinline(false);
        firValueParameterBuilder.setVararg(true);
        firSimpleFunctionBuilder.getValueParameters().add(firValueParameterBuilder.mo6809build());
        return firSimpleFunctionBuilder.mo6809build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirProperty buildPseudoPropertyByName(Name name2) {
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setName(name2);
        firPropertyBuilder.setSymbol(new FirPropertySymbol(new CallableId(StandardNames.DYNAMIC_FQ_NAME, firPropertyBuilder.getName())));
        firPropertyBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, EffectiveVisibility.Public.INSTANCE));
        firPropertyBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.DynamicScope.INSTANCE);
        firPropertyBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firPropertyBuilder.setReturnTypeRef(this.dynamicTypeRef);
        firPropertyBuilder.setVar(true);
        firPropertyBuilder.setLocal(false);
        return firPropertyBuilder.mo6809build();
    }
}
