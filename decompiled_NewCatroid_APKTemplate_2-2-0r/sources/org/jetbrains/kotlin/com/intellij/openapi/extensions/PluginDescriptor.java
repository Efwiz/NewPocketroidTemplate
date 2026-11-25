package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.nio.file.Path;

/* JADX INFO: loaded from: classes3.dex */
public interface PluginDescriptor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/extensions/PluginDescriptor", "getClassLoader"));
    }

    String getName();

    ClassLoader getPluginClassLoader();

    PluginId getPluginId();

    Path getPluginPath();

    String getSinceBuild();

    String getUntilBuild();

    String getVersion();

    default boolean isBundled() {
        return false;
    }

    default ClassLoader getClassLoader() {
        ClassLoader pluginClassLoader = getPluginClassLoader();
        if (pluginClassLoader == null) {
            pluginClassLoader = getClass().getClassLoader();
        }
        if (pluginClassLoader == null) {
            $$$reportNull$$$0(0);
        }
        return pluginClassLoader;
    }
}
