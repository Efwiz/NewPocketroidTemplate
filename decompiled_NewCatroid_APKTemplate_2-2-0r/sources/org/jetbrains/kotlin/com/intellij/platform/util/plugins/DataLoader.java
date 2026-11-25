package org.jetbrains.kotlin.com.intellij.platform.util.plugins;

import java.io.InputStream;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.lang.ZipFilePool;

/* JADX INFO: compiled from: dataLoader.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\rH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "", "pool", "Lorg/jetbrains/kotlin/com/intellij/util/lang/ZipFilePool;", "getPool", "()Lcom/intellij/util/lang/ZipFilePool;", "isExcludedFromSubSearch", "", "jarFile", "Ljava/nio/file/Path;", "load", "Ljava/io/InputStream;", "path", "", "toString", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public interface DataLoader {

    /* JADX INFO: compiled from: dataLoader.kt */
    @Metadata(k = 3, mv = {1, 5, 1})
    public static final class DefaultImpls {
        public static boolean isExcludedFromSubSearch(DataLoader dataLoader, Path jarFile) {
            Intrinsics.checkNotNullParameter(jarFile, "jarFile");
            return false;
        }
    }

    ZipFilePool getPool();

    boolean isExcludedFromSubSearch(Path jarFile);

    InputStream load(String path);

    String toString();
}
