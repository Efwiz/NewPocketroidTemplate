package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;

/* JADX INFO: compiled from: FirJvmPackageNameAnnotationsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmPackageNameAnnotationsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationChecker;", "()V", "jvmPackageNameClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmPackageNameAnnotationsChecker extends FirExpressionChecker<FirAnnotation> {
    public static final FirJvmPackageNameAnnotationsChecker INSTANCE = new FirJvmPackageNameAnnotationsChecker();
    private static final ClassId jvmPackageNameClassId;

    private FirJvmPackageNameAnnotationsChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(new FqName("kotlin.jvm.JvmPackageName"));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        jvmPackageNameClassId = classId;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotation expression, CheckerContext context, DiagnosticReporter reporter) {
        ConeClassLikeLookupTag lookupTag;
        String stringArgument;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirTypeRef annotationTypeRef = expression.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null || !Intrinsics.areEqual(lookupTag.getClassId(), jvmPackageNameClassId) || (stringArgument = FirAnnotationUtilsKt.getStringArgument(expression, StandardNames.NAME)) == null) {
            return;
        }
        if (stringArgument.length() == 0) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_CANNOT_BE_EMPTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else if (!FqNamesUtilKt.isValidJavaFqName(stringArgument)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_MUST_BE_VALID_NAME(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        FirFile containingFile = context.getContainingFile();
        if (containingFile == null) {
            return;
        }
        List<FirDeclaration> declarations = containingFile.getDeclarations();
        if ((declarations instanceof Collection) && declarations.isEmpty()) {
            return;
        }
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            if (((FirDeclaration) it.next()) instanceof FirClass) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
    }
}
