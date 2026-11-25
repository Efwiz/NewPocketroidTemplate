package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes4.dex */
public interface TokenStreamPattern {
    boolean handleUnmatchedClosing(IElementType iElementType);

    boolean isTopLevel(int i, int i2, int i3, int i4);

    boolean processToken(int i, boolean z);

    int result();
}
