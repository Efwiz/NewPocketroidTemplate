package org.luaj.vm2.lib;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.wasm.ir.convertors.WasmBinary;
import org.luaj.vm2.Buffer;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.compiler.DumpState;

/* JADX INFO: loaded from: classes6.dex */
public class StringLib extends TwoArgFunction {
    private static final int CAP_POSITION = -2;
    private static final int CAP_UNFINISHED = -1;
    private static final String FLAGS = "-+ #0";
    private static final int L_ESC = 37;
    private static final byte MASK_ALPHA = 1;
    private static final byte MASK_CONTROL = 64;
    private static final byte MASK_DIGIT = 8;
    private static final byte MASK_HEXDIGIT = -128;
    private static final byte MASK_LOWERCASE = 2;
    private static final byte MASK_PUNCT = 16;
    private static final byte MASK_SPACE = 32;
    private static final byte MASK_UPPERCASE = 4;
    private static final int MAX_CAPTURES = 32;
    private static final LuaString SPECIALS = valueOf("^$*+?.([%-");
    private static final byte[] CHAR_TABLE = new byte[256];

    static class FormatDesc {
        private static final int MAX_FLAGS = 5;
        private boolean alternateForm;
        public final int conversion;
        private boolean explicitPlus;
        private boolean leftAdjust;
        public final int length;
        private int precision;
        private boolean space;
        private int width;
        private boolean zeroPad;

        public FormatDesc(Varargs varargs, LuaString luaString, int i) {
            int length = luaString.length();
            int iLuaByte = 0;
            int i2 = i;
            int iLuaByte2 = 0;
            boolean z = true;
            while (z) {
                if (i2 < length) {
                    iLuaByte2 = luaString.luaByte(i2);
                    i2++;
                } else {
                    iLuaByte2 = 0;
                }
                if (iLuaByte2 == 32) {
                    this.space = true;
                } else if (iLuaByte2 == 35) {
                    this.alternateForm = true;
                } else if (iLuaByte2 == 43) {
                    this.explicitPlus = true;
                } else if (iLuaByte2 == 45) {
                    this.leftAdjust = true;
                } else if (iLuaByte2 != 48) {
                    z = false;
                } else {
                    this.zeroPad = true;
                }
            }
            if (i2 - i > 5) {
                LuaValue.error("invalid format (repeated flags)");
            }
            this.width = -1;
            if (Character.isDigit((char) iLuaByte2)) {
                this.width = iLuaByte2 - 48;
                if (i2 < length) {
                    iLuaByte2 = luaString.luaByte(i2);
                    i2++;
                } else {
                    iLuaByte2 = 0;
                }
                if (Character.isDigit((char) iLuaByte2)) {
                    this.width = (this.width * 10) + (iLuaByte2 - 48);
                    if (i2 < length) {
                        iLuaByte2 = luaString.luaByte(i2);
                        i2++;
                    } else {
                        iLuaByte2 = 0;
                    }
                }
            }
            this.precision = -1;
            if (iLuaByte2 == 46) {
                if (i2 < length) {
                    iLuaByte2 = luaString.luaByte(i2);
                    i2++;
                } else {
                    iLuaByte2 = 0;
                }
                if (Character.isDigit((char) iLuaByte2)) {
                    this.precision = iLuaByte2 - 48;
                    if (i2 < length) {
                        iLuaByte2 = luaString.luaByte(i2);
                        i2++;
                    } else {
                        iLuaByte2 = 0;
                    }
                    if (Character.isDigit((char) iLuaByte2)) {
                        this.precision = (this.precision * 10) + (iLuaByte2 - 48);
                        if (i2 < length) {
                            iLuaByte = luaString.luaByte(i2);
                            i2++;
                        }
                        iLuaByte2 = iLuaByte;
                    }
                }
            }
            if (Character.isDigit((char) iLuaByte2)) {
                LuaValue.error("invalid format (width or precision too long)");
            }
            this.zeroPad &= !this.leftAdjust;
            this.conversion = iLuaByte2;
            this.length = i2 - i;
        }

