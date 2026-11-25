package org.jetbrains.kotlin.cli.common.repl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0005\t\n\u000b\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "Ljava/io/Serializable;", "()V", "Companion", "Error", "HistoryMismatch", "Incomplete", "UnitResult", "ValueResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$HistoryMismatch;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Incomplete;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$UnitResult;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$ValueResult;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReplEvalResult implements Serializable {
    private static final long serialVersionUID = 8228307678L;

    public /* synthetic */ ReplEvalResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$ValueResult;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "name", "", "value", "", ModuleXmlParser.TYPE, "snippetInstance", "(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", "getName", "()Ljava/lang/String;", "getSnippetInstance", "()Ljava/lang/Object;", "getType", "getValue", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ValueResult extends ReplEvalResult {
        private static final long serialVersionUID = 1;
        private final String name;
        private final Object snippetInstance;
        private final String type;
        private final Object value;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueResult(String name2, Object obj, String str, Object obj2) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.value = obj;
            this.type = str;
            this.snippetInstance = obj2;
        }

        public /* synthetic */ ValueResult(String str, Object obj, String str2, Object obj2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, obj, str2, (i & 8) != 0 ? null : obj2);
        }

        public final String getName() {
            return this.name;
        }

        public final Object getSnippetInstance() {
            return this.snippetInstance;
        }

        public final String getType() {
            return this.type;
        }

        public final Object getValue() {
            return this.value;
        }

        public String toString() {
            Object obj = this.value;
            if (obj instanceof Function) {
                obj = "<function" + TypeIntrinsics.getFunctionArity(this.value) + '>';
            }
            return this.name + ": " + this.type + " = " + obj;
        }
    }

    private ReplEvalResult() {
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$UnitResult;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "()V", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnitResult extends ReplEvalResult {
        private static final long serialVersionUID = 1;

        public UnitResult() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Incomplete;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Incomplete extends ReplEvalResult {
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
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$HistoryMismatch;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", "lineNo", "", "(I)V", "getLineNo", "()I", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class HistoryMismatch extends ReplEvalResult {
        private static final long serialVersionUID = 1;
        private final int lineNo;

        public HistoryMismatch(int i) {
            super(null);
            this.lineNo = i;
        }

        public final int getLineNo() {
            return this.lineNo;
        }
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \b2\u00020\u0001:\u0003\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "toString", "Companion", "CompileTime", "Runtime", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error$CompileTime;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error$Runtime;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Error extends ReplEvalResult {
        private static final long serialVersionUID = 1;
        private final String message;

        public /* synthetic */ Error(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        /* JADX INFO: compiled from: ReplApi.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error$Runtime;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error;", CrashHianalyticsData.MESSAGE, "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Runtime extends Error {
            private static final long serialVersionUID = 1;
            private final Throwable cause;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Runtime(String message, Throwable th) {
                super(message, null);
                Intrinsics.checkNotNullParameter(message, "message");
                this.cause = th;
            }

            public /* synthetic */ Runtime(String str, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? null : th);
            }

            public final Throwable getCause() {
                return this.cause;
            }
        }

        private Error(String str) {
            super(null);
            this.message = str;
        }

        public final String getMessage() {
            return this.message;
        }

        /* JADX INFO: compiled from: ReplApi.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error$CompileTime;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult$Error;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;)V", "getLocation", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class CompileTime extends Error {
            private static final long serialVersionUID = 1;
            private final CompilerMessageLocation location;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public CompileTime(String message, CompilerMessageLocation compilerMessageLocation) {
                super(message, null);
                Intrinsics.checkNotNullParameter(message, "message");
                this.location = compilerMessageLocation;
            }

            public /* synthetic */ CompileTime(String str, CompilerMessageLocation compilerMessageLocation, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? null : compilerMessageLocation);
            }

            public final CompilerMessageLocation getLocation() {
                return this.location;
            }
        }

        public String toString() {
            return Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + "Error(message = \"" + this.message + '\"';
        }
    }
}
