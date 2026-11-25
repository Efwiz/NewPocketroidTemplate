package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirOverrideCheckerKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDelegatedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0001\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u000bH\u0002J&\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002J&\u0010$\u001a\u00020\u001f2\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020%0\"H\u0002J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J.\u0010(\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000f2\u001c\u0010)\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030+\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u001f0*H\u0016J\u001c\u0010-\u001a\u00020\u001f2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u001f0.H\u0016J$\u00100\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000f2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u001f0.H\u0016J(\u00101\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u000f2\u0016\u0010)\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u000302\u0012\u0004\u0012\u00020\u001f0.H\u0016R!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0015\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDelegatedMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "declaredMemberScope", "delegateFields", "", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Ljava/util/List;)V", "callableNamesLazy", "", "Lorg/jetbrains/kotlin/name/Name;", "getCallableNamesLazy", "()Ljava/util/Set;", "callableNamesLazy$delegate", "Lkotlin/Lazy;", "classifierNamesLazy", "getClassifierNamesLazy", "classifierNamesLazy$delegate", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "buildScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "delegateField", "collectFunctionsFromSpecificField", "", "name", "result", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "collectPropertiesFromSpecificField", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getCallableNames", "getClassifierNames", "processClassifiersByNameWithSubstitution", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedMemberScope extends FirContainingNamesAwareScope {

    /* JADX INFO: renamed from: callableNamesLazy$delegate, reason: from kotlin metadata */
    private final Lazy callableNamesLazy;

    /* JADX INFO: renamed from: classifierNamesLazy$delegate, reason: from kotlin metadata */
    private final Lazy classifierNamesLazy;
    private final FirClass containingClass;
    private final FirContainingNamesAwareScope declaredMemberScope;
    private final List<FirField> delegateFields;
    private final ConeClassLikeType dispatchReceiverType;
    private final FirOverrideChecker overrideChecker;
    private final ScopeSession scopeSession;
    private final FirSession session;

    /* JADX WARN: Multi-variable type inference failed */
    public FirDelegatedMemberScope(FirSession session, ScopeSession scopeSession, FirClass containingClass, FirContainingNamesAwareScope declaredMemberScope, List<? extends FirField> delegateFields) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        Intrinsics.checkNotNullParameter(delegateFields, "delegateFields");
        this.session = session;
        this.scopeSession = scopeSession;
        this.containingClass = containingClass;
        this.declaredMemberScope = declaredMemberScope;
        this.delegateFields = delegateFields;
        this.dispatchReceiverType = ScopeUtilsKt.defaultType(containingClass);
        this.overrideChecker = FirOverrideCheckerKt.getFirOverrideChecker(session);
        this.callableNamesLazy = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope$callableNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Set<Name> setEmptySet;
                FirDelegatedMemberScope firDelegatedMemberScope = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                setCreateSetBuilder.addAll(firDelegatedMemberScope.declaredMemberScope.getCallableNames());
                Iterator it = firDelegatedMemberScope.delegateFields.iterator();
                while (it.hasNext()) {
                    FirTypeScope firTypeScopeBuildScope = firDelegatedMemberScope.buildScope((FirField) it.next());
                    if (firTypeScopeBuildScope == null || (setEmptySet = firTypeScopeBuildScope.getCallableNames()) == null) {
                        setEmptySet = SetsKt.emptySet();
                    }
                    CollectionsKt.addAll(setCreateSetBuilder, setEmptySet);
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
        this.classifierNamesLazy = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope$classifierNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Set<Name> setEmptySet;
                FirDelegatedMemberScope firDelegatedMemberScope = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                setCreateSetBuilder.addAll(firDelegatedMemberScope.declaredMemberScope.getClassifierNames());
                Iterator it = firDelegatedMemberScope.delegateFields.iterator();
                while (it.hasNext()) {
                    FirTypeScope firTypeScopeBuildScope = firDelegatedMemberScope.buildScope((FirField) it.next());
                    if (firTypeScopeBuildScope == null || (setEmptySet = firTypeScopeBuildScope.getClassifierNames()) == null) {
                        setEmptySet = SetsKt.emptySet();
                    }
                    CollectionsKt.addAll(setCreateSetBuilder, setEmptySet);
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processFunctionsByName(name2, processor);
        ArrayList arrayList = new ArrayList();
        Iterator<FirField> it = this.delegateFields.iterator();
        while (it.hasNext()) {
            collectFunctionsFromSpecificField(it.next(), name2, arrayList);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            processor.invoke(it2.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirTypeScope buildScope(FirField delegateField) {
        return ScopeUtilsKt.scope(delegateField.getSymbol().getResolvedReturnType(), this.session, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, null);
    }

    private final void collectFunctionsFromSpecificField(final FirField delegateField, final Name name2, final List<FirNamedFunctionSymbol> result) {
        FirTypeScope firTypeScopeBuildScope = buildScope(delegateField);
        if (firTypeScopeBuildScope == null) {
            return;
        }
        firTypeScopeBuildScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope.collectFunctionsFromSpecificField.1
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
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                Object next;
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                FirSimpleFunction firSimpleFunction = (FirSimpleFunction) functionSymbol.getFir();
                if (!FirDelegatedMemberScopeKt.isPublicInAny(firSimpleFunction) || firSimpleFunction.getStatus().getModality() == Modality.ABSTRACT) {
                    FirSimpleFunction firSimpleFunction2 = firSimpleFunction;
                    if (firSimpleFunction2.getStatus().getModality() == Modality.FINAL || Intrinsics.areEqual(firSimpleFunction2.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                        return;
                    }
                    List<FirNamedFunctionSymbol> functions = FirScopeKt.getFunctions(FirDelegatedMemberScope.this.declaredMemberScope, name2);
                    FirDelegatedMemberScope firDelegatedMemberScope = FirDelegatedMemberScope.this;
                    if (!(functions instanceof Collection) || !functions.isEmpty()) {
                        Iterator<T> it = functions.iterator();
                        while (it.hasNext()) {
                            if (firDelegatedMemberScope.overrideChecker.isOverriddenFunction((FirSimpleFunction) ((FirNamedFunctionSymbol) it.next()).getFir(), firSimpleFunction)) {
                                return;
                            }
                        }
                    }
                    List<FirNamedFunctionSymbol> list = result;
                    FirDelegatedMemberScope firDelegatedMemberScope2 = FirDelegatedMemberScope.this;
                    Iterator<T> it2 = list.iterator();
                    do {
                        if (!it2.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it2.next();
                    } while (!firDelegatedMemberScope2.overrideChecker.isOverriddenFunction((FirSimpleFunction) ((FirNamedFunctionSymbol) next).getFir(), firSimpleFunction));
                    FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) next;
                    if (firNamedFunctionSymbol != null) {
                        FirDelegatedMemberScopeKt.setMultipleDelegatesWithTheSameSignature((FirCallableDeclaration) firNamedFunctionSymbol.getFir(), true);
                        return;
                    }
                    FirSimpleFunction firSimpleFunctionCreateCopyForFirFunction$default = FirFakeOverrideGenerator.createCopyForFirFunction$default(FirFakeOverrideGenerator.INSTANCE, new FirNamedFunctionSymbol(new CallableId(FirDeclarationUtilKt.getClassId(FirDelegatedMemberScope.this.containingClass), functionSymbol.getName())), firSimpleFunction, FirDelegatedMemberScope.this.dispatchReceiverType.getLookupTag(), FirDelegatedMemberScope.this.session, FirDeclarationOrigin.Delegated.INSTANCE, false, FirDelegatedMemberScope.this.dispatchReceiverType, null, null, null, null, null, Modality.OPEN, null, null, 28576, null);
                    FirDelegatedMemberScope firDelegatedMemberScope3 = FirDelegatedMemberScope.this;
                    ClassMembersKt.setDelegatedWrapperData(firSimpleFunctionCreateCopyForFirFunction$default, new DelegatedWrapperData((FirCallableDeclaration) functionSymbol.getFir(), firDelegatedMemberScope3.containingClass.getSymbol().getLookupTag(), delegateField));
                    result.add(firSimpleFunctionCreateCopyForFirFunction$default.getSymbol());
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processPropertiesByName(name2, processor);
        ArrayList arrayList = new ArrayList();
        Iterator<FirField> it = this.delegateFields.iterator();
        while (it.hasNext()) {
            collectPropertiesFromSpecificField(it.next(), name2, arrayList);
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            processor.invoke(it2.next());
        }
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

    private final void collectPropertiesFromSpecificField(final FirField delegateField, final Name name2, final List<FirPropertySymbol> result) {
        FirTypeScope firTypeScopeBuildScope = buildScope(delegateField);
        if (firTypeScopeBuildScope == null) {
            return;
        }
        firTypeScopeBuildScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope.collectPropertiesFromSpecificField.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                Object next;
                Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                if (propertySymbol instanceof FirPropertySymbol) {
                    FirVariableSymbol<?> firVariableSymbol = propertySymbol;
                    if (firVariableSymbol.getResolvedStatus().getModality() == Modality.FINAL || Intrinsics.areEqual(firVariableSymbol.getResolvedStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                        return;
                    }
                    FirPropertySymbol firPropertySymbol = (FirPropertySymbol) propertySymbol;
                    final FirProperty firProperty = (FirProperty) firPropertySymbol.getFir();
                    final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                    FirContainingNamesAwareScope firContainingNamesAwareScope = FirDelegatedMemberScope.this.declaredMemberScope;
                    Name name3 = name2;
                    final FirDelegatedMemberScope firDelegatedMemberScope = FirDelegatedMemberScope.this;
                    firContainingNamesAwareScope.processPropertiesByName(name3, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope.collectPropertiesFromSpecificField.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol2) {
                            invoke2(firVariableSymbol2);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            if ((it instanceof FirPropertySymbol) && firDelegatedMemberScope.overrideChecker.isOverriddenProperty((FirCallableDeclaration) ((FirPropertySymbol) it).getFir(), firProperty)) {
                                booleanRef.element = true;
                            }
                        }
                    });
                    if (booleanRef.element) {
                        return;
                    }
                    List<FirPropertySymbol> list = result;
                    FirDelegatedMemberScope firDelegatedMemberScope2 = FirDelegatedMemberScope.this;
                    Iterator<T> it = list.iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!firDelegatedMemberScope2.overrideChecker.isOverriddenProperty((FirCallableDeclaration) ((FirPropertySymbol) next).getFir(), firProperty));
                    FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) next;
                    if (firPropertySymbol2 != null) {
                        FirDelegatedMemberScopeKt.setMultipleDelegatesWithTheSameSignature((FirCallableDeclaration) firPropertySymbol2.getFir(), true);
                        return;
                    }
                    FirProperty firPropertyCreateCopyForFirProperty$default = FirFakeOverrideGenerator.createCopyForFirProperty$default(FirFakeOverrideGenerator.INSTANCE, new FirPropertySymbol(new CallableId(FirDeclarationUtilKt.getClassId(FirDelegatedMemberScope.this.containingClass), propertySymbol.getName())), firProperty, FirDelegatedMemberScope.this.dispatchReceiverType.getLookupTag(), FirDelegatedMemberScope.this.session, FirDeclarationOrigin.Delegated.INSTANCE, false, FirDelegatedMemberScope.this.dispatchReceiverType, null, null, null, null, Modality.OPEN, null, null, 14240, null);
                    FirDelegatedMemberScope firDelegatedMemberScope3 = FirDelegatedMemberScope.this;
                    ClassMembersKt.setDelegatedWrapperData(firPropertyCreateCopyForFirProperty$default, new DelegatedWrapperData((FirCallableDeclaration) firPropertySymbol.getFir(), firDelegatedMemberScope3.containingClass.getSymbol().getLookupTag(), delegateField));
                    result.add(firPropertyCreateCopyForFirProperty$default.getSymbol());
                }
            }
        });
    }

    private final Set<Name> getCallableNamesLazy() {
        return (Set) this.callableNamesLazy.getValue();
    }

    private final Set<Name> getClassifierNamesLazy() {
        return (Set) this.classifierNamesLazy.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return getCallableNamesLazy();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return getClassifierNamesLazy();
    }
}
