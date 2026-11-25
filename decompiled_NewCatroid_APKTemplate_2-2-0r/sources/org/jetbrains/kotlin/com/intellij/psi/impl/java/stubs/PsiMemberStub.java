package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMemberStub<T extends PsiMember & PsiNamedElement> extends NamedStub<T> {
    default boolean hasDeprecatedAnnotation() {
        return true;
    }

    default boolean hasDocComment() {
        return true;
    }

    boolean isDeprecated();
}
