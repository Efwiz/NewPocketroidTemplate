package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface TokenType {
    public static final IElementType WHITE_SPACE = new WhiteSpaceTokenType();
    public static final IElementType BAD_CHARACTER = new IElementType("BAD_CHARACTER", Language.ANY);
    public static final IElementType NEW_LINE_INDENT = new IElementType("NEW_LINE_INDENT", Language.ANY);
    public static final IElementType ERROR_ELEMENT = new IElementType("ERROR_ELEMENT", Language.ANY) { // from class: org.jetbrains.kotlin.com.intellij.psi.TokenType.1
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
        public boolean isLeftBound() {
            return true;
        }
    };
    public static final IElementType CODE_FRAGMENT = new IFileElementType("CODE_FRAGMENT", Language.ANY);
    public static final IElementType DUMMY_HOLDER = new IFileElementType("DUMMY_HOLDER", Language.ANY);
}
