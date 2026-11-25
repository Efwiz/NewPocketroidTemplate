package org.jetbrains.kotlin.com.intellij.ide.plugins;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: loaded from: classes3.dex */
public interface PluginUtil {
    PluginId getCallerPlugin(int i);

    static PluginUtil getInstance() {
        return (PluginUtil) ApplicationManager.getApplication().getService(PluginUtil.class);
    }
}
