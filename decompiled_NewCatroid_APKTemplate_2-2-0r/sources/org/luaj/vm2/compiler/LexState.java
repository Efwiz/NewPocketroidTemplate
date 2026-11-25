package org.luaj.vm2.compiler;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.LocVars;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.lib.MathLib;

/* JADX INFO: loaded from: classes6.dex */
public class LexState extends Constants {
    private static final int EOZ = -1;
    static final int FIRST_RESERVED = 257;
    private static final int LUAI_MAXCCALLS = 200;
    private static final int LUA_COMPAT_LSTR = 1;
    private static final boolean LUA_COMPAT_VARARG = true;
    private static final int MAX_INT = 2147483645;
    static final int NO_JUMP = -1;
    static final int NUM_RESERVED = 22;
    static final int OPR_ADD = 0;
    static final int OPR_AND = 13;
    static final int OPR_CONCAT = 6;
    static final int OPR_DIV = 3;
    static final int OPR_EQ = 8;
    static final int OPR_GE = 12;
    static final int OPR_GT = 11;
    static final int OPR_LE = 10;
    static final int OPR_LEN = 2;
    static final int OPR_LT = 9;
    static final int OPR_MINUS = 0;
    static final int OPR_MOD = 4;
    static final int OPR_MUL = 2;
    static final int OPR_NE = 7;
    static final int OPR_NOBINOPR = 15;
    static final int OPR_NOT = 1;
    static final int OPR_NOUNOPR = 3;
    static final int OPR_OR = 14;
    static final int OPR_POW = 5;
    static final int OPR_SUB = 1;
    static final Hashtable RESERVED;
    protected static final String RESERVED_LOCAL_VAR_FOR_CONTROL = "(for control)";
    protected static final String RESERVED_LOCAL_VAR_FOR_GENERATOR = "(for generator)";
    protected static final String RESERVED_LOCAL_VAR_FOR_INDEX = "(for index)";
    protected static final String RESERVED_LOCAL_VAR_FOR_LIMIT = "(for limit)";
    protected static final String RESERVED_LOCAL_VAR_FOR_STATE = "(for state)";
    protected static final String RESERVED_LOCAL_VAR_FOR_STEP = "(for step)";
    protected static final String[] RESERVED_LOCAL_VAR_KEYWORDS = {RESERVED_LOCAL_VAR_FOR_CONTROL, RESERVED_LOCAL_VAR_FOR_GENERATOR, RESERVED_LOCAL_VAR_FOR_INDEX, RESERVED_LOCAL_VAR_FOR_LIMIT, RESERVED_LOCAL_VAR_FOR_STATE, RESERVED_LOCAL_VAR_FOR_STEP};
    private static final Hashtable RESERVED_LOCAL_VAR_KEYWORDS_TABLE = new Hashtable();
    static final int TK_AND = 257;
    static final int TK_BREAK = 258;
    static final int TK_CONCAT = 279;
    static final int TK_DBCOLON = 285;
    static final int TK_DO = 259;
    static final int TK_DOTS = 280;
    static final int TK_ELSE = 260;
    static final int TK_ELSEIF = 261;
    static final int TK_END = 262;
    static final int TK_EOS = 286;
    static final int TK_EQ = 281;
    static final int TK_FALSE = 263;
    static final int TK_FOR = 264;
    static final int TK_FUNCTION = 265;
    static final int TK_GE = 282;
    static final int TK_GOTO = 266;
    static final int TK_IF = 267;
    static final int TK_IN = 268;
    static final int TK_LE = 283;
    static final int TK_LOCAL = 269;
    static final int TK_NAME = 288;
    static final int TK_NE = 284;
    static final int TK_NIL = 270;
    static final int TK_NOT = 271;
    static final int TK_NUMBER = 287;
    static final int TK_OR = 272;
    static final int TK_REPEAT = 273;
    static final int TK_RETURN = 274;
    static final int TK_STRING = 289;
    static final int TK_THEN = 275;
    static final int TK_TRUE = 276;
    static final int TK_UNTIL = 277;
    static final int TK_WHILE = 278;
    private static final int UCHAR_MAX = 255;
    static final int UNARY_PRIORITY = 8;
    static final int VCALL = 12;
    static final int VFALSE = 3;
    static final int VINDEXED = 9;
    static final int VJMP = 10;
    static final int VK = 4;
    static final int VKNUM = 5;
    static final int VLOCAL = 7;
    static final int VNIL = 1;
    static final int VNONRELOC = 6;
    static final int VRELOCABLE = 11;
    static final int VTRUE = 2;
    static final int VUPVAL = 8;
    static final int VVARARG = 13;
    static final int VVOID = 0;
    static final String[] luaX_tokens;
    static Priority[] priority;
    LuaC.CompileState L;
    int current;
    byte decpoint;
    LuaString envn;
    FuncState fs;
    int lastline;
    int linenumber;
    final Token lookahead;
    int nbuff;
    LuaString source;
    final Token t;
    InputStream z;
    Dyndata dyd = new Dyndata();
    char[] buff = new char[32];

    static class ConsControl {
        int na;
        int nh;
        expdesc t;
        int tostore;
        expdesc v = new expdesc();

        ConsControl() {
        }
    }

    static class Dyndata {
        Vardesc[] actvar;
        Labeldesc[] gt;
        Labeldesc[] label;
        int n_actvar = 0;
        int n_gt = 0;
        int n_label = 0;

        Dyndata() {
        }
    }

    static class LHS_assign {
        LHS_assign prev;
        expdesc v = new expdesc();

        LHS_assign() {
        }
    }

    static class Labeldesc {
        int line;
        short nactvar;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        LuaString f1056name;
        int pc;

        public Labeldesc(LuaString luaString, int i, int i2, short s) {
            this.f1056name = luaString;
            this.pc = i;
            this.line = i2;
            this.nactvar = s;
        }
    }

    static class Priority {
        final byte left;
        final byte right;

        public Priority(int i, int i2) {
            this.left = (byte) i;
            this.right = (byte) i2;
        }
    }

    private static class SemInfo {
        LuaValue r;
        LuaString ts;

        private SemInfo() {
        }
    }

    private static class Token {
        final SemInfo seminfo;
        int token;

        private Token() {
            this.seminfo = new SemInfo();
        }

        public void set(Token token) {
            this.token = token.token;
            this.seminfo.r = token.seminfo.r;
            this.seminfo.ts = token.seminfo.ts;
        }
    }

    static class Vardesc {
        final short idx;

        Vardesc(int i) {
            this.idx = (short) i;
        }
    }

    static class expdesc {
        int k;
        final U u = new U();
        final IntPtr t = new IntPtr();
        final IntPtr f = new IntPtr();

        static class U {
            private LuaValue _nval;
            short ind_idx;
            short ind_t;
            short ind_vt;
            int info;

            U() {
            }

            public LuaValue nval() {
                LuaValue luaValue = this._nval;
                return luaValue == null ? LuaInteger.valueOf(this.info) : luaValue;
            }

            public void setNval(LuaValue luaValue) {
                this._nval = luaValue;
            }
        }

        expdesc() {
        }

        boolean hasjumps() {
            return this.t.i != this.f.i;
        }

        void init(int i, int i2) {
            this.f.i = -1;
            this.t.i = -1;
            this.k = i;
            this.u.info = i2;
        }

        boolean isnumeral() {
            return this.k == 5 && this.t.i == -1 && this.f.i == -1;
        }

        public void setvalue(expdesc expdescVar) {
            this.f.i = expdescVar.f.i;
            this.k = expdescVar.k;
            this.t.i = expdescVar.t.i;
            this.u._nval = expdescVar.u._nval;
            this.u.ind_idx = expdescVar.u.ind_idx;
            this.u.ind_t = expdescVar.u.ind_t;
            this.u.ind_vt = expdescVar.u.ind_vt;
            this.u.info = expdescVar.u.info;
        }
    }

