package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInconsistentTypeParameterHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirTypeParameterBoundsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J \u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J(\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001e\u0010\u0019\u001a\u00020\u001a*\u00020\u001b2\u0006\u0010\u000b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterBoundsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "()V", "classKinds", "", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkBoundUniqueness", "checkConflictingBounds", "checkDynamicBounds", "checkExtensionFunctionTypeBound", "checkFinalUpperBounds", "containingDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkInconsistentTypeParameterBounds", "checkOnlyOneTypeParameterBound", "checkTypeAliasBound", "isRelated", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", ModuleXmlParser.TYPE, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterBoundsChecker extends FirDeclarationChecker<FirTypeParameter> {
    public static final FirTypeParameterBoundsChecker INSTANCE = new FirTypeParameterBoundsChecker();
    private static final Set<ClassKind> classKinds = SetsKt.setOf((Object[]) new ClassKind[]{ClassKind.CLASS, ClassKind.ENUM_CLASS, ClassKind.OBJECT});

    private FirTypeParameterBoundsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
        if (firDeclaration == null || (firDeclaration instanceof FirConstructor)) {
            return;
        }
        checkFinalUpperBounds(declaration, firDeclaration, context, reporter);
        checkExtensionFunctionTypeBound(declaration, context, reporter);
        FirMemberDeclaration firMemberDeclaration = firDeclaration instanceof FirMemberDeclaration ? (FirMemberDeclaration) firDeclaration : null;
        if (firMemberDeclaration == null || !FirHelpersKt.isInlineOnly(firMemberDeclaration, context.getSession())) {
            checkOnlyOneTypeParameterBound(declaration, context, reporter);
        }
        checkBoundUniqueness(declaration, context, reporter);
        checkConflictingBounds(declaration, context, reporter);
        checkTypeAliasBound(declaration, firDeclaration, context, reporter);
        checkDynamicBounds(declaration, context, reporter);
        checkInconsistentTypeParameterBounds(declaration, context, reporter);
    }

    private final void checkFinalUpperBounds(FirTypeParameter declaration, FirDeclaration containingDeclaration, CheckerContext context, DiagnosticReporter reporter) {
        if ((containingDeclaration instanceof FirSimpleFunction) && ((FirMemberDeclaration) containingDeclaration).getStatus().isOverride()) {
            return;
        }
        if ((containingDeclaration instanceof FirProperty) && ((FirMemberDeclaration) containingDeclaration).getStatus().isOverride()) {
            return;
        }
        for (FirResolvedTypeRef firResolvedTypeRef : declaration.getSymbol().getResolvedBounds()) {
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef;
            if (!TypeUtilsKt.canHaveSubtypes(FirTypeUtilsKt.getConeType(firResolvedTypeRef2), context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedTypeRef.getSource(), FirErrors.INSTANCE.getFINAL_UPPER_BOUND(), FirTypeUtilsKt.getConeType(firResolvedTypeRef2), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final void checkExtensionFunctionTypeBound(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        for (FirResolvedTypeRef firResolvedTypeRef : declaration.getSymbol().getResolvedBounds()) {
            if (TypeUtilsKt.isExtensionFunctionType(firResolvedTypeRef, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedTypeRef.getSource(), FirErrors.INSTANCE.getUPPER_BOUND_IS_EXTENSION_FUNCTION_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkTypeAliasBound(FirTypeParameter declaration, FirDeclaration containingDeclaration, CheckerContext context, DiagnosticReporter reporter) {
        if (containingDeclaration instanceof FirTypeAlias) {
            List<FirTypeRef> bounds = declaration.getBounds();
            ArrayList arrayList = new ArrayList();
            for (Object obj : bounds) {
                KtSourceElement source = ((FirTypeRef) obj).getSource();
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtRealSourceElementKind.INSTANCE)) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirTypeRef) it.next()).getSource(), FirErrors.INSTANCE.getBOUND_ON_TYPE_ALIAS_PARAMETER_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkOnlyOneTypeParameterBound(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        List<FirResolvedTypeRef> resolvedBounds = declaration.getSymbol().getResolvedBounds();
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : resolvedBounds) {
            if (hashSet.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) obj))) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : arrayList3) {
            if (FirTypeUtilsKt.getConeType((FirResolvedTypeRef) obj2) instanceof ConeTypeParameterType) {
                arrayList4.add(obj2);
            } else {
                arrayList5.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList4, arrayList5);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        if (list.size() > 1 || (list.size() == 1 && !list2.isEmpty())) {
            SourceNavigator sourceNavigatorForElement = SourceNavigator.INSTANCE.forElement(declaration);
            ArrayList arrayList6 = new ArrayList();
            for (Object obj3 : arrayList3) {
                if (sourceNavigatorForElement.isInTypeConstraint((FirResolvedTypeRef) obj3)) {
                    arrayList6.add(obj3);
                }
            }
            Set set = CollectionsKt.toSet(arrayList6);
            if (arrayList2.size() == 2) {
                FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) CollectionsKt.firstOrNull(list2);
                if (firResolvedTypeRef == null) {
                    firResolvedTypeRef = (FirResolvedTypeRef) CollectionsKt.last(list);
                }
                source = set.contains(firResolvedTypeRef) ? firResolvedTypeRef.getSource() : declaration.getSource();
            } else {
                source = declaration.getSource();
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getBOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkBoundUniqueness(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<FirResolvedTypeRef> resolvedBounds = declaration.getSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList();
        for (Object obj : resolvedBounds) {
            if (!(((FirResolvedTypeRef) obj) instanceof FirErrorTypeRef)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        HashSet hashSet = new HashSet();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) obj2;
            Object classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firResolvedTypeRef));
            if (classId == null) {
                classId = FirTypeUtilsKt.getConeType(firResolvedTypeRef);
            }
            if (hashSet.add(classId)) {
                arrayList3.add(obj2);
            }
        }
        ArrayList<FirResolvedTypeRef> arrayList4 = arrayList3;
        for (FirResolvedTypeRef firResolvedTypeRef2 : arrayList4) {
            FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(firResolvedTypeRef2), context.getSession());
            if (regularClassSymbol != null && classKinds.contains(regularClassSymbol.getClassKind()) && linkedHashSet.add(regularClassSymbol) && linkedHashSet.size() > 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedTypeRef2.getSource(), FirErrors.INSTANCE.getONLY_ONE_CLASS_BOUND_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        Iterator it = CollectionsKt.minus((Iterable) arrayList2, (Iterable) arrayList4).iterator();
        while (it.hasNext()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirResolvedTypeRef) it.next()).getSource(), FirErrors.INSTANCE.getREPEATED_BOUND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkConflictingBounds(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (declaration.getBounds().size() < 2) {
            return;
        }
        List<FirResolvedTypeRef> resolvedBounds = declaration.getSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        if (checkConflictingBounds$anyConflictingTypes(context, arrayList)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getCONFLICTING_UPPER_BOUNDS(), declaration.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private static final boolean checkConflictingBounds$anyConflictingTypes(CheckerContext checkerContext, List<? extends ConeKotlinType> list) {
        List<? extends ConeKotlinType> list2 = list;
        for (ConeKotlinType coneKotlinType : list2) {
            if (!TypeUtilsKt.canHaveSubtypes(coneKotlinType, checkerContext.getSession())) {
                for (ConeKotlinType coneKotlinType2 : list2) {
                    if (!Intrinsics.areEqual(coneKotlinType, coneKotlinType2) && !INSTANCE.isRelated(coneKotlinType, TypeComponentsKt.getTypeContext(checkerContext.getSession()), coneKotlinType2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final void checkDynamicBounds(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        for (FirTypeRef firTypeRef : declaration.getBounds()) {
            if (FirTypeUtilsKt.getConeType(firTypeRef) instanceof ConeDynamicType) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getDYNAMIC_UPPER_BOUND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean isRelated(KotlinTypeMarker kotlinTypeMarker, TypeCheckerProviderContext typeCheckerProviderContext, KotlinTypeMarker kotlinTypeMarker2) {
        return TypeUtilsKt.isSubtypeOf(kotlinTypeMarker, typeCheckerProviderContext, kotlinTypeMarker2) || FirHelpersKt.isSupertypeOf(kotlinTypeMarker, typeCheckerProviderContext, kotlinTypeMarker2);
    }

    private final void checkInconsistentTypeParameterBounds(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (declaration.getBounds().size() <= 1) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (FirResolvedTypeRef firResolvedTypeRef : declaration.getSymbol().getResolvedBounds()) {
            FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(firResolvedTypeRef, context.getSession());
            if (CollectionsKt.contains(linkedHashSet, regularClassSymbol)) {
                return;
            }
            if (regularClassSymbol != null) {
                linkedHashSet.add(regularClassSymbol);
                arrayList.add(new Pair(firResolvedTypeRef, regularClassSymbol));
            }
        }
        FirInconsistentTypeParameterHelpersKt.checkInconsistentTypeParameters(arrayList, context, reporter, declaration.getSource(), false);
    }
}
