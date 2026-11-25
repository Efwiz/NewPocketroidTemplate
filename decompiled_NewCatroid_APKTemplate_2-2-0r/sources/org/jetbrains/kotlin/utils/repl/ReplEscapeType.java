package org.jetbrains.kotlin.utils.repl;

import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReplEscapeType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u0000 \u000f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/utils/repl/ReplEscapeType;", "", "(Ljava/lang/String;I)V", "INITIAL_PROMPT", "HELP_PROMPT", "USER_OUTPUT", "REPL_RESULT", "READLINE_START", "READLINE_END", "REPL_INCOMPLETE", "COMPILE_ERROR", "RUNTIME_ERROR", "INTERNAL_ERROR", "ERRORS_REPORTED", MonitorResult.SUCCESS, "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ReplEscapeType {
    INITIAL_PROMPT,
    HELP_PROMPT,
    USER_OUTPUT,
    REPL_RESULT,
    READLINE_START,
    READLINE_END,
    REPL_INCOMPLETE,
    COMPILE_ERROR,
    RUNTIME_ERROR,
    INTERNAL_ERROR,
    ERRORS_REPORTED,
    SUCCESS;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<ReplEscapeType> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: ReplEscapeType.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/repl/ReplEscapeType$Companion;", "", "()V", "valueOfOrNull", "Lorg/jetbrains/kotlin/utils/repl/ReplEscapeType;", "string", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReplEscapeType valueOfOrNull(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            try {
                return ReplEscapeType.valueOf(string);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
    }
}
