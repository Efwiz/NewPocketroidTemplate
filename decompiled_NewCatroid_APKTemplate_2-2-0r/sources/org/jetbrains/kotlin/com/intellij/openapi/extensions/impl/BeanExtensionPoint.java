package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.reandroid.archive.PathTree;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public final class BeanExtensionPoint<T> extends ExtensionPointImpl<T> implements ImplementationClassResolver {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "className";
                break;
            case 2:
            case 8:
            case 11:
                objArr[0] = "pluginDescriptor";
                break;
            case 3:
            case 4:
            case 9:
            case 10:
                objArr[0] = "componentManager";
                break;
            case 5:
                objArr[0] = "adapter";
                break;
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/BeanExtensionPoint";
                break;
            case 7:
                objArr[0] = "descriptor";
                break;
            case 12:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 13:
                objArr[0] = "priorityListenerCallbacks";
                break;
            case 14:
                objArr[0] = "listenerCallbacks";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/BeanExtensionPoint";
        } else {
            objArr[1] = "resolveImplementationClass";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "resolveImplementationClass";
                break;
            case 6:
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createAdapter";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[2] = "unregisterExtensions";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BeanExtensionPoint(String str, String str2, PluginDescriptor pluginDescriptor, ComponentManager componentManager, boolean z) {
        super(str, str2, pluginDescriptor, componentManager, null, z);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ImplementationClassResolver
    public Class<?> resolveImplementationClass(ComponentManager componentManager, ExtensionComponentAdapter extensionComponentAdapter) throws ClassNotFoundException {
        if (componentManager == null) {
            $$$reportNull$$$0(4);
        }
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(5);
        }
        Class<T> extensionClass = getExtensionClass();
        if (extensionClass == null) {
            $$$reportNull$$$0(6);
        }
        return extensionClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl
    ExtensionComponentAdapter createAdapter(ExtensionDescriptor extensionDescriptor, PluginDescriptor pluginDescriptor, ComponentManager componentManager) {
        if (extensionDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(9);
        }
        if (componentManager.isInjectionForExtensionSupported()) {
            return new XmlExtensionAdapter.SimpleConstructorInjectionAdapter(getClassName(), pluginDescriptor, extensionDescriptor.orderId, extensionDescriptor.order, extensionDescriptor.element, this);
        }
        return new XmlExtensionAdapter(getClassName(), pluginDescriptor, extensionDescriptor.orderId, extensionDescriptor.order, extensionDescriptor.element, this);
    }
}
