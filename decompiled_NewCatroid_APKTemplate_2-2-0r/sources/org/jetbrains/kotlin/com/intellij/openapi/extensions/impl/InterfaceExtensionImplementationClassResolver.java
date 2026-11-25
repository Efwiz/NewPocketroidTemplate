package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.InternalIgnoreDependencyViolation;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;

/* JADX INFO: loaded from: classes3.dex */
final class InterfaceExtensionImplementationClassResolver implements ImplementationClassResolver {
    static final ImplementationClassResolver INSTANCE = new InterfaceExtensionImplementationClassResolver();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "adapter";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/InterfaceExtensionImplementationClassResolver";
        } else {
            objArr[0] = "componentManager";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "resolveImplementationClass";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/InterfaceExtensionImplementationClassResolver";
        }
        if (i != 2 && i != 3) {
            objArr[2] = "resolveImplementationClass";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private InterfaceExtensionImplementationClassResolver() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ImplementationClassResolver
    public Class<?> resolveImplementationClass(ComponentManager componentManager, ExtensionComponentAdapter extensionComponentAdapter) throws ClassNotFoundException {
        if (componentManager == null) {
            $$$reportNull$$$0(0);
        }
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(1);
        }
        Object obj = extensionComponentAdapter.implementationClassOrName;
        if (!(obj instanceof String)) {
            Class<?> cls = (Class) obj;
            if (cls == null) {
                $$$reportNull$$$0(2);
            }
            return cls;
        }
        PluginDescriptor pluginDescriptor = extensionComponentAdapter.getPluginDescriptor();
        String str = (String) obj;
        Class<?> clsLoadClass = componentManager.loadClass(str, pluginDescriptor);
        if (clsLoadClass.getClassLoader() != pluginDescriptor.get_pluginClassLoader() && pluginDescriptor.get_pluginClassLoader() != null && !str.startsWith("org.jetbrains.kotlin.com.intellij.webcore.resourceRoots.") && !str.startsWith("org.jetbrains.kotlin.com.intellij.tasks.impl.") && !clsLoadClass.isAnnotationPresent(InternalIgnoreDependencyViolation.class)) {
            String idString = pluginDescriptor.getId().getIdString();
            if (!idString.equals("org.jetbrains.kotlin.com.intellij.java") && !idString.equals("org.jetbrains.kotlin.com.intellij.java.ide") && !idString.equals("org.jetbrains.android") && !idString.equals("org.jetbrains.kotlin.com.intellij.kotlinNative.platformDeps") && !idString.equals("com.jetbrains.rider.android")) {
                ExtensionPointImpl.LOG.error((Throwable) componentManager.createError("Created extension classloader is not equal to plugin's one.\nSee https://youtrack.jetbrains.com/articles/IDEA-A-65/Plugin-Model#internalignoredependencyviolation\n(\n  className=" + str + ",\n  extensionInstanceClassloader=" + clsLoadClass.getClassLoader() + ",\n  pluginClassloader=" + pluginDescriptor.get_pluginClassLoader() + "\n)", pluginDescriptor.getId()));
            }
        }
        extensionComponentAdapter.implementationClassOrName = clsLoadClass;
        if (clsLoadClass == null) {
            $$$reportNull$$$0(3);
        }
        return clsLoadClass;
    }
}
