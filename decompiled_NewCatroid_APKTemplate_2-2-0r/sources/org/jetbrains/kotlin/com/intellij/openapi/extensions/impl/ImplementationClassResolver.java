package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
interface ImplementationClassResolver {
    Class<?> resolveImplementationClass(ComponentManager componentManager, ExtensionComponentAdapter extensionComponentAdapter) throws ClassNotFoundException;
}
