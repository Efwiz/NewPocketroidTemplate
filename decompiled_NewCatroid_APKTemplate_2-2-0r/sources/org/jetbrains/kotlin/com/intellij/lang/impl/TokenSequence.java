package org.jetbrains.kotlin.com.intellij.lang.impl;

import androidx.core.view.InputDeviceCompat;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.lexer.TokenList;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TokenSequence implements TokenList {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) TokenSequence.class);
    final int[] lexStarts;
    final IElementType[] lexTypes;
    final int lexemeCount;
    private final CharSequence myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "lexer";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/TokenSequence";
        } else {
            objArr[0] = "text";
        }
        if (i == 4) {
            objArr[1] = "performLexing";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/TokenSequence";
        } else {
            objArr[1] = "getTokenizedText";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "performLexing";
        } else if (i != 4 && i != 5) {
            objArr[2] = "assertMatches";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    TokenSequence(int[] iArr, IElementType[] iElementTypeArr, int i, CharSequence charSequence) {
        this.lexStarts = iArr;
        this.lexTypes = iElementTypeArr;
        this.lexemeCount = i;
        this.myText = charSequence;
    }

    void assertMatches(CharSequence charSequence, Lexer lexer) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (lexer == null) {
            $$$reportNull$$$0(1);
        }
        TokenSequence tokenSequencePerformLexing = new Builder(charSequence, lexer).performLexing();
        for (int i = 0; i <= this.lexemeCount; i++) {
            if (tokenSequencePerformLexing.lexStarts[i] == this.lexStarts[i]) {
                IElementType iElementType = tokenSequencePerformLexing.lexTypes[i];
                IElementType iElementType2 = this.lexTypes[i];
            }
        }
    }

    public static TokenSequence performLexing(CharSequence charSequence, Lexer lexer) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        if (lexer == null) {
            $$$reportNull$$$0(3);
        }
        if (lexer instanceof TokenList.WrappingLexer) {
            TokenList tokens = ((TokenList.WrappingLexer) lexer).getTokens();
            if (tokens instanceof TokenSequence) {
                TokenSequence tokenSequence = (TokenSequence) tokens;
                if (Comparing.equal(charSequence, tokenSequence.myText)) {
                    return new TokenSequence(tokenSequence.lexStarts, (IElementType[]) tokenSequence.lexTypes.clone(), tokenSequence.lexemeCount, charSequence);
                }
            }
        }
        TokenSequence tokenSequencePerformLexing = new Builder(charSequence, lexer).performLexing();
        if (tokenSequencePerformLexing == null) {
            $$$reportNull$$$0(4);
        }
        return tokenSequencePerformLexing;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.TokenList
    public int getTokenCount() {
        return this.lexemeCount;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.TokenList
    public IElementType getTokenType(int i) {
        if (i < 0 || i >= getTokenCount()) {
            return null;
        }
        return this.lexTypes[i];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.TokenList
    public int getTokenStart(int i) {
        return this.lexStarts[i];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.TokenList
    public int getTokenEnd(int i) {
        return this.lexStarts[i + 1];
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.TokenList
    public CharSequence getTokenizedText() {
        CharSequence charSequence = this.myText;
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return charSequence;
    }

    private static class Builder {
        private int[] myLexStarts;
        private IElementType[] myLexTypes;
        private final Lexer myLexer;
        private final CharSequence myText;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "text";
            } else {
                objArr[0] = "lexer";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/TokenSequence$Builder";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        Builder(CharSequence charSequence, Lexer lexer) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            if (lexer == null) {
                $$$reportNull$$$0(1);
            }
            this.myText = charSequence;
            this.myLexer = lexer;
            int iMax = Math.max(10, charSequence.length() / 5);
            this.myLexStarts = new int[iMax];
            this.myLexTypes = new IElementType[iMax];
        }

        TokenSequence performLexing() {
            this.myLexer.start(this.myText);
            int i = 0;
            int i2 = 0;
            while (true) {
                IElementType tokenType = this.myLexer.getTokenType();
                if (tokenType != null) {
                    if (i % 20 == 0) {
                        ProgressIndicatorProvider.checkCanceled();
                    }
                    if (i >= this.myLexTypes.length - 1) {
                        resizeLexemes((i * 3) / 2);
                    }
                    int tokenStart = this.myLexer.getTokenStart();
                    if (tokenStart < i2) {
                        reportDescendingOffsets(i, i2, tokenStart);
                    }
                    this.myLexStarts[i] = tokenStart;
                    this.myLexTypes[i] = tokenType;
                    i++;
                    this.myLexer.advance();
                    i2 = tokenStart;
                } else {
                    this.myLexStarts[i] = this.myText.length();
                    return new TokenSequence(this.myLexStarts, this.myLexTypes, i, this.myText);
                }
            }
        }

        private void reportDescendingOffsets(int i, int i2, int i3) {
            StringBuilder sb = new StringBuilder();
            IElementType tokenType = this.myLexer.getTokenType();
            sb.append("Token sequence broken\n  this: '").append(this.myLexer.getTokenText()).append("' (").append(tokenType).append(':').append(tokenType != null ? tokenType.getLanguage() : null).append(") ").append(i3).append(":").append(this.myLexer.getTokenEnd());
            if (i > 0) {
                int i4 = i - 1;
                int i5 = this.myLexStarts[i4];
                sb.append("\n  prev: '").append(this.myText.subSequence(i5, i2)).append("' (").append(this.myLexTypes[i4]).append(':').append(this.myLexTypes[i4].getLanguage()).append(") ").append(i5).append(":").append(i2);
            }
            int iMax = Math.max(i3 + InputDeviceCompat.SOURCE_ANY, 0);
            int iMin = Math.min(i3 + 256, this.myText.length());
            sb.append("\n  quote: [").append(iMax).append(':').append(iMin).append("] '").append(this.myText.subSequence(iMax, iMin)).append('\'');
            TokenSequence.LOG.error(sb);
        }

        private void resizeLexemes(int i) {
            this.myLexStarts = ArrayUtil.realloc(this.myLexStarts, i);
            this.myLexTypes = (IElementType[]) ArrayUtil.realloc(this.myLexTypes, i, IElementType.ARRAY_FACTORY);
        }
    }
}
