package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFileSystemItemProcessor extends PsiElementProcessor<PsiFileSystemItem> {
    boolean acceptItem(String str, boolean z);
}
