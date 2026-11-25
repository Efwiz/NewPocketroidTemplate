package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface StringEscapesTokenTypes {
    public static final IElementType INVALID_CHARACTER_ESCAPE_TOKEN;
    public static final IElementType INVALID_UNICODE_ESCAPE_TOKEN;
    public static final TokenSet STRING_LITERAL_ESCAPES;
    public static final IElementType VALID_STRING_ESCAPE_TOKEN;

    static {
        IElementType iElementType = new IElementType("VALID_STRING_ESCAPE_TOKEN", Language.ANY);
        VALID_STRING_ESCAPE_TOKEN = iElementType;
        IElementType iElementType2 = new IElementType("INVALID_CHARACTER_ESCAPE_TOKEN", Language.ANY);
        INVALID_CHARACTER_ESCAPE_TOKEN = iElementType2;
        IElementType iElementType3 = new IElementType("INVALID_UNICODE_ESCAPE_TOKEN", Language.ANY);
        INVALID_UNICODE_ESCAPE_TOKEN = iElementType3;
        STRING_LITERAL_ESCAPES = TokenSet.create(iElementType, iElementType2, iElementType3);
    }
}
