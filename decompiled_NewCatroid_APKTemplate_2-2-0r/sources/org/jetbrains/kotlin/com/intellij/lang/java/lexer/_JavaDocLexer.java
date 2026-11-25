package org.jetbrains.kotlin.com.intellij.lang.java.lexer;

import java.io.IOException;
import java.io.Reader;
import org.jetbrains.kotlin.com.intellij.lexer.DocCommentTokenTypes;
import org.jetbrains.kotlin.com.intellij.lexer.FlexLexer;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
class _JavaDocLexer implements FlexLexer {
    private boolean myJdk15Enabled;
    private DocCommentTokenTypes myTokenTypes;
    private boolean zzAtBOL;
    private boolean zzAtEOF;
    private CharSequence zzBuffer;
    private int zzCurrentPos;
    private int zzEndRead;
    private int zzLexicalState;
    private int zzMarkedPos;
    private Reader zzReader;
    private int zzStartRead;
    private int zzState;
    private static final int[] ZZ_LEXSTATE = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10};
    static final char[] ZZ_CMAP_Z = zzUnpackCMap("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0005\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0011\f\u0015\u0001\u0016(\u0015\u0001\u0017\u0002\u0015\u0001\u0018\u0001\u0019\u0001\u001a\u0001\u001b\u0015\u0015\u0001\u001c\u0010\u0011\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\u0001\u0011\u0001$\u0001%\u0001&\u0001\u0011\u0001'\u0002\u0011\u0001(\u0004\u0011\u0001\u0015\u0001)\u0001*\u0005\u0011\u0002\u0015\u0001+\u0019\u0011\u0001\u0015\u0001,\u0001\u0011\u0001- \u0011\u0001.\u0011\u0011\u0001/\u00010\u000b\u0011\u00011\b\u0011S\u0015\u00012\u0007\u0015\u00013\u00014\u001f\u0011\u0001\u0015\u00014܂\u0011");
    static final char[] ZZ_CMAP_Y = zzUnpackCMap("\u0001\u0000\u0001\u0001\u0002\u0000\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0002\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0003\u0000\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u000f\u0002\u0007\u0001\u0011\u0003\u0007\u0001\u00119\u0007\u0001\u0012\u0001\u0007\u0001\u0013\u0001\u0000\u0001\u0014\u0001\u0015\u0010\u0000\u0001\u0016\u0001\u000e\u0001\u0017\u0001\u0018\u0002\u0007\u0001\u0019\t\u0007\u0001\u001a\u0011\u0007\u0001\u0013\u0001\u001b\u0013\u0007\u0001\u0000\u0001\u001c\u0003\u0007\u0001\u0011\u0001\u001d\u0001\u001c\u0004\u0007\u0001\u001e\b\u0000\u0003\u0007\u0002\u001f\u0002\u0000\u0001 \u0002\u0000\u0005\u0007\u0001\u001f\u0003\u0000\u0001!\u0001\u001c\u000b\u0007\u0001\"\u0001\u0000\u0001#\u0001!\u0001\u0000\u0001$\u0002\u0000\u0001%\u0003\u0007\u0003\u0000\u0001&\n\u0007\u0001'\u0001\u0000\u0001\u001d\u0002\u0000\u0001\u001b\u0003\u0007\u0001\u001f\u0001(\u0001\u000f\u0002\u0007\u0001'\u0001\u000f\u0001)\u0001*\u0002\u0000\u0003\u0007\u0001*\b\u0000\u0001%\u0001\u0014\n\u0000\u0001+\u0006\u0007\u0001,\u0002\u0000\u0001*\u0001\u0007\u0001\u0013\u0001\u0000\u0002\u001c\u0001&\u0001-\u0001.\u0002\u0007\u0001%\u0001/\u0001,\u0001\u0000\u0001\u0017\u0001\u0000\u00010\u0001\u0013\u0001\u0000\u00011\u0001 \u0001&\u00012\u0001.\u0002\u0007\u0001%\u00013\u0001\u0013\u0003\u0000\u00014\u0002\u0000\u00015\u0001\u0000\u0001&\u0001\u001a\u0001\u0019\u0002\u0007\u0001%\u00016\u0001,\u0002\u0000\u0001*\u0001\u0000\u0001\u0013\u0001\u0000\u0001\u001d\u0001\u0000\u0001&\u0001-\u0001.\u0002\u0007\u0001%\u00016\u0001,\u0003\u0000\u00010\u0001\u0013\u0001\u0000\u0001\u001d\u0001\u0000\u00017\u00018\u00019\u0001:\u0001;\u00018\u0001\u0007\u0001\u0013\u0002\u0000\u0001*\u0004\u0000\u0001\u001d\u0001&\u0001\u0016\u0001%\u0002\u0007\u0001%\u0001<\u0001,\u0003\u0000\u0002\u0013\u0003\u0000\u0001&\u0001\u0016\u0001%\u0002\u0007\u0001%\u0001<\u0001,\u0003\u0000\u0001\u0017\u0001\u0013\u0001\u0000\u0001=\u0001\u0000\u0001&\u0001\u0016\u0001%\u0004\u0007\u0001>\u0001\u0000\u0001\u0017\u0002\u0000\u0001\u0013\u0002\u0000\u0001\u001b\u0001&\u0001\u0007\u0001\u0011\u0001\u001b\u0002\u0007\u0001\u0019\u0001\"\u0001\u0011\u0007\u0000\u0001\u001c\u0005\u0007\u0001?\u0001\u001e\u0001\u0011\u0007\u0000\u0001@\u0001A\u0001+\u0001\u001c\u0001B\u0001C\u0001?\u0001\u0010\u0001D\u0002\u0000\u0001+\u0004\u0000\u0001*\u0007\u0000\u0001\u0007\u0001\u001c\u0003\u0007\u0001\u0014\u0003\u0000\u0001\u0014\u000e\u0000\u0005\u0007\u0001\u001f\u0001\u0000\u0001\u001e\u0002\u0000\u0001'\u0001\u000e\u0001E\u0001!\u0001F\u0001\u0007\u0001\u0013\u0001\u0017\u0002\u0000\u0004\u0007\u0001\u001a\u0001\u0010\u0005\u0007\u0001G)\u0007\u00019\u0001\u0011\u00019\u0005\u0007\u00019\u0004\u0007\u00019\u0001\u0011\u00019\u0001\u0007\u0001\u0011\u0007\u0007\u00019\b\u0007\u0001\u001f\u0004\u0000\u0002\u0007\u0002\u0000\n\u0007\u0001\u0014\u0001\u0000\u0001\u001cL\u0007\u0001-\u0002\u0007\u0001\u001c\u0002\u0007\u0001\u001f\t\u0007\u00018\u0001*\u0001\u0000\u0001\u0007\u0001\u0016\u0001\u0013\u0001\u0000\u0002\u0007\u0001\u0013\u0001\u0000\u0002\u0007\u0001\u0013\u0001\u0000\u0001\u0007\u0001\u0016\u0001*\u0001\u0000\u0006\u0007\u00011\u0003\u0000\u0001\u001e\u0001;\b\u0000\u000b\u0007\u0001\u0000\u0005\u0007\u0001H\b\u0007\u0001'\u0001\u0000\u0003\u0007\u0001\u0014\u0006\u0000\u0003\u0007\u0001'\u0001\u0014\u0001\u0000\u0005\u0007\u00011\u0002\u0000\u0001\u001c\u0007\u0000\u0002\u0007\u0001\u0011\u0001\u0000\u0006\u0007\u0001\u0014\t\u0000\u0001\u001e\u000b\u0000\u0001&\u0005\u0007\u00011\u0001\u0000\u0001&\u00011\u0006\u0000\u0001I\u0003\u0007\u0001*\u0001!\u0001\u0000\u0001\u001b\u0004\u0007\u0001'\u0003\u0000\u0004\u0007\u00011\u0004\u0000\u0001&\u0001\u0000\u0001\u001b\u0003\u0007\u0001'\r\u0000\u0001J\u0001K\u0001\u0000\u0018\u0007\b\u0000\"\u0007\u0002'\u0004\u0007\u0002'\u0001\u0007\u0001L\u0003\u0007\u0001'\u0006\u0007\u0001\u0016\u0001D\u0001M\u0001\u0014\u0001N\u00011\u0001\u0007\u0001\u0014\u0001M\u0001\u0014\u0005\u0000\u0001O\u0001\u0000\u0001\u001e\u0001*\u0001\u0000\u0001)\u0003\u0000\u0001\u001d\u0001\u001e\u0002\u0000\u0001\u0007\u0001\u0014\u0003\u0007\u0001\u001f\b\u0000\u0001P\u0001\u001b\u0001\"\u0001Q\u0001\u0015\u0001R\u0001\u0007\u0001S\u0001&\u0001T\u0002\u0000\u0005\u0007\u0001*N\u0000\u0005\u0007\u0001\u0011\u0005\u0007\u0001\u0011\u0010\u0007\u0001\u0014\u0001U\u0001V\u0001\u0000\u0004\u0007\u0001\u001a\u0001\u0010\u0007\u0007\u0001\u001e\u0002\u0000\u0002\u0007\u0001\u0011\u0001\u0000\b\u0011\t\u0000\u0001\u001e:\u0000\u0001&\u0003\u0000\u0001\u001c\u0001\u0013\u0001Q\u0001\u0014\u0001\u001c\t\u0007\u0001\u0011\u0001&\u0001\u001c\n\u0007\u0001G\u0001&\u0004\u0007\u0001'\u0001\u001c\n\u0007\u0001\u0011\u0002\u0000\u0003\u0007\u0001\u001f\u0006\u0000x\u0007\u0001'\t\u00009\u0007\u0001\u0014\u0006\u0000\u0011\u0007\u0001\u0014\b\u0000\u0005\u0007\u0001'!\u0007\u0001\u0014\u0002\u0007\u0001\u0000\u0001V\u0002\u0000\u0005\u0007\u0001\u0011\u0001\u0000\u0001\u001e\u0003\u0007\u0001\u0000\n\u0007\u0004\u0000\u0001\u001e\u0001\u0007\u0001\u001b\f\u0007\u0001W\u00011\u0001\u0000\u0001\u0007\u0001\u001f\t\u0000\u0001\u0007\u0001X\u0001G\u0002\u0007\u0001\u001f\u0002\u0000\u0001*\u0006\u0007\u00011\u0001\u0000\u0001\u001b\u0005\u0007\u00011\u0007\u0000\u0001\u001b\u0001 \u0001\u0000\u0001\u001b\u0002\u0007\u0001'\u0001\u0000\u0002\u0007\u0001\u0011\u0003\u0000\u0003\u0007\u0001\u0014\u0001+\u0005\u0007\u0001\u001f\u0002\u0000\u0001\u001e\u0006\u0000\u0005\u0007\u0001*\u0002\u0000\u0001G\u00011\u0002\u0000\u0002\u0007\u0001\u0011\u0001\u000f\u0006\u0007\u0001E\u0001Q\u0001H\u0002\u0000\u0001Y\u0001\u0007\u0001\u001f\u00015\u0001\u0000\u0003Z\u0001\u0000\u0002\u0011\u0012\u0000\u0004\u0007\u0001\u001f\u0003\u00004\u0007\u00011\u0001\u0000\u0002\u0007\u0001\u0011\u0001I\u0005\u0007\u00011 \u0000-\u0007\u0001'\r\u0007\u0001\u0013\u0004\u0000\u0001\u0011\u0001\u0000\u0001I\u0001[\u0001\u0007\u0001%\u0001\u0011\u0001D\u0001\\\r\u0007\u0001\u0013\u0003\u0000\u0001I,\u0007\u0001'\u0002\u0000\b\u0007\u0001\u001b\u0006\u0007\u0005\u0000\u0001\u0007\u0001\u0014\u0006\u0000\u0001;\u0002\u0000\u0001&\u0003\u0000\u0001\u001d\u0001\u0016\u0010\u0007\u0001\u0014\u0001)\u0003\u0000\u0001\u001c\u0002\u0007\u00012\u0001\u001c\u0002\u0007\u0001\u001f\u0001!\n\u0007\u0001\u0011\u0003\u001b\u00015\u0001K\u0003\u0000\u0001\u0007\u0001<\u0002\u0007\u0001\u0011\u0002\u0007\u0001]\u0001\u0007\u0001'\u0001\u0007\u0001'\u0004\u0000\u000f\u0007\u0001\u001f\b\u0000\u0006\u0007\u0001\u0014!\u0000\u0003\u0007\u0001\u0014\u0006\u0007\u0001*\u0005\u0000\u0003\u0007\u0001\u0011\u0002\u0000\u0003\u0007\u0001\u001f\u0006\u0000\u0003\u0007\u0001'\u0004\u0007\u00011\u0001\u0007\u0001Q\u0005\u0000\u0013\u0007\u0001',\u0000\u0001'\u0001%\u0004\u0007\u0001\u001a\u0001^\u0002\u0007\u0001'\u0015\u0000\u0002\u0007\u0001'\u0001\u0000\u0003\u0007\u0001\u0013\b\u0000\u0007\u0007\u0001!\b\u0000\u0001*\u0001\u0000\u0001<\u0001\u001c\u0002\u0007\u00011\u0005\u0000\u0003\u0007\u0001\u0014\u0010\u0000\u0006\u0007\u0001'\u0001\u0000\u0002\u0007\u0001'\u0001\u0000\u0002\u0007\u0001\u001f\u0011\u0000\t\u0007\u0001*6\u0000\u0001I\u0006\u0007\t\u0000\u0001I\u0005\u0007\u0004\u0000\u0003\u0007\u0001*\u0002\u0000\u0001I\u0003\u0007\u0001\u0011\u000b\u0000\u0001I\u0005\u0007\u0001\u001f\u0001\u0000\u0001_\u0017\u0000\u0005\u0007\u0001\u001f*\u0000-\u0007\u0001\u0011\u0012\u0000\f\u0007\u0001\u001f3\u0000\u0005\u0007\u0001\u0011:\u0000\u0007\u0007\u0001*X\u0000\b\u0007\u0001\u0014\u0001\u0000\u0001*\u0007\u0000\u0001I\u0001\u0007\f\u0000\u0001\u0013?\u0000\n\u0007\u0001\u0016\b\u0007\u0001\u0016\u0001`\u0001J\u0001\u0007\u0001a\u0001<\u0007\u0007\u0001\u001a\u0001b\u0002\u0016\u0003\u0007\u0001c\u0001D\u0001\u001b\u0001%)\u0007\u0001'\u0003\u0007\u0001%\u0002\u0007\u0001G\u0003\u0007\u0001G\u0002\u0007\u0001\u0016\u0003\u0007\u0001\u0016\u0002\u0007\u0001\u0011\u0003\u0007\u0001\u0011\u0003\u0007\u0001%\u0003\u0007\u0001%\u0002\u0007\u0001G\u00011\u0006\u0000\u0001<\u0003\u0007\u0001@\u0001\u001c\u0001G\u0001d\u0001P\u0001e\u0001@\u0001L\u0001@\u0002G\u00014\u0001\u0007\u0001\u0019\u0001\u0007\u00011\u0001f\u0001\u0019\u0001\u0007\u00011(\u0000\u001a\u0007\u0001\u0011\u0005\u0000F\u0007\u0001\u0014\u0001\u0000\u001b\u0007\u0001'<\u0000");
    static final char[] ZZ_CMAP_A = zzUnpackCMap("\t\u0000\u0001\u0002\u0001\u0001\u0001\u001d\u0001\u001e\u0001\u0001\u0002\u0000\u0001\u0002\u0002\u0000\u0001\f\u0001\u0004\u0003\u0000\u0001\n\u0001\u000b\u0001\b\u0001\u0000\u0001\r\u0001\u0005\u0001\u0003\u0001\u0007\n\u0003\u0001\u0005\u0001\u0000\u0001\u0013\u0001\u0000\u0001\u0014\u0001\u0000\u0001\u000e\u0012\u0004\u0001\t\u0001\u0000\u0001\t\u0001\u0000\u0001\u0004\u0001\u0000\u0001\u0010\u0001\u0004\u0001\u0016\u0001\u0018\u0001\u0019\u0003\u0004\u0001\u001b\u0002\u0004\u0001\u001a\u0001\u0012\u0001\u0004\u0001\u0017\u0001\u000f\u0001\u0004\u0001\u0011\u0001\u0004\u0001\u001c\u0006\u0004\u0001\u0015\u0001\u0000\u0001\u0006\u0007\u0000\u0001\u001d\u0004\u0000\u0004\u0004\u0004\u0000\u0001\u0004\n\u0000\u0001\u0004\u0002\u0000\u0007\u0004\u0001\u0000\u0002\u0004\u0004\u0000\u0004\u0004\u0006\u0000\u0005\u0004\u0007\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0005\u0004\u0001\u0000\u0002\u0004\u0006\u0000\u0001\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0004\u0004\u0001\u0000\u000b\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0006\u0004\u0001\u0000\u0007\u0004\u0001\u0000\u0001\u0004\r\u0000\u0004\u0004\b\u0000\u0001\u0004\n\u0000\u0006\u0004\u0001\u0000\u0001\u0004\u0007\u0000\u0002\u0004\u0003\u0000\u0003\u0004\u0002\u0000\u0002\u0004\u0001\u0000\u0006\u0004\u0005\u0000\t\u0004\u0006\u0000\u0002\u0004\u0006\u0000\u0001\u0004\u0003\u0000\u0001\u0004\u000b\u0000\u0006\u0004\u0003\u0000\u0001\u0004\u0002\u0000\u0005\u0004\u0002\u0000\u0002\u0004\u0002\u0000\u0006\u0004\u0001\u0000\u0001\u0004\u0003\u0000\u0002\u0004\u0004\u0000\u0002\u0004\u0001\u0000\u0005\u0004\u0004\u0000\u0003\u0004\u0004\u0000\u0002\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0002\u0004\u0002\u0000\u0004\u0004\u0001\u0000\u0001\u0004\u0003\u0000\u0003\u0004\u0003\u0000\u0001\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0003\u0004\u0003\u0000\u0001\u0004\u0001\u0000\u0006\u0004\u0003\u0000\u0003\u0004\u0001\u0000\u0004\u0004\u0003\u0000\u0002\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0002\u0004\u0003\u0000\u0002\u0004\u0003\u0000\u0004\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0002\u0004\u0005\u0000\u0003\u0004\u0002\u0000\u0001\u0004\u0002\u0000\u0001\u0004\u0001\u0000\u0002\u0004\u0005\u0000\u0002\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0002\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0001\u0004\u0003\u0000\u0003\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0002\u0004\u0001\u0000\b\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0001\u0004\u0003\u0000\u0002\u0004\u0001\u0000\u0001\u0004\u0004\u0000\u0006\u0004\u0001\u0000\u0005\u0004\u0001\u0000\u0001\u0004\b\u0000\u0005\u0004\u0001\u0000\u0004\u0004\u0001\u0000\u0004\u0004\u0003\u0000\u0002\u0004\u0002\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0002\u0000\u0003\u0004\u0001\u0000\u0006\u0004\u0002\u0000\u0002\u0004\u0002\u001d\b\u0000\u0001\u0004\u0004\u0000\u0001\u0004\u0001\u0000\u0005\u0004\u0002\u0000\u0001\u0004\u0001\u0000\u0004\u0004\u0001\u0000\u0003\u0004\u0002\u0000\u0006\u0004\u0004\u0000\u0001\u0004\u0004\u0000\u0004\u0004\u0003\u0000\u0002\u0004\u0004\u0000\u0001\u0004\u0002\u0000\u0004\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0001\u0004\u0003\u0000\u0003\u0004\u0003\u0000\u0006\u0004\u0006\u0000\u0001\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0005\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0002\u0004\u0003\u0000\u0001\u0004\u0002\u0000\u0001\u0004\u0001\u0000\u0004\u0004\u0005\u0000\u0001\u0004\u0002\u0000\u0002\u0004\u0001\u0000\u0002\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0006\u0004\u0002\u0000\u0005\u0004\u0001\u0000\u0004\u0004\u0002\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0005\u0000\u0001\u0004\u0001\u0000\u0001\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0003\u0004\u0001\u0000\u0003\u0004");
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
        zzUnpackAction("\u000b\u0000\u0003\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0002\u0002\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\n\u0001\u0010\u0001\u0001\u0001\u0011\u0001\u0012\u0002\u0011\u0001\u0000\u0001\u0013\u0001\u0000\u0006\u0014\u0001\u0015\n\u0014\u0001\u0016\u0001\u0014\u0002\u0017\u0002\u0014", 0, iArr);
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
        zzUnpackRowMap("\u0000\u0000\u0000\u001f\u0000>\u0000]\u0000|\u0000\u009b\u0000º\u0000Ù\u0000ø\u0000ė\u0000Ķ\u0000ŕ\u0000Ŵ\u0000Ɠ\u0000ŕ\u0000Ʋ\u0000ŕ\u0000Ɠ\u0000Ǒ\u0000ŕ\u0000ǰ\u0000ȏ\u0000Ȯ\u0000ɍ\u0000ɬ\u0000ŕ\u0000ŕ\u0000ŕ\u0000ŕ\u0000ŕ\u0000ʋ\u0000ŕ\u0000ʪ\u0000ŕ\u0000ˉ\u0000ˉ\u0000Ɠ\u0000˨\u0000ŕ\u0000Ɠ\u0000̇\u0000̦\u0000ͅ\u0000ͤ\u0000\u0383\u0000\u03a2\u0000ŕ\u0000ρ\u0000Ϡ\u0000Ͽ\u0000О\u0000н\u0000ќ\u0000ѻ\u0000Қ\u0000ҹ\u0000Ә\u0000ͅ\u0000ӷ\u0000̇\u0000ͅ\u0000Ԗ\u0000Ե", 0, iArr);
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
        int[] iArr = new int[1364];
        zzUnpackTrans("\u0007\f\u0001\r\u0001\u000e\u0016\f\u0001\u000f\u0002\u0010\u0003\u000f\u0001\u0011\u0001\u000f\u0001\u0012\u0005\u000f\u0001\u0013\u0006\u000f\u0001\u0014\u0007\u000f\u0001\f\u0001\u0010\u0001\u000f\u0001\u0010\u0001\u0015\u0003\u000f\u0001\u0011\u0001\u000f\u0001\u0012\f\u000f\u0001\u0014\u0007\u000f\u0001\f\u0001\u0015\u0001\f\u0002\u0016\u0003\f\u0001\u0011\u0001\f\u0001\u000e\u0015\f\u0001\u0016\u0001\f\u0002\u0017\u0005\f\u0001\u000e\u0015\f\u0001\u0017\u0001\u000f\u0002\u0018\u0002\u0019\u0001\u000f\u0001\u0011\u0001\u000f\u0001\u0012\u0001\u0019\u0001\u001a\u0001\u000f\u0001\u001b\u0001\u001c\u0001\u000f\u0004\u0019\u0001\u001d\u0002\u000f\u0007\u0019\u0001\f\u0001\u0018\u0001\f\u0002\u0010\u0002\u0019\u0003\f\u0001\u000e\u0001\u0019\u0001\f\u0001\u001e\u0001\f\u0001\u001c\u0001\f\u0004\u0019\u0003\f\u0007\u0019\u0001\f\u0001\u0010\u0004\f\u0001\u001f\u0003\f\u0001\u000e\u0006\f\u0004\u001f\u0001\f\u0001 \u0001\f\u0007\u001f\n\f\u0001\u000e\u0005\f\u0001!\u0010\f\u0001\"\u0001#\u0001$\u0003\"\u0001\u0011\u0001\"\u0001%\f\"\u0001\u0014\u0007\"\u0001\f\u0001#\u0001\"\u0001#\u0001$\u0003\"\u0001\u0011\u0001\"\u0001%\u0014\"\u0001\f\u0001#'\u0000\u0001&\u001d\u0000\u0001'\u0001(\u0017\u0000\u0002\u0010\u001b\u0000\u0001\u0010\u0001)\u0002\u0000\f)\u0001*\u000e)\u0003\u0000\u0001\u0015\u001b\u0000\u0001\u0015\u0001\u0000\u0002\u0016\u001b\u0000\u0001\u0016\u0001\u0000\u0002\u0017\u001b\u0000\u0001\u0017\u0001\u0000\u0002\u0018\u001b\u0000\u0001\u0018\u0003\u0000\u0002\u0019\u0004\u0000\u0001\u0019\u0005\u0000\u0004\u0019\u0003\u0000\u0007\u0019\u0005\u0000\u0003\u001f\t\u0000\u0004\u001f\u0003\u0000\u0007\u001f\u0002\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\b+\u0001,\u0006+\u0001-\u0003+\u0001.\u0003+\u0002\u0000\u0002#\u001b\u0000\u0001#\b\u0000\u0001/\u0016\u0000\u0001)\u0002\u0000\u001b)\u0001\u0000\u0001)\u0002\u0000\r)\u00010\r)\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0017+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\t+\u00011\r+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0010+\u00012\u0006+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0014+\u00013\u0002+\u0001\u0000\u0001)\u0002\u0000\u000e)\u00014\f)\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\n+\u00015\f+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0011+\u00016\u0005+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0015+\u00017\u0001+\u0001\u0000\u0001)\u0002\u0000\r)\u00018\r)\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\t+\u00019\r+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0012+\u0001:\u0004+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0012+\u0001;\u0004+\u0001\u0000\u0001)\u0002\u0000\u000f)\u0001<\u000b)\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u000b+\u0001=\u000b+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\n+\u0001>\f+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\t+\u0001?\r+\u0001\u0000\u0001+\u0002\u0000\u0003+\u0001\u0000\u0013+\u0001:\u0003+\u0001\u0000", 0, iArr);
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
        zzUnpackAttribute("\u000b\u0000\u0001\t\u0002\u0001\u0001\t\u0001\u0001\u0001\t\u0002\u0001\u0001\t\u0005\u0001\u0005\t\u0001\u0001\u0001\t\u0001\u0001\u0001\t\u0003\u0001\u0001\u0000\u0001\t\u0001\u0000\u0006\u0001\u0001\t\u0010\u0001", 0, iArr);
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
    public _JavaDocLexer(boolean z, DocCommentTokenTypes docCommentTokenTypes) {
        this(null);
        this.myJdk15Enabled = z;
        this.myTokenTypes = docCommentTokenTypes;
    }

    public boolean checkAhead(char c) {
        return this.zzMarkedPos < this.zzBuffer.length() && this.zzBuffer.charAt(this.zzMarkedPos) == c;
    }

    public void goTo(int i) {
        this.zzStartRead = i;
        this.zzMarkedPos = i;
        this.zzCurrentPos = i;
        this.zzAtEOF = false;
    }

    _JavaDocLexer(Reader reader) {
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

    @Override // org.jetbrains.kotlin.com.intellij.lexer.FlexLexer
    public IElementType advance() throws IOException {
        int iCodePointAt;
        int i = this.zzEndRead;
        CharSequence charSequence = this.zzBuffer;
        int[] iArr = ZZ_TRANS;
        int[] iArr2 = ZZ_ROWMAP;
        int[] iArr3 = ZZ_ATTRIBUTE;
        while (true) {
            int iCharCount = this.zzMarkedPos;
            this.zzStartRead = iCharCount;
            this.zzCurrentPos = iCharCount;
            int i2 = ZZ_LEXSTATE[this.zzLexicalState];
            this.zzState = i2;
            int i3 = (iArr3[i2] & 1) == 1 ? i2 : -1;
            while (true) {
                if (iCharCount < i) {
                    iCodePointAt = Character.codePointAt(charSequence, iCharCount);
                    iCharCount += Character.charCount(iCodePointAt);
                } else {
                    if (!this.zzAtEOF) {
                        this.zzCurrentPos = iCharCount;
                        this.zzMarkedPos = iCharCount;
                        boolean zZzRefill = zzRefill();
                        int i4 = this.zzCurrentPos;
                        iCharCount = this.zzMarkedPos;
                        CharSequence charSequence2 = this.zzBuffer;
                        int i5 = this.zzEndRead;
                        if (zZzRefill) {
                            charSequence = charSequence2;
                            i = i5;
                        } else {
                            int iCodePointAt2 = Character.codePointAt(charSequence2, i4);
                            int iCharCount2 = i4 + Character.charCount(iCodePointAt2);
                            iCodePointAt = iCodePointAt2;
                            i = i5;
                            iCharCount = iCharCount2;
                            charSequence = charSequence2;
                        }
                    }
                    iCodePointAt = -1;
                }
                int i6 = iArr[iArr2[this.zzState] + ZZ_CMAP(iCodePointAt)];
                if (i6 != -1) {
                    this.zzState = i6;
                    int i7 = iArr3[i6];
                    if ((i7 & 1) == 1) {
                        if ((i7 & 8) == 8) {
                            iCharCount = iCharCount;
                            i3 = i6;
                        } else {
                            iCharCount = iCharCount;
                            i3 = i6;
                        }
                    }
                }
            }
            this.zzMarkedPos = iCharCount;
            if (iCodePointAt == -1 && this.zzStartRead == this.zzCurrentPos) {
                this.zzAtEOF = true;
                return null;
            }
            if (i3 >= 0) {
                i3 = ZZ_ACTION[i3];
            }
            switch (i3) {
                case 1:
                    return this.myTokenTypes.badCharacter();
                case 2:
                    yybegin(4);
                    return this.myTokenTypes.commentData();
                case 3:
                    return this.myTokenTypes.space();
                case 4:
                    yybegin(4);
                    return this.myTokenTypes.inlineTagEnd();
                case 5:
                    if (checkAhead('@')) {
                        yybegin(16);
                        return this.myTokenTypes.inlineTagStart();
                    }
                    yybegin(4);
                    return this.myTokenTypes.inlineTagStart();
                case 6:
                    return this.myTokenTypes.commentData();
                case 7:
                    if (checkAhead('<') || checkAhead('\"') || checkAhead('{')) {
                        yybegin(4);
                    } else {
                        yybegin(10);
                    }
                    return this.myTokenTypes.space();
                case 8:
                    yybegin(10);
                    return this.myTokenTypes.space();
                case 9:
                    yybegin(4);
                    return this.myTokenTypes.space();
                case 10:
                    return this.myTokenTypes.tagValueToken();
                case 11:
                    yybegin(12);
                    return this.myTokenTypes.tagValueLParen();
                case 12:
                    return this.myTokenTypes.tagValueSharp();
                case 13:
                    return this.myTokenTypes.tagValueComma();
                case 14:
                    if (this.myJdk15Enabled) {
                        yybegin(14);
                        return this.myTokenTypes.tagValueLT();
                    }
                    yybegin(4);
                    return this.myTokenTypes.commentData();
                case 15:
                    yybegin(10);
                    return this.myTokenTypes.tagValueRParen();
                case 16:
                    yybegin(4);
                    return this.myTokenTypes.tagValueGT();
                case 17:
                    yybegin(18);
                    return this.myTokenTypes.commentData();
                case 18:
                    yybegin(18);
                    return this.myTokenTypes.space();
                case 19:
                    return this.myTokenTypes.commentEnd();
                case 20:
                    yybegin(6);
                    return this.myTokenTypes.tagName();
                case 21:
                    yybegin(2);
                    return this.myTokenTypes.commentStart();
                case 22:
                    yybegin(20);
                    return this.myTokenTypes.tagName();
                case 23:
                    yybegin(8);
                    return this.myTokenTypes.tagName();
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
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                    break;
                default:
                    zzScanError(1);
                    break;
            }
        }
    }
}
