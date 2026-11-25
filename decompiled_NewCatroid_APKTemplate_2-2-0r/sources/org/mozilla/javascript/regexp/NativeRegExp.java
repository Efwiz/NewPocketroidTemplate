package org.mozilla.javascript.regexp;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.Undefined;

/* JADX INFO: loaded from: classes4.dex */
public class NativeRegExp extends IdScriptableObject implements Function {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ANCHOR_BOL = -2;
    private static final int INDEX_LEN = 2;
    private static final int Id_compile = 1;
    private static final int Id_exec = 4;
    private static final int Id_global = 3;
    private static final int Id_ignoreCase = 4;
    private static final int Id_lastIndex = 1;
    private static final int Id_multiline = 5;
    private static final int Id_prefix = 6;
    private static final int Id_source = 2;
    private static final int Id_test = 5;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    public static final int JSREG_FOLD = 2;
    public static final int JSREG_GLOB = 1;
    public static final int JSREG_MULTILINE = 4;
    public static final int MATCH = 1;
    private static final int MAX_INSTANCE_ID = 5;
    private static final int MAX_PROTOTYPE_ID = 8;
    public static final int PREFIX = 2;
    private static final Object REGEXP_TAG = new Object();
    private static final byte REOP_ALNUM = 9;
    private static final byte REOP_ALT = 31;
    private static final byte REOP_ALTPREREQ = 53;
    private static final byte REOP_ALTPREREQ2 = 55;
    private static final byte REOP_ALTPREREQi = 54;
    private static final byte REOP_ASSERT = 41;
    private static final byte REOP_ASSERTNOTTEST = 44;
    private static final byte REOP_ASSERTTEST = 43;
    private static final byte REOP_ASSERT_NOT = 42;
    private static final byte REOP_BACKREF = 13;
    private static final byte REOP_BOL = 2;
    private static final byte REOP_CLASS = 22;
    private static final byte REOP_DIGIT = 7;
    private static final byte REOP_DOT = 6;
    private static final byte REOP_EMPTY = 1;
    private static final byte REOP_END = 57;
    private static final byte REOP_ENDCHILD = 49;
    private static final byte REOP_EOL = 3;
    private static final byte REOP_FLAT = 14;
    private static final byte REOP_FLAT1 = 15;
    private static final byte REOP_FLAT1i = 17;
    private static final byte REOP_FLATi = 16;
    private static final byte REOP_JUMP = 32;
    private static final byte REOP_LPAREN = 29;
    private static final byte REOP_MINIMALOPT = 47;
    private static final byte REOP_MINIMALPLUS = 46;
    private static final byte REOP_MINIMALQUANT = 48;
    private static final byte REOP_MINIMALREPEAT = 52;
    private static final byte REOP_MINIMALSTAR = 45;
    private static final byte REOP_NCLASS = 23;
    private static final byte REOP_NONALNUM = 10;
    private static final byte REOP_NONDIGIT = 8;
    private static final byte REOP_NONSPACE = 12;
    private static final byte REOP_OPT = 28;
    private static final byte REOP_PLUS = 27;
    private static final byte REOP_QUANT = 25;
    private static final byte REOP_REPEAT = 51;
    private static final byte REOP_RPAREN = 30;
    private static final byte REOP_SIMPLE_END = 23;
    private static final byte REOP_SIMPLE_START = 1;
    private static final byte REOP_SPACE = 11;
    private static final byte REOP_STAR = 26;
    private static final byte REOP_UCFLAT1 = 18;
    private static final byte REOP_UCFLAT1i = 19;
    private static final byte REOP_WBDRY = 4;
    private static final byte REOP_WNONBDRY = 5;
    private static final int SymbolId_match = 7;
    private static final int SymbolId_search = 8;
    public static final int TEST = 0;
    private static final boolean debug = false;
    private static final long serialVersionUID = 4965263491464903264L;
    Object lastIndex = ScriptRuntime.zeroObj;
    private int lastIndexAttr = 6;
    private RECompiled re;

    private static boolean isControlLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean reopIsSimple(int i) {
        return i >= 1 && i <= 23;
    }

    private static int toASCIIHexDigit(int i) {
        if (i < 48) {
            return -1;
        }
        if (i <= 57) {
            return i - 48;
        }
        int i2 = i | 32;
        if (97 > i2 || i2 > 102) {
            return -1;
        }
        return i2 - 87;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 5;
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        NativeRegExp nativeRegExp = new NativeRegExp();
        nativeRegExp.re = compileRE(context, "", null, false);
        nativeRegExp.activatePrototypeMap(8);
        nativeRegExp.setParentScope(scriptable);
        nativeRegExp.setPrototype(getObjectPrototype(scriptable));
        NativeRegExpCtor nativeRegExpCtor = new NativeRegExpCtor();
        nativeRegExp.defineProperty(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, nativeRegExpCtor, 2);
        ScriptRuntime.setFunctionProtoAndParent(nativeRegExpCtor, scriptable);
        nativeRegExpCtor.setImmunePrototypeProperty(nativeRegExp);
        if (z) {
            nativeRegExp.sealObject();
            nativeRegExpCtor.sealObject();
        }
        defineProperty(scriptable, "RegExp", nativeRegExpCtor, 2);
    }

    NativeRegExp(Scriptable scriptable, RECompiled rECompiled) {
        this.re = rECompiled;
        setLastIndex(ScriptRuntime.zeroObj);
        ScriptRuntime.setBuiltinProtoAndParent(this, scriptable, TopLevel.Builtins.RegExp);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "RegExp";
    }

    @Override // org.mozilla.javascript.ScriptableObject
    public String getTypeOf() {
        return "object";
    }

