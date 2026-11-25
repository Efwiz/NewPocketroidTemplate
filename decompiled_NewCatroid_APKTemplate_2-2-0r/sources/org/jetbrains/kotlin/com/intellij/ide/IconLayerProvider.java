package org.jetbrains.kotlin.com.intellij.ide;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;

/* JADX INFO: loaded from: classes3.dex */
public interface IconLayerProvider {
    public static final ExtensionPointName<IconLayerProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.iconLayerProvider");

    String getLayerDescription();

    Icon getLayerIcon(Iconable iconable, boolean z);
}
