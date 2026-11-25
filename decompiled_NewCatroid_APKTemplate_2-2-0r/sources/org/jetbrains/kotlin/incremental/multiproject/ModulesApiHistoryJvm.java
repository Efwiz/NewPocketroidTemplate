package org.jetbrains.kotlin.incremental.multiproject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.incremental.IncrementalModuleEntry;
import org.jetbrains.kotlin.incremental.IncrementalModuleInfo;
import org.jetbrains.kotlin.incremental.util.Either;

/* JADX INFO: compiled from: ModulesApiHistory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u0003H\u0016J\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u0003H\u0014¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryJvm;", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistoryBase;", "rootProjectDir", "Ljava/io/File;", "modulesInfo", "Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalModuleInfo;)V", "abiSnapshot", "Lorg/jetbrains/kotlin/incremental/util/Either;", "", "jar", "getBuildHistoryFilesForJar", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModulesApiHistoryJvm extends ModulesApiHistoryBase {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModulesApiHistoryJvm(File rootProjectDir, IncrementalModuleInfo modulesInfo) {
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
        File file = getModulesInfo().getJarToClassListFile().get(jar);
        if (file == null) {
            return new Either.Error("Unknown jar: " + jar);
        }
        if (!file.isFile()) {
            return new Either.Error("Class list file does not exist " + file);
        }
        try {
            String text$default = FilesKt.readText$default(file, null, 1, null);
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            List listSplit$default = StringsKt.split$default((CharSequence) text$default, new String[]{pathSeparator}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                File file2 = (File) obj;
                if (file2.exists() && file2.getParentFile() != null) {
                    arrayList2.add(obj);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj2 : arrayList2) {
                File parentFile = ((File) obj2).getParentFile();
                Object obj3 = linkedHashMap.get(parentFile);
                if (obj3 == null) {
                    obj3 = (List) new ArrayList();
                    linkedHashMap.put(parentFile, obj3);
                }
                ((List) obj3).add(obj2);
            }
            HashSet hashSet = new HashSet();
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
        } catch (Throwable th) {
            return new Either.Error("Could not read class list for " + jar + " from " + file + ": " + th);
        }
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> abiSnapshot(File jar) {
        File abiSnapshot;
        Intrinsics.checkNotNullParameter(jar, "jar");
        IncrementalModuleEntry incrementalModuleEntry = getModulesInfo().getJarToModule().get(jar);
        if (incrementalModuleEntry == null || (abiSnapshot = incrementalModuleEntry.getAbiSnapshot()) == null) {
            abiSnapshot = getModulesInfo().getJarToAbiSnapshot().get(jar);
        }
        if (abiSnapshot != null) {
            return new Either.Success(SetsKt.setOf(abiSnapshot));
        }
        return new Either.Error("Failed to find abi snapshot for file " + jar.getAbsolutePath());
    }
}
