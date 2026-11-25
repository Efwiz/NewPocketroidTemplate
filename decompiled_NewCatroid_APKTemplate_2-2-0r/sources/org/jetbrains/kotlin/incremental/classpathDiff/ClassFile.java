package org.jetbrains.kotlin.incremental.classpathDiff;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClassFile.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFile;", "", "classRoot", "Ljava/io/File;", "relativePath", "", "(Ljava/io/File;Ljava/lang/String;)V", "getClassRoot", "()Ljava/io/File;", "unixStyleRelativePath", "getUnixStyleRelativePath", "()Ljava/lang/String;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassFile {
    private final File classRoot;
    private final String unixStyleRelativePath;

    public ClassFile(File classRoot, String relativePath) {
        Intrinsics.checkNotNullParameter(classRoot, "classRoot");
        Intrinsics.checkNotNullParameter(relativePath, "relativePath");
        this.classRoot = classRoot;
        this.unixStyleRelativePath = FilesKt.getInvariantSeparatorsPath(new File(relativePath));
    }

    public final File getClassRoot() {
        return this.classRoot;
    }

    public final String getUnixStyleRelativePath() {
        return this.unixStyleRelativePath;
    }
}
