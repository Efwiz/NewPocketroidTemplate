package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IncrementalModuleInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\"\n\u0002\b\u000e\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016Bw\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\t0\u0005\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\rR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\t0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "Ljava/io/Serializable;", "rootProjectBuildDir", "Ljava/io/File;", "dirToModule", "", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleEntry;", "nameToModules", "", "", "jarToClassListFile", "jarToModule", "jarToAbiSnapshot", "(Ljava/io/File;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getDirToModule", "()Ljava/util/Map;", "getJarToAbiSnapshot", "getJarToClassListFile", "getJarToModule", "getNameToModules", "getRootProjectBuildDir", "()Ljava/io/File;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalModuleInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private final Map<File, IncrementalModuleEntry> dirToModule;
    private final Map<File, File> jarToAbiSnapshot;
    private final Map<File, File> jarToClassListFile;
    private final Map<File, IncrementalModuleEntry> jarToModule;
    private final Map<String, Set<IncrementalModuleEntry>> nameToModules;
    private final File rootProjectBuildDir;

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalModuleInfo(File rootProjectBuildDir, Map<File, IncrementalModuleEntry> dirToModule, Map<String, ? extends Set<IncrementalModuleEntry>> nameToModules, Map<File, ? extends File> jarToClassListFile, Map<File, IncrementalModuleEntry> jarToModule, Map<File, ? extends File> jarToAbiSnapshot) {
        Intrinsics.checkNotNullParameter(rootProjectBuildDir, "rootProjectBuildDir");
        Intrinsics.checkNotNullParameter(dirToModule, "dirToModule");
        Intrinsics.checkNotNullParameter(nameToModules, "nameToModules");
        Intrinsics.checkNotNullParameter(jarToClassListFile, "jarToClassListFile");
        Intrinsics.checkNotNullParameter(jarToModule, "jarToModule");
        Intrinsics.checkNotNullParameter(jarToAbiSnapshot, "jarToAbiSnapshot");
        this.rootProjectBuildDir = rootProjectBuildDir;
        this.dirToModule = dirToModule;
        this.nameToModules = nameToModules;
        this.jarToClassListFile = jarToClassListFile;
        this.jarToModule = jarToModule;
        this.jarToAbiSnapshot = jarToAbiSnapshot;
    }

    public final File getRootProjectBuildDir() {
        return this.rootProjectBuildDir;
    }

    public final Map<File, IncrementalModuleEntry> getDirToModule() {
        return this.dirToModule;
    }

    public final Map<String, Set<IncrementalModuleEntry>> getNameToModules() {
        return this.nameToModules;
    }

    public final Map<File, File> getJarToClassListFile() {
        return this.jarToClassListFile;
    }

    public final Map<File, IncrementalModuleEntry> getJarToModule() {
        return this.jarToModule;
    }

    public final Map<File, File> getJarToAbiSnapshot() {
        return this.jarToAbiSnapshot;
    }
}
