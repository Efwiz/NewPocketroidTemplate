package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: compiled from: CompilationResults.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilationResultCategory;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "(Ljava/lang/String;II)V", "getCode", "()I", "IC_COMPILE_ITERATION", "BUILD_REPORT_LINES", "VERBOSE_BUILD_REPORT_LINES", "BUILD_METRICS", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum CompilationResultCategory {
    IC_COMPILE_ITERATION(0),
    BUILD_REPORT_LINES(1),
    VERBOSE_BUILD_REPORT_LINES(2),
    BUILD_METRICS(3);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final int code;

    public static EnumEntries<CompilationResultCategory> getEntries() {
        return $ENTRIES;
    }

    CompilationResultCategory(int i) {
        this.code = i;
    }

    public final int getCode() {
        return this.code;
    }
}
