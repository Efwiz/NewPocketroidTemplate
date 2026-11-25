package org.jetbrains.kotlin.cli.common.fir;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u0006H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/fir/KtSourceFileDiagnosticPos;", "", Constant.LINE, "", "column", "lineContent", "", "(IILjava/lang/String;)V", "getColumn", "()I", "getLine", "getLineContent", "()Ljava/lang/String;", "toString", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtSourceFileDiagnosticPos {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KtSourceFileDiagnosticPos NONE = new KtSourceFileDiagnosticPos(-1, -1, null);
    private final int column;
    private final int line;
    private final String lineContent;

    public KtSourceFileDiagnosticPos(int i, int i2, String str) {
        this.line = i;
        this.column = i2;
        this.lineContent = str;
    }

    public final int getColumn() {
        return this.column;
    }

    public final int getLine() {
        return this.line;
    }

    public final String getLineContent() {
        return this.lineContent;
    }

    public String toString() {
        return (this.line < 0 ? new StringBuilder("(offset: ").append(this.column).append(" line unknown)") : new StringBuilder("(").append(this.line).append(',').append(this.column).append(')')).toString();
    }

    /* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/fir/KtSourceFileDiagnosticPos$Companion;", "", "()V", "NONE", "Lorg/jetbrains/kotlin/cli/common/fir/KtSourceFileDiagnosticPos;", "getNONE", "()Lorg/jetbrains/kotlin/cli/common/fir/KtSourceFileDiagnosticPos;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KtSourceFileDiagnosticPos getNONE() {
            return KtSourceFileDiagnosticPos.NONE;
        }
    }
}
