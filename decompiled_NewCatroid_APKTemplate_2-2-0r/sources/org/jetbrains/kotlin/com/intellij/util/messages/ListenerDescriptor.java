package org.jetbrains.kotlin.com.intellij.util.messages;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public final class ListenerDescriptor {
    public final boolean activeInHeadlessMode;
    public final boolean activeInTestMode;
    public final String listenerClassName;
    public final ExtensionDescriptor.Os os;
    public transient PluginDescriptor pluginDescriptor;
    public final String topicClassName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "listenerClassName";
        } else {
            objArr[0] = "topicClassName";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/ListenerDescriptor";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ListenerDescriptor(ExtensionDescriptor.Os os, String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        this.os = os;
        this.listenerClassName = str;
        this.topicClassName = str2;
        this.activeInTestMode = z;
        this.activeInHeadlessMode = z2;
    }
}
