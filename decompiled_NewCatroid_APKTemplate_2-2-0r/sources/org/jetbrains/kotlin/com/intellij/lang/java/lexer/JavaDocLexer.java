package org.jetbrains.kotlin.com.intellij.lang.java.lexer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes;
import org.jetbrains.kotlin.com.intellij.lexer.JavaDocTokenTypes;
import org.jetbrains.kotlin.com.intellij.lexer.LexerBase;
import org.jetbrains.kotlin.com.intellij.lexer.MergingLexerAdapter;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JavaDocLexer extends MergingLexerAdapter {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", FirebaseAnalytics.Param.LEVEL, "org/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaDocLexer", "<init>"));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaDocLexer(LanguageLevel languageLevel) {
        this(JavaDocTokenTypes.INSTANCE, languageLevel.isAtLeast(LanguageLevel.JDK_1_5));
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
    }

    private JavaDocLexer(DocCommentTokenTypes docCommentTokenTypes, boolean z) {
        super(new AsteriskStripperLexer(new _JavaDocLexer(z, docCommentTokenTypes), docCommentTokenTypes), docCommentTokenTypes.spaceCommentsTokenSet());
    }

    private static class AsteriskStripperLexer extends LexerBase {
        private boolean myAfterLineBreak;
        private CharSequence myBuffer;
        private int myBufferEndOffset;
        private int myBufferIndex;
        private final _JavaDocLexer myFlex;
        private boolean myInLeadingSpace;
        private int myState;
        private int myTokenEndOffset;
        private IElementType myTokenType;
        private final DocCommentTokenTypes myTokenTypes;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "buffer";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaDocLexer$AsteriskStripperLexer";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/lexer/JavaDocLexer$AsteriskStripperLexer";
            } else {
                objArr[1] = "getBufferSequence";
            }
            if (i != 1) {
                objArr[2] = "start";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        AsteriskStripperLexer(_JavaDocLexer _javadoclexer, DocCommentTokenTypes docCommentTokenTypes) {
            this.myFlex = _javadoclexer;
            this.myTokenTypes = docCommentTokenTypes;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public final void start(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.myBuffer = charSequence;
            this.myBufferIndex = i;
            this.myBufferEndOffset = i2;
            this.myTokenType = null;
            this.myTokenEndOffset = i;
            this.myFlex.reset(charSequence, i, i2, i3);
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getState() {
            return this.myState;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public CharSequence getBufferSequence() {
            CharSequence charSequence = this.myBuffer;
            if (charSequence == null) {
                $$$reportNull$$$0(1);
            }
            return charSequence;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public int getBufferEnd() {
            return this.myBufferEndOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public final IElementType getTokenType() {
            locateToken();
            return this.myTokenType;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public final int getTokenStart() {
            locateToken();
            return this.myBufferIndex;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public final int getTokenEnd() {
            locateToken();
            return this.myTokenEndOffset;
        }

        @Override // org.jetbrains.kotlin.com.intellij.lexer.Lexer
        public final void advance() {
            locateToken();
            this.myTokenType = null;
        }

        protected final void locateToken() {
            if (this.myTokenType != null) {
                return;
            }
            _locateToken();
            if (this.myTokenType == this.myTokenTypes.space()) {
                this.myAfterLineBreak = CharArrayUtil.containLineBreaks(this.myBuffer, getTokenStart(), getTokenEnd());
            }
        }

        private void _locateToken() {
            int i;
            int i2 = this.myTokenEndOffset;
            int i3 = this.myBufferEndOffset;
            if (i2 == i3) {
                this.myTokenType = null;
                this.myBufferIndex = i3;
                return;
            }
            this.myBufferIndex = i2;
            boolean z = false;
            if (this.myAfterLineBreak) {
                this.myAfterLineBreak = false;
                while (true) {
                    int i4 = this.myTokenEndOffset;
                    if (i4 < this.myBufferEndOffset && this.myBuffer.charAt(i4) == '*') {
                        int i5 = this.myTokenEndOffset;
                        if (i5 + 1 < this.myBufferEndOffset && this.myBuffer.charAt(i5 + 1) == '/') {
                            break;
                        } else {
                            this.myTokenEndOffset++;
                        }
                    } else {
                        break;
                    }
                }
                this.myInLeadingSpace = true;
                if (this.myBufferIndex < this.myTokenEndOffset) {
                    this.myTokenType = this.myTokenTypes.commentLeadingAsterisks();
                    return;
                }
            }
            if (this.myInLeadingSpace) {
                this.myInLeadingSpace = false;
                while (true) {
                    int i6 = this.myTokenEndOffset;
                    if (i6 >= this.myBufferEndOffset || !Character.isWhitespace(this.myBuffer.charAt(i6))) {
                        break;
                    }
                    if (this.myBuffer.charAt(this.myTokenEndOffset) == '\n') {
                        z = true;
                    }
                    this.myTokenEndOffset++;
                }
                int iYystate = this.myFlex.yystate();
                if (iYystate == 4 || ((i = this.myTokenEndOffset) < this.myBufferEndOffset && (this.myBuffer.charAt(i) == '@' || this.myBuffer.charAt(this.myTokenEndOffset) == '{' || this.myBuffer.charAt(this.myTokenEndOffset) == '\"' || this.myBuffer.charAt(this.myTokenEndOffset) == '<'))) {
                    this.myFlex.yybegin(2);
                }
                if (this.myBufferIndex < this.myTokenEndOffset) {
                    this.myTokenType = (z || iYystate == 8 || iYystate == 6 || iYystate == 16 || iYystate == 12) ? this.myTokenTypes.space() : this.myTokenTypes.commentData();
                    return;
                }
            }
            flexLocateToken();
        }

        private void flexLocateToken() {
            try {
                this.myState = this.myFlex.yystate();
                this.myFlex.goTo(this.myBufferIndex);
                this.myTokenType = this.myFlex.advance();
                this.myTokenEndOffset = this.myFlex.getTokenEnd();
            } catch (IOException unused) {
            }
        }
    }
}
