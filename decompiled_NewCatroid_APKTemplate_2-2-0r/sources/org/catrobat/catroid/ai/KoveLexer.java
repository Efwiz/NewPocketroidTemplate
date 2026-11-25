package org.catrobat.catroid.ai;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.slf4j.Marker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveLexer.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveParser.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ai/KoveLexer;", "", "input", "", "(Ljava/lang/String;)V", "indentStack", "", "", Constant.LINE, "tokens", "Lorg/catrobat/catroid/ai/Token;", "tokenize", "", "tokenizeLine", "", "text", "startCol", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveLexer {
    private final List<Integer> indentStack;
    private final String input;
    private int line;
    private final List<Token> tokens;

    public KoveLexer(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        this.input = input;
        this.tokens = new ArrayList();
        this.indentStack = CollectionsKt.mutableListOf(0);
        this.line = 1;
    }

    public final List<Token> tokenize() {
        int i = 0;
        for (String str : new Regex("\\r?\\n").split(this.input, 0)) {
            i++;
            this.line = i;
            if (StringsKt.trim((CharSequence) str).toString().length() != 0) {
                int i2 = 0;
                while (i2 < str.length() && str.charAt(i2) == ' ') {
                    i2++;
                }
                String strSubstring = str.substring(i2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                if (strSubstring.length() != 0 && !StringsKt.startsWith$default(strSubstring, "#", false, 2, (Object) null)) {
                    int iIntValue = ((Number) CollectionsKt.last((List) this.indentStack)).intValue();
                    if (i2 > iIntValue) {
                        this.indentStack.add(Integer.valueOf(i2));
                        this.tokens.add(new Token(TokenType.INDENT, "", this.line, 1));
                    } else if (i2 < iIntValue) {
                        while (!this.indentStack.isEmpty() && ((Number) CollectionsKt.last((List) this.indentStack)).intValue() > i2) {
                            List<Integer> list = this.indentStack;
                            list.remove(CollectionsKt.getLastIndex(list));
                            this.tokens.add(new Token(TokenType.DEDENT, "", this.line, 1));
                        }
                    }
                    tokenizeLine(strSubstring, i2 + 1);
                    this.tokens.add(new Token(TokenType.NEWLINE, "", this.line, str.length() + 1));
                }
            }
        }
        while (this.indentStack.size() > 1) {
            List<Integer> list2 = this.indentStack;
            list2.remove(CollectionsKt.getLastIndex(list2));
            this.tokens.add(new Token(TokenType.DEDENT, "", this.line, 1));
        }
        this.tokens.add(new Token(TokenType.EOF, "", this.line, 1));
        return this.tokens;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:147:0x02d1  */
    private final void tokenizeLine(String text, int startCol) {
        int i;
        int i2;
        TokenType tokenType;
        int i3;
        int length = text.length();
        int i4 = 0;
        while (i4 < length) {
            char cCharAt = text.charAt(i4);
            int i5 = startCol + i4;
            if (!CharsKt.isWhitespace(cCharAt)) {
                if (cCharAt == '(') {
                    this.tokens.add(new Token(TokenType.LPAREN, "(", this.line, i5));
                } else if (cCharAt == ')') {
                    this.tokens.add(new Token(TokenType.RPAREN, Constant.AFTER_QUTO, this.line, i5));
                } else if (cCharAt == ':') {
                    this.tokens.add(new Token(TokenType.COLON, ":", this.line, i5));
                } else if (cCharAt == ',') {
                    this.tokens.add(new Token(TokenType.COMMA, ",", this.line, i5));
                } else if (cCharAt == '@') {
                    this.tokens.add(new Token(TokenType.AT, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, this.line, i5));
                } else if (cCharAt == '+') {
                    this.tokens.add(new Token(TokenType.PLUS, Marker.ANY_NON_NULL_MARKER, this.line, i5));
                } else if (cCharAt == '-') {
                    this.tokens.add(new Token(TokenType.MINUS, Parameters.DEFAULT_OPTION_PREFIXES, this.line, i5));
                } else if (cCharAt == '*') {
                    i = i4 + 1;
                    if (i >= length || text.charAt(i) != '*') {
                        this.tokens.add(new Token(TokenType.MULT, "*", this.line, i5));
                        i4 = i;
                    } else {
                        this.tokens.add(new Token(TokenType.POW, "**", this.line, i5));
                        i4 += 2;
                    }
                } else if (cCharAt == '/') {
                    this.tokens.add(new Token(TokenType.DIV, "/", this.line, i5));
                } else if (cCharAt == '%') {
                    this.tokens.add(new Token(TokenType.MOD, "%", this.line, i5));
                } else if (cCharAt == '=') {
                    i = i4 + 1;
                    if (i >= length || text.charAt(i) != '=') {
                        this.tokens.add(new Token(TokenType.EQUAL_SINGLE, ContainerUtils.KEY_VALUE_DELIMITER, this.line, i5));
                        i4 = i;
                    } else {
                        this.tokens.add(new Token(TokenType.EQUAL_DOUBLE, "==", this.line, i5));
                        i4 += 2;
                    }
                } else {
                    if (cCharAt == '!' && (i3 = i4 + 1) < length && text.charAt(i3) == '=') {
                        this.tokens.add(new Token(TokenType.NOT_EQUAL, "!=", this.line, i5));
                    } else if (cCharAt == '>') {
                        i = i4 + 1;
                        if (i >= length || text.charAt(i) != '=') {
                            this.tokens.add(new Token(TokenType.GREATER, ">", this.line, i5));
                            i4 = i;
                        } else {
                            this.tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", this.line, i5));
                        }
                    } else if (cCharAt == '<') {
                        i = i4 + 1;
                        if (i >= length || text.charAt(i) != '=') {
                            this.tokens.add(new Token(TokenType.LESS, "<", this.line, i5));
                            i4 = i;
                        } else {
                            this.tokens.add(new Token(TokenType.LESS_EQUAL, "<=", this.line, i5));
                        }
                    } else if (cCharAt == '\"' || cCharAt == '\'') {
                        i4++;
                        StringBuilder sb = new StringBuilder();
                        while (i4 < length && text.charAt(i4) != cCharAt) {
                            if (text.charAt(i4) != '\\' || (i2 = i4 + 1) >= length) {
                                sb.append(text.charAt(i4));
                                i4++;
                            } else {
                                sb.append(text.charAt(i2));
                                i4 += 2;
                            }
                        }
                        if (i4 < length) {
                            i4++;
                        }
                        List<Token> list = this.tokens;
                        TokenType tokenType2 = TokenType.STRING;
                        String string = sb.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        list.add(new Token(tokenType2, string, this.line, i5));
                    } else if (Character.isDigit(cCharAt)) {
                        StringBuilder sb2 = new StringBuilder();
                        while (i4 < length && (Character.isDigit(text.charAt(i4)) || text.charAt(i4) == '.')) {
                            sb2.append(text.charAt(i4));
                            i4++;
                        }
                        List<Token> list2 = this.tokens;
                        TokenType tokenType3 = TokenType.NUMBER;
                        String string2 = sb2.toString();
                        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                        list2.add(new Token(tokenType3, string2, this.line, i5));
                    } else if (Character.isLetter(cCharAt) || cCharAt == '_') {
                        StringBuilder sb3 = new StringBuilder();
                        while (i4 < length && (Character.isLetterOrDigit(text.charAt(i4)) || text.charAt(i4) == '_')) {
                            sb3.append(text.charAt(i4));
                            i4++;
                        }
                        String string3 = sb3.toString();
                        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                        switch (string3) {
                            case "repeat_until":
                                tokenType = TokenType.REPEAT_UNTIL;
                                break;
                            case "repeat":
                                tokenType = TokenType.REPEAT;
                                break;
                            case "forever":
                                tokenType = TokenType.FOREVER;
                                break;
                            case "if":
                                tokenType = TokenType.IF;
                                break;
                            case "or":
                                tokenType = TokenType.OR;
                                break;
                            case "and":
                                tokenType = TokenType.AND;
                                break;
                            case "def":
                                tokenType = TokenType.DEF;
                                break;
                            case "not":
                                tokenType = TokenType.NOT;
                                break;
                            case "else":
                                tokenType = TokenType.ELSE;
                                break;
                            case "pass":
                                tokenType = TokenType.PASS;
                                break;
                            case "class":
                                tokenType = TokenType.CLASS;
                                break;
                            default:
                                tokenType = TokenType.IDENTIFIER;
                                break;
                        }
                        this.tokens.add(new Token(tokenType, string3, this.line, i5));
                    }
                    i4 += 2;
                }
            }
            i4++;
        }
    }
}
