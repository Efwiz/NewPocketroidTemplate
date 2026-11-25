package org.jetbrains.kotlin.com.intellij.openapi.roots.impl;

import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class PackageDirectoryCache$$ExternalSyntheticLambda0 implements Condition {
    public final /* synthetic */ GlobalSearchScope f$0;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
    public final boolean value(Object obj) {
        return this.f$0.contains((VirtualFile) obj);
    }
}
