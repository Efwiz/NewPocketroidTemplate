package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentConfig;
import org.jetbrains.kotlin.com.intellij.openapi.components.ServiceDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointDescriptor;
import org.jetbrains.kotlin.com.intellij.util.messages.ListenerDescriptor;

/* JADX INFO: compiled from: ContainerDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0005J\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004H\u0000¢\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0012H\u0016R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R&\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0004\u0018\u00010\u00118\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ContainerDescriptor;", "", "()V", "_services", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/components/ServiceDescriptor;", "components", "Lorg/jetbrains/kotlin/com/intellij/openapi/components/ComponentConfig;", "distinctExtensionPointCount", "", "getDistinctExtensionPointCount", "()I", "setDistinctExtensionPointCount", "(I)V", "extensionPoints", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointDescriptor;", "extensions", "", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor;", "listeners", "Lorg/jetbrains/kotlin/com/intellij/util/messages/ListenerDescriptor;", "services", "", "getServices", "()Ljava/util/List;", "addService", "", "serviceDescriptor", "getComponentListToAdd", "getComponentListToAdd$intellij_platform_core_impl", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class ContainerDescriptor {
    private List<ServiceDescriptor> _services;
    public List<ComponentConfig> components;
    private transient int distinctExtensionPointCount = -1;
    public List<ExtensionPointDescriptor> extensionPoints;
    public transient Map<String, ? extends List<ExtensionDescriptor>> extensions;
    public List<ListenerDescriptor> listeners;

    public final int getDistinctExtensionPointCount() {
        return this.distinctExtensionPointCount;
    }

    public final void setDistinctExtensionPointCount(int i) {
        this.distinctExtensionPointCount = i;
    }

    public final void addService(ServiceDescriptor serviceDescriptor) {
        Intrinsics.checkNotNullParameter(serviceDescriptor, "serviceDescriptor");
        if (this._services == null) {
            this._services = new ArrayList();
        }
        List<ServiceDescriptor> list = this._services;
        Intrinsics.checkNotNull(list);
        list.add(serviceDescriptor);
    }

    public final List<ComponentConfig> getComponentListToAdd$intellij_platform_core_impl() {
        List<ComponentConfig> list = this.components;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        this.components = arrayList;
        return arrayList;
    }

    public String toString() {
        if (this._services == null && this.components == null && this.extensionPoints == null && this.extensions == null && this.listeners == null) {
            return "ContainerDescriptor(empty)";
        }
        return "ContainerDescriptor(services=" + this._services + ", components=" + this.components + ", extensionPoints=" + this.extensionPoints + ", extensions=" + this.extensions + ", listeners=" + this.listeners + Constant.AFTER_QUTO;
    }
}
