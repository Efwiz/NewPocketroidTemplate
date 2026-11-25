package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmRedundantRepeatableChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmRedundantRepeatableChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmRedundantRepeatableChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmRedundantRepeatableChecker INSTANCE = new FirJvmRedundantRepeatableChecker();

    private FirJvmRedundantRepeatableChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FqName fqNameAsSingleFqName;
        FqName fqNameAsSingleFqName2;
        ConeClassLikeLookupTag lookupTag;
        ConeClassLikeLookupTag lookupTag2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirDeclaration firDeclaration = declaration;
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.INSTANCE.getRepeatable(), context.getSession());
        FirAnnotation annotationByClassId2 = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.INSTANCE.getJvmRepeatable(), context.getSession());
        if (annotationByClassId2 == null) {
            annotationByClassId2 = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.Java.INSTANCE.getRepeatable(), context.getSession());
        }
        if (annotationByClassId == null || annotationByClassId2 == null) {
            return;
        }
        KtSourceElement source = annotationByClassId.getSource();
        KtDiagnosticFactory2<FqName, FqName> redundant_repeatable_annotation = FirJvmErrors.INSTANCE.getREDUNDANT_REPEATABLE_ANNOTATION();
        FirTypeRef annotationTypeRef = annotationByClassId.getAnnotationTypeRef();
        ClassId classId = null;
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        ClassId classId2 = (coneClassLikeType == null || (lookupTag2 = coneClassLikeType.getLookupTag()) == null) ? null : lookupTag2.getClassId();
        if (classId2 == null || (fqNameAsSingleFqName = classId2.asSingleFqName()) == null) {
            fqNameAsSingleFqName = FqName.ROOT;
        }
        FqName fqName = fqNameAsSingleFqName;
        Intrinsics.checkNotNull(fqName);
        FirTypeRef annotationTypeRef2 = annotationByClassId2.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef2 = annotationTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef2 : null;
        ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
        ConeClassLikeType coneClassLikeType2 = type2 instanceof ConeClassLikeType ? (ConeClassLikeType) type2 : null;
        if (coneClassLikeType2 != null && (lookupTag = coneClassLikeType2.getLookupTag()) != null) {
            classId = lookupTag.getClassId();
        }
        if (classId == null || (fqNameAsSingleFqName2 = classId.asSingleFqName()) == null) {
            fqNameAsSingleFqName2 = FqName.ROOT;
        }
        FqName fqName2 = fqNameAsSingleFqName2;
        Intrinsics.checkNotNull(fqName2);
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, redundant_repeatable_annotation, fqName, fqName2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }
}
