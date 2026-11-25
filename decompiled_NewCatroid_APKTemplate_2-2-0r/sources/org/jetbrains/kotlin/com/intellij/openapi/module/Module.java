package org.jetbrains.kotlin.com.intellij.openapi.module;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.AreaInstance;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface Module extends Disposable, ComponentManager, AreaInstance {
    public static final Module[] EMPTY_ARRAY = new Module[0];

    GlobalSearchScope getModuleRuntimeScope(boolean z);

    GlobalSearchScope getModuleScope();

    GlobalSearchScope getModuleTestsWithDependentsScope();

    GlobalSearchScope getModuleWithDependenciesAndLibrariesScope(boolean z);

    GlobalSearchScope getModuleWithDependenciesScope();

    GlobalSearchScope getModuleWithDependentsScope();

    GlobalSearchScope getModuleWithLibrariesScope();

    String getName();
}
