package org.jetbrains.kotlin.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: DiagnosedRange.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/checkers/DiagnosedRange;", "", "start", "", "(I)V", "diagnostics", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "end", "getEnd", "()I", "setEnd", "getStart", "addDiagnostic", "", "diagnostic", "", "getDiagnostics", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DiagnosedRange {
    private final SmartList<TextDiagnostic> diagnostics = new SmartList<>();
    private int end;
    private final int start;

    public DiagnosedRange(int i) {
        this.start = i;
    }

    public final int getStart() {
        return this.start;
    }

    public final int getEnd() {
        return this.end;
    }

    public final void setEnd(int i) {
        this.end = i;
    }

    public final List<TextDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public final void addDiagnostic(String diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostics.add(TextDiagnostic.INSTANCE.parseDiagnostic(diagnostic));
    }
}
