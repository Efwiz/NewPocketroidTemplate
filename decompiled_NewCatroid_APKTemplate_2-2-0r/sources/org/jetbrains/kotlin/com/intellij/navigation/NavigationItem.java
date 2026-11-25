package org.jetbrains.kotlin.com.intellij.navigation;

import org.jetbrains.kotlin.com.intellij.pom.Navigatable;

/* JADX INFO: loaded from: classes3.dex */
public interface NavigationItem extends Navigatable {
    public static final NavigationItem[] EMPTY_NAVIGATION_ITEM_ARRAY = new NavigationItem[0];

    String getName();

    ItemPresentation getPresentation();
}
