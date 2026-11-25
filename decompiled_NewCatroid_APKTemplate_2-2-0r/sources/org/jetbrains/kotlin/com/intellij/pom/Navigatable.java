package org.jetbrains.kotlin.com.intellij.pom;

/* JADX INFO: loaded from: classes3.dex */
public interface Navigatable {
    public static final Navigatable[] EMPTY_NAVIGATABLE_ARRAY = new Navigatable[0];

    boolean canNavigate();

    boolean canNavigateToSource();

    void navigate(boolean z);
}
