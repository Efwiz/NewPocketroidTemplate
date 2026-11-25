package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.lexer.KtKeywordToken;

/* JADX INFO: compiled from: FirFunctionParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000e\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0010\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkParameterTypes", "function", "checkUninitializedParameter", "checkValOrVarParameter", "checkVarargParameters", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionParameterChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirFunctionParameterChecker INSTANCE = new FirFunctionParameterChecker();

    private FirFunctionParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkVarargParameters(declaration, context, reporter);
        checkParameterTypes(declaration, context, reporter);
        checkUninitializedParameter(declaration, context, reporter);
        checkValOrVarParameter(declaration, context, reporter);
    }

    private final void checkParameterTypes(FirFunction function, CheckerContext context, DiagnosticReporter reporter) {
        if (function instanceof FirAnonymousFunction) {
            return;
        }
        for (FirValueParameter firValueParameter : function.getValueParameters()) {
            FirTypeRef returnTypeRef = firValueParameter.getReturnTypeRef();
            if (returnTypeRef instanceof FirErrorTypeRef) {
                KtSourceElement source = returnTypeRef.getSource();
                if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtRealSourceElementKind.INSTANCE)) {
                    ConeDiagnostic diagnostic = ((FirErrorTypeRef) returnTypeRef).getDiagnostic();
                    if ((diagnostic instanceof ConeSimpleDiagnostic) && ((ConeSimpleDiagnostic) diagnostic).getKind() == DiagnosticKind.ValueParameterWithNoTypeAnnotation) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirErrors.INSTANCE.getVALUE_PARAMETER_WITH_NO_TYPE_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
    }

    private final void checkVarargParameters(FirFunction function, CheckerContext context, DiagnosticReporter reporter) {
        List<FirValueParameter> valueParameters = function.getValueParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (((FirValueParameter) obj).getIsVararg()) {
                arrayList.add(obj);
            }
        }
        ArrayList<FirValueParameter> arrayList2 = arrayList;
        if (arrayList2.size() > 1) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirValueParameter) it.next()).getSource(), FirErrors.INSTANCE.getMULTIPLE_VARARG_PARAMETERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        for (FirValueParameter firValueParameter : arrayList2) {
            ConeKotlinType coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()));
            if (coneKotlinTypeArrayElementType != null && (ConeBuiltinTypeUtilsKt.isNothingOrNullableNothing(FirHelpersKt.leastUpperBound(coneKotlinTypeArrayElementType, context.getSession())) || (FirHelpersKt.isValueClass(coneKotlinTypeArrayElementType, context.getSession()) && !ConeBuiltinTypeUtilsKt.isUnsignedTypeOrNullableUnsignedType(coneKotlinTypeArrayElementType)))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirErrors.INSTANCE.getFORBIDDEN_VARARG_PARAMETER_TYPE(), coneKotlinTypeArrayElementType, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final void checkUninitializedParameter(final FirFunction function, final CheckerContext context, final DiagnosticReporter reporter) {
        Iterator<FirValueParameter> it = function.getValueParameters().iterator();
        final int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            FirExpression defaultValue = it.next().getDefaultValue();
            if (defaultValue != null) {
                defaultValue.accept(new FirVisitorVoid() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionParameterChecker.checkUninitializedParameter.1
                    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
                    /* JADX INFO: renamed from: visitElement */
                    public void mo6787visitElement(FirElement element) {
                        Intrinsics.checkNotNullParameter(element, "element");
                        element.acceptChildren(this);
                    }

                    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
                    public void visitQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression) {
                        int iIndexOf;
                        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
                        FirValueParameterSymbol resolvedValueParameterSymbol$default = FirReferenceUtilsKt.toResolvedValueParameterSymbol$default(qualifiedAccessExpression.getCalleeReference(), false, 1, null);
                        if (resolvedValueParameterSymbol$default != null && (iIndexOf = function.getValueParameters().indexOf(resolvedValueParameterSymbol$default.getFir())) >= 0 && i <= iIndexOf) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, qualifiedAccessExpression.getSource(), FirErrors.INSTANCE.getUNINITIALIZED_PARAMETER(), resolvedValueParameterSymbol$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        }
                    }

                    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
                    public void visitPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression) {
                        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
                        visitQualifiedAccessExpression(propertyAccessExpression);
                    }
                });
            }
            i = i2;
        }
    }

    private final void checkValOrVarParameter(FirFunction function, CheckerContext context, DiagnosticReporter reporter) {
        KtKeywordToken valOrVarKeyword;
        boolean z = function instanceof FirConstructor;
        if (z && ((FirConstructor) function).getIsPrimary()) {
            return;
        }
        Iterator<FirValueParameter> it = function.getValueParameters().iterator();
        while (it.hasNext()) {
            KtSourceElement source = it.next().getSource();
            if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) && (valOrVarKeyword = FirKeywordUtilsKt.getValOrVarKeyword(source)) != null) {
                if (z) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getVAL_OR_VAR_ON_SECONDARY_CONSTRUCTOR_PARAMETER(), valOrVarKeyword, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                } else {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getVAL_OR_VAR_ON_FUN_PARAMETER(), valOrVarKeyword, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
