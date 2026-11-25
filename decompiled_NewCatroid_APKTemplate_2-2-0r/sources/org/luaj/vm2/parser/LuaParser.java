package org.luaj.vm2.parser;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.google.android.gms.cast.CastStatusCodes;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.Block;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.FuncArgs;
import org.luaj.vm2.ast.FuncBody;
import org.luaj.vm2.ast.FuncName;
import org.luaj.vm2.ast.Name;
import org.luaj.vm2.ast.ParList;
import org.luaj.vm2.ast.Stat;
import org.luaj.vm2.ast.Str;
import org.luaj.vm2.ast.SyntaxElement;
import org.luaj.vm2.ast.TableConstructor;
import org.luaj.vm2.ast.TableField;

/* JADX INFO: loaded from: classes6.dex */
public class LuaParser implements LuaParserConstants {
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private static int[] jj_la1_2;
    private final JJCalls[] jj_2_rtns;
    private int jj_endpos;
    private List jj_expentries;
    private int[] jj_expentry;
    private int jj_gc;
    private int jj_gen;
    SimpleCharStream jj_input_stream;
    private int jj_kind;
    private int jj_la;
    private final int[] jj_la1;
    private Token jj_lastpos;
    private int[] jj_lasttokens;
    private final LookaheadSuccess jj_ls;
    public Token jj_nt;
    private int jj_ntk;
    private boolean jj_rescan;
    private Token jj_scanpos;
    public Token token;
    public LuaParserTokenManager token_source;

    static final class JJCalls {
        int arg;
        Token first;
        int gen;
        JJCalls next;

