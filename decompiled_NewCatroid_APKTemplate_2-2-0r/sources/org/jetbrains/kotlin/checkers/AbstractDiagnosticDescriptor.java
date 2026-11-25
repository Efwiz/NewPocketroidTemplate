package org.jetbrains.kotlin.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: DiagnosticDescriptors.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/checkers/AbstractDiagnosticDescriptor;", "", "start", "", "end", "(II)V", "getEnd", "()I", "getStart", "textRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRange", "()Lcom/intellij/openapi/util/TextRange;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDiagnosticDescriptor {
    private final int end;
    private final int start;

    public AbstractDiagnosticDescriptor(int i, int i2) {
        this.start = i;
        this.end = i2;
    }

    public final int getEnd() {
        return this.end;
    }

    public final int getStart() {
        return this.start;
    }

    public final TextRange getTextRange() {
        return new TextRange(this.start, this.end);
    }
}
