package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiBuilderUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "builder";
                break;
            case 2:
            case 7:
                objArr[0] = "leftBrace";
                break;
            case 3:
            case 8:
                objArr[0] = "rightBrace";
                break;
            case 4:
                objArr[0] = "codeBlock";
                break;
            case 5:
                objArr[0] = "text";
                break;
            case 6:
                objArr[0] = "lexer";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/PsiBuilderUtil";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/PsiBuilderUtil";
                break;
            default:
                objArr[1] = "rawTokenText";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[2] = "parseBlockLazy";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "hasProperBraceBalance";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static void advance(PsiBuilder psiBuilder, int i) {
        for (int i2 = 0; i2 < i && !psiBuilder.eof(); i2++) {
            psiBuilder.getTokenType();
            psiBuilder.advanceLexer();
        }
    }

    public static boolean expect(PsiBuilder psiBuilder, IElementType iElementType) {
        if (psiBuilder.getTokenType() != iElementType) {
            return false;
        }
        psiBuilder.advanceLexer();
        return true;
    }

    public static boolean expect(PsiBuilder psiBuilder, TokenSet tokenSet) {
        if (!tokenSet.contains(psiBuilder.getTokenType())) {
            return false;
        }
        psiBuilder.advanceLexer();
        return true;
    }

    public static void drop(PsiBuilder.Marker... markerArr) {
        for (PsiBuilder.Marker marker : markerArr) {
            if (marker != null) {
                marker.drop();
            }
        }
    }

    public static void rollbackTo(PsiBuilder.Marker marker) {
        if (marker != null) {
            marker.rollbackTo();
        }
    }

    public static PsiBuilder.Marker parseBlockLazy(PsiBuilder psiBuilder, IElementType iElementType, IElementType iElementType2, IElementType iElementType3) {
        int i = 1;
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (iElementType2 == null) {
            $$$reportNull$$$0(3);
        }
        if (iElementType3 == null) {
            $$$reportNull$$$0(4);
        }
        if (psiBuilder.getTokenType() != iElementType) {
            return null;
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        while (i > 0 && !psiBuilder.eof()) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == iElementType) {
                i++;
            } else if (tokenType == iElementType2) {
                i--;
            }
            psiBuilder.advanceLexer();
        }
        markerM6463mark.collapse(iElementType3);
        if (i > 0) {
            markerM6463mark.setCustomEdgeTokenBinders(null, WhitespacesBinders.GREEDY_RIGHT_BINDER);
        }
        return markerM6463mark;
    }

    public static boolean hasProperBraceBalance(CharSequence charSequence, Lexer lexer, IElementType iElementType, IElementType iElementType2) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        if (lexer == null) {
            $$$reportNull$$$0(6);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(7);
        }
        if (iElementType2 == null) {
            $$$reportNull$$$0(8);
        }
        lexer.start(charSequence);
        if (lexer.getTokenType() != iElementType) {
            return false;
        }
        lexer.advance();
        int i = 1;
        while (true) {
            ProgressManager.checkCanceled();
            IElementType tokenType = lexer.getTokenType();
            if (tokenType == null) {
                return i == 0;
            }
            if (i == 0) {
                return false;
            }
            if (tokenType == iElementType) {
                i++;
            } else if (tokenType == iElementType2) {
                i--;
            }
            lexer.advance();
        }
    }
}
