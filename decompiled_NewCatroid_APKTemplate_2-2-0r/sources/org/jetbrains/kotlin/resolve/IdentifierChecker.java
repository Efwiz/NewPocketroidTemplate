package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: compiled from: IdentifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IdentifierChecker extends PlatformSpecificExtension<IdentifierChecker> {
    void checkDeclaration(KtDeclaration declaration, DiagnosticSink diagnosticHolder);

    void checkIdentifier(KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder);

    /* JADX INFO: compiled from: IdentifierChecker.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/IdentifierChecker$Default;", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "()V", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkIdentifier", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements IdentifierChecker {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
        public void checkDeclaration(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        }

        @Override // org.jetbrains.kotlin.resolve.IdentifierChecker
        public void checkIdentifier(KtSimpleNameExpression simpleNameExpression, DiagnosticSink diagnosticHolder) {
            Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
            Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        }

        private Default() {
        }
    }
}
