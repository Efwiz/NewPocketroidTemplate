package org.jetbrains.kotlin.js.parser;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;

/* JADX INFO: compiled from: parserUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J \u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/AccumulatingReporter;", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/ErrorReporter;", "()V", "hasErrors", "", "getHasErrors", "()Z", "setHasErrors", "(Z)V", AbstractDiagnosticCollector.SUPPRESS_ALL_WARNINGS, "", "Lorg/jetbrains/kotlin/js/parser/AccumulatingReporter$Warning;", "getWarnings", "()Ljava/util/List;", "error", "", CrashHianalyticsData.MESSAGE, "", "startPosition", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "endPosition", CompilerOptions.WARNING, "Warning", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AccumulatingReporter implements ErrorReporter {
    private boolean hasErrors;
    private final List<Warning> warnings = new ArrayList();

    public final boolean getHasErrors() {
        return this.hasErrors;
    }

    public final void setHasErrors(boolean z) {
        this.hasErrors = z;
    }

    public final List<Warning> getWarnings() {
        return this.warnings;
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    public void warning(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
        this.warnings.add(new Warning(message, startPosition, endPosition));
    }

    @Override // org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.ErrorReporter
    /* JADX INFO: renamed from: error */
    public void mo6456error(String message, CodePosition startPosition, CodePosition endPosition) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(startPosition, "startPosition");
        Intrinsics.checkNotNullParameter(endPosition, "endPosition");
        this.hasErrors = true;
    }

    /* JADX INFO: compiled from: parserUtils.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/AccumulatingReporter$Warning;", "", CrashHianalyticsData.MESSAGE, "", "startPosition", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "endPosition", "(Ljava/lang/String;Lcom/google/gwt/dev/js/rhino/CodePosition;Lcom/google/gwt/dev/js/rhino/CodePosition;)V", "getEndPosition", "()Lcom/google/gwt/dev/js/rhino/CodePosition;", "getMessage", "()Ljava/lang/String;", "getStartPosition", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Warning {
        private final CodePosition endPosition;
        private final String message;
        private final CodePosition startPosition;

        public Warning(String message, CodePosition startPosition, CodePosition endPosition) {
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(startPosition, "startPosition");
            Intrinsics.checkNotNullParameter(endPosition, "endPosition");
            this.message = message;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        public final CodePosition getEndPosition() {
            return this.endPosition;
        }

        public final String getMessage() {
            return this.message;
        }

        public final CodePosition getStartPosition() {
            return this.startPosition;
        }
    }
}
