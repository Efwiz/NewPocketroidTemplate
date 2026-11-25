package org.jetbrains.kotlin.build;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: generatedFiles.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/build/GeneratedFile;", "", "sourceFiles", "", "Ljava/io/File;", "outputFile", "(Ljava/util/Collection;Ljava/io/File;)V", "getOutputFile", "()Ljava/io/File;", "", "getSourceFiles", "()Ljava/util/List;", "toString", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GeneratedFile {
    private final File outputFile;
    private final List<File> sourceFiles;

    public GeneratedFile(Collection<? extends File> sourceFiles, File outputFile) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        this.outputFile = outputFile;
        this.sourceFiles = CollectionsKt.sortedWith(sourceFiles, new Comparator() { // from class: org.jetbrains.kotlin.build.GeneratedFile$special$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((File) t).getPath(), ((File) t2).getPath());
            }
        });
    }

    public final File getOutputFile() {
        return this.outputFile;
    }

    public final List<File> getSourceFiles() {
        return this.sourceFiles;
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + this.outputFile;
    }
}
