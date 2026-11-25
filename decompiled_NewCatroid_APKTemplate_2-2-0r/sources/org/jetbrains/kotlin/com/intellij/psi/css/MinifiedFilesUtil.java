package org.jetbrains.kotlin.com.intellij.psi.css;

import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public final class MinifiedFilesUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 5:
            case 10:
            case 16:
                objArr[0] = "parserDefinition";
                break;
            case 2:
            case 6:
            case 11:
                objArr[0] = "noWSRequireBeforeTokenSet";
                break;
            case 3:
            case 7:
            case 12:
                objArr[0] = "noWSRequireAfterTokenSet";
                break;
            case 4:
            case 15:
            default:
                objArr[0] = "fileContent";
                break;
            case 8:
                objArr[0] = "stringsTokenSet";
                break;
            case 9:
            case 14:
                objArr[0] = "lexer";
                break;
            case 13:
                objArr[0] = "stringLiteralElements";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/css/MinifiedFilesUtil";
        if (i != 14) {
            objArr[2] = "isMinified";
        } else {
            objArr[2] = "isNewLine";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private MinifiedFilesUtil() {
    }

    public static boolean isMinified(CharSequence charSequence, ParserDefinition parserDefinition, TokenSet tokenSet, TokenSet tokenSet2) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(1);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(2);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(3);
        }
        return isMinified(charSequence, parserDefinition, tokenSet, tokenSet2, parserDefinition.getStringLiteralElements());
    }

    public static boolean isMinified(CharSequence charSequence, ParserDefinition parserDefinition, TokenSet tokenSet, TokenSet tokenSet2, TokenSet tokenSet3) {
        int length;
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(5);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(6);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(7);
        }
        if (tokenSet3 == null) {
            $$$reportNull$$$0(8);
        }
        Lexer lexerCreateLexer = parserDefinition.createLexer(null);
        lexerCreateLexer.start(charSequence);
        if (!isMinified(lexerCreateLexer, parserDefinition, tokenSet, tokenSet2, tokenSet3)) {
            return false;
        }
        if (lexerCreateLexer.getTokenType() == null || (length = charSequence.length() - 400) <= 0) {
            return true;
        }
        while (lexerCreateLexer.getTokenType() != null && lexerCreateLexer.getTokenStart() < length) {
            lexerCreateLexer.advance();
        }
        if (lexerCreateLexer.getTokenType() == null || charSequence.length() - lexerCreateLexer.getTokenStart() < 300) {
            return true;
        }
        return isMinified(lexerCreateLexer, parserDefinition, tokenSet, tokenSet2, tokenSet3);
    }

    private static boolean isMinified(Lexer lexer, ParserDefinition parserDefinition, TokenSet tokenSet, TokenSet tokenSet2, TokenSet tokenSet3) {
        String tokenText;
        if (lexer == null) {
            $$$reportNull$$$0(9);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(10);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(11);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(12);
        }
        if (tokenSet3 == null) {
            $$$reportNull$$$0(13);
        }
        TokenSet whitespaceTokens = parserDefinition.getWhitespaceTokens();
        TokenSet commentTokens = parserDefinition.getCommentTokens();
        IElementType tokenType = lexer.getTokenType();
        IElementType iElementType = null;
        String str = null;
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        while (tokenType != null) {
            if (commentTokens.contains(tokenType)) {
                tokenText = lexer.getTokenText();
            } else {
                int tokenEnd = lexer.getTokenEnd() - lexer.getTokenStart();
                if (!isNewLine(lexer, tokenEnd) || !commentTokens.contains(iElementType) || tokenSet2.contains(iElementType)) {
                    i += tokenEnd;
                    if (!tokenSet3.contains(tokenType)) {
                        i2 += tokenEnd;
                    }
                    if (whitespaceTokens.contains(tokenType)) {
                        if (tokenEnd > 1 && !commentTokens.contains(iElementType)) {
                            lexer.advance();
                            if (lexer.getTokenType() == null) {
                                break;
                            }
                            return false;
                        }
                        if (!(isNewLine(lexer, tokenEnd) && StringUtil.equals(str, "\n")) && (tokenEnd <= 0 || !tokenSet2.contains(iElementType))) {
                            z = false;
                        } else {
                            i3++;
                            z = true;
                        }
                    } else if (!z && whitespaceTokens.contains(iElementType) && StringUtil.isNotEmpty(str) && tokenSet.contains(tokenType)) {
                        i3++;
                    }
                    if (!tokenSet3.contains(tokenType) && i >= 2048) {
                        break;
                    }
                    tokenText = lexer.getTokenText();
                }
                lexer.advance();
                tokenType = lexer.getTokenType();
            }
            str = tokenText;
            iElementType = tokenType;
            lexer.advance();
            tokenType = lexer.getTokenType();
        }
        return i >= 150 && (((double) i3) + 0.0d) / ((double) i2) < 0.01d;
    }

    private static boolean isNewLine(Lexer lexer, int i) {
        if (lexer == null) {
            $$$reportNull$$$0(14);
        }
        return i == 1 && StringUtil.equals(lexer.getTokenText(), "\n");
    }

    public static boolean isMinified(CharSequence charSequence, ParserDefinition parserDefinition) {
        if (charSequence == null) {
            $$$reportNull$$$0(15);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(16);
        }
        return isMinified(charSequence, parserDefinition, TokenSet.EMPTY, TokenSet.EMPTY);
    }
}
