package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.BuiltinTypes;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirJsNativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsAbstractNativeIndexerChecker;", "Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsAbstractNativeAnnotationChecker;", "requiredAnnotation", "Lorg/jetbrains/kotlin/name/ClassId;", "indexerKind", "", "requiredParametersCount", "", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/lang/String;I)V", "check", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirJsAbstractNativeIndexerChecker extends FirJsAbstractNativeAnnotationChecker {
    private final String indexerKind;
    private final int requiredParametersCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJsAbstractNativeIndexerChecker(ClassId requiredAnnotation, String indexerKind, int i) {
        super(requiredAnnotation);
        Intrinsics.checkNotNullParameter(requiredAnnotation, "requiredAnnotation");
        Intrinsics.checkNotNullParameter(indexerKind, "indexerKind");
        this.indexerKind = indexerKind;
        this.requiredParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsAbstractNativeAnnotationChecker, org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        super.check(declaration, context, reporter);
        List<FirValueParameter> valueParameters = declaration.getValueParameters();
        BuiltinTypes builtinTypes = context.getSession().getBuiltinTypes();
        if (!valueParameters.isEmpty()) {
            FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.first((List) valueParameters);
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
            if (!(coneType instanceof ConeErrorType) && !ConeBuiltinTypeUtilsKt.isString(coneType) && !TypeUtilsKt.isSubtypeOf$default(coneType, FirTypeUtilsKt.getConeType(builtinTypes.getNumberType()), context.getSession(), false, 4, null)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirJsErrors.INSTANCE.getNATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER(), this.indexerKind, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
        if (valueParameters.size() != this.requiredParametersCount) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNATIVE_INDEXER_WRONG_PARAMETER_COUNT(), Integer.valueOf(this.requiredParametersCount), this.indexerKind, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
        for (FirValueParameter firValueParameter2 : valueParameters) {
            if (firValueParameter2.getDefaultValue() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter2.getSource(), FirJsErrors.INSTANCE.getNATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS(), this.indexerKind, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
