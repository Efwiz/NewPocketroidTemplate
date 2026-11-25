package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: FirTypeParametersInObjectChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParametersInObjectChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParametersInObjectChecker extends FirDeclarationChecker<FirClass> {
    public static final FirTypeParametersInObjectChecker INSTANCE = new FirTypeParametersInObjectChecker();

    private FirTypeParametersInObjectChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement child$default;
        KtDiagnosticFactory0 type_parameters_in_anonymous_object;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() == ClassKind.OBJECT && (declaration instanceof FirRegularClass)) {
            if (declaration.getTypeParameters().isEmpty()) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getTYPE_PARAMETERS_IN_OBJECT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (declaration.getClassKind() == ClassKind.CLASS && (declaration instanceof FirAnonymousObject)) {
            KtSourceElement source = declaration.getSource();
            if (source != null) {
                IElementType TYPE_PARAMETER_LIST = KtNodeTypes.TYPE_PARAMETER_LIST;
                Intrinsics.checkNotNullExpressionValue(TYPE_PARAMETER_LIST, "TYPE_PARAMETER_LIST");
                child$default = FirSourceUtilsKt.getChild$default(source, TYPE_PARAMETER_LIST, 0, 1, false, 10, (Object) null);
            } else {
                child$default = null;
            }
            if (child$default != null) {
                if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ProhibitTypeParametersInAnonymousObjects)) {
                    type_parameters_in_anonymous_object = FirErrors.INSTANCE.getTYPE_PARAMETERS_IN_OBJECT();
                } else {
                    type_parameters_in_anonymous_object = FirErrors.INSTANCE.getTYPE_PARAMETERS_IN_ANONYMOUS_OBJECT();
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), type_parameters_in_anonymous_object, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
