package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClasspathChanges.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "Ljava/io/Serializable;", "currentClasspathEntrySnapshotFiles", "", "Ljava/io/File;", "classpathSnapshotDir", "(Ljava/util/List;Ljava/io/File;)V", "getCurrentClasspathEntrySnapshotFiles", "()Ljava/util/List;", "shrunkPreviousClasspathSnapshotFile", "getShrunkPreviousClasspathSnapshotFile", "()Ljava/io/File;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathSnapshotFiles implements Serializable {
    private static final long serialVersionUID = 0;
    private final List<File> currentClasspathEntrySnapshotFiles;
    private final File shrunkPreviousClasspathSnapshotFile;

    /* JADX WARN: Multi-variable type inference failed */
    public ClasspathSnapshotFiles(List<? extends File> currentClasspathEntrySnapshotFiles, File classpathSnapshotDir) {
        Intrinsics.checkNotNullParameter(currentClasspathEntrySnapshotFiles, "currentClasspathEntrySnapshotFiles");
        Intrinsics.checkNotNullParameter(classpathSnapshotDir, "classpathSnapshotDir");
        this.currentClasspathEntrySnapshotFiles = currentClasspathEntrySnapshotFiles;
        this.shrunkPreviousClasspathSnapshotFile = new File(classpathSnapshotDir, "shrunk-classpath-snapshot.bin");
    }

    public final List<File> getCurrentClasspathEntrySnapshotFiles() {
        return this.currentClasspathEntrySnapshotFiles;
    }

    public final File getShrunkPreviousClasspathSnapshotFile() {
        return this.shrunkPreviousClasspathSnapshotFile;
    }
}
