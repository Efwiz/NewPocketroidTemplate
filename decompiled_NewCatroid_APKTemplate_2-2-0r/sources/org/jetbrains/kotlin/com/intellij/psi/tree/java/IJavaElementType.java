package org.jetbrains.kotlin.com.intellij.psi.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class IJavaElementType extends IElementType {
    private final boolean myLeftBound;

    public IJavaElementType(String str) {
        this(str, false);
    }

    public IJavaElementType(String str, boolean z) {
        super(str, JavaLanguage.INSTANCE);
        this.myLeftBound = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
    public boolean isLeftBound() {
        return this.myLeftBound;
    }
}
