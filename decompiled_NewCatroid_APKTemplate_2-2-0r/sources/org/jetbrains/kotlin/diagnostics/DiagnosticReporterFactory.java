package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.PendingDiagnosticsCollectorWithSuppress;
import org.jetbrains.kotlin.diagnostics.impl.SimpleDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.impl.SimpleDiagnosticsCollectorWithSuppress;

/* JADX INFO: compiled from: DiagnosticReporterFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u001a\b\u0002\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0006J,\u0010\n\u001a\u00020\u000b2\u001a\b\u0002\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00062\b\b\u0002\u0010\f\u001a\u00020\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporterFactory;", "", "()V", "createPendingReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/PendingDiagnosticsCollectorWithSuppress;", "rawReport", "Lkotlin/Function2;", "", "", "", "createReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "disableSuppress", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticReporterFactory {
    public static final DiagnosticReporterFactory INSTANCE = new DiagnosticReporterFactory();

    private DiagnosticReporterFactory() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BaseDiagnosticsCollector createReporter$default(DiagnosticReporterFactory diagnosticReporterFactory, Function2 function2, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            function2 = new Function2<Boolean, String, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory.createReporter.1
                public final void invoke(boolean z2, String str) {
                    Intrinsics.checkNotNullParameter(str, "<anonymous parameter 1>");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, String str) {
                    invoke(bool.booleanValue(), str);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return diagnosticReporterFactory.createReporter(function2, z);
    }

    public final BaseDiagnosticsCollector createReporter(Function2<? super Boolean, ? super String, Unit> rawReport, boolean disableSuppress) {
        Intrinsics.checkNotNullParameter(rawReport, "rawReport");
        if (disableSuppress) {
            return new SimpleDiagnosticsCollector(rawReport);
        }
        return new SimpleDiagnosticsCollectorWithSuppress(rawReport);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PendingDiagnosticsCollectorWithSuppress createPendingReporter$default(DiagnosticReporterFactory diagnosticReporterFactory, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            function2 = new Function2<Boolean, String, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory.createPendingReporter.1
                public final void invoke(boolean z, String str) {
                    Intrinsics.checkNotNullParameter(str, "<anonymous parameter 1>");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, String str) {
                    invoke(bool.booleanValue(), str);
                    return Unit.INSTANCE;
                }
            };
        }
        return diagnosticReporterFactory.createPendingReporter(function2);
    }

    public final PendingDiagnosticsCollectorWithSuppress createPendingReporter(Function2<? super Boolean, ? super String, Unit> rawReport) {
        Intrinsics.checkNotNullParameter(rawReport, "rawReport");
        return new PendingDiagnosticsCollectorWithSuppress(rawReport);
    }
}
