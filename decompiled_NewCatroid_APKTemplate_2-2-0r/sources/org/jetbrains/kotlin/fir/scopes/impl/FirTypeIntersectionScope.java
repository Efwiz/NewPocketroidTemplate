package org.jetbrains.kotlin.fir.scopes.impl;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeIntersectionScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 E2\u00020\u0001:\u0001EB-\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0!H\u0016J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000e0!H\u0016J-\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H$0\u001f0\u001e\"\f\b\u0000\u0010$*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010%\u001a\u0002H$¢\u0006\u0002\u0010&Jf\u0010'\u001a\u00020(\"\f\b\u0000\u0010)*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010*\u001a\u00020\u000e2\u0014\b\b\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020(0,2/\u0010-\u001a+\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u000e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020(0,\u0012\u0004\u0012\u00020(0.¢\u0006\u0002\b0H\u0082\bJ.\u00101\u001a\u00020(2\u0006\u0010*\u001a\u00020\u000e2\u001c\u0010+\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u000303\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020(02H\u0016Jt\u00105\u001a\u000206\"\f\b\u0000\u0010)*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u00107\u001a\u0002H)2\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002060225\u00108\u001a1\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H)\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020602\u0012\u0004\u0012\u0002060.¢\u0006\u0002\b0H\u0002¢\u0006\u0002\u00109J*\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020<2\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020602H\u0016J*\u0010=\u001a\u0002062\u0006\u0010>\u001a\u00020?2\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020602H\u0016J$\u0010@\u001a\u00020(2\u0006\u0010*\u001a\u00020\u000e2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020(0,H\u0016J(\u0010A\u001a\u00020(2\u0006\u0010*\u001a\u00020\u000e2\u0016\u0010+\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030B\u0012\u0004\u0012\u00020(0,H\u0016J\b\u0010C\u001a\u00020DH\u0016R!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R+\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u0014j\b\u0012\u0004\u0012\u00020\u000e`\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0012\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u001b\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d0\u001f0\u001e0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirOverrideScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "scopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;)V", "callableNamesCached", "", "Lorg/jetbrains/kotlin/name/Name;", "getCallableNamesCached", "()Ljava/util/Set;", "callableNamesCached$delegate", "Lkotlin/Lazy;", "classifiersNamesCached", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getClassifiersNamesCached", "()Ljava/util/HashSet;", "classifiersNamesCached$delegate", "intersectionContext", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "overriddenSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "getCallableNames", "", "getClassifierNames", "getDirectOverriddenSymbols", ExifInterface.LATITUDE_SOUTH, NativeSymbol.TYPE_NAME, "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Ljava/util/Collection;", "processCallablesByName", "", "D", "name", "processor", "Lkotlin/Function1;", "processCallables", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lkotlin/ExtensionFunctionType;", "processClassifiersByNameWithSubstitution", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDirectOverriddenCallablesWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "callableSymbol", "processDirectOverriddenInBaseScope", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "processDirectOverriddenFunctionsWithBaseScope", "functionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "toString", "", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeIntersectionScope extends AbstractFirOverrideScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: callableNamesCached$delegate, reason: from kotlin metadata */
    private final Lazy callableNamesCached;

    /* JADX INFO: renamed from: classifiersNamesCached$delegate, reason: from kotlin metadata */
    private final Lazy classifiersNamesCached;
    private final FirTypeIntersectionScopeContext intersectionContext;
    private final Map<FirCallableSymbol<?>, Collection<MemberWithBaseScope<FirCallableSymbol<?>>>> overriddenSymbols;
    private final List<FirTypeScope> scopes;

    public /* synthetic */ FirTypeIntersectionScope(FirSession firSession, FirOverrideChecker firOverrideChecker, List list, ConeSimpleKotlinType coneSimpleKotlinType, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firOverrideChecker, list, coneSimpleKotlinType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirTypeIntersectionScope(FirSession firSession, FirOverrideChecker firOverrideChecker, List<? extends FirTypeScope> list, ConeSimpleKotlinType coneSimpleKotlinType) {
        super(firSession, firOverrideChecker);
        this.scopes = list;
        this.intersectionContext = new FirTypeIntersectionScopeContext(firSession, firOverrideChecker, list, coneSimpleKotlinType, false);
        this.overriddenSymbols = new LinkedHashMap();
        this.callableNamesCached = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope$callableNamesCached$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                List list2 = this.this$0.scopes;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((FirTypeScope) it.next()).getCallableNames());
                }
                return linkedHashSet;
            }
        });
        this.classifiersNamesCached = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<HashSet<Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope$classifiersNamesCached$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final HashSet<Name> invoke() {
                List list2 = this.this$0.scopes;
                HashSet<Name> hashSet = new HashSet<>();
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(hashSet, ((FirTypeScope) it.next()).getClassifierNames());
                }
                return hashSet;
            }
        });
    }

    private final Set<Name> getCallableNamesCached() {
        return (Set) this.callableNamesCached.getValue();
    }

    private final HashSet<Name> getClassifiersNamesCached() {
        return (HashSet) this.classifiersNamesCached.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            FirTypeIntersectionScopeContext firTypeIntersectionScopeContext = this.intersectionContext;
            List<FirTypeScope> scopes = firTypeIntersectionScopeContext.getScopes();
            ArrayList arrayList = new ArrayList();
            for (FirTypeScope firTypeScope : scopes) {
                ArrayList arrayList2 = new ArrayList();
                firTypeScope.processFunctionsByName(name2, new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList2));
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
                if (pair != null) {
                    arrayList.add(pair);
                }
            }
            for (FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection : firTypeIntersectionScopeContext.convertGroupedCallablesToIntersectionResults(arrayList)) {
                FirCallableSymbol<?> chosenSymbol = resultOfIntersection.getChosenSymbol();
                this.overriddenSymbols.put(chosenSymbol, resultOfIntersection.getOverriddenMembers());
                processor.invoke(chosenSymbol);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            FirTypeIntersectionScopeContext firTypeIntersectionScopeContext = this.intersectionContext;
            List<FirTypeScope> scopes = firTypeIntersectionScopeContext.getScopes();
            ArrayList arrayList = new ArrayList();
            for (FirTypeScope firTypeScope : scopes) {
                ArrayList arrayList2 = new ArrayList();
                firTypeScope.processPropertiesByName(name2, new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList2));
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
                if (pair != null) {
                    arrayList.add(pair);
                }
            }
            for (FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection : firTypeIntersectionScopeContext.convertGroupedCallablesToIntersectionResults(arrayList)) {
                FirCallableSymbol<?> chosenSymbol = resultOfIntersection.getChosenSymbol();
                this.overriddenSymbols.put(chosenSymbol, resultOfIntersection.getOverriddenMembers());
                processor.invoke(chosenSymbol);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getClassifierNames().contains(name2)) {
            this.intersectionContext.processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    public final <S extends FirCallableSymbol<?>> Collection<MemberWithBaseScope<S>> getDirectOverriddenSymbols(S symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        MemberWithBaseScope<FirCallableSymbol<?>> valueIfComputed = this.intersectionContext.getIntersectionOverrides().getValueIfComputed(symbol);
        List listEmptyList = this.overriddenSymbols.get(symbol);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        List listEmptyList2 = valueIfComputed != null ? this.overriddenSymbols.get(valueIfComputed.getMember()) : null;
        if (listEmptyList2 == null) {
            listEmptyList2 = CollectionsKt.emptyList();
        }
        List listPlus = CollectionsKt.plus((Collection) listEmptyList, (Iterable) listEmptyList2);
        Intrinsics.checkNotNull(listPlus, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope<S of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope.getDirectOverriddenSymbols>>");
        return listPlus;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope$processDirectOverriddenFunctionsWithBaseScope$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirTypeIntersectionScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenFunctionsWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenFunctionsWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirNamedFunctionSymbol p1, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenFunctionsWithBaseScope(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return processDirectOverriddenCallablesWithBaseScope(functionSymbol, processor, AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScope$processDirectOverriddenPropertiesWithBaseScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirTypeIntersectionScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46151 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C46151 INSTANCE = new C46151();

        C46151() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenPropertiesWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenPropertiesWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenPropertiesWithBaseScope(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return processDirectOverriddenCallablesWithBaseScope(propertySymbol, processor, C46151.INSTANCE);
    }

    private final <D extends FirCallableSymbol<?>> ProcessorAction processDirectOverriddenCallablesWithBaseScope(D callableSymbol, Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction> processor, Function3<? super FirTypeScope, ? super D, ? super Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverriddenInBaseScope) {
        for (MemberWithBaseScope memberWithBaseScope : getDirectOverriddenSymbols(callableSymbol)) {
            FirCallableSymbol firCallableSymbolComponent1 = memberWithBaseScope.component1();
            FirTypeScope baseScope = memberWithBaseScope.getBaseScope();
            if (firCallableSymbolComponent1 == callableSymbol) {
                if (processDirectOverriddenInBaseScope.invoke(baseScope, callableSymbol, processor).not()) {
                    return ProcessorAction.STOP;
                }
            } else if (processor.invoke(firCallableSymbolComponent1, baseScope).not()) {
                return ProcessorAction.STOP;
            }
        }
        return ProcessorAction.NEXT;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return getCallableNamesCached();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return getClassifiersNamesCached();
    }

    public String toString() {
        return "Intersection of [" + CollectionsKt.joinToString$default(this.scopes, ", ", null, null, 0, null, null, 62, null) + ']';
    }

    /* JADX INFO: compiled from: FirTypeIntersectionScope.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScope$Companion;", "", "()V", "prepareIntersectionScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "scopes", "", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirTypeScope prepareIntersectionScope(FirSession session, FirOverrideChecker overrideChecker, List<? extends FirTypeScope> scopes, ConeSimpleKotlinType dispatchReceiverType) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(overrideChecker, "overrideChecker");
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            Intrinsics.checkNotNullParameter(dispatchReceiverType, "dispatchReceiverType");
            FirTypeScope firTypeScope = (FirTypeScope) CollectionsKt.singleOrNull((List) scopes);
            if (firTypeScope != null) {
                return firTypeScope;
            }
            if (scopes.isEmpty()) {
                return FirTypeScope.Empty.INSTANCE;
            }
            return new FirTypeIntersectionScope(session, overrideChecker, scopes, dispatchReceiverType, null);
        }
    }
}
