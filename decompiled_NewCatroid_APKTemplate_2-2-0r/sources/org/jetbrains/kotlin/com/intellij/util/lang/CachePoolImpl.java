package org.jetbrains.kotlin.com.intellij.util.lang;

import java.nio.file.Path;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
final class CachePoolImpl {
    final Map<Path, ClasspathCache.LoaderData> loaderIndexCache;
    private final Map<Path, Map<Loader.Attribute, String>> manifestData;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "file";
        } else {
            objArr[0] = "manifestAttributes";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/CachePoolImpl";
        if (i == 1 || i == 2) {
            objArr[2] = "cacheManifestData";
        } else {
            objArr[2] = "getManifestData";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    Map<Loader.Attribute, String> getManifestData(Path path) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        return this.manifestData.get(path);
    }

    void cacheManifestData(Path path, Map<Loader.Attribute, String> map) {
        if (path == null) {
            $$$reportNull$$$0(1);
        }
        if (map == null) {
            $$$reportNull$$$0(2);
        }
        this.manifestData.put(path, map);
    }
}
