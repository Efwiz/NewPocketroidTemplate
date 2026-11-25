package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.serviceContainer.BaseKeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CustomLoadingExtensionPointBean<T> extends BaseKeyedLazyInstance<T> {

    @Attribute
    public String factoryArgument;

    @Attribute
    public String factoryClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "componentManager";
        } else if (i != 2) {
            objArr[0] = "instance";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/CustomLoadingExtensionPointBean";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/CustomLoadingExtensionPointBean";
        } else {
            objArr[1] = "createInstance";
        }
        if (i == 1) {
            objArr[2] = "createInstance";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    protected CustomLoadingExtensionPointBean() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected CustomLoadingExtensionPointBean(T t) {
        super(t);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
    }

    public final T createInstance(ComponentManager componentManager) {
        PluginAware pluginAware;
        if (componentManager == null) {
            $$$reportNull$$$0(1);
        }
        String str = this.factoryClass;
        if (str == null) {
            pluginAware = (T) super.createInstance(componentManager, getPluginDescriptor());
        } else {
            pluginAware = (T) ((ExtensionFactory) componentManager.instantiateClass(str, getPluginDescriptor())).createInstance(this.factoryArgument, getImplementationClassName());
        }
        if (pluginAware instanceof PluginAware) {
            pluginAware.setPluginDescriptor(getPluginDescriptor());
        }
        if (pluginAware == null) {
            $$$reportNull$$$0(2);
        }
        return (T) pluginAware;
    }
}
