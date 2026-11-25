package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompilationOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/MultiModuleICSettings;", "Ljava/io/Serializable;", "buildHistoryFile", "Ljava/io/File;", "useModuleDetection", "", "(Ljava/io/File;Z)V", "getBuildHistoryFile", "()Ljava/io/File;", "getUseModuleDetection", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MultiModuleICSettings implements Serializable {
    public static final long serialVersionUID = 0;
    private final File buildHistoryFile;
    private final boolean useModuleDetection;

    public static /* synthetic */ MultiModuleICSettings copy$default(MultiModuleICSettings multiModuleICSettings, File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            file = multiModuleICSettings.buildHistoryFile;
        }
        if ((i & 2) != 0) {
            z = multiModuleICSettings.useModuleDetection;
        }
        return multiModuleICSettings.copy(file, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final File getBuildHistoryFile() {
        return this.buildHistoryFile;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getUseModuleDetection() {
        return this.useModuleDetection;
    }

    public final MultiModuleICSettings copy(File buildHistoryFile, boolean useModuleDetection) {
        Intrinsics.checkNotNullParameter(buildHistoryFile, "buildHistoryFile");
        return new MultiModuleICSettings(buildHistoryFile, useModuleDetection);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MultiModuleICSettings)) {
            return false;
        }
        MultiModuleICSettings multiModuleICSettings = (MultiModuleICSettings) other;
        return Intrinsics.areEqual(this.buildHistoryFile, multiModuleICSettings.buildHistoryFile) && this.useModuleDetection == multiModuleICSettings.useModuleDetection;
    }

    public int hashCode() {
        return (this.buildHistoryFile.hashCode() * 31) + Boolean.hashCode(this.useModuleDetection);
    }

    public String toString() {
        return "MultiModuleICSettings(buildHistoryFile=" + this.buildHistoryFile + ", useModuleDetection=" + this.useModuleDetection + ')';
    }

    public MultiModuleICSettings(File buildHistoryFile, boolean z) {
        Intrinsics.checkNotNullParameter(buildHistoryFile, "buildHistoryFile");
        this.buildHistoryFile = buildHistoryFile;
        this.useModuleDetection = z;
    }

    public final File getBuildHistoryFile() {
        return this.buildHistoryFile;
    }

    public final boolean getUseModuleDetection() {
        return this.useModuleDetection;
    }
}
