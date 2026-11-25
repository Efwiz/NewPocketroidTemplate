package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;

/* JADX INFO: compiled from: ConversionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/CharacterWithDiagnostic;", "", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "(Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;)V", "value", "", "(C)V", "getValue", "()Ljava/lang/Character;", "Ljava/lang/Character;", "getDiagnostic", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CharacterWithDiagnostic {
    private final DiagnosticKind diagnostic;
    private final Character value;

    public final Character getValue() {
        return this.value;
    }

    public CharacterWithDiagnostic(DiagnosticKind diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostic = diagnostic;
        this.value = null;
    }

    public CharacterWithDiagnostic(char c) {
        this.diagnostic = null;
        this.value = Character.valueOf(c);
    }

    public final DiagnosticKind getDiagnostic() {
        return this.diagnostic;
    }
}
