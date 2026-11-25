package org.jetbrains.kotlin.fir.analysis.collectors;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContextForProvider;
import org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;

/* JADX INFO: compiled from: CheckerRunningDiagnosticCollectorVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/CheckerRunningDiagnosticCollectorVisitor;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/AbstractDiagnosticCollectorVisitor;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;", "components", "Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContextForProvider;Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;)V", "getComponents", "()Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "checkElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "checkSettings", "onDeclarationExit", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CheckerRunningDiagnosticCollectorVisitor extends AbstractDiagnosticCollectorVisitor {
    private final DiagnosticCollectorComponents components;

    protected final DiagnosticCollectorComponents getComponents() {
        return this.components;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CheckerRunningDiagnosticCollectorVisitor(CheckerContextForProvider context, DiagnosticCollectorComponents components) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor
    public void checkSettings() {
        Iterator<T> it = this.components.getRegularComponents().iterator();
        while (it.hasNext()) {
            ((AbstractDiagnosticCollectorComponent) it.next()).checkSettings(getContext());
        }
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor
    protected void checkElement(FirElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        Iterator<T> it = this.components.getRegularComponents().iterator();
        while (it.hasNext()) {
            element.accept((AbstractDiagnosticCollectorComponent) it.next(), getContext());
        }
        element.accept(this.components.getReportCommitter(), getContext());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollectorVisitor
    protected void onDeclarationExit(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirFile) {
            this.components.getReportCommitter().endOfFile((FirFile) declaration);
        }
    }
}
