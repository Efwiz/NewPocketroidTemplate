package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public interface StubElement<T extends PsiElement> extends Stub {
    <P extends PsiElement, S extends StubElement<P>> S findChildStubByType(IStubElementType<S, P> iStubElementType);

    <E extends PsiElement> E[] getChildrenByType(IElementType iElementType, ArrayFactory<E> arrayFactory);

    <E extends PsiElement> E[] getChildrenByType(IElementType iElementType, E[] eArr);

    <E extends PsiElement> E[] getChildrenByType(TokenSet tokenSet, ArrayFactory<E> arrayFactory);

    <E extends PsiElement> E[] getChildrenByType(TokenSet tokenSet, E[] eArr);

    List<StubElement> getChildrenStubs();

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.Stub
    StubElement getParentStub();

    <E extends PsiElement> E getParentStubOfType(Class<E> cls);

    T getPsi();

    IStubElementType getStubType();
}
