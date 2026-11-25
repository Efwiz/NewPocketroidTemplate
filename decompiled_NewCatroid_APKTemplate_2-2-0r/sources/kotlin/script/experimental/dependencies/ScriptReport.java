package kotlin.script.experimental.dependencies;

import com.google.android.gms.cast.MediaError;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: resolvers.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lkotlin/script/experimental/dependencies/ScriptReport;", "", CrashHianalyticsData.MESSAGE, "", "severity", "Lkotlin/script/experimental/dependencies/ScriptReport$Severity;", "position", "Lkotlin/script/experimental/dependencies/ScriptReport$Position;", "(Ljava/lang/String;Lkotlin/script/experimental/dependencies/ScriptReport$Severity;Lkotlin/script/experimental/dependencies/ScriptReport$Position;)V", "getMessage", "()Ljava/lang/String;", "getPosition", "()Lkotlin/script/experimental/dependencies/ScriptReport$Position;", "getSeverity", "()Lkotlin/script/experimental/dependencies/ScriptReport$Severity;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "Position", "Severity", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptReport {
    private final String message;
    private final Position position;
    private final Severity severity;

    /* JADX INFO: compiled from: resolvers.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lkotlin/script/experimental/dependencies/ScriptReport$Severity;", "", "(Ljava/lang/String;I)V", "FATAL", MediaError.ERROR_TYPE_ERROR, "WARNING", "INFO", "DEBUG", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Severity {
        FATAL,
        ERROR,
        WARNING,
        INFO,
        DEBUG;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Severity> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ ScriptReport copy$default(ScriptReport scriptReport, String str, Severity severity, Position position, int i, Object obj) {
        if ((i & 1) != 0) {
            str = scriptReport.message;
        }
        if ((i & 2) != 0) {
            severity = scriptReport.severity;
        }
        if ((i & 4) != 0) {
            position = scriptReport.position;
        }
        return scriptReport.copy(str, severity, position);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Position getPosition() {
        return this.position;
    }

    public final ScriptReport copy(String message, Severity severity, Position position) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        return new ScriptReport(message, severity, position);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptReport)) {
            return false;
        }
        ScriptReport scriptReport = (ScriptReport) other;
        return Intrinsics.areEqual(this.message, scriptReport.message) && this.severity == scriptReport.severity && Intrinsics.areEqual(this.position, scriptReport.position);
    }

    public int hashCode() {
        int iHashCode = ((this.message.hashCode() * 31) + this.severity.hashCode()) * 31;
        Position position = this.position;
        return iHashCode + (position == null ? 0 : position.hashCode());
    }

    public String toString() {
        return "ScriptReport(message=" + this.message + ", severity=" + this.severity + ", position=" + this.position + ')';
    }

    /* JADX INFO: compiled from: resolvers.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\tJ:\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u001b"}, d2 = {"Lkotlin/script/experimental/dependencies/ScriptReport$Position;", "", "startLine", "", "startColumn", "endLine", "endColumn", "(IILjava/lang/Integer;Ljava/lang/Integer;)V", "getEndColumn", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getEndLine", "getStartColumn", "()I", "getStartLine", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(IILjava/lang/Integer;Ljava/lang/Integer;)Lkotlin/script/experimental/dependencies/ScriptReport$Position;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Position {
        private final Integer endColumn;
        private final Integer endLine;
        private final int startColumn;
        private final int startLine;

        public static /* synthetic */ Position copy$default(Position position, int i, int i2, Integer num, Integer num2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = position.startLine;
            }
            if ((i3 & 2) != 0) {
                i2 = position.startColumn;
            }
            if ((i3 & 4) != 0) {
                num = position.endLine;
            }
            if ((i3 & 8) != 0) {
                num2 = position.endColumn;
            }
            return position.copy(i, i2, num, num2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getStartLine() {
            return this.startLine;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getStartColumn() {
            return this.startColumn;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Integer getEndLine() {
            return this.endLine;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final Integer getEndColumn() {
            return this.endColumn;
        }

        public final Position copy(int startLine, int startColumn, Integer endLine, Integer endColumn) {
            return new Position(startLine, startColumn, endLine, endColumn);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Position)) {
                return false;
            }
            Position position = (Position) other;
            return this.startLine == position.startLine && this.startColumn == position.startColumn && Intrinsics.areEqual(this.endLine, position.endLine) && Intrinsics.areEqual(this.endColumn, position.endColumn);
        }

        public int hashCode() {
            int iHashCode = ((Integer.hashCode(this.startLine) * 31) + Integer.hashCode(this.startColumn)) * 31;
            Integer num = this.endLine;
            int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
            Integer num2 = this.endColumn;
            return iHashCode2 + (num2 != null ? num2.hashCode() : 0);
        }

        public String toString() {
            return "Position(startLine=" + this.startLine + ", startColumn=" + this.startColumn + ", endLine=" + this.endLine + ", endColumn=" + this.endColumn + ')';
        }

        public Position(int i, int i2, Integer num, Integer num2) {
            this.startLine = i;
            this.startColumn = i2;
            this.endLine = num;
            this.endColumn = num2;
        }

        public /* synthetic */ Position(int i, int i2, Integer num, Integer num2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i3 & 4) != 0 ? null : num, (i3 & 8) != 0 ? null : num2);
        }

        public final Integer getEndColumn() {
            return this.endColumn;
        }

        public final Integer getEndLine() {
            return this.endLine;
        }

        public final int getStartColumn() {
            return this.startColumn;
        }

        public final int getStartLine() {
            return this.startLine;
        }
    }

    public ScriptReport(String message, Severity severity, Position position) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.message = message;
        this.severity = severity;
        this.position = position;
    }

    public /* synthetic */ ScriptReport(String str, Severity severity, Position position, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? Severity.ERROR : severity, (i & 4) != 0 ? null : position);
    }

    public final String getMessage() {
        return this.message;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final Severity getSeverity() {
        return this.severity;
    }
}
