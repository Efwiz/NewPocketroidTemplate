package org.jetbrains.kotlin.checkers.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PositionalTextDiagnostic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/PositionalTextDiagnostic;", "", "diagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "start", "", "end", "(Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;II)V", "getDiagnostic", "()Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "getEnd", "()I", "getStart", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PositionalTextDiagnostic {
    private final TextDiagnostic diagnostic;
    private final int end;
    private final int start;

    public static /* synthetic */ PositionalTextDiagnostic copy$default(PositionalTextDiagnostic positionalTextDiagnostic, TextDiagnostic textDiagnostic, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            textDiagnostic = positionalTextDiagnostic.diagnostic;
        }
        if ((i3 & 2) != 0) {
            i = positionalTextDiagnostic.start;
        }
        if ((i3 & 4) != 0) {
            i2 = positionalTextDiagnostic.end;
        }
        return positionalTextDiagnostic.copy(textDiagnostic, i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TextDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getStart() {
        return this.start;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getEnd() {
        return this.end;
    }

    public final PositionalTextDiagnostic copy(TextDiagnostic diagnostic, int start, int end) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return new PositionalTextDiagnostic(diagnostic, start, end);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PositionalTextDiagnostic)) {
            return false;
        }
        PositionalTextDiagnostic positionalTextDiagnostic = (PositionalTextDiagnostic) other;
        return Intrinsics.areEqual(this.diagnostic, positionalTextDiagnostic.diagnostic) && this.start == positionalTextDiagnostic.start && this.end == positionalTextDiagnostic.end;
    }

    public int hashCode() {
        return (((this.diagnostic.hashCode() * 31) + Integer.hashCode(this.start)) * 31) + Integer.hashCode(this.end);
    }

    public String toString() {
        return "PositionalTextDiagnostic(diagnostic=" + this.diagnostic + ", start=" + this.start + ", end=" + this.end + ')';
    }

    public PositionalTextDiagnostic(TextDiagnostic diagnostic, int i, int i2) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.diagnostic = diagnostic;
        this.start = i;
        this.end = i2;
    }

    public final TextDiagnostic getDiagnostic() {
        return this.diagnostic;
    }

    public final int getEnd() {
        return this.end;
    }

    public final int getStart() {
        return this.start;
    }
}
