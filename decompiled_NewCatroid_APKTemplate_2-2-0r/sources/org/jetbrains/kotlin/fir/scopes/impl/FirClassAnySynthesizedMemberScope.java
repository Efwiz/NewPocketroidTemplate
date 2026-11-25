package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbolKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBooleanTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitStringTypeRef;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirClassAnySynthesizedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 52\u00020\u0001:\u00015B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u0015\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0000¢\u0006\u0002\b\u001fJ\b\u0010 \u001a\u00020\u0019H\u0002J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\"H\u0016J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001e0\"H\u0016J.\u0010$\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001e2\u001c\u0010&\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030(\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020%0'H\u0016J\u001c\u0010*\u001a\u00020%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020%0+H\u0016J$\u0010-\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001e2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020%0+H\u0016J(\u0010.\u001a\u00020%2\u0006\u0010\u001d\u001a\u00020\u001e2\u0016\u0010&\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030/\u0012\u0004\u0012\u00020%0+H\u0016J\u001e\u00100\u001a\u00020%*\u0002012\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u00102\u001a\u000203H\u0002J\u0014\u00104\u001a\u000203*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassAnySynthesizedMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "declaredMemberScope", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "baseModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "originForFunctions", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Synthetic;", "superKlassScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "synthesizedCache", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSynthesizedStorage$SynthesizedCache;", "synthesizedSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "generateEqualsFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "generateHashCodeFunction", "generateSyntheticFunctionByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "generateSyntheticFunctionByName$providers", "generateToStringFunction", "getCallableNames", "", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "generateSyntheticFunction", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirSimpleFunctionBuilder;", "isOperator", "", "matchesSomeAnyMember", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassAnySynthesizedMemberScope extends FirContainingNamesAwareScope {
    private final FirModuleData baseModuleData;
    private final FirContainingNamesAwareScope declaredMemberScope;
    private final ConeClassLikeType dispatchReceiverType;
    private final ConeClassLikeLookupTag lookupTag;
    private final FirDeclarationOrigin.Synthetic originForFunctions;
    private final FirSession session;
    private final FirTypeScope superKlassScope;
    private final FirSynthesizedStorage.SynthesizedCache synthesizedCache;
    private final KtSourceElement synthesizedSource;
    private static final HashSet<Name> ANY_MEMBER_NAMES = SetsKt.hashSetOf(OperatorNameConventions.HASH_CODE, OperatorNameConventions.EQUALS, OperatorNameConventions.TO_STRING);

    public FirClassAnySynthesizedMemberScope(FirSession session, FirContainingNamesAwareScope declaredMemberScope, FirRegularClass klass, ScopeSession scopeSession) {
        FirDeclarationOrigin.Synthetic.ValueClassMember valueClassMember;
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.declaredMemberScope = declaredMemberScope;
        if (klass.getStatus().isData()) {
            valueClassMember = FirDeclarationOrigin.Synthetic.DataClassMember.INSTANCE;
        } else if (klass.getStatus().isInline()) {
            valueClassMember = FirDeclarationOrigin.Synthetic.ValueClassMember.INSTANCE;
        } else {
            throw new IllegalStateException(("This scope should not be created for non-data and non-value class. " + UtilsKt.render(klass)).toString());
        }
        this.originForFunctions = valueClassMember;
        ConeClassLikeLookupTag lookupTag = klass.getSymbol().getLookupTag();
        this.lookupTag = lookupTag;
        this.baseModuleData = klass.getModuleData();
        FirRegularClass firRegularClass = klass;
        this.dispatchReceiverType = ScopeUtilsKt.defaultType(firRegularClass);
        this.synthesizedCache = (FirSynthesizedStorage.SynthesizedCache) FirClassAnySynthesizedMemberScopeKt.getSynthesizedStorage(session).getSynthesizedCacheByScope().getValue(lookupTag, null);
        KtSourceElement source = klass.getSource();
        this.synthesizedSource = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE, 0, 0, 6, null) : null;
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) CollectionsKt.firstOrNull(SupertypeUtilsKt.lookupSuperTypes$default((FirClass) firRegularClass, false, false, session, true, (SupertypeSupplier) null, 32, (Object) null));
        this.superKlassScope = coneClassLikeType != null ? FirKotlinScopeProviderKt.scopeForSupertype(coneClassLikeType, session, scopeSession, firRegularClass, FirResolvePhase.TYPES) : null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.declaredMemberScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.declaredMemberScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(final Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (!ANY_MEMBER_NAMES.contains(name2)) {
            this.declaredMemberScope.processFunctionsByName(name2, processor);
            return;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        this.declaredMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassAnySynthesizedMemberScope.processFunctionsByName.1
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
            public final void invoke2(FirNamedFunctionSymbol fromDeclaredScope) {
                Intrinsics.checkNotNullParameter(fromDeclaredScope, "fromDeclaredScope");
                if (FirClassAnySynthesizedMemberScope.this.matchesSomeAnyMember(fromDeclaredScope, name2)) {
                    booleanRef.element = false;
                }
                processor.invoke(fromDeclaredScope);
            }
        });
        if (booleanRef.element) {
            FirTypeScope firTypeScope = this.superKlassScope;
            if (firTypeScope != null) {
                firTypeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirClassAnySynthesizedMemberScope.processFunctionsByName.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol fromSuperType) {
                        Intrinsics.checkNotNullParameter(fromSuperType, "fromSuperType");
                        if (booleanRef.element && fromSuperType.getRawStatus().getModality() == Modality.FINAL && this.matchesSomeAnyMember(fromSuperType, name2)) {
                            booleanRef.element = false;
                        }
                    }
                });
            }
            if (booleanRef.element) {
                processor.invoke(this.synthesizedCache.getSynthesizedFunction().getValue(name2, this));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean matchesSomeAnyMember(FirNamedFunctionSymbol firNamedFunctionSymbol, Name name2) {
        if (Intrinsics.areEqual(name2, OperatorNameConventions.HASH_CODE) ? true : Intrinsics.areEqual(name2, OperatorNameConventions.TO_STRING)) {
            return firNamedFunctionSymbol.getValueParameterSymbols().isEmpty() && !FirCallableSymbolKt.isExtension(firNamedFunctionSymbol) && ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getContextReceivers().isEmpty();
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firNamedFunctionSymbol, FirResolvePhase.TYPES);
        return DeclarationUtilsKt.isEquals((FirSimpleFunction) firNamedFunctionSymbol.getFir(), this.session);
    }

    public final FirNamedFunctionSymbol generateSyntheticFunctionByName$providers(Name name2) {
        FirSimpleFunction firSimpleFunctionGenerateToStringFunction;
        Intrinsics.checkNotNullParameter(name2, "name");
        if (Intrinsics.areEqual(name2, OperatorNameConventions.EQUALS)) {
            firSimpleFunctionGenerateToStringFunction = generateEqualsFunction();
        } else if (Intrinsics.areEqual(name2, OperatorNameConventions.HASH_CODE)) {
            firSimpleFunctionGenerateToStringFunction = generateHashCodeFunction();
        } else {
            if (!Intrinsics.areEqual(name2, OperatorNameConventions.TO_STRING)) {
                AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                throw null;
            }
            firSimpleFunctionGenerateToStringFunction = generateToStringFunction();
        }
        return firSimpleFunctionGenerateToStringFunction.getSymbol();
    }

    static /* synthetic */ void generateSyntheticFunction$default(FirClassAnySynthesizedMemberScope firClassAnySynthesizedMemberScope, FirSimpleFunctionBuilder firSimpleFunctionBuilder, Name name2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        firClassAnySynthesizedMemberScope.generateSyntheticFunction(firSimpleFunctionBuilder, name2, z);
    }

    private final void generateSyntheticFunction(FirSimpleFunctionBuilder firSimpleFunctionBuilder, Name name2, boolean z) {
        firSimpleFunctionBuilder.setSource(this.synthesizedSource);
        firSimpleFunctionBuilder.setModuleData(this.baseModuleData);
        firSimpleFunctionBuilder.setOrigin(this.originForFunctions);
        firSimpleFunctionBuilder.setName(name2);
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.OPEN, EffectiveVisibility.Public.INSTANCE);
        firResolvedDeclarationStatusImpl.setOperator(z);
        firSimpleFunctionBuilder.setStatus(firResolvedDeclarationStatusImpl);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(this.lookupTag.getClassId(), name2)));
        firSimpleFunctionBuilder.setDispatchReceiverType(this.dispatchReceiverType);
    }

    private final FirSimpleFunction generateEqualsFunction() {
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        generateSyntheticFunction(firSimpleFunctionBuilder, OperatorNameConventions.EQUALS, true);
        firSimpleFunctionBuilder.setReturnTypeRef(new FirImplicitBooleanTypeRef(firSimpleFunctionBuilder.getSource()));
        List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        Name nameIdentifier = Name.identifier("other");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        firValueParameterBuilder.setName(nameIdentifier);
        firValueParameterBuilder.setOrigin(this.originForFunctions);
        firValueParameterBuilder.setModuleData(this.baseModuleData);
        firValueParameterBuilder.setReturnTypeRef(new FirImplicitNullableAnyTypeRef(null));
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getName()));
        firValueParameterBuilder.setContainingFunctionSymbol(firSimpleFunctionBuilder.getSymbol());
        firValueParameterBuilder.setCrossinline(false);
        firValueParameterBuilder.setNoinline(false);
        firValueParameterBuilder.setVararg(false);
        valueParameters.add(firValueParameterBuilder.mo6809build());
        return firSimpleFunctionBuilder.mo6809build();
    }

    private final FirSimpleFunction generateHashCodeFunction() {
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        generateSyntheticFunction$default(this, firSimpleFunctionBuilder, OperatorNameConventions.HASH_CODE, false, 2, null);
        firSimpleFunctionBuilder.setReturnTypeRef(new FirImplicitIntTypeRef(firSimpleFunctionBuilder.getSource()));
        return firSimpleFunctionBuilder.mo6809build();
    }

    private final FirSimpleFunction generateToStringFunction() {
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        generateSyntheticFunction$default(this, firSimpleFunctionBuilder, OperatorNameConventions.TO_STRING, false, 2, null);
        firSimpleFunctionBuilder.setReturnTypeRef(new FirImplicitStringTypeRef(firSimpleFunctionBuilder.getSource()));
        return firSimpleFunctionBuilder.mo6809build();
    }
}
