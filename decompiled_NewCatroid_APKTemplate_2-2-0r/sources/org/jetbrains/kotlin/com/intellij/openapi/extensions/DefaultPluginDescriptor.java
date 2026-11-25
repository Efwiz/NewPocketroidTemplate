package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.nio.file.Path;

/* JADX INFO: loaded from: classes3.dex */
public final class DefaultPluginDescriptor implements PluginDescriptor {
    private final ClassLoader myPluginClassLoader;
    private final PluginId myPluginId;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/DefaultPluginDescriptor";
        } else {
            objArr[0] = "pluginId";
        }
        if (i == 3) {
            objArr[1] = "getPluginId";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/DefaultPluginDescriptor";
        } else {
            objArr[1] = "getOptionalDependentPluginIds";
        }
        if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public Path getPluginPath() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getSinceBuild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getUntilBuild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getVersion() {
        return null;
    }

    public DefaultPluginDescriptor(PluginId pluginId) {
        if (pluginId == null) {
            $$$reportNull$$$0(1);
        }
        this.myPluginId = pluginId;
        this.myPluginClassLoader = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public PluginId getPluginId() {
        PluginId pluginId = this.myPluginId;
        if (pluginId == null) {
            $$$reportNull$$$0(3);
        }
        return pluginId;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public ClassLoader getPluginClassLoader() {
        return this.myPluginClassLoader;
    }

    public String toString() {
        return "Default plugin descriptor for " + this.myPluginId;
    }
}
