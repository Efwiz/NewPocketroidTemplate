package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirNativeObjCRefinementOverridesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016JN\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J$\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J,\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\rH\u0002J<\u0010\u001c\u001a\u00020\u0006*\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00112\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0010\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u00132\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCRefinementOverridesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "firTypeScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "memberSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "declarationToReport", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "objCAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "swiftAnnotations", "hasRefinedAnnotations", "Lkotlin/Pair;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "inheritsRefinedAnnotations", "reportIncompatibleOverride", "annotations", "notRefinedSupers", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCRefinementOverridesChecker extends FirDeclarationChecker<FirClass> {
    public static final FirNativeObjCRefinementOverridesChecker INSTANCE = new FirNativeObjCRefinementOverridesChecker();

    private FirNativeObjCRefinementOverridesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(final FirClass declaration, final CheckerContext context, final DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        final FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(declaration, context);
        FirTypeScope firTypeScope = firTypeScopeUnsubstitutedScope;
        FirContainingNamesAwareScopeKt.processAllFunctions(firTypeScope, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.native.checkers.FirNativeObjCRefinementOverridesChecker.check.1
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
            public final void invoke2(FirNamedFunctionSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                FirNamedFunctionSymbol firNamedFunctionSymbol = symbol;
                if (ClassMembersKt.isIntersectionOverride(firNamedFunctionSymbol)) {
                    FirNativeObjCRefinementOverridesChecker.INSTANCE.check(firTypeScopeUnsubstitutedScope, firNamedFunctionSymbol, declaration, context, reporter, CollectionsKt.emptyList(), CollectionsKt.emptyList());
                }
            }
        });
        FirContainingNamesAwareScopeKt.processAllProperties(firTypeScope, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.native.checkers.FirNativeObjCRefinementOverridesChecker.check.2
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
            public final void invoke2(FirVariableSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                FirVariableSymbol<?> firVariableSymbol = symbol;
                if (ClassMembersKt.isIntersectionOverride(firVariableSymbol)) {
                    FirNativeObjCRefinementOverridesChecker.INSTANCE.check(firTypeScopeUnsubstitutedScope, firVariableSymbol, declaration, context, reporter, CollectionsKt.emptyList(), CollectionsKt.emptyList());
                }
            }
        });
    }

    public final void check(FirTypeScope firTypeScope, FirCallableSymbol<?> memberSymbol, FirDeclaration declarationToReport, CheckerContext context, DiagnosticReporter reporter, List<? extends FirAnnotation> objCAnnotations, List<? extends FirAnnotation> swiftAnnotations) {
        Intrinsics.checkNotNullParameter(firTypeScope, "firTypeScope");
        Intrinsics.checkNotNullParameter(memberSymbol, "memberSymbol");
        Intrinsics.checkNotNullParameter(declarationToReport, "declarationToReport");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(objCAnnotations, "objCAnnotations");
        Intrinsics.checkNotNullParameter(swiftAnnotations, "swiftAnnotations");
        List<FirCallableSymbol<?>> listRetrieveDirectOverriddenOf = FirTypeScopeKt.retrieveDirectOverriddenOf(firTypeScope, memberSymbol);
        if (listRetrieveDirectOverriddenOf.isEmpty()) {
            return;
        }
        boolean z = !objCAnnotations.isEmpty();
        boolean z2 = !swiftAnnotations.isEmpty();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z3 = z2;
        for (FirCallableSymbol<?> firCallableSymbol : listRetrieveDirectOverriddenOf) {
            Pair<Boolean, Boolean> pairInheritsRefinedAnnotations = inheritsRefinedAnnotations(firCallableSymbol, context.getSession(), firTypeScope);
            boolean zBooleanValue = pairInheritsRefinedAnnotations.component1().booleanValue();
            boolean zBooleanValue2 = pairInheritsRefinedAnnotations.component2().booleanValue();
            if (zBooleanValue) {
                z = true;
            } else {
                arrayList.add(firCallableSymbol);
            }
            if (zBooleanValue2) {
                z3 = true;
            } else {
                arrayList2.add(firCallableSymbol);
            }
        }
        if (z && !arrayList.isEmpty()) {
            reportIncompatibleOverride(reporter, declarationToReport, objCAnnotations, arrayList, context);
        }
        if (!z3 || arrayList2.isEmpty()) {
            return;
        }
        reportIncompatibleOverride(reporter, declarationToReport, swiftAnnotations, arrayList2, context);
    }

    private final Pair<Boolean, Boolean> inheritsRefinedAnnotations(FirCallableSymbol<?> firCallableSymbol, FirSession firSession, FirTypeScope firTypeScope) {
        Pair<Boolean, Boolean> pairHasRefinedAnnotations = hasRefinedAnnotations(firCallableSymbol, firSession);
        boolean zBooleanValue = pairHasRefinedAnnotations.component1().booleanValue();
        boolean zBooleanValue2 = pairHasRefinedAnnotations.component2().booleanValue();
        boolean z = true;
        if (zBooleanValue && zBooleanValue2) {
            return TuplesKt.to(true, true);
        }
        FirCallableSymbol<?> firCallableSymbol2 = (FirCallableSymbol) CollectionsKt.firstOrNull((List) FirTypeScopeKt.retrieveDirectOverriddenOf(firTypeScope, firCallableSymbol));
        if (firCallableSymbol2 == null) {
            return TuplesKt.to(Boolean.valueOf(zBooleanValue), Boolean.valueOf(zBooleanValue2));
        }
        Pair<Boolean, Boolean> pairInheritsRefinedAnnotations = inheritsRefinedAnnotations(firCallableSymbol2, firSession, firTypeScope);
        boolean zBooleanValue3 = pairInheritsRefinedAnnotations.component1().booleanValue();
        boolean zBooleanValue4 = pairInheritsRefinedAnnotations.component2().booleanValue();
        Boolean boolValueOf = Boolean.valueOf(zBooleanValue || zBooleanValue3);
        if (!zBooleanValue2 && !zBooleanValue4) {
            z = false;
        }
        return TuplesKt.to(boolValueOf, Boolean.valueOf(z));
    }

    private final Pair<Boolean, Boolean> hasRefinedAnnotations(FirCallableSymbol<?> firCallableSymbol, FirSession firSession) {
        Iterator<FirAnnotation> it = firCallableSymbol.getResolvedAnnotationsWithClassIds().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            FirClassLikeSymbol<?> annotationClassLikeSymbol = FirAnnotationUtilsKt.toAnnotationClassLikeSymbol(it.next(), firSession);
            List<FirAnnotation> resolvedAnnotationsWithClassIds = annotationClassLikeSymbol != null ? annotationClassLikeSymbol.getResolvedAnnotationsWithClassIds() : null;
            if (resolvedAnnotationsWithClassIds == null) {
                resolvedAnnotationsWithClassIds = CollectionsKt.emptyList();
            }
            Iterator<FirAnnotation> it2 = resolvedAnnotationsWithClassIds.iterator();
            while (it2.hasNext()) {
                ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId(it2.next(), firSession);
                if (Intrinsics.areEqual(annotationClassId, FirNativeObjCRefinementChecker.INSTANCE.getHidesFromObjCClassId())) {
                    z = true;
                    break;
                }
                if (Intrinsics.areEqual(annotationClassId, FirNativeObjCRefinementChecker.INSTANCE.getRefinesInSwiftClassId())) {
                    z2 = true;
                    break;
                }
            }
            if (z && z2) {
                return TuplesKt.to(true, true);
            }
        }
        return TuplesKt.to(Boolean.valueOf(z), Boolean.valueOf(z2));
    }

    private final void reportIncompatibleOverride(DiagnosticReporter diagnosticReporter, FirDeclaration firDeclaration, List<? extends FirAnnotation> list, List<? extends FirCallableSymbol<?>> list2, CheckerContext checkerContext) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) it.next());
            FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, checkerContext.getSession()) : null;
            if (firRegularClassSymbol != null) {
                arrayList.add(firRegularClassSymbol);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (list.isEmpty()) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firDeclaration.getSource(), FirNativeErrors.INSTANCE.getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE(), firDeclaration.getSymbol(), arrayList2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        Iterator<? extends FirAnnotation> it2 = list.iterator();
        while (it2.hasNext()) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, it2.next().getSource(), FirNativeErrors.INSTANCE.getINCOMPATIBLE_OBJC_REFINEMENT_OVERRIDE(), firDeclaration.getSymbol(), arrayList2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }
}
