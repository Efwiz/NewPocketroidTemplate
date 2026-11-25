package org.jetbrains.kotlin.js.resolve.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.google.gwt.dev.js.parserExceptions.AbortParsingException;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePositionKt;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: JsCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J4\u0010\u0013\u001a\u00020\u000f2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bH\u0002J \u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCodeErrorReporter;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ErrorReporter;", "nodeToReport", "Lorg/jetbrains/kotlin/psi/KtExpression;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Ljava/lang/String;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "absoluteOffset", "", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "getAbsoluteOffset", "(Lcom/google/gwt/dev/js/rhino/CodePosition;)I", "error", "", CrashHianalyticsData.MESSAGE, "startPosition", "endPosition", "report", "diagnosticFactory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallData;", CompilerOptions.WARNING, "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCodeErrorReporter implements ErrorReporter {
    private final String code;
    private final KtExpression nodeToReport;
    private final BindingTrace trace;

    public JsCodeErrorReporter(KtExpression nodeToReport, String code, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(nodeToReport, "nodeToReport");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.nodeToReport = nodeToReport;
        this.code = code;
        this.trace = trace;
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    public void warning(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
        DiagnosticFactory1<KtExpression, JsCallData> JSCODE_WARNING = ErrorsJs.JSCODE_WARNING;
        Intrinsics.checkNotNullExpressionValue(JSCODE_WARNING, "JSCODE_WARNING");
        report(JSCODE_WARNING, message, startPosition, endPosition);
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    /* JADX INFO: renamed from: error */
    public void mo6456error(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
        DiagnosticFactory1<KtExpression, JsCallData> JSCODE_ERROR = ErrorsJs.JSCODE_ERROR;
        Intrinsics.checkNotNullExpressionValue(JSCODE_ERROR, "JSCODE_ERROR");
        report(JSCODE_ERROR, message, startPosition, endPosition);
        throw new AbortParsingException();
    }

    private final void report(DiagnosticFactory1<KtExpression, JsCallData> diagnosticFactory, String message, CodePosition startPosition, CodePosition endPosition) {
        JsCallDataWithCode jsCallDataWithCode;
        if (JsCallCheckerKt.isConstantStringLiteral(this.nodeToReport)) {
            jsCallDataWithCode = new JsCallData(new TextRange(getAbsoluteOffset(startPosition), getAbsoluteOffset(endPosition)), message);
        } else {
            TextRange textRange = this.nodeToReport.getTextRange();
            TextRange textRange2 = new TextRange(CodePositionKt.offsetOf(this.code, startPosition), CodePositionKt.offsetOf(this.code, endPosition));
            Intrinsics.checkNotNull(textRange);
            jsCallDataWithCode = new JsCallDataWithCode(textRange, message, this.code, textRange2);
        }
        Diagnostic diagnosticOn = diagnosticFactory.on(this.nodeToReport, jsCallDataWithCode);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        this.trace.report(diagnosticOn);
    }

    private final int getAbsoluteOffset(CodePosition codePosition) {
        return this.nodeToReport.getTextOffset() + this.nodeToReport.getFirstChild().getTextLength() + CodePositionKt.offsetOf(this.code, codePosition);
    }
}
