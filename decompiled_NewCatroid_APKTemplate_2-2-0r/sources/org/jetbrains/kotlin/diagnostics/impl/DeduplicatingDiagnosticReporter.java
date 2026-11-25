package org.jetbrains.kotlin.diagnostics.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractKtDiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;

/* JADX INFO: compiled from: DeduplicatingDiagnosticReporter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u001a\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u0004\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/impl/DeduplicatingDiagnosticReporter;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "inner", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "reported", "", "Lkotlin/Triple;", "", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "report", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeduplicatingDiagnosticReporter extends DiagnosticReporter {
    private final DiagnosticReporter inner;
    private final Set<Triple<String, AbstractKtSourceElement, AbstractKtDiagnosticFactory>> reported;

    public DeduplicatingDiagnosticReporter(DiagnosticReporter inner) {
        Intrinsics.checkNotNullParameter(inner, "inner");
        this.inner = inner;
        this.reported = new LinkedHashSet();
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticReporter
    public void report(KtDiagnostic diagnostic, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (diagnostic == null || !this.reported.add(new Triple<>(context.getContainingFilePath(), diagnostic.getElement(), diagnostic.getFactory()))) {
            return;
        }
        this.inner.report(diagnostic, context);
    }
}