    static {
        int i = 0;
        while (true) {
            String[] strArr = RESERVED_LOCAL_VAR_KEYWORDS;
            if (i >= strArr.length) {
                break;
            }
            RESERVED_LOCAL_VAR_KEYWORDS_TABLE.put(strArr[i], Boolean.TRUE);
            i++;
        }
        luaX_tokens = new String[]{"and", PsiKeyword.BREAK, PsiKeyword.DO, PsiKeyword.ELSE, "elseif", "end", "false", PsiKeyword.FOR, "function", PsiKeyword.GOTO, PsiKeyword.IF, "in", "local", "nil", "not", "or", "repeat", "return", "then", "true", "until", PsiKeyword.WHILE, "..", "...", "==", ">=", "<=", "~=", "::", "<eos>", "<number>", "<name>", "<string>", "<eof>"};
        RESERVED = new Hashtable();
        int i2 = 0;
        for (int i3 = 22; i2 < i3; i3 = 22) {
            RESERVED.put(LuaValue.valueOf(luaX_tokens[i2]), new Integer(i2 + 257));
            i2++;
        }
        priority = new Priority[]{new Priority(6, 6), new Priority(6, 6), new Priority(7, 7), new Priority(7, 7), new Priority(7, 7), new Priority(10, 9), new Priority(5, 4), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(3, 3), new Priority(2, 2), new Priority(1, 1)};
    }

    public LexState(LuaC.CompileState compileState, InputStream inputStream) {
        this.t = new Token();
        this.lookahead = new Token();
        this.z = inputStream;
        this.L = compileState;
    }

    private static final String LUA_QL(Object obj) {
        return LUA_QS(String.valueOf(obj));
    }

    private static final String LUA_QS(String str) {
        return new StringBuffer(FormatHelper.QUOTE).append(str).append(FormatHelper.QUOTE).toString();
    }

    public static boolean isReservedKeyword(String str) {
        return RESERVED_LOCAL_VAR_KEYWORDS_TABLE.containsKey(str);
    }

    private boolean isalnum(int i) {
        return (i >= 48 && i <= 57) || (i >= 97 && i <= 122) || ((i >= 65 && i <= 90) || i == 95);
    }

    private boolean isalpha(int i) {
        return (i >= 97 && i <= 122) || (i >= 65 && i <= 90);
    }

    private static boolean iscntrl(int i) {
        return i < 32;
    }

    private boolean isdigit(int i) {
        return i >= 48 && i <= 57;
    }

    private boolean isspace(int i) {
        return i <= 32;
    }

    private boolean isxdigit(int i) {
        return (i >= 48 && i <= 57) || (i >= 97 && i <= 102) || (i >= 65 && i <= 70);
    }

    static int luaO_int2fb(int i) {
        int i2 = 0;
        while (i >= 16) {
            i = (i + 1) >> 1;
            i2++;
        }
        if (i < 8) {
            return i;
        }
        return (i - 8) | ((i2 + 1) << 3);
    }

    private void skipShebang() {
        if (this.current == 35) {
            while (!currIsNewline() && this.current != -1) {
                nextChar();
            }
        }
    }

    static final boolean vkisinreg(int i) {
        return i == 6 || i == 7;
    }

    static final boolean vkisvar(int i) {
        return 7 <= i && i <= 9;
    }

    Prototype addprototype() {
        Prototype prototype = this.fs.f;
        if (prototype.p == null || this.fs.np >= prototype.p.length) {
            prototype.p = realloc(prototype.p, Math.max(1, this.fs.np * 2));
        }
        Prototype[] prototypeArr = prototype.p;
        FuncState funcState = this.fs;
        int i = funcState.np;
        funcState.np = i + 1;
        Prototype prototype2 = new Prototype();
        prototypeArr[i] = prototype2;
        return prototype2;
    }

    void adjust_assign(int i, int i2, expdesc expdescVar) {
        FuncState funcState = this.fs;
        int i3 = i - i2;
        if (hasmultret(expdescVar.k)) {
            int i4 = i3 + 1;
            if (i4 < 0) {
                i4 = 0;
            }
            funcState.setreturns(expdescVar, i4);
            if (i4 > 1) {
                funcState.reserveregs(i4 - 1);
                return;
            }
            return;
        }
        if (expdescVar.k != 0) {
            funcState.exp2nextreg(expdescVar);
        }
        if (i3 > 0) {
            short s = funcState.freereg;
            funcState.reserveregs(i3);
            funcState.nil(s, i3);
        }
    }

    void adjustlocalvars(int i) {
        FuncState funcState = this.fs;
        funcState.nactvar = (short) (funcState.nactvar + i);
        while (i > 0) {
            funcState.getlocvar(funcState.nactvar - i).startpc = funcState.pc;
            i--;
        }
    }

    void anchor_token() {
        _assert(this.fs != null || this.t.token == TK_EOS);
        if (this.t.token == TK_NAME || this.t.token == TK_STRING) {
            LuaString luaString = this.t.seminfo.ts;
            this.L.cachedLuaString(this.t.seminfo.ts);
        }
    }

    void assignment(LHS_assign lHS_assign, int i) {
        expdesc expdescVar = new expdesc();
        check_condition(7 <= lHS_assign.v.k && lHS_assign.v.k <= 9, "syntax error");
        if (!testnext(44)) {
            checknext(61);
            int iExplist = explist(expdescVar);
            if (iExplist != i) {
                adjust_assign(i, iExplist, expdescVar);
                if (iExplist > i) {
                    FuncState funcState = this.fs;
                    funcState.freereg = (short) (funcState.freereg - (iExplist - i));
                }
            } else {
                this.fs.setoneret(expdescVar);
            }
            this.fs.storevar(lHS_assign.v, expdescVar);
        }
        LHS_assign lHS_assign2 = new LHS_assign();
        lHS_assign2.prev = lHS_assign;
        suffixedexp(lHS_assign2.v);
        if (lHS_assign2.v.k != 9) {
            check_conflict(lHS_assign, lHS_assign2.v);
        }
        assignment(lHS_assign2, i + 1);
        expdescVar.init(6, this.fs.freereg - 1);
        this.fs.storevar(lHS_assign.v, expdescVar);
    }

    void block() {
        FuncState funcState = this.fs;
        funcState.enterblock(new FuncState.BlockCnt(), false);
        statlist();
        funcState.leaveblock();
    }