        public static final void pad(Buffer buffer, char c, int i) {
            byte b = (byte) c;
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    return;
                }
                buffer.append(b);
                i = i2;
            }
        }

        public void format(Buffer buffer, byte b) {
            buffer.append(b);
        }

        public void format(Buffer buffer, double d) {
            buffer.append(String.valueOf(d));
        }

        /* JADX WARN: Code duplicated, block: B:15:0x0021  */
        public void format(Buffer buffer, long j) {
            int i;
            String string;
            int i2;
            int i3;
            int i4;
            if (j == 0 && this.precision == 0) {
                string = "";
            } else {
                int i5 = this.conversion;
                if (i5 == 88) {
                    i = 16;
                } else if (i5 == 111) {
                    i = 8;
                } else if (i5 != 120) {
                    i = 10;
                } else {
                    i = 16;
                }
                string = Long.toString(j, i);
                if (this.conversion == 88) {
                    string = string.toUpperCase();
                }
            }
            int length = string.length();
            if (j < 0) {
                i2 = length;
                length--;
            } else {
                i2 = (this.explicitPlus || this.space) ? length + 1 : length;
            }
            int i6 = this.precision;
            if (i6 > length) {
                i3 = i6 - length;
            } else {
                i3 = (i6 == -1 && this.zeroPad && (i4 = this.width) > i2) ? i4 - i2 : 0;
            }
            int i7 = i2 + i3;
            int i8 = this.width;
            int i9 = i8 > i7 ? i8 - i7 : 0;
            if (!this.leftAdjust) {
                pad(buffer, StringUtil.SPACE, i9);
            }
            if (j < 0) {
                if (i3 > 0) {
                    buffer.append((byte) 45);
                    string = string.substring(1);
                }
            } else if (this.explicitPlus) {
                buffer.append((byte) 43);
            } else if (this.space) {
                buffer.append((byte) 32);
            }
            if (i3 > 0) {
                pad(buffer, ExternalAnnotationProvider.NULLABLE, i3);
            }
            buffer.append(string);
            if (this.leftAdjust) {
                pad(buffer, StringUtil.SPACE, i9);
            }
        }

        public void format(Buffer buffer, LuaString luaString) {
            int iIndexOf = luaString.indexOf((byte) 0, 0);
            if (iIndexOf != -1) {
                luaString = luaString.substring(0, iIndexOf);
            }
            buffer.append(luaString);
        }
    }

    static class GMatchAux extends VarArgFunction {
        private final MatchState ms;
        private int soffset = 0;
        private final int srclen;

        public GMatchAux(Varargs varargs, LuaString luaString, LuaString luaString2) {
            this.srclen = luaString.length();
            this.ms = new MatchState(varargs, luaString, luaString2);
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            while (this.soffset < this.srclen) {
                this.ms.reset();
                int iMatch = this.ms.match(this.soffset, 0);
                if (iMatch >= 0) {
                    int i = this.soffset;
                    this.soffset = iMatch;
                    return this.ms.push_captures(true, i, iMatch);
                }
                this.soffset++;
            }
            return NIL;
        }
    }

    static class MatchState {
        final Varargs args;
        final LuaString p;
        final LuaString s;
        int level = 0;
        int[] cinit = new int[32];
        int[] clen = new int[32];

        MatchState(Varargs varargs, LuaString luaString, LuaString luaString2) {
            this.s = luaString;
            this.p = luaString2;
            this.args = varargs;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x0010 A[PHI: r1 r2
          0x0010: PHI (r1v5 int) = (r1v1 int), (r1v2 int) binds: [B:5:0x000e, B:8:0x0020] A[DONT_GENERATE, DONT_INLINE]
          0x0010: PHI (r2v10 byte) = (r2v1 byte), (r2v3 byte) binds: [B:5:0x000e, B:8:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
        private void add_s(Buffer buffer, LuaString luaString, int i, int i2) {
            int length = luaString.length();
            int i3 = 0;
            while (i3 < length) {
                byte bLuaByte = (byte) luaString.luaByte(i3);
                if (bLuaByte != 37) {
                    buffer.append(bLuaByte);
                } else {
                    i3++;
                    bLuaByte = (byte) luaString.luaByte(i3);
                    if (Character.isDigit((char) bLuaByte)) {
                        buffer.append(bLuaByte == 48 ? this.s.substring(i, i2) : push_onecapture(bLuaByte + WasmBinary.SUB_FINAL_TYPE, i, i2).strvalue());
                    } else {
                        buffer.append(bLuaByte);
                    }
                }
                i3++;
            }
        }

        private int capture_to_close() {
            for (int i = this.level - 1; i >= 0; i--) {
                if (this.clen[i] == -1) {
                    return i;
                }
            }
            LuaValue.error("invalid pattern capture");
            return 0;
        }

        private int check_capture(int i) {
            int i2 = i - 49;
            if (i2 < 0 || i2 >= this.level || this.clen[i2] == -1) {
                LuaValue.error("invalid capture index");
            }
            return i2;
        }

        /* JADX WARN: Code duplicated, block: B:27:0x003d  */
        /* JADX WARN: Code duplicated, block: B:28:0x003f  */
        static boolean match_class(int i, int i2) {
            boolean z;
            char lowerCase = Character.toLowerCase((char) i2);
            byte b = StringLib.CHAR_TABLE[i];
            if (lowerCase != 'a') {
                if (lowerCase != 'l') {
                    if (lowerCase != 'p') {
                        if (lowerCase != 's') {
                            if (lowerCase != 'u') {
                                if (lowerCase != 'z') {
                                    if (lowerCase != 'c') {
                                        if (lowerCase != 'd') {
                                            if (lowerCase != 'w') {
                                                if (lowerCase != 'x') {
                                                    return i2 == i;
                                                }
                                                if ((b & (-128)) != 0) {
                                                    z = true;
                                                } else {
                                                    z = false;
                                                }
                                            } else if ((b & 9) != 0) {
                                                z = true;
                                            } else {
                                                z = false;
                                            }
                                        } else if ((b & 8) != 0) {
                                            z = true;
                                        } else {
                                            z = false;
                                        }
                                    } else if ((b & 64) != 0) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                } else if (i == 0) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            } else if ((b & 4) != 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                        } else if ((b & 32) != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else if ((b & 16) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else if ((b & 2) != 0) {
                    z = true;
                } else {
                    z = false;
                }
            } else if ((b & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (lowerCase == i2) {
                return z;
            }
            return !z;
        }

        private LuaValue push_onecapture(int i, int i2, int i3) {
            if (i >= this.level) {
                return i == 0 ? this.s.substring(i2, i3) : LuaValue.error("invalid capture index");
            }
            int i4 = this.clen[i];
            if (i4 == -1) {
                return LuaValue.error("unfinished capture");
            }
            if (i4 == -2) {
                return LuaValue.valueOf(this.cinit[i] + 1);
            }
            int i5 = this.cinit[i];
            return this.s.substring(i5, i4 + i5);
        }

        public void add_value(Buffer buffer, int i, int i2, LuaValue luaValue) {
            LuaValue luaValueSubstring;
            int iType = luaValue.type();
            if (iType == 3 || iType == 4) {
                add_s(buffer, luaValue.strvalue(), i, i2);
                return;
            }
            if (iType == 5) {
                luaValueSubstring = luaValue.get(push_onecapture(0, i, i2));
            } else {
                if (iType != 6) {
                    LuaValue.error("bad argument: string/function/table expected");
                    return;
                }
                luaValueSubstring = luaValue.invoke(push_captures(true, i, i2)).arg1();
            }
            if (!luaValueSubstring.toboolean()) {
                luaValueSubstring = this.s.substring(i, i2);
            } else if (!luaValueSubstring.isstring()) {
                LuaValue.error(new StringBuffer("invalid replacement value (a ").append(luaValueSubstring.typename()).append(Constant.AFTER_QUTO).toString());
            }
            buffer.append(luaValueSubstring.strvalue());
        }

        int classend(int i) {
            int i2 = i + 1;
            int iLuaByte = this.p.luaByte(i);
            if (iLuaByte == 37) {
                if (i2 == this.p.length()) {
                    LuaValue.error("malformed pattern (ends with %)");
                }
                return i + 2;
            }
            if (iLuaByte != 91) {
                return i2;
            }
            if (this.p.luaByte(i2) == 94) {
                i2 = i + 2;
            }
            do {
                if (i2 == this.p.length()) {
                    LuaValue.error("malformed pattern (missing ])");
                }
                int i3 = i2 + 1;
                i2 = (this.p.luaByte(i2) != 37 || i3 == this.p.length()) ? i3 : i2 + 2;
            } while (this.p.luaByte(i2) != 93);
            return i2 + 1;
        }

        int end_capture(int i, int i2) {
            int iCapture_to_close = capture_to_close();
            this.clen[iCapture_to_close] = i - this.cinit[iCapture_to_close];
            int iMatch = match(i, i2);
            if (iMatch == -1) {
                this.clen[iCapture_to_close] = -1;
            }
            return iMatch;
        }

        /* JADX WARN: Code duplicated, block: B:101:0x0138 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:102:0x0130 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:103:0x012b A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:104:0x011a A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:105:0x0114 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:106:0x0112 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:118:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:65:0x00f3  */
        /* JADX WARN: Code duplicated, block: B:68:0x00fc  */
        /* JADX WARN: Code duplicated, block: B:71:0x0106  */
        /* JADX WARN: Code duplicated, block: B:73:0x010a  */
        /* JADX WARN: Code duplicated, block: B:75:0x010e  */
        /* JADX WARN: Code duplicated, block: B:79:0x0115  */
        /* JADX WARN: Code duplicated, block: B:89:0x0132  */
        int match(int i, int i2) {
            int iClassend;
            boolean z;
            int iLuaByte;
            int iMatch;
            while (i2 != this.p.length()) {
                int iLuaByte2 = this.p.luaByte(i2);
                if (iLuaByte2 != 36) {
                    if (iLuaByte2 == 37) {
                        int i3 = i2 + 1;
                        if (i3 == this.p.length()) {
                            LuaValue.error("malformed pattern (ends with '%')");
                        }
                        int iLuaByte3 = this.p.luaByte(i3);
                        if (iLuaByte3 != 98) {
                            if (iLuaByte3 != 102) {
                                int iLuaByte4 = this.p.luaByte(i3);
                                if (Character.isDigit((char) iLuaByte4)) {
                                    int iMatch_capture = match_capture(i, iLuaByte4);
                                    if (iMatch_capture == -1) {
                                        return -1;
                                    }
                                    return match(iMatch_capture, i2 + 2);
                                }
                            } else {
                                int i4 = i2 + 2;
                                if (this.p.luaByte(i4) != 91) {
                                    LuaValue.error("Missing [ after %f in pattern");
                                }
                                iClassend = classend(i4);
                                int i5 = iClassend - 1;
                                if (matchbracketclass(i == 0 ? -1 : this.s.luaByte(i - 1), i4, i5) || matchbracketclass(this.s.luaByte(i), i4, i5)) {
                                    return -1;
                                }
                            }
                            i2 = iClassend;
                        } else {
                            i = matchbalance(i, i2 + 2);
                            if (i == -1) {
                                return -1;
                            }
                            i2 += 4;
                        }
                    } else {
                        if (iLuaByte2 == 40) {
                            int i6 = i2 + 1;
                            return (i6 >= this.p.length() || this.p.luaByte(i6) != 41) ? start_capture(i, i6, -1) : start_capture(i, i2 + 2, -2);
                        }
                        if (iLuaByte2 == 41) {
                            return end_capture(i, i2 + 1);
                        }
                        iClassend = classend(i2);
                        if (i < this.s.length() || !singlematch(this.s.luaByte(i), i2, iClassend)) {
                            z = false;
                        } else {
                            z = true;
                        }
                        iLuaByte = iClassend < this.p.length() ? this.p.luaByte(iClassend) : 0;
                        if (iLuaByte != 42) {
                            return max_expand(i, i2, iClassend);
                        }
                        if (iLuaByte != 43) {
                            if (z) {
                                return max_expand(i + 1, i2, iClassend);
                            }
                            return -1;
                        }
                        if (iLuaByte != 45) {
                            return min_expand(i, i2, iClassend);
                        }
                        if (iLuaByte == 63) {
                            if (!z) {
                                return -1;
                            }
                            i++;
                            i2 = iClassend;
                        } else {
                            if (!z && (iMatch = match(i + 1, iClassend + 1)) != -1) {
                                return iMatch;
                            }
                            i2 = iClassend + 1;
                        }
                    }
                }
                if (i2 + 1 == this.p.length()) {
                    if (i == this.s.length()) {
                        return i;
                    }
                    return -1;
                }
                iClassend = classend(i2);
                if (i < this.s.length()) {
                    z = false;
                } else {
                    z = false;
                }
                if (iClassend < this.p.length()) {
                }
                if (iLuaByte != 42) {
                    return max_expand(i, i2, iClassend);
                }
                if (iLuaByte != 43) {
                    if (z) {
                        return max_expand(i + 1, i2, iClassend);
                    }
                    return -1;
                }
                if (iLuaByte != 45) {
                    return min_expand(i, i2, iClassend);
                }
                if (iLuaByte == 63) {
                    if (!z) {
                    }
                    i2 = iClassend + 1;
                } else {
                    if (!z) {
                        return -1;
                    }
                    i++;
                    i2 = iClassend;
                }
            }
            return i;
        }

        int match_capture(int i, int i2) {
            int iCheck_capture = check_capture(i2);
            int i3 = this.clen[iCheck_capture];
            if (this.s.length() - i < i3) {
                return -1;
            }
            LuaString luaString = this.s;
            if (LuaString.equals(luaString, this.cinit[iCheck_capture], luaString, i, i3)) {
                return i + i3;
            }
            return -1;
        }

        int matchbalance(int i, int i2) {
            int iLuaByte;
            int length = this.p.length();
            if (i2 == length || i2 + 1 == length) {
                LuaValue.error("unbalanced pattern");
            }
            int length2 = this.s.length();
            if (i >= length2 || this.s.luaByte(i) != (iLuaByte = this.p.luaByte(i2))) {
                return -1;
            }
            int i3 = 1;
            int iLuaByte2 = this.p.luaByte(i2 + 1);
            while (true) {
                int i4 = i + 1;
                if (i4 >= length2) {
                    return -1;
                }
                if (this.s.luaByte(i4) == iLuaByte2) {
                    i3--;
                    if (i3 == 0) {
                        return i + 2;
                    }
                } else if (this.s.luaByte(i4) == iLuaByte) {
                    i3++;
                }
                i = i4;
            }
        }

        /* JADX WARN: Code duplicated, block: B:16:0x003b  */
        /* JADX WARN: Code duplicated, block: B:26:0x005c A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:29:0x005d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:30:0x002f A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:31:0x002e A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:32:0x0020 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:33:0x0054 A[ADDED_TO_REGION, EDGE_INSN: B:33:0x0054->B:24:0x0054 BREAK  A[LOOP:0: B:7:0x0012->B:36:0x0012], REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:38:0x0012 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:9:0x0016  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005a -> B:5:0x000f). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:7:0x0012
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        boolean matchbracketclass(int r6, int r7, int r8) {
            /*
                r5 = this;
                org.luaj.vm2.LuaString r0 = r5.p
                int r1 = r7 + 1
                int r0 = r0.luaByte(r1)
                r2 = 94
                r3 = 1
                if (r0 != r2) goto L11
                r7 = 0
                r0 = r7
            Lf:
                r7 = r1
                goto L12
            L11:
                r0 = r3
            L12:
                int r1 = r7 + 1
                if (r1 >= r8) goto L5d
                org.luaj.vm2.LuaString r2 = r5.p
                int r2 = r2.luaByte(r1)
                r4 = 37
                if (r2 != r4) goto L2f
                int r7 = r7 + 2
                org.luaj.vm2.LuaString r1 = r5.p
                int r1 = r1.luaByte(r7)
                boolean r1 = match_class(r6, r1)
                if (r1 == 0) goto L12
                return r0
            L2f:
                org.luaj.vm2.LuaString r2 = r5.p
                int r4 = r7 + 2
                int r2 = r2.luaByte(r4)
                r4 = 45
                if (r2 != r4) goto L54
                int r2 = r7 + 3
                if (r2 >= r8) goto L54
                org.luaj.vm2.LuaString r1 = r5.p
                int r7 = r7 + 1
                int r7 = r1.luaByte(r7)
                if (r7 > r6) goto L52
                org.luaj.vm2.LuaString r7 = r5.p
                int r7 = r7.luaByte(r2)
                if (r6 > r7) goto L52
                return r0
            L52:
                r7 = r2
                goto L12
            L54:
                org.luaj.vm2.LuaString r7 = r5.p
                int r7 = r7.luaByte(r1)
                if (r7 != r6) goto Lf
                return r0
            L5d:
                r6 = r0 ^ 1
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.lib.StringLib.MatchState.matchbracketclass(int, int, int):boolean");
        }

        int max_expand(int i, int i2, int i3) {
            int i4 = 0;
            while (true) {
                int i5 = i + i4;
                if (i5 >= this.s.length() || !singlematch(this.s.luaByte(i5), i2, i3)) {
                    break;
                }
                i4++;
            }
            while (i4 >= 0) {
                int iMatch = match(i + i4, i3 + 1);
                if (iMatch != -1) {
                    return iMatch;
                }
                i4--;
            }
            return -1;
        }

        int min_expand(int i, int i2, int i3) {
            while (true) {
                int iMatch = match(i, i3 + 1);
                if (iMatch != -1) {
                    return iMatch;
                }
                if (i >= this.s.length() || !singlematch(this.s.luaByte(i), i2, i3)) {
                    return -1;
                }
                i++;
            }
        }

        Varargs push_captures(boolean z, int i, int i2) {
            int i3 = this.level;
            if (i3 == 0 && z) {
                i3 = 1;
            }
            if (i3 == 0) {
                return LuaValue.NONE;
            }
            if (i3 == 1) {
                return push_onecapture(0, i, i2);
            }
            LuaValue[] luaValueArr = new LuaValue[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                luaValueArr[i4] = push_onecapture(i4, i, i2);
            }
            return LuaValue.varargsOf(luaValueArr);
        }

        void reset() {
            this.level = 0;
        }

        boolean singlematch(int i, int i2, int i3) {
            int iLuaByte = this.p.luaByte(i2);
            if (iLuaByte == 37) {
                return match_class(i, this.p.luaByte(i2 + 1));
            }
            if (iLuaByte == 46) {
                return true;
            }
            if (iLuaByte != 91) {
                return this.p.luaByte(i2) == i;
            }
            return matchbracketclass(i, i2, i3 - 1);
        }

        int start_capture(int i, int i2, int i3) {
            int i4 = this.level;
            if (i4 >= 32) {
                LuaValue.error("too many captures");
            }
            this.cinit[i4] = i;
            this.clen[i4] = i3;
            this.level = i4 + 1;
            int iMatch = match(i, i2);
            if (iMatch == -1) {
                this.level--;
            }
            return iMatch;
        }
    }

    static final class byte_ extends VarArgFunction {
        byte_() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            int i = luaStringCheckstring.m_length;
            int iPosrelat = StringLib.posrelat(varargs.optint(2, 1), i);
            int iPosrelat2 = StringLib.posrelat(varargs.optint(3, iPosrelat), i);
            if (iPosrelat <= 0) {
                iPosrelat = 1;
            }
            if (iPosrelat2 <= i) {
                i = iPosrelat2;
            }
            if (iPosrelat > i) {
                return NONE;
            }
            int i2 = (i - iPosrelat) + 1;
            if (iPosrelat + i2 <= i) {
                error("string slice too long");
            }
            LuaValue[] luaValueArr = new LuaValue[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                luaValueArr[i3] = valueOf(luaStringCheckstring.luaByte((iPosrelat + i3) - 1));
            }
            return varargsOf(luaValueArr);
        }
    }

    static final class char_ extends VarArgFunction {
        char_() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            int iNarg = varargs.narg();
            byte[] bArr = new byte[iNarg];
            int i = 0;
            int i2 = 1;
            while (i < iNarg) {
                int iCheckint = varargs.checkint(i2);
                if (iCheckint < 0 || iCheckint >= 256) {
                    argerror(i2, "invalid value");
                }
                bArr[i] = (byte) iCheckint;
                i++;
                i2++;
            }
            return LuaString.valueUsing(bArr);
        }
    }

    static final class dump extends OneArgFunction {
        dump() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            LuaFunction luaFunctionCheckfunction = luaValue.checkfunction();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                DumpState.dump(((LuaClosure) luaFunctionCheckfunction).p, byteArrayOutputStream, true);
                return LuaString.valueUsing(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                return error(e.getMessage());
            }
        }
    }

    static final class find extends VarArgFunction {
        find() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return StringLib.str_find_aux(varargs, true);
        }
    }

    static final class format extends VarArgFunction {
        format() {
        }

        /* JADX WARN: Code duplicated, block: B:44:0x00bc  */
        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            long jChecklong;
            int i = 1;
            LuaString luaStringCheckstring = varargs.checkstring(1);
            int length = luaStringCheckstring.length();
            Buffer buffer = new Buffer(length);
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                int iLuaByte = luaStringCheckstring.luaByte(i2);
                if (iLuaByte == 10) {
                    buffer.append("\n");
                } else if (iLuaByte != 37) {
                    buffer.append((byte) iLuaByte);
                } else if (i3 < length) {
                    if (luaStringCheckstring.luaByte(i3) == 37) {
                        i2 += 2;
                        buffer.append((byte) 37);
                    } else {
                        i++;
                        FormatDesc formatDesc = new FormatDesc(varargs, luaStringCheckstring, i3);
                        i3 += formatDesc.length;
                        int i4 = formatDesc.conversion;
                        if (i4 == 69 || i4 == 71) {
                            formatDesc.format(buffer, varargs.checkdouble(i));
                        } else if (i4 == 88) {
                            jChecklong = varargs.checklong(i);
                            formatDesc.format(buffer, jChecklong);
                        } else {
                            if (i4 != 105) {
                                if (i4 != 111) {
                                    if (i4 == 113) {
                                        StringLib.addquoted(buffer, varargs.checkstring(i));
                                    } else if (i4 == 115) {
                                        LuaString luaStringCheckstring2 = varargs.checkstring(i);
                                        if (formatDesc.precision != -1 || luaStringCheckstring2.length() < 100) {
                                            formatDesc.format(buffer, luaStringCheckstring2);
                                        } else {
                                            buffer.append(luaStringCheckstring2);
                                        }
                                    } else if (i4 != 117 && i4 != 120) {
                                        switch (i4) {
                                            case 99:
                                                formatDesc.format(buffer, (byte) varargs.checkint(i));
                                                break;
                                            case 100:
                                                break;
                                            case 101:
                                            case 102:
                                            case 103:
                                                formatDesc.format(buffer, varargs.checkdouble(i));
                                                break;
                                            default:
                                                error(new StringBuffer("invalid option '%").append((char) formatDesc.conversion).append("' to 'format'").toString());
                                                break;
                                        }
                                    }
                                }
                                jChecklong = varargs.checklong(i);
                                formatDesc.format(buffer, jChecklong);
                            }
                            jChecklong = varargs.checkint(i);
                            formatDesc.format(buffer, jChecklong);
                        }
                    }
                }
                i2 = i3;
            }
            return buffer.tostring();
        }
    }

    static final class gmatch extends VarArgFunction {
        gmatch() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return new GMatchAux(varargs, varargs.checkstring(1), varargs.checkstring(2));
        }
    }

    static final class gsub extends VarArgFunction {
        gsub() {
        }

        /* JADX WARN: Code duplicated, block: B:23:0x005b A[EDGE_INSN: B:23:0x005b->B:20:0x005b BREAK  A[LOOP:0: B:9:0x0036->B:25:?], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:25:? A[LOOP:0: B:9:0x0036->B:25:?, LOOP_END, SYNTHETIC] */
        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            int length = luaStringCheckstring.length();
            LuaString luaStringCheckstring2 = varargs.checkstring(2);
            LuaValue luaValueArg = varargs.arg(3);
            int iOptint = varargs.optint(4, length + 1);
            int i = 0;
            int i2 = (luaStringCheckstring2.length() <= 0 || luaStringCheckstring2.charAt(0) != 94) ? 0 : 1;
            Buffer buffer = new Buffer(length);
            MatchState matchState = new MatchState(varargs, luaStringCheckstring, luaStringCheckstring2);
            int i3 = 0;
            while (i < iOptint) {
                matchState.reset();
                int iMatch = matchState.match(i3, i2);
                if (iMatch != -1) {
                    i++;
                    matchState.add_value(buffer, i3, iMatch, luaValueArg);
                }
                if (iMatch != -1 && iMatch > i3) {
                    i3 = iMatch;
                    if (i2 != 0) {
                        break;
                        break;
                    }
                } else {
                    if (i3 >= length) {
                        break;
                    }
                    iMatch = i3 + 1;
                    buffer.append((byte) luaStringCheckstring.luaByte(i3));
                    i3 = iMatch;
                    if (i2 != 0) {
                        break;
                    }
                }
            }
            buffer.append(luaStringCheckstring.substring(i3, length));
            return varargsOf(buffer.tostring(), valueOf(i));
        }
    }

    static final class len extends OneArgFunction {
        len() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return luaValue.checkstring().len();
        }
    }

    static final class lower extends OneArgFunction {
        lower() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return valueOf(luaValue.checkjstring().toLowerCase());
        }
    }

    static final class match extends VarArgFunction {
        match() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return StringLib.str_find_aux(varargs, false);
        }
    }

    static final class rep extends VarArgFunction {
        rep() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            int length = luaStringCheckstring.length() * varargs.checkint(2);
            byte[] bArr = new byte[length];
            int length2 = luaStringCheckstring.length();
            for (int i = 0; i < length; i += length2) {
                luaStringCheckstring.copyInto(0, bArr, i, length2);
            }
            return LuaString.valueUsing(bArr);
        }
    }

    static final class reverse extends OneArgFunction {
        reverse() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            LuaString luaStringCheckstring = luaValue.checkstring();
            int length = luaStringCheckstring.length();
            byte[] bArr = new byte[length];
            int i = length - 1;
            int i2 = 0;
            while (i2 < length) {
                bArr[i] = (byte) luaStringCheckstring.luaByte(i2);
                i2++;
                i--;
            }
            return LuaString.valueUsing(bArr);
        }
    }

    static final class sub extends VarArgFunction {
        sub() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            int length = luaStringCheckstring.length();
            int iPosrelat = StringLib.posrelat(varargs.checkint(2), length);
            int iPosrelat2 = StringLib.posrelat(varargs.optint(3, -1), length);
            if (iPosrelat < 1) {
                iPosrelat = 1;
            }
            if (iPosrelat2 <= length) {
                length = iPosrelat2;
            }
            return iPosrelat <= length ? luaStringCheckstring.substring(iPosrelat - 1, length) : EMPTYSTRING;
        }
    }

    static final class upper extends OneArgFunction {
        upper() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return valueOf(luaValue.checkjstring().toUpperCase());
        }
    }

    static {
        for (int i = 0; i < 256; i++) {
            char c = (char) i;
            byte[] bArr = CHAR_TABLE;
            byte b = (byte) (((c < ' ' || c == 127) ? 64 : 0) | (Character.isDigit(c) ? 8 : 0) | (Character.isLowerCase(c) ? 2 : 0) | (Character.isUpperCase(c) ? 4 : 0));
            bArr[i] = b;
            if ((c >= 'a' && c <= 'f') || ((c >= 'A' && c <= 'F') || (c >= '0' && c <= '9'))) {
                bArr[i] = (byte) (b | (-128));
            }
            if ((c >= '!' && c <= '/') || (c >= ':' && c <= '@')) {
                bArr[i] = (byte) (bArr[i] | 16);
            }
            byte b2 = bArr[i];
            if ((b2 & 6) != 0) {
                bArr[i] = (byte) (b2 | 1);
            }
        }
        byte[] bArr2 = CHAR_TABLE;
        bArr2[32] = 32;
        bArr2[13] = (byte) (bArr2[13] | 32);
        bArr2[10] = (byte) (bArr2[10] | 32);
        bArr2[9] = (byte) (bArr2[9] | 32);
        byte b3 = (byte) (bArr2[12] | 32);
        bArr2[12] = b3;
        bArr2[12] = (byte) (b3 | 32);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addquoted(Buffer buffer, LuaString luaString) {
        buffer.append((byte) 34);
        int length = luaString.length();
        for (int i = 0; i < length; i++) {
            int iLuaByte = luaString.luaByte(i);
            if (iLuaByte == 10 || iLuaByte == 34 || iLuaByte == 92) {
                buffer.append((byte) 92);
            } else {
                if (iLuaByte <= 31 || iLuaByte == 127) {
                    buffer.append((byte) 92);
                    int i2 = i + 1;
                    if (i2 == length || luaString.luaByte(i2) < 48 || luaString.luaByte(i2) > 57) {
                        buffer.append(Integer.toString(iLuaByte));
                    } else {
                        buffer.append((byte) 48);
                        buffer.append((byte) ((char) ((iLuaByte / 10) + 48)));
                        iLuaByte = (char) ((iLuaByte % 10) + 48);
                    }
                }
            }
            buffer.append((byte) iLuaByte);
        }
        buffer.append((byte) 34);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int posrelat(int i, int i2) {
        return i >= 0 ? i : i2 + i + 1;
    }

    static Varargs str_find_aux(Varargs varargs, boolean z) {
        LuaString luaStringCheckstring = varargs.checkstring(1);
        LuaString luaStringCheckstring2 = varargs.checkstring(2);
        int iOptint = varargs.optint(3, 1);
        if (iOptint > 0) {
            iOptint = Math.min(iOptint - 1, luaStringCheckstring.length());
        } else if (iOptint < 0) {
            iOptint = Math.max(0, luaStringCheckstring.length() + iOptint);
        }
        if (z && (varargs.arg(4).toboolean() || luaStringCheckstring2.indexOfAny(SPECIALS) == -1)) {
            int iIndexOf = luaStringCheckstring.indexOf(luaStringCheckstring2, iOptint);
            if (iIndexOf != -1) {
                return varargsOf(valueOf(iIndexOf + 1), valueOf(iIndexOf + luaStringCheckstring2.length()));
            }
        } else {
            MatchState matchState = new MatchState(varargs, luaStringCheckstring, luaStringCheckstring2);
            int i = luaStringCheckstring2.luaByte(0) == 94 ? 1 : 0;
            int i2 = i;
            while (true) {
                matchState.reset();
                int iMatch = matchState.match(iOptint, i2);
                if (iMatch != -1) {
                    return z ? varargsOf(valueOf(iOptint + 1), valueOf(iMatch), matchState.push_captures(false, iOptint, iMatch)) : matchState.push_captures(true, iOptint, iMatch);
                }
                int i3 = iOptint + 1;
                if (iOptint < luaStringCheckstring.length() && i == 0) {
                    iOptint = i3;
                }
            }
        }
        return NIL;
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable();
        luaTable.set("byte", new byte_());
        luaTable.set(PsiKeyword.CHAR, new char_());
        luaTable.set(ArchiveStreamFactory.DUMP, new dump());
        luaTable.set("find", new find());
        luaTable.set(ConfigConstants.CONFIG_KEY_FORMAT, new format());
        luaTable.set("gmatch", new gmatch());
        luaTable.set("gsub", new gsub());
        luaTable.set("len", new len());
        luaTable.set("lower", new lower());
        luaTable.set("match", new match());
        luaTable.set("rep", new rep());
        luaTable.set("reverse", new reverse());
        luaTable.set("sub", new sub());
        luaTable.set("upper", new upper());
        LuaTable luaTableTableOf = LuaValue.tableOf(new LuaValue[]{INDEX, luaTable});
        luaValue2.set("string", luaTable);
        luaValue2.get("package").get("loaded").set("string", luaTable);
        if (LuaString.s_metatable == null) {
            LuaString.s_metatable = luaTableTableOf;
        }
        return luaTable;
    }
}
