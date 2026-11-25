package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirWhenSubjectChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenSubjectChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirWhenSubjectChecker extends FirExpressionChecker<FirWhenExpression> {
    public static final FirWhenSubjectChecker INSTANCE = new FirWhenSubjectChecker();

    private FirWhenSubjectChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirWhenExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        KtSourceElement child$default;
        KtSourceElement source2;
        KtSourceElement source3;
        KtSourceElement source4;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirExpression subject = expression.getSubject();
        FirVariable subjectVariable = expression.getSubjectVariable();
        FirExpression firExpression = subjectVariable != null ? subjectVariable : subject;
        if (firExpression == null || (source = firExpression.getSource()) == null) {
            return;
        }
        KtSourceElement child$default2 = null;
        if (Intrinsics.areEqual((subject == null || (source4 = subject.getSource()) == null) ? null : source4.getElementType(), KtNodeTypes.DESTRUCTURING_DECLARATION)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getILLEGAL_DECLARATION_IN_WHEN_SUBJECT(), "destructuring declaration", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (subjectVariable == null || (source3 = subjectVariable.getSource()) == null) {
            child$default = null;
        } else {
            KtKeywordToken VAR_KEYWORD = KtTokens.VAR_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(VAR_KEYWORD, "VAR_KEYWORD");
            child$default = FirSourceUtilsKt.getChild$default(source3, (IElementType) VAR_KEYWORD, 0, 0, false, 14, (Object) null);
        }
        if (child$default != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getILLEGAL_DECLARATION_IN_WHEN_SUBJECT(), PsiKeyword.VAR, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (subjectVariable != null && (source2 = subjectVariable.getSource()) != null) {
            IElementType PROPERTY_DELEGATE = KtNodeTypes.PROPERTY_DELEGATE;
            Intrinsics.checkNotNullExpressionValue(PROPERTY_DELEGATE, "PROPERTY_DELEGATE");
            child$default2 = FirSourceUtilsKt.getChild$default(source2, PROPERTY_DELEGATE, 0, 0, false, 14, (Object) null);
        }
        if (child$default2 != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getILLEGAL_DECLARATION_IN_WHEN_SUBJECT(), "delegated property", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        } else {
            if (subjectVariable == null || subjectVariable.getInitializer() != null) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getILLEGAL_DECLARATION_IN_WHEN_SUBJECT(), "variable without initializer", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
