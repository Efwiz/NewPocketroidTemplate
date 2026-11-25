package org.jetbrains.kotlin.incremental.multiproject;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.incremental.IncrementalModuleEntry;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;
import org.jetbrains.kotlin.incremental.util.Either;

/* JADX INFO: compiled from: ModulesApiHistory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\u00122\u0006\u0010\u0013\u001a\u00020\u0003H$J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\u00122\u0006\u0010\u0015\u001a\u00020\u0003H\u0014J\"\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\u00122\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0003H\u0004R6\u0010\u0007\u001a*\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\bj\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryBase;", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "rootProjectDir", "Ljava/io/File;", "modulesInfo", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;)V", "dirToHistoryFileCache", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getModulesInfo", "()Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "possibleParentsToBuildDirs", "Ljava/nio/file/Path;", "getPossibleParentsToBuildDirs", "()Ljava/util/Set;", "getBuildHistoryFilesForJar", "Lorg/jetbrains/kotlin/incremental/util/Either;", "jar", "getBuildHistoryForDir", "file", "historyFilesForChangedFiles", "changedFiles", "isInProjectBuildDir", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ModulesApiHistoryBase implements ModulesApiHistory {
    private final HashMap<File, Set<File>> dirToHistoryFileCache;
    private final IncrementalModuleInfo modulesInfo;
    private final Set<Path> possibleParentsToBuildDirs;

    protected abstract Either<Set<File>> getBuildHistoryFilesForJar(File jar);

    public ModulesApiHistoryBase(File rootProjectDir, IncrementalModuleInfo modulesInfo) {
        Intrinsics.checkNotNullParameter(rootProjectDir, "rootProjectDir");
        Intrinsics.checkNotNullParameter(modulesInfo, "modulesInfo");
        this.modulesInfo = modulesInfo;
        this.possibleParentsToBuildDirs = SetsKt.setOf((Object[]) new Path[]{Paths.get(modulesInfo.getRootProjectBuildDir().getParentFile().getAbsolutePath(), new String[0]), Paths.get(rootProjectDir.getAbsolutePath(), new String[0])});
        this.dirToHistoryFileCache = new HashMap<>();
    }

    protected final IncrementalModuleInfo getModulesInfo() {
        return this.modulesInfo;
    }

    protected final Set<Path> getPossibleParentsToBuildDirs() {
        return this.possibleParentsToBuildDirs;
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> historyFilesForChangedFiles(Set<? extends File> changedFiles) {
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        HashSet hashSet = new HashSet();
        ArrayList<File> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (File file : changedFiles) {
            String extension = FilesKt.getExtension(file);
            if (StringsKt.equals(extension, "class", true)) {
                arrayList2.add(file);
            } else if (StringsKt.equals(extension, "jar", true)) {
                arrayList.add(file);
            } else if (StringsKt.equals(extension, "klib", true)) {
                arrayList.add(file);
            }
        }
        for (File file2 : arrayList) {
            Intrinsics.checkNotNull(file2);
            Either<Set<File>> buildHistoryFilesForJar = getBuildHistoryFilesForJar(file2);
            if (buildHistoryFilesForJar instanceof Either.Success) {
                hashSet.addAll((Collection) ((Either.Success) buildHistoryFilesForJar).getValue());
            } else if (buildHistoryFilesForJar instanceof Either.Error) {
                return buildHistoryFilesForJar;
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : arrayList2) {
            File parentFile = ((File) obj).getParentFile();
            Object obj2 = linkedHashMap.get(parentFile);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(parentFile, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (File file3 : linkedHashMap.keySet()) {
            Intrinsics.checkNotNull(file3);
            Either<Set<File>> buildHistoryForDir = getBuildHistoryForDir(file3);
            if (buildHistoryForDir instanceof Either.Success) {
                hashSet.addAll((Collection) ((Either.Success) buildHistoryForDir).getValue());
            } else if (buildHistoryForDir instanceof Either.Error) {
                return buildHistoryForDir;
            }
        }
        return new Either.Success(hashSet);
    }

    protected Either<Set<File>> getBuildHistoryForDir(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        HashMap<File, Set<File>> map = this.dirToHistoryFileCache;
        Set<File> of = map.get(file);
        if (of == null) {
            IncrementalModuleEntry incrementalModuleEntry = this.modulesInfo.getDirToModule().get(file);
            File parentFile = file.getParentFile();
            if (incrementalModuleEntry != null) {
                of = SetsKt.setOf(incrementalModuleEntry.getBuildHistoryFile());
            } else if (parentFile != null && isInProjectBuildDir(parentFile)) {
                Either<Set<File>> buildHistoryForDir = getBuildHistoryForDir(parentFile);
                if (!(buildHistoryForDir instanceof Either.Success)) {
                    if (buildHistoryForDir instanceof Either.Error) {
                        return buildHistoryForDir;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                of = (Set) ((Either.Success) buildHistoryForDir).getValue();
            } else {
                return new Either.Error("Unable to get build history for " + file);
            }
            map.put(file, of);
        }
        return new Either.Success(of);
    }

    protected final boolean isInProjectBuildDir(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        Set<Path> set = this.possibleParentsToBuildDirs;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (ModulesApiHistoryKt.isParentOf((Path) it.next(), file)) {
                return true;
            }
        }
        return false;
    }
}
