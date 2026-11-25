package org.jetbrains.kotlin.com.intellij.model;

import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;

/* JADX INFO: loaded from: classes3.dex */
abstract class BranchedVirtualFile extends LightVirtualFile {
    protected abstract ModelBranch getBranch();
}
