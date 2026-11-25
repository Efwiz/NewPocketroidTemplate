package org.jetbrains.kotlin.cli.common.repl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "Ljava/io/Serializable;", "()V", "Companion", "CompiledClasses", "Error", "Incomplete", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$Incomplete;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReplCompileResult implements Serializable {
    private static final long serialVersionUID = 8228307678L;

    public /* synthetic */ ReplCompileResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0010\u0018\u0000  2\u00020\u0001:\u0001 B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\b\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001d¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/LineId;", "previousLines", "", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "mainClassName", "", "classes", "Lorg/jetbrains/kotlin/cli/common/repl/CompiledClassData;", "hasResult", "", "classpathAddendum", "Ljava/io/File;", ModuleXmlParser.TYPE, "data", "", "(Lorg/jetbrains/kotlin/cli/common/repl/LineId;Ljava/util/List;Ljava/lang/String;Ljava/util/List;ZLjava/util/List;Ljava/lang/String;Ljava/lang/Object;)V", "getClasses", "()Ljava/util/List;", "getClasspathAddendum", "getData", "()Ljava/lang/Object;", "getHasResult", "()Z", "getLineId", "()Lorg/jetbrains/kotlin/cli/common/repl/LineId;", "getMainClassName", "()Ljava/lang/String;", "getPreviousLines", "getType", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompiledClasses extends ReplCompileResult {
        private static final long serialVersionUID = 2;
        private final List<CompiledClassData> classes;
        private final List<File> classpathAddendum;
        private final Object data;
        private final boolean hasResult;
        private final LineId lineId;
        private final String mainClassName;
        private final List<ILineId> previousLines;
        private final String type;

        public final LineId getLineId() {
            return this.lineId;
        }

        public final List<ILineId> getPreviousLines() {
            return this.previousLines;
        }

        public final String getMainClassName() {
            return this.mainClassName;
        }

        public final List<CompiledClassData> getClasses() {
            return this.classes;
        }

        public final boolean getHasResult() {
            return this.hasResult;
        }

        public final List<File> getClasspathAddendum() {
            return this.classpathAddendum;
        }

        public final String getType() {
            return this.type;
        }

        public final Object getData() {
            return this.data;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public CompiledClasses(LineId lineId, List<? extends ILineId> previousLines, String mainClassName, List<CompiledClassData> classes, boolean z, List<? extends File> classpathAddendum, String str, Object obj) {
            super(null);
            Intrinsics.checkNotNullParameter(lineId, "lineId");
            Intrinsics.checkNotNullParameter(previousLines, "previousLines");
            Intrinsics.checkNotNullParameter(mainClassName, "mainClassName");
            Intrinsics.checkNotNullParameter(classes, "classes");
            Intrinsics.checkNotNullParameter(classpathAddendum, "classpathAddendum");
            this.lineId = lineId;
            this.previousLines = previousLines;
            this.mainClassName = mainClassName;
            this.classes = classes;
            this.hasResult = z;
            this.classpathAddendum = classpathAddendum;
            this.type = str;
            this.data = obj;
        }
    }

    private ReplCompileResult() {
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$Incomplete;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Incomplete extends ReplCompileResult {
        private static final long serialVersionUID = 1;
        private final String message;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Incomplete(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;)V", "getLocation", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "getMessage", "()Ljava/lang/String;", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends ReplCompileResult {
        private static final long serialVersionUID = 1;
        private final CompilerMessageLocation location;
        private final String message;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(String message, CompilerMessageLocation compilerMessageLocation) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
            this.location = compilerMessageLocation;
        }

        public /* synthetic */ Error(String str, CompilerMessageLocation compilerMessageLocation, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : compilerMessageLocation);
        }

        public final CompilerMessageLocation getLocation() {
            return this.location;
        }

        public final String getMessage() {
            return this.message;
        }

        public String toString() {
            return "Error(message = \"" + this.message + '\"';
        }
    }
}
