package org.jetbrains.kotlin.daemon.common;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilerCallbackServicesFacade.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompiledPackagePart;", "Ljava/io/Serializable;", "filePath", "", "metadata", "", "binaryAst", "inlineData", "(Ljava/lang/String;[B[B[B)V", "getBinaryAst", "()[B", "getFilePath", "()Ljava/lang/String;", "getInlineData", "getMetadata", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompiledPackagePart implements Serializable {
    private final byte[] binaryAst;
    private final String filePath;
    private final byte[] inlineData;
    private final byte[] metadata;

    public CompiledPackagePart(String filePath, byte[] metadata, byte[] binaryAst, byte[] inlineData) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(binaryAst, "binaryAst");
        Intrinsics.checkNotNullParameter(inlineData, "inlineData");
        this.filePath = filePath;
        this.metadata = metadata;
        this.binaryAst = binaryAst;
        this.inlineData = inlineData;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final byte[] getBinaryAst() {
        return this.binaryAst;
    }

    public final byte[] getInlineData() {
        return this.inlineData;
    }

    public final byte[] getMetadata() {
        return this.metadata;
    }
}
