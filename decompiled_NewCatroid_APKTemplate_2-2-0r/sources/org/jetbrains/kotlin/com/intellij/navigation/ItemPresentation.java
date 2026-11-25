package org.jetbrains.kotlin.com.intellij.navigation;

import javax.swing.Icon;

/* JADX INFO: loaded from: classes3.dex */
public interface ItemPresentation {
    Icon getIcon(boolean z);

    default String getLocationString() {
        return null;
    }
}
