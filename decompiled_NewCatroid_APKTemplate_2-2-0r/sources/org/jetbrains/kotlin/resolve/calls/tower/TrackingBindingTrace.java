package org.jetbrains.kotlin.resolve.calls.tower;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: TrackingBindingTrace.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u008c\u0001\u0010\f\u001a\u0004\u0018\u0001H\r\"\u0010\b\u0000\u0010\u000e*\n \u0010*\u0004\u0018\u00010\u000f0\u000f\"\u0010\b\u0001\u0010\r*\n \u0010*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r\u0018\u00010\u00120\u00122\u000e\u0010\u0013\u001a\n \u0010*\u0004\u0018\u0001H\u000eH\u000eH\u0097\u0003¢\u0006\u0002\u0010\u0014J\t\u0010\u0015\u001a\u00020\u0016H\u0097\u0001J\u0097\u0001\u0010\u0017\u001a$\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e \u0010*\u0010\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e0\u00190\u0018\"\u0010\b\u0000\u0010\u000e*\n \u0010*\u0004\u0018\u00010\u000f0\u000f\"\u0010\b\u0001\u0010\r*\n \u0010*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r\u0018\u00010\u001a0\u001aH\u0097\u0001J\u0015\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0001\u0010\u001d\u001a\u00020\u001eH\u0097\u0001J\u0006\u0010\u001f\u001a\u00020 J\u009a\u0001\u0010!\u001a\u00020 \"\u0010\b\u0000\u0010\u000e*\n \u0010*\u0004\u0018\u00010\u000f0\u000f\"\u0010\b\u0001\u0010\r*\n \u0010*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\rH\r\u0018\u00010\u001a0\u001a2\u000e\u0010\u0013\u001a\n \u0010*\u0004\u0018\u0001H\u000eH\u000e2\u000e\u0010\"\u001a\n \u0010*\u0004\u0018\u0001H\rH\rH\u0096\u0001¢\u0006\u0002\u0010#Jx\u0010!\u001a\u00020 \"\u0010\b\u0000\u0010\u000e*\n \u0010*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00050\u0005 \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000eH\u000e\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u001a0\u001a2\u000e\u0010\u0013\u001a\n \u0010*\u0004\u0018\u0001H\u000eH\u000eH\u0096\u0001¢\u0006\u0002\u0010$J\u001f\u0010%\u001a\u00020 2\b\b\u0001\u0010\u001d\u001a\u00020\u001e2\n\b\u0001\u0010&\u001a\u0004\u0018\u00010\u001cH\u0096\u0001J\u0010\u0010'\u001a\u00020 2\u0006\u0010(\u001a\u00020)H\u0016J\t\u0010*\u001a\u00020\u0005H\u0096\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TrackingBindingTrace;", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "trace", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "reported", "", "getReported", "()Z", "setReported", "(Z)V", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getBindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getKeys", "", "", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "markAsReported", "", PsiKeyword.RECORD, "value", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;)V", "recordType", ModuleXmlParser.TYPE, "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "wantsDiagnostics", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrackingBindingTrace implements BindingTrace {
    private boolean reported;
    private final BindingTrace trace;

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
        return (V) this.trace.get(slice, key);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public BindingContext getBindingContext() {
        return this.trace.getBindingContext();
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> slice) {
        return this.trace.getKeys(slice);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public KotlinType getType(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.trace.getType(expression);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K> void record(WritableSlice<K, Boolean> slice, K key) {
        this.trace.record(slice, key);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> slice, K key, V value) {
        this.trace.record(slice, key, value);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public void recordType(KtExpression expression, KotlinType type) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.trace.recordType(expression, type);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.trace.wantsDiagnostics();
    }

    public TrackingBindingTrace(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.trace = trace;
    }

    public final BindingTrace getTrace() {
        return this.trace;
    }

    public final boolean getReported() {
        return this.reported;
    }

    public final void setReported(boolean z) {
        this.reported = z;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void report(Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Collection<Diagnostic> collectionForElement = getBindingContext().getDiagnostics().noSuppression().forElement(diagnostic.getPsiElement());
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            Iterator<T> it = collectionForElement.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual((Diagnostic) it.next(), diagnostic)) {
                    return;
                }
            }
        }
        this.trace.report(diagnostic);
        this.reported = true;
    }

    public final void markAsReported() {
        this.reported = true;
    }
}
