package org.jetbrains.kotlin.compilerRunner;

import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SimpleOutputItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J#\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/compilerRunner/SimpleOutputItem;", "", "sourceFiles", "", "Ljava/io/File;", "outputFile", "(Ljava/util/Collection;Ljava/io/File;)V", "getOutputFile", "()Ljava/io/File;", "getSourceFiles", "()Ljava/util/Collection;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SimpleOutputItem {
    private final File outputFile;
    private final Collection<File> sourceFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SimpleOutputItem copy$default(SimpleOutputItem simpleOutputItem, Collection collection, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = simpleOutputItem.sourceFiles;
        }
        if ((i & 2) != 0) {
            file = simpleOutputItem.outputFile;
        }
        return simpleOutputItem.copy(collection, file);
    }

    public final Collection<File> component1() {
        return this.sourceFiles;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final File getOutputFile() {
        return this.outputFile;
    }

    public final SimpleOutputItem copy(Collection<? extends File> sourceFiles, File outputFile) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        return new SimpleOutputItem(sourceFiles, outputFile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimpleOutputItem)) {
            return false;
        }
        SimpleOutputItem simpleOutputItem = (SimpleOutputItem) other;
        return Intrinsics.areEqual(this.sourceFiles, simpleOutputItem.sourceFiles) && Intrinsics.areEqual(this.outputFile, simpleOutputItem.outputFile);
    }

    public int hashCode() {
        return (this.sourceFiles.hashCode() * 31) + this.outputFile.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleOutputItem(Collection<? extends File> sourceFiles, File outputFile) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(outputFile, "outputFile");
        this.sourceFiles = sourceFiles;
        this.outputFile = outputFile;
    }

    public final File getOutputFile() {
        return this.outputFile;
    }

    public final Collection<File> getSourceFiles() {
        return this.sourceFiles;
    }

    public String toString() {
        return this.sourceFiles + "->" + this.outputFile;
    }
}
