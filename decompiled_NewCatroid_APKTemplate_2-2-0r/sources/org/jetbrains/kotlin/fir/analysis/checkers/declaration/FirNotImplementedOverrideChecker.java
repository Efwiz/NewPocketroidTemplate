package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.DelegatedWrapperData;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.ImplementationStatus;

/* JADX INFO: compiled from: FirNotImplementedOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNotImplementedOverrideChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isFromInterfaceOrEnum", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNotImplementedOverrideChecker extends FirDeclarationChecker<FirClass> {
    public static final FirNotImplementedOverrideChecker INSTANCE = new FirNotImplementedOverrideChecker();

    /* JADX INFO: compiled from: FirNotImplementedOverrideChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ImplementationStatus.values().length];
            try {
                iArr[ImplementationStatus.AMBIGUOUSLY_INHERITED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ImplementationStatus.NOT_IMPLEMENTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ImplementationStatus.VAR_IMPLEMENTED_BY_VAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirNotImplementedOverrideChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Object next;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (source == null) {
            return;
        }
        KtSourceElementKind kind = source.getKind();
        if (!(kind instanceof KtFakeSourceElementKind) || Intrinsics.areEqual(kind, KtFakeSourceElementKind.EnumInitializer.INSTANCE)) {
            Modality modality = FirHelpersKt.modality(declaration);
            boolean z = modality == Modality.ABSTRACT || modality == Modality.SEALED;
            ClassKind classKind = declaration.getClassKind();
            if (classKind == ClassKind.ANNOTATION_CLASS || classKind == ClassKind.ENUM_CLASS) {
                return;
            }
            FirClassSymbol<? extends FirClass> symbol = declaration.getSymbol();
            FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList();
            for (Name name2 : firTypeScopeUnsubstitutedScope.getCallableNames()) {
                boolean z2 = z;
                FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
                firTypeScope.processFunctionsByName(name2, new AnonymousClass1(firTypeScopeUnsubstitutedScope, arrayList3, arrayList4, arrayList5, context, symbol, arrayList2, arrayList, arrayList6, arrayList7));
                firTypeScope.processPropertiesByName(name2, new AnonymousClass2(firTypeScope, arrayList3, arrayList4, arrayList5, context, symbol, arrayList2, arrayList, arrayList6, arrayList7));
                firTypeScopeUnsubstitutedScope = firTypeScope;
                z = z2;
            }
            boolean z3 = z;
            FirIntersectionCallableSymbol firIntersectionCallableSymbol = (FirIntersectionCallableSymbol) CollectionsKt.firstOrNull((List) arrayList7);
            if (firIntersectionCallableSymbol != 0) {
                Iterator<T> it = firIntersectionCallableSymbol.getIntersections().iterator();
                while (true) {
                    if (it.hasNext()) {
                        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
                        if ((firCallableSymbol instanceof FirPropertySymbol) && ((FirPropertySymbol) firCallableSymbol).isVal() && firCallableSymbol.getResolvedStatus().getModality() != Modality.ABSTRACT) {
                            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source, (KtDiagnosticFactoryForDeprecation3<FirClassSymbol<? extends FirClass>, FirCallableSymbol, FirCallableSymbol>) ((KtDiagnosticFactoryForDeprecation3<Object, Object, Object>) FirErrors.INSTANCE.getVAR_IMPLEMENTED_BY_INHERITED_VAL()), symbol, (FirCallableSymbol) firIntersectionCallableSymbol, firCallableSymbol, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
                            break;
                        }
                    } else {
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                }
            }
            if (!z3 && !arrayList.isEmpty()) {
                Iterator it2 = arrayList.iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                } while (INSTANCE.isFromInterfaceOrEnum((FirCallableSymbol) next, context));
                FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) next;
                if (firCallableSymbol2 == null) {
                    firCallableSymbol2 = (FirCallableSymbol) CollectionsKt.first((List) arrayList);
                }
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol2.getFir();
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                    if (originalForSubstitutionOverrideAttr == null) {
                        originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                    }
                    if (originalForSubstitutionOverrideAttr == null) {
                        break;
                    } else {
                        firCallableDeclaration = originalForSubstitutionOverrideAttr;
                    }
                }
                FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration.getSymbol();
                if (symbol2 != null) {
                    if (isFromInterfaceOrEnum(symbol2, context)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getABSTRACT_MEMBER_NOT_IMPLEMENTED(), symbol, symbol2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    } else {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED(), symbol, symbol2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<out org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration>");
                }
            }
            if (!z3 && !arrayList6.isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getINVISIBLE_ABSTRACT_MEMBER_FROM_SUPER(), symbol, (FirCallableSymbol) CollectionsKt.first((List) arrayList6), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
            FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) CollectionsKt.firstOrNull((List) arrayList3);
            if (firCallableSymbol3 != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getMANY_IMPL_MEMBER_NOT_IMPLEMENTED(), symbol, firCallableSymbol3, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
            Pair pair = (Pair) CollectionsKt.firstOrNull((List) arrayList4);
            if (pair != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getOVERRIDING_FINAL_MEMBER_BY_DELEGATION(), (FirCallableSymbol) pair.component1(), (FirCallableSymbol) pair.component2(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
            Pair pair2 = (Pair) CollectionsKt.firstOrNull((List) arrayList5);
            if (pair2 != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE(), (FirCallableSymbol) pair2.component1(), (FirCallableSymbol) pair2.component2(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
            if (!arrayList3.isEmpty() || arrayList2.isEmpty()) {
                return;
            }
            DeclarationSymbolMarker declarationSymbolMarker = (FirCallableSymbol) CollectionsKt.first((List) arrayList2);
            Intrinsics.checkNotNull(declarationSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol");
            Collection<FirCallableSymbol<?>> intersections = ((FirIntersectionCallableSymbol) declarationSymbolMarker).getIntersections();
            ArrayList arrayList8 = new ArrayList();
            ArrayList arrayList9 = new ArrayList();
            for (Object obj : intersections) {
                if (((FirCallableSymbol) obj).getResolvedStatus().getModality() == Modality.ABSTRACT) {
                    arrayList8.add(obj);
                } else {
                    arrayList9.add(obj);
                }
            }
            Pair pair3 = new Pair(arrayList8, arrayList9);
            List list = (List) pair3.component1();
            List list2 = (List) pair3.component2();
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it3 = list2.iterator();
                while (it3.hasNext()) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) it3.next());
                    FirClassLikeSymbol<?> symbol3 = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
                    FirRegularClassSymbol firRegularClassSymbol = symbol3 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol3 : null;
                    if ((firRegularClassSymbol != null ? firRegularClassSymbol.getClassKind() : null) == ClassKind.CLASS) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getMANY_IMPL_MEMBER_NOT_IMPLEMENTED(), symbol, declarationSymbolMarker, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                        return;
                    }
                }
            }
            if (z3) {
                List list3 = list;
                if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                    Iterator it4 = list3.iterator();
                    while (it4.hasNext()) {
                        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) it4.next());
                        FirClassLikeSymbol<?> symbol4 = coneClassLikeLookupTagContainingClassLookupTag2 != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag2, context.getSession()) : null;
                        FirRegularClassSymbol firRegularClassSymbol2 = symbol4 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol4 : null;
                        if ((firRegularClassSymbol2 != null ? firRegularClassSymbol2.getClassKind() : null) == ClassKind.CLASS) {
                            return;
                        }
                    }
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getMANY_INTERFACES_MEMBER_NOT_IMPLEMENTED(), symbol, declarationSymbolMarker, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void check$collectSymbol(FirTypeScope firTypeScope, List<FirCallableSymbol<?>> list, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list2, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list3, CheckerContext checkerContext, FirClassSymbol<? extends FirClass> firClassSymbol, List<FirCallableSymbol<?>> list4, List<FirCallableSymbol<?>> list5, List<FirCallableSymbol<?>> list6, List<FirIntersectionCallableSymbol> list7, FirCallableSymbol<?> firCallableSymbol) {
        List<MemberWithBaseScope<FirCallableSymbol<?>>> listFilterOutOverriddenProperties;
        Object obj;
        Object next;
        Modality modality;
        FirCallableSymbol member;
        DelegatedWrapperData delegatedWrapperData = ClassMembersKt.getDelegatedWrapperData(firCallableSymbol);
        if (delegatedWrapperData == null) {
            int i = WhenMappings.$EnumSwitchMapping$0[FirHelpersKt.getImplementationStatus(firCallableSymbol, checkerContext.getSessionHolder(), firClassSymbol).ordinal()];
            if (i == 1) {
                list4.add(firCallableSymbol);
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                Intrinsics.checkNotNull(firCallableSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol");
                list7.add((FirIntersectionCallableSymbol) firCallableSymbol);
                return;
            }
            if (FirHelpersKt.isVisibleInClass(firCallableSymbol, firClassSymbol)) {
                list5.add(firCallableSymbol);
                return;
            } else {
                list6.add(firCallableSymbol);
                return;
            }
        }
        List<MemberWithBaseScope<FirCallableSymbol<?>>> directOverriddenMembersWithBaseScope = FirTypeScopeKt.getDirectOverriddenMembersWithBaseScope(firTypeScope, firCallableSymbol);
        if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
            Intrinsics.checkNotNull(directOverriddenMembersWithBaseScope, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope<org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol>>");
            listFilterOutOverriddenProperties = FirOverrideUtilsKt.filterOutOverriddenFunctions(directOverriddenMembersWithBaseScope);
        } else if (firCallableSymbol instanceof FirPropertySymbol) {
            Intrinsics.checkNotNull(directOverriddenMembersWithBaseScope, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope<org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol>>");
            listFilterOutOverriddenProperties = FirOverrideUtilsKt.filterOutOverriddenProperties(directOverriddenMembersWithBaseScope);
        } else {
            listFilterOutOverriddenProperties = directOverriddenMembersWithBaseScope;
        }
        Iterable iterable = listFilterOutOverriddenProperties;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((MemberWithBaseScope) it.next()).getMember());
        }
        ArrayList arrayList2 = arrayList;
        FirCallableDeclaration wrapped = delegatedWrapperData.getWrapped();
        while (true) {
            obj = null;
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(wrapped) || (wrapped.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(wrapped) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(wrapped) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(wrapped) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            } else {
                wrapped = originalForSubstitutionOverrideAttr;
            }
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = wrapped.getSymbol();
        if (Intrinsics.areEqual((Object) FirDelegatedMemberScopeKt.getMultipleDelegatesWithTheSameSignature(firCallableSymbol), (Object) true) && !directOverriddenMembersWithBaseScope.isEmpty()) {
            MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) CollectionsKt.singleOrNull((List) directOverriddenMembersWithBaseScope);
            if (!Intrinsics.areEqual((memberWithBaseScope == null || (member = memberWithBaseScope.getMember()) == null) ? null : member.getOrigin(), FirDeclarationOrigin.Enhancement.INSTANCE)) {
                list.add(firCallableSymbol);
            }
        }
        ArrayList arrayList3 = arrayList2;
        Iterator it2 = arrayList3.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
            modality = ((FirCallableSymbol) next).getResolvedStatus().getModality();
            if (modality == null) {
                break;
            }
        } while (modality != Modality.FINAL);
        FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) next;
        for (Object obj2 : arrayList3) {
            FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) obj2;
            if (firCallableSymbol3.getResolvedStatus().getModality() == Modality.OPEN) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol3.getFir();
                while (true) {
                    FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        originalForSubstitutionOverrideAttr2 = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
                    }
                    if (originalForSubstitutionOverrideAttr2 == null) {
                        break;
                    } else {
                        firCallableDeclaration = originalForSubstitutionOverrideAttr2;
                    }
                }
                FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration.getSymbol();
                if (symbol2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
                }
                if (!Intrinsics.areEqual(symbol, symbol2)) {
                    obj = obj2;
                    break;
                }
            }
        }
        FirCallableSymbol firCallableSymbol4 = (FirCallableSymbol) obj;
        if (firCallableSymbol2 != null) {
            list2.add(TuplesKt.to(firCallableSymbol, firCallableSymbol2));
        } else if (firCallableSymbol4 != null) {
            list3.add(TuplesKt.to(firCallableSymbol, firCallableSymbol4));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNotImplementedOverrideChecker$check$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirNotImplementedOverrideChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<FirCallableSymbol<?>, Unit> {
        final /* synthetic */ FirTypeScope $classScope;
        final /* synthetic */ FirClassSymbol<? extends FirClass> $classSymbol;
        final /* synthetic */ CheckerContext $context;
        final /* synthetic */ List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> $delegationOverrideOfFinal;
        final /* synthetic */ List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> $delegationOverrideOfOpen;
        final /* synthetic */ List<FirCallableSymbol<?>> $invisibleSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $manyImplementationsDelegationSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $notImplementedIntersectionSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $notImplementedSymbols;
        final /* synthetic */ List<FirIntersectionCallableSymbol> $varsImplementedByInheritedVal;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FirTypeScope firTypeScope, List<FirCallableSymbol<?>> list, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list2, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list3, CheckerContext checkerContext, FirClassSymbol<? extends FirClass> firClassSymbol, List<FirCallableSymbol<?>> list4, List<FirCallableSymbol<?>> list5, List<FirCallableSymbol<?>> list6, List<FirIntersectionCallableSymbol> list7) {
            super(1);
            this.$classScope = firTypeScope;
            this.$manyImplementationsDelegationSymbols = list;
            this.$delegationOverrideOfFinal = list2;
            this.$delegationOverrideOfOpen = list3;
            this.$context = checkerContext;
            this.$classSymbol = firClassSymbol;
            this.$notImplementedIntersectionSymbols = list4;
            this.$notImplementedSymbols = list5;
            this.$invisibleSymbols = list6;
            this.$varsImplementedByInheritedVal = list7;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "check$collectSymbol(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "collectSymbol";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
            invoke2(firCallableSymbol);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirCallableSymbol<?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            FirNotImplementedOverrideChecker.check$collectSymbol(this.$classScope, this.$manyImplementationsDelegationSymbols, this.$delegationOverrideOfFinal, this.$delegationOverrideOfOpen, this.$context, this.$classSymbol, this.$notImplementedIntersectionSymbols, this.$notImplementedSymbols, this.$invisibleSymbols, this.$varsImplementedByInheritedVal, p0);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNotImplementedOverrideChecker$check$2, reason: invalid class name */
    /* JADX INFO: compiled from: FirNotImplementedOverrideChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<FirCallableSymbol<?>, Unit> {
        final /* synthetic */ FirTypeScope $classScope;
        final /* synthetic */ FirClassSymbol<? extends FirClass> $classSymbol;
        final /* synthetic */ CheckerContext $context;
        final /* synthetic */ List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> $delegationOverrideOfFinal;
        final /* synthetic */ List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> $delegationOverrideOfOpen;
        final /* synthetic */ List<FirCallableSymbol<?>> $invisibleSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $manyImplementationsDelegationSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $notImplementedIntersectionSymbols;
        final /* synthetic */ List<FirCallableSymbol<?>> $notImplementedSymbols;
        final /* synthetic */ List<FirIntersectionCallableSymbol> $varsImplementedByInheritedVal;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(FirTypeScope firTypeScope, List<FirCallableSymbol<?>> list, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list2, List<Pair<FirCallableSymbol<?>, FirCallableSymbol<?>>> list3, CheckerContext checkerContext, FirClassSymbol<? extends FirClass> firClassSymbol, List<FirCallableSymbol<?>> list4, List<FirCallableSymbol<?>> list5, List<FirCallableSymbol<?>> list6, List<FirIntersectionCallableSymbol> list7) {
            super(1);
            this.$classScope = firTypeScope;
            this.$manyImplementationsDelegationSymbols = list;
            this.$delegationOverrideOfFinal = list2;
            this.$delegationOverrideOfOpen = list3;
            this.$context = checkerContext;
            this.$classSymbol = firClassSymbol;
            this.$notImplementedIntersectionSymbols = list4;
            this.$notImplementedSymbols = list5;
            this.$invisibleSymbols = list6;
            this.$varsImplementedByInheritedVal = list7;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "check$collectSymbol(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "collectSymbol";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(FirCallableSymbol<?> firCallableSymbol) {
            invoke2(firCallableSymbol);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirCallableSymbol<?> p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            FirNotImplementedOverrideChecker.check$collectSymbol(this.$classScope, this.$manyImplementationsDelegationSymbols, this.$delegationOverrideOfFinal, this.$delegationOverrideOfOpen, this.$context, this.$classSymbol, this.$notImplementedIntersectionSymbols, this.$notImplementedSymbols, this.$invisibleSymbols, this.$varsImplementedByInheritedVal, p0);
        }
    }

    private final boolean isFromInterfaceOrEnum(FirCallableSymbol<?> firCallableSymbol, CheckerContext checkerContext) {
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firCallableSymbol, checkerContext.getSession());
        FirRegularClassSymbol firRegularClassSymbol = containingClassSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingClassSymbol : null;
        if (firRegularClassSymbol == null) {
            return false;
        }
        FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol;
        return firRegularClassSymbol2.getClassKind() == ClassKind.INTERFACE || firRegularClassSymbol2.getClassKind() == ClassKind.ENUM_CLASS;
    }
}
