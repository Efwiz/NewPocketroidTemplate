package org.jetbrains.kotlin.incremental.multiproject;

import java.io.File;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalModuleEntry;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;
import org.jetbrains.kotlin.incremental.util.Either;

/* JADX INFO: compiled from: ModulesApiHistory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u0003H\u0016J\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u0003H\u0014¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryJs;", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryBase;", "rootProjectDir", "Ljava/io/File;", "modulesInfo", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;)V", "abiSnapshot", "Lorg/jetbrains/kotlin/incremental/util/Either;", "", "jar", "getBuildHistoryFilesForJar", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModulesApiHistoryJs extends ModulesApiHistoryBase {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModulesApiHistoryJs(File rootProjectDir, IncrementalModuleInfo modulesInfo) {
        super(rootProjectDir, modulesInfo);
        Intrinsics.checkNotNullParameter(rootProjectDir, "rootProjectDir");
        Intrinsics.checkNotNullParameter(modulesInfo, "modulesInfo");
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistoryBase
    protected Either<Set<File>> getBuildHistoryFilesForJar(File jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        IncrementalModuleEntry incrementalModuleEntry = getModulesInfo().getJarToModule().get(jar);
        if (incrementalModuleEntry != null) {
            return new Either.Success(SetsKt.setOf(incrementalModuleEntry.getBuildHistoryFile()));
        }
        return new Either.Error("No module is found for jar " + jar);
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> abiSnapshot(File jar) {
        File abiSnapshot;
        Intrinsics.checkNotNullParameter(jar, "jar");
        IncrementalModuleEntry incrementalModuleEntry = getModulesInfo().getJarToModule().get(jar);
        return (incrementalModuleEntry == null || (abiSnapshot = incrementalModuleEntry.getAbiSnapshot()) == null) ? new Either.Error("Failed to find snapshot for file " + jar.getAbsolutePath()) : new Either.Success(SetsKt.setOf(abiSnapshot));
    }
}
