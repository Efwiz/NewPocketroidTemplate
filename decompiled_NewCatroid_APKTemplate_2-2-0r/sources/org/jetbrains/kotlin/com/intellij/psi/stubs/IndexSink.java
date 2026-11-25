package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface IndexSink {
    <Psi extends PsiElement, K> void occurrence(StubIndexKey<K, Psi> stubIndexKey, K k);
}
