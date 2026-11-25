package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.diagnostics.Diagnostic;

/* JADX INFO: compiled from: ConstModifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability;", "", "canBeConst", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "(ZLorg/jetbrains/kotlin/diagnostics/Diagnostic;)V", "getCanBeConst", "()Z", "getDiagnostic", "()Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "Applicable", "NonApplicable", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability$Applicable;", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability$NonApplicable;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstApplicability {
    private final boolean canBeConst;
    private final Diagnostic diagnostic;

    public /* synthetic */ ConstApplicability(boolean z, Diagnostic diagnostic, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, diagnostic);
    }

    /* JADX INFO: compiled from: ConstModifierChecker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability$Applicable;", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Applicable extends ConstApplicability {
        public static final Applicable INSTANCE = new Applicable();

        /* JADX WARN: Multi-variable type inference failed */
        private Applicable() {
            super(true, null, 0 == true ? 1 : 0);
        }
    }

    private ConstApplicability(boolean z, Diagnostic diagnostic) {
        this.canBeConst = z;
        this.diagnostic = diagnostic;
    }

    public final boolean getCanBeConst() {
        return this.canBeConst;
    }

    public final Diagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX INFO: compiled from: ConstModifierChecker.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability$NonApplicable;", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "(Lorg/jetbrains/kotlin/diagnostics/Diagnostic;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NonApplicable extends ConstApplicability {
        /* JADX WARN: Multi-variable type inference failed */
        public NonApplicable() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public NonApplicable(Diagnostic diagnostic) {
            super(false, diagnostic, null);
        }

        public /* synthetic */ NonApplicable(Diagnostic diagnostic, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : diagnostic);
        }
    }
}
