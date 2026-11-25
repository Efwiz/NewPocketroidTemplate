package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirVolatileAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirVolatileAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "VOLATILE_CLASS_IDS", "", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirVolatileAnnotationChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirVolatileAnnotationChecker INSTANCE = new FirVolatileAnnotationChecker();
    private static final List<ClassId> VOLATILE_CLASS_IDS = CollectionsKt.listOf((Object[]) new ClassId[]{StandardClassIds.Annotations.INSTANCE.getVolatile(), StandardClassIds.Annotations.INSTANCE.getJvmVolatile()});

    private FirVolatileAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirBackingField backingField;
        List<FirAnnotation> annotations;
        FirAnnotation annotationByClassIds;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtRealSourceElementKind.INSTANCE) || (backingField = declaration.getBackingField()) == null || (annotations = backingField.getAnnotations()) == null || (annotationByClassIds = FirAnnotationUtilsKt.getAnnotationByClassIds(annotations, VOLATILE_CLASS_IDS, context.getSession())) == null) {
            return;
        }
        if (!declaration.getIsVar()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassIds.getSource(), FirErrors.INSTANCE.getVOLATILE_ON_VALUE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (declaration.getDelegateFieldSymbol() != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassIds.getSource(), FirErrors.INSTANCE.getVOLATILE_ON_DELEGATE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
