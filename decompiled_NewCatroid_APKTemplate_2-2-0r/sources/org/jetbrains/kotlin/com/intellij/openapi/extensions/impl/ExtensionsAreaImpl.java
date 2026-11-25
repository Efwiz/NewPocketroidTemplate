package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.reandroid.archive.PathTree;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.DefaultPluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public final class ExtensionsAreaImpl implements ExtensionsArea {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ExtensionsAreaImpl.class);
    private final ComponentManager componentManager;
    private final Map<String, Throwable> epTraces;
    public volatile Map<String, ExtensionPointImpl<?>> extensionPoints;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 25 || i == 38 || i == 41) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 25 || i == 38 || i == 41) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 9:
            case 11:
            case 21:
            case 24:
            case 27:
            case 29:
            case 31:
            case 36:
                objArr[0] = "pluginDescriptor";
                break;
            case 2:
                objArr[0] = "extensionPointElements";
                break;
            case 3:
            case 16:
            case 37:
            case 39:
            case 40:
            case 44:
            case 45:
            case 46:
                objArr[0] = "extensionPointName";
                break;
            case 5:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 6:
                objArr[0] = "priorityListenerCallbacks";
                break;
            case 7:
                objArr[0] = "listenerCallbacks";
                break;
            case 8:
            case 10:
                objArr[0] = "descriptors";
                break;
            case 12:
                objArr[0] = "extensionPoint";
                break;
            case 13:
            case 17:
                objArr[0] = "extensionPointBeanClass";
                break;
            case 14:
            case 18:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 15:
                objArr[0] = "parentDisposable";
                break;
            case 19:
            case 22:
            case 26:
                objArr[0] = "name";
                break;
            case 20:
            case 23:
                objArr[0] = "extensionClass";
                break;
            case 25:
            case 38:
            case 41:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionsAreaImpl";
                break;
            case 28:
                objArr[0] = "pointName";
                break;
            case 30:
            case 33:
                objArr[0] = "points";
                break;
            case 32:
                objArr[0] = "value";
                break;
            case 34:
            default:
                objArr[0] = "componentManager";
                break;
            case 35:
                objArr[0] = "result";
                break;
            case 42:
                objArr[0] = "consumer";
                break;
            case 43:
                objArr[0] = "aClass";
                break;
        }
        if (i == 25) {
            objArr[1] = "doRegisterExtensionPoint";
        } else if (i == 38 || i == 41) {
            objArr[1] = "getExtensionPoint";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionsAreaImpl";
        }
        switch (i) {
            case 1:
            case 2:
            case 30:
            case 31:
                objArr[2] = "registerExtensionPoints";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "unregisterExtensions";
                break;
            case 8:
            case 9:
                objArr[2] = "resetExtensionPoints";
                break;
            case 10:
            case 11:
                objArr[2] = "unregisterExtensionPoints";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "registerExtensionPoint";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "registerPoint";
                break;
            case 22:
            case 23:
            case 24:
                objArr[2] = "doRegisterExtensionPoint";
                break;
            case 25:
            case 38:
            case 41:
                break;
            case 26:
            case 27:
                objArr[2] = "registerFakeBeanPoint";
                break;
            case 28:
            case 29:
                objArr[2] = "checkThatPointNotDuplicated";
                break;
            case 32:
                objArr[2] = "setPoints";
                break;
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "createExtensionPoints";
                break;
            case 37:
            case 40:
                objArr[2] = "getExtensionPoint";
                break;
            case 39:
                objArr[2] = "getExtensionPointIfRegistered";
                break;
            case 42:
                objArr[2] = "processExtensionPoints";
                break;
            case 43:
                objArr[2] = "findExtensionByClass";
                break;
            case 44:
                objArr[2] = "unregisterExtensionPoint";
                break;
            case 45:
            case 46:
                objArr[2] = "hasExtensionPoint";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 25 && i != 38 && i != 41) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ExtensionsAreaImpl(ComponentManager componentManager) {
        if (componentManager == null) {
            $$$reportNull$$$0(0);
        }
        this.extensionPoints = Collections.emptyMap();
        this.epTraces = null;
        this.componentManager = componentManager;
    }

    public void notifyAreaReplaced(ExtensionsAreaImpl extensionsAreaImpl) {
        HashSet hashSet = new HashSet(this.extensionPoints.size());
        for (ExtensionPointImpl<?> extensionPointImpl : this.extensionPoints.values()) {
            extensionPointImpl.notifyAreaReplaced(this);
            hashSet.add(extensionPointImpl.getName());
        }
        if (extensionsAreaImpl == null) {
            return;
        }
        for (ExtensionPointImpl<?> extensionPointImpl2 : extensionsAreaImpl.extensionPoints.values()) {
            if (!hashSet.contains(extensionPointImpl2.getName())) {
                extensionPointImpl2.notifyAreaReplaced(this);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public void registerExtensionPoint(String str, String str2, ExtensionPoint.Kind kind, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (str2 == null) {
            $$$reportNull$$$0(17);
        }
        if (kind == null) {
            $$$reportNull$$$0(18);
        }
        doRegisterExtensionPoint(str, str2, new DefaultPluginDescriptor(PluginId.getId("fakeIdForTests")), kind == ExtensionPoint.Kind.INTERFACE, z);
    }

    private <T> ExtensionPointImpl<T> doRegisterExtensionPoint(String str, String str2, PluginDescriptor pluginDescriptor, boolean z, boolean z2) {
        ExtensionPointImpl<T> beanExtensionPoint;
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (str2 == null) {
            $$$reportNull$$$0(23);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (z) {
            beanExtensionPoint = new InterfaceExtensionPoint<>(str, str2, pluginDescriptor, this.componentManager, null, z2);
        } else {
            beanExtensionPoint = new BeanExtensionPoint<>(str, str2, pluginDescriptor, this.componentManager, z2);
        }
        checkThatPointNotDuplicated(str, beanExtensionPoint.getPluginDescriptor());
        HashMap map = new HashMap(this.extensionPoints.size() + 1);
        map.putAll(this.extensionPoints);
        map.put(str, beanExtensionPoint);
        this.extensionPoints = Collections.unmodifiableMap(map);
        return beanExtensionPoint;
    }

    private void checkThatPointNotDuplicated(String str, PluginDescriptor pluginDescriptor) {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        if (hasExtensionPoint(str)) {
            throw this.componentManager.createError("Duplicate registration for EP '" + str + "': first in " + getExtensionPoint(str).getPluginDescriptor().getPluginId() + ", second in " + pluginDescriptor.getPluginId(), pluginDescriptor.getPluginId());
        }
    }

    public void registerExtensionPoints(List<? extends ExtensionPointDescriptor> list, PluginDescriptor pluginDescriptor) {
        if (list == null) {
            $$$reportNull$$$0(30);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        HashMap map = new HashMap(this.extensionPoints);
        createExtensionPoints(list, this.componentManager, map, pluginDescriptor);
        this.extensionPoints = map;
    }

    public static void createExtensionPoints(List<? extends ExtensionPointDescriptor> list, ComponentManager componentManager, Map<? super String, ExtensionPointImpl<?>> map, PluginDescriptor pluginDescriptor) {
        ExtensionPointImpl<?> interfaceExtensionPoint;
        if (list == null) {
            $$$reportNull$$$0(33);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(34);
        }
        if (map == null) {
            $$$reportNull$$$0(35);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(36);
        }
        for (ExtensionPointDescriptor extensionPointDescriptor : list) {
            String qualifiedName = extensionPointDescriptor.getQualifiedName(pluginDescriptor);
            if (extensionPointDescriptor.isBean) {
                interfaceExtensionPoint = new BeanExtensionPoint<>(qualifiedName, extensionPointDescriptor.className, pluginDescriptor, componentManager, extensionPointDescriptor.isDynamic);
            } else {
                interfaceExtensionPoint = new InterfaceExtensionPoint(qualifiedName, extensionPointDescriptor.className, pluginDescriptor, componentManager, null, extensionPointDescriptor.isDynamic);
            }
            ExtensionPointImpl<?> extensionPointImplPutIfAbsent = map.putIfAbsent(qualifiedName, interfaceExtensionPoint);
            if (extensionPointImplPutIfAbsent != null) {
                throw componentManager.createError("Duplicate registration for EP " + qualifiedName + ": first in " + extensionPointImplPutIfAbsent.getPluginDescriptor() + ", second in " + pluginDescriptor, pluginDescriptor.getPluginId());
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public <T> ExtensionPointImpl<T> getExtensionPoint(String str) {
        if (str == null) {
            $$$reportNull$$$0(37);
        }
        ExtensionPointImpl<T> extensionPointIfRegistered = getExtensionPointIfRegistered(str);
        if (extensionPointIfRegistered == null) {
            throw new IllegalArgumentException("Missing extension point: " + str + " in container " + this.componentManager);
        }
        if (extensionPointIfRegistered == null) {
            $$$reportNull$$$0(38);
        }
        return extensionPointIfRegistered;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public <T> ExtensionPointImpl<T> getExtensionPointIfRegistered(String str) {
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        return (ExtensionPointImpl) this.extensionPoints.get(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public <T> ExtensionPoint<T> getExtensionPoint(ExtensionPointName<T> extensionPointName) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(40);
        }
        ExtensionPointImpl<T> extensionPoint = getExtensionPoint(extensionPointName.getName());
        if (extensionPoint == null) {
            $$$reportNull$$$0(41);
        }
        return extensionPoint;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public boolean hasExtensionPoint(String str) {
        if (str == null) {
            $$$reportNull$$$0(45);
        }
        return this.extensionPoints.containsKey(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea
    public boolean hasExtensionPoint(ExtensionPointName<?> extensionPointName) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(46);
        }
        return hasExtensionPoint(extensionPointName.getName());
    }

    public String toString() {
        return this.componentManager.toString();
    }
}
