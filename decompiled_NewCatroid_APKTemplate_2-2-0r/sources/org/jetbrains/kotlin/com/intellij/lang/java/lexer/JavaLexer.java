package org.jetbrains.kotlin.com.intellij.lang.java.lexer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lexer.LexerBase;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaLexer extends LexerBase {
    private CharSequence myBuffer;
    private char[] myBufferArray;
    private int myBufferEndOffset;
    private int myBufferIndex;
    private final _JavaLexer myFlexLexer;
    private int myTokenEndOffset;
    private IElementType myTokenType;
    private static final Set<String> KEYWORDS = new HashSet(Arrays.asList("abstract", "boolean", PsiKeyword.BREAK, "byte", PsiKeyword.CASE, PsiKeyword.CATCH, PsiKeyword.CHAR, "class", PsiKeyword.CONST, PsiKeyword.CONTINUE, "default", PsiKeyword.DO, "double", PsiKeyword.ELSE, PsiKeyword.EXTENDS, "final", PsiKeyword.FINALLY, "float", PsiKeyword.FOR, PsiKeyword.GOTO, PsiKeyword.IF, PsiKeyword.IMPLEMENTS, PsiKeyword.IMPORT, PsiKeyword.INSTANCEOF, "int", PsiKeyword.INTERFACE, "long", "native", PsiKeyword.NEW, "package", "private", "protected", "public", "return", "short", "static", "strictfp", PsiKeyword.SUPER, PsiKeyword.SWITCH, "synchronized", "this", PsiKeyword.THROW, PsiKeyword.THROWS, "transient", PsiKeyword.TRY, PsiKeyword.VOID, "volatile", PsiKeyword.WHILE, "true", "false", "null", "non-sealed"));
    private static final Set<CharSequence> JAVA9_KEYWORDS = CollectionFactory.createCharSequenceSet(Arrays.asList("open", "module", PsiKeyword.REQUIRES, PsiKeyword.EXPORTS, PsiKeyword.OPENS, PsiKeyword.USES, PsiKeyword.PROVIDES, "transitive", "to", "with"));

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 4) {
            objArr[0] = "buffer";
        } else if (i != 5) {
            objArr[0] = FirebaseAnalytics.Param.LEVEL;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaLexer";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaLexer";
        } else {
            objArr[1] = "getBufferSequence";
        }
        if (i == 1) {
            objArr[2] = "isSoftKeyword";
        } else if (i == 2) {
            objArr[2] = "isSealedAvailable";
        } else if (i == 3) {
            objArr[2] = "<init>";
        } else if (i == 4) {
            objArr[2] = "start";
        } else if (i != 5) {
            objArr[2] = "isKeyword";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getState() {
        return 0;
    }

    public static boolean isKeyword(String str, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
        return KEYWORDS.contains(str) || (languageLevel.isAtLeast(LanguageLevel.JDK_1_4) && PsiKeyword.ASSERT.equals(str)) || (languageLevel.isAtLeast(LanguageLevel.JDK_1_5) && PsiKeyword.ENUM.equals(str));
    }

    public static boolean isSoftKeyword(CharSequence charSequence, LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence != null) {
            if (languageLevel.isAtLeast(LanguageLevel.JDK_1_9) && JAVA9_KEYWORDS.contains(charSequence)) {
                return true;
            }
            if (languageLevel.isAtLeast(LanguageLevel.JDK_10) && PsiKeyword.VAR.contentEquals(charSequence)) {
                return true;
            }
            if (languageLevel.isAtLeast(LanguageLevel.JDK_16) && PsiKeyword.RECORD.contentEquals(charSequence)) {
                return true;
            }
            if (languageLevel.isAtLeast(LanguageLevel.JDK_14) && PsiKeyword.YIELD.contentEquals(charSequence)) {
                return true;
            }
            if (isSealedAvailable(languageLevel) && ("sealed".contentEquals(charSequence) || PsiKeyword.PERMITS.contentEquals(charSequence))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSealedAvailable(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(2);
        }
        return languageLevel.isAtLeast(LanguageLevel.JDK_16_PREVIEW);
    }

    public JavaLexer(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(3);
        }
        this.myFlexLexer = new _JavaLexer(languageLevel);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void start(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        this.myBuffer = charSequence;
        this.myBufferArray = CharArrayUtil.fromSequenceWithoutCopying(charSequence);
        this.myBufferIndex = i;
        this.myBufferEndOffset = i2;
        this.myTokenType = null;
        this.myTokenEndOffset = i;
        this.myFlexLexer.reset(this.myBuffer, i, i2, 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public IElementType getTokenType() {
        locateToken();
        return this.myTokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenStart() {
        return this.myBufferIndex;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getTokenEnd() {
        locateToken();
        return this.myTokenEndOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public void advance() {
        locateToken();
        this.myTokenType = null;
    }

    /* JADX WARN: Code duplicated, block: B:62:0x00fd  */
    private void locateToken() {
        int i;
        if (this.myTokenType != null) {
            return;
        }
        int i2 = this.myTokenEndOffset;
        int i3 = this.myBufferEndOffset;
        if (i2 == i3) {
            this.myBufferIndex = i3;
            return;
        }
        this.myBufferIndex = i2;
        char cCharAt = charAt(i2);
        if (cCharAt == '\t' || cCharAt == '\n' || cCharAt == '\f' || cCharAt == '\r' || cCharAt == ' ') {
            this.myTokenType = TokenType.WHITE_SPACE;
            this.myTokenEndOffset = getWhitespaces(this.myBufferIndex + 1);
        } else if (cCharAt == '\'') {
            this.myTokenType = JavaTokenType.CHARACTER_LITERAL;
            this.myTokenEndOffset = getClosingQuote(this.myBufferIndex + 1, cCharAt);
        } else if (cCharAt == '/') {
            int i4 = this.myBufferIndex;
            if (i4 + 1 >= this.myBufferEndOffset) {
                this.myTokenType = JavaTokenType.DIV;
                this.myTokenEndOffset = this.myBufferEndOffset;
            } else {
                char cCharAt2 = charAt(i4 + 1);
                if (cCharAt2 == '/') {
                    this.myTokenType = JavaTokenType.END_OF_LINE_COMMENT;
                    this.myTokenEndOffset = getLineTerminator(this.myBufferIndex + 2);
                } else if (cCharAt2 == '*') {
                    int i5 = this.myBufferIndex;
                    if (i5 + 2 < this.myBufferEndOffset && charAt(i5 + 2) == '*') {
                        int i6 = this.myBufferIndex;
                        if (i6 + 3 < this.myBufferEndOffset && charAt(i6 + 3) == '/') {
                            this.myTokenType = JavaTokenType.C_STYLE_COMMENT;
                            this.myTokenEndOffset = getClosingComment(this.myBufferIndex + 2);
                        } else {
                            this.myTokenType = JavaDocElementType.DOC_COMMENT;
                            this.myTokenEndOffset = getClosingComment(this.myBufferIndex + 3);
                        }
                    } else {
                        this.myTokenType = JavaTokenType.C_STYLE_COMMENT;
                        this.myTokenEndOffset = getClosingComment(this.myBufferIndex + 2);
                    }
                } else {
                    flexLocateToken();
                }
            }
        } else if (cCharAt == '\"') {
            int i7 = this.myBufferIndex;
            if (i7 + 2 < this.myBufferEndOffset && charAt(i7 + 2) == '\"' && charAt(this.myBufferIndex + 1) == '\"') {
                this.myTokenType = JavaTokenType.TEXT_BLOCK_LITERAL;
                this.myTokenEndOffset = getTextBlockEnd(this.myBufferIndex + 2);
            } else {
                this.myTokenType = JavaTokenType.STRING_LITERAL;
                this.myTokenEndOffset = getClosingQuote(this.myBufferIndex + 1, cCharAt);
            }
        } else if (cCharAt == '#' && (i = this.myBufferIndex) == 0 && i + 1 < this.myBufferEndOffset && charAt(i + 1) == '!') {
            this.myTokenType = JavaTokenType.END_OF_LINE_COMMENT;
            this.myTokenEndOffset = getLineTerminator(this.myBufferIndex + 2);
        } else {
            flexLocateToken();
        }
        int i8 = this.myTokenEndOffset;
        int i9 = this.myBufferEndOffset;
        if (i8 > i9) {
            this.myTokenEndOffset = i9;
        }
    }

    private int getWhitespaces(int i) {
        int i2 = this.myBufferEndOffset;
        if (i >= i2) {
            return i2;
        }
        char cCharAt = charAt(i);
        while (true) {
            if (cCharAt != ' ' && cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\f') {
                return i;
            }
            i++;
            if (i == this.myBufferEndOffset) {
                return i;
            }
            cCharAt = charAt(i);
        }
    }

    private void flexLocateToken() {
        try {
            this.myFlexLexer.goTo(this.myBufferIndex);
            this.myTokenType = this.myFlexLexer.advance();
            this.myTokenEndOffset = this.myFlexLexer.getTokenEnd();
        } catch (IOException unused) {
        }
    }

    private int getClosingQuote(int i, char c) {
        int i2;
        int i3 = this.myBufferEndOffset;
        if (i >= i3) {
            return i3;
        }
        char cCharAt = charAt(i);
        while (true) {
            if (cCharAt != c && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\\') {
                i++;
                int i4 = this.myBufferEndOffset;
                if (i >= i4) {
                    return i4;
                }
                cCharAt = charAt(i);
            } else {
                if (cCharAt != '\\') {
                    if (cCharAt != c) {
                        i--;
                    }
                    return i + 1;
                }
                int i5 = i + 1;
                int i6 = this.myBufferEndOffset;
                if (i5 >= i6) {
                    return i6;
                }
                char cCharAt2 = charAt(i5);
                if (cCharAt2 == '\n' || cCharAt2 == '\r') {
                    i = i5;
                    cCharAt = cCharAt2;
                } else {
                    if (cCharAt2 == 'u') {
                        while (true) {
                            i2 = i5 + 1;
                            if (i2 >= this.myBufferEndOffset || charAt(i2) != 'u') {
                                break;
                            }
                            i5 = i2;
                        }
                        int i7 = i5 + 4;
                        int i8 = this.myBufferEndOffset;
                        if (i7 >= i8) {
                            return i8;
                        }
                        i = i2 + ((charAt(i2) == '0' && charAt(i5 + 2) == '0' && charAt(i5 + 3) == '5' && charAt(i7) == 'c') ? 5 : 4);
                    } else {
                        i += 2;
                    }
                    int i9 = this.myBufferEndOffset;
                    if (i >= i9) {
                        return i9;
                    }
                    cCharAt = charAt(i);
                }
            }
        }
    }

    private int getClosingComment(int i) {
        while (i < this.myBufferEndOffset - 1 && (charAt(i) != '*' || charAt(i + 1) != '/')) {
            i++;
        }
        return i + 2;
    }

    private int getLineTerminator(int i) {
        char cCharAt;
        while (i < this.myBufferEndOffset && (cCharAt = charAt(i)) != '\r' && cCharAt != '\n') {
            i++;
        }
        return i;
    }

    private int getTextBlockEnd(int i) {
        int i2;
        while (true) {
            i = getClosingQuote(i + 1, '\"');
            if (i >= this.myBufferEndOffset) {
                return i;
            }
            char cCharAt = charAt(i);
            if (cCharAt == '\\') {
                i++;
            } else if (cCharAt == '\"' && (i2 = i + 1) < this.myBufferEndOffset && charAt(i2) == '\"') {
                return i + 2;
            }
        }
    }

    private char charAt(int i) {
        char[] cArr = this.myBufferArray;
        return cArr != null ? cArr[i] : this.myBuffer.charAt(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public CharSequence getBufferSequence() {
        CharSequence charSequence = this.myBuffer;
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
    public int getBufferEnd() {
        return this.myBufferEndOffset;
    }
}
