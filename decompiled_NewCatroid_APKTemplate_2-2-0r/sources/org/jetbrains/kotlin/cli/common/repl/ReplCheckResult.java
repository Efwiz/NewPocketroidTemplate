package org.jetbrains.kotlin.cli.common.repl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;

/* JADX INFO: compiled from: ReplApi.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "Ljava/io/Serializable;", "()V", "Companion", "Error", "Incomplete", "Ok", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Incomplete;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Ok;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReplCheckResult implements Serializable {
    private static final long serialVersionUID = 8228307678L;

    public /* synthetic */ ReplCheckResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Ok;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "()V", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Ok extends ReplCheckResult {
        private static final long serialVersionUID = 1;

        public Ok() {
            super(null);
        }
    }

    private ReplCheckResult() {
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Incomplete;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "()V", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Incomplete extends ReplCheckResult {
        private static final long serialVersionUID = 1;

        public Incomplete() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ReplApi.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult$Error;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;)V", "getLocation", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "getMessage", "()Ljava/lang/String;", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends ReplCheckResult {
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
            return "Error(message = \"" + this.message + "\")";
        }
    }
}
