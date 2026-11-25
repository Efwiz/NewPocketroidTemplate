package org.jetbrains.kotlin.utils;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinPaths.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinPathsFromBaseDirectory;", "Lorg/jetbrains/kotlin/utils/KotlinPaths;", "basePath", "Ljava/io/File;", "(Ljava/io/File;)V", "getBasePath", "()Ljava/io/File;", "homePath", "getHomePath", "libPath", "getLibPath", "jar", "Lorg/jetbrains/kotlin/utils/KotlinPaths$Jar;", "sourcesJar", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinPathsFromBaseDirectory implements KotlinPaths {
    private final File basePath;

    public KotlinPathsFromBaseDirectory(File basePath) {
        Intrinsics.checkNotNullParameter(basePath, "basePath");
        this.basePath = basePath;
    }

    public final File getBasePath() {
        return this.basePath;
    }

    @Override // org.jetbrains.kotlin.utils.KotlinPaths
    public File getHomePath() {
        throw new IllegalStateException("No home path defined");
    }

    @Override // org.jetbrains.kotlin.utils.KotlinPaths
    public File getLibPath() {
        return this.basePath;
    }

    @Override // org.jetbrains.kotlin.utils.KotlinPaths
    public File jar(KotlinPaths.Jar jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        return FilesKt.resolve(this.basePath, jar.getBaseName() + ".jar");
    }

    @Override // org.jetbrains.kotlin.utils.KotlinPaths
    public File sourcesJar(KotlinPaths.Jar jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        return FilesKt.resolve(this.basePath, jar.getBaseName() + "-sources.jar");
    }
}
