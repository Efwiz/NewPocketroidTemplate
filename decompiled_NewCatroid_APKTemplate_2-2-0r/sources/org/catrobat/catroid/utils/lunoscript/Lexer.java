package org.catrobat.catroid.utils.lunoscript;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Lexer.class
  classes14.dex
 */
/* JADX INFO: compiled from: Lexer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0015H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\b\u0010\u001d\u001a\u00020\u001aH\u0002J\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0015H\u0002J\b\u0010!\u001a\u00020\u0011H\u0002J\b\u0010\"\u001a\u00020\u0015H\u0002J\b\u0010#\u001a\u00020\u0015H\u0002J\b\u0010$\u001a\u00020\u0011H\u0002J\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000f0&J\u0010\u0010'\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0015H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Lexer;", "", FirebaseAnalytics.Param.SOURCE, "", "(Ljava/lang/String;)V", "current", "", "keywords", "", "Lorg/catrobat/catroid/utils/lunoscript/TokenType;", Constant.LINE, "lineStart", "start", "tokens", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "addToken", "", ModuleXmlParser.TYPE, "literal", "advance", "", "f_string", "quoteType", "identifier", "isAlpha", "", "c", "isAlphaNumeric", "isAtEnd", "isDigit", "match", "expected", "number", "peek", "peekNext", "scanToken", "scanTokens", "", "string", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Lexer {
    private int current;
    private final Map<String, TokenType> keywords;
    private int line;
    private int lineStart;
    private final String source;
    private int start;
    private final List<Token> tokens;

    private final boolean isAlpha(char c) {
        return ('a' <= c && c < '{') || ('A' <= c && c < '[') || c == '_';
    }

    private final boolean isDigit(char c) {
        return '0' <= c && c < ':';
    }

    public Lexer(String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
        this.tokens = new ArrayList();
        this.line = 1;
        this.keywords = MapsKt.mapOf(TuplesKt.to(PsiKeyword.VAR, TokenType.VAR), TuplesKt.to(PsiKeyword.IF, TokenType.IF), TuplesKt.to(PsiKeyword.ELSE, TokenType.ELSE), TuplesKt.to("true", TokenType.TRUE), TuplesKt.to("false", TokenType.FALSE), TuplesKt.to("null", TokenType.NULL), TuplesKt.to("fun", TokenType.FUN), TuplesKt.to("return", TokenType.RETURN), TuplesKt.to("class", TokenType.CLASS), TuplesKt.to("this", TokenType.THIS), TuplesKt.to(PsiKeyword.SUPER, TokenType.SUPER), TuplesKt.to(PsiKeyword.WHILE, TokenType.WHILE), TuplesKt.to(PsiKeyword.FOR, TokenType.FOR), TuplesKt.to("in", TokenType.IN), TuplesKt.to(PsiKeyword.BREAK, TokenType.BREAK), TuplesKt.to(PsiKeyword.CONTINUE, TokenType.CONTINUE), TuplesKt.to(PsiKeyword.SWITCH, TokenType.SWITCH), TuplesKt.to(PsiKeyword.CASE, TokenType.CASE), TuplesKt.to("default", TokenType.DEFAULT), TuplesKt.to(PsiKeyword.IMPORT, TokenType.IMPORT), TuplesKt.to("val", TokenType.VAR), TuplesKt.to(PsiKeyword.TRY, TokenType.TRY), TuplesKt.to(PsiKeyword.CATCH, TokenType.CATCH), TuplesKt.to(PsiKeyword.FINALLY, TokenType.FINALLY), TuplesKt.to("static", TokenType.STATIC));
    }

    public final List<Token> scanTokens() {
        while (!isAtEnd()) {
            this.start = this.current;
            scanToken();
        }
        this.tokens.add(new Token(TokenType.EOF, "", null, this.line, this.current - this.lineStart));
        return this.tokens;
    }

    private final boolean isAtEnd() {
        return this.current >= this.source.length();
    }

    private final char advance() {
        String str = this.source;
        int i = this.current;
        this.current = i + 1;
        return str.charAt(i);
    }

    static /* synthetic */ void addToken$default(Lexer lexer, TokenType tokenType, Object obj, int i, Object obj2) {
        if ((i & 2) != 0) {
            obj = null;
        }
        lexer.addToken(tokenType, obj);
    }

    private final void addToken(TokenType type, Object literal) {
        String strSubstring = this.source.substring(this.start, this.current);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        this.tokens.add(new Token(type, strSubstring, literal, this.line, this.start - this.lineStart));
    }

    private final boolean match(char expected) {
        if (isAtEnd() || this.source.charAt(this.current) != expected) {
            return false;
        }
        this.current++;
        return true;
    }

    private final char peek() {
        if (isAtEnd()) {
            return (char) 0;
        }
        return this.source.charAt(this.current);
    }

    private final char peekNext() {
        if (this.current + 1 >= this.source.length()) {
            return (char) 0;
        }
        return this.source.charAt(this.current + 1);
    }

    private final void string(char quoteType) {
        while (peek() != quoteType && !isAtEnd()) {
            if (peek() == '\n') {
                this.line++;
                this.lineStart = this.current + 1;
            }
            advance();
        }
        if (isAtEnd()) {
            throw new LunoSyntaxError("Unterminated string.", this.line, this.start - this.lineStart);
        }
        advance();
        String strSubstring = this.source.substring(this.start + 1, this.current - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        addToken(TokenType.STRING_LITERAL, strSubstring);
    }

    private final void number() {
        while (isDigit(peek())) {
            advance();
        }
        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }
        if (CharsKt.equals(peek(), 'f', true)) {
            advance();
            TokenType tokenType = TokenType.FLOAT_LITERAL;
            String strSubstring = this.source.substring(this.start, this.current - 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            addToken(tokenType, Float.valueOf(Float.parseFloat(strSubstring)));
            return;
        }
        TokenType tokenType2 = TokenType.NUMBER_LITERAL;
        String strSubstring2 = this.source.substring(this.start, this.current);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        addToken(tokenType2, Double.valueOf(Double.parseDouble(strSubstring2)));
    }

    private final void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }
        String strSubstring = this.source.substring(this.start, this.current);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (Intrinsics.areEqual(strSubstring, "f") && (peek() == '\"' || peek() == '\'')) {
            this.current = this.start;
            return;
        }
        TokenType tokenType = this.keywords.get(strSubstring);
        if (tokenType == null) {
            tokenType = TokenType.IDENTIFIER;
        }
        addToken$default(this, tokenType, null, 2, null);
    }

    private final boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private final void scanToken() {
        TokenType tokenType;
        char cAdvance = advance();
        if (cAdvance == '(') {
            addToken$default(this, TokenType.LPAREN, null, 2, null);
            return;
        }
        if (cAdvance == ')') {
            addToken$default(this, TokenType.RPAREN, null, 2, null);
            return;
        }
        if (cAdvance == '{') {
            addToken$default(this, TokenType.LBRACE, null, 2, null);
            return;
        }
        if (cAdvance == '}') {
            addToken$default(this, TokenType.RBRACE, null, 2, null);
            return;
        }
        if (cAdvance == '[') {
            addToken$default(this, TokenType.LBRACKET, null, 2, null);
            return;
        }
        if (cAdvance == ']') {
            addToken$default(this, TokenType.RBRACKET, null, 2, null);
            return;
        }
        if (cAdvance == ',') {
            addToken$default(this, TokenType.COMMA, null, 2, null);
            return;
        }
        if (cAdvance == '.') {
            addToken$default(this, TokenType.DOT, null, 2, null);
            return;
        }
        if (cAdvance == ';') {
            addToken$default(this, TokenType.SEMICOLON, null, 2, null);
            return;
        }
        if (cAdvance == ':') {
            addToken$default(this, TokenType.COLON, null, 2, null);
            return;
        }
        if (cAdvance == '-') {
            if (match('>')) {
                tokenType = TokenType.ARROW;
            } else {
                tokenType = match('=') ? TokenType.MINUS_ASSIGN : TokenType.MINUS;
            }
            addToken$default(this, tokenType, null, 2, null);
            return;
        }
        if (cAdvance == '+') {
            addToken$default(this, match('=') ? TokenType.PLUS_ASSIGN : TokenType.PLUS, null, 2, null);
            return;
        }
        if (cAdvance == '*') {
            addToken$default(this, match('=') ? TokenType.MULTIPLY_ASSIGN : TokenType.MULTIPLY, null, 2, null);
            return;
        }
        if (cAdvance == '%') {
            addToken$default(this, match('=') ? TokenType.MODULO_ASSIGN : TokenType.MODULO, null, 2, null);
            return;
        }
        if (cAdvance == '!') {
            addToken$default(this, match('=') ? TokenType.NEQ : TokenType.BANG, null, 2, null);
            return;
        }
        if (cAdvance == '=') {
            addToken$default(this, match('=') ? TokenType.EQ : TokenType.ASSIGN, null, 2, null);
            return;
        }
        if (cAdvance == '<') {
            addToken$default(this, match('=') ? TokenType.LTE : TokenType.LT, null, 2, null);
            return;
        }
        if (cAdvance == '>') {
            addToken$default(this, match('=') ? TokenType.GTE : TokenType.GT, null, 2, null);
            return;
        }
        if (cAdvance == '&') {
            if (!match(Typography.amp)) {
                throw new LunoSyntaxError("Unexpected character: '&'", this.line, (this.current - this.lineStart) - 1);
            }
            addToken$default(this, TokenType.AND, null, 2, null);
            return;
        }
        if (cAdvance == '|') {
            if (!match('|')) {
                throw new LunoSyntaxError("Unexpected character: '|'", this.line, (this.current - this.lineStart) - 1);
            }
            addToken$default(this, TokenType.OR, null, 2, null);
            return;
        }
        if (cAdvance == '/') {
            if (match('/')) {
                while (peek() != '\n' && !isAtEnd()) {
                    advance();
                }
                addToken$default(this, TokenType.COMMENT, null, 2, null);
                return;
            }
            if (match('=')) {
                addToken$default(this, TokenType.DIVIDE_ASSIGN, null, 2, null);
                return;
            } else {
                addToken$default(this, TokenType.DIVIDE, null, 2, null);
                return;
            }
        }
        if (cAdvance == 'f') {
            if (peek() == '\"' || peek() == '\'') {
                f_string(advance());
                return;
            } else {
                identifier();
                return;
            }
        }
        if (cAdvance == ' ' || cAdvance == '\r' || cAdvance == '\t') {
            return;
        }
        if (cAdvance == '\n') {
            this.line++;
            this.lineStart = this.current;
            return;
        }
        if (cAdvance == '\"') {
            string('\"');
            return;
        }
        if (cAdvance == '\'') {
            string('\'');
        } else if (isDigit(cAdvance)) {
            number();
        } else {
            if (!isAlpha(cAdvance)) {
                throw new LunoSyntaxError("Unexpected character: '" + cAdvance + FormatHelper.QUOTE, this.line, (this.current - this.lineStart) - 1);
            }
            identifier();
        }
    }

    private final void f_string(char quoteType) {
        while (peek() != quoteType && !isAtEnd()) {
            if (peek() == '\n') {
                this.line++;
                this.lineStart = this.current + 1;
            }
            advance();
        }
        if (isAtEnd()) {
            throw new LunoSyntaxError("Unterminated f-string.", this.line, this.start - this.lineStart);
        }
        advance();
        String strSubstring = this.source.substring(this.start + 2, this.current - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        addToken(TokenType.F_STRING, strSubstring);
    }
}
