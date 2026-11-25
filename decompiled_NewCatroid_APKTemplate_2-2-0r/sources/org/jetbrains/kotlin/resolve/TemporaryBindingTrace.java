package org.jetbrains.kotlin.resolve;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes6.dex */
public class TemporaryBindingTrace extends DelegatingBindingTrace {
    protected final BindingTrace trace;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 4) {
            objArr[0] = "trace";
        } else {
            objArr[0] = "filter";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/TemporaryBindingTrace";
        if (i == 3) {
            objArr[2] = "<init>";
        } else if (i != 4) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else {
            objArr[2] = "commit";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static TemporaryBindingTrace create(BindingTrace bindingTrace, String str) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        return create(bindingTrace, str, BindingTraceFilter.INSTANCE.getACCEPT_ALL());
    }

    public static TemporaryBindingTrace create(BindingTrace bindingTrace, String str, BindingTraceFilter bindingTraceFilter) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(1);
        }
        return new TemporaryBindingTrace(bindingTrace, str, bindingTraceFilter);
    }

    public static TemporaryBindingTrace create(BindingTrace bindingTrace, String str, Object obj) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(2);
        }
        return create(bindingTrace, AnalyzingUtils.formDebugNameForBindingTrace(str, obj));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected TemporaryBindingTrace(BindingTrace bindingTrace, String str, BindingTraceFilter bindingTraceFilter) {
        super(bindingTrace.getBindingContext(), str, true, bindingTraceFilter, false, null);
        if (bindingTrace == null) {
            $$$reportNull$$$0(3);
        }
        this.trace = bindingTrace;
    }

    public void commit() {
        addOwnDataTo(this.trace);
        clear();
    }

    public void commit(TraceEntryFilter traceEntryFilter, boolean z) {
        if (traceEntryFilter == null) {
            $$$reportNull$$$0(4);
        }
        addOwnDataTo(this.trace, traceEntryFilter, z);
        clear();
    }

    @Override // org.jetbrains.kotlin.resolve.DelegatingBindingTrace, org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.trace.wantsDiagnostics();
    }
}
