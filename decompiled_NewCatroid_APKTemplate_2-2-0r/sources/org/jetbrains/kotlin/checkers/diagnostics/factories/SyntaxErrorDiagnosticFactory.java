package org.jetbrains.kotlin.checkers.diagnostics.factories;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.checkers.diagnostics.SyntaxErrorDiagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: SyntaxErrorDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/SyntaxErrorDiagnosticFactory;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "Lorg/jetbrains/kotlin/checkers/diagnostics/SyntaxErrorDiagnostic;", "()V", "name", "", "getName", "()Ljava/lang/String;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntaxErrorDiagnosticFactory extends DiagnosticFactory<SyntaxErrorDiagnostic> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final SyntaxErrorDiagnosticFactory INSTANCE = new SyntaxErrorDiagnosticFactory();

    private SyntaxErrorDiagnosticFactory() {
        super(Severity.ERROR);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticFactory
    public String getName() {
        return "SYNTAX";
    }

    /* JADX INFO: compiled from: SyntaxErrorDiagnosticFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/SyntaxErrorDiagnosticFactory$Companion;", "", "()V", JvmAbi.INSTANCE_FIELD, "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/SyntaxErrorDiagnosticFactory;", "getINSTANCE", "()Lorg/jetbrains/kotlin/checkers/diagnostics/factories/SyntaxErrorDiagnosticFactory;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SyntaxErrorDiagnosticFactory getINSTANCE() {
            return SyntaxErrorDiagnosticFactory.INSTANCE;
        }
    }
}
