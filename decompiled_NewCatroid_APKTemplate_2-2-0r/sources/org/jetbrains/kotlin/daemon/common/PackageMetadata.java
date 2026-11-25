package org.jetbrains.kotlin.daemon.common;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilerCallbackServicesFacade.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/PackageMetadata;", "Ljava/io/Serializable;", "packageName", "", "metadata", "", "(Ljava/lang/String;[B)V", "getMetadata", "()[B", "getPackageName", "()Ljava/lang/String;", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageMetadata implements Serializable {
    private static final long serialVersionUID = 54021986502349756L;
    private final byte[] metadata;
    private final String packageName;

    public PackageMetadata(String packageName, byte[] metadata) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.packageName = packageName;
        this.metadata = metadata;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final byte[] getMetadata() {
        return this.metadata;
    }
}
