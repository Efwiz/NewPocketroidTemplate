package org.jetbrains.kotlin.kapt3.diagnostic;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KaptError.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\u0018\u00002\u00060\u0001j\u0002`\u0002:\u0001\u000bB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError$Kind;", "(Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError$Kind;)V", "cause", "", "(Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError$Kind;Ljava/lang/Throwable;)V", "getKind", "()Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError$Kind;", Namer.CLASS_KIND_ENUM, "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KaptError extends RuntimeException {
    private final Kind kind;

    public final Kind getKind() {
        return this.kind;
    }

    /* JADX INFO: compiled from: KaptError.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/kapt3/diagnostic/KaptError$Kind;", "", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "EXCEPTION", "ERROR_RAISED", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        EXCEPTION("Exception while annotation processing"),
        ERROR_RAISED("Error while annotation processing");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String message;

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(String str) {
            this.message = str;
        }

        public final String getMessage() {
            return this.message;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KaptError(Kind kind) {
        super(kind.getMessage());
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KaptError(Kind kind, Throwable cause) {
        super(kind.getMessage(), cause);
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(cause, "cause");
        this.kind = kind;
    }
}
