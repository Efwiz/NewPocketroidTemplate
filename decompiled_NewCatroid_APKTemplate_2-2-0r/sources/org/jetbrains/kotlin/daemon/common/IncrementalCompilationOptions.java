package org.jetbrains.kotlin.daemon.common;

import java.io.File;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.incremental.ClasspathChanges;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;

/* JADX INFO: compiled from: CompilationOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001c\u0018\u0000 92\u00020\u0001:\u00019Bç\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u0006\u0010\u0014\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0003¢\u0006\u0002\u0010\"J\b\u00108\u001a\u00020\u001eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010!\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b,\u0010*R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0019\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0016¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0011\u0010 \u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010$R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b4\u0010&R\u0011\u0010\u0014\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b5\u0010$R\u0011\u0010\u001f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u0010$R\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b7\u0010&¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/IncrementalCompilationOptions;", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "areFileChangesKnown", "", "modifiedFiles", "", "Ljava/io/File;", "deletedFiles", "classpathChanges", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "workingDir", "compilerMode", "Lorg/jetbrains/kotlin/daemon/common/CompilerMode;", "targetPlatform", "Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;", "reportCategories", "", "", "reportSeverity", "requestedCompilationResults", "usePreciseJavaTracking", "outputFiles", "", "multiModuleICSettings", "Lorg/jetbrains/kotlin/daemon/common/MultiModuleICSettings;", "modulesInfo", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "rootProjectDir", "buildDir", "kotlinScriptExtensions", "", "withAbiSnapshot", "preciseCompilationResultsBackup", "keepIncrementalCompilationCachesInMemory", "(ZLjava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/incremental/ClasspathChanges;Ljava/io/File;Lorg/jetbrains/kotlin/daemon/common/CompilerMode;Lorg/jetbrains/kotlin/daemon/common/CompileService$TargetPlatform;[Ljava/lang/Integer;I[Ljava/lang/Integer;ZLjava/util/Collection;Lorg/jetbrains/kotlin/daemon/common/MultiModuleICSettings;Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;Ljava/io/File;Ljava/io/File;[Ljava/lang/String;ZZZ)V", "getAreFileChangesKnown", "()Z", "getBuildDir", "()Ljava/io/File;", "getClasspathChanges", "()Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "getDeletedFiles", "()Ljava/util/List;", "getKeepIncrementalCompilationCachesInMemory", "getModifiedFiles", "getModulesInfo", "()Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "getMultiModuleICSettings", "()Lorg/jetbrains/kotlin/daemon/common/MultiModuleICSettings;", "getOutputFiles", "()Ljava/util/Collection;", "getPreciseCompilationResultsBackup", "getRootProjectDir", "getUsePreciseJavaTracking", "getWithAbiSnapshot", "getWorkingDir", "toString", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCompilationOptions extends CompilationOptions {
    public static final long serialVersionUID = 3;
    private final boolean areFileChangesKnown;
    private final File buildDir;
    private final ClasspathChanges classpathChanges;
    private final List<File> deletedFiles;
    private final boolean keepIncrementalCompilationCachesInMemory;
    private final List<File> modifiedFiles;
    private final IncrementalModuleInfo modulesInfo;
    private final MultiModuleICSettings multiModuleICSettings;
    private final Collection<File> outputFiles;
    private final boolean preciseCompilationResultsBackup;
    private final File rootProjectDir;
    private final boolean usePreciseJavaTracking;
    private final boolean withAbiSnapshot;
    private final File workingDir;

    public /* synthetic */ IncrementalCompilationOptions(boolean z, List list, List list2, ClasspathChanges classpathChanges, File file, CompilerMode compilerMode, CompileService.TargetPlatform targetPlatform, Integer[] numArr, int i, Integer[] numArr2, boolean z2, Collection collection, MultiModuleICSettings multiModuleICSettings, IncrementalModuleInfo incrementalModuleInfo, File file2, File file3, String[] strArr, boolean z3, boolean z4, boolean z5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, list, list2, classpathChanges, file, compilerMode, targetPlatform, numArr, i, numArr2, z2, (i2 & 2048) != 0 ? null : collection, (i2 & 4096) != 0 ? null : multiModuleICSettings, (i2 & 8192) != 0 ? null : incrementalModuleInfo, file2, file3, (65536 & i2) != 0 ? null : strArr, (131072 & i2) != 0 ? false : z3, (262144 & i2) != 0 ? false : z4, (i2 & 524288) != 0 ? false : z5);
    }

    public final boolean getAreFileChangesKnown() {
        return this.areFileChangesKnown;
    }

    public final List<File> getModifiedFiles() {
        return this.modifiedFiles;
    }

    public final List<File> getDeletedFiles() {
        return this.deletedFiles;
    }

    public final ClasspathChanges getClasspathChanges() {
        return this.classpathChanges;
    }

    public final File getWorkingDir() {
        return this.workingDir;
    }

    public final boolean getUsePreciseJavaTracking() {
        return this.usePreciseJavaTracking;
    }

    public final Collection<File> getOutputFiles() {
        return this.outputFiles;
    }

    public final MultiModuleICSettings getMultiModuleICSettings() {
        return this.multiModuleICSettings;
    }

    public final IncrementalModuleInfo getModulesInfo() {
        return this.modulesInfo;
    }

    public final File getRootProjectDir() {
        return this.rootProjectDir;
    }

    public final File getBuildDir() {
        return this.buildDir;
    }

    public final boolean getWithAbiSnapshot() {
        return this.withAbiSnapshot;
    }

    public final boolean getPreciseCompilationResultsBackup() {
        return this.preciseCompilationResultsBackup;
    }

    public final boolean getKeepIncrementalCompilationCachesInMemory() {
        return this.keepIncrementalCompilationCachesInMemory;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalCompilationOptions(boolean z, List<? extends File> list, List<? extends File> list2, ClasspathChanges classpathChanges, File workingDir, CompilerMode compilerMode, CompileService.TargetPlatform targetPlatform, Integer[] reportCategories, int i, Integer[] requestedCompilationResults, boolean z2, Collection<? extends File> collection, MultiModuleICSettings multiModuleICSettings, IncrementalModuleInfo incrementalModuleInfo, File file, File file2, String[] strArr, boolean z3, boolean z4, boolean z5) {
        super(compilerMode, targetPlatform, reportCategories, i, requestedCompilationResults, strArr);
        Intrinsics.checkNotNullParameter(classpathChanges, "classpathChanges");
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(compilerMode, "compilerMode");
        Intrinsics.checkNotNullParameter(targetPlatform, "targetPlatform");
        Intrinsics.checkNotNullParameter(reportCategories, "reportCategories");
        Intrinsics.checkNotNullParameter(requestedCompilationResults, "requestedCompilationResults");
        this.areFileChangesKnown = z;
        this.modifiedFiles = list;
        this.deletedFiles = list2;
        this.classpathChanges = classpathChanges;
        this.workingDir = workingDir;
        this.usePreciseJavaTracking = z2;
        this.outputFiles = collection;
        this.multiModuleICSettings = multiModuleICSettings;
        this.modulesInfo = incrementalModuleInfo;
        this.rootProjectDir = file;
        this.buildDir = file2;
        this.withAbiSnapshot = z3;
        this.preciseCompilationResultsBackup = z4;
        this.keepIncrementalCompilationCachesInMemory = z5;
    }

    @Override // org.jetbrains.kotlin.daemon.common.CompilationOptions
    public String toString() {
        return "IncrementalCompilationOptions(super=" + super.toString() + ", areFileChangesKnown=" + this.areFileChangesKnown + ", modifiedFiles=" + this.modifiedFiles + ", deletedFiles=" + this.deletedFiles + ", classpathChanges=" + Reflection.getOrCreateKotlinClass(this.classpathChanges.getClass()).getSimpleName() + ", workingDir=" + this.workingDir + ", multiModuleICSettings=" + this.multiModuleICSettings + ", usePreciseJavaTracking=" + this.usePreciseJavaTracking + ", outputFiles=" + this.outputFiles + ')';
    }
}
