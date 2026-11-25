package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompileIterationResult.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompileIterationResult;", "Ljava/io/Serializable;", "sourceFiles", "", "Ljava/io/File;", "exitCode", "", "(Ljava/lang/Iterable;Ljava/lang/String;)V", "getExitCode", "()Ljava/lang/String;", "getSourceFiles", "()Ljava/lang/Iterable;", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompileIterationResult implements Serializable {
    public static final long serialVersionUID = 0;
    private final String exitCode;
    private final Iterable<File> sourceFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public CompileIterationResult(Iterable<? extends File> sourceFiles, String exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        this.sourceFiles = sourceFiles;
        this.exitCode = exitCode;
    }

    public final Iterable<File> getSourceFiles() {
        return this.sourceFiles;
    }

    public final String getExitCode() {
        return this.exitCode;
    }
}
