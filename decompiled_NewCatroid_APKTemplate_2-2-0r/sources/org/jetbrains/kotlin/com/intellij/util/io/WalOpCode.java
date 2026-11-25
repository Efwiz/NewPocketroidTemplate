package org.jetbrains.kotlin.com.intellij.util.io;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0080\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalOpCode;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "(Ljava/lang/String;II)V", "getCode$intellij_platform_util", "()I", "PUT", "REMOVE", "APPEND", "Companion", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public enum WalOpCode {
    PUT(0),
    REMOVE(1),
    APPEND(2);

    private final int code;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int size = values().length;

    WalOpCode(int i) {
        this.code = i;
    }

    /* JADX INFO: renamed from: getCode$intellij_platform_util, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalOpCode$Companion;", "", "()V", "size", "", "getSize", "()I", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getSize() {
            return WalOpCode.size;
        }
    }
}
