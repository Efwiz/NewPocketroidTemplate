package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirNativeObjCRefinementChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J,\u0010\u0012\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013*\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCRefinementChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "()V", "hidesFromObjCClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getHidesFromObjCClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "refinesInSwiftClassId", "getRefinesInSwiftClassId", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "findRefinedAnnotations", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCRefinementChecker extends FirDeclarationChecker<FirCallableDeclaration> {
    public static final FirNativeObjCRefinementChecker INSTANCE = new FirNativeObjCRefinementChecker();
    private static final ClassId hidesFromObjCClassId;
    private static final ClassId refinesInSwiftClassId;

    private FirNativeObjCRefinementChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.native.HidesFromObjC"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        hidesFromObjCClassId = classId;
        ClassId classId2 = ClassId.topLevel(new FqName("kotlin.native.RefinesInSwift"));
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        refinesInSwiftClassId = classId2;
    }

    public final ClassId getHidesFromObjCClassId() {
        return hidesFromObjCClassId;
    }

    public final ClassId getRefinesInSwiftClassId() {
        return refinesInSwiftClassId;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((declaration instanceof FirSimpleFunction) || (declaration instanceof FirProperty)) {
            Pair<List<FirAnnotation>, List<FirAnnotation>> pairFindRefinedAnnotations = findRefinedAnnotations(declaration, context.getSession());
            List<FirAnnotation> listComponent1 = pairFindRefinedAnnotations.component1();
            List<FirAnnotation> listComponent2 = pairFindRefinedAnnotations.component2();
            if (!listComponent1.isEmpty() && !listComponent2.isEmpty()) {
                Iterator<FirAnnotation> it = listComponent2.iterator();
                while (it.hasNext()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, it.next().getSource(), FirNativeErrors.INSTANCE.getREDUNDANT_SWIFT_REFINEMENT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
            FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
            if (firClass != null) {
                FirNativeObjCRefinementOverridesChecker.INSTANCE.check(FirHelpersKt.unsubstitutedScope(firClass, context), declaration.getSymbol(), declaration, context, reporter, listComponent1, listComponent2);
            }
        }
    }

    private final Pair<List<FirAnnotation>, List<FirAnnotation>> findRefinedAnnotations(FirCallableDeclaration firCallableDeclaration, FirSession firSession) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (FirAnnotation firAnnotation : firCallableDeclaration.getAnnotations()) {
            FirClassLikeSymbol<?> annotationClassLikeSymbol = FirAnnotationUtilsKt.toAnnotationClassLikeSymbol(firAnnotation, firSession);
            List<FirAnnotation> resolvedAnnotationsWithClassIds = annotationClassLikeSymbol != null ? annotationClassLikeSymbol.getResolvedAnnotationsWithClassIds() : null;
            if (resolvedAnnotationsWithClassIds == null) {
                resolvedAnnotationsWithClassIds = CollectionsKt.emptyList();
            }
            Iterator<FirAnnotation> it = resolvedAnnotationsWithClassIds.iterator();
            while (it.hasNext()) {
                ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId(it.next(), firSession);
                if (Intrinsics.areEqual(annotationClassId, hidesFromObjCClassId)) {
                    arrayList.add(firAnnotation);
                    break;
                }
                if (Intrinsics.areEqual(annotationClassId, refinesInSwiftClassId)) {
                    arrayList2.add(firAnnotation);
                    break;
                }
            }
        }
        return TuplesKt.to(arrayList, arrayList2);
    }
}
