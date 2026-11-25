package org.jetbrains.kotlin.checkers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic;

/* JADX INFO: compiled from: DiagnosticDescriptors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/checkers/ActualDiagnosticDescriptor;", "Lorg/jetbrains/kotlin/checkers/AbstractDiagnosticDescriptor;", "start", "", "end", "diagnostics", "", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "(IILjava/util/List;)V", "getDiagnostics", "()Ljava/util/List;", "textDiagnosticsMap", "", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "getTextDiagnosticsMap", "()Ljava/util/Map;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActualDiagnosticDescriptor extends AbstractDiagnosticDescriptor {
    private final List<AbstractTestDiagnostic> diagnostics;

    public final List<AbstractTestDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ActualDiagnosticDescriptor(int i, int i2, List<? extends AbstractTestDiagnostic> diagnostics) {
        super(i, i2);
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        this.diagnostics = diagnostics;
    }

    public final Map<AbstractTestDiagnostic, TextDiagnostic> getTextDiagnosticsMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (AbstractTestDiagnostic abstractTestDiagnostic : this.diagnostics) {
            linkedHashMap.put(abstractTestDiagnostic, TextDiagnostic.INSTANCE.asTextDiagnostic(abstractTestDiagnostic));
        }
        return linkedHashMap;
    }
}
