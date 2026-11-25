package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFileEx extends PsiFile {
    public static final Key<Boolean> BATCH_REFERENCE_PROCESSING = Key.create("BATCH_REFERENCE_PROCESSING");

    boolean isContentsLoaded();

    void markInvalidated();

    void onContentReload();
}
