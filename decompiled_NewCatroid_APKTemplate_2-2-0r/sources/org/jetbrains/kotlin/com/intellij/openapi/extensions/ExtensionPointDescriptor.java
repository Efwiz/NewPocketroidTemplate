package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExtensionDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0010\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointDescriptor;", "", "name", "", "isNameQualified", "", "className", "isBean", "isDynamic", "(Ljava/lang/String;ZLjava/lang/String;ZZ)V", "getQualifiedName", "pluginDescriptor", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginDescriptor;", "nameEquals", "qualifiedName", "toString", "intellij.platform.extensions"}, k = 1, mv = {1, 5, 1})
public final class ExtensionPointDescriptor {
    public final String className;
    public final boolean isBean;
    public final boolean isDynamic;
    public final boolean isNameQualified;
    public final String name;

    public ExtensionPointDescriptor(String name2, boolean z, String className, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(className, "className");
        this.name = name2;
        this.isNameQualified = z;
        this.className = className;
        this.isBean = z2;
        this.isDynamic = z3;
    }

    public final String getQualifiedName(PluginDescriptor pluginDescriptor) {
        Intrinsics.checkNotNullParameter(pluginDescriptor, "pluginDescriptor");
        return this.isNameQualified ? this.name : pluginDescriptor.getPluginId() + '.' + this.name;
    }

    public String toString() {
        return "ExtensionPointDescriptor(name=" + this.name + ", isNameQualified=" + this.isNameQualified + ", className=" + this.className + ", isBean=" + this.isBean + ", isDynamic=" + this.isDynamic + ')';
    }
}
