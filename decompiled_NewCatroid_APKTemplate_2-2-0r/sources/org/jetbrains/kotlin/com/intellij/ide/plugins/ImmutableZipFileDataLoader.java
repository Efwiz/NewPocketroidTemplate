package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.io.InputStream;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: ImmutableZipFileDataLoader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ImmutableZipFileDataLoader;", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "resolver", "Lorg/jetbrains/kotlin/com/intellij/util/lang/ZipFilePool$EntryResolver;", "zipPath", "Ljava/nio/file/Path;", "pool", "Lorg/jetbrains/kotlin/com/intellij/util/lang/ZipFilePool;", "(Lcom/intellij/util/lang/ZipFilePool$EntryResolver;Ljava/nio/file/Path;Lcom/intellij/util/lang/ZipFilePool;)V", "getPool", "()Lcom/intellij/util/lang/ZipFilePool;", "isExcludedFromSubSearch", "", "jarFile", "load", "Ljava/io/InputStream;", "path", "", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class ImmutableZipFileDataLoader implements DataLoader {
    private final ZipFilePool pool;
    private final ZipFilePool.EntryResolver resolver;
    private final Path zipPath;

    public ImmutableZipFileDataLoader(ZipFilePool.EntryResolver resolver, Path zipPath, ZipFilePool pool) {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(zipPath, "zipPath");
        Intrinsics.checkNotNullParameter(pool, "pool");
        this.resolver = resolver;
        this.zipPath = zipPath;
        this.pool = pool;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public ZipFilePool getPool() {
        return this.pool;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public InputStream load(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        ZipFilePool.EntryResolver entryResolver = this.resolver;
        if (path.charAt(0) == '/') {
            path = path.substring(1);
            Intrinsics.checkNotNullExpressionValue(path, "(this as java.lang.String).substring(startIndex)");
        }
        return entryResolver.loadZipEntry(path);
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public boolean isExcludedFromSubSearch(Path jarFile) {
        Intrinsics.checkNotNullParameter(jarFile, "jarFile");
        return jarFile == this.zipPath;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public String toString() {
        return this.resolver.toString();
    }
}
