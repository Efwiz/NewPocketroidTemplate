package org.jetbrains.kotlin.kdoc.lexer;

import java.io.IOException;
import java.io.Reader;
import org.jetbrains.kotlin.com.intellij.lexer.FlexLexer;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.kdoc.parser.KDocKnownTag;

/* JADX INFO: loaded from: classes5.dex */
class _KDocLexer implements FlexLexer {
    public static final int CODE_BLOCK = 12;
    public static final int CODE_BLOCK_CONTENTS_BEGINNING = 16;
    public static final int CODE_BLOCK_LINE_BEGINNING = 14;
    public static final int CONTENTS = 10;
    public static final int CONTENTS_BEGINNING = 4;
    public static final int INDENTED_CODE_BLOCK = 18;
    public static final int LINE_BEGINNING = 2;
    public static final int TAG_BEGINNING = 6;
    public static final int TAG_TEXT_BEGINNING = 8;
    public static final int YYEOF = -1;
    public static final int YYINITIAL = 0;
    private boolean zzAtBOL;
    private boolean zzAtEOF;
    private CharSequence zzBuffer;
    private int zzCurrentPos;
    private boolean zzEOFDone;
    private int zzEndRead;
    private int zzLexicalState;
    private int zzMarkedPos;
    private Reader zzReader;
    private int zzStartRead;
    private int zzState;
    private static final int[] ZZ_LEXSTATE = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 6, 6};
    static final char[] ZZ_CMAP_Z = zzUnpackCMap("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0005\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0011\f\u0015\u0001\u0016(\u0015\u0001\u0017\u0002\u0015\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0015\u0015\u0001\u001c\u0010\u0011\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\u0001\u0011\u0001$\u0001%\u0001&\u0001\u0011\u0001'\u0002\u0011\u0001(\u0004\u0011\u0001\u0015\u0001)\u0001*\u0005\u0011\u0002\u0015\u0001+\u0019\u0011\u0001\u0015\u0001,\u0001\u0011\u0001- \u0011\u0001.\u0011\u0011\u0001/\u00010\u000b\u0011\u00011\b\u0011S\u0015\u00012\u0007\u0015\u00013\u00014\u001f\u0011\u0001\u0015\u00014܂\u0011");
    static final char[] ZZ_CMAP_Y = zzUnpackCMap("\u0001\u0000\u0001\u0001\u0002\u0000\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0002\u0007\u0001\b\u0001\t\u0002\u0007\u0001\n\u0001\u000b\u0003\u0000\u0001\f\u0001\r\u0001\u000e\u0001\r\u0002\u0007\u0001\u000f\u0003\u0007\u0001\u000f9\u0007\u0001\u0010\u0001\u0007\u0001\u0011\u0001\u0000\u0001\u0012\u0001\u0013\u0010\u0000\u0001\u0014\u0001\f\u0001\u0015\u0001\u0016\u0002\u0007\u0001\u0017\t\u0007\u0001\u0018\u0011\u0007\u0001\u0011\u0001\u0019\u0013\u0007\u0001\u0000\u0001\u001a\u0003\u0007\u0001\u000f\u0001\u001b\u0001\u001a\u0004\u0007\u0001\u001c\b\u0000\u0003\u0007\u0002\u001d\u0002\u0000\u0001\u001e\u0002\u0000\u0005\u0007\u0001\u001d\u0003\u0000\u0001\u001f\u0001\u001a\u000b\u0007\u0001 \u0001\u0000\u0001!\u0001\u001f\u0001\u0000\u0001\"\u0002\u0000\u0001#\u0003\u0007\u0003\u0000\u0001$\n\u0007\u0001%\u0001\u0000\u0001\u001b\u0002\u0000\u0001\u0019\u0003\u0007\u0001\u001d\u0001&\u0001\r\u0002\u0007\u0001%\u0001\r\u0001'\u0001(\u0002\u0000\u0003\u0007\u0001(\b\u0000\u0001#\u0001\u0012\n\u0000\u0001)\u0006\u0007\u0001*\u0002\u0000\u0001(\u0001\u0007\u0001\u0011\u0001\u0000\u0002\u001a\u0001$\u0001+\u0001,\u0002\u0007\u0001#\u0001-\u0001*\u0001\u0000\u0001\u0015\u0001\u0000\u0001.\u0001\u0011\u0001\u0000\u0001/\u0001\u001e\u0001$\u00010\u0001,\u0002\u0007\u0001#\u00011\u0001\u0011\u0003\u0000\u00012\u0002\u0000\u00013\u0001\u0000\u0001$\u0001\u0018\u0001\u0017\u0002\u0007\u0001#\u00014\u0001*\u0002\u0000\u0001(\u0001\u0000\u0001\u0011\u0001\u0000\u0001\u001b\u0001\u0000\u0001$\u0001+\u0001,\u0002\u0007\u0001#\u00014\u0001*\u0003\u0000\u0001.\u0001\u0011\u0001\u0000\u0001\u001b\u0001\u0000\u00015\u00016\u00017\u00018\u00019\u00016\u0001\u0007\u0001\u0011\u0002\u0000\u0001(\u0004\u0000\u0001\u001b\u0001$\u0001\u0014\u0001#\u0002\u0007\u0001#\u0001:\u0001*\u0003\u0000\u0002\u0011\u0003\u0000\u0001$\u0001\u0014\u0001#\u0002\u0007\u0001#\u0001:\u0001*\u0003\u0000\u0001\u0015\u0001\u0011\u0001\u0000\u0001;\u0001\u0000\u0001$\u0001\u0014\u0001#\u0004\u0007\u0001<\u0001\u0000\u0001\u0015\u0002\u0000\u0001\u0011\u0002\u0000\u0001\u0019\u0001$\u0001\u0007\u0001\u000f\u0001\u0019\u0002\u0007\u0001\u0017\u0001 \u0001\u000f\u0007\u0000\u0001\u001a\u0005\u0007\u0001=\u0001\u001c\u0001\u000f\u0007\u0000\u0001>\u0001?\u0001)\u0001\u001a\u0001@\u0001A\u0001=\u0001\u000e\u0001B\u0002\u0000\u0001)\u0004\u0000\u0001(\u0007\u0000\u0001\u0007\u0001\u001a\u0003\u0007\u0001\u0012\u0003\u0000\u0001\u0012\u000e\u0000\u0005\u0007\u0001\u001d\u0001\u0000\u0001\u001c\u0002\u0000\u0001%\u0001\f\u0001C\u0001\u001f\u0001D\u0001\u0007\u0001\u0011\u0001\u0015\u0002\u0000\u0004\u0007\u0001\u0018\u0001\u000e\u0005\u0007\u0001E)\u0007\u00017\u0001\u000f\u00017\u0005\u0007\u00017\u0004\u0007\u00017\u0001\u000f\u00017\u0001\u0007\u0001\u000f\u0007\u0007\u00017\b\u0007\u0001\u001d\u0004\u0000\u0002\u0007\u0002\u0000\n\u0007\u0001\u0012\u0001\u0000\u0001\u001aL\u0007\u0001+\u0002\u0007\u0001\u001a\u0002\u0007\u0001\u001d\t\u0007\u00016\u0001(\u0001\u0000\u0001\u0007\u0001\u0014\u0001\u0011\u0001\u0000\u0002\u0007\u0001\u0011\u0001\u0000\u0002\u0007\u0001\u0011\u0001\u0000\u0001\u0007\u0001\u0014\u0001(\u0001\u0000\u0006\u0007\u0001/\u0003\u0000\u0001\u001c\u00019\b\u0000\u000b\u0007\u0001\u0000\u0005\u0007\u0001F\b\u0007\u0001%\u0001\u0000\u0003\u0007\u0001\u0012\u0006\u0000\u0003\u0007\u0001%\u0001\u0012\u0001\u0000\u0005\u0007\u0001/\u0002\u0000\u0001\u001a\u0007\u0000\u0002\u0007\u0001\u000f\u0001\u0000\u0006\u0007\u0001\u0012\t\u0000\u0001\u001c\u000b\u0000\u0001$\u0005\u0007\u0001/\u0001\u0000\u0001$\u0001/\u0006\u0000\u0001G\u0003\u0007\u0001(\u0001\u001f\u0001\u0000\u0001\u0019\u0004\u0007\u0001%\u0003\u0000\u0004\u0007\u0001/\u0004\u0000\u0001$\u0001\u0000\u0001\u0019\u0003\u0007\u0001%\r\u0000\u0001H\u0001I\u0001\u0000\u0018\u0007\b\u0000\"\u0007\u0002%\u0004\u0007\u0002%\u0001\u0007\u0001J\u0003\u0007\u0001%\u0006\u0007\u0001\u0014\u0001B\u0001K\u0001\u0012\u0001L\u0001/\u0001\u0007\u0001\u0012\u0001K\u0001\u0012\u0005\u0000\u0001M\u0001\u0000\u0001\u001c\u0001(\u0001\u0000\u0001'\u0003\u0000\u0001\u001b\u0001\u001c\u0002\u0000\u0001\u0007\u0001\u0012\u0003\u0007\u0001\u001d\b\u0000\u0001N\u0001\u0019\u0001 \u0001O\u0001\u0013\u0001P\u0001\u0007\u0001Q\u0001$\u0001R\u0002\u0000\u0005\u0007\u0001(N\u0000\u0005\u0007\u0001\u000f\u0005\u0007\u0001\u000f\u0010\u0007\u0001\u0012\u0001S\u0001T\u0001\u0000\u0004\u0007\u0001\u0018\u0001\u000e\u0007\u0007\u0001\u001c\u0002\u0000\u0002\u0007\u0001\u000f\u0001\u0000\b\u000f\t\u0000\u0001\u001c:\u0000\u0001$\u0003\u0000\u0001\u001a\u0001\u0011\u0001O\u0001\u0012\u0001\u001a\t\u0007\u0001\u000f\u0001$\u0001\u001a\n\u0007\u0001E\u0001$\u0004\u0007\u0001%\u0001\u001a\n\u0007\u0001\u000f\u0002\u0000\u0003\u0007\u0001\u001d\u0006\u0000x\u0007\u0001%\t\u00009\u0007\u0001\u0012\u0006\u0000\u0011\u0007\u0001\u0012\b\u0000\u0005\u0007\u0001%!\u0007\u0001\u0012\u0002\u0007\u0001\u0000\u0001T\u0002\u0000\u0005\u0007\u0001\u000f\u0001\u0000\u0001\u001c\u0003\u0007\u0001\u0000\n\u0007\u0004\u0000\u0001\u001c\u0001\u0007\u0001\u0019\f\u0007\u0001U\u0001/\u0001\u0000\u0001\u0007\u0001\u001d\t\u0000\u0001\u0007\u0001V\u0001E\u0002\u0007\u0001\u001d\u0002\u0000\u0001(\u0006\u0007\u0001/\u0001\u0000\u0001\u0019\u0005\u0007\u0001/\u0007\u0000\u0001\u0019\u0001\u001e\u0001\u0000\u0001\u0019\u0002\u0007\u0001%\u0001\u0000\u0002\u0007\u0001\u000f\u0003\u0000\u0003\u0007\u0001\u0012\u0001)\u0005\u0007\u0001\u001d\u0002\u0000\u0001\u001c\u0006\u0000\u0005\u0007\u0001(\u0002\u0000\u0001E\u0001/\u0002\u0000\u0002\u0007\u0001\u000f\u0001\r\u0006\u0007\u0001C\u0001O\u0001F\u0002\u0000\u0001W\u0001\u0007\u0001\u001d\u00013\u0001\u0000\u0003X\u0001\u0000\u0002\u000f\u0012\u0000\u0004\u0007\u0001\u001d\u0003\u00004\u0007\u0001/\u0001\u0000\u0002\u0007\u0001\u000f\u0001G\u0005\u0007\u0001/ \u0000-\u0007\u0001%\r\u0007\u0001\u0011\u0004\u0000\u0001\u000f\u0001\u0000\u0001G\u0001Y\u0001\u0007\u0001#\u0001\u000f\u0001B\u0001Z\r\u0007\u0001\u0011\u0003\u0000\u0001G,\u0007\u0001%\u0002\u0000\b\u0007\u0001\u0019\u0006\u0007\u0005\u0000\u0001\u0007\u0001\u0012\u0006\u0000\u00019\u0002\u0000\u0001$\u0003\u0000\u0001\u001b\u0001\u0014\u0010\u0007\u0001\u0012\u0001'\u0003\u0000\u0001\u001a\u0002\u0007\u00010\u0001\u001a\u0002\u0007\u0001\u001d\u0001\u001f\n\u0007\u0001\u000f\u0003\u0019\u00013\u0001I\u0003\u0000\u0001\u0007\u0001:\u0002\u0007\u0001\u000f\u0002\u0007\u0001[\u0001\u0007\u0001%\u0001\u0007\u0001%\u0004\u0000\u000f\u0007\u0001\u001d\b\u0000\u0006\u0007\u0001\u0012!\u0000\u0003\u0007\u0001\u0012\u0006\u0007\u0001(\u0005\u0000\u0003\u0007\u0001\u000f\u0002\u0000\u0003\u0007\u0001\u001d\u0006\u0000\u0003\u0007\u0001%\u0004\u0007\u0001/\u0001\u0007\u0001O\u0005\u0000\u0013\u0007\u0001%,\u0000\u0001%\u0001#\u0004\u0007\u0001\u0018\u0001\\\u0002\u0007\u0001%\u0015\u0000\u0002\u0007\u0001%\u0001\u0000\u0003\u0007\u0001\u0011\b\u0000\u0007\u0007\u0001\u001f\b\u0000\u0001(\u0001\u0000\u0001:\u0001\u001a\u0002\u0007\u0001/\u0005\u0000\u0003\u0007\u0001\u0012\u0010\u0000\u0006\u0007\u0001%\u0001\u0000\u0002\u0007\u0001%\u0001\u0000\u0002\u0007\u0001\u001d\u0011\u0000\t\u0007\u0001(6\u0000\u0001G\u0006\u0007\t\u0000\u0001G\u0005\u0007\u0004\u0000\u0003\u0007\u0001(\u0002\u0000\u0001G\u0003\u0007\u0001\u000f\u000b\u0000\u0001G\u0005\u0007\u0001\u001d\u0001\u0000\u0001]\u0017\u0000\u0005\u0007\u0001\u001d*\u0000-\u0007\u0001\u000f\u0012\u0000\f\u0007\u0001\u001d3\u0000\u0005\u0007\u0001\u000f:\u0000\u0007\u0007\u0001(X\u0000\b\u0007\u0001\u0012\u0001\u0000\u0001(\u0007\u0000\u0001G\u0001\u0007\f\u0000\u0001\u0011?\u0000\n\u0007\u0001\u0014\b\u0007\u0001\u0014\u0001^\u0001H\u0001\u0007\u0001_\u0001:\u0007\u0007\u0001\u0018\u0001`\u0002\u0014\u0003\u0007\u0001a\u0001B\u0001\u0019\u0001#)\u0007\u0001%\u0003\u0007\u0001#\u0002\u0007\u0001E\u0003\u0007\u0001E\u0002\u0007\u0001\u0014\u0003\u0007\u0001\u0014\u0002\u0007\u0001\u000f\u0003\u0007\u0001\u000f\u0003\u0007\u0001#\u0003\u0007\u0001#\u0002\u0007\u0001E\u0001/\u0006\u0000\u0001:\u0003\u0007\u0001>\u0001\u001a\u0001E\u0001b\u0001N\u0001c\u0001>\u0001J\u0001>\u0002E\u00012\u0001\u0007\u0001\u0017\u0001\u0007\u0001/\u0001d\u0001\u0017\u0001\u0007\u0001/(\u0000\u001a\u0007\u0001\u000f\u0005\u0000F\u0007\u0001\u0012\u0001\u0000\u001b\u0007\u0001%<\u0000");
    static final char[] ZZ_CMAP_A = zzUnpackCMap("\t\u0000\u0001\u0001\u0001\t\u0001\n\u0001\u000b\u0001\n\u0002\u0000\u0001\u000f\u0003\u0000\u0001\u0003\u0003\u0000\u0001\u0011\u0001\u0012\u0001\r\u0003\u0000\u0001\u0004\u0001\f\n\u0002\u0006\u0000\u0001\u000e\u0012\u0003\u0001\u0005\u0001\u0010\u0001\u0006\u0001\u0000\u0001\u0003\u0001\u0007\n\u0003\u0003\u0000\u0001\b\u0006\u0000\u0001\n\u0004\u0000\u0004\u0003\u0004\u0000\u0001\u0003\n\u0000\u0001\u0003\u0002\u0000\u0007\u0003\u0001\u0000\u0002\u0003\u0004\u0000\u0004\u0003\u0006\u0000\u0005\u0003\u0007\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0005\u0003\u0001\u0000\u0002\u0003\u0006\u0000\u0001\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0004\u0003\u0001\u0000\u000b\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0006\u0003\u0001\u0000\u0007\u0003\u0001\u0000\u0001\u0003\r\u0000\u0004\u0003\b\u0000\u0001\u0003\n\u0000\u0006\u0003\u0001\u0000\u0001\u0003\u0007\u0000\u0002\u0003\u0003\u0000\u0003\u0003\u0002\u0000\u0002\u0003\u0001\u0000\u0006\u0003\u0005\u0000\t\u0003\u0006\u0000\u0002\u0003\u0006\u0000\u0001\u0003\u0003\u0000\u0001\u0003\u000b\u0000\u0006\u0003\u0003\u0000\u0001\u0003\u0002\u0000\u0005\u0003\u0002\u0000\u0002\u0003\u0002\u0000\u0006\u0003\u0001\u0000\u0001\u0003\u0003\u0000\u0002\u0003\u0004\u0000\u0002\u0003\u0001\u0000\u0005\u0003\u0004\u0000\u0003\u0003\u0004\u0000\u0002\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0002\u0003\u0002\u0000\u0004\u0003\u0001\u0000\u0001\u0003\u0003\u0000\u0003\u0003\u0003\u0000\u0001\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0003\u0003\u0003\u0000\u0001\u0003\u0001\u0000\u0006\u0003\u0003\u0000\u0003\u0003\u0001\u0000\u0004\u0003\u0003\u0000\u0002\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0002\u0003\u0003\u0000\u0002\u0003\u0003\u0000\u0004\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0002\u0003\u0005\u0000\u0003\u0003\u0002\u0000\u0001\u0003\u0002\u0000\u0001\u0003\u0001\u0000\u0002\u0003\u0005\u0000\u0002\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0002\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0001\u0003\u0003\u0000\u0003\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0002\u0003\u0001\u0000\b\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0001\u0003\u0003\u0000\u0002\u0003\u0001\u0000\u0001\u0003\u0004\u0000\u0006\u0003\u0001\u0000\u0005\u0003\u0001\u0000\u0001\u0003\b\u0000\u0005\u0003\u0001\u0000\u0004\u0003\u0001\u0000\u0004\u0003\u0003\u0000\u0002\u0003\u0002\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0002\u0000\u0003\u0003\u0001\u0000\u0006\u0003\u0002\u0000\u0002\u0003\u0002\n\b\u0000\u0001\u0003\u0004\u0000\u0001\u0003\u0001\u0000\u0005\u0003\u0002\u0000\u0001\u0003\u0001\u0000\u0004\u0003\u0001\u0000\u0003\u0003\u0002\u0000\u0006\u0003\u0004\u0000\u0001\u0003\u0004\u0000\u0004\u0003\u0003\u0000\u0002\u0003\u0004\u0000\u0001\u0003\u0002\u0000\u0004\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0001\u0003\u0003\u0000\u0003\u0003\u0003\u0000\u0006\u0003\u0006\u0000\u0001\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0005\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0002\u0003\u0003\u0000\u0001\u0003\u0002\u0000\u0001\u0003\u0001\u0000\u0004\u0003\u0005\u0000\u0001\u0003\u0002\u0000\u0002\u0003\u0001\u0000\u0002\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0006\u0003\u0002\u0000\u0005\u0003\u0001\u0000\u0004\u0003\u0002\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0005\u0000\u0001\u0003\u0001\u0000\u0001\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0003\u0003\u0001\u0000\u0003\u0003");
    private static final int[] ZZ_ACTION = zzUnpackAction();
    private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
    private static final int[] ZZ_TRANS = zzUnpackTrans();
    private static final String[] ZZ_ERROR_MSG = {"Unknown internal scanner error", "Error: could not match input", "Error: pushback value was too large"};
    private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

    private boolean zzRefill() throws IOException {
        return true;
    }

    public static int ZZ_CMAP(int i) {
        return ZZ_CMAP_A[(i & 7) | (ZZ_CMAP_Y[(ZZ_CMAP_Z[i >> 9] << 6) | ((i >> 3) & 63)] << 3)];
    }

    private static int[] zzUnpackAction() {
        int[] iArr = new int[63];
        zzUnpackAction("\t\u0000\u0003\u0001\u0001\u0002\u0001\u0003\u0003\u0002\u0001\u0004\u0001\u0005\u0001\u0004\u0003\u0002\u0001\u0006\u0001\u0007\u0002\u0002\u0001\b\u0001\t\u0003\b\u0001\n\u0001\u0000\u0001\u000b\u0006\u0000\u0001\u0004\u0001\f\u0001\r\u0004\u0000\u0001\u000e\u0002\u0000\u0001\u000f\u0001\u0004\u0001\u0010\u0001\u0011\u0001\u0000\u0002\u0012\u0001\u0000\u0001\u0013\u0001\u0003\u0001\u0014\u0001\u0013", 0, iArr);
        return iArr;
    }

    private static int zzUnpackAction(String str, int i, int[] iArr) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int iCharAt = str.charAt(i2);
            i2 += 2;
            char cCharAt = str.charAt(i3);
            do {
                iArr[i] = cCharAt;
                iCharAt--;
                i++;
            } while (iCharAt > 0);
        }
        return i;
    }

    private static int[] zzUnpackRowMap() {
        int[] iArr = new int[63];
        zzUnpackRowMap("\u0000\u0000\u0000\u0013\u0000&\u00009\u0000L\u0000_\u0000r\u0000\u0085\u0000\u0098\u0000«\u0000¾\u0000Ñ\u0000«\u0000ä\u0000÷\u0000Ċ\u0000ĝ\u0000İ\u0000Ń\u0000Ŗ\u0000ũ\u0000Ñ\u0000ż\u0000Ə\u0000Ƣ\u0000Ƶ\u0000ǈ\u0000«\u0000Ǜ\u0000Ñ\u0000Ǯ\u0000ȁ\u0000Ȕ\u0000ȧ\u0000«\u0000Ñ\u0000Ⱥ\u0000ɍ\u0000ɠ\u0000ɳ\u0000ʆ\u0000ʙ\u0000«\u0000ʬ\u0000ʿ\u0000˒\u0000˥\u0000˸\u0000«\u0000̋\u0000̞\u0000̱\u0000̈́\u0000«\u0000«\u0000͗\u0000Ⱥ\u0000ɠ\u0000ͪ\u0000Ⱥ\u0000̈́\u0000ͽ\u0000«", 0, iArr);
        return iArr;
    }

    private static int zzUnpackRowMap(String str, int i, int[] iArr) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int iCharAt = str.charAt(i2) << 16;
            i2 += 2;
            iArr[i] = str.charAt(i3) | iCharAt;
            i++;
        }
        return i;
    }

    private static int[] zzUnpackTrans() {
        int[] iArr = new int[912];
        zzUnpackTrans("\f\n\u0001\u000b\u0001\f\u0005\n\u0001\r\u0001\u000e\u0003\r\u0001\u000f\u0001\r\u0001\u0010\u0001\u0011\u0001\u0012\u0001\r\u0001\u0012\u0001\r\u0001\u0013\u0001\r\u0001\u0014\u0001\u0015\u0003\r\u0001\u000e\u0003\r\u0001\u000f\u0001\r\u0001\u0010\u0001\u0011\u0001\u0012\u0001\r\u0001\u0012\u0001\r\u0001\u0016\u0001\u0017\u0001\u0014\u0001\u0015\u0003\r\u0001\u0018\u0001\r\u0001\u0019\u0001\r\u0001\u001a\u0003\r\u0001\u0018\u0001\r\u0001\u0018\u0001\r\u0001\u0016\u0001\r\u0001\u0018\u0004\r\u0001\u0018\u0003\r\u0001\u001b\u0003\r\u0001\u0018\u0001\r\u0001\u0018\u0001\r\u0001\u0016\u0001\r\u0001\u0018\u0004\r\u0001\u000e\u0003\r\u0001\u000f\u0001\r\u0001\u0010\u0001\u0011\u0001\u0012\u0001\r\u0001\u0012\u0001\r\u0001\u0016\u0001\r\u0001\u0014\u0001\u0015\u0002\r\u0001\u001c\u0001\u001d\u0007\u001c\u0001\u001d\u0001\u001c\u0001\u001d\u0001\u001c\u0001\u001e\u0001\u001c\u0001\u001d\u0004\u001c\u0001\u001d\u0005\u001c\u0001\u001f\u0001 \u0001\u001d\u0001\u001c\u0001\u001d\u0001\u001c\u0001!\u0001\u001c\u0001\u001d\u0004\u001c\u0001\u001d\u0005\u001c\u0001\u001f\u0001 \u0001\u001d\u0001\u001c\u0001\u001d\u0001\u001c\u0001\u001e\u0001\u001c\u0001\u001d\u0003\u001c \u0000\u0001\"\u0011\u0000\u0001#\u0001$\u0006\u0000\u0001\u000e\u0007\u0000\u0001\u0012\u0001\u0000\u0001\u0012\u0003\u0000\u0001\u0012\u0003\u0000\u0003%\u0001&\u0001%\u0001\u0000\u0001'\f%\u0007\u0000\u0001(\u0013\u0000\u0001)\u000b\u0000\u0001\u0012\u0007\u0000\u0001\u0012\u0001\u0000\u0001\u0012\u0003\u0000\u0001\u0012\u000f\u0000\u0001#\u0001\u0013\u0006\u0000\u0001\u0012\u0007\u0000\u0001\u0012\u0001\u0000\u0001\u0012\u0003\u0000\u0001*\b\u0000\u0002+\u000f\u0000\u0001,\u0010\u0000\u0001\u0018\u0007\u0000\u0001\u0018\u0001\u0000\u0001\u0018\u0003\u0000\u0001\u0018\u0005\u0000\u0003\u0019\u0011\u0000\u0001-\u0012\u0000\u0001.\u0010\u0000\u0001\u001d\u0007\u0000\u0001\u001d\u0001\u0000\u0001\u001d\u0003\u0000\u0001\u001d\n\u0000\u0001/\u0013\u0000\u00010\u0016\u0000\u0001#\u0001!\u0012\u0000\u00011\u0005\u0000\u0005%\u0001\u0000\u0001'\u000e%\u0003&\u0001\u0000\u00012\u0011%\u0001\u0000\u0001'\n%\u00013\u0001%\u0007\u0000\u00014\u0013\u0000\u00014\u000b\u0000\u0001\u0012\u0007\u0000\u0001\u0012\u0001\u0000\u0001\u0012\u0003\u0000\u00015\u0005\u0000\u0002,\u0011\u0000\u0003-\u0001\u0000\u00016\u000e\u0000\u0003.\u0001\u0000\u00017\u0013\u0000\u00018\u0013\u0000\u00018\n\u0000\u00059\u0001\u0000\u0001:\n9\u00013\u00019\u00053\u0001;\f3\u0001<\t4\u0003\u0000\u00074\u0001\u0000\u0001\u0012\u0007\u0000\u0001\u0012\u0001\u0000\u0001\u0012\u0003\u0000\u0001=\u0004\u0000\u00018\u0007\u0000\u0001>\u0001\u0000\u00018\u0003\u0000\u00018\u0003\u0000\u0012;\u0001?\u0001\u0000\u0001>\t\u0000\u0001>\u0003\u0000\u0001>\u0003\u0000", 0, iArr);
        return iArr;
    }

    private static int zzUnpackTrans(String str, int i, int[] iArr) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int iCharAt = str.charAt(i2);
            i2 += 2;
            int iCharAt2 = str.charAt(i3) - 1;
            do {
                iArr[i] = iCharAt2;
                iCharAt--;
                i++;
            } while (iCharAt > 0);
        }
        return i;
    }

    private static int[] zzUnpackAttribute() {
        int[] iArr = new int[63];
        zzUnpackAttribute("\t\u0000\u0001\t\u0002\u0001\u0001\t\u000e\u0001\u0001\t\u0005\u0001\u0001\u0000\u0001\t\u0006\u0000\u0001\u0001\u0001\t\u0001\u0001\u0004\u0000\u0001\t\u0002\u0000\u0002\u0001\u0002\t\u0001\u0000\u0002\u0001\u0001\u0000\u0003\u0001\u0001\t", 0, iArr);
        return iArr;
    }

    private static int zzUnpackAttribute(String str, int i, int[] iArr) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int iCharAt = str.charAt(i2);
            i2 += 2;
            char cCharAt = str.charAt(i3);
            do {
                iArr[i] = cCharAt;
                iCharAt--;
                i++;
            } while (iCharAt > 0);
        }
        return i;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public _KDocLexer() {
        this(null);
    }

    private boolean isLastToken() {
        return this.zzMarkedPos == this.zzBuffer.length();
    }

    private boolean yytextContainLineBreaks() {
        return CharArrayUtil.containLineBreaks(this.zzBuffer, this.zzStartRead, this.zzMarkedPos);
    }

    _KDocLexer(Reader reader) {
        this.zzLexicalState = 0;
        this.zzBuffer = "";
        this.zzAtBOL = true;
        this.zzReader = reader;
    }

    private static char[] zzUnpackCMap(String str) {
        int length = str.length();
        int i = 0;
        int iCharAt = 0;
        for (int i2 = 0; i2 < length; i2 += 2) {
            iCharAt += str.charAt(i2);
        }
        char[] cArr = new char[iCharAt];
        int i3 = 0;
        while (i < str.length()) {
            int i4 = i + 1;
            int iCharAt2 = str.charAt(i);
            i += 2;
            char cCharAt = str.charAt(i4);
            do {
                cArr[i3] = cCharAt;
                iCharAt2--;
                i3++;
            } while (iCharAt2 > 0);
        }
        return cArr;
    }

    public final int getTokenStart() {
        return this.zzStartRead;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.FlexLexer
    public final int getTokenEnd() {
        return getTokenStart() + yylength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.FlexLexer
    public void reset(CharSequence charSequence, int i, int i2, int i3) {
        this.zzBuffer = charSequence;
        this.zzStartRead = i;
        this.zzMarkedPos = i;
        this.zzCurrentPos = i;
        this.zzAtEOF = false;
        this.zzAtBOL = true;
        this.zzEndRead = i2;
        yybegin(i3);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.FlexLexer
    public final int yystate() {
        return this.zzLexicalState;
    }

    public final void yybegin(int i) {
        this.zzLexicalState = i;
    }

    public final CharSequence yytext() {
        return this.zzBuffer.subSequence(this.zzStartRead, this.zzMarkedPos);
    }

    public final char yycharat(int i) {
        return this.zzBuffer.charAt(this.zzStartRead + i);
    }

    public final int yylength() {
        return this.zzMarkedPos - this.zzStartRead;
    }

    private void zzScanError(int i) {
        String str;
        try {
            str = ZZ_ERROR_MSG[i];
        } catch (ArrayIndexOutOfBoundsException unused) {
            str = ZZ_ERROR_MSG[0];
        }
        throw new Error(str);
    }

    public void yypushback(int i) {
        if (i > yylength()) {
            zzScanError(2);
        }
        this.zzMarkedPos -= i;
    }

    private void zzDoEOF() {
        if (this.zzEOFDone) {
            return;
        }
        this.zzEOFDone = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lexer.FlexLexer
    public IElementType advance() throws IOException {
        int i;
        int iCodePointAt;
        int i2 = this.zzEndRead;
        CharSequence charSequence = this.zzBuffer;
        int[] iArr = ZZ_TRANS;
        int[] iArr2 = ZZ_ROWMAP;
        int[] iArr3 = ZZ_ATTRIBUTE;
        while (true) {
            int iCharCount = this.zzMarkedPos;
            this.zzStartRead = iCharCount;
            this.zzCurrentPos = iCharCount;
            int i3 = ZZ_LEXSTATE[this.zzLexicalState];
            this.zzState = i3;
            int i4 = (iArr3[i3] & 1) == 1 ? i3 : -1;
            while (true) {
                i = 8;
                if (iCharCount < i2) {
                    iCodePointAt = Character.codePointAt(charSequence, iCharCount);
                    iCharCount += Character.charCount(iCodePointAt);
                } else {
                    if (!this.zzAtEOF) {
                        this.zzCurrentPos = iCharCount;
                        this.zzMarkedPos = iCharCount;
                        boolean zZzRefill = zzRefill();
                        int i5 = this.zzCurrentPos;
                        iCharCount = this.zzMarkedPos;
                        CharSequence charSequence2 = this.zzBuffer;
                        int i6 = this.zzEndRead;
                        if (zZzRefill) {
                            charSequence = charSequence2;
                            i2 = i6;
                        } else {
                            int iCodePointAt2 = Character.codePointAt(charSequence2, i5);
                            int iCharCount2 = i5 + Character.charCount(iCodePointAt2);
                            iCodePointAt = iCodePointAt2;
                            i2 = i6;
                            iCharCount = iCharCount2;
                            charSequence = charSequence2;
                        }
                    }
                    iCodePointAt = -1;
                }
                int i7 = iArr[iArr2[this.zzState] + ZZ_CMAP(iCodePointAt)];
                if (i7 != -1) {
                    this.zzState = i7;
                    int i8 = iArr3[i7];
                    if ((i8 & 1) == 1) {
                        if ((i8 & 8) == 8) {
                            iCharCount = iCharCount;
                            i4 = i7;
                        } else {
                            iCharCount = iCharCount;
                            i4 = i7;
                        }
                    }
                }
            }
            this.zzMarkedPos = iCharCount;
            if (iCodePointAt == -1 && this.zzStartRead == this.zzCurrentPos) {
                this.zzAtEOF = true;
                zzDoEOF();
                return null;
            }
            if (i4 >= 0) {
                i4 = ZZ_ACTION[i4];
            }
            switch (i4) {
                case 1:
                    return TokenType.BAD_CHARACTER;
                case 2:
                    yybegin(10);
                    return KDocTokens.TEXT;
                case 3:
                    if (yystate() == 4) {
                        yybegin(18);
                        return KDocTokens.CODE_BLOCK_TEXT;
                    }
                    break;
                case 4:
                    if (yytextContainLineBreaks()) {
                        yybegin(2);
                        return TokenType.WHITE_SPACE;
                    }
                    yybegin(yystate() != 4 ? 10 : 4);
                    return KDocTokens.TEXT;
                case 5:
                    yybegin(4);
                    return KDocTokens.LEADING_ASTERISK;
                case 6:
                    if (yytextContainLineBreaks()) {
                        yybegin(2);
                    }
                    return TokenType.WHITE_SPACE;
                case 7:
                    yybegin(8);
                    return KDocTokens.MARKDOWN_LINK;
                case 8:
                    yybegin(yystate() != 18 ? 12 : 18);
                    return KDocTokens.CODE_BLOCK_TEXT;
                case 9:
                    if (yytextContainLineBreaks()) {
                        yybegin(yystate() == 18 ? 2 : 14);
                        return TokenType.WHITE_SPACE;
                    }
                    return KDocTokens.CODE_BLOCK_TEXT;
                case 10:
                    yybegin(16);
                    return KDocTokens.LEADING_ASTERISK;
                case 11:
                    return isLastToken() ? KDocTokens.END : KDocTokens.TEXT;
                case 12:
                    yybegin(10);
                    return KDocTokens.MARKDOWN_ESCAPED_CHAR;
                case 13:
                    KDocKnownTag kDocKnownTagFindByTagName = KDocKnownTag.INSTANCE.findByTagName(this.zzBuffer.subSequence(this.zzStartRead, this.zzMarkedPos));
                    if (kDocKnownTagFindByTagName != null && kDocKnownTagFindByTagName.getIsReferenceRequired()) {
                        i = 6;
                    }
                    yybegin(i);
                    return KDocTokens.TAG_NAME;
                case 14:
                    yybegin(4);
                    return KDocTokens.START;
                case 15:
                    yybegin(14);
                    return KDocTokens.TEXT;
                case 16:
                    yybegin(8);
                    return KDocTokens.MARKDOWN_LINK;
                case 17:
                    yybegin(10);
                    return KDocTokens.MARKDOWN_LINK;
                case 18:
                    this.zzMarkedPos = Character.offsetByCodePoints(charSequence, iCharCount, -1);
                    yybegin(10);
                    return KDocTokens.MARKDOWN_LINK;
                case 19:
                    yybegin(10);
                    return KDocTokens.MARKDOWN_INLINE_LINK;
                case 20:
                    this.zzMarkedPos = Character.offsetByCodePoints(charSequence, this.zzStartRead, 3);
                    yybegin(10);
                    return KDocTokens.TEXT;
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                    break;
                default:
                    zzScanError(1);
                    break;
            }
        }
    }
}
