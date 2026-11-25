package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: compiled from: IdentifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompositeIdentifierChecker;", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "identifierCheckers", "", "(Ljava/util/List;)V", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositeIdentifierChecker implements IdentifierChecker {
    private final List<IdentifierChecker> identifierCheckers;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositeIdentifierChecker(List<? extends IdentifierChecker> identifierCheckers) {
        Intrinsics.checkNotNullParameter(identifierCheckers, "identifierCheckers");
        this.identifierCheckers = identifierCheckers;
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkIdentifier(KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Iterator<T> it = this.identifierCheckers.iterator();
        while (it.hasNext()) {
            ((IdentifierChecker) it.next()).checkIdentifier(simpleNameExpression, diagnosticHolder);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
    public void checkDeclaration(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Iterator<T> it = this.identifierCheckers.iterator();
        while (it.hasNext()) {
            ((IdentifierChecker) it.next()).checkDeclaration(declaration, diagnosticHolder);
        }
    }
}
