package org.jetbrains.kotlin.com.intellij.navigation;

import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;

/* JADX INFO: loaded from: classes3.dex */
public interface ItemPresentationProvider<T extends NavigationItem> {
    ItemPresentation getPresentation(T t);
}
