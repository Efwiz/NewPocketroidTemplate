package org.jetbrains.kotlin.com.intellij.psi.compiled;

import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClsStubBuilder {
    public abstract PsiFileStub<?> buildFileStub(FileContent fileContent) throws ClsFormatException;

    public abstract int getStubVersion();
}
