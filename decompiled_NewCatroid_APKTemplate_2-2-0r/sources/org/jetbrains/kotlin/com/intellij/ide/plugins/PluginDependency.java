package org.jetbrains.kotlin.com.intellij.ide.plugins;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: compiled from: PluginDependency.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B!\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0015\u001a\u00020\u0005H\u0016R(\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\t\u001a\u0004\u0018\u00010\u0005@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007@PX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginDependency;", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDependency;", "pluginId", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "configFile", "", "isOptional", "", "(Lcom/intellij/openapi/extensions/PluginId;Ljava/lang/String;Z)V", "<set-?>", "getConfigFile", "()Ljava/lang/String;", "setConfigFile$intellij_platform_core_impl", "(Ljava/lang/String;)V", "()Z", "setOptional$intellij_platform_core_impl", "(Z)V", "getPluginId", "()Lcom/intellij/openapi/extensions/PluginId;", "subDescriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class PluginDependency {
    private String configFile;
    private boolean isOptional;
    private final PluginId pluginId;
    public transient IdeaPluginDescriptorImpl subDescriptor;

    public PluginDependency(PluginId pluginId, String str, boolean z) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        this.pluginId = pluginId;
        this.configFile = str;
        this.isOptional = z;
    }

    public PluginId getPluginId() {
        return this.pluginId;
    }

    public final String getConfigFile() {
        return this.configFile;
    }

    /* JADX INFO: renamed from: isOptional, reason: from getter */
    public boolean getIsOptional() {
        return this.isOptional;
    }

    public void setOptional$intellij_platform_core_impl(boolean z) {
        this.isOptional = z;
    }

    public String toString() {
        return "PluginDependency(pluginId=" + getPluginId() + ", isOptional=" + getIsOptional() + ", configFile=" + this.configFile + ", subDescriptor=" + this.subDescriptor + ')';
    }
}
