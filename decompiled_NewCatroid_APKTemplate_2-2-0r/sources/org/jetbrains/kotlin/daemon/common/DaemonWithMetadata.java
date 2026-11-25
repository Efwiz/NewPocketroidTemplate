package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ClientUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/DaemonWithMetadata;", "", "daemon", "Lorg/jetbrains/kotlin/daemon/common/CompileService;", "runFile", "Ljava/io/File;", "jvmOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "(Lorg/jetbrains/kotlin/daemon/common/CompileService;Ljava/io/File;Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;)V", "getDaemon", "()Lorg/jetbrains/kotlin/daemon/common/CompileService;", "getJvmOptions", "()Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "getRunFile", "()Ljava/io/File;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DaemonWithMetadata {
    private final CompileService daemon;
    private final DaemonJVMOptions jvmOptions;
    private final File runFile;

    public static /* synthetic */ DaemonWithMetadata copy$default(DaemonWithMetadata daemonWithMetadata, CompileService compileService, File file, DaemonJVMOptions daemonJVMOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            compileService = daemonWithMetadata.daemon;
        }
        if ((i & 2) != 0) {
            file = daemonWithMetadata.runFile;
        }
        if ((i & 4) != 0) {
            daemonJVMOptions = daemonWithMetadata.jvmOptions;
        }
        return daemonWithMetadata.copy(compileService, file, daemonJVMOptions);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CompileService getDaemon() {
        return this.daemon;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final File getRunFile() {
        return this.runFile;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final DaemonJVMOptions getJvmOptions() {
        return this.jvmOptions;
    }

    public final DaemonWithMetadata copy(CompileService daemon, File runFile, DaemonJVMOptions jvmOptions) {
        Intrinsics.checkNotNullParameter(daemon, "daemon");
        Intrinsics.checkNotNullParameter(runFile, "runFile");
        Intrinsics.checkNotNullParameter(jvmOptions, "jvmOptions");
        return new DaemonWithMetadata(daemon, runFile, jvmOptions);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DaemonWithMetadata)) {
            return false;
        }
        DaemonWithMetadata daemonWithMetadata = (DaemonWithMetadata) other;
        return Intrinsics.areEqual(this.daemon, daemonWithMetadata.daemon) && Intrinsics.areEqual(this.runFile, daemonWithMetadata.runFile) && Intrinsics.areEqual(this.jvmOptions, daemonWithMetadata.jvmOptions);
    }

    public int hashCode() {
        return (((this.daemon.hashCode() * 31) + this.runFile.hashCode()) * 31) + this.jvmOptions.hashCode();
    }

    public String toString() {
        return "DaemonWithMetadata(daemon=" + this.daemon + ", runFile=" + this.runFile + ", jvmOptions=" + this.jvmOptions + ')';
    }

    public DaemonWithMetadata(CompileService daemon, File runFile, DaemonJVMOptions jvmOptions) {
        Intrinsics.checkNotNullParameter(daemon, "daemon");
        Intrinsics.checkNotNullParameter(runFile, "runFile");
        Intrinsics.checkNotNullParameter(jvmOptions, "jvmOptions");
        this.daemon = daemon;
        this.runFile = runFile;
        this.jvmOptions = jvmOptions;
    }

    public final CompileService getDaemon() {
        return this.daemon;
    }

    public final DaemonJVMOptions getJvmOptions() {
        return this.jvmOptions;
    }

    public final File getRunFile() {
        return this.runFile;
    }
}
