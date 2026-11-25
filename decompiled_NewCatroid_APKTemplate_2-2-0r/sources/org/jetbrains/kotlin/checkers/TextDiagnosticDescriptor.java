package org.jetbrains.kotlin.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.PositionalTextDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic;

/* JADX INFO: compiled from: DiagnosticDescriptors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/checkers/TextDiagnosticDescriptor;", "Lorg/jetbrains/kotlin/checkers/AbstractDiagnosticDescriptor;", "positionalTextDiagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/PositionalTextDiagnostic;", "(Lorg/jetbrains/kotlin/checkers/diagnostics/PositionalTextDiagnostic;)V", "textDiagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "getTextDiagnostic", "()Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextDiagnosticDescriptor extends AbstractDiagnosticDescriptor {
    private final PositionalTextDiagnostic positionalTextDiagnostic;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextDiagnosticDescriptor(PositionalTextDiagnostic positionalTextDiagnostic) {
        super(positionalTextDiagnostic.getStart(), positionalTextDiagnostic.getEnd());
        Intrinsics.checkNotNullParameter(positionalTextDiagnostic, "positionalTextDiagnostic");
        this.positionalTextDiagnostic = positionalTextDiagnostic;
    }

    public final TextDiagnostic getTextDiagnostic() {
        return this.positionalTextDiagnostic.getDiagnostic();
    }
}
