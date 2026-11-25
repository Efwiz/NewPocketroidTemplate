package org.jetbrains.kotlin.com.intellij.lang.jvm.facade;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmClass;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface JvmElementProvider {
    public static final ExtensionPointName<JvmElementProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.jvm.elementProvider");

    List<? extends JvmClass> getClasses(String str, GlobalSearchScope globalSearchScope);
}
