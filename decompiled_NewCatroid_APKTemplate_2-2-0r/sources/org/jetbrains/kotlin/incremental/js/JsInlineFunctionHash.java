package org.jetbrains.kotlin.incremental.js;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsInlineFunctionHash.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/JsInlineFunctionHash;", "Ljava/io/Serializable;", "sourceFilePath", "", "fqName", "inlineFunctionMd5Hash", "", "(Ljava/lang/String;Ljava/lang/String;J)V", "getFqName", "()Ljava/lang/String;", "getInlineFunctionMd5Hash", "()J", "getSourceFilePath", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInlineFunctionHash implements Serializable {
    private final String fqName;
    private final long inlineFunctionMd5Hash;
    private final String sourceFilePath;

    public JsInlineFunctionHash(String sourceFilePath, String fqName, long j) {
        Intrinsics.checkNotNullParameter(sourceFilePath, "sourceFilePath");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.sourceFilePath = sourceFilePath;
        this.fqName = fqName;
        this.inlineFunctionMd5Hash = j;
    }

    public final String getFqName() {
        return this.fqName;
    }

    public final long getInlineFunctionMd5Hash() {
        return this.inlineFunctionMd5Hash;
    }

    public final String getSourceFilePath() {
        return this.sourceFilePath;
    }
}
