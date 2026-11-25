package org.catrobat.catroid.utils.lunoscript;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/TokenType.class
  classes14.dex
 */
/* JADX INFO: compiled from: Token.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b@\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@¨\u0006A"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/TokenType;", "", "(Ljava/lang/String;I)V", "VAR", "IF", "ELSE", "FUN", "CLASS", "STATIC", "RETURN", "TRUE", "FALSE", "NULL", "WHILE", "FOR", "IN", "SWITCH", "CASE", "DEFAULT", "BREAK", "CONTINUE", "THIS", "SUPER", "IMPORT", "TRY", "CATCH", "FINALLY", "IDENTIFIER", "NUMBER_LITERAL", "F_STRING", "STRING_LITERAL", "FLOAT_LITERAL", "ASSIGN", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "MODULO", "EQ", "NEQ", "LT", "GT", "LTE", "GTE", "PLUS_ASSIGN", "MINUS_ASSIGN", "MULTIPLY_ASSIGN", "DIVIDE_ASSIGN", "MODULO_ASSIGN", "BANG", "AND", "OR", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "COMMA", "DOT", "SEMICOLON", "COLON", "ARROW", "COMMENT", "EOF", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TokenType {
    VAR,
    IF,
    ELSE,
    FUN,
    CLASS,
    STATIC,
    RETURN,
    TRUE,
    FALSE,
    NULL,
    WHILE,
    FOR,
    IN,
    SWITCH,
    CASE,
    DEFAULT,
    BREAK,
    CONTINUE,
    THIS,
    SUPER,
    IMPORT,
    TRY,
    CATCH,
    FINALLY,
    IDENTIFIER,
    NUMBER_LITERAL,
    F_STRING,
    STRING_LITERAL,
    FLOAT_LITERAL,
    ASSIGN,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULO,
    EQ,
    NEQ,
    LT,
    GT,
    LTE,
    GTE,
    PLUS_ASSIGN,
    MINUS_ASSIGN,
    MULTIPLY_ASSIGN,
    DIVIDE_ASSIGN,
    MODULO_ASSIGN,
    BANG,
    AND,
    OR,
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    COMMA,
    DOT,
    SEMICOLON,
    COLON,
    ARROW,
    COMMENT,
    EOF;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<TokenType> getEntries() {
        return $ENTRIES;
    }
}
