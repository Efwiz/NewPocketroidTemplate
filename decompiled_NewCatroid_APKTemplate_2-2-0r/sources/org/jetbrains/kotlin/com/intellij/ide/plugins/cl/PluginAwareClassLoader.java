package org.jetbrains.kotlin.com.intellij.ide.plugins.cl;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: loaded from: classes3.dex */
public interface PluginAwareClassLoader {
    PluginId getPluginId();

    int getState();
}
