package org.jetbrains.kotlin.com.intellij.openapi.components;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public final class ComponentConfig {
    public static final ComponentConfig[] EMPTY_ARRAY = new ComponentConfig[0];
    public final String headlessImplementationClass;
    public final String implementationClass;
    public final String interfaceClass;
    public boolean loadForDefaultProject;
    public final Map<String, String> options;
    public final ExtensionDescriptor.Os os;
    public boolean overrides;

    public ComponentConfig(String str, String str2, String str3, boolean z, ExtensionDescriptor.Os os, boolean z2, Map<String, String> map) {
        this.implementationClass = str2;
        this.interfaceClass = str;
        this.headlessImplementationClass = str3;
        this.loadForDefaultProject = z;
        this.overrides = z2;
        this.os = os;
        this.options = map;
    }

    public String toString() {
        return "ComponentConfig{implementationClass='" + this.implementationClass + "', interfaceClass='" + this.interfaceClass + "', headlessImplementationClass='" + this.headlessImplementationClass + "', loadForDefaultProject=" + this.loadForDefaultProject + ", options=" + this.options + '}';
    }
}
