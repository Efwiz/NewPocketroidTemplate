package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.incremental.snapshots.FileSnapshotMap;
import org.jetbrains.kotlin.incremental.storage.BasicMapsOwner;
import org.jetbrains.kotlin.incremental.storage.SourceToOutputFilesMap;

/* JADX INFO: compiled from: InputsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010J\u0014\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eJ\u0014\u0010\u0015\u001a\u00020\u00122\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/InputsCache;", "Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "workingDir", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "sourceSnapshotMap", "Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotMap;", "getSourceSnapshotMap$incremental_compilation_impl", "()Lorg/jetbrains/kotlin/incremental/snapshots/FileSnapshotMap;", "sourceToOutputMap", "Lorg/jetbrains/kotlin/incremental/storage/SourceToOutputFilesMap;", "getOutputForSourceFiles", "", "sources", "", "registerOutputForSourceFiles", "", "generatedFiles", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "removeOutputForSourceFiles", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InputsCache extends BasicMapsOwner {
    private final IncrementalCompilationContext icContext;
    private final FileSnapshotMap sourceSnapshotMap;
    private final SourceToOutputFilesMap sourceToOutputMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InputsCache(File workingDir, IncrementalCompilationContext icContext) {
        super(workingDir);
        Intrinsics.checkNotNullParameter(workingDir, "workingDir");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.icContext = icContext;
        this.sourceSnapshotMap = (FileSnapshotMap) registerMap(new FileSnapshotMap(getStorageFile("source-snapshot"), icContext));
        this.sourceToOutputMap = (SourceToOutputFilesMap) registerMap(new SourceToOutputFilesMap(getStorageFile("source-to-output"), icContext));
    }

    /* JADX INFO: renamed from: getSourceSnapshotMap$incremental_compilation_impl, reason: from getter */
    public final FileSnapshotMap getSourceSnapshotMap() {
        return this.sourceSnapshotMap;
    }

    public final void removeOutputForSourceFiles(Iterable<? extends File> sources) {
        Intrinsics.checkNotNullParameter(sources, "sources");
        for (final File file : sources) {
            Collection<File> andRemove = this.sourceToOutputMap.getAndRemove(file);
            if (andRemove != null) {
                for (final File file2 : andRemove) {
                    ICReporterKt.debug(this.icContext.getReporter(), new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.InputsCache$removeOutputForSourceFiles$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final String invoke() {
                            return "Deleting " + file2 + " on clearing cache for " + file;
                        }
                    });
                    CompilationTransaction transaction = this.icContext.getTransaction();
                    Path path = file2.toPath();
                    Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
                    transaction.deleteFile(path);
                }
            }
        }
    }

    public final void registerOutputForSourceFiles(List<? extends GeneratedFile> generatedFiles) {
        Intrinsics.checkNotNullParameter(generatedFiles, "generatedFiles");
        MultiMap multiMap = new MultiMap();
        for (GeneratedFile generatedFile : generatedFiles) {
            Iterator<File> it = generatedFile.getSourceFiles().iterator();
            while (it.hasNext()) {
                multiMap.putValue(it.next(), generatedFile.getOutputFile());
            }
        }
        for (Map.Entry entry : multiMap.entrySet()) {
            Intrinsics.checkNotNull(entry);
            File file = (File) entry.getKey();
            Collection<? extends File> collection = (Collection) entry.getValue();
            SourceToOutputFilesMap sourceToOutputFilesMap = this.sourceToOutputMap;
            Intrinsics.checkNotNull(file);
            Intrinsics.checkNotNull(collection);
            sourceToOutputFilesMap.set(file, collection);
        }
    }

    public final List<File> getOutputForSourceFiles(Iterable<? extends File> sources) {
        Intrinsics.checkNotNullParameter(sources, "sources");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = sources.iterator();
        while (it.hasNext()) {
            List listEmptyList = this.sourceToOutputMap.get(it.next());
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }
}
