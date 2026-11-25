package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterInQualifiedAccess;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;

/* JADX INFO: compiled from: FirKClassWithIncorrectTypeArgumentChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00072\u0006\u0010\r\u001a\u00020\u0013H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirKClassWithIncorrectTypeArgumentChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "()V", "typeParameterFromError", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getTypeParameterFromError", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isKClassTypeWithErrorOrNullableArgument", "", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirKClassWithIncorrectTypeArgumentChecker extends FirDeclarationChecker<FirCallableDeclaration> {
    public static final FirKClassWithIncorrectTypeArgumentChecker INSTANCE = new FirKClassWithIncorrectTypeArgumentChecker();

    private FirKClassWithIncorrectTypeArgumentChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (context.getContainingDeclarations().size() > 1 || (source = declaration.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef());
        if (isKClassTypeWithErrorOrNullableArgument(coneType, TypeComponentsKt.getTypeContext(context.getSession()))) {
            ConeTypeProjection coneTypeProjection = coneType.getTypeArguments()[0];
            Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection");
            FirTypeParameterSymbol typeParameterFromError = getTypeParameterFromError(((ConeKotlinTypeProjection) coneTypeProjection).getType());
            if (typeParameterFromError != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getKCLASS_WITH_NULLABLE_TYPE_PARAMETER_IN_SIGNATURE(), typeParameterFromError, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final boolean isKClassTypeWithErrorOrNullableArgument(ConeKotlinType coneKotlinType, ConeInferenceContext coneInferenceContext) {
        ConeTypeProjection coneTypeProjection;
        ConeKotlinType type;
        if (InferenceUtilsKt.isKClassType(coneKotlinType) && (coneTypeProjection = (ConeTypeProjection) CollectionsKt.singleOrNull(ArraysKt.toList(coneKotlinType.getTypeArguments()))) != null) {
            if (coneTypeProjection instanceof ConeStarProjection) {
                type = null;
            } else {
                if (!(coneTypeProjection instanceof ConeKotlinTypeProjection)) {
                    throw new NoWhenBranchMatchedException();
                }
                type = ((ConeKotlinTypeProjection) coneTypeProjection).getType();
            }
            if (type != null) {
                FirTypeParameterSymbol typeParameterFromError = INSTANCE.getTypeParameterFromError(type);
                if (typeParameterFromError != null) {
                    return coneInferenceContext.isNullableType(FirNestedClassifierScopeKt.toConeType(typeParameterFromError));
                }
                return (type instanceof ConeErrorType) || coneInferenceContext.isNullableType(type);
            }
        }
        return false;
    }

    private final FirTypeParameterSymbol getTypeParameterFromError(ConeKotlinType coneKotlinType) {
        ConeErrorType coneErrorType = coneKotlinType instanceof ConeErrorType ? (ConeErrorType) coneKotlinType : null;
        ConeDiagnostic diagnostic = coneErrorType != null ? coneErrorType.getDiagnostic() : null;
        ConeTypeParameterInQualifiedAccess coneTypeParameterInQualifiedAccess = diagnostic instanceof ConeTypeParameterInQualifiedAccess ? (ConeTypeParameterInQualifiedAccess) diagnostic : null;
        if (coneTypeParameterInQualifiedAccess != null) {
            return coneTypeParameterInQualifiedAccess.getSymbol();
        }
        return null;
    }
}
