package org.jetbrains.kotlin.daemon.common;

import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: compiled from: CompilerServicesFacadeBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "(Ljava/lang/String;II)V", "getCode", "()I", MediaError.ERROR_TYPE_ERROR, "WARNING", "INFO", "DEBUG", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ReportSeverity {
    ERROR(0),
    WARNING(1),
    INFO(2),
    DEBUG(3);

    private final int code;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<ReportSeverity> getEntries() {
        return $ENTRIES;
    }

    ReportSeverity(int i) {
        this.code = i;
    }

    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: compiled from: CompilerServicesFacadeBase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/ReportSeverity$Companion;", "", "()V", "fromCode", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Code duplicated, block: B:10:0x0017 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:11:0x0018  */
        public final ReportSeverity fromCode(int code) {
            for (ReportSeverity reportSeverity : ReportSeverity.values()) {
                if (reportSeverity.getCode() == code) {
                    if (reportSeverity != null) {
                        return reportSeverity;
                    }
                    throw new IllegalStateException(("Can't find a matching ReportSeverity with code = " + code).toString());
                }
            }
            reportSeverity = null;
            if (reportSeverity != null) {
                return reportSeverity;
            }
            throw new IllegalStateException(("Can't find a matching ReportSeverity with code = " + code).toString());
        }
    }
}
