package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ITokenTypeRemapper {
    IElementType filter(IElementType iElementType, int i, int i2, CharSequence charSequence);
}
