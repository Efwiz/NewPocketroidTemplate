package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import org.jetbrains.kotlin.com.intellij.openapi.application.CachedSingletonsRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue;

/* JADX INFO: loaded from: classes3.dex */
public final class StandardFileSystems {
    public static final String FILE_PROTOCOL = "file";
    public static final String FILE_PROTOCOL_PREFIX = "file://";
    public static final String JAR_PROTOCOL = "jar";
    public static final String JAR_PROTOCOL_PREFIX = "jar://";
    public static final String JRT_PROTOCOL = "jrt";
    public static final String JRT_PROTOCOL_PREFIX = "jrt://";
    private static final ClearableLazyValue<VirtualFileSystem> ourLocal = CachedSingletonsRegistry.markLazyValue(new ClearableLazyValue<VirtualFileSystem>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/vfs/StandardFileSystems$1", "compute"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
        public VirtualFileSystem compute() {
            VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
            if (fileSystem == null) {
                $$$reportNull$$$0(0);
            }
            return fileSystem;
        }
    });
    private static final ClearableLazyValue<VirtualFileSystem> ourJar = CachedSingletonsRegistry.markLazyValue(new ClearableLazyValue<VirtualFileSystem>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.2
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/vfs/StandardFileSystems$2", "compute"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
        public VirtualFileSystem compute() {
            VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("jar");
            if (fileSystem == null) {
                $$$reportNull$$$0(0);
            }
            return fileSystem;
        }
    });

    public static VirtualFileSystem local() {
        return ourLocal.getValue();
    }

    public static VirtualFileSystem jar() {
        return ourJar.getValue();
    }
}