    boolean block_follow(boolean z) {
        int i = this.t.token;
        if (i == TK_UNTIL) {
            return z;
        }
        if (i != TK_EOS) {
            switch (i) {
                case 260:
                case 261:
                case 262:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    void body(expdesc expdescVar, boolean z, int i) {
        FuncState funcState = new FuncState();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        funcState.f = addprototype();
        funcState.f.linedefined = i;
        open_func(funcState, blockCnt);
        checknext(40);
        if (z) {
            new_localvarliteral("self");
            adjustlocalvars(1);
        }
        parlist();
        checknext(41);
        statlist();
        funcState.f.lastlinedefined = this.linenumber;
        check_match(262, TK_FUNCTION, i);
        codeclosure(expdescVar);
        close_func();
    }

    void breaklabel() {
        LuaString luaStringValueOf = LuaString.valueOf(PsiKeyword.BREAK);
        Dyndata dyndata = this.dyd;
        Labeldesc[] labeldescArrGrow = grow(dyndata.label, this.dyd.n_label + 1);
        dyndata.label = labeldescArrGrow;
        Dyndata dyndata2 = this.dyd;
        int i = dyndata2.n_label;
        dyndata2.n_label = i + 1;
        findgotos(this.dyd.label[newlabelentry(labeldescArrGrow, i, luaStringValueOf, 0, this.fs.pc)]);
    }

    void buffreplace(char c, char c2) {
        int i = this.nbuff;
        char[] cArr = this.buff;
        while (true) {
            i--;
            if (i < 0) {
                return;
            }
            if (cArr[i] == c) {
                cArr[i] = c2;
            }
        }
    }

    void check(int i) {
        if (this.t.token != i) {
            error_expected(i);
        }
    }

    void check_condition(boolean z, String str) {
        if (z) {
            return;
        }
        syntaxerror(str);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    void check_conflict(LHS_assign lHS_assign, expdesc expdescVar) {
        FuncState funcState = this.fs;
        short s = funcState.freereg;
        boolean z = false;
        while (lHS_assign != null) {
            if (lHS_assign.v.k == 9) {
                if (lHS_assign.v.u.ind_vt == expdescVar.k && lHS_assign.v.u.ind_t == expdescVar.u.info) {
                    lHS_assign.v.u.ind_vt = (short) 7;
                    lHS_assign.v.u.ind_t = s;
                    z = true;
                }
                if (expdescVar.k == 7 && lHS_assign.v.u.ind_idx == expdescVar.u.info) {
                    lHS_assign.v.u.ind_idx = s;
                    z = true;
                }
            }
            lHS_assign = lHS_assign.prev;
        }
        if (z) {
            funcState.codeABC(expdescVar.k == 7 ? 0 : 5, s, expdescVar.u.info, 0);
            funcState.reserveregs(1);
        }
    }

    void check_match(int i, int i2, int i3) {
        if (testnext(i)) {
            return;
        }
        if (i3 == this.linenumber) {
            error_expected(i);
        } else {
            syntaxerror(this.L.pushfstring(new StringBuffer().append(LUA_QS(token2str(i))).append(" expected (to close ").append(LUA_QS(token2str(i2))).append(" at line ").append(i3).append(Constant.AFTER_QUTO).toString()));
        }
    }

    boolean check_next(String str) {
        if (str.indexOf(this.current) < 0) {
            return false;
        }
        save_and_next();
        return true;
    }

    void checkname(expdesc expdescVar) {
        codestring(expdescVar, str_checkname());
    }

    void checknext(int i) {
        check(i);
        next();
    }

    void close_func() {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        funcState.ret(0, 0);
        funcState.leaveblock();
        prototype.code = realloc(prototype.code, funcState.pc);
        prototype.lineinfo = realloc(prototype.lineinfo, funcState.pc);
        prototype.k = realloc(prototype.k, funcState.nk);
        prototype.p = realloc(prototype.p, funcState.np);
        prototype.locvars = realloc(prototype.locvars, funcState.nlocvars);
        prototype.upvalues = realloc(prototype.upvalues, funcState.nups);
        _assert(funcState.bl == null);
        this.fs = funcState.prev;
    }

    void closegoto(int i, Labeldesc labeldesc) {
        FuncState funcState = this.fs;
        Labeldesc[] labeldescArr = this.dyd.gt;
        Labeldesc labeldesc2 = labeldescArr[i];
        _assert(labeldesc2.f1056name.eq_b(labeldesc.f1056name));
        if (labeldesc2.nactvar < labeldesc.nactvar) {
            semerror(this.L.pushfstring(new StringBuffer("<goto ").append(labeldesc2.f1056name).append("> at line ").append(labeldesc2.line).append(" jumps into the scope of local '").append(funcState.getlocvar(labeldesc2.nactvar).varname.tojstring()).append(FormatHelper.QUOTE).toString()));
        }
        funcState.patchlist(labeldesc2.pc, labeldesc.pc);
        System.arraycopy(labeldescArr, i + 1, labeldescArr, i, (this.dyd.n_gt - i) - 1);
        Dyndata dyndata = this.dyd;
        int i2 = dyndata.n_gt - 1;
        dyndata.n_gt = i2;
        labeldescArr[i2] = null;
    }

    void codeclosure(expdesc expdescVar) {
        FuncState funcState = this.fs.prev;
        expdescVar.init(11, funcState.codeABx(37, 0, funcState.np - 1));
        funcState.exp2nextreg(expdescVar);
    }

    void codestring(expdesc expdescVar, LuaString luaString) {
        expdescVar.init(4, this.fs.stringK(luaString));
    }

    int cond() {
        expdesc expdescVar = new expdesc();
        expr(expdescVar);
        if (expdescVar.k == 1) {
            expdescVar.k = 3;
        }
        this.fs.goiftrue(expdescVar);
        return expdescVar.f.i;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0061  */
    void constructor(expdesc expdescVar) {
        FuncState funcState = this.fs;
        int i = this.linenumber;
        int iCodeABC = funcState.codeABC(11, 0, 0, 0);
        ConsControl consControl = new ConsControl();
        consControl.tostore = 0;
        consControl.nh = 0;
        consControl.na = 0;
        consControl.t = expdescVar;
        expdescVar.init(11, iCodeABC);
        consControl.v.init(0, 0);
        funcState.exp2nextreg(expdescVar);
        checknext(123);
        while (true) {
            _assert(consControl.v.k == 0 || consControl.tostore > 0);
            if (this.t.token != 125) {
                funcState.closelistfield(consControl);
                int i2 = this.t.token;
                if (i2 == 91) {
                    recfield(consControl);
                } else {
                    if (i2 == TK_NAME) {
                        lookahead();
                        if (this.lookahead.token == 61) {
                            recfield(consControl);
                        }
                    }
                    listfield(consControl);
                }
                if (!testnext(44) && !testnext(59)) {
                    break;
                }
            } else {
                break;
            }
        }
        check_match(125, 123, i);
        funcState.lastlistfield(consControl);
        InstructionPtr instructionPtr = new InstructionPtr(funcState.f.code, iCodeABC);
        SETARG_B(instructionPtr, luaO_int2fb(consControl.na));
        SETARG_C(instructionPtr, luaO_int2fb(consControl.nh));
    }

    boolean currIsNewline() {
        int i = this.current;
        return i == 10 || i == 13;
    }

    void enterlevel() {
        LuaC.CompileState compileState = this.L;
        int i = compileState.nCcalls + 1;
        compileState.nCcalls = i;
        if (i > 200) {
            lexerror("chunk has too many syntax levels", 0);
        }
    }

    void error_expected(int i) {
        syntaxerror(this.L.pushfstring(new StringBuffer().append(LUA_QS(token2str(i))).append(" expected").toString()));
    }

    int exp1() {
        expdesc expdescVar = new expdesc();
        expr(expdescVar);
        int i = expdescVar.k;
        this.fs.exp2nextreg(expdescVar);
        return i;
    }

    int explist(expdesc expdescVar) {
        expr(expdescVar);
        int i = 1;
        while (testnext(44)) {
            this.fs.exp2nextreg(expdescVar);
            expr(expdescVar);
            i++;
        }
        return i;
    }

    void expr(expdesc expdescVar) {
        subexpr(expdescVar, 0);
    }

    void exprstat() {
        FuncState funcState = this.fs;
        LHS_assign lHS_assign = new LHS_assign();
        suffixedexp(lHS_assign.v);
        if (this.t.token == 61 || this.t.token == 44) {
            lHS_assign.prev = null;
            assignment(lHS_assign, 1);
        } else {
            check_condition(lHS_assign.v.k == 12, "syntax error");
            SETARG_C(funcState.getcodePtr(lHS_assign.v), 1);
        }
    }

    void fieldsel(expdesc expdescVar) {
        FuncState funcState = this.fs;
        expdesc expdescVar2 = new expdesc();
        funcState.exp2anyregup(expdescVar);
        next();
        checkname(expdescVar2);
        funcState.indexed(expdescVar, expdescVar2);
    }

    void findgotos(Labeldesc labeldesc) {
        Labeldesc[] labeldescArr = this.dyd.gt;
        int i = this.fs.bl.firstgoto;
        while (i < this.dyd.n_gt) {
            if (labeldescArr[i].f1056name.eq_b(labeldesc.f1056name)) {
                closegoto(i, labeldesc);
            } else {
                i++;
            }
        }
    }

    boolean findlabel(int i) {
        FuncState.BlockCnt blockCnt = this.fs.bl;
        Dyndata dyndata = this.dyd;
        Labeldesc labeldesc = dyndata.gt[i];
        for (int i2 = blockCnt.firstlabel; i2 < dyndata.n_label; i2++) {
            Labeldesc labeldesc2 = dyndata.label[i2];
            if (labeldesc2.f1056name.eq_b(labeldesc.f1056name)) {
                if (labeldesc.nactvar > labeldesc2.nactvar && (blockCnt.upval || dyndata.n_label > blockCnt.firstlabel)) {
                    this.fs.patchclose(labeldesc.pc, labeldesc2.nactvar);
                }
                closegoto(i, labeldesc2);
                return true;
            }
        }
        return false;
    }

    void forbody(int i, int i2, int i3, boolean z) {
        int i4;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        FuncState funcState = this.fs;
        adjustlocalvars(3);
        checknext(259);
        int iCodeAsBx = z ? funcState.codeAsBx(33, i, -1) : funcState.jump();
        funcState.enterblock(blockCnt, false);
        adjustlocalvars(i3);
        funcState.reserveregs(i3);
        block();
        funcState.leaveblock();
        funcState.patchtohere(iCodeAsBx);
        if (z) {
            i4 = 32;
        } else {
            funcState.codeABC(34, i, 0, i3);
            funcState.fixline(i2);
            i += 2;
            i4 = 35;
        }
        funcState.patchlist(funcState.codeAsBx(i4, i, -1), iCodeAsBx + 1);
        funcState.fixline(i2);
    }

    void forlist(LuaString luaString) {
        FuncState funcState = this.fs;
        expdesc expdescVar = new expdesc();
        short s = funcState.freereg;
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_GENERATOR);
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_STATE);
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_CONTROL);
        new_localvar(luaString);
        int i = 4;
        while (testnext(44)) {
            new_localvar(str_checkname());
            i++;
        }
        checknext(TK_IN);
        int i2 = this.linenumber;
        adjust_assign(3, explist(expdescVar), expdescVar);
        funcState.checkstack(3);
        forbody(s, i2, i - 3, false);
    }

    void fornum(LuaString luaString, int i) {
        FuncState funcState = this.fs;
        short s = funcState.freereg;
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_INDEX);
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_LIMIT);
        new_localvarliteral(RESERVED_LOCAL_VAR_FOR_STEP);
        new_localvar(luaString);
        checknext(61);
        exp1();
        checknext(44);
        exp1();
        if (testnext(44)) {
            exp1();
        } else {
            funcState.codeABx(1, funcState.freereg, funcState.numberK(LuaInteger.valueOf(1)));
            funcState.reserveregs(1);
        }
        forbody(s, i, 1, true);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0053  */
    void forstat(int i) {
        FuncState funcState = this.fs;
        funcState.enterblock(new FuncState.BlockCnt(), true);
        next();
        LuaString luaStringStr_checkname = str_checkname();
        int i2 = this.t.token;
        if (i2 == 44) {
            forlist(luaStringStr_checkname);
        } else if (i2 == 61) {
            fornum(luaStringStr_checkname, i);
        } else if (i2 != TK_IN) {
            syntaxerror(new StringBuffer().append(LUA_QL(ContainerUtils.KEY_VALUE_DELIMITER)).append(" or ").append(LUA_QL("in")).append(" expected").toString());
        } else {
            forlist(luaStringStr_checkname);
        }
        check_match(262, 264, i);
        funcState.leaveblock();
    }

    void funcargs(expdesc expdescVar, int i) {
        int i2;
        FuncState funcState = this.fs;
        expdesc expdescVar2 = new expdesc();
        int i3 = this.t.token;
        if (i3 == 40) {
            next();
            if (this.t.token == 41) {
                expdescVar2.k = 0;
            } else {
                explist(expdescVar2);
                funcState.setmultret(expdescVar2);
            }
            check_match(41, 40, i);
        } else if (i3 == 123) {
            constructor(expdescVar2);
        } else if (i3 != TK_STRING) {
            syntaxerror("function arguments expected");
            return;
        } else {
            codestring(expdescVar2, this.t.seminfo.ts);
            next();
        }
        _assert(expdescVar.k == 6);
        int i4 = expdescVar.u.info;
        if (hasmultret(expdescVar2.k)) {
            i2 = -1;
        } else {
            if (expdescVar2.k != 0) {
                funcState.exp2nextreg(expdescVar2);
            }
            i2 = funcState.freereg - (i4 + 1);
        }
        expdescVar.init(12, funcState.codeABC(29, i4, i2 + 1, 2));
        funcState.fixline(i);
        funcState.freereg = (short) (i4 + 1);
    }

    boolean funcname(expdesc expdescVar) {
        singlevar(expdescVar);
        while (this.t.token == 46) {
            fieldsel(expdescVar);
        }
        if (this.t.token != 58) {
            return false;
        }
        fieldsel(expdescVar);
        return true;
    }

    void funcstat(int i) {
        expdesc expdescVar = new expdesc();
        expdesc expdescVar2 = new expdesc();
        next();
        body(expdescVar2, funcname(expdescVar), i);
        this.fs.storevar(expdescVar, expdescVar2);
        this.fs.fixline(i);
    }

    int getbinopr(int i) {
        if (i == 37) {
            return 4;
        }
        if (i == 45) {
            return 1;
        }
        if (i == 47) {
            return 3;
        }
        if (i == 60) {
            return 9;
        }
        if (i == 62) {
            return 11;
        }
        if (i == 94) {
            return 5;
        }
        if (i == 257) {
            return 13;
        }
        if (i == 272) {
            return 14;
        }
        if (i == TK_CONCAT) {
            return 6;
        }
        if (i == 42) {
            return 2;
        }
        if (i == 43) {
            return 0;
        }
        switch (i) {
            case TK_EQ /* 281 */:
                return 8;
            case TK_GE /* 282 */:
                return 12;
            case TK_LE /* 283 */:
                return 10;
            case TK_NE /* 284 */:
                return 7;
            default:
                return 15;
        }
    }

    int getunopr(int i) {
        if (i == 35) {
            return 2;
        }
        if (i != 45) {
            return i != TK_NOT ? 3 : 1;
        }
        return 0;
    }

    void gotostat(int i) {
        LuaString luaStringValueOf;
        int i2 = this.linenumber;
        if (testnext(TK_GOTO)) {
            luaStringValueOf = str_checkname();
        } else {
            next();
            luaStringValueOf = LuaString.valueOf(PsiKeyword.BREAK);
        }
        LuaString luaString = luaStringValueOf;
        Dyndata dyndata = this.dyd;
        Labeldesc[] labeldescArrGrow = grow(dyndata.gt, this.dyd.n_gt + 1);
        dyndata.gt = labeldescArrGrow;
        Dyndata dyndata2 = this.dyd;
        int i3 = dyndata2.n_gt;
        dyndata2.n_gt = i3 + 1;
        findlabel(newlabelentry(labeldescArrGrow, i3, luaString, i2, i));
    }

    boolean hasmultret(int i) {
        return i == 12 || i == 13;
    }

    int hexvalue(int i) {
        if (i <= 57) {
            return i - 48;
        }
        return i <= 70 ? i - 55 : i - 87;
    }

    void ifstat(int i) {
        IntPtr intPtr = new IntPtr(-1);
        do {
            test_then_block(intPtr);
        } while (this.t.token == 261);
        if (testnext(260)) {
            block();
        }
        check_match(262, TK_IF, i);
        this.fs.patchtohere(intPtr.i);
    }

    void inclinenumber() {
        int i = this.current;
        _assert(currIsNewline());
        nextChar();
        if (currIsNewline() && this.current != i) {
            nextChar();
        }
        int i2 = this.linenumber + 1;
        this.linenumber = i2;
        if (i2 >= MAX_INT) {
            syntaxerror("chunk has too many lines");
        }
    }

    void labelstat(LuaString luaString, int i) {
        this.fs.checkrepeated(this.dyd.label, this.dyd.n_label, luaString);
        checknext(TK_DBCOLON);
        Dyndata dyndata = this.dyd;
        Labeldesc[] labeldescArrGrow = grow(dyndata.label, this.dyd.n_label + 1);
        dyndata.label = labeldescArrGrow;
        Dyndata dyndata2 = this.dyd;
        int i2 = dyndata2.n_label;
        dyndata2.n_label = i2 + 1;
        int iNewlabelentry = newlabelentry(labeldescArrGrow, i2, luaString, i, this.fs.pc);
        skipnoopstat();
        if (block_follow(false)) {
            this.dyd.label[iNewlabelentry].nactvar = this.fs.bl.nactvar;
        }
        findgotos(this.dyd.label[iNewlabelentry]);
    }

    void leavelevel() {
        this.L.nCcalls--;
    }

    void lexerror(String str, int i) {
        String strChunkid = Lua.chunkid(this.source.tojstring());
        this.L.pushfstring(new StringBuffer().append(strChunkid).append(":").append(this.linenumber).append(": ").append(str).toString());
        if (i != 0) {
            this.L.pushfstring(new StringBuffer("syntax error: ").append(str).append(" near ").append(txtToken(i)).toString());
        }
        throw new LuaError(new StringBuffer().append(strChunkid).append(":").append(this.linenumber).append(": ").append(str).toString());
    }

    void listfield(ConsControl consControl) {
        expr(consControl.v);
        this.fs.checklimit(consControl.na, MAX_INT, "items in a constructor");
        consControl.na++;
        consControl.tostore++;
    }

    int llex(SemInfo semInfo) {
        int i;
        while (true) {
            this.nbuff = 0;
            while (true) {
                int i2 = this.current;
                if (i2 == -1) {
                    return TK_EOS;
                }
                if (i2 != 10 && i2 != 13) {
                    if (i2 == 34 || i2 == 39) {
                        read_string(i2, semInfo);
                        return TK_STRING;
                    }
                    if (i2 != 91) {
                        if (i2 == 126) {
                            nextChar();
                            if (this.current != 61) {
                                return 126;
                            }
                            nextChar();
                            return TK_NE;
                        }
                        if (i2 != 45) {
                            if (i2 == 46) {
                                save_and_next();
                                if (check_next(".")) {
                                    return check_next(".") ? TK_DOTS : TK_CONCAT;
                                }
                                if (!isdigit(this.current)) {
                                    return 46;
                                }
                                read_numeral(semInfo);
                                return 287;
                            }
                            switch (i2) {
                                case 48:
                                case 49:
                                case 50:
                                case 51:
                                case 52:
                                case 53:
                                case 54:
                                case 55:
                                case 56:
                                case 57:
                                    read_numeral(semInfo);
                                    return 287;
                                case 58:
                                    nextChar();
                                    if (this.current != 58) {
                                        return 58;
                                    }
                                    nextChar();
                                    return TK_DBCOLON;
                                default:
                                    switch (i2) {
                                        case 60:
                                            nextChar();
                                            if (this.current != 61) {
                                                return 60;
                                            }
                                            nextChar();
                                            return TK_LE;
                                        case 61:
                                            break;
                                        case 62:
                                            nextChar();
                                            if (this.current != 61) {
                                                return 62;
                                            }
                                            nextChar();
                                            return TK_GE;
                                        default:
                                            if (isspace(i2)) {
                                                _assert(!currIsNewline());
                                                nextChar();
                                            } else {
                                                if (isdigit(this.current)) {
                                                    read_numeral(semInfo);
                                                    return 287;
                                                }
                                                if (!isalpha(this.current) && (i = this.current) != 95) {
                                                    nextChar();
                                                    return i;
                                                }
                                                while (true) {
                                                    save_and_next();
                                                    if (!isalnum(this.current) && this.current != 95) {
                                                        LuaString luaStringNewstring = newstring(this.buff, 0, this.nbuff);
                                                        Hashtable hashtable = RESERVED;
                                                        if (hashtable.containsKey(luaStringNewstring)) {
                                                            return ((Integer) hashtable.get(luaStringNewstring)).intValue();
                                                        }
                                                        semInfo.ts = luaStringNewstring;
                                                        return TK_NAME;
                                                    }
                                                }
                                            }
                                            break;
                                    }
                                    break;
                            }
                        } else {
                            nextChar();
                            if (this.current != 45) {
                                return 45;
                            }
                            nextChar();
                            if (this.current == 91) {
                                int iSkip_sep = skip_sep();
                                this.nbuff = 0;
                                if (iSkip_sep >= 0) {
                                    read_long_string(null, iSkip_sep);
                                }
                            }
                            while (!currIsNewline() && this.current != -1) {
                                nextChar();
                            }
                        }
                    } else {
                        int iSkip_sep2 = skip_sep();
                        if (iSkip_sep2 >= 0) {
                            read_long_string(semInfo, iSkip_sep2);
                            return TK_STRING;
                        }
                        if (iSkip_sep2 == -1) {
                            return 91;
                        }
                        lexerror("invalid long string delimiter", TK_STRING);
                    }
                } else {
                    inclinenumber();
                }
            }
        }
        nextChar();
        if (this.current != 61) {
            return 61;
        }
        nextChar();
        return TK_EQ;
    }

    void localfunc() {
        expdesc expdescVar = new expdesc();
        FuncState funcState = this.fs;
        new_localvar(str_checkname());
        adjustlocalvars(1);
        body(expdescVar, false, this.linenumber);
        funcState.getlocvar(funcState.nactvar - 1).startpc = funcState.pc;
    }

    void localstat() {
        expdesc expdescVar = new expdesc();
        int iExplist = 0;
        int i = 0;
        do {
            new_localvar(str_checkname());
            i++;
        } while (testnext(44));
        if (testnext(61)) {
            iExplist = explist(expdescVar);
        } else {
            expdescVar.k = 0;
        }
        adjust_assign(i, iExplist, expdescVar);
        adjustlocalvars(i);
    }

    void lookahead() {
        _assert(this.lookahead.token == TK_EOS);
        Token token = this.lookahead;
        token.token = llex(token.seminfo);
    }

    public void mainfunc(FuncState funcState) {
        open_func(funcState, new FuncState.BlockCnt());
        this.fs.f.is_vararg = 1;
        expdesc expdescVar = new expdesc();
        expdescVar.init(7, 0);
        this.fs.newupvalue(this.envn, expdescVar);
        next();
        statlist();
        check(TK_EOS);
        close_func();
    }

    void new_localvar(LuaString luaString) {
        int iRegisterlocalvar = registerlocalvar(luaString);
        this.fs.checklimit(this.dyd.n_actvar + 1, 200, "local variables");
        if (this.dyd.actvar == null || this.dyd.n_actvar + 1 > this.dyd.actvar.length) {
            Dyndata dyndata = this.dyd;
            dyndata.actvar = realloc(dyndata.actvar, Math.max(1, this.dyd.n_actvar * 2));
        }
        Vardesc[] vardescArr = this.dyd.actvar;
        Dyndata dyndata2 = this.dyd;
        int i = dyndata2.n_actvar;
        dyndata2.n_actvar = i + 1;
        vardescArr[i] = new Vardesc(iRegisterlocalvar);
    }

    void new_localvarliteral(String str) {
        new_localvar(newstring(str));
    }

    int newlabelentry(Labeldesc[] labeldescArr, int i, LuaString luaString, int i2, int i3) {
        labeldescArr[i] = new Labeldesc(luaString, i3, i2, this.fs.nactvar);
        return i;
    }

    LuaString newstring(String str) {
        return this.L.newTString(str);
    }

    LuaString newstring(char[] cArr, int i, int i2) {
        return this.L.newTString(new String(cArr, i, i2));
    }

    void next() {
        Token token;
        this.lastline = this.linenumber;
        int i = this.lookahead.token;
        int iLlex = TK_EOS;
        if (i != TK_EOS) {
            this.t.set(this.lookahead);
            token = this.lookahead;
        } else {
            token = this.t;
            iLlex = llex(token.seminfo);
        }
        token.token = iLlex;
    }

    void nextChar() {
        try {
            this.current = this.z.read();
        } catch (IOException e) {
            e.printStackTrace();
            this.current = -1;
        }
    }

    void open_func(FuncState funcState, FuncState.BlockCnt blockCnt) {
        funcState.prev = this.fs;
        funcState.ls = this;
        this.fs = funcState;
        funcState.pc = 0;
        funcState.lasttarget = -1;
        funcState.jpc = new IntPtr(-1);
        funcState.freereg = (short) 0;
        funcState.nk = 0;
        funcState.np = 0;
        funcState.nups = (short) 0;
        funcState.nlocvars = (short) 0;
        funcState.nactvar = (short) 0;
        funcState.firstlocal = this.dyd.n_actvar;
        funcState.bl = null;
        funcState.f.source = this.source;
        funcState.f.maxstacksize = 2;
        funcState.enterblock(blockCnt, false);
    }

    void parlist() {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        int i = 0;
        prototype.is_vararg = 0;
        if (this.t.token != 41) {
            do {
                int i2 = this.t.token;
                if (i2 == TK_DOTS) {
                    next();
                    prototype.is_vararg = 1;
                } else if (i2 != TK_NAME) {
                    syntaxerror(new StringBuffer("<name> or ").append(LUA_QL("...")).append(" expected").toString());
                } else {
                    new_localvar(str_checkname());
                    i++;
                }
                if (prototype.is_vararg != 0) {
                    break;
                }
            } while (testnext(44));
        }
        adjustlocalvars(i);
        prototype.numparams = funcState.nactvar;
        funcState.reserveregs(funcState.nactvar);
    }

    void primaryexp(expdesc expdescVar) {
        int i = this.t.token;
        if (i != 40) {
            if (i != TK_NAME) {
                syntaxerror(new StringBuffer("unexpected symbol ").append(this.t.token).append(" (").append((char) this.t.token).append(Constant.AFTER_QUTO).toString());
                return;
            } else {
                singlevar(expdescVar);
                return;
            }
        }
        int i2 = this.linenumber;
        next();
        expr(expdescVar);
        check_match(41, 40, i2);
        this.fs.dischargevars(expdescVar);
    }

    void read_long_string(SemInfo semInfo, int i) {
        String str;
        int i2;
        save_and_next();
        if (currIsNewline()) {
            inclinenumber();
        }
        boolean z = false;
        while (!z) {
            int i3 = this.current;
            if (i3 == -1) {
                str = semInfo != null ? "unfinished long string" : "unfinished long comment";
                i2 = TK_EOS;
            } else if (i3 == 10 || i3 == 13) {
                save(10);
                inclinenumber();
                if (semInfo == null) {
                    this.nbuff = 0;
                }
            } else {
                i2 = 91;
                if (i3 != 91) {
                    if (i3 != 93) {
                        if (semInfo != null) {
                            save_and_next();
                        } else {
                            nextChar();
                        }
                    } else if (skip_sep() == i) {
                        save_and_next();
                        z = true;
                    }
                } else if (skip_sep() == i) {
                    save_and_next();
                    if (i == 0) {
                        str = "nesting of [[...]] is deprecated";
                    }
                }
            }
            lexerror(str, i2);
        }
        if (semInfo != null) {
            int i4 = i + 2;
            semInfo.ts = this.L.newTString(LuaString.valueOf(this.buff, i4, this.nbuff - (i4 * 2)));
        }
    }

    void read_numeral(SemInfo semInfo) {
        int i = this.current;
        _assert(isdigit(i));
        save_and_next();
        String str = (i == 48 && check_next("Xx")) ? "Pp" : "Ee";
        while (true) {
            if (check_next(str)) {
                check_next("+-");
            }
            if (!isxdigit(this.current) && this.current != 46) {
                save(0);
                str2d(new String(this.buff, 0, this.nbuff), semInfo);
                return;
            }
            save_and_next();
        }
    }

    void read_string(int i, SemInfo semInfo) {
        while (true) {
            save_and_next();
            while (true) {
                int i2 = this.current;
                if (i2 == i) {
                    save_and_next();
                    semInfo.ts = this.L.newTString(LuaString.valueOf(this.buff, 1, this.nbuff - 2));
                    return;
                }
                if (i2 != -1) {
                    int i3 = 10;
                    if (i2 == 10 || i2 == 13) {
                        lexerror("unfinished string", TK_STRING);
                    } else {
                        if (i2 != 92) {
                            break;
                        }
                        nextChar();
                        int i4 = this.current;
                        if (i4 == -1) {
                            continue;
                        } else if (i4 == 10 || i4 == 13) {
                            save(10);
                            inclinenumber();
                        } else {
                            if (i4 == 102) {
                                i3 = 12;
                            } else if (i4 != 110) {
                                if (i4 == 114) {
                                    i3 = 13;
                                } else if (i4 == 116) {
                                    i3 = 9;
                                } else if (i4 == 118) {
                                    i3 = 11;
                                } else if (i4 == 120) {
                                    i3 = readhexaesc();
                                } else if (i4 == 122) {
                                    while (true) {
                                        nextChar();
                                        while (true) {
                                            if (isspace(this.current)) {
                                                if (currIsNewline()) {
                                                    inclinenumber();
                                                }
                                            }
                                        }
                                    }
                                } else if (i4 == 97) {
                                    i3 = 7;
                                } else if (i4 == 98) {
                                    i3 = 8;
                                } else {
                                    if (!isdigit(i4)) {
                                        break;
                                    }
                                    int i5 = 0;
                                    int i6 = 0;
                                    do {
                                        i5 = (i5 * 10) + (this.current - 48);
                                        nextChar();
                                        i6++;
                                        if (i6 >= 3) {
                                            break;
                                        }
                                    } while (isdigit(this.current));
                                    if (i5 > 255) {
                                        lexerror("escape sequence too large", TK_STRING);
                                    }
                                    save(i5);
                                }
                            }
                            save(i3);
                            nextChar();
                        }
                    }
                } else {
                    lexerror("unfinished string", TK_EOS);
                }
            }
        }
    }

    int readhexaesc() {
        nextChar();
        int i = this.current;
        nextChar();
        int i2 = this.current;
        if (!isxdigit(i) || !isxdigit(i2)) {
            lexerror(new StringBuffer("hexadecimal digit expected 'x").append((char) i).append((char) i2).toString(), TK_STRING);
        }
        return (hexvalue(i) << 4) + hexvalue(i2);
    }

    void recfield(ConsControl consControl) {
        FuncState funcState = this.fs;
        short s = funcState.freereg;
        expdesc expdescVar = new expdesc();
        expdesc expdescVar2 = new expdesc();
        if (this.t.token == TK_NAME) {
            funcState.checklimit(consControl.nh, MAX_INT, "items in a constructor");
            checkname(expdescVar);
        } else {
            yindex(expdescVar);
        }
        consControl.nh++;
        checknext(61);
        int iExp2RK = funcState.exp2RK(expdescVar);
        expr(expdescVar2);
        funcState.codeABC(10, consControl.t.u.info, iExp2RK, funcState.exp2RK(expdescVar2));
        funcState.freereg = s;
    }

    int registerlocalvar(LuaString luaString) {
        FuncState funcState = this.fs;
        Prototype prototype = funcState.f;
        if (prototype.locvars == null || funcState.nlocvars + 1 > prototype.locvars.length) {
            prototype.locvars = realloc(prototype.locvars, (funcState.nlocvars * 2) + 1);
        }
        prototype.locvars[funcState.nlocvars] = new LocVars(luaString, 0, 0);
        short s = funcState.nlocvars;
        funcState.nlocvars = (short) (s + 1);
        return s;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    void removevars(int i) {
        FuncState funcState = this.fs;
        while (funcState.nactvar > i) {
            short s = (short) (funcState.nactvar - 1);
            funcState.nactvar = s;
            funcState.getlocvar(s).endpc = funcState.pc;
        }
    }

    void repeatstat(int i) {
        FuncState funcState = this.fs;
        int i2 = funcState.getlabel();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        FuncState.BlockCnt blockCnt2 = new FuncState.BlockCnt();
        funcState.enterblock(blockCnt, true);
        funcState.enterblock(blockCnt2, false);
        next();
        statlist();
        check_match(TK_UNTIL, 273, i);
        int iCond = cond();
        if (blockCnt2.upval) {
            funcState.patchclose(iCond, blockCnt2.nactvar);
        }
        funcState.leaveblock();
        funcState.patchlist(iCond, i2);
        funcState.leaveblock();
    }

    void retstat() {
        int iExplist;
        FuncState funcState = this.fs;
        expdesc expdescVar = new expdesc();
        int iExp2anyreg = 0;
        if (block_follow(true) || this.t.token == 59) {
            iExplist = 0;
        } else {
            iExplist = explist(expdescVar);
            if (hasmultret(expdescVar.k)) {
                funcState.setmultret(expdescVar);
                if (expdescVar.k == 12 && iExplist == 1) {
                    SET_OPCODE(funcState.getcodePtr(expdescVar), 30);
                    _assert(Lua.GETARG_A(funcState.getcode(expdescVar)) == funcState.nactvar);
                }
                iExp2anyreg = funcState.nactvar;
                iExplist = -1;
            } else if (iExplist == 1) {
                iExp2anyreg = funcState.exp2anyreg(expdescVar);
            } else {
                funcState.exp2nextreg(expdescVar);
                short s = funcState.nactvar;
                _assert(iExplist == funcState.freereg - s);
                iExp2anyreg = s;
            }
        }
        funcState.ret(iExp2anyreg, iExplist);
        testnext(59);
    }

    void save(int i) {
        char[] cArr = this.buff;
        if (cArr == null || this.nbuff + 1 > cArr.length) {
            this.buff = realloc(cArr, (this.nbuff * 2) + 1);
        }
        char[] cArr2 = this.buff;
        int i2 = this.nbuff;
        this.nbuff = i2 + 1;
        cArr2[i2] = (char) i;
    }

    void save_and_next() {
        save(this.current);
        nextChar();
    }

    void semerror(String str) {
        this.t.token = 0;
        syntaxerror(str);
    }

    void setinput(LuaC.CompileState compileState, int i, InputStream inputStream, LuaString luaString) {
        this.decpoint = (byte) 46;
        this.L = compileState;
        this.lookahead.token = TK_EOS;
        this.z = inputStream;
        this.fs = null;
        this.linenumber = 1;
        this.lastline = 1;
        this.source = luaString;
        this.envn = LuaValue.ENV;
        this.nbuff = 0;
        this.current = i;
        skipShebang();
    }

    void simpleexp(expdesc expdescVar) {
        int i;
        int i2 = this.t.token;
        if (i2 == 123) {
            constructor(expdescVar);
            return;
        }
        if (i2 != 263) {
            if (i2 == TK_FUNCTION) {
                next();
                body(expdescVar, false, this.linenumber);
                return;
            }
            if (i2 == TK_NIL) {
                expdescVar.init(1, 0);
            } else if (i2 == TK_TRUE) {
                i = 2;
            } else if (i2 == TK_DOTS) {
                FuncState funcState = this.fs;
                check_condition(funcState.f.is_vararg != 0, new StringBuffer("cannot use ").append(LUA_QL("...")).append(" outside a vararg function").toString());
                expdescVar.init(13, funcState.codeABC(38, 0, 1, 0));
            } else if (i2 == 287) {
                expdescVar.init(5, 0);
                expdescVar.u.setNval(this.t.seminfo.r);
            } else {
                if (i2 != TK_STRING) {
                    suffixedexp(expdescVar);
                    return;
                }
                codestring(expdescVar, this.t.seminfo.ts);
            }
            next();
        }
        i = 3;
        expdescVar.init(i, 0);
        next();
    }

    void singlevar(expdesc expdescVar) {
        LuaString luaStringStr_checkname = str_checkname();
        FuncState funcState = this.fs;
        boolean z = true;
        if (FuncState.singlevaraux(funcState, luaStringStr_checkname, expdescVar, 1) == 0) {
            expdesc expdescVar2 = new expdesc();
            FuncState.singlevaraux(funcState, this.envn, expdescVar, 1);
            if (expdescVar.k != 7 && expdescVar.k != 8) {
                z = false;
            }
            _assert(z);
            codestring(expdescVar2, luaStringStr_checkname);
            funcState.indexed(expdescVar, expdescVar2);
        }
    }

    int skip_sep() {
        int i;
        int i2 = this.current;
        int i3 = 0;
        _assert(i2 == 91 || i2 == 93);
        save_and_next();
        while (true) {
            i = this.current;
            if (i != 61) {
                break;
            }
            save_and_next();
            i3++;
        }
        return i == i2 ? i3 : (-i3) - 1;
    }

    void skipnoopstat() {
        while (true) {
            if (this.t.token != 59 && this.t.token != TK_DBCOLON) {
                return;
            } else {
                statement();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0053  */
    void statement() {
        int i = this.linenumber;
        enterlevel();
        int i2 = this.t.token;
        if (i2 == 59) {
            next();
        } else if (i2 == TK_LOCAL) {
            next();
            if (testnext(TK_FUNCTION)) {
                localfunc();
            } else {
                localstat();
            }
        } else if (i2 == TK_WHILE) {
            whilestat(i);
        } else if (i2 == TK_DBCOLON) {
            next();
            labelstat(str_checkname(), i);
        } else if (i2 == 258) {
            gotostat(this.fs.jump());
        } else if (i2 == 259) {
            next();
            block();
            check_match(262, 259, i);
        } else if (i2 == 273) {
            repeatstat(i);
        } else if (i2 != TK_RETURN) {
            switch (i2) {
                case 264:
                    forstat(i);
                    break;
                case TK_FUNCTION /* 265 */:
                    funcstat(i);
                    break;
                case TK_GOTO /* 266 */:
                    gotostat(this.fs.jump());
                    break;
                case TK_IF /* 267 */:
                    ifstat(i);
                    break;
                default:
                    exprstat();
                    break;
            }
        } else {
            next();
            retstat();
        }
        _assert(this.fs.f.maxstacksize >= this.fs.freereg && this.fs.freereg >= this.fs.nactvar);
        FuncState funcState = this.fs;
        funcState.freereg = funcState.nactvar;
        leavelevel();
    }

    void statlist() {
        while (!block_follow(true)) {
            int i = this.t.token;
            statement();
            if (i == TK_RETURN) {
                return;
            }
        }
    }

    boolean str2d(String str, SemInfo semInfo) {
        LuaValue luaValueStrx2number;
        if (str.indexOf(110) >= 0 || str.indexOf(78) >= 0) {
            luaValueStrx2number = LuaValue.ZERO;
        } else {
            luaValueStrx2number = (str.indexOf(120) >= 0 || str.indexOf(88) >= 0) ? strx2number(str, semInfo) : LuaValue.valueOf(Double.parseDouble(str.trim()));
        }
        semInfo.r = luaValueStrx2number;
        return true;
    }

    LuaString str_checkname() {
        check(TK_NAME);
        LuaString luaString = this.t.seminfo.ts;
        next();
        return luaString;
    }

    LuaValue strx2number(String str, SemInfo semInfo) {
        double d;
        int i;
        char c;
        boolean z;
        char[] charArray = str.toCharArray();
        int i2 = 0;
        int i3 = 0;
        while (i3 < charArray.length && isspace(charArray[i3])) {
            i3++;
        }
        if (i3 >= charArray.length || charArray[i3] != '-') {
            d = 1.0d;
        } else {
            i3++;
            d = -1.0d;
        }
        if (i3 + 2 >= charArray.length) {
            return LuaValue.ZERO;
        }
        int i4 = i3 + 1;
        if (charArray[i3] != '0') {
            return LuaValue.ZERO;
        }
        char c2 = charArray[i4];
        if (c2 != 'x' && c2 != 'X') {
            return LuaValue.ZERO;
        }
        int i5 = i3 + 2;
        double dHexvalue = 0.0d;
        while (i5 < charArray.length && isxdigit(charArray[i5])) {
            dHexvalue = (dHexvalue * 16.0d) + ((double) hexvalue(charArray[i5]));
            i5++;
        }
        if (i5 >= charArray.length || charArray[i5] != '.') {
            i = 0;
        } else {
            i5++;
            i = 0;
            while (i5 < charArray.length && isxdigit(charArray[i5])) {
                dHexvalue = (dHexvalue * 16.0d) + ((double) hexvalue(charArray[i5]));
                i -= 4;
                i5++;
            }
        }
        if (i5 < charArray.length && ((c = charArray[i5]) == 'p' || c == 'P')) {
            int i6 = i5 + 1;
            if (i6 >= charArray.length || charArray[i6] != '-') {
                z = false;
            } else {
                i6 = i5 + 2;
                z = true;
            }
            while (i6 < charArray.length && isdigit(charArray[i6])) {
                i2 = ((i2 * 10) + charArray[i6]) - 48;
                i6++;
            }
            if (z) {
                i2 = -i2;
            }
            i += i2;
        }
        return LuaValue.valueOf(d * dHexvalue * MathLib.dpow_d(2.0d, i));
    }

    int subexpr(expdesc expdescVar, int i) {
        enterlevel();
        int i2 = getunopr(this.t.token);
        if (i2 != 3) {
            int i3 = this.linenumber;
            next();
            subexpr(expdescVar, 8);
            this.fs.prefix(i2, expdescVar, i3);
        } else {
            simpleexp(expdescVar);
        }
        int i4 = getbinopr(this.t.token);
        while (i4 != 15 && priority[i4].left > i) {
            expdesc expdescVar2 = new expdesc();
            int i5 = this.linenumber;
            next();
            this.fs.infix(i4, expdescVar);
            int iSubexpr = subexpr(expdescVar2, priority[i4].right);
            this.fs.posfix(i4, expdescVar, expdescVar2, i5);
            i4 = iSubexpr;
        }
        leavelevel();
        return i4;
    }

    void suffixedexp(expdesc expdescVar) {
        int i = this.linenumber;
        primaryexp(expdescVar);
        while (true) {
            int i2 = this.t.token;
            if (i2 != 40) {
                if (i2 == 46) {
                    fieldsel(expdescVar);
                } else if (i2 == 58) {
                    expdesc expdescVar2 = new expdesc();
                    next();
                    checkname(expdescVar2);
                    this.fs.self(expdescVar, expdescVar2);
                    funcargs(expdescVar, i);
                } else if (i2 == 91) {
                    expdesc expdescVar3 = new expdesc();
                    this.fs.exp2anyregup(expdescVar);
                    yindex(expdescVar3);
                    this.fs.indexed(expdescVar, expdescVar3);
                } else if (i2 != 123 && i2 != TK_STRING) {
                    return;
                }
            }
            this.fs.exp2nextreg(expdescVar);
            funcargs(expdescVar, i);
        }
    }

    void syntaxerror(String str) {
        lexerror(str, this.t.token);
    }

    void test_then_block(IntPtr intPtr) {
        int iJump;
        expdesc expdescVar = new expdesc();
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        next();
        expr(expdescVar);
        checknext(TK_THEN);
        if (this.t.token == TK_GOTO || this.t.token == 258) {
            this.fs.goiffalse(expdescVar);
            this.fs.enterblock(blockCnt, false);
            gotostat(expdescVar.t.i);
            skipnoopstat();
            if (block_follow(false)) {
                this.fs.leaveblock();
                return;
            }
            iJump = this.fs.jump();
        } else {
            this.fs.goiftrue(expdescVar);
            this.fs.enterblock(blockCnt, false);
            iJump = expdescVar.f.i;
        }
        statlist();
        this.fs.leaveblock();
        if (this.t.token == 260 || this.t.token == 261) {
            FuncState funcState = this.fs;
            funcState.concat(intPtr, funcState.jump());
        }
        this.fs.patchtohere(iJump);
    }

    boolean testnext(int i) {
        if (this.t.token != i) {
            return false;
        }
        next();
        return true;
    }

    String token2str(int i) {
        LuaC.CompileState compileState;
        String strValueOf;
        if (i >= 257) {
            return luaX_tokens[i - 257];
        }
        if (iscntrl(i)) {
            compileState = this.L;
            strValueOf = new StringBuffer("char(").append(i).append(Constant.AFTER_QUTO).toString();
        } else {
            compileState = this.L;
            strValueOf = String.valueOf((char) i);
        }
        return compileState.pushfstring(strValueOf);
    }

    String txtToken(int i) {
        switch (i) {
            case 287:
            case TK_NAME /* 288 */:
            case TK_STRING /* 289 */:
                return new String(this.buff, 0, this.nbuff);
            default:
                return token2str(i);
        }
    }

    void undefgoto(Labeldesc labeldesc) {
        semerror(this.L.pushfstring((isReservedKeyword(labeldesc.f1056name.tojstring()) ? new StringBuffer("<").append(labeldesc.f1056name).append("> at line ").append(labeldesc.line).append(" not inside a loop") : new StringBuffer("no visible label '").append(labeldesc.f1056name).append("' for <goto> at line ").append(labeldesc.line)).toString()));
    }

    void whilestat(int i) {
        FuncState funcState = this.fs;
        FuncState.BlockCnt blockCnt = new FuncState.BlockCnt();
        next();
        int i2 = funcState.getlabel();
        int iCond = cond();
        funcState.enterblock(blockCnt, true);
        checknext(259);
        block();
        funcState.patchlist(funcState.jump(), i2);
        check_match(262, TK_WHILE, i);
        funcState.leaveblock();
        funcState.patchtohere(iCond);
    }

    void yindex(expdesc expdescVar) {
        next();
        expr(expdescVar);
        this.fs.exp2val(expdescVar);
        checknext(93);
    }
}
