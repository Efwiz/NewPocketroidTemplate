package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.impl.DeduplicatingDiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.impl.DeduplicatingDiagnosticReporterKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverrideFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirImplementationMismatchChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J0\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J<\u0010\u0012\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J4\u0010\u0018\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00192\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J,\u0010\u001a\u001a\u00020\u001b*\u00020\u001b2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImplementationMismatchChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkConflictingMembers", "containingClass", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "name", "Lorg/jetbrains/kotlin/name/Name;", "checkInheritanceClash", "typeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "classScope", "checkValOverridesVar", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "substituteTypeParameters", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "fromDeclaration", "toDeclaration", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplementationMismatchChecker extends FirDeclarationChecker<FirClass> {
    public static final FirImplementationMismatchChecker INSTANCE = new FirImplementationMismatchChecker();

    private FirImplementationMismatchChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(final FirClass declaration, final CheckerContext context, DiagnosticReporter reporter) {
        ClassKind classKind;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (source == null) {
            return;
        }
        KtSourceElementKind kind = source.getKind();
        if (!(kind instanceof KtFakeSourceElementKind) || Intrinsics.areEqual(kind, KtFakeSourceElementKind.EnumInitializer.INSTANCE)) {
            if (((declaration instanceof FirRegularClass) && declaration.getStatus().isExpect()) || (classKind = declaration.getClassKind()) == ClassKind.ANNOTATION_CLASS || classKind == ClassKind.ENUM_CLASS) {
                return;
            }
            final TypeCheckerState typeCheckerStateNewTypeCheckerState = TypeComponentsKt.getTypeContext(context.getSession()).newTypeCheckerState(false, false);
            final FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
            final DeduplicatingDiagnosticReporter deduplicatingDiagnosticReporterDeduplicating = DeduplicatingDiagnosticReporterKt.deduplicating(reporter);
            for (Name name2 : firTypeScopeUnsubstitutedScope.getCallableNames()) {
                firTypeScopeUnsubstitutedScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImplementationMismatchChecker.check.1
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
                    public final void invoke2(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirImplementationMismatchChecker.INSTANCE.checkInheritanceClash(declaration, context, deduplicatingDiagnosticReporterDeduplicating, typeCheckerStateNewTypeCheckerState, it, firTypeScopeUnsubstitutedScope);
                    }
                });
                firTypeScopeUnsubstitutedScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImplementationMismatchChecker.check.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirImplementationMismatchChecker.INSTANCE.checkInheritanceClash(declaration, context, deduplicatingDiagnosticReporterDeduplicating, typeCheckerStateNewTypeCheckerState, it, firTypeScopeUnsubstitutedScope);
                        FirImplementationMismatchChecker.INSTANCE.checkValOverridesVar(declaration, context, deduplicatingDiagnosticReporterDeduplicating, it, firTypeScopeUnsubstitutedScope);
                    }
                });
                checkConflictingMembers(declaration, context, deduplicatingDiagnosticReporterDeduplicating, firTypeScopeUnsubstitutedScope, name2);
            }
        }
    }

    private static final void checkInheritanceClash$reportTypeMismatch(DiagnosticReporter diagnosticReporter, FirClass firClass, CheckerContext checkerContext, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, boolean z) {
        KtDiagnosticFactory2<FirCallableSymbol<?>, FirCallableSymbol<?>> return_type_mismatch_on_inheritance;
        if ((firCallableSymbol instanceof FirPropertySymbol) && (firCallableSymbol2 instanceof FirPropertySymbol)) {
            if (((FirPropertySymbol) firCallableSymbol).isVar() || ((FirPropertySymbol) firCallableSymbol2).isVar()) {
                return_type_mismatch_on_inheritance = FirErrors.INSTANCE.getVAR_TYPE_MISMATCH_ON_INHERITANCE();
            } else if (z) {
                return_type_mismatch_on_inheritance = FirErrors.INSTANCE.getPROPERTY_TYPE_MISMATCH_BY_DELEGATION();
            } else {
                return_type_mismatch_on_inheritance = FirErrors.INSTANCE.getPROPERTY_TYPE_MISMATCH_ON_INHERITANCE();
            }
        } else if (z) {
            return_type_mismatch_on_inheritance = FirErrors.INSTANCE.getRETURN_TYPE_MISMATCH_BY_DELEGATION();
        } else {
            return_type_mismatch_on_inheritance = FirErrors.INSTANCE.getRETURN_TYPE_MISMATCH_ON_INHERITANCE();
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firClass.getSource(), return_type_mismatch_on_inheritance, firCallableSymbol, firCallableSymbol2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private static final boolean checkInheritanceClash$canOverride(CheckerContext checkerContext, TypeCheckerState typeCheckerState, FirCallableSymbol<?> firCallableSymbol, ConeKotlinType coneKotlinType, FirCallableSymbol<?> firCallableSymbol2, ConeKotlinType coneKotlinType2) {
        ConeKotlinType coneKotlinTypeSubstituteTypeParameters = INSTANCE.substituteTypeParameters(coneKotlinType, firCallableSymbol, firCallableSymbol2, checkerContext);
        if ((firCallableSymbol2 instanceof FirPropertySymbol) && ((FirPropertySymbol) firCallableSymbol2).isVar()) {
            return AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerState, coneKotlinTypeSubstituteTypeParameters, coneKotlinType2);
        }
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerState, (KotlinTypeMarker) coneKotlinTypeSubstituteTypeParameters, (KotlinTypeMarker) coneKotlinType2, false, 8, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void checkInheritanceClash(FirClass containingClass, CheckerContext context, DiagnosticReporter reporter, TypeCheckerState typeCheckerState, FirCallableSymbol<?> symbol, FirTypeScope classScope) {
        List intersections;
        FirCallableSymbol<?> next;
        Pair pair;
        boolean z;
        boolean z2;
        FirCallableSymbol<?> firCallableSymbol;
        ConeClassLikeLookupTag containingClass2;
        Object next2;
        List intersections2;
        if (ClassMembersKt.isSubstitutionOverride(symbol)) {
            return;
        }
        Object obj = null;
        if (ClassMembersKt.getDelegatedWrapperData(symbol) != null) {
            List directOverriddenMembers$default = FirTypeScopeKt.getDirectOverriddenMembers$default(classScope, symbol, false, 2, null);
            Iterator it = directOverriddenMembers$default.iterator();
            do {
                if (!it.hasNext()) {
                    next2 = null;
                    break;
                }
                next2 = it.next();
            } while (!(((FirCallableSymbol) next2) instanceof FirIntersectionCallableSymbol));
            DeclarationSymbolMarker declarationSymbolMarker = (FirCallableSymbol) next2;
            if (declarationSymbolMarker == null || (intersections2 = ((FirIntersectionCallableSymbol) declarationSymbolMarker).getIntersections()) == null) {
                intersections2 = directOverriddenMembers$default;
            }
            intersections = CollectionsKt.plus((Collection<? extends FirCallableSymbol<?>>) intersections2, symbol);
        } else if (!(symbol instanceof FirIntersectionCallableSymbol) || !Intrinsics.areEqual(symbol.getCallableId().getClassId(), FirDeclarationUtilKt.getClassId(containingClass))) {
            return;
        } else {
            intersections = ((FirIntersectionCallableSymbol) symbol).getIntersections();
        }
        Collection<FirCallableSymbol<?>> collection = intersections;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (FirCallableSymbol<?> firCallableSymbol2 : collection) {
            arrayList.add(TuplesKt.to(firCallableSymbol2, FirTypeUtilsKt.getConeType(context.getReturnTypeCalculator().tryCalculateReturnType(firCallableSymbol2))));
        }
        ArrayList arrayList2 = arrayList;
        boolean z3 = arrayList2 instanceof Collection;
        if (!z3 || !arrayList2.isEmpty()) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                if (((Pair) it2.next()).getSecond() instanceof ConeErrorType) {
                    return;
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator<FirCallableSymbol<?>> it3 = intersections.iterator();
        while (true) {
            if (!it3.hasNext()) {
                next = null;
                break;
            }
            next = it3.next();
            DelegatedWrapperData delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(next);
            if (Intrinsics.areEqual((delegatedWrapperData == null || (containingClass2 = delegatedWrapperData.getContainingClass()) == null) ? null : containingClass2.getClassId(), FirDeclarationUtilKt.getClassId(containingClass))) {
                break;
            } else if (next.getResolvedStatus().getModality() != Modality.ABSTRACT) {
                arrayList3.add(next);
            }
        }
        if (!z3 || !arrayList2.isEmpty()) {
            Iterator it4 = arrayList2.iterator();
            pair = null;
            while (true) {
                if (!it4.hasNext()) {
                    z = false;
                    break;
                }
                Pair pair2 = (Pair) it4.next();
                FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) pair2.component1();
                ConeKotlinType coneKotlinType = (ConeKotlinType) pair2.component2();
                if (!z3 || !arrayList2.isEmpty()) {
                    Iterator it5 = arrayList2.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            z2 = true;
                            break;
                        }
                        Pair pair3 = (Pair) it5.next();
                        FirCallableSymbol firCallableSymbol4 = (FirCallableSymbol) pair3.component1();
                        ConeKotlinType coneKotlinType2 = (ConeKotlinType) pair3.component2();
                        boolean zCheckInheritanceClash$canOverride = checkInheritanceClash$canOverride(context, typeCheckerState, firCallableSymbol3, coneKotlinType, firCallableSymbol4, coneKotlinType2);
                        if (!zCheckInheritanceClash$canOverride && pair == null && !checkInheritanceClash$canOverride(context, typeCheckerState, firCallableSymbol4, coneKotlinType2, firCallableSymbol3, coneKotlinType)) {
                            pair = TuplesKt.to(firCallableSymbol3, firCallableSymbol4);
                        }
                        if (!zCheckInheritanceClash$canOverride) {
                            z2 = false;
                            break;
                        }
                    }
                } else {
                    z2 = true;
                    break;
                    break;
                }
                if (z2) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            pair = null;
        }
        if (pair != null) {
            if (z) {
                pair = null;
            }
            if (pair != null) {
                checkInheritanceClash$reportTypeMismatch(reporter, containingClass, context, (FirCallableSymbol) pair.component1(), (FirCallableSymbol) pair.component2(), false);
                return;
            }
        }
        if (next == null && arrayList3.isEmpty()) {
            return;
        }
        if (next == null) {
            FirCallableSymbol<?> firCallableSymbol5 = (FirCallableSymbol) CollectionsKt.singleOrNull((List) arrayList3);
            if (firCallableSymbol5 == null) {
                return;
            } else {
                firCallableSymbol = firCallableSymbol5;
            }
        } else {
            firCallableSymbol = next;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(context.getReturnTypeCalculator().tryCalculateReturnType(firCallableSymbol));
        for (Object obj2 : arrayList2) {
            Pair pair4 = (Pair) obj2;
            if (!checkInheritanceClash$canOverride(context, typeCheckerState, firCallableSymbol, coneType, (FirCallableSymbol) pair4.component1(), (ConeKotlinType) pair4.component2())) {
                obj = obj2;
                break;
            }
        }
        Pair pair5 = (Pair) obj;
        if (pair5 == null) {
            return;
        }
        checkInheritanceClash$reportTypeMismatch(reporter, containingClass, context, firCallableSymbol, (FirCallableSymbol) pair5.component1(), next != null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkValOverridesVar(FirClass containingClass, CheckerContext context, DiagnosticReporter reporter, FirVariableSymbol<?> symbol, FirTypeScope classScope) {
        Object next;
        if (symbol instanceof FirPropertySymbol) {
            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) symbol;
            if (firPropertySymbol.isVar() || ClassMembersKt.getDelegatedWrapperData(symbol) == null) {
                return;
            }
            Iterator<T> it = FirTypeScopeKt.getDirectOverriddenProperties(classScope, firPropertySymbol, true).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!((FirPropertySymbol) next).isVar());
            FirPropertySymbol firPropertySymbol2 = (FirPropertySymbol) next;
            if (firPropertySymbol2 == null) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, containingClass.getSource(), FirErrors.INSTANCE.getVAR_OVERRIDDEN_BY_VAL_BY_DELEGATION(), symbol, firPropertySymbol2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final void checkConflictingMembers(FirClass containingClass, CheckerContext context, DiagnosticReporter reporter, FirTypeScope scope, Name name2) {
        Object next;
        FirNamedFunctionSymbol firNamedFunctionSymbol;
        FirNamedFunctionSymbol firNamedFunctionSymbol2;
        final ArrayList arrayList = new ArrayList();
        scope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirImplementationMismatchChecker.checkConflictingMembers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol3) {
                invoke2(firNamedFunctionSymbol3);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol sym) {
                Intrinsics.checkNotNullParameter(sym, "sym");
                if (!(sym instanceof FirIntersectionOverrideFunctionSymbol)) {
                    arrayList.add(sym);
                    return;
                }
                Collection<FirCallableSymbol<?>> intersections = ((FirIntersectionOverrideFunctionSymbol) sym).getIntersections();
                List<FirNamedFunctionSymbol> list = arrayList;
                Iterator<T> it = intersections.iterator();
                while (it.hasNext()) {
                    FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
                    FirNamedFunctionSymbol firNamedFunctionSymbol3 = firCallableSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) firCallableSymbol : null;
                    if (firNamedFunctionSymbol3 != null) {
                        list.add(firNamedFunctionSymbol3);
                    }
                }
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next2 = it.next();
            FirNamedFunctionSymbol firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) next2;
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            List list = listCreateListBuilder;
            FirResolvedTypeRef resolvedReceiverTypeRef = firNamedFunctionSymbol3.getResolvedReceiverTypeRef();
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, resolvedReceiverTypeRef != null ? resolvedReceiverTypeRef.getType() : null);
            Iterator<T> it2 = firNamedFunctionSymbol3.getValueParameterSymbols().iterator();
            while (it2.hasNext()) {
                list.add(FirTypeUtilsKt.getConeType(((FirValueParameterSymbol) it2.next()).getResolvedReturnTypeRef()));
            }
            List listBuild = CollectionsKt.build(listCreateListBuilder);
            Object obj = linkedHashMap.get(listBuild);
            if (obj == null) {
                obj = (List) new ArrayList();
                linkedHashMap.put(listBuild, obj);
            }
            ((List) obj).add(next2);
        }
        Collection collectionValues = linkedHashMap.values();
        ArrayList<Pair> arrayList2 = new ArrayList();
        Iterator it3 = collectionValues.iterator();
        while (it3.hasNext()) {
            Iterator it4 = CollectionsKt.zipWithNext((List) it3.next()).iterator();
            do {
                if (!it4.hasNext()) {
                    next = null;
                    break;
                }
                next = it4.next();
                Pair pair = (Pair) next;
                firNamedFunctionSymbol = (FirNamedFunctionSymbol) pair.component1();
                firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) pair.component2();
                if (firNamedFunctionSymbol.getRawStatus().isSuspend() != firNamedFunctionSymbol2.getRawStatus().isSuspend()) {
                    break;
                }
            } while (firNamedFunctionSymbol.getTypeParameterSymbols().size() == firNamedFunctionSymbol2.getTypeParameterSymbols().size());
            Pair pair2 = (Pair) next;
            if (pair2 != null) {
                arrayList2.add(pair2);
            }
        }
        for (Pair pair3 : arrayList2) {
            FirNamedFunctionSymbol firNamedFunctionSymbol4 = (FirNamedFunctionSymbol) pair3.component1();
            FirNamedFunctionSymbol firNamedFunctionSymbol5 = (FirNamedFunctionSymbol) pair3.component2();
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol4);
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol5);
            if (!Intrinsics.areEqual(coneClassLikeLookupTagContainingClassLookupTag, coneClassLikeLookupTagContainingClassLookupTag2)) {
                ConeClassLikeLookupTag lookupTag = containingClass.getSymbol().getLookupTag();
                if (Intrinsics.areEqual(coneClassLikeLookupTagContainingClassLookupTag, lookupTag)) {
                    KtSourceElement source = firNamedFunctionSymbol4.getSource();
                    if ((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firNamedFunctionSymbol4.getSource(), FirErrors.INSTANCE.getCONFLICTING_OVERLOADS(), TuplesKt.toList(pair3), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
                if (Intrinsics.areEqual(coneClassLikeLookupTagContainingClassLookupTag2, lookupTag)) {
                    KtSourceElement source2 = firNamedFunctionSymbol5.getSource();
                    if ((source2 != null ? source2.getKind() : null) instanceof KtRealSourceElementKind) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firNamedFunctionSymbol5.getSource(), FirErrors.INSTANCE.getCONFLICTING_OVERLOADS(), TuplesKt.toList(pair3), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, containingClass.getSource(), FirErrors.INSTANCE.getCONFLICTING_INHERITED_MEMBERS(), containingClass.getSymbol(), TuplesKt.toList(pair3), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }

    private final ConeKotlinType substituteTypeParameters(ConeKotlinType coneKotlinType, FirCallableSymbol<?> firCallableSymbol, FirCallableSymbol<?> firCallableSymbol2, CheckerContext checkerContext) {
        List<FirTypeParameterSymbol> typeParameterSymbols = firCallableSymbol.getTypeParameterSymbols();
        List<FirTypeParameterSymbol> typeParameterSymbols2 = firCallableSymbol2.getTypeParameterSymbols();
        List<FirTypeParameterSymbol> list = typeParameterSymbols;
        Iterator<T> it = list.iterator();
        List<FirTypeParameterSymbol> list2 = typeParameterSymbols2;
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(list2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(TuplesKt.to((FirTypeParameterSymbol) it.next(), FirNestedClassifierScopeKt.toConeType((FirTypeParameterSymbol) it2.next())));
        }
        return SubstitutorsKt.substitutorByMap(MapsKt.toMap(arrayList), checkerContext.getSession()).substituteOrSelf(coneKotlinType);
    }
}
