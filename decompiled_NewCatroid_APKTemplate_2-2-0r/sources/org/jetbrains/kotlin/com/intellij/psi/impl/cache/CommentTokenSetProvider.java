package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface CommentTokenSetProvider {
    public static final LanguageExtension<CommentTokenSetProvider> EXTENSION = new LanguageExtension<>("org.jetbrains.kotlin.com.intellij.commentTokenSetProvider");

    boolean isInComments(IElementType iElementType);
}
