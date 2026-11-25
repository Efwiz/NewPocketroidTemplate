package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJavaAnnotationsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJavaAnnotationsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationChecker;", "()V", "javaToKotlinNameMap", "", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaAnnotationsChecker extends FirExpressionChecker<FirAnnotation> {
    public static final FirJavaAnnotationsChecker INSTANCE = new FirJavaAnnotationsChecker();
    private static final Map<ClassId, ClassId> javaToKotlinNameMap = MapsKt.mapOf(TuplesKt.to(StandardClassIds.Annotations.Java.INSTANCE.getTarget(), StandardClassIds.Annotations.INSTANCE.getTarget()), TuplesKt.to(StandardClassIds.Annotations.Java.INSTANCE.getRetention(), StandardClassIds.Annotations.INSTANCE.getRetention()), TuplesKt.to(StandardClassIds.Annotations.Java.INSTANCE.getDeprecated(), StandardClassIds.Annotations.INSTANCE.getDeprecated()), TuplesKt.to(StandardClassIds.Annotations.Java.INSTANCE.getDocumented(), StandardClassIds.Annotations.INSTANCE.getMustBeDocumented()));

    private FirJavaAnnotationsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotation expression, CheckerContext context, DiagnosticReporter reporter) {
        ConeClassLikeLookupTag lookupTag;
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
        if (Intrinsics.areEqual((firDeclaration == null || (source = firDeclaration.getSource()) == null) ? null : source.getKind(), KtRealSourceElementKind.INSTANCE)) {
            FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(expression.getAnnotationTypeRef(), context.getSession());
            if ((regularClassSymbol != null ? regularClassSymbol.getOrigin() : null) instanceof FirDeclarationOrigin.Java) {
                FirTypeRef annotationTypeRef = expression.getAnnotationTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
                if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
                    return;
                }
                ClassId classId = javaToKotlinNameMap.get(lookupTag.getClassId());
                if (classId != null) {
                    KtSourceElement source2 = expression.getSource();
                    KtDiagnosticFactory1<FqName> deprecated_java_annotation = FirJvmErrors.INSTANCE.getDEPRECATED_JAVA_ANNOTATION();
                    FqName fqNameAsSingleFqName = classId.asSingleFqName();
                    Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, deprecated_java_annotation, fqNameAsSingleFqName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if (expression instanceof FirAnnotationCall) {
                    FirArgumentList argumentList = ((FirAnnotationCall) expression).getArgumentList();
                    if (argumentList instanceof FirResolvedArgumentList) {
                        for (Map.Entry<FirExpression, FirValueParameter> entry : ((FirResolvedArgumentList) argumentList).getMapping().entrySet()) {
                            FirExpression key = entry.getKey();
                            if (!Intrinsics.areEqual(entry.getValue().getName(), StandardClassIds.Annotations.ParameterNames.INSTANCE.getValue()) && !(key instanceof FirWrappedArgumentExpression)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, key.getSource(), FirJvmErrors.INSTANCE.getPOSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            }
                        }
                    }
                }
            }
        }
    }
}
