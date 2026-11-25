package org.jetbrains.kotlin.com.intellij.serviceContainer;

import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LazyExtensionInstance<T> {
    private volatile T instance;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "componentManager";
                break;
            case 2:
            case 7:
                objArr[0] = "pluginDescriptor";
                break;
            case 3:
            case 4:
            case 5:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/serviceContainer/LazyExtensionInstance";
                break;
            default:
                objArr[0] = "instance";
                break;
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "getInstance";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/serviceContainer/LazyExtensionInstance";
        } else {
            objArr[1] = "createInstance";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "getInstance";
                break;
            case 3:
            case 4:
            case 5:
            case 8:
                break;
            case 6:
            case 7:
                objArr[2] = "createInstance";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract String getImplementationClassName();

    protected LazyExtensionInstance() {
    }

    protected LazyExtensionInstance(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.instance = t;
    }

    public final T getInstance(ComponentManager componentManager, PluginDescriptor pluginDescriptor) {
        if (componentManager == null) {
            $$$reportNull$$$0(1);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        T t = this.instance;
        if (t != null) {
            if (t == null) {
                $$$reportNull$$$0(3);
            }
            return t;
        }
        synchronized (this) {
            T t2 = this.instance;
            if (t2 != null) {
                if (t2 == null) {
                    $$$reportNull$$$0(4);
                }
                return t2;
            }
            T tCreateInstance = createInstance(componentManager, pluginDescriptor);
            this.instance = tCreateInstance;
            if (tCreateInstance == null) {
                $$$reportNull$$$0(5);
            }
            return tCreateInstance;
        }
    }

    public T createInstance(ComponentManager componentManager, PluginDescriptor pluginDescriptor) {
        if (componentManager == null) {
            $$$reportNull$$$0(6);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        String implementationClassName = getImplementationClassName();
        if (implementationClassName == null) {
            throw new PluginException("implementation class is not specified", pluginDescriptor.getPluginId());
        }
        T t = (T) componentManager.instantiateClass(implementationClassName, pluginDescriptor);
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        return t;
    }
}
