package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IncrementalModuleInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÂ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J;\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalModuleEntry;", "Ljava/io/Serializable;", Constants.EXTRA_PROJECT_PATH, "", "name", "buildDir", "Ljava/io/File;", "buildHistoryFile", "abiSnapshot", "(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Ljava/io/File;Ljava/io/File;)V", "getAbiSnapshot", "()Ljava/io/File;", "getBuildDir", "getBuildHistoryFile", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IncrementalModuleEntry implements Serializable {
    private static final long serialVersionUID = 0;
    private final File abiSnapshot;
    private final File buildDir;
    private final File buildHistoryFile;
    private final String name;
    private final String projectPath;

    public static /* synthetic */ IncrementalModuleEntry copy$default(IncrementalModuleEntry incrementalModuleEntry, String str, String str2, File file, File file2, File file3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = incrementalModuleEntry.projectPath;
        }
        if ((i & 2) != 0) {
            str2 = incrementalModuleEntry.name;
        }
        String str3 = str2;
        if ((i & 4) != 0) {
            file = incrementalModuleEntry.buildDir;
        }
        File file4 = file;
        if ((i & 8) != 0) {
            file2 = incrementalModuleEntry.buildHistoryFile;
        }
        File file5 = file2;
        if ((i & 16) != 0) {
            file3 = incrementalModuleEntry.abiSnapshot;
        }
        return incrementalModuleEntry.copy(str, str3, file4, file5, file3);
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final File getBuildDir() {
        return this.buildDir;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final File getBuildHistoryFile() {
        return this.buildHistoryFile;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final File getAbiSnapshot() {
        return this.abiSnapshot;
    }

    public final IncrementalModuleEntry copy(String projectPath, String name2, File buildDir, File buildHistoryFile, File abiSnapshot) {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(buildDir, "buildDir");
        Intrinsics.checkNotNullParameter(buildHistoryFile, "buildHistoryFile");
        Intrinsics.checkNotNullParameter(abiSnapshot, "abiSnapshot");
        return new IncrementalModuleEntry(projectPath, name2, buildDir, buildHistoryFile, abiSnapshot);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IncrementalModuleEntry)) {
            return false;
        }
        IncrementalModuleEntry incrementalModuleEntry = (IncrementalModuleEntry) other;
        return Intrinsics.areEqual(this.projectPath, incrementalModuleEntry.projectPath) && Intrinsics.areEqual(this.name, incrementalModuleEntry.name) && Intrinsics.areEqual(this.buildDir, incrementalModuleEntry.buildDir) && Intrinsics.areEqual(this.buildHistoryFile, incrementalModuleEntry.buildHistoryFile) && Intrinsics.areEqual(this.abiSnapshot, incrementalModuleEntry.abiSnapshot);
    }

    public int hashCode() {
        return (((((((this.projectPath.hashCode() * 31) + this.name.hashCode()) * 31) + this.buildDir.hashCode()) * 31) + this.buildHistoryFile.hashCode()) * 31) + this.abiSnapshot.hashCode();
    }

    public String toString() {
        return "IncrementalModuleEntry(projectPath=" + this.projectPath + ", name=" + this.name + ", buildDir=" + this.buildDir + ", buildHistoryFile=" + this.buildHistoryFile + ", abiSnapshot=" + this.abiSnapshot + ')';
    }

    public IncrementalModuleEntry(String projectPath, String name2, File buildDir, File buildHistoryFile, File abiSnapshot) {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(buildDir, "buildDir");
        Intrinsics.checkNotNullParameter(buildHistoryFile, "buildHistoryFile");
        Intrinsics.checkNotNullParameter(abiSnapshot, "abiSnapshot");
        this.projectPath = projectPath;
        this.name = name2;
        this.buildDir = buildDir;
        this.buildHistoryFile = buildHistoryFile;
        this.abiSnapshot = abiSnapshot;
    }

    public final String getName() {
        return this.name;
    }

    public final File getBuildDir() {
        return this.buildDir;
    }

    public final File getBuildHistoryFile() {
        return this.buildHistoryFile;
    }

    public final File getAbiSnapshot() {
        return this.abiSnapshot;
    }
}
