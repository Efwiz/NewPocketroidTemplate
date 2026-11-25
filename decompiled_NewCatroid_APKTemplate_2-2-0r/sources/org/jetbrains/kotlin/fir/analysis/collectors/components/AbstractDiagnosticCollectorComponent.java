package org.jetbrains.kotlin.fir.analysis.collectors.components;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;

/* JADX INFO: compiled from: AbstractDiagnosticCollectorComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0004J\u0010\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0003H\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/components/AbstractDiagnosticCollectorComponent;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "getReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "checkAndCommitReportsOn", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "checkSettings", "data", "visitElement", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDiagnosticCollectorComponent extends FirVisitor<Unit, CheckerContext> {
    private final DiagnosticReporter reporter;
    private final FirSession session;

    public void checkSettings(CheckerContext data) {
        Intrinsics.checkNotNullParameter(data, "data");
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(FirElement element, CheckerContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, CheckerContext checkerContext) {
        visitElement2(firElement, checkerContext);
        return Unit.INSTANCE;
    }

    protected final FirSession getSession() {
        return this.session;
    }

    protected final DiagnosticReporter getReporter() {
        return this.reporter;
    }

    public AbstractDiagnosticCollectorComponent(FirSession session, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        this.session = session;
        this.reporter = reporter;
    }

    protected final void checkAndCommitReportsOn(FirElement element, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
        KtSourceElement source = element.getSource();
        if (source == null) {
            return;
        }
        this.reporter.checkAndCommitReportsOn(source, context);
    }
}
