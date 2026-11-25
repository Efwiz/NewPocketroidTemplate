package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/CorruptionException;", "Ljava/io/IOException;", "reason", "", "(Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
final class CorruptionException extends IOException {
    private final String reason;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CorruptionException(String reason) {
        super(reason);
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.reason = reason;
    }
}
