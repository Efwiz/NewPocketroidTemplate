package org.jetbrains.kotlin.com.intellij.openapi.components;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.diagnostic.ActivityCategory;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.AreaInstance;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public interface ComponentManager extends Disposable, AreaInstance, UserDataHolder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 6 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 6 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "interfaceClass";
                break;
            case 2:
                objArr[0] = "extensionPointName";
                break;
            case 3:
            case 6:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/components/ComponentManager";
                break;
            case 4:
            case 5:
            case 7:
                objArr[0] = "serviceClass";
                break;
            case 8:
                objArr[0] = "aClass";
                break;
            case 9:
            case 11:
                objArr[0] = "pluginId";
                break;
            case 10:
                objArr[0] = "error";
                break;
            case 12:
                objArr[0] = "className";
                break;
            case 13:
                objArr[0] = "pluginDescriptor";
                break;
            case 15:
                objArr[0] = "os";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 3) {
            objArr[1] = "getExtensions";
        } else if (i == 6) {
            objArr[1] = "getServices";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/components/ComponentManager";
        } else {
            objArr[1] = "instantiateClass";
        }
        switch (i) {
            case 1:
                objArr[2] = "hasComponent";
                break;
            case 2:
                objArr[2] = "getExtensions";
                break;
            case 3:
            case 6:
            case 14:
                break;
            case 4:
                objArr[2] = "getService";
                break;
            case 5:
                objArr[2] = "getServices";
                break;
            case 7:
                objArr[2] = "getServiceIfCreated";
                break;
            case 8:
            case 9:
            case 12:
            case 13:
                objArr[2] = "instantiateClass";
                break;
            case 10:
            case 11:
                objArr[2] = "logError";
                break;
            case 15:
                objArr[2] = "isSuitableForOs";
                break;
            default:
                objArr[2] = "getComponent";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 6 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    RuntimeException createError(String str, Throwable th, PluginId pluginId, Map<String, String> map);

    RuntimeException createError(String str, PluginId pluginId);

    RuntimeException createError(Throwable th, PluginId pluginId);

    ActivityCategory getActivityCategory(boolean z);

    <T> T getComponent(Class<T> cls);

    Condition<?> getDisposed();

    MessageBus getMessageBus();

    <T> T getService(Class<T> cls);

    <T> T instantiateClassWithConstructorInjection(Class<T> cls, Object obj, PluginId pluginId);

    boolean isDisposed();

    boolean isInjectionForExtensionSupported();

    default boolean isSuitableForOs(ExtensionDescriptor.Os os) {
        if (os != null) {
            return true;
        }
        $$$reportNull$$$0(15);
        return true;
    }

    <T> Class<T> loadClass(String str, PluginDescriptor pluginDescriptor) throws ClassNotFoundException;

    default <T> T getServiceIfCreated(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(7);
        }
        return (T) getService(cls);
    }

    default ExtensionsArea getExtensionArea() {
        throw new AbstractMethodError();
    }

    default <T> T instantiateClass(Class<T> cls, PluginId pluginId) {
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(9);
        }
        return (T) ReflectionUtil.newInstance(cls, false);
    }

    default void logError(Throwable th, PluginId pluginId) {
        if (th == null) {
            $$$reportNull$$$0(10);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(11);
        }
        throw createError(th, pluginId);
    }

    default <T> T instantiateClass(String str, PluginDescriptor pluginDescriptor) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        try {
            T t = (T) ReflectionUtil.newInstance(loadClass(str, pluginDescriptor));
            if (t == null) {
                $$$reportNull$$$0(14);
            }
            return t;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
