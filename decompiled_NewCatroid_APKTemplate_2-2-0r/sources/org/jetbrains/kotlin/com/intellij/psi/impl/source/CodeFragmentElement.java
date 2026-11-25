package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;

/* JADX INFO: loaded from: classes3.dex */
public class CodeFragmentElement extends FileElement {
    public CodeFragmentElement(CharSequence charSequence) {
        super(TokenType.CODE_FRAGMENT, charSequence);
    }
}
