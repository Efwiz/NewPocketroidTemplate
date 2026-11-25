package org.catrobat.catroid.ai;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/TokenType.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveParser.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b&\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/ai/TokenType;", "", "(Ljava/lang/String;I)V", "IDENTIFIER", "NUMBER", "STRING", "PLUS", "MINUS", "MULT", "DIV", "MOD", "POW", "EQUAL_SINGLE", "EQUAL_DOUBLE", "NOT_EQUAL", "GREATER", "GREATER_EQUAL", "LESS", "LESS_EQUAL", "AND", "OR", "NOT", "LPAREN", "RPAREN", "COLON", "COMMA", "AT", "IF", "ELSE", "FOREVER", "REPEAT", "REPEAT_UNTIL", "DEF", "CLASS", "PASS", "INDENT", "DEDENT", "NEWLINE", "EOF", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TokenType {
    IDENTIFIER,
    NUMBER,
    STRING,
    PLUS,
    MINUS,
    MULT,
    DIV,
    MOD,
    POW,
    EQUAL_SINGLE,
    EQUAL_DOUBLE,
    NOT_EQUAL,
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,
    AND,
    OR,
    NOT,
    LPAREN,
    RPAREN,
    COLON,
    COMMA,
    AT,
    IF,
    ELSE,
    FOREVER,
    REPEAT,
    REPEAT_UNTIL,
    DEF,
    CLASS,
    PASS,
    INDENT,
    DEDENT,
    NEWLINE,
    EOF;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<TokenType> getEntries() {
        return $ENTRIES;
    }
}
