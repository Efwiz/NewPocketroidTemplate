package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExtensionComponentAdapter implements LoadingOrder.Orderable {
    public static final ExtensionComponentAdapter[] EMPTY_ARRAY = new ExtensionComponentAdapter[0];
    Object implementationClassOrName;
    protected final ImplementationClassResolver implementationClassResolver;
    private final LoadingOrder order;
    private final String orderId;
    protected final PluginDescriptor pluginDescriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "pluginDescriptor";
                break;
            case 2:
                objArr[0] = "order";
                break;
            case 3:
                objArr[0] = "implementationClassResolver";
                break;
            case 4:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionComponentAdapter";
                break;
            case 5:
                objArr[0] = "componentManager";
                break;
            default:
                objArr[0] = "implementationClassName";
                break;
        }
        if (i == 4) {
            objArr[1] = "getPluginDescriptor";
        } else if (i == 6) {
            objArr[1] = "getImplementationClass";
        } else if (i == 7 || i == 8) {
            objArr[1] = "getAssignableToClassName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionComponentAdapter";
        }
        switch (i) {
            case 4:
            case 6:
            case 7:
            case 8:
                break;
            case 5:
                objArr[2] = "getImplementationClass";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract <T> T createInstance(ComponentManager componentManager);

    abstract boolean isInstanceCreated();

    ExtensionComponentAdapter(String str, PluginDescriptor pluginDescriptor, String str2, LoadingOrder loadingOrder, ImplementationClassResolver implementationClassResolver) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (loadingOrder == null) {
            $$$reportNull$$$0(2);
        }
        if (implementationClassResolver == null) {
            $$$reportNull$$$0(3);
        }
        this.implementationClassOrName = str;
        this.pluginDescriptor = pluginDescriptor;
        this.orderId = str2;
        this.order = loadingOrder;
        this.implementationClassResolver = implementationClassResolver;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder.Orderable
    public final LoadingOrder getOrder() {
        return this.order;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder.Orderable
    public final String getOrderId() {
        return this.orderId;
    }

    public final PluginDescriptor getPluginDescriptor() {
        PluginDescriptor pluginDescriptor = this.pluginDescriptor;
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        return pluginDescriptor;
    }

    public final <T> Class<T> getImplementationClass(ComponentManager componentManager) throws ClassNotFoundException {
        if (componentManager == null) {
            $$$reportNull$$$0(5);
        }
        Class<T> cls = (Class<T>) this.implementationClassResolver.resolveImplementationClass(componentManager, this);
        if (cls == null) {
            $$$reportNull$$$0(6);
        }
        return cls;
    }

    public final String getAssignableToClassName() {
        Object obj = this.implementationClassOrName;
        if (obj instanceof String) {
            String str = (String) obj;
            if (str == null) {
                $$$reportNull$$$0(7);
            }
            return str;
        }
        String name2 = ((Class) obj).getName();
        if (name2 == null) {
            $$$reportNull$$$0(8);
        }
        return name2;
    }

    public final String toString() {
        return getClass().getSimpleName() + "(implementation=" + getAssignableToClassName() + ", plugin=" + this.pluginDescriptor + Constant.AFTER_QUTO;
    }
}
