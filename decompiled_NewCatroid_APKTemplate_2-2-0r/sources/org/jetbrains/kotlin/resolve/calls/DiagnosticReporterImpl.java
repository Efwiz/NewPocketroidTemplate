package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;

/* JADX INFO: compiled from: DiagnosticReporterImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/DiagnosticReporterImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/psi/Call;)V", "onCallArgument", "", "callArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DiagnosticReporterImpl implements DiagnosticReporter {
    private final BindingTrace bindingTrace;
    private final Call call;

    public DiagnosticReporterImpl(BindingTrace bindingTrace, Call call) {
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(call, "call");
        this.bindingTrace = bindingTrace;
        this.call = call;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.DiagnosticReporter
    public void onCallArgument(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(callArgument, "callArgument");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        ParametrizedDiagnostic diagnostic2 = CallDiagnosticToDiagnostic.INSTANCE.toDiagnostic(((ValueArgument) callArgument).getCallElement(), diagnostic);
        if (diagnostic2 != null) {
            this.bindingTrace.report(diagnostic2);
        }
    }
}
