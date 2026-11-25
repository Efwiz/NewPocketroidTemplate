package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirUnstableSmartcastTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: Synthetics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\n\u0018\u0000 *2\u00020\u0001:\u0001*B1\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014H\u0002J@\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0016\u0010\u001d\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001f\u0012\u0004\u0012\u00020\u00170\u001eH\u0002J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120!H\u0016J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120!H\u0016J(\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u00122\u0016\u0010\u001d\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001f\u0012\u0004\u0012\u00020\u00170\u001eH\u0016J(\u0010%\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u0007H\u0002J\b\u0010(\u001a\u00020\u001cH\u0002J\f\u0010)\u001a\u00020\u001c*\u00020\u001aH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticPropertiesScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "syntheticNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticNamesProvider;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticNamesProvider;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "buildSyntheticProperty", "Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticProperty;", "propertyName", "Lorg/jetbrains/kotlin/name/Name;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "setter", "checkGetAndCreateSynthetic", "", "getterName", "getterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "needCheckForSetter", "", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getCallableNames", "", "getClassifierNames", "processPropertiesByName", "name", "setterTypeIsConsistentWithGetterType", "setterSymbol", "setterParameterType", "shouldSearchForJavaRecordComponents", "mayBeUsedAsGetterForSyntheticProperty", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertiesScope extends FirContainingNamesAwareScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirTypeScope baseScope;
    private final ConeKotlinType dispatchReceiverType;
    private final ReturnTypeCalculator returnTypeCalculator;
    private final FirSession session;
    private final FirSyntheticNamesProvider syntheticNamesProvider;

    public /* synthetic */ FirSyntheticPropertiesScope(FirSession firSession, FirTypeScope firTypeScope, ConeKotlinType coneKotlinType, FirSyntheticNamesProvider firSyntheticNamesProvider, ReturnTypeCalculator returnTypeCalculator, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firTypeScope, coneKotlinType, firSyntheticNamesProvider, returnTypeCalculator);
    }

    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: compiled from: Synthetics.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticPropertiesScope$Companion;", "", "()V", "createIfSyntheticNamesProviderIsDefined", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirSyntheticPropertiesScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "baseScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FirSyntheticPropertiesScope createIfSyntheticNamesProviderIsDefined$default(Companion companion, FirSession firSession, ConeKotlinType coneKotlinType, FirTypeScope firTypeScope, ReturnTypeCalculator returnTypeCalculator, int i, Object obj) {
            if ((i & 8) != 0) {
                returnTypeCalculator = null;
            }
            return companion.createIfSyntheticNamesProviderIsDefined(firSession, coneKotlinType, firTypeScope, returnTypeCalculator);
        }

        public final FirSyntheticPropertiesScope createIfSyntheticNamesProviderIsDefined(FirSession session, ConeKotlinType dispatchReceiverType, FirTypeScope baseScope, ReturnTypeCalculator returnTypeCalculator) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(dispatchReceiverType, "dispatchReceiverType");
            Intrinsics.checkNotNullParameter(baseScope, "baseScope");
            FirSyntheticNamesProvider syntheticNamesProvider = FirSyntheticNamesProviderKt.getSyntheticNamesProvider(session);
            if (syntheticNamesProvider == null) {
                return null;
            }
            return new FirSyntheticPropertiesScope(session, baseScope, dispatchReceiverType, syntheticNamesProvider, returnTypeCalculator, null);
        }
    }

    private FirSyntheticPropertiesScope(FirSession firSession, FirTypeScope firTypeScope, ConeKotlinType coneKotlinType, FirSyntheticNamesProvider firSyntheticNamesProvider, ReturnTypeCalculator returnTypeCalculator) {
        this.session = firSession;
        this.baseScope = firTypeScope;
        this.dispatchReceiverType = coneKotlinType;
        this.syntheticNamesProvider = firSyntheticNamesProvider;
        this.returnTypeCalculator = returnTypeCalculator;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(final Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<Name> listPossibleGetterNamesByPropertyName = this.syntheticNamesProvider.possibleGetterNamesByPropertyName(name2);
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        for (final Name name3 : listPossibleGetterNamesByPropertyName) {
            this.baseScope.processFunctionsByName(name3, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope.processPropertiesByName.1
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
                public final void invoke2(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    FirSyntheticPropertiesScope.this.checkGetAndCreateSynthetic(name2, name3, it, true, processor);
                    booleanRef.element = true;
                }
            });
        }
        if (booleanRef.element || !shouldSearchForJavaRecordComponents()) {
            return;
        }
        this.baseScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope.processPropertiesByName.2
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

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (Intrinsics.areEqual((Object) ClassMembersKt.isJavaRecordComponent((FirFunction) it.getFir()), (Object) true)) {
                    FirSyntheticPropertiesScope firSyntheticPropertiesScope = FirSyntheticPropertiesScope.this;
                    Name name4 = name2;
                    firSyntheticPropertiesScope.checkGetAndCreateSynthetic(name4, name4, it, false, processor);
                }
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean shouldSearchForJavaRecordComponents() {
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(this.dispatchReceiverType, this.session);
        if (regularClassSymbol == null) {
            return true;
        }
        Boolean boolIsJavaRecord = ClassMembersKt.isJavaRecord((FirRegularClass) regularClassSymbol.getFir());
        if (boolIsJavaRecord != null) {
            return boolIsJavaRecord.booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        Set<Name> callableNames = this.baseScope.getCallableNames();
        HashSet hashSet = new HashSet();
        Iterator<T> it = callableNames.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, this.syntheticNamesProvider.possiblePropertyNamesByAccessorName((Name) it.next()));
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return SetsKt.emptySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:63:0x00e7  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void checkGetAndCreateSynthetic(final Name propertyName, Name getterName, final FirNamedFunctionSymbol getterSymbol, boolean needCheckForSetter, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        FirSimpleFunction firSimpleFunction;
        ReturnTypeCalculator returnTypeCalculator;
        FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnTypeOrNull;
        FirSimpleFunction firSimpleFunction2 = (FirSimpleFunction) getterSymbol.getFir();
        if (firSimpleFunction2.getTypeParameters().isEmpty() && firSimpleFunction2.getValueParameters().isEmpty() && !firSimpleFunction2.getStatus().isStatic()) {
            FirTypeRef returnTypeRef = firSimpleFunction2.getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (type == null && needCheckForSetter && ((returnTypeCalculator = this.returnTypeCalculator) == null || (firResolvedTypeRefTryCalculateReturnTypeOrNull = returnTypeCalculator.tryCalculateReturnTypeOrNull(firSimpleFunction2)) == null || (type = firResolvedTypeRefTryCalculateReturnTypeOrNull.getType()) == null || (type instanceof ConeErrorType))) {
                type = null;
            }
            if ((type == null || !ConeBuiltinTypeUtilsKt.isUnit(type) || type.getAttributes().contains(CompilerConeAttributes.EnhancedNullability.INSTANCE)) && mayBeUsedAsGetterForSyntheticProperty(getterSymbol)) {
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                if (needCheckForSetter && type != null) {
                    this.baseScope.processFunctionsByName(this.syntheticNamesProvider.setterNameByGetterName(getterName), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope.checkGetAndCreateSynthetic.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                            invoke2(firNamedFunctionSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r5v1, types: [T, org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirNamedFunctionSymbol setterSymbol) {
                            ConeKotlinType type2;
                            Intrinsics.checkNotNullParameter(setterSymbol, "setterSymbol");
                            if (objectRef.element != null) {
                                return;
                            }
                            FirSimpleFunction firSimpleFunction3 = (FirSimpleFunction) setterSymbol.getFir();
                            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.singleOrNull((List) firSimpleFunction3.getValueParameters());
                            if (firValueParameter == null || !firSimpleFunction3.getTypeParameters().isEmpty() || firSimpleFunction3.getStatus().isStatic()) {
                                return;
                            }
                            FirTypeRef returnTypeRef2 = firValueParameter.getReturnTypeRef();
                            FirResolvedTypeRef firResolvedTypeRef2 = returnTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef2 : null;
                            if (firResolvedTypeRef2 == null || (type2 = firResolvedTypeRef2.getType()) == null || !this.setterTypeIsConsistentWithGetterType(propertyName, getterSymbol, setterSymbol, type2)) {
                                return;
                            }
                            objectRef.element = setterSymbol.getFir();
                        }
                    });
                }
                FirSyntheticProperty firSyntheticPropertyBuildSyntheticProperty = buildSyntheticProperty(propertyName, firSimpleFunction2, (FirSimpleFunction) objectRef.element);
                FirSimpleFunction firSimpleFunction3 = firSimpleFunction2;
                FirSimpleFunction firSimpleFunction4 = (FirSimpleFunction) ((ClassMembersKt.isSubstitutionOverride(firSimpleFunction3) || (firSimpleFunction3.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firSimpleFunction3) : null);
                if (firSimpleFunction4 != null) {
                    FirSyntheticProperty firSyntheticProperty = firSyntheticPropertyBuildSyntheticProperty;
                    FirSimpleFunction firSimpleFunction5 = (FirSimpleFunction) objectRef.element;
                    if (firSimpleFunction5 != null) {
                        FirSimpleFunction firSimpleFunction6 = firSimpleFunction5;
                        firSimpleFunction = (FirSimpleFunction) ((ClassMembersKt.isSubstitutionOverride(firSimpleFunction6) || (firSimpleFunction6.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firSimpleFunction6) : null);
                        if (firSimpleFunction == null) {
                            firSimpleFunction = (FirSimpleFunction) objectRef.element;
                        }
                    } else {
                        firSimpleFunction = (FirSimpleFunction) objectRef.element;
                    }
                    ClassMembersKt.setOriginalForSubstitutionOverrideAttr(firSyntheticProperty, buildSyntheticProperty(propertyName, firSimpleFunction4, firSimpleFunction));
                }
                FirSyntheticPropertySymbol symbol = firSyntheticPropertyBuildSyntheticProperty.getSymbol();
                FirTypeScope firTypeScope = this.baseScope;
                FirUnstableSmartcastTypeScope firUnstableSmartcastTypeScope = firTypeScope instanceof FirUnstableSmartcastTypeScope ? (FirUnstableSmartcastTypeScope) firTypeScope : null;
                if (firUnstableSmartcastTypeScope != null && firUnstableSmartcastTypeScope.isSymbolFromUnstableSmartcast(getterSymbol)) {
                    firUnstableSmartcastTypeScope.markSymbolFromUnstableSmartcast(symbol);
                }
                processor.invoke(symbol);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirSyntheticProperty buildSyntheticProperty(final Name propertyName, final FirSimpleFunction getter, final FirSimpleFunction setter) {
        FqName relativeClassName;
        FqName packageName;
        ClassId classId;
        ClassId classId2;
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) getter.getSymbol().getFir();
        while (true) {
            relativeClassName = null;
            if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
                break;
            }
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol != null) {
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirNamedFunctionSymbol) symbol);
            if (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull == null || (classId2 = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId()) == null || (packageName = classId2.getPackageFqName()) == null) {
                packageName = getter.getSymbol().getCallableId().getPackageName();
            }
            final FqName fqName = packageName;
            Intrinsics.checkNotNull(fqName);
            if (coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null && (classId = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId()) != null) {
                relativeClassName = classId.getRelativeClassName();
            }
            final FqName fqName2 = relativeClassName;
            return FirSyntheticPropertyBuilderKt.buildSyntheticProperty(new Function1<FirSyntheticPropertyBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope.buildSyntheticProperty.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirSyntheticPropertyBuilder firSyntheticPropertyBuilder) {
                    invoke2(firSyntheticPropertyBuilder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirSyntheticPropertyBuilder buildSyntheticProperty) {
                    Intrinsics.checkNotNullParameter(buildSyntheticProperty, "$this$buildSyntheticProperty");
                    buildSyntheticProperty.setModuleData(FirModuleDataKt.getModuleData(FirSyntheticPropertiesScope.this.getSession()));
                    buildSyntheticProperty.setName(propertyName);
                    buildSyntheticProperty.setSymbol(new FirSimpleSyntheticPropertySymbol(new CallableId(fqName, fqName2, propertyName, null, 8, null), getter.getSymbol().getCallableId()));
                    buildSyntheticProperty.setDelegateGetter(getter);
                    buildSyntheticProperty.setDelegateSetter(setter);
                    buildSyntheticProperty.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAccessors(FirSyntheticPropertiesScope.this.getSession(), getter, setter));
                }
            });
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean setterTypeIsConsistentWithGetterType(Name propertyName, FirNamedFunctionSymbol getterSymbol, FirNamedFunctionSymbol setterSymbol, ConeKotlinType setterParameterType) {
        ConeKotlinType type = getterSymbol.getResolvedReturnTypeRef().getType();
        ConeKotlinType coneKotlinType = setterParameterType;
        if (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(this.session), type, coneKotlinType, false, 8, null)) {
            return true;
        }
        if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(this.session), (KotlinTypeMarker) type, (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null)) {
            return setterTypeIsConsistentWithGetterType$processOverrides(this, propertyName, setterSymbol, null) || setterTypeIsConsistentWithGetterType$processOverrides(this, propertyName, getterSymbol, setterSymbol);
        }
        return false;
    }

    private static final boolean setterTypeIsConsistentWithGetterType$processOverrides(final FirSyntheticPropertiesScope firSyntheticPropertiesScope, final Name name2, FirNamedFunctionSymbol firNamedFunctionSymbol, final FirNamedFunctionSymbol firNamedFunctionSymbol2) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        firSyntheticPropertiesScope.baseScope.processDirectOverriddenFunctionsWithBaseScope(firNamedFunctionSymbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope$setterTypeIsConsistentWithGetterType$processOverrides$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol symbol, FirTypeScope scope) {
                FirSimpleFunction delegate;
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(scope, "scope");
                if (booleanRef.element) {
                    return ProcessorAction.STOP;
                }
                ConeSimpleKotlinType dispatchReceiverType = symbol.getDispatchReceiverType();
                if (dispatchReceiverType == null) {
                    return ProcessorAction.NEXT;
                }
                List<FirVariableSymbol<?>> properties = FirScopeKt.getProperties(new FirSyntheticPropertiesScope(firSyntheticPropertiesScope.getSession(), scope, dispatchReceiverType, firSyntheticPropertiesScope.syntheticNamesProvider, firSyntheticPropertiesScope.returnTypeCalculator, null), name2);
                FirNamedFunctionSymbol firNamedFunctionSymbol3 = firNamedFunctionSymbol2;
                if (!(properties instanceof Collection) || !properties.isEmpty()) {
                    Iterator<T> it = properties.iterator();
                    while (it.hasNext()) {
                        FirVariable firVariable = (FirVariable) ((FirVariableSymbol) it.next()).getFir();
                        if (firVariable instanceof FirSyntheticProperty) {
                            FirSyntheticPropertyAccessor setter = ((FirSyntheticProperty) firVariable).getSetter();
                            if (Intrinsics.areEqual((setter == null || (delegate = setter.getDelegate()) == null) ? null : delegate.getSymbol(), firNamedFunctionSymbol3 == null ? symbol : firNamedFunctionSymbol3)) {
                                booleanRef.element = true;
                                return ProcessorAction.STOP;
                            }
                        }
                    }
                }
                return ProcessorAction.NEXT;
            }
        });
        return booleanRef.element;
    }

    private final boolean mayBeUsedAsGetterForSyntheticProperty(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        FirScopeKt.processOverriddenFunctionsAndSelf(this.baseScope, firNamedFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticPropertiesScope.mayBeUsedAsGetterForSyntheticProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) it.getFir();
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                    }
                    if (originalForSubstitutionOverrideAttr == null) {
                        break;
                    }
                    firCallableDeclaration = originalForSubstitutionOverrideAttr;
                }
                FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
                if (symbol != null) {
                    FirSimpleFunction firSimpleFunction = (FirSimpleFunction) ((FirNamedFunctionSymbol) symbol).getFir();
                    if (Intrinsics.areEqual(firSimpleFunction.getOrigin(), FirDeclarationOrigin.Enhancement.INSTANCE)) {
                        booleanRef.element = true;
                    }
                    if (Intrinsics.areEqual((Object) DeprecationUtilsKt.isHiddenEverywhereBesideSuperCalls(firSimpleFunction), (Object) true)) {
                        booleanRef2.element = true;
                    }
                    return ProcessorAction.NEXT;
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
            }
        });
        return booleanRef.element && !booleanRef2.element;
    }
}
