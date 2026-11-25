package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.util.SmartFMap;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class ObservableBindingTrace implements BindingTrace {
    private SmartFMap<WritableSlice, RecordHandler> handlers = SmartFMap.emptyMap();
    private final BindingTrace originalTrace;

    public interface RecordHandler<K, V> {
        void handleRecord(WritableSlice<K, V> writableSlice, K k, V v);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "org/jetbrains/kotlin/resolve/ObservableBindingTrace";
                break;
            case 3:
            case 4:
                objArr[0] = "expression";
                break;
            case 5:
                objArr[0] = "slice";
                break;
            case 6:
                objArr[0] = "handler";
                break;
            default:
                objArr[0] = "diagnostic";
                break;
        }
        if (i == 1) {
            objArr[1] = "getBindingContext";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/resolve/ObservableBindingTrace";
        } else {
            objArr[1] = "getKeys";
        }
        switch (i) {
            case 1:
            case 2:
                break;
            case 3:
                objArr[2] = "getType";
                break;
            case 4:
                objArr[2] = "recordType";
                break;
            case 5:
            case 6:
                objArr[2] = "addHandler";
                break;
            default:
                objArr[2] = "report";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ObservableBindingTrace(BindingTrace bindingTrace) {
        this.originalTrace = bindingTrace;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void report(Diagnostic diagnostic) {
        if (diagnostic == null) {
            $$$reportNull$$$0(0);
        }
        this.originalTrace.report(diagnostic);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    /* JADX INFO: renamed from: getBindingContext */
    public BindingContext getContext() {
        BindingContext context = this.originalTrace.getContext();
        if (context == null) {
            $$$reportNull$$$0(1);
        }
        return context;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> writableSlice, K k, V v) {
        this.originalTrace.record(writableSlice, k, v);
        RecordHandler recordHandler = this.handlers.get(writableSlice);
        if (recordHandler != null) {
            recordHandler.handleRecord(writableSlice, k, v);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K> void record(WritableSlice<K, Boolean> writableSlice, K k) {
        record(writableSlice, k, true);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
        return (V) this.originalTrace.get(readOnlySlice, k);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
        Collection<K> keys = this.originalTrace.getKeys(writableSlice);
        if (keys == null) {
            $$$reportNull$$$0(2);
        }
        return keys;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public KotlinType getType(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(3);
        }
        return this.originalTrace.getType(ktExpression);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public void recordType(KtExpression ktExpression, KotlinType kotlinType) {
        if (ktExpression == null) {
            $$$reportNull$$$0(4);
        }
        this.originalTrace.recordType(ktExpression, kotlinType);
    }

    public <K, V> ObservableBindingTrace addHandler(WritableSlice<K, V> writableSlice, RecordHandler<K, V> recordHandler) {
        if (writableSlice == null) {
            $$$reportNull$$$0(5);
        }
        if (recordHandler == null) {
            $$$reportNull$$$0(6);
        }
        this.handlers = this.handlers.plus(writableSlice, recordHandler);
        return this;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.originalTrace.wantsDiagnostics();
    }

    public String toString() {
        return "ObservableTrace over " + this.originalTrace.toString();
    }
}
