package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.reandroid.archive.PathTree;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public final class InterfaceExtensionPoint<T> extends ExtensionPointImpl<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "className";
                break;
            case 2:
            case 5:
            case 8:
                objArr[0] = "pluginDescriptor";
                break;
            case 3:
            case 6:
            case 7:
                objArr[0] = "componentManager";
                break;
            case 4:
                objArr[0] = "descriptor";
                break;
            case 9:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 10:
                objArr[0] = "priorityListenerCallbacks";
                break;
            case 11:
                objArr[0] = "listenerCallbacks";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/InterfaceExtensionPoint";
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[2] = "createAdapter";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "unregisterExtensions";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InterfaceExtensionPoint(String str, String str2, PluginDescriptor pluginDescriptor, ComponentManager componentManager, Class<T> cls, boolean z) {
        super(str, str2, pluginDescriptor, componentManager, cls, z);
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

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl
    ExtensionComponentAdapter createAdapter(ExtensionDescriptor extensionDescriptor, PluginDescriptor pluginDescriptor, ComponentManager componentManager) {
        if (extensionDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(6);
        }
        extensionDescriptor.element = null;
        String str = extensionDescriptor.implementation;
        if (str == null) {
            throw componentManager.createError("Attribute \"implementation\" is not specified for \"" + getName() + "\" extension", pluginDescriptor.getId());
        }
        return new XmlExtensionAdapter.SimpleConstructorInjectionAdapter(str, pluginDescriptor, extensionDescriptor.orderId, extensionDescriptor.order, null, InterfaceExtensionImplementationClassResolver.INSTANCE);
    }
}
