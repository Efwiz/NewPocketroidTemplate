package org.jetbrains.kotlin.js.common;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: IdentifierPolicy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0002\u001a\f\u0010\n\u001a\u00020\b*\u00020\tH\u0002\u001a\n\u0010\u000b\u001a\u00020\b*\u00020\t\u001a\n\u0010\f\u001a\u00020\b*\u00020\t\u001a\f\u0010\r\u001a\u00020\b*\u00020\tH\u0002\u001a\f\u0010\u000e\u001a\u00020\b*\u00020\tH\u0002\u001a\n\u0010\u000f\u001a\u00020\b*\u00020\u0002\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0010"}, d2 = {"RESERVED_KEYWORDS", "", "", "getRESERVED_KEYWORDS", "()Ljava/util/Set;", "SPECIAL_KEYWORDS", "getSPECIAL_KEYWORDS", "isAllowedLatinLetterOrSpecial", "", "", "isAllowedSimpleDigit", "isES5IdentifierPart", "isES5IdentifierStart", "isES5IdentifierStartFull", "isNotAllowedSimpleCharacter", "isValidES5Identifier", "js.ast"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IdentifierPolicyKt {
    private static final Set<String> RESERVED_KEYWORDS;
    private static final Set<String> SPECIAL_KEYWORDS;

    private static final boolean isAllowedLatinLetterOrSpecial(char c) {
        return ('a' <= c && c < '{') || ('A' <= c && c < '[') || c == '_' || c == '$';
    }

    private static final boolean isAllowedSimpleDigit(char c) {
        return '0' <= c && c < ':';
    }

    private static final boolean isNotAllowedSimpleCharacter(char c) {
        return c == ' ' || c == '<' || c == '>' || c == '-' || c == '?';
    }

    public static final boolean isES5IdentifierStart(char c) {
        if (isAllowedLatinLetterOrSpecial(c)) {
            return true;
        }
        if (isNotAllowedSimpleCharacter(c)) {
            return false;
        }
        return isES5IdentifierStartFull(c);
    }

    private static final boolean isES5IdentifierStartFull(char c) {
        return Character.isLetter(c) || ((byte) Character.getType(c)) == 10;
    }

    public static final boolean isES5IdentifierPart(char c) {
        if (isAllowedLatinLetterOrSpecial(c) || isAllowedSimpleDigit(c)) {
            return true;
        }
        if (isNotAllowedSimpleCharacter(c)) {
            return false;
        }
        if (isES5IdentifierStartFull(c)) {
            return true;
        }
        byte type = (byte) Character.getType(c);
        return (type == 6 || type == 8 || type == 9 || type == 23) || c == 8204 || c == 8205;
    }

    public static final boolean isValidES5Identifier(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (str.length() == 0 || !isES5IdentifierStart(str.charAt(0))) {
            return false;
        }
        int length = str.length();
        for (int i = 1; i < length; i++) {
            if (!isES5IdentifierPart(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static {
        Set<String> of = SetsKt.setOf("default");
        SPECIAL_KEYWORDS = of;
        RESERVED_KEYWORDS = SetsKt.plus((Set) of, (Iterable) SetsKt.setOf((Object[]) new String[]{"await", PsiKeyword.BREAK, PsiKeyword.CASE, PsiKeyword.CATCH, PsiKeyword.CONTINUE, "debugger", "delete", PsiKeyword.DO, PsiKeyword.ELSE, PsiKeyword.FINALLY, PsiKeyword.FOR, "function", PsiKeyword.IF, "in", PsiKeyword.INSTANCEOF, PsiKeyword.NEW, "return", PsiKeyword.SWITCH, "this", PsiKeyword.THROW, PsiKeyword.TRY, "typeof", PsiKeyword.VAR, PsiKeyword.VOID, PsiKeyword.WHILE, "with", "class", PsiKeyword.CONST, PsiKeyword.ENUM, "export", PsiKeyword.EXTENDS, PsiKeyword.IMPORT, PsiKeyword.SUPER, PsiKeyword.IMPLEMENTS, PsiKeyword.INTERFACE, "let", "package", "private", "protected", "public", "static", PsiKeyword.YIELD, "null", "true", "false", "eval", "arguments"}));
    }

    public static final Set<String> getSPECIAL_KEYWORDS() {
        return SPECIAL_KEYWORDS;
    }

    public static final Set<String> getRESERVED_KEYWORDS() {
        return RESERVED_KEYWORDS;
    }
}
