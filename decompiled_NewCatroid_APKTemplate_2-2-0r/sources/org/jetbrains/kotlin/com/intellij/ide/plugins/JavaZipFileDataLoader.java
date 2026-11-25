package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: PluginDescriptorLoader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/JavaZipFileDataLoader;", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "file", "Ljava/util/zip/ZipFile;", "(Ljava/util/zip/ZipFile;)V", "pool", "Lorg/jetbrains/kotlin/com/intellij/util/lang/ZipFilePool;", "getPool", "()Lcom/intellij/util/lang/ZipFilePool;", "load", "Ljava/io/InputStream;", "path", "", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
final class JavaZipFileDataLoader implements DataLoader {
    private final ZipFile file;

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public ZipFilePool getPool() {
        return null;
    }

    public JavaZipFileDataLoader(ZipFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public boolean isExcludedFromSubSearch(Path jarFile) {
        Intrinsics.checkNotNullParameter(jarFile, "jarFile");
        return DataLoader.DefaultImpls.isExcludedFromSubSearch(this, jarFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public InputStream load(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        ZipFile zipFile = this.file;
        if (path.charAt(0) == '/') {
            path = path.substring(1);
            Intrinsics.checkNotNullExpressionValue(path, "(this as java.lang.String).substring(startIndex)");
        }
        ZipEntry entry = zipFile.getEntry(path);
        if (entry != null) {
            return this.file.getInputStream(entry);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader
    public String toString() {
        return this.file.toString();
    }
}
