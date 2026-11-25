package org.jetbrains.kotlin.com.intellij.platform.util.plugins;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: dataLoader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/LocalFsDataLoader;", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "basePath", "Ljava/nio/file/Path;", "(Ljava/nio/file/Path;)V", "getBasePath", "()Ljava/nio/file/Path;", "pool", "Lorg/jetbrains/kotlin/com/intellij/util/lang/ZipFilePool;", "getPool", "()Lcom/intellij/util/lang/ZipFilePool;", "load", "Ljava/io/InputStream;", "path", "", "toString", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public final class LocalFsDataLoader implements DataLoader {
    private final Path basePath;

    public LocalFsDataLoader(Path basePath) {
        Intrinsics.checkNotNullParameter(basePath, "basePath");
        this.basePath = basePath;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public boolean isExcludedFromSubSearch(Path jarFile) {
        Intrinsics.checkNotNullParameter(jarFile, "jarFile");
        return DataLoader.DefaultImpls.isExcludedFromSubSearch(this, jarFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public ZipFilePool getPool() {
        return ZipFilePool.POOL;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public InputStream load(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        try {
            return Files.newInputStream(this.basePath.resolve(path), new OpenOption[0]);
        } catch (NoSuchFileException unused) {
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public String toString() {
        return this.basePath.toString();
    }
}
