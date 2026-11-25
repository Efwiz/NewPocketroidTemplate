package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: AbstractFilteringTrace.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J=\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\t2\u0006\u0010\u000e\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J5\u0010\u0013\u001a\u00020\u0014\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\tH$¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0014H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AbstractFilteringTrace;", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "parentTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "name", "", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Ljava/lang/String;)V", PsiKeyword.RECORD, "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "slice", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "shouldBeHiddenFromParent", "", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;)Z", "wantsDiagnostics", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFilteringTrace extends DelegatingBindingTrace {
    private final BindingTrace parentTrace;

    protected abstract <K, V> boolean shouldBeHiddenFromParent(WritableSlice<K, V> slice, K key);

    /* JADX WARN: Illegal instructions before constructor call */
    public AbstractFilteringTrace(BindingTrace parentTrace, String name2) {
        Intrinsics.checkNotNullParameter(parentTrace, "parentTrace");
        Intrinsics.checkNotNullParameter(name2, "name");
        BindingContext context = parentTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        super(context, name2, true, BindingTraceFilter.INSTANCE.getACCEPT_ALL(), false, null, 32, null);
        this.parentTrace = parentTrace;
    }

    @Override // org.jetbrains.kotlin.resolve.DelegatingBindingTrace, org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> slice, K key, V value) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        if (shouldBeHiddenFromParent(slice, key)) {
            super.record(slice, key, value);
        } else {
            this.parentTrace.record(slice, key, value);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.DelegatingBindingTrace, org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void report(Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        DiagnosticSink.DiagnosticsCallback diagnosticsCallback = getDiagnosticsCallback();
        if (diagnosticsCallback != null) {
            diagnosticsCallback.callback(diagnostic);
        }
        this.parentTrace.report(diagnostic);
    }

    @Override // org.jetbrains.kotlin.resolve.DelegatingBindingTrace, org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.parentTrace.wantsDiagnostics();
    }
}