    @Override // org.mozilla.javascript.Function, org.mozilla.javascript.Callable
    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (context.getLanguageVersion() < 200) {
            return execSub(context, scriptable, objArr, 1);
        }
        throw ScriptRuntime.notFunctionError(scriptable2);
    }

    @Override // org.mozilla.javascript.Function
    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        if (context.getLanguageVersion() < 200) {
            return (Scriptable) execSub(context, scriptable, objArr, 1);
        }
        throw ScriptRuntime.notFunctionError(this);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0039  */
    Scriptable compile(Context context, Scriptable scriptable, Object[] objArr) {
        String strEscapeRegExp;
        if (objArr.length > 0 && (objArr[0] instanceof NativeRegExp)) {
            if (objArr.length > 1 && objArr[1] != Undefined.instance) {
                throw ScriptRuntime.typeError0("msg.bad.regexp.compile");
            }
            NativeRegExp nativeRegExp = (NativeRegExp) objArr[0];
            this.re = nativeRegExp.re;
            setLastIndex(nativeRegExp.lastIndex);
            return this;
        }
        if (objArr.length != 0) {
            Object obj = objArr[0];
            if (obj instanceof Undefined) {
                strEscapeRegExp = "";
            } else {
                strEscapeRegExp = escapeRegExp(obj);
            }
        } else {
            strEscapeRegExp = "";
        }
        this.re = compileRE(context, strEscapeRegExp, (objArr.length <= 1 || objArr[1] == Undefined.instance) ? null : ScriptRuntime.toString(objArr[1]), false);
        setLastIndex(ScriptRuntime.zeroObj);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("/");
        if (this.re.source.length != 0) {
            sb.append(this.re.source);
        } else {
            sb.append("(?:)");
        }
        sb.append('/');
        if ((this.re.flags & 1) != 0) {
            sb.append('g');
        }
        if ((this.re.flags & 2) != 0) {
            sb.append('i');
        }
        if ((this.re.flags & 4) != 0) {
            sb.append(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL);
        }
        return sb.toString();
    }

    NativeRegExp() {
    }

    private static RegExpImpl getImpl(Context context) {
        return (RegExpImpl) ScriptRuntime.getRegExpProxy(context);
    }

    private static String escapeRegExp(Object obj) {
        String string = ScriptRuntime.toString(obj);
        StringBuilder sb = null;
        int i = 0;
        for (int iIndexOf = string.indexOf(47); iIndexOf > -1; iIndexOf = string.indexOf(47, iIndexOf + 1)) {
            if (iIndexOf == i || string.charAt(iIndexOf - 1) != '\\') {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append((CharSequence) string, i, iIndexOf);
                sb.append("\\/");
                i = iIndexOf + 1;
            }
        }
        if (sb == null) {
            return string;
        }
        sb.append((CharSequence) string, i, string.length());
        return sb.toString();
    }

    private Object execSub(Context context, Scriptable scriptable, Object[] objArr, int i) {
        String string;
        RegExpImpl impl = getImpl(context);
        if (objArr.length == 0) {
            string = impl.input;
            if (string == null) {
                string = ScriptRuntime.toString(Undefined.instance);
            }
        } else {
            string = ScriptRuntime.toString(objArr[0]);
        }
        String str = string;
        double integer = (this.re.flags & 1) != 0 ? ScriptRuntime.toInteger(this.lastIndex) : 0.0d;
        if (integer < 0.0d || str.length() < integer) {
            setLastIndex(ScriptRuntime.zeroObj);
            return null;
        }
        int[] iArr = {(int) integer};
        Object objExecuteRegExp = executeRegExp(context, scriptable, impl, str, iArr, i);
        if ((this.re.flags & 1) == 0) {
            return objExecuteRegExp;
        }
        if (objExecuteRegExp == null || objExecuteRegExp == Undefined.instance) {
            setLastIndex(ScriptRuntime.zeroObj);
            return objExecuteRegExp;
        }
        setLastIndex(Double.valueOf(iArr[0]));
        return objExecuteRegExp;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static RECompiled compileRE(Context context, String str, String str2, boolean z) {
        int i;
        int i2;
        RECompiled rECompiled = new RECompiled(str);
        int length = str.length();
        if (str2 != null) {
            i = 0;
            for (int i3 = 0; i3 < str2.length(); i3++) {
                char cCharAt = str2.charAt(i3);
                if (cCharAt == 'g') {
                    i2 = 1;
                } else if (cCharAt == 'i') {
                    i2 = 2;
                } else if (cCharAt == 'm') {
                    i2 = 4;
                } else {
                    reportError("msg.invalid.re.flag", String.valueOf(cCharAt));
                    i2 = 0;
                }
                if ((i & i2) != 0) {
                    reportError("msg.invalid.re.flag", String.valueOf(cCharAt));
                }
                i |= i2;
            }
        } else {
            i = 0;
        }
        rECompiled.flags = i;
        CompilerState compilerState = new CompilerState(context, rECompiled.source, length, i);
        if (z && length > 0) {
            compilerState.result = new RENode((byte) 14);
            compilerState.result.chr = compilerState.cpbegin[0];
            compilerState.result.length = length;
            compilerState.result.flatIndex = 0;
            compilerState.progLength += 5;
        } else {
            if (!parseDisjunction(compilerState)) {
                return null;
            }
            if (compilerState.maxBackReference > compilerState.parenCount) {
                compilerState = new CompilerState(context, rECompiled.source, length, i);
                compilerState.backReferenceLimit = compilerState.parenCount;
                if (!parseDisjunction(compilerState)) {
                    return null;
                }
            }
        }
        rECompiled.program = new byte[compilerState.progLength + 1];
        if (compilerState.classCount != 0) {
            rECompiled.classList = new RECharSet[compilerState.classCount];
            rECompiled.classCount = compilerState.classCount;
        }
        rECompiled.program[emitREBytecode(compilerState, rECompiled, 0, compilerState.result)] = 57;
        rECompiled.parenCount = compilerState.parenCount;
        byte b = rECompiled.program[0];
        if (b == 2) {
            rECompiled.anchorCh = -2;
        } else if (b != 31) {
            switch (b) {
                case 14:
                case 16:
                    rECompiled.anchorCh = rECompiled.source[getIndex(rECompiled.program, 1)];
                    break;
                case 15:
                case 17:
                    rECompiled.anchorCh = (char) (rECompiled.program[1] & 255);
                    break;
                case 18:
                case 19:
                    rECompiled.anchorCh = (char) getIndex(rECompiled.program, 1);
                    break;
            }
        } else {
            RENode rENode = compilerState.result;
            if (rENode.kid.op == 2 && rENode.kid2.op == 2) {
                rECompiled.anchorCh = -2;
            }
        }
        return rECompiled;
    }

    private static boolean isWord(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || isDigit(c) || c == '_';
    }

    private static boolean isLineTerm(char c) {
        return ScriptRuntime.isJSLineTerminator(c);
    }

    private static boolean isREWhiteSpace(int i) {
        return ScriptRuntime.isJSWhitespaceOrLineTerminator(i);
    }

    private static char upcase(char c) {
        if (c < 128) {
            return ('a' > c || c > 'z') ? c : (char) (c - ' ');
        }
        char upperCase = Character.toUpperCase(c);
        return upperCase < 128 ? c : upperCase;
    }

    private static char downcase(char c) {
        if (c < 128) {
            return ('A' > c || c > 'Z') ? c : (char) (c + StringUtil.SPACE);
        }
        char lowerCase = Character.toLowerCase(c);
        return lowerCase < 128 ? c : lowerCase;
    }

    private static boolean parseDisjunction(CompilerState compilerState) {
        if (!parseAlternative(compilerState)) {
            return false;
        }
        char[] cArr = compilerState.cpbegin;
        int i = compilerState.cp;
        if (i != cArr.length && cArr[i] == '|') {
            compilerState.cp++;
            RENode rENode = new RENode((byte) 31);
            rENode.kid = compilerState.result;
            if (!parseDisjunction(compilerState)) {
                return false;
            }
            rENode.kid2 = compilerState.result;
            compilerState.result = rENode;
            if (rENode.kid.op == 14 && rENode.kid2.op == 14) {
                rENode.op = (compilerState.flags & 2) == 0 ? (byte) 53 : (byte) 54;
                rENode.chr = rENode.kid.chr;
                rENode.index = rENode.kid2.chr;
                compilerState.progLength += 13;
            } else if (rENode.kid.op == 22 && rENode.kid.index < 256 && rENode.kid2.op == 14 && (compilerState.flags & 2) == 0) {
                rENode.op = (byte) 55;
                rENode.chr = rENode.kid2.chr;
                rENode.index = rENode.kid.index;
                compilerState.progLength += 13;
            } else if (rENode.kid.op == 14 && rENode.kid2.op == 22 && rENode.kid2.index < 256 && (compilerState.flags & 2) == 0) {
                rENode.op = (byte) 55;
                rENode.chr = rENode.kid.chr;
                rENode.index = rENode.kid2.index;
                compilerState.progLength += 13;
            } else {
                compilerState.progLength += 9;
            }
        }
        return true;
    }

    private static boolean parseAlternative(CompilerState compilerState) {
        char[] cArr = compilerState.cpbegin;
        RENode rENode = null;
        RENode rENode2 = null;
        while (compilerState.cp != compilerState.cpend && cArr[compilerState.cp] != '|' && (compilerState.parenNesting == 0 || cArr[compilerState.cp] != ')')) {
            if (!parseTerm(compilerState)) {
                return false;
            }
            if (rENode == null) {
                rENode = compilerState.result;
                rENode2 = rENode;
            } else {
                rENode2.next = compilerState.result;
            }
            while (rENode2.next != null) {
                rENode2 = rENode2.next;
            }
        }
        if (rENode == null) {
            compilerState.result = new RENode((byte) 1);
        } else {
            compilerState.result = rENode;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:31:0x0053
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static boolean calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState r11, org.mozilla.javascript.regexp.RENode r12, char[] r13, int r14, int r15) {
        /*
            Method dump skipped, instruction units count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState, org.mozilla.javascript.regexp.RENode, char[], int, int):boolean");
    }

    private static void doFlat(CompilerState compilerState, char c) {
        compilerState.result = new RENode((byte) 14);
        compilerState.result.chr = c;
        compilerState.result.length = 1;
        compilerState.result.flatIndex = -1;
        compilerState.progLength += 3;
    }

    private static int getDecimalValue(char c, CompilerState compilerState, int i, String str) {
        int i2 = compilerState.cp;
        char[] cArr = compilerState.cpbegin;
        int i3 = c - 48;
        boolean z = false;
        while (compilerState.cp != compilerState.cpend) {
            char c2 = cArr[compilerState.cp];
            if (!isDigit(c2)) {
                break;
            }
            if (!z && (i3 = (i3 * 10) + (c2 - '0')) >= i) {
                i3 = i;
                z = true;
            }
            compilerState.cp++;
        }
        if (z) {
            reportError(str, String.valueOf(cArr, i2, compilerState.cp - i2));
        }
        return i3;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v105 */
    /* JADX WARN: Type inference failed for: r4v67 */
    /* JADX WARN: Type inference failed for: r4v68, types: [int] */
    /* JADX WARN: Type inference failed for: r4v69, types: [int] */
    /* JADX WARN: Type inference failed for: r4v74, types: [char] */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:72:0x0158
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static boolean parseTerm(org.mozilla.javascript.regexp.CompilerState r16) {
        /*
            Method dump skipped, instruction units count: 1202
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.parseTerm(org.mozilla.javascript.regexp.CompilerState):boolean");
    }

    private static void resolveForwardJump(byte[] bArr, int i, int i2) {
        if (i > i2) {
            throw Kit.codeBug();
        }
        addIndex(bArr, i, i2 - i);
    }

    private static int getOffset(byte[] bArr, int i) {
        return getIndex(bArr, i);
    }

    private static int addIndex(byte[] bArr, int i, int i2) {
        if (i2 < 0) {
            throw Kit.codeBug();
        }
        if (i2 > 65535) {
            throw Context.reportRuntimeError("Too complex regexp");
        }
        bArr[i] = (byte) (i2 >> 8);
        bArr[i + 1] = (byte) i2;
        return i + 2;
    }

    private static int getIndex(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    /* JADX WARN: Switch 'out' block B:69:0x010a for B:22:0x0030 already processed. Defaulting to fallback option. */
    private static int emitREBytecode(CompilerState compilerState, RECompiled rECompiled, int i, RENode rENode) {
        int i2;
        byte[] bArr = rECompiled.program;
        while (rENode != null) {
            int iAddIndex = i + 1;
            bArr[i] = rENode.op;
            byte b = rENode.op;
            if (b != 1) {
                if (b != 22) {
                    if (b == 25) {
                        if (rENode.min == 0 && rENode.max == -1) {
                            bArr[i] = rENode.greedy ? (byte) 26 : (byte) 45;
                        } else if (rENode.min == 0 && rENode.max == 1) {
                            bArr[i] = rENode.greedy ? (byte) 28 : (byte) 47;
                        } else if (rENode.min == 1 && rENode.max == -1) {
                            bArr[i] = rENode.greedy ? (byte) 27 : (byte) 46;
                        } else {
                            if (!rENode.greedy) {
                                bArr[i] = 48;
                            }
                            iAddIndex = addIndex(bArr, addIndex(bArr, iAddIndex, rENode.min), rENode.max + 1);
                        }
                        int iAddIndex2 = addIndex(bArr, addIndex(bArr, iAddIndex, rENode.parenCount), rENode.parenIndex);
                        int iEmitREBytecode = emitREBytecode(compilerState, rECompiled, iAddIndex2 + 2, rENode.kid);
                        i2 = iEmitREBytecode + 1;
                        bArr[iEmitREBytecode] = 49;
                        resolveForwardJump(bArr, iAddIndex2, i2);
                    } else if (b == 29) {
                        int iEmitREBytecode2 = emitREBytecode(compilerState, rECompiled, addIndex(bArr, iAddIndex, rENode.parenIndex), rENode.kid);
                        bArr[iEmitREBytecode2] = 30;
                        i = addIndex(bArr, iEmitREBytecode2 + 1, rENode.parenIndex);
                    } else if (b == 31) {
                        RENode rENode2 = rENode.kid2;
                        int iEmitREBytecode3 = emitREBytecode(compilerState, rECompiled, iAddIndex + 2, rENode.kid);
                        int i3 = iEmitREBytecode3 + 1;
                        bArr[iEmitREBytecode3] = 32;
                        int i4 = iEmitREBytecode3 + 3;
                        resolveForwardJump(bArr, iAddIndex, i4);
                        int iEmitREBytecode4 = emitREBytecode(compilerState, rECompiled, i4, rENode2);
                        int i5 = iEmitREBytecode4 + 1;
                        bArr[iEmitREBytecode4] = 32;
                        i = iEmitREBytecode4 + 3;
                        resolveForwardJump(bArr, i3, i);
                        resolveForwardJump(bArr, i5, i);
                    } else if (b == 13) {
                        i = addIndex(bArr, iAddIndex, rENode.parenIndex);
                    } else if (b == 14) {
                        if (rENode.flatIndex != -1) {
                            while (rENode.next != null && rENode.next.op == 14 && rENode.flatIndex + rENode.length == rENode.next.flatIndex) {
                                rENode.length += rENode.next.length;
                                rENode.next = rENode.next.next;
                            }
                        }
                        if (rENode.flatIndex != -1 && rENode.length > 1) {
                            if ((compilerState.flags & 2) != 0) {
                                bArr[i] = 16;
                            } else {
                                bArr[i] = 14;
                            }
                            i = addIndex(bArr, addIndex(bArr, iAddIndex, rENode.flatIndex), rENode.length);
                        } else if (rENode.chr < 256) {
                            if ((compilerState.flags & 2) != 0) {
                                bArr[i] = 17;
                            } else {
                                bArr[i] = 15;
                            }
                            i += 2;
                            bArr[iAddIndex] = (byte) rENode.chr;
                        } else {
                            if ((compilerState.flags & 2) != 0) {
                                bArr[i] = 19;
                            } else {
                                bArr[i] = 18;
                            }
                            i = addIndex(bArr, iAddIndex, rENode.chr);
                        }
                    } else if (b == 41) {
                        int iEmitREBytecode5 = emitREBytecode(compilerState, rECompiled, i + 3, rENode.kid);
                        i2 = iEmitREBytecode5 + 1;
                        bArr[iEmitREBytecode5] = 43;
                        resolveForwardJump(bArr, iAddIndex, i2);
                    } else if (b != 42) {
                        switch (b) {
                            case 53:
                            case 54:
                            case 55:
                                boolean z = rENode.op == 54;
                                char cUpcase = rENode.chr;
                                if (z) {
                                    cUpcase = upcase(cUpcase);
                                }
                                addIndex(bArr, iAddIndex, cUpcase);
                                int i6 = i + 3;
                                int iUpcase = rENode.index;
                                if (z) {
                                    iUpcase = upcase((char) iUpcase);
                                }
                                addIndex(bArr, i6, iUpcase);
                                iAddIndex = i + 5;
                                RENode rENode3 = rENode.kid2;
                                int iEmitREBytecode6 = emitREBytecode(compilerState, rECompiled, iAddIndex + 2, rENode.kid);
                                int i7 = iEmitREBytecode6 + 1;
                                bArr[iEmitREBytecode6] = 32;
                                int i8 = iEmitREBytecode6 + 3;
                                resolveForwardJump(bArr, iAddIndex, i8);
                                int iEmitREBytecode7 = emitREBytecode(compilerState, rECompiled, i8, rENode3);
                                int i9 = iEmitREBytecode7 + 1;
                                bArr[iEmitREBytecode7] = 32;
                                i = iEmitREBytecode7 + 3;
                                resolveForwardJump(bArr, i7, i);
                                resolveForwardJump(bArr, i9, i);
                                break;
                            default:
                                i = iAddIndex;
                                continue;
                        }
                    } else {
                        int iEmitREBytecode8 = emitREBytecode(compilerState, rECompiled, i + 3, rENode.kid);
                        i2 = iEmitREBytecode8 + 1;
                        bArr[iEmitREBytecode8] = 44;
                        resolveForwardJump(bArr, iAddIndex, i2);
                    }
                    i = i2;
                } else {
                    if (!rENode.sense) {
                        bArr[i] = 23;
                    }
                    i = addIndex(bArr, iAddIndex, rENode.index);
                    rECompiled.classList[rENode.index] = new RECharSet(rENode.bmsize, rENode.startIndex, rENode.kidlen, rENode.sense);
                }
            }
            rENode = rENode.next;
        }
        return i;
    }

    private static void pushProgState(REGlobalData rEGlobalData, int i, int i2, int i3, REBackTrackData rEBackTrackData, int i4, int i5) {
        rEGlobalData.stateStackTop = new REProgState(rEGlobalData.stateStackTop, i, i2, i3, rEBackTrackData, i4, i5);
    }

    private static REProgState popProgState(REGlobalData rEGlobalData) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.stateStackTop = rEProgState.previous;
        return rEProgState;
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b, int i) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b, i, rEGlobalData.cp, rEProgState.continuationOp, rEProgState.continuationPc);
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b, int i, int i2, int i3, int i4) {
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b, i, i2, i3, i4);
    }

    private static boolean flatNMatcher(REGlobalData rEGlobalData, int i, int i2, String str, int i3) {
        if (rEGlobalData.cp + i2 > i3) {
            return false;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            if (rEGlobalData.regexp.source[i + i4] != str.charAt(rEGlobalData.cp + i4)) {
                return false;
            }
        }
        rEGlobalData.cp += i2;
        return true;
    }

    private static boolean flatNIMatcher(REGlobalData rEGlobalData, int i, int i2, String str, int i3) {
        if (rEGlobalData.cp + i2 > i3) {
            return false;
        }
        char[] cArr = rEGlobalData.regexp.source;
        for (int i4 = 0; i4 < i2; i4++) {
            char c = cArr[i + i4];
            char cCharAt = str.charAt(rEGlobalData.cp + i4);
            if (c != cCharAt && upcase(c) != upcase(cCharAt)) {
                return false;
            }
        }
        rEGlobalData.cp += i2;
        return true;
    }

    private static boolean backrefMatcher(REGlobalData rEGlobalData, int i, String str, int i2) {
        if (rEGlobalData.parens == null || i >= rEGlobalData.parens.length) {
            return false;
        }
        int iParensIndex = rEGlobalData.parensIndex(i);
        if (iParensIndex == -1) {
            return true;
        }
        int iParensLength = rEGlobalData.parensLength(i);
        if (rEGlobalData.cp + iParensLength > i2) {
            return false;
        }
        if ((rEGlobalData.regexp.flags & 2) != 0) {
            for (int i3 = 0; i3 < iParensLength; i3++) {
                char cCharAt = str.charAt(iParensIndex + i3);
                char cCharAt2 = str.charAt(rEGlobalData.cp + i3);
                if (cCharAt != cCharAt2 && upcase(cCharAt) != upcase(cCharAt2)) {
                    return false;
                }
            }
        } else if (!str.regionMatches(iParensIndex, str, rEGlobalData.cp, iParensLength)) {
            return false;
        }
        rEGlobalData.cp += iParensLength;
        return true;
    }

    private static void addCharacterToCharSet(RECharSet rECharSet, char c) {
        int i = c / '\b';
        if (c >= rECharSet.length) {
            throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
        }
        byte[] bArr = rECharSet.bits;
        bArr[i] = (byte) ((1 << (c & 7)) | bArr[i]);
    }

    private static void addCharacterRangeToCharSet(RECharSet rECharSet, char c, char c2) {
        int i = c / '\b';
        int i2 = c2 / '\b';
        if (c2 >= rECharSet.length || c > c2) {
            throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
        }
        char c3 = (char) (c & 7);
        char c4 = (char) (c2 & 7);
        if (i == i2) {
            byte[] bArr = rECharSet.bits;
            bArr[i] = (byte) (((255 >> (7 - (c4 - c3))) << c3) | bArr[i]);
            return;
        }
        byte[] bArr2 = rECharSet.bits;
        bArr2[i] = (byte) ((255 << c3) | bArr2[i]);
        while (true) {
            i++;
            if (i < i2) {
                rECharSet.bits[i] = -1;
            } else {
                byte[] bArr3 = rECharSet.bits;
                bArr3[i2] = (byte) (bArr3[i2] | (255 >> (7 - c4)));
                return;
            }
        }
    }

    private static void processCharSet(REGlobalData rEGlobalData, RECharSet rECharSet) {
        synchronized (rECharSet) {
            if (!rECharSet.converted) {
                processCharSetImpl(rEGlobalData, rECharSet);
                rECharSet.converted = true;
            }
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:24:0x0065. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0068. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:26:0x006b. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:41:0x0092
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:127)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:75)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static void processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData r18, org.mozilla.javascript.regexp.RECharSet r19) {
        /*
            Method dump skipped, instruction units count: 520
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData, org.mozilla.javascript.regexp.RECharSet):void");
    }

    private static boolean classMatcher(REGlobalData rEGlobalData, RECharSet rECharSet, char c) {
        if (!rECharSet.converted) {
            processCharSet(rEGlobalData, rECharSet);
        }
        int i = c >> 3;
        boolean z = true;
        if (rECharSet.length != 0 && c < rECharSet.length && (rECharSet.bits[i] & (1 << (c & 7))) != 0) {
            z = false;
        }
        return rECharSet.sense ^ z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:101:0x01ce A[PHI: r5
      0x01ce: PHI (r5v16 boolean) = (r5v10 boolean), (r5v20 boolean), (r5v20 boolean) binds: [B:99:0x01cb, B:85:0x0199, B:87:0x01a5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:116:0x01fb A[PHI: r7
      0x01fb: PHI (r7v13 int) = 
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v0 int)
      (r7v7 int)
      (r7v11 int)
      (r7v11 int)
      (r7v12 int)
      (r7v12 int)
      (r7v14 int)
      (r7v14 int)
     binds: [B:113:0x01ec, B:115:0x01f9, B:106:0x01d7, B:108:0x01e3, B:73:0x016d, B:75:0x0179, B:68:0x0156, B:70:0x0162, B:63:0x013f, B:65:0x014b, B:58:0x0128, B:60:0x0134, B:53:0x0111, B:55:0x011d, B:48:0x00fa, B:50:0x0106, B:43:0x00e3, B:45:0x00ef, B:39:0x00c0, B:19:0x005c, B:21:0x0064, B:12:0x0038, B:16:0x004a, B:7:0x0014, B:9:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:118:0x01fe A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:119:0x0200  */
    /* JADX WARN: Code duplicated, block: B:121:0x0203  */
    private static int simpleMatch(REGlobalData rEGlobalData, String str, int i, byte[] bArr, int i2, int i3, boolean z) {
        boolean z2;
        int i4;
        char cCharAt;
        int i5 = rEGlobalData.cp;
        boolean zBackrefMatcher = true;
        switch (i) {
            case 1:
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 2:
                if (rEGlobalData.cp != 0 && (!rEGlobalData.multiline || !isLineTerm(str.charAt(rEGlobalData.cp - 1)))) {
                    zBackrefMatcher = false;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 3:
                if (rEGlobalData.cp != i3 && (!rEGlobalData.multiline || !isLineTerm(str.charAt(rEGlobalData.cp)))) {
                    zBackrefMatcher = false;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 4:
                z2 = rEGlobalData.cp == 0 || !isWord(str.charAt(rEGlobalData.cp - 1));
                if (rEGlobalData.cp < i3 && isWord(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                }
                zBackrefMatcher ^= z2;
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 5:
                z2 = rEGlobalData.cp == 0 || !isWord(str.charAt(rEGlobalData.cp - 1));
                if (rEGlobalData.cp >= i3 || !isWord(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                }
                zBackrefMatcher ^= z2;
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 6:
                if (rEGlobalData.cp == i3 || isLineTerm(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 7:
                if (rEGlobalData.cp == i3 || !isDigit(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 8:
                if (rEGlobalData.cp == i3 || isDigit(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 9:
                if (rEGlobalData.cp == i3 || !isWord(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 10:
                if (rEGlobalData.cp == i3 || isWord(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 11:
                if (rEGlobalData.cp == i3 || !isREWhiteSpace(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 12:
                if (rEGlobalData.cp == i3 || isREWhiteSpace(str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 13:
                int index = getIndex(bArr, i2);
                i2 += 2;
                zBackrefMatcher = backrefMatcher(rEGlobalData, index, str, i3);
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 14:
                int index2 = getIndex(bArr, i2);
                int index3 = getIndex(bArr, i2 + 2);
                i2 += 4;
                zBackrefMatcher = flatNMatcher(rEGlobalData, index2, index3, str, i3);
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 15:
                i4 = i2 + 1;
                char c = (char) (bArr[i2] & 255);
                if (rEGlobalData.cp != i3 && str.charAt(rEGlobalData.cp) == c) {
                    rEGlobalData.cp++;
                    i2 = i4;
                    if (zBackrefMatcher) {
                        rEGlobalData.cp = i5;
                        return -1;
                    }
                    if (!z) {
                        rEGlobalData.cp = i5;
                    }
                    return i2;
                }
                i2 = i4;
                zBackrefMatcher = false;
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 16:
                int index4 = getIndex(bArr, i2);
                int index5 = getIndex(bArr, i2 + 2);
                i2 += 4;
                zBackrefMatcher = flatNIMatcher(rEGlobalData, index4, index5, str, i3);
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 17:
                i4 = i2 + 1;
                char c2 = (char) (bArr[i2] & 255);
                if (rEGlobalData.cp != i3) {
                    char cCharAt2 = str.charAt(rEGlobalData.cp);
                    if (c2 == cCharAt2 || upcase(c2) == upcase(cCharAt2)) {
                        rEGlobalData.cp++;
                    } else {
                        zBackrefMatcher = false;
                    }
                    i2 = i4;
                    if (zBackrefMatcher) {
                        rEGlobalData.cp = i5;
                        return -1;
                    }
                    if (!z) {
                        rEGlobalData.cp = i5;
                    }
                    return i2;
                }
                i2 = i4;
                zBackrefMatcher = false;
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 18:
                char index6 = (char) getIndex(bArr, i2);
                i2 += 2;
                if (rEGlobalData.cp == i3 || str.charAt(rEGlobalData.cp) != index6) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 19:
                char index7 = (char) getIndex(bArr, i2);
                i2 += 2;
                if (rEGlobalData.cp == i3 || !(index7 == (cCharAt = str.charAt(rEGlobalData.cp)) || upcase(index7) == upcase(cCharAt))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
            case 20:
            case 21:
            default:
                throw Kit.codeBug();
            case 22:
            case 23:
                int index8 = getIndex(bArr, i2);
                i2 += 2;
                if (rEGlobalData.cp == i3 || !classMatcher(rEGlobalData, rEGlobalData.regexp.classList[index8], str.charAt(rEGlobalData.cp))) {
                    zBackrefMatcher = false;
                } else {
                    rEGlobalData.cp++;
                }
                if (zBackrefMatcher) {
                    rEGlobalData.cp = i5;
                    return -1;
                }
                if (!z) {
                    rEGlobalData.cp = i5;
                }
                return i2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:141:0x02d9 A[PHI: r14
      0x02d9: PHI (r14v43 int) = (r14v42 int), (r14v44 int) binds: [B:140:0x02d7, B:133:0x02a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:152:0x0322 A[PHI: r14
      0x0322: PHI (r14v14 int) = (r14v2 int), (r14v26 int), (r14v26 int), (r14v26 int), (r14v26 int) binds: [B:203:0x0322, B:45:0x00bb, B:46:0x00bd, B:38:0x00a4, B:40:0x00b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:154:0x0336  */
    /* JADX WARN: Code duplicated, block: B:156:0x0344  */
    /* JADX WARN: Code duplicated, block: B:157:0x0349  */
    /* JADX WARN: Code duplicated, block: B:158:0x0352  */
    /* JADX WARN: Code duplicated, block: B:165:0x0390  */
    /* JADX WARN: Code duplicated, block: B:168:0x0398  */
    /* JADX WARN: Code duplicated, block: B:169:0x039a  */
    /* JADX WARN: Code duplicated, block: B:170:0x039c  */
    /* JADX WARN: Code duplicated, block: B:171:0x039e  */
    /* JADX WARN: Code duplicated, block: B:172:0x03a0  */
    /* JADX WARN: Code duplicated, block: B:174:0x03a5  */
    /* JADX WARN: Code duplicated, block: B:177:0x03ab  */
    /* JADX WARN: Code duplicated, block: B:179:0x03b1  */
    /* JADX WARN: Code duplicated, block: B:183:0x03d1  */
    /* JADX WARN: Code duplicated, block: B:184:0x03e0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:185:0x03e2  */
    /* JADX WARN: Code duplicated, block: B:186:0x03ed  */
    /* JADX WARN: Code duplicated, block: B:199:0x0393 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private static boolean executeREBytecode(REGlobalData rEGlobalData, String str, int i) {
        int i2;
        int offset;
        int i3;
        int i4;
        int i5;
        int iSimpleMatch;
        int i6;
        int i7;
        int offset2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        byte b;
        boolean z;
        byte[] bArr = rEGlobalData.regexp.program;
        int i20 = 0;
        int i21 = bArr[0];
        int i22 = 1;
        char c = '9';
        int offset3 = 1;
        if (rEGlobalData.regexp.anchorCh < 0 && reopIsSimple(i21)) {
            while (true) {
                if (rEGlobalData.cp > i) {
                    z = false;
                    break;
                }
                int iSimpleMatch2 = simpleMatch(rEGlobalData, str, i21, bArr, 1, i, true);
                if (iSimpleMatch2 >= 0) {
                    offset3 = iSimpleMatch2 + 1;
                    i21 = bArr[iSimpleMatch2];
                    z = true;
                    break;
                }
                rEGlobalData.skipped++;
                rEGlobalData.cp++;
            }
            if (!z) {
                return false;
            }
        }
        int i23 = 0;
        int i24 = i21;
        int i25 = 57;
        offset3 = 0;
        while (true) {
            if (reopIsSimple(i24)) {
                int iSimpleMatch3 = simpleMatch(rEGlobalData, str, i24, bArr, offset3, i, true);
                int i26 = iSimpleMatch3 >= 0 ? i22 : i20;
                if (i26 != 0) {
                    offset3 = iSimpleMatch3;
                }
                i23 = i26;
            } else {
                if (i24 == c) {
                    return i22;
                }
                switch (i24) {
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        switch (i24) {
                            case 25:
                                i6 = 1;
                                int offset4 = getOffset(bArr, offset3);
                                offset2 = getOffset(bArr, offset3 + 2) - 1;
                                i10 = offset4;
                                i8 = offset3 + 4;
                                i9 = i6;
                                i11 = i8;
                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                if (i9 != 0) {
                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                    i24 = bArr[i11 + 6];
                                    i25 = 51;
                                    offset3 = i11 + 7;
                                    offset3 = i11;
                                } else if (i10 != 0) {
                                    offset3 = i11 + 7;
                                    i24 = bArr[i11 + 6];
                                    offset3 = i11;
                                    i25 = 52;
                                } else {
                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                    popProgState(rEGlobalData);
                                    int i27 = i11 + 4;
                                    int offset5 = i27 + getOffset(bArr, i27);
                                    offset3 = offset5 + 1;
                                    i24 = bArr[offset5];
                                }
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            case 26:
                                i7 = 1;
                                offset2 = -1;
                                i8 = offset3;
                                i9 = i7;
                                i10 = i20;
                                i11 = i8;
                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                if (i9 != 0) {
                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                    i24 = bArr[i11 + 6];
                                    i25 = 51;
                                    offset3 = i11 + 7;
                                    offset3 = i11;
                                } else if (i10 != 0) {
                                    offset3 = i11 + 7;
                                    i24 = bArr[i11 + 6];
                                    offset3 = i11;
                                    i25 = 52;
                                } else {
                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                    popProgState(rEGlobalData);
                                    int i28 = i11 + 4;
                                    int offset6 = i28 + getOffset(bArr, i28);
                                    offset3 = offset6 + 1;
                                    i24 = bArr[offset6];
                                }
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            case 27:
                                i6 = 1;
                                offset2 = -1;
                                i8 = offset3;
                                i10 = 1;
                                i9 = i6;
                                i11 = i8;
                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                if (i9 != 0) {
                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                    i24 = bArr[i11 + 6];
                                    i25 = 51;
                                    offset3 = i11 + 7;
                                    offset3 = i11;
                                } else if (i10 != 0) {
                                    offset3 = i11 + 7;
                                    i24 = bArr[i11 + 6];
                                    offset3 = i11;
                                    i25 = 52;
                                } else {
                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                    popProgState(rEGlobalData);
                                    int i29 = i11 + 4;
                                    int offset7 = i29 + getOffset(bArr, i29);
                                    offset3 = offset7 + 1;
                                    i24 = bArr[offset7];
                                }
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            case 28:
                                i6 = 1;
                                i10 = i20;
                                i8 = offset3;
                                offset2 = 1;
                                i9 = i6;
                                i11 = i8;
                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                if (i9 != 0) {
                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                    i24 = bArr[i11 + 6];
                                    i25 = 51;
                                    offset3 = i11 + 7;
                                    offset3 = i11;
                                } else if (i10 != 0) {
                                    offset3 = i11 + 7;
                                    i24 = bArr[i11 + 6];
                                    offset3 = i11;
                                    i25 = 52;
                                } else {
                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                    popProgState(rEGlobalData);
                                    int i210 = i11 + 4;
                                    int offset8 = i210 + getOffset(bArr, i210);
                                    offset3 = offset8 + 1;
                                    i24 = bArr[offset8];
                                }
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            default:
                                switch (i24) {
                                    case 45:
                                        i7 = i20;
                                        offset2 = -1;
                                        i8 = offset3;
                                        i9 = i7;
                                        i10 = i20;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i211 = i11 + 4;
                                            int offset9 = i211 + getOffset(bArr, i211);
                                            offset3 = offset9 + 1;
                                            i24 = bArr[offset9];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 46:
                                        i6 = i20;
                                        offset2 = -1;
                                        i8 = offset3;
                                        i10 = 1;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i212 = i11 + 4;
                                            int offset10 = i212 + getOffset(bArr, i212);
                                            offset3 = offset10 + 1;
                                            i24 = bArr[offset10];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 47:
                                        i6 = i20;
                                        i10 = i20;
                                        i8 = offset3;
                                        offset2 = 1;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i213 = i11 + 4;
                                            int offset11 = i213 + getOffset(bArr, i213);
                                            offset3 = offset11 + 1;
                                            i24 = bArr[offset11];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 48:
                                        i6 = i20;
                                        int offset12 = getOffset(bArr, offset3);
                                        offset2 = getOffset(bArr, offset3 + 2) - 1;
                                        i10 = offset12;
                                        i8 = offset3 + 4;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i214 = i11 + 4;
                                            int offset13 = i214 + getOffset(bArr, i214);
                                            offset3 = offset13 + 1;
                                            i24 = bArr[offset13];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    default:
                                        throw Kit.codeBug();
                                }
                                break;
                        }
                        break;
                    case 29:
                        int i30 = offset3 + 2;
                        i20 = 0;
                        rEGlobalData.setParens(getIndex(bArr, offset3), rEGlobalData.cp, 0);
                        offset3 += 3;
                        i24 = bArr[i30];
                        i22 = 1;
                        c = '9';
                        break;
                    case 30:
                        int index = getIndex(bArr, offset3);
                        int i31 = offset3 + 2;
                        int iParensIndex = rEGlobalData.parensIndex(index);
                        rEGlobalData.setParens(index, iParensIndex, rEGlobalData.cp - iParensIndex);
                        offset3 += 3;
                        i24 = bArr[i31];
                        i20 = 0;
                        i22 = 1;
                        c = '9';
                        break;
                    case 31:
                        offset = offset3 + getOffset(bArr, offset3);
                        i3 = offset3 + 3;
                        i4 = bArr[offset3 + 2];
                        int i32 = rEGlobalData.cp;
                        if (reopIsSimple(i4)) {
                            iSimpleMatch = simpleMatch(rEGlobalData, str, i4, bArr, i3, i, true);
                            if (iSimpleMatch < 0) {
                                offset3 = offset + 1;
                                i24 = bArr[offset];
                            } else {
                                i5 = bArr[iSimpleMatch];
                                offset3 = iSimpleMatch + 1;
                                i23 = 1;
                            }
                            i20 = 0;
                            i22 = 1;
                            c = '9';
                        } else {
                            i5 = i4;
                            offset3 = i3;
                        }
                        pushBackTrackState(rEGlobalData, bArr[offset], offset + 1, i32, i25, offset3);
                        i24 = i5;
                        i20 = 0;
                        i22 = 1;
                        c = '9';
                        break;
                    case 32:
                        int offset14 = offset3 + getOffset(bArr, offset3);
                        i2 = offset14 + 1;
                        i24 = bArr[offset14];
                        offset3 = i2;
                        i20 = 0;
                        i22 = 1;
                        c = '9';
                        break;
                    default:
                        switch (i24) {
                            case 41:
                                int index2 = offset3 + getIndex(bArr, offset3);
                                int i33 = offset3 + 2;
                                offset3 += 3;
                                int i34 = bArr[i33];
                                if (reopIsSimple(i34) && simpleMatch(rEGlobalData, str, i34, bArr, offset3, i, false) < 0) {
                                    i23 = 0;
                                } else {
                                    pushProgState(rEGlobalData, 0, 0, rEGlobalData.cp, rEGlobalData.backTrackStackTop, i25, offset3);
                                    pushBackTrackState(rEGlobalData, (byte) 43, index2);
                                    i24 = i34;
                                }
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            case 42:
                                int index3 = offset3 + getIndex(bArr, offset3);
                                int i35 = offset3 + 2;
                                offset3 += 3;
                                int i36 = bArr[i35];
                                if (reopIsSimple(i36)) {
                                    b = 44;
                                    int iSimpleMatch4 = simpleMatch(rEGlobalData, str, i36, bArr, offset3, i, false);
                                    if (iSimpleMatch4 >= 0 && bArr[iSimpleMatch4] == ',') {
                                        i23 = 0;
                                        break;
                                    }
                                    i20 = 0;
                                    i22 = 1;
                                    c = '9';
                                } else {
                                    b = 44;
                                }
                                pushProgState(rEGlobalData, 0, 0, rEGlobalData.cp, rEGlobalData.backTrackStackTop, i25, offset3);
                                pushBackTrackState(rEGlobalData, b, index3);
                                i24 = i36;
                                i20 = 0;
                                i22 = 1;
                                c = '9';
                                break;
                            case 43:
                            case 44:
                                REProgState rEProgStatePopProgState = popProgState(rEGlobalData);
                                rEGlobalData.cp = rEProgStatePopProgState.index;
                                rEGlobalData.backTrackStackTop = rEProgStatePopProgState.backTrack;
                                offset3 = rEProgStatePopProgState.continuationPc;
                                int i37 = rEProgStatePopProgState.continuationOp;
                                if (i24 == 44) {
                                    i23 ^= 1;
                                }
                                i25 = i37;
                                break;
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                                switch (i24) {
                                    case 25:
                                        i6 = 1;
                                        int offset15 = getOffset(bArr, offset3);
                                        offset2 = getOffset(bArr, offset3 + 2) - 1;
                                        i10 = offset15;
                                        i8 = offset3 + 4;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i215 = i11 + 4;
                                            int offset16 = i215 + getOffset(bArr, i215);
                                            offset3 = offset16 + 1;
                                            i24 = bArr[offset16];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 26:
                                        i7 = 1;
                                        offset2 = -1;
                                        i8 = offset3;
                                        i9 = i7;
                                        i10 = i20;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i216 = i11 + 4;
                                            int offset17 = i216 + getOffset(bArr, i216);
                                            offset3 = offset17 + 1;
                                            i24 = bArr[offset17];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 27:
                                        i6 = 1;
                                        offset2 = -1;
                                        i8 = offset3;
                                        i10 = 1;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i217 = i11 + 4;
                                            int offset18 = i217 + getOffset(bArr, i217);
                                            offset3 = offset18 + 1;
                                            i24 = bArr[offset18];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    case 28:
                                        i6 = 1;
                                        i10 = i20;
                                        i8 = offset3;
                                        offset2 = 1;
                                        i9 = i6;
                                        i11 = i8;
                                        pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                        if (i9 != 0) {
                                            pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                            i24 = bArr[i11 + 6];
                                            i25 = 51;
                                            offset3 = i11 + 7;
                                            offset3 = i11;
                                        } else if (i10 != 0) {
                                            offset3 = i11 + 7;
                                            i24 = bArr[i11 + 6];
                                            offset3 = i11;
                                            i25 = 52;
                                        } else {
                                            pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                            popProgState(rEGlobalData);
                                            int i218 = i11 + 4;
                                            int offset19 = i218 + getOffset(bArr, i218);
                                            offset3 = offset19 + 1;
                                            i24 = bArr[offset19];
                                        }
                                        i20 = 0;
                                        i22 = 1;
                                        c = '9';
                                        break;
                                    default:
                                        switch (i24) {
                                            case 45:
                                                i7 = i20;
                                                offset2 = -1;
                                                i8 = offset3;
                                                i9 = i7;
                                                i10 = i20;
                                                i11 = i8;
                                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                                if (i9 != 0) {
                                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                                    i24 = bArr[i11 + 6];
                                                    i25 = 51;
                                                    offset3 = i11 + 7;
                                                    offset3 = i11;
                                                } else if (i10 != 0) {
                                                    offset3 = i11 + 7;
                                                    i24 = bArr[i11 + 6];
                                                    offset3 = i11;
                                                    i25 = 52;
                                                } else {
                                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                                    popProgState(rEGlobalData);
                                                    int i219 = i11 + 4;
                                                    int offset110 = i219 + getOffset(bArr, i219);
                                                    offset3 = offset110 + 1;
                                                    i24 = bArr[offset110];
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                                break;
                                            case 46:
                                                i6 = i20;
                                                offset2 = -1;
                                                i8 = offset3;
                                                i10 = 1;
                                                i9 = i6;
                                                i11 = i8;
                                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                                if (i9 != 0) {
                                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                                    i24 = bArr[i11 + 6];
                                                    i25 = 51;
                                                    offset3 = i11 + 7;
                                                    offset3 = i11;
                                                } else if (i10 != 0) {
                                                    offset3 = i11 + 7;
                                                    i24 = bArr[i11 + 6];
                                                    offset3 = i11;
                                                    i25 = 52;
                                                } else {
                                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                                    popProgState(rEGlobalData);
                                                    int i2110 = i11 + 4;
                                                    int offset111 = i2110 + getOffset(bArr, i2110);
                                                    offset3 = offset111 + 1;
                                                    i24 = bArr[offset111];
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                                break;
                                            case 47:
                                                i6 = i20;
                                                i10 = i20;
                                                i8 = offset3;
                                                offset2 = 1;
                                                i9 = i6;
                                                i11 = i8;
                                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                                if (i9 != 0) {
                                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                                    i24 = bArr[i11 + 6];
                                                    i25 = 51;
                                                    offset3 = i11 + 7;
                                                    offset3 = i11;
                                                } else if (i10 != 0) {
                                                    offset3 = i11 + 7;
                                                    i24 = bArr[i11 + 6];
                                                    offset3 = i11;
                                                    i25 = 52;
                                                } else {
                                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                                    popProgState(rEGlobalData);
                                                    int i2111 = i11 + 4;
                                                    int offset112 = i2111 + getOffset(bArr, i2111);
                                                    offset3 = offset112 + 1;
                                                    i24 = bArr[offset112];
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                                break;
                                            case 48:
                                                i6 = i20;
                                                int offset113 = getOffset(bArr, offset3);
                                                offset2 = getOffset(bArr, offset3 + 2) - 1;
                                                i10 = offset113;
                                                i8 = offset3 + 4;
                                                i9 = i6;
                                                i11 = i8;
                                                pushProgState(rEGlobalData, i10, offset2, rEGlobalData.cp, null, i25, offset3);
                                                if (i9 != 0) {
                                                    pushBackTrackState(rEGlobalData, (byte) 51, i11);
                                                    i24 = bArr[i11 + 6];
                                                    i25 = 51;
                                                    offset3 = i11 + 7;
                                                    offset3 = i11;
                                                } else if (i10 != 0) {
                                                    offset3 = i11 + 7;
                                                    i24 = bArr[i11 + 6];
                                                    offset3 = i11;
                                                    i25 = 52;
                                                } else {
                                                    pushBackTrackState(rEGlobalData, (byte) 52, i11);
                                                    popProgState(rEGlobalData);
                                                    int i2112 = i11 + 4;
                                                    int offset114 = i2112 + getOffset(bArr, i2112);
                                                    offset3 = offset114 + 1;
                                                    i24 = bArr[offset114];
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                                break;
                                            default:
                                                throw Kit.codeBug();
                                        }
                                        break;
                                }
                                break;
                            case 49:
                                offset3 = offset3;
                                i24 = i25;
                                i22 = 1;
                                i23 = 1;
                                break;
                            default:
                                switch (i24) {
                                    case 51:
                                        while (true) {
                                            REProgState rEProgStatePopProgState2 = popProgState(rEGlobalData);
                                            if (i23 == 0) {
                                                if (rEProgStatePopProgState2.min == 0) {
                                                    i23 = 1;
                                                }
                                                i12 = rEProgStatePopProgState2.continuationPc;
                                                i13 = rEProgStatePopProgState2.continuationOp;
                                                int i38 = offset3 + 4;
                                                offset3 = i38 + getOffset(bArr, i38);
                                                offset3 = i12;
                                                i25 = i13;
                                            } else if (rEProgStatePopProgState2.min == 0 && rEGlobalData.cp == rEProgStatePopProgState2.index) {
                                                i17 = rEProgStatePopProgState2.continuationPc;
                                                i18 = rEProgStatePopProgState2.continuationOp;
                                                int i39 = offset3 + 4;
                                                offset3 = i39 + getOffset(bArr, i39);
                                                offset3 = i17;
                                                i25 = i18;
                                                i23 = i20;
                                            } else {
                                                int i40 = rEProgStatePopProgState2.min;
                                                int i41 = rEProgStatePopProgState2.max;
                                                if (i40 != 0) {
                                                    i40--;
                                                }
                                                int i42 = i40;
                                                if (i41 != -1) {
                                                    i41--;
                                                }
                                                int i43 = i41;
                                                if (i43 == 0) {
                                                    int i44 = rEProgStatePopProgState2.continuationPc;
                                                    int i45 = rEProgStatePopProgState2.continuationOp;
                                                    int i46 = offset3 + 4;
                                                    offset3 = i46 + getOffset(bArr, i46);
                                                    offset3 = i44;
                                                    i25 = i45;
                                                    i23 = 1;
                                                } else {
                                                    int i47 = offset3 + 6;
                                                    int i48 = bArr[i47];
                                                    int i49 = rEGlobalData.cp;
                                                    if (reopIsSimple(i48)) {
                                                        int iSimpleMatch5 = simpleMatch(rEGlobalData, str, i48, bArr, offset3 + 7, i, true);
                                                        if (iSimpleMatch5 < 0) {
                                                            int i50 = i42 == 0 ? 1 : i20;
                                                            int i51 = rEProgStatePopProgState2.continuationPc;
                                                            int i52 = rEProgStatePopProgState2.continuationOp;
                                                            int i53 = offset3 + 4;
                                                            offset3 = i53 + getOffset(bArr, i53);
                                                            i23 = i50;
                                                            offset3 = i51;
                                                            i25 = i52;
                                                        } else {
                                                            i15 = iSimpleMatch5;
                                                            i14 = 1;
                                                        }
                                                    } else {
                                                        i14 = i23;
                                                        i15 = i47;
                                                    }
                                                    pushProgState(rEGlobalData, i42, i43, i49, null, rEProgStatePopProgState2.continuationOp, rEProgStatePopProgState2.continuationPc);
                                                    if (i42 == 0) {
                                                        pushBackTrackState(rEGlobalData, (byte) 51, offset3, i49, rEProgStatePopProgState2.continuationOp, rEProgStatePopProgState2.continuationPc);
                                                        int index4 = getIndex(bArr, offset3);
                                                        int index5 = getIndex(bArr, offset3 + 2);
                                                        for (int i54 = i20; i54 < index4; i54++) {
                                                            rEGlobalData.setParens(index5 + i54, -1, i20);
                                                        }
                                                    }
                                                    i24 = bArr[i15];
                                                    if (i24 != 49) {
                                                        i16 = i15 + 1;
                                                        i23 = i14;
                                                        i22 = 1;
                                                        c = '9';
                                                        i25 = 51;
                                                        offset3 = i16;
                                                    } else {
                                                        i23 = i14;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                        break;
                                    case 52:
                                        REProgState rEProgStatePopProgState3 = popProgState(rEGlobalData);
                                        if (i23 == 0) {
                                            if (rEProgStatePopProgState3.max == -1 || rEProgStatePopProgState3.max > 0) {
                                                i19 = 52;
                                                pushProgState(rEGlobalData, rEProgStatePopProgState3.min, rEProgStatePopProgState3.max, rEGlobalData.cp, null, rEProgStatePopProgState3.continuationOp, rEProgStatePopProgState3.continuationPc);
                                                int index6 = getIndex(bArr, offset3);
                                                int index7 = getIndex(bArr, offset3 + 2);
                                                int i55 = offset3 + 6;
                                                for (int i56 = i20; i56 < index6; i56++) {
                                                    rEGlobalData.setParens(index7 + i56, -1, i20);
                                                }
                                                i16 = offset3 + 7;
                                                i24 = bArr[i55];
                                            } else {
                                                i12 = rEProgStatePopProgState3.continuationPc;
                                                i13 = rEProgStatePopProgState3.continuationOp;
                                                offset3 = i12;
                                                i25 = i13;
                                            }
                                        } else {
                                            i19 = 52;
                                            if (rEProgStatePopProgState3.min == 0 && rEGlobalData.cp == rEProgStatePopProgState3.index) {
                                                i17 = rEProgStatePopProgState3.continuationPc;
                                                i18 = rEProgStatePopProgState3.continuationOp;
                                                offset3 = i17;
                                                i25 = i18;
                                                i23 = i20;
                                                break;
                                            } else {
                                                int i57 = rEProgStatePopProgState3.min;
                                                int i58 = rEProgStatePopProgState3.max;
                                                if (i57 != 0) {
                                                    i57--;
                                                }
                                                int i59 = i57;
                                                if (i58 != -1) {
                                                    i58--;
                                                }
                                                pushProgState(rEGlobalData, i59, i58, rEGlobalData.cp, null, rEProgStatePopProgState3.continuationOp, rEProgStatePopProgState3.continuationPc);
                                                if (i59 != 0) {
                                                    int index8 = getIndex(bArr, offset3);
                                                    int index9 = getIndex(bArr, offset3 + 2);
                                                    int i60 = offset3 + 6;
                                                    for (int i61 = i20; i61 < index8; i61++) {
                                                        rEGlobalData.setParens(index9 + i61, -1, i20);
                                                    }
                                                    i16 = offset3 + 7;
                                                    i24 = bArr[i60];
                                                } else {
                                                    int i62 = rEProgStatePopProgState3.continuationPc;
                                                    i25 = rEProgStatePopProgState3.continuationOp;
                                                    pushBackTrackState(rEGlobalData, (byte) 52, offset3);
                                                    popProgState(rEGlobalData);
                                                    int i63 = offset3 + 4;
                                                    int offset20 = i63 + getOffset(bArr, i63);
                                                    i24 = bArr[offset20];
                                                    offset3 = i62;
                                                    offset3 = offset20 + 1;
                                                }
                                            }
                                            i22 = 1;
                                            c = '9';
                                        }
                                        i25 = i19;
                                        i22 = 1;
                                        c = '9';
                                        offset3 = i16;
                                        break;
                                    case 53:
                                    case 54:
                                    case 55:
                                        char index10 = (char) getIndex(bArr, offset3);
                                        char index11 = (char) getIndex(bArr, offset3 + 2);
                                        offset3 += 4;
                                        if (rEGlobalData.cp != i) {
                                            char cCharAt = str.charAt(rEGlobalData.cp);
                                            if (i24 == 55) {
                                                if (cCharAt == index10 || classMatcher(rEGlobalData, rEGlobalData.regexp.classList[index11], cCharAt)) {
                                                    offset = offset3 + getOffset(bArr, offset3);
                                                    i3 = offset3 + 3;
                                                    i4 = bArr[offset3 + 2];
                                                    int i310 = rEGlobalData.cp;
                                                    if (reopIsSimple(i4)) {
                                                        iSimpleMatch = simpleMatch(rEGlobalData, str, i4, bArr, i3, i, true);
                                                        if (iSimpleMatch < 0) {
                                                            offset3 = offset + 1;
                                                            i24 = bArr[offset];
                                                        } else {
                                                            i5 = bArr[iSimpleMatch];
                                                            offset3 = iSimpleMatch + 1;
                                                            i23 = 1;
                                                        }
                                                    } else {
                                                        i5 = i4;
                                                        offset3 = i3;
                                                    }
                                                    pushBackTrackState(rEGlobalData, bArr[offset], offset + 1, i310, i25, offset3);
                                                    i24 = i5;
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                            } else {
                                                if (i24 == 54) {
                                                    cCharAt = upcase(cCharAt);
                                                }
                                                if (cCharAt == index10 || cCharAt == index11) {
                                                    offset = offset3 + getOffset(bArr, offset3);
                                                    i3 = offset3 + 3;
                                                    i4 = bArr[offset3 + 2];
                                                    int i311 = rEGlobalData.cp;
                                                    if (reopIsSimple(i4)) {
                                                        iSimpleMatch = simpleMatch(rEGlobalData, str, i4, bArr, i3, i, true);
                                                        if (iSimpleMatch < 0) {
                                                            offset3 = offset + 1;
                                                            i24 = bArr[offset];
                                                        } else {
                                                            i5 = bArr[iSimpleMatch];
                                                            offset3 = iSimpleMatch + 1;
                                                            i23 = 1;
                                                        }
                                                    } else {
                                                        i5 = i4;
                                                        offset3 = i3;
                                                    }
                                                    pushBackTrackState(rEGlobalData, bArr[offset], offset + 1, i311, i25, offset3);
                                                    i24 = i5;
                                                }
                                                i20 = 0;
                                                i22 = 1;
                                                c = '9';
                                            }
                                        }
                                        i23 = i20;
                                        break;
                                    default:
                                        throw Kit.codeBug("invalid bytecode");
                                }
                                break;
                        }
                        break;
                }
            }
            if (i23 == 0) {
                REBackTrackData rEBackTrackData = rEGlobalData.backTrackStackTop;
                if (rEBackTrackData == null) {
                    return false;
                }
                rEGlobalData.backTrackStackTop = rEBackTrackData.previous;
                rEGlobalData.parens = rEBackTrackData.parens;
                rEGlobalData.cp = rEBackTrackData.cp;
                rEGlobalData.stateStackTop = rEBackTrackData.stateStackTop;
                i25 = rEBackTrackData.continuationOp;
                offset3 = rEBackTrackData.continuationPc;
                offset3 = rEBackTrackData.pc;
                i24 = rEBackTrackData.op;
            } else {
                i2 = offset3 + 1;
                i24 = bArr[offset3];
                offset3 = i2;
            }
            i20 = 0;
            i22 = 1;
            c = '9';
        }
    }

    private static boolean matchRegExp(REGlobalData rEGlobalData, RECompiled rECompiled, String str, int i, int i2, boolean z) {
        if (rECompiled.parenCount != 0) {
            rEGlobalData.parens = new long[rECompiled.parenCount];
        } else {
            rEGlobalData.parens = null;
        }
        rEGlobalData.backTrackStackTop = null;
        rEGlobalData.stateStackTop = null;
        rEGlobalData.multiline = z || (rECompiled.flags & 4) != 0;
        rEGlobalData.regexp = rECompiled;
        int i3 = rEGlobalData.regexp.anchorCh;
        int i4 = i;
        while (i4 <= i2) {
            if (i3 >= 0) {
                while (i4 != i2) {
                    char cCharAt = str.charAt(i4);
                    if (cCharAt != i3 && ((rEGlobalData.regexp.flags & 2) == 0 || upcase(cCharAt) != upcase((char) i3))) {
                        i4++;
                    }
                }
                return false;
            }
            rEGlobalData.cp = i4;
            rEGlobalData.skipped = i4 - i;
            for (int i5 = 0; i5 < rECompiled.parenCount; i5++) {
                rEGlobalData.parens[i5] = -1;
            }
            boolean zExecuteREBytecode = executeREBytecode(rEGlobalData, str, i2);
            rEGlobalData.backTrackStackTop = null;
            rEGlobalData.stateStackTop = null;
            if (zExecuteREBytecode) {
                return true;
            }
            if (i3 == -2 && !rEGlobalData.multiline) {
                rEGlobalData.skipped = i2;
                return false;
            }
            i4 = rEGlobalData.skipped + i + 1;
        }
        return false;
    }

    Object executeRegExp(Context context, Scriptable scriptable, RegExpImpl regExpImpl, String str, int[] iArr, int i) {
        Scriptable scriptable2;
        Object obj;
        NativeRegExp nativeRegExp = this;
        REGlobalData rEGlobalData = new REGlobalData();
        int i2 = iArr[0];
        int length = str.length();
        int i3 = i2 > length ? length : i2;
        SubString subString = null;
        if (!matchRegExp(rEGlobalData, nativeRegExp.re, str, i3, length, regExpImpl.multiline)) {
            if (i != 2) {
                return null;
            }
            return Undefined.instance;
        }
        int i4 = rEGlobalData.cp;
        iArr[0] = i4;
        int i5 = i4 - (rEGlobalData.skipped + i3);
        int i6 = i4 - i5;
        if (i == 0) {
            scriptable2 = null;
            obj = Boolean.TRUE;
        } else {
            Scriptable scriptableNewArray = context.newArray(scriptable, 0);
            scriptableNewArray.put(0, scriptableNewArray, str.substring(i6, i6 + i5));
            scriptable2 = scriptableNewArray;
            obj = scriptableNewArray;
        }
        if (nativeRegExp.re.parenCount == 0) {
            regExpImpl.parens = null;
            regExpImpl.lastParen = new SubString();
        } else {
            regExpImpl.parens = new SubString[nativeRegExp.re.parenCount];
            int i7 = 0;
            while (i7 < nativeRegExp.re.parenCount) {
                int iParensIndex = rEGlobalData.parensIndex(i7);
                if (iParensIndex != -1) {
                    subString = new SubString(str, iParensIndex, rEGlobalData.parensLength(i7));
                    regExpImpl.parens[i7] = subString;
                    if (i != 0) {
                        scriptable2.put(i7 + 1, scriptable2, subString.toString());
                    }
                } else if (i != 0) {
                    scriptable2.put(i7 + 1, scriptable2, Undefined.instance);
                }
                i7++;
                nativeRegExp = this;
            }
            regExpImpl.lastParen = subString;
        }
        if (i != 0) {
            scriptable2.put("index", scriptable2, Integer.valueOf(rEGlobalData.skipped + i3));
            scriptable2.put("input", scriptable2, str);
        }
        if (regExpImpl.lastMatch == null) {
            regExpImpl.lastMatch = new SubString();
            regExpImpl.leftContext = new SubString();
            regExpImpl.rightContext = new SubString();
        }
        regExpImpl.lastMatch.str = str;
        regExpImpl.lastMatch.index = i6;
        regExpImpl.lastMatch.length = i5;
        regExpImpl.leftContext.str = str;
        if (context.getLanguageVersion() == 120) {
            regExpImpl.leftContext.index = i3;
            regExpImpl.leftContext.length = rEGlobalData.skipped;
        } else {
            regExpImpl.leftContext.index = 0;
            regExpImpl.leftContext.length = i3 + rEGlobalData.skipped;
        }
        regExpImpl.rightContext.str = str;
        regExpImpl.rightContext.index = i4;
        regExpImpl.rightContext.length = length - i4;
        return obj;
    }

    int getFlags() {
        return this.re.flags;
    }

    private static void reportWarning(Context context, String str, String str2) {
        if (context.hasFeature(11)) {
            Context.reportWarning(ScriptRuntime.getMessage1(str, str2));
        }
    }

    private static void reportError(String str, String str2) {
        throw ScriptRuntime.constructError("SyntaxError", ScriptRuntime.getMessage1(str, str2));
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0041  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        String str2;
        int i;
        int i2;
        int length = str.length();
        if (length == 6) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'g') {
                str2 = "global";
                i = 3;
            } else if (cCharAt == 's') {
                str2 = FirebaseAnalytics.Param.SOURCE;
                i = 2;
            } else {
                str2 = null;
                i = 0;
            }
        } else if (length == 9) {
            char cCharAt2 = str.charAt(0);
            if (cCharAt2 == 'l') {
                str2 = "lastIndex";
                i = 1;
            } else if (cCharAt2 == 'm') {
                str2 = "multiline";
                i = 5;
            } else {
                str2 = null;
                i = 0;
            }
        } else if (length == 10) {
            str2 = "ignoreCase";
            i = 4;
        } else {
            str2 = null;
            i = 0;
        }
        int i3 = (str2 == null || str2 == str || str2.equals(str)) ? i : 0;
        if (i3 == 0) {
            return super.findInstanceIdInfo(str);
        }
        if (i3 == 1) {
            i2 = this.lastIndexAttr;
        } else {
            if (i3 != 2 && i3 != 3 && i3 != 4 && i3 != 5) {
                throw new IllegalStateException();
            }
            i2 = 7;
        }
        return instanceIdInfo(i2, i3);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i) {
        if (i == 1) {
            return "lastIndex";
        }
        if (i == 2) {
            return FirebaseAnalytics.Param.SOURCE;
        }
        if (i == 3) {
            return "global";
        }
        if (i == 4) {
            return "ignoreCase";
        }
        if (i == 5) {
            return "multiline";
        }
        return super.getInstanceIdName(i);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i) {
        if (i == 1) {
            return this.lastIndex;
        }
        if (i == 2) {
            return new String(this.re.source);
        }
        if (i == 3) {
            return ScriptRuntime.wrapBoolean((this.re.flags & 1) != 0);
        }
        if (i == 4) {
            return ScriptRuntime.wrapBoolean((this.re.flags & 2) != 0);
        }
        if (i != 5) {
            return super.getInstanceIdValue(i);
        }
        return ScriptRuntime.wrapBoolean((this.re.flags & 4) != 0);
    }

    private void setLastIndex(Object obj) {
        if ((this.lastIndexAttr & 1) != 0) {
            throw ScriptRuntime.typeError1("msg.modify.readonly", "lastIndex");
        }
        this.lastIndex = obj;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdValue(int i, Object obj) {
        if (i == 1) {
            setLastIndex(obj);
        } else {
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                return;
            }
            super.setInstanceIdValue(i, obj);
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void setInstanceIdAttributes(int i, int i2) {
        if (i == 1) {
            this.lastIndexAttr = i2;
        } else {
            super.setInstanceIdAttributes(i, i2);
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        String str2;
        if (i == 7) {
            initPrototypeMethod(REGEXP_TAG, i, SymbolKey.MATCH, "[Symbol.match]", 1);
            return;
        }
        if (i == 8) {
            initPrototypeMethod(REGEXP_TAG, i, SymbolKey.SEARCH, "[Symbol.search]", 1);
            return;
        }
        int i2 = 0;
        int i3 = 1;
        switch (i) {
            case 1:
                i2 = 2;
                str = "compile";
                String str3 = str;
                i3 = i2;
                str2 = str3;
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            case 2:
                str = "toString";
                String str4 = str;
                i3 = i2;
                str2 = str4;
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            case 3:
                str = "toSource";
                String str5 = str;
                i3 = i2;
                str2 = str5;
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            case 4:
                str2 = "exec";
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            case 5:
                str2 = "test";
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            case 6:
                str2 = "prefix";
                initPrototypeMethod(REGEXP_TAG, i, str2, i3);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(REGEXP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        switch (iMethodId) {
            case 1:
                return realThis(scriptable2, idFunctionObject).compile(context, scriptable, objArr);
            case 2:
            case 3:
                return realThis(scriptable2, idFunctionObject).toString();
            case 4:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
            case 5:
                return Boolean.TRUE.equals(realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 0)) ? Boolean.TRUE : Boolean.FALSE;
            case 6:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 2);
            case 7:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
            case 8:
                Scriptable scriptable3 = (Scriptable) realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
                return scriptable3.get("index", scriptable3);
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    private static NativeRegExp realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (!(scriptable instanceof NativeRegExp)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (NativeRegExp) scriptable;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(Symbol symbol) {
        if (SymbolKey.MATCH.equals(symbol)) {
            return 7;
        }
        return SymbolKey.SEARCH.equals(symbol) ? 8 : 0;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x003f  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int length = str.length();
        int i = 4;
        if (length != 4) {
            i = 6;
            if (length == 6) {
                str2 = "prefix";
            } else if (length == 7) {
                str2 = "compile";
                i = 1;
            } else if (length != 8) {
                str2 = null;
                i = 0;
            } else {
                i = 3;
                char cCharAt = str.charAt(3);
                if (cCharAt == 'o') {
                    str2 = "toSource";
                } else if (cCharAt == 't') {
                    str2 = "toString";
                    i = 2;
                } else {
                    str2 = null;
                    i = 0;
                }
            }
        } else {
            char cCharAt2 = str.charAt(0);
            if (cCharAt2 == 'e') {
                str2 = "exec";
            } else if (cCharAt2 == 't') {
                str2 = "test";
                i = 5;
            } else {
                str2 = null;
                i = 0;
            }
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }
}
