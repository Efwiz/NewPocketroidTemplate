package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.StubFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFileStub<T extends PsiFile> extends UserDataHolder, StubElement<T> {
    public static final PsiFileStub[] EMPTY_ARRAY = new PsiFileStub[0];

    String getInvalidationReason();

    StubFileElementType getType();
}
