package org.jetbrains.kotlin.com.intellij.ui;

import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: IconManager.java */
/* JADX INFO: loaded from: classes3.dex */
final class IconManagerHelper {
    static volatile IconManager instance;
    private static final AtomicBoolean isActivated = new AtomicBoolean();
}