        JJCalls() {
        }
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }
    }

    static {
        LuaValue.valueOf(true);
        jj_la1_init_0();
        jj_la1_init_1();
        jj_la1_init_2();
    }

    public LuaParser(InputStream inputStream) {
        this(inputStream, null);
    }

    public LuaParser(InputStream inputStream, String str) {
        this.jj_la1 = new int[34];
        this.jj_2_rtns = new JJCalls[7];
        int i = 0;
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        try {
            this.jj_input_stream = new SimpleCharStream(inputStream, str, 1, 1);
            this.token_source = new LuaParserTokenManager(this.jj_input_stream);
            this.token = new Token();
            this.jj_ntk = -1;
            this.jj_gen = 0;
            for (int i2 = 0; i2 < 34; i2++) {
                this.jj_la1[i2] = -1;
            }
            while (true) {
                JJCalls[] jJCallsArr = this.jj_2_rtns;
                if (i >= jJCallsArr.length) {
                    return;
                }
                jJCallsArr[i] = new JJCalls();
                i++;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public LuaParser(Reader reader) {
        this.jj_la1 = new int[34];
        this.jj_2_rtns = new JJCalls[7];
        int i = 0;
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.jj_input_stream = new SimpleCharStream(reader, 1, 1);
        this.token_source = new LuaParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 34; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public LuaParser(LuaParserTokenManager luaParserTokenManager) {
        this.jj_la1 = new int[34];
        this.jj_2_rtns = new JJCalls[7];
        int i = 0;
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new ArrayList();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.token_source = luaParserTokenManager;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 34; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    private void L(SyntaxElement syntaxElement, long j) {
        syntaxElement.beginLine = (int) (j >> 32);
        syntaxElement.beginColumn = (short) j;
        syntaxElement.endLine = this.token.endLine;
        syntaxElement.endColumn = (short) this.token.endColumn;
    }

    private void L(SyntaxElement syntaxElement, Token token) {
        syntaxElement.beginLine = token.beginLine;
        syntaxElement.beginColumn = (short) token.beginColumn;
        syntaxElement.endLine = this.token.endLine;
        syntaxElement.endColumn = (short) this.token.endColumn;
    }

    private long LineInfo() {
        return (((long) this.jj_input_stream.getBeginLine()) << 32) | ((long) this.jj_input_stream.getBeginColumn());
    }

    private static Exp.FuncCall assertfunccall(Exp.PrimaryExp primaryExp) throws ParseException {
        if (primaryExp.isfunccall()) {
            return (Exp.FuncCall) primaryExp;
        }
        throw new ParseException("expected function call");
    }

    private static Exp.VarExp assertvarexp(Exp.PrimaryExp primaryExp) throws ParseException {
        if (primaryExp.isvarexp()) {
            return (Exp.VarExp) primaryExp;
        }
        throw new ParseException("expected variable");
    }

    private boolean jj_2_1(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_1();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(0, i);
        }
    }

    private boolean jj_2_2(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_2();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(1, i);
        }
    }

    private boolean jj_2_3(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_3();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(2, i);
        }
    }

    private boolean jj_2_4(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_4();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(3, i);
        }
    }

    private boolean jj_2_5(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_5();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(4, i);
        }
    }

    private boolean jj_2_6(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_6();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(5, i);
        }
    }

    private boolean jj_2_7(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_7();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(6, i);
        }
    }

    private boolean jj_3R_10() {
        Token token = this.jj_scanpos;
        if (!jj_3R_15()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_16()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_17()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_18();
    }

    private boolean jj_3R_11() {
        Token token = this.jj_scanpos;
        if (!jj_3R_19()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_20()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_21()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_22()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_23()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_24()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_25()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_26()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_27()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_28()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_29()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_30()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_31()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_32()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_33();
    }

    private boolean jj_3R_12() {
        Token token = this.jj_scanpos;
        if (!jj_3R_34()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_35();
    }

    private boolean jj_3R_13() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(72)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(70);
    }

    private boolean jj_3R_14() {
        Token token = this.jj_scanpos;
        if (!jj_3R_36()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3_7()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_37();
    }

    private boolean jj_3R_15() {
        return jj_scan_token(73) || jj_scan_token(51);
    }

    private boolean jj_3R_16() {
        return jj_scan_token(77) || jj_3R_12();
    }

    private boolean jj_3R_17() {
        return jj_scan_token(74) || jj_scan_token(51);
    }

    private boolean jj_3R_18() {
        return jj_3R_38();
    }

    private boolean jj_3R_19() {
        return jj_scan_token(82);
    }

    private boolean jj_3R_20() {
        return jj_scan_token(83);
    }

    private boolean jj_3R_21() {
        return jj_scan_token(84);
    }

    private boolean jj_3R_22() {
        return jj_scan_token(85);
    }

    private boolean jj_3R_23() {
        return jj_scan_token(86);
    }

    private boolean jj_3R_24() {
        return jj_scan_token(87);
    }

    private boolean jj_3R_25() {
        return jj_scan_token(88);
    }

    private boolean jj_3R_26() {
        return jj_scan_token(89);
    }

    private boolean jj_3R_27() {
        return jj_scan_token(90);
    }

    private boolean jj_3R_28() {
        return jj_scan_token(91);
    }

    private boolean jj_3R_29() {
        return jj_scan_token(92);
    }

    private boolean jj_3R_30() {
        return jj_scan_token(93);
    }

    private boolean jj_3R_31() {
        return jj_scan_token(94);
    }

    private boolean jj_3R_32() {
        return jj_scan_token(29);
    }

    private boolean jj_3R_33() {
        return jj_scan_token(44);
    }

    private boolean jj_3R_34() {
        return jj_3R_39();
    }

    private boolean jj_3R_35() {
        return jj_3R_40();
    }

    private boolean jj_3R_36() {
        return jj_scan_token(77);
    }

    private boolean jj_3R_37() {
        return jj_3R_12();
    }

    private boolean jj_3R_38() {
        Token token = this.jj_scanpos;
        if (!jj_3R_41()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_42()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_43();
    }

    private boolean jj_3R_39() {
        Token token = this.jj_scanpos;
        if (!jj_3R_44()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_45()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_46()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_47()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_48()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_49()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_50()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_51()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_52();
    }

    private boolean jj_3R_40() {
        Token token = this.jj_scanpos;
        if (!jj_3R_53()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_54()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_55();
    }

    private boolean jj_3R_41() {
        if (jj_scan_token(75)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_56()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(76);
    }

    private boolean jj_3R_42() {
        return jj_3R_57();
    }

    private boolean jj_3R_43() {
        return jj_3R_58();
    }

    private boolean jj_3R_44() {
        return jj_scan_token(42);
    }

    private boolean jj_3R_45() {
        return jj_scan_token(48);
    }

    private boolean jj_3R_46() {
        return jj_scan_token(35);
    }

    private boolean jj_3R_47() {
        return jj_scan_token(52);
    }

    private boolean jj_3R_48() {
        return jj_3R_58();
    }

    private boolean jj_3R_49() {
        return jj_scan_token(79);
    }

    private boolean jj_3R_50() {
        return jj_3R_57();
    }

    private boolean jj_3R_51() {
        return jj_3R_59();
    }

    private boolean jj_3R_52() {
        return jj_3R_60();
    }

    private boolean jj_3R_53() {
        return jj_scan_token(83);
    }

    private boolean jj_3R_54() {
        return jj_scan_token(43);
    }

    private boolean jj_3R_55() {
        return jj_scan_token(69);
    }

    private boolean jj_3R_56() {
        return jj_3R_61();
    }

    private boolean jj_3R_57() {
        if (jj_scan_token(80)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_62()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(81);
    }

    private boolean jj_3R_58() {
        Token token = this.jj_scanpos;
        if (!jj_3R_63()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_64()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_65()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_66()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_67()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_68()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_69();
    }

    private boolean jj_3R_59() {
        return jj_scan_token(37);
    }

    private boolean jj_3R_60() {
        return jj_3R_70();
    }

    private boolean jj_3R_61() {
        return jj_3R_12();
    }

    private boolean jj_3R_62() {
        return jj_3R_71();
    }

    private boolean jj_3R_63() {
        return jj_scan_token(61);
    }

    private boolean jj_3R_64() {
        return jj_scan_token(62);
    }

    private boolean jj_3R_65() {
        return jj_scan_token(23);
    }

    private boolean jj_3R_66() {
        return jj_scan_token(24);
    }

    private boolean jj_3R_67() {
        return jj_scan_token(25);
    }

    private boolean jj_3R_68() {
        return jj_scan_token(26);
    }

    private boolean jj_3R_69() {
        return jj_scan_token(27);
    }

    private boolean jj_3R_70() {
        Token token = this.jj_scanpos;
        if (!jj_3R_72()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_73();
    }

    private boolean jj_3R_71() {
        return jj_3R_14();
    }

    private boolean jj_3R_72() {
        return jj_scan_token(51);
    }

    private boolean jj_3R_73() {
        return jj_scan_token(75);
    }

    private boolean jj_3_1() {
        return jj_scan_token(36) || jj_scan_token(51) || jj_scan_token(71);
    }

    private boolean jj_3_2() {
        return jj_scan_token(41) || jj_scan_token(37);
    }

    private boolean jj_3_3() {
        return jj_3R_10();
    }

    private boolean jj_3_4() {
        return jj_scan_token(72) || jj_scan_token(51);
    }

    private boolean jj_3_5() {
        return jj_3R_11() || jj_3R_12();
    }

    private boolean jj_3_6() {
        return jj_3R_13() || jj_3R_14();
    }

    private boolean jj_3_7() {
        return jj_scan_token(51) || jj_scan_token(71);
    }

    private void jj_add_error_token(int i, int i2) {
        if (i2 >= 100) {
            return;
        }
        int i3 = this.jj_endpos;
        if (i2 == i3 + 1) {
            int[] iArr = this.jj_lasttokens;
            this.jj_endpos = i3 + 1;
            iArr[i3] = i;
            return;
        }
        if (i3 != 0) {
            this.jj_expentry = new int[i3];
            for (int i4 = 0; i4 < this.jj_endpos; i4++) {
                this.jj_expentry[i4] = this.jj_lasttokens[i4];
            }
            loop1: for (int[] iArr2 : this.jj_expentries) {
                if (iArr2.length == this.jj_expentry.length) {
                    int i5 = 0;
                    while (true) {
                        int[] iArr3 = this.jj_expentry;
                        if (i5 >= iArr3.length) {
                            this.jj_expentries.add(iArr3);
                            break loop1;
                        } else if (iArr2[i5] != iArr3[i5]) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                }
            }
            if (i2 != 0) {
                int[] iArr4 = this.jj_lasttokens;
                this.jj_endpos = i2;
                iArr4[i2 - 1] = i;
            }
        }
    }

    private Token jj_consume_token(int i) throws ParseException {
        Token token = this.token;
        if (token.next != null) {
            this.token = this.token.next;
        } else {
            Token token2 = this.token;
            Token nextToken = this.token_source.getNextToken();
            token2.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        if (this.token.kind != i) {
            this.token = token;
            this.jj_kind = i;
            throw generateParseException();
        }
        this.jj_gen++;
        int i2 = this.jj_gc + 1;
        this.jj_gc = i2;
        if (i2 > 100) {
            int i3 = 0;
            this.jj_gc = 0;
            while (true) {
                JJCalls[] jJCallsArr = this.jj_2_rtns;
                if (i3 >= jJCallsArr.length) {
                    break;
                }
                for (JJCalls jJCalls = jJCallsArr[i3]; jJCalls != null; jJCalls = jJCalls.next) {
                    if (jJCalls.gen < this.jj_gen) {
                        jJCalls.first = null;
                    }
                }
                i3++;
            }
        }
        return this.token;
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{0, -1073741824, 0, 0, 0, -1073741824, 0, 0, 0, 0, 260046848, 0, 0, 0, 0, 0, 0, 260046848, 260046848, 260046848, 0, 260046848, 260046848, 260046848, 0, 0, 0, 260046848, 0, 0, 260046848, 0, 536870912, 0};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[]{0, 803568, 8192, 0, 0, 278720, 48, 524800, 2, 1, 1612254248, 0, 0, 0, 0, 0, 524288, 1610612736, 1612254248, 1610612736, 0, 1612252200, 1610612736, 1612254248, 524288, 0, 524288, 1612254248, 0, 0, 1612254248, 0, 4096, 2048};
    }

    private static void jj_la1_init_2() {
        jj_la1_2 = new int[]{32, CastStatusCodes.ERROR_NO_CAST_CONFIGURATION, 0, 256, 128, 66, 0, 2048, 0, 0, 624672, 64, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, 256, 512, 1024, 2048, 77312, 624672, 67584, 256, 100352, 0, 624672, 32768, 256, 32768, 632864, 320, 8192, 624672, 320, 2147221504, 524320};
    }

    private int jj_ntk() {
        int i;
        Token token = this.token.next;
        this.jj_nt = token;
        if (token == null) {
            Token token2 = this.token;
            Token nextToken = this.token_source.getNextToken();
            token2.next = nextToken;
            i = nextToken.kind;
        } else {
            i = token.kind;
        }
        this.jj_ntk = i;
        return i;
    }

    private void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 7; i++) {
            try {
                JJCalls jJCalls = this.jj_2_rtns[i];
                do {
                    if (jJCalls.gen > this.jj_gen) {
                        this.jj_la = jJCalls.arg;
                        Token token = jJCalls.first;
                        this.jj_scanpos = token;
                        this.jj_lastpos = token;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                            case 2:
                                jj_3_3();
                                break;
                            case 3:
                                jj_3_4();
                                break;
                            case 4:
                                jj_3_5();
                                break;
                            case 5:
                                jj_3_6();
                                break;
                            case 6:
                                jj_3_7();
                                break;
                        }
                    }
                    jJCalls = jJCalls.next;
                } while (jJCalls != null);
            } catch (LookaheadSuccess unused) {
            }
        }
        this.jj_rescan = false;
    }

    private void jj_save(int i, int i2) {
        JJCalls jJCalls = this.jj_2_rtns[i];
        while (jJCalls.gen > this.jj_gen) {
            if (jJCalls.next == null) {
                JJCalls jJCalls2 = new JJCalls();
                jJCalls.next = jJCalls2;
                jJCalls = jJCalls2;
                break;
            }
            jJCalls = jJCalls.next;
        }
        jJCalls.gen = (this.jj_gen + i2) - this.jj_la;
        jJCalls.first = this.token;
        jJCalls.arg = i2;
    }

    private boolean jj_scan_token(int i) {
        Token token = this.jj_scanpos;
        if (token == this.jj_lastpos) {
            this.jj_la--;
            if (token.next == null) {
                Token token2 = this.jj_scanpos;
                Token nextToken = this.token_source.getNextToken();
                token2.next = nextToken;
                this.jj_scanpos = nextToken;
                this.jj_lastpos = nextToken;
            } else {
                Token token3 = this.jj_scanpos.next;
                this.jj_scanpos = token3;
                this.jj_lastpos = token3;
            }
        } else {
            this.jj_scanpos = token.next;
        }
        if (this.jj_rescan) {
            Token token4 = this.token;
            int i2 = 0;
            while (token4 != null && token4 != this.jj_scanpos) {
                i2++;
                token4 = token4.next;
            }
            if (token4 != null) {
                jj_add_error_token(i, i2);
            }
        }
        if (this.jj_scanpos.kind != i) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public static void main(String[] strArr) throws ParseException {
        new LuaParser(System.in).Chunk();
    }

    public final Stat Assign(Exp.VarExp varExp) throws ParseException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(varExp);
        long jLineInfo = LineInfo();
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 72) {
                this.jj_la1[13] = this.jj_gen;
                jj_consume_token(71);
                Stat statAssignment = Stat.assignment(arrayList, ExpList());
                L(statAssignment, jLineInfo);
                return statAssignment;
            }
            jj_consume_token(72);
            arrayList.add(VarExp());
        }
    }

    public final int Binop() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 29) {
            jj_consume_token(29);
            return 60;
        }
        if (iJj_ntk == 44) {
            jj_consume_token(44);
            return 59;
        }
        switch (iJj_ntk) {
            case 82:
                jj_consume_token(82);
                return 13;
            case 83:
                jj_consume_token(83);
                return 14;
            case 84:
                jj_consume_token(84);
                return 15;
            case 85:
                jj_consume_token(85);
                return 16;
            case 86:
                jj_consume_token(86);
                return 18;
            case 87:
                jj_consume_token(87);
                return 17;
            case 88:
                jj_consume_token(88);
                return 22;
            case 89:
                jj_consume_token(89);
                return 25;
            case 90:
                jj_consume_token(90);
                return 26;
            case 91:
                jj_consume_token(91);
                return 63;
            case 92:
                jj_consume_token(92);
                return 62;
            case 93:
                jj_consume_token(93);
                return 24;
            case 94:
                jj_consume_token(94);
                return 61;
            default:
                this.jj_la1[32] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final Block Block() throws ParseException {
        Block block = new Block();
        long jLineInfo = LineInfo();
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 30 && iJj_ntk != 31 && iJj_ntk != 41 && iJj_ntk != 46 && iJj_ntk != 65 && iJj_ntk != 70 && iJj_ntk != 75 && iJj_ntk != 50 && iJj_ntk != 51) {
                switch (iJj_ntk) {
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                        break;
                    default:
                        this.jj_la1[1] = this.jj_gen;
                        int iJj_ntk2 = this.jj_ntk;
                        if (iJj_ntk2 == -1) {
                            iJj_ntk2 = jj_ntk();
                        }
                        if (iJj_ntk2 != 45) {
                            this.jj_la1[2] = this.jj_gen;
                        } else {
                            block.add(ReturnStat());
                        }
                        L(block, jLineInfo);
                        return block;
                }
            }
            block.add(Stat());
        }
    }

    public final Chunk Chunk() throws ParseException {
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 69) {
            this.jj_la1[0] = this.jj_gen;
        } else {
            jj_consume_token(69);
            this.token_source.SwitchTo(1);
        }
        Block Block = Block();
        jj_consume_token(0);
        Chunk chunk = new Chunk(Block);
        L(chunk, jLineInfo);
        return chunk;
    }

    public final Exp Exp() throws ParseException {
        Exp expSimpleExp;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 35 || iJj_ntk == 37 || iJj_ntk == 48) {
            expSimpleExp = SimpleExp();
        } else {
            if (iJj_ntk != 69) {
                if (iJj_ntk != 75) {
                    if (iJj_ntk != 83) {
                        if (iJj_ntk != 42) {
                            if (iJj_ntk != 43) {
                                if (iJj_ntk != 51 && iJj_ntk != 52 && iJj_ntk != 61 && iJj_ntk != 62 && iJj_ntk != 79 && iJj_ntk != 80) {
                                    switch (iJj_ntk) {
                                        case 23:
                                        case 24:
                                        case 25:
                                        case 26:
                                        case 27:
                                            break;
                                        default:
                                            this.jj_la1[23] = this.jj_gen;
                                            jj_consume_token(-1);
                                            throw new ParseException();
                                    }
                                }
                            }
                        }
                    }
                }
                expSimpleExp = SimpleExp();
            }
            expSimpleExp = Exp.unaryexp(Unop(), Exp());
        }
        while (jj_2_5(2)) {
            expSimpleExp = Exp.binaryexp(expSimpleExp, Binop(), Exp());
        }
        L(expSimpleExp, jLineInfo);
        return expSimpleExp;
    }

    public final List ExpList() throws ParseException {
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(Exp());
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 72) {
                this.jj_la1[20] = this.jj_gen;
                return arrayList;
            }
            jj_consume_token(72);
        }
    }

    public final Stat ExprStat() throws ParseException {
        Stat statAssign;
        long jLineInfo = LineInfo();
        Exp.PrimaryExp PrimaryExp = PrimaryExp();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 71 || iJj_ntk == 72) {
            statAssign = Assign(assertvarexp(PrimaryExp));
        } else {
            this.jj_la1[12] = this.jj_gen;
            statAssign = null;
        }
        if (statAssign == null) {
            statAssign = Stat.functioncall(assertfunccall(PrimaryExp));
        }
        L(statAssign, jLineInfo);
        return statAssign;
    }

    public final TableField Field() throws ParseException {
        TableField tableFieldKeyedField;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 77) {
            this.jj_la1[29] = this.jj_gen;
            if (jj_2_7(2)) {
                Token tokenJj_consume_token = jj_consume_token(51);
                jj_consume_token(71);
                tableFieldKeyedField = TableField.namedField(tokenJj_consume_token.image, Exp());
            } else {
                int iJj_ntk2 = this.jj_ntk;
                if (iJj_ntk2 == -1) {
                    iJj_ntk2 = jj_ntk();
                }
                if (iJj_ntk2 != 35 && iJj_ntk2 != 37 && iJj_ntk2 != 48 && iJj_ntk2 != 69 && iJj_ntk2 != 75 && iJj_ntk2 != 83 && iJj_ntk2 != 42 && iJj_ntk2 != 43 && iJj_ntk2 != 51 && iJj_ntk2 != 52 && iJj_ntk2 != 61 && iJj_ntk2 != 62 && iJj_ntk2 != 79 && iJj_ntk2 != 80) {
                    switch (iJj_ntk2) {
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            this.jj_la1[30] = this.jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                tableFieldKeyedField = TableField.listField(Exp());
            }
        } else {
            jj_consume_token(77);
            Exp Exp = Exp();
            jj_consume_token(78);
            jj_consume_token(71);
            tableFieldKeyedField = TableField.keyedField(Exp, Exp());
        }
        L(tableFieldKeyedField, jLineInfo);
        return tableFieldKeyedField;
    }

    public final List FieldList() throws ParseException {
        ArrayList arrayList = new ArrayList();
        while (true) {
            arrayList.add(Field());
            if (!jj_2_6(2)) {
                break;
            }
            FieldSep();
        }
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 70 || iJj_ntk == 72) {
            FieldSep();
        } else {
            this.jj_la1[28] = this.jj_gen;
        }
        return arrayList;
    }

    public final void FieldSep() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        int i = 70;
        if (iJj_ntk != 70) {
            i = 72;
            if (iJj_ntk != 72) {
                this.jj_la1[31] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        jj_consume_token(i);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0085  */
    /* JADX WARN: Code duplicated, block: B:49:0x0093  */
    public final FuncArgs FuncArgs() throws ParseException {
        FuncArgs funcArgsString;
        List listExpList;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 61 || iJj_ntk == 62) {
            funcArgsString = FuncArgs.string(Str());
        } else if (iJj_ntk == 75) {
            jj_consume_token(75);
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            if (iJj_ntk2 != 35 && iJj_ntk2 != 37 && iJj_ntk2 != 48 && iJj_ntk2 != 69 && iJj_ntk2 != 75 && iJj_ntk2 != 83 && iJj_ntk2 != 42 && iJj_ntk2 != 43 && iJj_ntk2 != 51 && iJj_ntk2 != 52 && iJj_ntk2 != 61 && iJj_ntk2 != 62 && iJj_ntk2 != 79 && iJj_ntk2 != 80) {
                switch (iJj_ntk2) {
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                        listExpList = ExpList();
                        break;
                    default:
                        this.jj_la1[18] = this.jj_gen;
                        listExpList = null;
                        break;
                }
            } else {
                listExpList = ExpList();
            }
            jj_consume_token(76);
            funcArgsString = FuncArgs.explist(listExpList);
        } else if (iJj_ntk != 80) {
            switch (iJj_ntk) {
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    funcArgsString = FuncArgs.string(Str());
                    break;
                default:
                    this.jj_la1[19] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        } else {
            funcArgsString = FuncArgs.tableconstructor(TableConstructor());
        }
        L(funcArgsString, jLineInfo);
        return funcArgsString;
    }

    public final FuncBody FuncBody() throws ParseException {
        ParList ParList;
        long jLineInfo = LineInfo();
        jj_consume_token(75);
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 51 || iJj_ntk == 79) {
            ParList = ParList();
        } else {
            this.jj_la1[24] = this.jj_gen;
            ParList = null;
        }
        jj_consume_token(76);
        Block Block = Block();
        jj_consume_token(34);
        FuncBody funcBody = new FuncBody(ParList, Block);
        L(funcBody, jLineInfo);
        return funcBody;
    }

    public final FuncName FuncName() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(51);
        FuncName funcName = new FuncName(tokenJj_consume_token.image);
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 73) {
                break;
            }
            jj_consume_token(73);
            tokenJj_consume_token = jj_consume_token(51);
            funcName.adddot(tokenJj_consume_token.image);
        }
        this.jj_la1[14] = this.jj_gen;
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 != 74) {
            this.jj_la1[15] = this.jj_gen;
        } else {
            jj_consume_token(74);
            tokenJj_consume_token = jj_consume_token(51);
            funcName.method = tokenJj_consume_token.image;
        }
        L(funcName, tokenJj_consume_token);
        return funcName;
    }

    public final FuncBody FunctionCall() throws ParseException {
        long jLineInfo = LineInfo();
        jj_consume_token(37);
        FuncBody FuncBody = FuncBody();
        L(FuncBody, jLineInfo);
        return FuncBody;
    }

    public final Stat IfThenElse() throws ParseException {
        jj_consume_token(39);
        Exp Exp = Exp();
        jj_consume_token(47);
        Block Block = Block();
        Block Block2 = null;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 33) {
                break;
            }
            jj_consume_token(33);
            Exp Exp2 = Exp();
            jj_consume_token(47);
            Block Block3 = Block();
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
            }
            arrayList.add(Exp2);
            arrayList2.add(Block3);
        }
        this.jj_la1[8] = this.jj_gen;
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 != 32) {
            this.jj_la1[9] = this.jj_gen;
        } else {
            jj_consume_token(32);
            Block2 = Block();
        }
        jj_consume_token(34);
        return Stat.ifthenelse(Exp, Block, arrayList, arrayList2, Block2);
    }

    public final Stat Label() throws ParseException {
        jj_consume_token(65);
        Token tokenJj_consume_token = jj_consume_token(51);
        jj_consume_token(65);
        return Stat.labelstat(tokenJj_consume_token.image);
    }

    public final List NameList() throws ParseException {
        ArrayList arrayList = new ArrayList();
        Name name2 = new Name(jj_consume_token(51).image);
        while (true) {
            arrayList.add(name2);
            if (!jj_2_4(2)) {
                return arrayList;
            }
            jj_consume_token(72);
            name2 = new Name(jj_consume_token(51).image);
        }
    }

    public final ParList ParList() throws ParseException {
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        boolean z = true;
        if (iJj_ntk != 51) {
            if (iJj_ntk != 79) {
                this.jj_la1[26] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            jj_consume_token(79);
            ParList parList = new ParList(null, true);
            L(parList, jLineInfo);
            return parList;
        }
        List listNameList = NameList();
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 != 72) {
            this.jj_la1[25] = this.jj_gen;
            z = false;
        } else {
            jj_consume_token(72);
            jj_consume_token(79);
        }
        ParList parList2 = new ParList(listNameList, z);
        L(parList2, jLineInfo);
        return parList2;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006e A[FALL_THROUGH] */
    public final Exp.PrimaryExp PostfixOp(Exp.PrimaryExp primaryExp) throws ParseException {
        Exp.PrimaryExp primaryExpFunctionop;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 61 || iJj_ntk == 62) {
            primaryExpFunctionop = Exp.functionop(primaryExp, FuncArgs());
        } else if (iJj_ntk == 77) {
            jj_consume_token(77);
            Exp Exp = Exp();
            jj_consume_token(78);
            primaryExpFunctionop = Exp.indexop(primaryExp, Exp);
        } else if (iJj_ntk != 80) {
            switch (iJj_ntk) {
                default:
                    switch (iJj_ntk) {
                        case 73:
                            jj_consume_token(73);
                            primaryExpFunctionop = Exp.fieldop(primaryExp, jj_consume_token(51).image);
                            break;
                        case 74:
                            jj_consume_token(74);
                            Token tokenJj_consume_token = jj_consume_token(51);
                            primaryExpFunctionop = Exp.methodop(primaryExp, tokenJj_consume_token.image, FuncArgs());
                            break;
                        case 75:
                            break;
                        default:
                            this.jj_la1[17] = this.jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    primaryExpFunctionop = Exp.functionop(primaryExp, FuncArgs());
                    break;
            }
        } else {
            primaryExpFunctionop = Exp.functionop(primaryExp, FuncArgs());
        }
        L(primaryExpFunctionop, jLineInfo);
        return primaryExpFunctionop;
    }

    public final Exp.PrimaryExp PrefixExp() throws ParseException {
        Exp.PrimaryExp primaryExpNameprefix;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 51) {
            primaryExpNameprefix = Exp.nameprefix(jj_consume_token(51).image);
        } else {
            if (iJj_ntk != 75) {
                this.jj_la1[16] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            jj_consume_token(75);
            Exp Exp = Exp();
            jj_consume_token(76);
            primaryExpNameprefix = Exp.parensprefix(Exp);
        }
        L(primaryExpNameprefix, jLineInfo);
        return primaryExpNameprefix;
    }

    public final Exp.PrimaryExp PrimaryExp() throws ParseException {
        long jLineInfo = LineInfo();
        Exp.PrimaryExp primaryExpPrefixExp = PrefixExp();
        while (jj_2_3(2)) {
            primaryExpPrefixExp = PostfixOp(primaryExpPrefixExp);
        }
        L(primaryExpPrefixExp, jLineInfo);
        return primaryExpPrefixExp;
    }

    public void ReInit(InputStream inputStream) {
        ReInit(inputStream, null);
    }

    public void ReInit(InputStream inputStream, String str) {
        try {
            this.jj_input_stream.ReInit(inputStream, str, 1, 1);
            this.token_source.ReInit(this.jj_input_stream);
            this.token = new Token();
            this.jj_ntk = -1;
            int i = 0;
            this.jj_gen = 0;
            for (int i2 = 0; i2 < 34; i2++) {
                this.jj_la1[i2] = -1;
            }
            while (true) {
                JJCalls[] jJCallsArr = this.jj_2_rtns;
                if (i >= jJCallsArr.length) {
                    return;
                }
                jJCallsArr[i] = new JJCalls();
                i++;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void ReInit(Reader reader) {
        this.jj_input_stream.ReInit(reader, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        int i = 0;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 34; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public void ReInit(LuaParserTokenManager luaParserTokenManager) {
        this.token_source = luaParserTokenManager;
        this.token = new Token();
        this.jj_ntk = -1;
        int i = 0;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 34; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0057  */
    public final Stat ReturnStat() throws ParseException {
        List listExpList;
        long jLineInfo = LineInfo();
        jj_consume_token(45);
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 35 && iJj_ntk != 37 && iJj_ntk != 48 && iJj_ntk != 69 && iJj_ntk != 75 && iJj_ntk != 83 && iJj_ntk != 42 && iJj_ntk != 43 && iJj_ntk != 51 && iJj_ntk != 52 && iJj_ntk != 61 && iJj_ntk != 62 && iJj_ntk != 79 && iJj_ntk != 80) {
            switch (iJj_ntk) {
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    listExpList = ExpList();
                    break;
                default:
                    this.jj_la1[10] = this.jj_gen;
                    listExpList = null;
                    break;
            }
        } else {
            listExpList = ExpList();
        }
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 != 70) {
            this.jj_la1[11] = this.jj_gen;
        } else {
            jj_consume_token(70);
        }
        Stat statReturnstat = Stat.returnstat(listExpList);
        L(statReturnstat, jLineInfo);
        return statReturnstat;
    }

    public final Exp SimpleExp() throws ParseException {
        LuaValue luaValueStr;
        Exp expConstant;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 35) {
            if (iJj_ntk == 37) {
                expConstant = Exp.anonymousfunction(FunctionCall());
            } else if (iJj_ntk == 42) {
                jj_consume_token(42);
                luaValueStr = LuaValue.NIL;
            } else if (iJj_ntk == 48) {
                jj_consume_token(48);
                luaValueStr = LuaValue.TRUE;
            } else {
                if (iJj_ntk == 75 || iJj_ntk == 51) {
                    return PrimaryExp();
                }
                if (iJj_ntk != 52) {
                    if (iJj_ntk != 61 && iJj_ntk != 62) {
                        if (iJj_ntk == 79) {
                            jj_consume_token(79);
                            expConstant = Exp.varargs();
                        } else if (iJj_ntk != 80) {
                            switch (iJj_ntk) {
                                case 23:
                                case 24:
                                case 25:
                                case 26:
                                case 27:
                                    break;
                                default:
                                    this.jj_la1[21] = this.jj_gen;
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                        } else {
                            expConstant = Exp.tableconstructor(TableConstructor());
                        }
                    }
                    luaValueStr = Str();
                } else {
                    expConstant = Exp.numberconstant(jj_consume_token(52).image);
                }
            }
            L(expConstant, jLineInfo);
            return expConstant;
        }
        jj_consume_token(35);
        luaValueStr = LuaValue.FALSE;
        expConstant = Exp.constant(luaValueStr);
        L(expConstant, jLineInfo);
        return expConstant;
    }

    public final Stat Stat() throws ParseException {
        Stat statBreakstat;
        long jLineInfo = LineInfo();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 30) {
            jj_consume_token(30);
            statBreakstat = Stat.breakstat();
        } else if (iJj_ntk == 31) {
            jj_consume_token(31);
            Block Block = Block();
            jj_consume_token(34);
            statBreakstat = Stat.block(Block);
        } else if (iJj_ntk == 38) {
            jj_consume_token(38);
            statBreakstat = Stat.gotostat(jj_consume_token(51).image);
        } else if (iJj_ntk == 39) {
            statBreakstat = IfThenElse();
        } else if (iJj_ntk == 46) {
            jj_consume_token(46);
            Block Block2 = Block();
            jj_consume_token(49);
            statBreakstat = Stat.repeatuntil(Block2, Exp());
        } else if (iJj_ntk == 50) {
            jj_consume_token(50);
            Exp Exp = Exp();
            jj_consume_token(31);
            Block Block3 = Block();
            jj_consume_token(34);
            statBreakstat = Stat.whiledo(Exp, Block3);
        } else if (iJj_ntk != 65) {
            List listExpList = null;
            Exp Exp2 = null;
            if (iJj_ntk == 70) {
                jj_consume_token(70);
                return null;
            }
            this.jj_la1[5] = this.jj_gen;
            if (jj_2_1(3)) {
                jj_consume_token(36);
                Token tokenJj_consume_token = jj_consume_token(51);
                jj_consume_token(71);
                Exp Exp3 = Exp();
                jj_consume_token(72);
                Exp Exp4 = Exp();
                int iJj_ntk2 = this.jj_ntk;
                if (iJj_ntk2 == -1) {
                    iJj_ntk2 = jj_ntk();
                }
                if (iJj_ntk2 != 72) {
                    this.jj_la1[3] = this.jj_gen;
                } else {
                    jj_consume_token(72);
                    Exp2 = Exp();
                }
                jj_consume_token(31);
                Block Block4 = Block();
                jj_consume_token(34);
                statBreakstat = Stat.fornumeric(tokenJj_consume_token.image, Exp3, Exp4, Exp2, Block4);
            } else {
                int iJj_ntk3 = this.jj_ntk;
                if (iJj_ntk3 == -1) {
                    iJj_ntk3 = jj_ntk();
                }
                if (iJj_ntk3 == 36) {
                    jj_consume_token(36);
                    List listNameList = NameList();
                    jj_consume_token(40);
                    List listExpList2 = ExpList();
                    jj_consume_token(31);
                    Block Block5 = Block();
                    jj_consume_token(34);
                    statBreakstat = Stat.forgeneric(listNameList, listExpList2, Block5);
                } else if (iJj_ntk3 != 37) {
                    this.jj_la1[6] = this.jj_gen;
                    if (jj_2_2(2)) {
                        jj_consume_token(41);
                        jj_consume_token(37);
                        Token tokenJj_consume_token2 = jj_consume_token(51);
                        statBreakstat = Stat.localfunctiondef(tokenJj_consume_token2.image, FuncBody());
                    } else {
                        int iJj_ntk4 = this.jj_ntk;
                        if (iJj_ntk4 == -1) {
                            iJj_ntk4 = jj_ntk();
                        }
                        if (iJj_ntk4 == 41) {
                            jj_consume_token(41);
                            List listNameList2 = NameList();
                            int iJj_ntk5 = this.jj_ntk;
                            if (iJj_ntk5 == -1) {
                                iJj_ntk5 = jj_ntk();
                            }
                            if (iJj_ntk5 != 71) {
                                this.jj_la1[4] = this.jj_gen;
                            } else {
                                jj_consume_token(71);
                                listExpList = ExpList();
                            }
                            statBreakstat = Stat.localassignment(listNameList2, listExpList);
                        } else {
                            if (iJj_ntk4 != 51 && iJj_ntk4 != 75) {
                                this.jj_la1[7] = this.jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                            }
                            statBreakstat = ExprStat();
                        }
                    }
                } else {
                    jj_consume_token(37);
                    statBreakstat = Stat.functiondef(FuncName(), FuncBody());
                }
            }
        } else {
            statBreakstat = Label();
        }
        L(statBreakstat, jLineInfo);
        return statBreakstat;
    }

    public final LuaString Str() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 61) {
            jj_consume_token(61);
            return Str.quoteString(this.token.image);
        }
        if (iJj_ntk == 62) {
            jj_consume_token(62);
            return Str.charString(this.token.image);
        }
        switch (iJj_ntk) {
            case 23:
                jj_consume_token(23);
                return Str.longString(this.token.image);
            case 24:
                jj_consume_token(24);
                return Str.longString(this.token.image);
            case 25:
                jj_consume_token(25);
                return Str.longString(this.token.image);
            case 26:
                jj_consume_token(26);
                return Str.longString(this.token.image);
            case 27:
                jj_consume_token(27);
                return Str.longString(this.token.image);
            default:
                this.jj_la1[22] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x005d  */
    public final TableConstructor TableConstructor() throws ParseException {
        TableConstructor tableConstructor = new TableConstructor();
        long jLineInfo = LineInfo();
        jj_consume_token(80);
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 35 && iJj_ntk != 37 && iJj_ntk != 48 && iJj_ntk != 69 && iJj_ntk != 75 && iJj_ntk != 77 && iJj_ntk != 83 && iJj_ntk != 42 && iJj_ntk != 43 && iJj_ntk != 51 && iJj_ntk != 52 && iJj_ntk != 61 && iJj_ntk != 62 && iJj_ntk != 79 && iJj_ntk != 80) {
            switch (iJj_ntk) {
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    tableConstructor.fields = FieldList();
                    break;
                default:
                    this.jj_la1[27] = this.jj_gen;
                    break;
            }
        } else {
            tableConstructor.fields = FieldList();
        }
        jj_consume_token(81);
        L(tableConstructor, jLineInfo);
        return tableConstructor;
    }

    public final int Unop() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 43) {
            jj_consume_token(43);
            return 20;
        }
        if (iJj_ntk == 69) {
            jj_consume_token(69);
            return 21;
        }
        if (iJj_ntk == 83) {
            jj_consume_token(83);
            return 19;
        }
        this.jj_la1[33] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
    }

    public final Exp.VarExp VarExp() throws ParseException {
        return assertvarexp(PrimaryExp());
    }

    public final void disable_tracing() {
    }

    public final void enable_tracing() {
    }

    public ParseException generateParseException() {
        this.jj_expentries.clear();
        boolean[] zArr = new boolean[95];
        int i = this.jj_kind;
        if (i >= 0) {
            zArr[i] = true;
            this.jj_kind = -1;
        }
        for (int i2 = 0; i2 < 34; i2++) {
            if (this.jj_la1[i2] == this.jj_gen) {
                for (int i3 = 0; i3 < 32; i3++) {
                    int i4 = 1 << i3;
                    if ((jj_la1_0[i2] & i4) != 0) {
                        zArr[i3] = true;
                    }
                    if ((jj_la1_1[i2] & i4) != 0) {
                        zArr[i3 + 32] = true;
                    }
                    if ((jj_la1_2[i2] & i4) != 0) {
                        zArr[i3 + 64] = true;
                    }
                }
            }
        }
        for (int i5 = 0; i5 < 95; i5++) {
            if (zArr[i5]) {
                int[] iArr = {i5};
                this.jj_expentry = iArr;
                this.jj_expentries.add(iArr);
            }
        }
        this.jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] iArr2 = new int[this.jj_expentries.size()][];
        for (int i6 = 0; i6 < this.jj_expentries.size(); i6++) {
            iArr2[i6] = (int[]) this.jj_expentries.get(i6);
        }
        return new ParseException(this.token, iArr2, tokenImage);
    }

    public SimpleCharStream getCharStream() {
        return this.jj_input_stream;
    }

    public final Token getNextToken() {
        if (this.token.next != null) {
            this.token = this.token.next;
        } else {
            Token token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        this.jj_gen++;
        return this.token;
    }

    public final Token getToken(int i) {
        Token token = this.token;
        for (int i2 = 0; i2 < i; i2++) {
            if (token.next != null) {
                token = token.next;
            } else {
                Token nextToken = this.token_source.getNextToken();
                token.next = nextToken;
                token = nextToken;
            }
        }
        return token;
    }
}
