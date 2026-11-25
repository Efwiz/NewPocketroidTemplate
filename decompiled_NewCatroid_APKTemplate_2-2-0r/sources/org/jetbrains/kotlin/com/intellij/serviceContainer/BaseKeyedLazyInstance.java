package org.jetbrains.kotlin.com.intellij.serviceContainer;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseKeyedLazyInstance<T> extends LazyExtensionInstance<T> implements PluginAware {
    private PluginDescriptor pluginDescriptor;

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/serviceContainer/BaseKeyedLazyInstance";
        } else if (i == 2) {
            objArr[0] = "value";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/serviceContainer/BaseKeyedLazyInstance";
        } else {
            objArr[0] = "instance";
        }
        if (i == 1) {
            objArr[1] = "getPluginDescriptor";
        } else if (i == 3) {
            objArr[1] = "getInstance";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/serviceContainer/BaseKeyedLazyInstance";
        } else {
            objArr[1] = "getLoaderForClass";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "setPluginDescriptor";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.serviceContainer.LazyExtensionInstance
    protected abstract String getImplementationClassName();

    protected BaseKeyedLazyInstance() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected BaseKeyedLazyInstance(T t) {
        super(t);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Transient
    public final PluginDescriptor getPluginDescriptor() {
        PluginDescriptor pluginDescriptor = this.pluginDescriptor;
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        return pluginDescriptor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware
    public final void setPluginDescriptor(PluginDescriptor pluginDescriptor) {
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        this.pluginDescriptor = pluginDescriptor;
    }

    public final T getInstance() {
        T baseKeyedLazyInstance = getInstance(ApplicationManager.getApplication(), this.pluginDescriptor);
        if (baseKeyedLazyInstance == null) {
            $$$reportNull$$$0(3);
        }
        return baseKeyedLazyInstance;
    }

    public ClassLoader getLoaderForClass() {
        ClassLoader classLoader;
        PluginDescriptor pluginDescriptor = this.pluginDescriptor;
        if (pluginDescriptor != null) {
            classLoader = pluginDescriptor.getClassLoader();
        } else {
            classLoader = getClass().getClassLoader();
        }
        if (classLoader == null) {
            $$$reportNull$$$0(4);
        }
        return classLoader;
    }
}
