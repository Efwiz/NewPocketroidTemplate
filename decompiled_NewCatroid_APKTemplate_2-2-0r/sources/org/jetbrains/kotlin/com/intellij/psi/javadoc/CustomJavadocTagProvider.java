package org.jetbrains.kotlin.com.intellij.psi.javadoc;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface CustomJavadocTagProvider {
    public static final ExtensionPointName<CustomJavadocTagProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.customJavadocTagProvider");

    List<JavadocTagInfo> getSupportedTags();
}
