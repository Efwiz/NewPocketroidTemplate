package org.luaj.vm2;

import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes6.dex */
public abstract class LuaValue extends Varargs {
    private static final int MAXTAGLOOP = 100;
    public static final int TBOOLEAN = 1;
    public static final int TFUNCTION = 6;
    public static final int TINT = -2;
    public static final int TLIGHTUSERDATA = 2;
    public static final int TNIL = 0;
    public static final int TNONE = -1;
    public static final int TNUMBER = 3;
    public static final int TSTRING = 4;
    public static final int TTABLE = 5;
    public static final int TTHREAD = 8;
    public static final int TUSERDATA = 7;
    public static final int TVALUE = 9;
    public static final String[] TYPE_NAMES = {"nil", "boolean", "lightuserdata", "number", "string", "table", "function", "userdata", "thread", "value"};
    public static final LuaValue NIL = LuaNil._NIL;
    public static final LuaBoolean TRUE = LuaBoolean._TRUE;
    public static final LuaBoolean FALSE = LuaBoolean._FALSE;
    public static final LuaValue NONE = None._NONE;
    public static final LuaNumber ZERO = LuaInteger.valueOf(0);
    public static final LuaNumber ONE = LuaInteger.valueOf(1);
    public static final LuaNumber MINUSONE = LuaInteger.valueOf(-1);
    public static final LuaValue[] NOVALS = new LuaValue[0];
    public static LuaString ENV = valueOf("_ENV");
    public static final LuaString INDEX = valueOf("__index");
    public static final LuaString NEWINDEX = valueOf("__newindex");
    public static final LuaString CALL = valueOf("__call");
    public static final LuaString MODE = valueOf("__mode");
    public static final LuaString METATABLE = valueOf("__metatable");
    public static final LuaString ADD = valueOf("__add");
    public static final LuaString SUB = valueOf("__sub");
    public static final LuaString DIV = valueOf("__div");
    public static final LuaString MUL = valueOf("__mul");
    public static final LuaString POW = valueOf("__pow");
    public static final LuaString MOD = valueOf("__mod");
    public static final LuaString UNM = valueOf("__unm");
    public static final LuaString LEN = valueOf("__len");
    public static final LuaString EQ = valueOf("__eq");
    public static final LuaString LT = valueOf("__lt");
    public static final LuaString LE = valueOf("__le");
    public static final LuaString TOSTRING = valueOf("__tostring");
    public static final LuaString CONCAT = valueOf("__concat");
    public static final LuaString EMPTYSTRING = valueOf("");
    private static int MAXSTACK = 250;
    public static final LuaValue[] NILS = new LuaValue[250];

    private static final class None extends LuaNil {
        static None _NONE = new None();

        private None() {
        }

        @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public LuaValue arg(int i) {
            return NIL;
        }

        @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public LuaValue arg1() {
            return NIL;
        }

        @Override // org.luaj.vm2.Varargs
        void copyto(LuaValue[] luaValueArr, int i, int i2) {
            while (i2 > 0) {
                luaValueArr[i] = NIL;
                i2--;
                i++;
            }
        }

        @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public int narg() {
            return 0;
        }

        @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public Varargs subargs(int i) {
            return i > 0 ? this : argerror(1, "start must be > 0");
        }

        @Override // org.luaj.vm2.LuaNil, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
        public String tojstring() {
            return "none";
        }
    }

    static {
        for (int i = 0; i < MAXSTACK; i++) {
            NILS[i] = NIL;
        }
    }

    public static LuaValue argerror(int i, String str) {
        throw new LuaError(new StringBuffer("bad argument #").append(i).append(": ").append(str).toString());
    }

    public static void assert_(boolean z, String str) {
        if (!z) {
            throw new LuaError(str);
        }
    }

    public static final boolean eqmtcall(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
        LuaString luaString = EQ;
        LuaValue luaValueRawget = luaValue2.rawget(luaString);
        if (luaValueRawget.isnil() || luaValueRawget != luaValue4.rawget(luaString)) {
            return false;
        }
        return luaValueRawget.call(luaValue, luaValue3).toboolean();
    }

    public static LuaValue error(String str) {
        throw new LuaError(str);
    }

    protected static LuaValue gettable(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue luaValueMetatag;
        LuaValue luaValueRawget;
        int i = 0;
        while (true) {
            if (luaValue.istable()) {
                luaValueRawget = luaValue.rawget(luaValue2);
                if (!luaValueRawget.isnil()) {
                    break;
                }
                LuaValue luaValueMetatag2 = luaValue.metatag(INDEX);
                if (luaValueMetatag2.isnil()) {
                    break;
                }
                luaValueMetatag = luaValueMetatag2;
            } else {
                luaValueMetatag = luaValue.metatag(INDEX);
                if (luaValueMetatag.isnil()) {
                    luaValue.indexerror();
                }
            }
            if (luaValueMetatag.isfunction()) {
                return luaValueMetatag.call(luaValue, luaValue2);
            }
            i++;
            if (i >= 100) {
                error("loop in gettable");
                return NIL;
            }
            luaValue = luaValueMetatag;
        }
        return luaValueRawget;
    }

    private void indexerror() {
        error(new StringBuffer("attempt to index ? (a ").append(typename()).append(" value)").toString());
    }

    public static LuaTable listOf(LuaValue[] luaValueArr) {
        return new LuaTable(null, luaValueArr, null);
    }

    public static LuaTable listOf(LuaValue[] luaValueArr, Varargs varargs) {
        return new LuaTable(null, luaValueArr, varargs);
    }

    protected static Metatable metatableOf(LuaValue luaValue) {
        if (luaValue == null || !luaValue.istable()) {
            if (luaValue != null) {
                return new NonTableMetatable(luaValue);
            }
            return null;
        }
        LuaValue luaValueRawget = luaValue.rawget(MODE);
        if (luaValueRawget.isstring()) {
            String str = luaValueRawget.tojstring();
            boolean z = str.indexOf(107) >= 0;
            boolean z2 = str.indexOf(118) >= 0;
            if (z || z2) {
                return new WeakTable(z, z2, luaValue);
            }
        }
        return (LuaTable) luaValue;
    }

    protected static boolean settable(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        LuaValue luaValueMetatag;
        int i = 0;
        while (true) {
            if (!luaValue.istable()) {
                luaValueMetatag = luaValue.metatag(NEWINDEX);
                if (luaValueMetatag.isnil()) {
                    luaValue.typerror("index");
                }
            } else {
                if (!luaValue.rawget(luaValue2).isnil()) {
                    break;
                }
                luaValueMetatag = luaValue.metatag(NEWINDEX);
                if (luaValueMetatag.isnil()) {
                    break;
                }
            }
            if (luaValueMetatag.isfunction()) {
                luaValueMetatag.call(luaValue, luaValue2, luaValue3);
                return true;
            }
            i++;
            if (i >= 100) {
                error("loop in settable");
                return false;
            }
            luaValue = luaValueMetatag;
        }
        luaValue.rawset(luaValue2, luaValue3);
        return true;
    }

    public static LuaTable tableOf() {
        return new LuaTable();
    }

    public static LuaTable tableOf(int i, int i2) {
        return new LuaTable(i, i2);
    }

    public static LuaTable tableOf(Varargs varargs, int i) {
        return new LuaTable(varargs, i);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArr) {
        return new LuaTable(luaValueArr, null, null);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArr, LuaValue[] luaValueArr2) {
        return new LuaTable(luaValueArr, luaValueArr2, null);
    }

    public static LuaTable tableOf(LuaValue[] luaValueArr, LuaValue[] luaValueArr2, Varargs varargs) {
        return new LuaTable(luaValueArr, luaValueArr2, varargs);
    }

    public static Varargs tailcallOf(LuaValue luaValue, Varargs varargs) {
        return new TailcallVarargs(luaValue, varargs);
    }

    public static LuaUserdata userdataOf(Object obj) {
        return new LuaUserdata(obj);
    }

    public static LuaUserdata userdataOf(Object obj, LuaValue luaValue) {
        return new LuaUserdata(obj, luaValue);
    }

    public static LuaBoolean valueOf(boolean z) {
        return z ? TRUE : FALSE;
    }

    public static LuaInteger valueOf(int i) {
        return LuaInteger.valueOf(i);
    }

    public static LuaNumber valueOf(double d) {
        return LuaDouble.valueOf(d);
    }

    public static LuaString valueOf(String str) {
        return LuaString.valueOf(str);
    }

    public static LuaString valueOf(byte[] bArr) {
        return LuaString.valueOf(bArr);
    }

    public static LuaString valueOf(byte[] bArr, int i, int i2) {
        return LuaString.valueOf(bArr, i, i2);
    }

    public static Varargs varargsOf(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        return varargs.narg() != 0 ? new Varargs.ArrayPartVarargs(new LuaValue[]{luaValue, luaValue2}, 0, 2, varargs) : new Varargs.PairVarargs(luaValue, luaValue2);
    }

    public static Varargs varargsOf(LuaValue luaValue, Varargs varargs) {
        return varargs.narg() != 0 ? new Varargs.PairVarargs(luaValue, varargs) : luaValue;
    }

    public static Varargs varargsOf(LuaValue[] luaValueArr) {
        int length = luaValueArr.length;
        if (length == 0) {
            return NONE;
        }
        if (length != 1) {
            return length != 2 ? new Varargs.ArrayVarargs(luaValueArr, NONE) : new Varargs.PairVarargs(luaValueArr[0], luaValueArr[1]);
        }
        return luaValueArr[0];
    }

    public static Varargs varargsOf(LuaValue[] luaValueArr, int i, int i2) {
        if (i2 == 0) {
            return NONE;
        }
        if (i2 != 1) {
            return i2 != 2 ? new Varargs.ArrayPartVarargs(luaValueArr, i, i2, NONE) : new Varargs.PairVarargs(luaValueArr[i], luaValueArr[i + 1]);
        }
        return luaValueArr[i];
    }

    public static Varargs varargsOf(LuaValue[] luaValueArr, int i, int i2, Varargs varargs) {
        if (i2 == 0) {
            return varargs;
        }
        if (i2 == 1) {
            return varargs.narg() > 0 ? new Varargs.PairVarargs(luaValueArr[i], varargs) : luaValueArr[i];
        }
        if (i2 == 2 && varargs.narg() <= 0) {
            return new Varargs.PairVarargs(luaValueArr[i], luaValueArr[i + 1]);
        }
        return new Varargs.ArrayPartVarargs(luaValueArr, i, i2, varargs);
    }

    public static Varargs varargsOf(LuaValue[] luaValueArr, Varargs varargs) {
        int length = luaValueArr.length;
        if (length == 0) {
            return varargs;
        }
        if (length == 1) {
            return varargs.narg() > 0 ? new Varargs.PairVarargs(luaValueArr[0], varargs) : luaValueArr[0];
        }
        if (length == 2 && varargs.narg() <= 0) {
            return new Varargs.PairVarargs(luaValueArr[0], luaValueArr[1]);
        }
        return new Varargs.ArrayVarargs(luaValueArr, varargs);
    }

    public LuaValue add(double d) {
        return arithmtwith(ADD, d);
    }

    public LuaValue add(int i) {
        return add(i);
    }

    public LuaValue add(LuaValue luaValue) {
        return arithmt(ADD, luaValue);
    }

    public LuaValue and(LuaValue luaValue) {
        return toboolean() ? luaValue : this;
    }

    @Override // org.luaj.vm2.Varargs
    public LuaValue arg(int i) {
        return i == 1 ? this : NIL;
    }

    @Override // org.luaj.vm2.Varargs
    public LuaValue arg1() {
        return this;
    }

    protected LuaValue argerror(String str) {
        throw new LuaError(new StringBuffer("bad argument: ").append(str).append(" expected, got ").append(typename()).toString());
    }

    protected LuaValue aritherror() {
        throw new LuaError(new StringBuffer("attempt to perform arithmetic on ").append(typename()).toString());
    }

    protected LuaValue aritherror(String str) {
        throw new LuaError(new StringBuffer("attempt to perform arithmetic '").append(str).append("' on ").append(typename()).toString());
    }

    protected LuaValue arithmt(LuaValue luaValue, LuaValue luaValue2) {
        LuaValue luaValueMetatag = metatag(luaValue);
        if (luaValueMetatag.isnil()) {
            luaValueMetatag = luaValue2.metatag(luaValue);
            if (luaValueMetatag.isnil()) {
                error(new StringBuffer("attempt to perform arithmetic ").append(luaValue).append(" on ").append(typename()).append(" and ").append(luaValue2.typename()).toString());
            }
        }
        return luaValueMetatag.call(this, luaValue2);
    }

    protected LuaValue arithmtwith(LuaValue luaValue, double d) {
        LuaValue luaValueMetatag = metatag(luaValue);
        if (luaValueMetatag.isnil()) {
            error(new StringBuffer("attempt to perform arithmetic ").append(luaValue).append(" on number and ").append(typename()).toString());
        }
        return luaValueMetatag.call(valueOf(d), this);
    }

    public Buffer buffer() {
        return new Buffer(this);
    }

    public LuaValue call() {
        return callmt().call(this);
    }

    public LuaValue call(String str) {
        return call(valueOf(str));
    }

    public LuaValue call(LuaValue luaValue) {
        return callmt().call(this, luaValue);
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return callmt().call(this, luaValue, luaValue2);
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return callmt().invoke(new LuaValue[]{this, luaValue, luaValue2, luaValue3}).arg1();
    }

    protected LuaValue callmt() {
        return checkmetatag(CALL, "attempt to call ");
    }

    public boolean checkboolean() {
        argerror("boolean");
        return false;
    }

    public LuaClosure checkclosure() {
        argerror("closure");
        return null;
    }

    public double checkdouble() {
        argerror("double");
        return 0.0d;
    }

    public LuaFunction checkfunction() {
        argerror("function");
        return null;
    }

    public Globals checkglobals() {
        argerror("globals");
        return null;
    }

    public int checkint() {
        argerror("int");
        return 0;
    }

    public LuaInteger checkinteger() {
        argerror("integer");
        return null;
    }

    public String checkjstring() {
        argerror("string");
        return null;
    }

    public long checklong() {
        argerror("long");
        return 0L;
    }

    protected LuaValue checkmetatag(LuaValue luaValue, String str) {
        LuaValue luaValueMetatag = metatag(luaValue);
        if (luaValueMetatag.isnil()) {
            throw new LuaError(new StringBuffer().append(str).append(typename()).toString());
        }
        return luaValueMetatag;
    }

    public LuaValue checknotnil() {
        return this;
    }

    public LuaNumber checknumber() {
        argerror("number");
        return null;
    }

    public LuaNumber checknumber(String str) {
        throw new LuaError(str);
    }

    public LuaString checkstring() {
        argerror("string");
        return null;
    }

    public LuaTable checktable() {
        argerror("table");
        return null;
    }

    public LuaThread checkthread() {
        argerror("thread");
        return null;
    }

    public Object checkuserdata() {
        argerror("userdata");
        return null;
    }

    public Object checkuserdata(Class cls) {
        argerror("userdata");
        return null;
    }

    protected LuaValue compareerror(String str) {
        throw new LuaError(new StringBuffer("attempt to compare ").append(typename()).append(" with ").append(str).toString());
    }

    protected LuaValue compareerror(LuaValue luaValue) {
        throw new LuaError(new StringBuffer("attempt to compare ").append(typename()).append(" with ").append(luaValue.typename()).toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
    
        if (r1.isnil() == false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.luaj.vm2.LuaValue comparemt(org.luaj.vm2.LuaValue r4, org.luaj.vm2.LuaValue r5) {
        /*
            r3 = this;
            org.luaj.vm2.LuaValue r0 = r3.metatag(r4)
            boolean r1 = r0.isnil()
            if (r1 == 0) goto L6c
            org.luaj.vm2.LuaValue r0 = r5.metatag(r4)
            boolean r1 = r0.isnil()
            if (r1 != 0) goto L15
            goto L6c
        L15:
            org.luaj.vm2.LuaString r0 = org.luaj.vm2.LuaValue.LE
            boolean r0 = r0.raweq(r4)
            if (r0 == 0) goto L3c
            org.luaj.vm2.LuaString r0 = org.luaj.vm2.LuaValue.LT
            org.luaj.vm2.LuaValue r1 = r3.metatag(r0)
            boolean r2 = r1.isnil()
            if (r2 == 0) goto L33
            org.luaj.vm2.LuaValue r1 = r5.metatag(r0)
            boolean r0 = r1.isnil()
            if (r0 != 0) goto L3c
        L33:
            org.luaj.vm2.LuaValue r4 = r1.call(r5, r3)
            org.luaj.vm2.LuaValue r4 = r4.not()
            return r4
        L3c:
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            java.lang.String r1 = "attempt to compare "
            r0.<init>(r1)
            java.lang.StringBuffer r4 = r0.append(r4)
            java.lang.String r0 = " on "
            java.lang.StringBuffer r4 = r4.append(r0)
            java.lang.String r0 = r3.typename()
            java.lang.StringBuffer r4 = r4.append(r0)
            java.lang.String r0 = " and "
            java.lang.StringBuffer r4 = r4.append(r0)
            java.lang.String r5 = r5.typename()
            java.lang.StringBuffer r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            org.luaj.vm2.LuaValue r4 = error(r4)
            return r4
        L6c:
            org.luaj.vm2.LuaValue r4 = r0.call(r3, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.LuaValue.comparemt(org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue");
    }

    public Buffer concat(Buffer buffer) {
        return buffer.concatTo(this);
    }

    public LuaValue concat(LuaValue luaValue) {
        return concatmt(luaValue);
    }

    public LuaValue concatTo(LuaNumber luaNumber) {
        return luaNumber.concatmt(this);
    }

    public LuaValue concatTo(LuaString luaString) {
        return luaString.concatmt(this);
    }

    public LuaValue concatTo(LuaValue luaValue) {
        return luaValue.concatmt(this);
    }

    public LuaValue concatmt(LuaValue luaValue) {
        LuaString luaString = CONCAT;
        LuaValue luaValueMetatag = metatag(luaString);
        if (luaValueMetatag.isnil()) {
            luaValueMetatag = luaValue.metatag(luaString);
            if (luaValueMetatag.isnil()) {
                error(new StringBuffer("attempt to concatenate ").append(typename()).append(" and ").append(luaValue.typename()).toString());
            }
        }
        return luaValueMetatag.call(this, luaValue);
    }

    public LuaValue div(double d) {
        return aritherror("div");
    }

    public LuaValue div(int i) {
        return aritherror("div");
    }

    public LuaValue div(LuaValue luaValue) {
        return arithmt(DIV, luaValue);
    }

    public LuaValue divInto(double d) {
        return arithmtwith(DIV, d);
    }

    public LuaValue eq(LuaValue luaValue) {
        return this == luaValue ? TRUE : FALSE;
    }

    public boolean eq_b(LuaValue luaValue) {
        return this == luaValue;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public LuaValue get(int i) {
        return get(LuaInteger.valueOf(i));
    }

    public LuaValue get(String str) {
        return get(valueOf(str));
    }

    public LuaValue get(LuaValue luaValue) {
        return gettable(this, luaValue);
    }

    public LuaValue getmetatable() {
        return null;
    }

    public LuaValue gt(double d) {
        return compareerror("number");
    }

    public LuaValue gt(int i) {
        return compareerror("number");
    }

    public LuaValue gt(LuaValue luaValue) {
        return luaValue.comparemt(LE, this);
    }

    public boolean gt_b(double d) {
        compareerror("number");
        return false;
    }

    public boolean gt_b(int i) {
        compareerror("number");
        return false;
    }

    public boolean gt_b(LuaValue luaValue) {
        return luaValue.comparemt(LE, this).toboolean();
    }

    public LuaValue gteq(double d) {
        return compareerror("number");
    }

    public LuaValue gteq(int i) {
        return valueOf(todouble() >= ((double) i));
    }

    public LuaValue gteq(LuaValue luaValue) {
        return luaValue.comparemt(LT, this);
    }

    public boolean gteq_b(double d) {
        compareerror("number");
        return false;
    }

    public boolean gteq_b(int i) {
        compareerror("number");
        return false;
    }

    public boolean gteq_b(LuaValue luaValue) {
        return luaValue.comparemt(LT, this).toboolean();
    }

    protected LuaValue illegal(String str, String str2) {
        throw new LuaError(new StringBuffer("illegal operation '").append(str).append("' for ").append(str2).toString());
    }

    public Varargs inext(LuaValue luaValue) {
        return typerror("table");
    }

    public void initupvalue1(LuaValue luaValue) {
    }

    public Varargs invoke() {
        return invoke(NONE);
    }

    public Varargs invoke(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        return invoke(varargsOf(luaValue, luaValue2, varargs));
    }

    public Varargs invoke(LuaValue luaValue, Varargs varargs) {
        return invoke(varargsOf(luaValue, varargs));
    }

    public Varargs invoke(Varargs varargs) {
        return callmt().invoke(this, varargs);
    }

    public Varargs invoke(LuaValue[] luaValueArr) {
        return invoke(varargsOf(luaValueArr));
    }

    public Varargs invoke(LuaValue[] luaValueArr, Varargs varargs) {
        return invoke(varargsOf(luaValueArr, varargs));
    }

    public Varargs invokemethod(String str) {
        return get(str).invoke(this);
    }

    public Varargs invokemethod(String str, Varargs varargs) {
        return get(str).invoke(varargsOf(this, varargs));
    }

    public Varargs invokemethod(String str, LuaValue[] luaValueArr) {
        return get(str).invoke(varargsOf(this, varargsOf(luaValueArr)));
    }

    public Varargs invokemethod(LuaValue luaValue) {
        return get(luaValue).invoke(this);
    }

    public Varargs invokemethod(LuaValue luaValue, Varargs varargs) {
        return get(luaValue).invoke(varargsOf(this, varargs));
    }

    public Varargs invokemethod(LuaValue luaValue, LuaValue[] luaValueArr) {
        return get(luaValue).invoke(varargsOf(this, varargsOf(luaValueArr)));
    }

    public boolean isboolean() {
        return false;
    }

    public boolean isclosure() {
        return false;
    }

    public boolean isfunction() {
        return false;
    }

    public boolean isint() {
        return false;
    }

    public boolean isinttype() {
        return false;
    }

    public boolean islong() {
        return false;
    }

    public boolean isnil() {
        return false;
    }

    public boolean isnumber() {
        return false;
    }

    public boolean isstring() {
        return false;
    }

    public boolean istable() {
        return false;
    }

    public boolean isthread() {
        return false;
    }

    public boolean isuserdata() {
        return false;
    }

    public boolean isuserdata(Class cls) {
        return false;
    }

    public boolean isvalidkey() {
        return true;
    }

    public LuaValue len() {
        return checkmetatag(LEN, "attempt to get length of ").call(this);
    }

    protected LuaValue lenerror() {
        throw new LuaError(new StringBuffer("attempt to get length of ").append(typename()).toString());
    }

    public int length() {
        return len().toint();
    }

    public LuaValue load(LuaValue luaValue) {
        return luaValue.call(EMPTYSTRING, this);
    }

    public LuaValue lt(double d) {
        return compareerror("number");
    }

    public LuaValue lt(int i) {
        return compareerror("number");
    }

    public LuaValue lt(LuaValue luaValue) {
        return comparemt(LT, luaValue);
    }

    public boolean lt_b(double d) {
        compareerror("number");
        return false;
    }

    public boolean lt_b(int i) {
        compareerror("number");
        return false;
    }

    public boolean lt_b(LuaValue luaValue) {
        return comparemt(LT, luaValue).toboolean();
    }

    public LuaValue lteq(double d) {
        return compareerror("number");
    }

    public LuaValue lteq(int i) {
        return compareerror("number");
    }

    public LuaValue lteq(LuaValue luaValue) {
        return comparemt(LE, luaValue);
    }

    public boolean lteq_b(double d) {
        compareerror("number");
        return false;
    }

    public boolean lteq_b(int i) {
        compareerror("number");
        return false;
    }

    public boolean lteq_b(LuaValue luaValue) {
        return comparemt(LE, luaValue).toboolean();
    }

    public LuaValue metatag(LuaValue luaValue) {
        LuaValue luaValue2 = getmetatable();
        return luaValue2 == null ? NIL : luaValue2.rawget(luaValue);
    }

    public LuaValue method(String str) {
        return get(str).call(this);
    }

    public LuaValue method(String str, LuaValue luaValue) {
        return get(str).call(this, luaValue);
    }

    public LuaValue method(String str, LuaValue luaValue, LuaValue luaValue2) {
        return get(str).call(this, luaValue, luaValue2);
    }

    public LuaValue method(LuaValue luaValue) {
        return get(luaValue).call(this);
    }

    public LuaValue method(LuaValue luaValue, LuaValue luaValue2) {
        return get(luaValue).call(this, luaValue2);
    }

    public LuaValue method(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return get(luaValue).call(this, luaValue2, luaValue3);
    }

    public LuaValue mod(double d) {
        return aritherror("mod");
    }

    public LuaValue mod(int i) {
        return aritherror("mod");
    }

    public LuaValue mod(LuaValue luaValue) {
        return arithmt(MOD, luaValue);
    }

    public LuaValue modFrom(double d) {
        return arithmtwith(MOD, d);
    }

    public LuaValue mul(double d) {
        return arithmtwith(MUL, d);
    }

    public LuaValue mul(int i) {
        return mul(i);
    }

    public LuaValue mul(LuaValue luaValue) {
        return arithmt(MUL, luaValue);
    }

    @Override // org.luaj.vm2.Varargs
    public int narg() {
        return 1;
    }

    public LuaValue neg() {
        return checkmetatag(UNM, "attempt to perform arithmetic on ").call(this);
    }

    public LuaValue neq(LuaValue luaValue) {
        return eq_b(luaValue) ? FALSE : TRUE;
    }

    public boolean neq_b(LuaValue luaValue) {
        return !eq_b(luaValue);
    }

    public Varargs next(LuaValue luaValue) {
        return typerror("table");
    }

    public LuaValue not() {
        return FALSE;
    }

    public Varargs onInvoke(Varargs varargs) {
        return invoke(varargs);
    }

    public boolean optboolean(boolean z) {
        argerror("boolean");
        return false;
    }

    public LuaClosure optclosure(LuaClosure luaClosure) {
        argerror("closure");
        return null;
    }

    public double optdouble(double d) {
        argerror("double");
        return 0.0d;
    }

    public LuaFunction optfunction(LuaFunction luaFunction) {
        argerror("function");
        return null;
    }

    public int optint(int i) {
        argerror("int");
        return 0;
    }

    public LuaInteger optinteger(LuaInteger luaInteger) {
        argerror("integer");
        return null;
    }

    public String optjstring(String str) {
        argerror(CommonClassNames.JAVA_LANG_STRING_SHORT);
        return null;
    }

    public long optlong(long j) {
        argerror("long");
        return 0L;
    }

    public LuaNumber optnumber(LuaNumber luaNumber) {
        argerror("number");
        return null;
    }

    public LuaString optstring(LuaString luaString) {
        argerror("string");
        return null;
    }

    public LuaTable opttable(LuaTable luaTable) {
        argerror("table");
        return null;
    }

    public LuaThread optthread(LuaThread luaThread) {
        argerror("thread");
        return null;
    }

    public Object optuserdata(Class cls, Object obj) {
        argerror(cls.getName());
        return null;
    }

    public Object optuserdata(Object obj) {
        argerror("object");
        return null;
    }

    public LuaValue optvalue(LuaValue luaValue) {
        return this;
    }

    public LuaValue or(LuaValue luaValue) {
        return toboolean() ? this : luaValue;
    }

    public LuaValue pow(double d) {
        return aritherror("pow");
    }

    public LuaValue pow(int i) {
        return aritherror("pow");
    }

    public LuaValue pow(LuaValue luaValue) {
        return arithmt(POW, luaValue);
    }

    public LuaValue powWith(double d) {
        return arithmtwith(POW, d);
    }

    public LuaValue powWith(int i) {
        return powWith(i);
    }

    public void presize(int i) {
        typerror("table");
    }

    public boolean raweq(double d) {
        return false;
    }

    public boolean raweq(int i) {
        return false;
    }

    public boolean raweq(LuaString luaString) {
        return false;
    }

    public boolean raweq(LuaUserdata luaUserdata) {
        return false;
    }

    public boolean raweq(LuaValue luaValue) {
        return this == luaValue;
    }

    public LuaValue rawget(int i) {
        return rawget(valueOf(i));
    }

    public LuaValue rawget(String str) {
        return rawget(valueOf(str));
    }

    public LuaValue rawget(LuaValue luaValue) {
        return unimplemented("rawget");
    }

    public int rawlen() {
        typerror("table or string");
        return 0;
    }

    public void rawset(int i, String str) {
        rawset(i, valueOf(str));
    }

    public void rawset(int i, LuaValue luaValue) {
        rawset(valueOf(i), luaValue);
    }

    public void rawset(String str, double d) {
        rawset(valueOf(str), valueOf(d));
    }

    public void rawset(String str, int i) {
        rawset(valueOf(str), valueOf(i));
    }

    public void rawset(String str, String str2) {
        rawset(valueOf(str), valueOf(str2));
    }

    public void rawset(String str, LuaValue luaValue) {
        rawset(valueOf(str), luaValue);
    }

    public void rawset(LuaValue luaValue, LuaValue luaValue2) {
        unimplemented("rawset");
    }

    public void rawsetlist(int i, Varargs varargs) {
        int iNarg = varargs.narg();
        int i2 = 0;
        while (i2 < iNarg) {
            int i3 = i + i2;
            i2++;
            rawset(i3, varargs.arg(i2));
        }
    }

    public void set(int i, String str) {
        set(i, valueOf(str));
    }

    public void set(int i, LuaValue luaValue) {
        set(LuaInteger.valueOf(i), luaValue);
    }

    public void set(String str, double d) {
        set(valueOf(str), valueOf(d));
    }

    public void set(String str, int i) {
        set(valueOf(str), valueOf(i));
    }

    public void set(String str, String str2) {
        set(valueOf(str), valueOf(str2));
    }

    public void set(String str, LuaValue luaValue) {
        set(valueOf(str), luaValue);
    }

    public void set(LuaValue luaValue, LuaValue luaValue2) {
        settable(this, luaValue, luaValue2);
    }

    public LuaValue setmetatable(LuaValue luaValue) {
        return argerror("table");
    }

    public int strcmp(LuaString luaString) {
        error(new StringBuffer("attempt to compare ").append(typename()).toString());
        return 0;
    }

    public int strcmp(LuaValue luaValue) {
        error(new StringBuffer("attempt to compare ").append(typename()).toString());
        return 0;
    }

    public LuaValue strongvalue() {
        return this;
    }

    public LuaString strvalue() {
        typerror("strValue");
        return null;
    }

    public LuaValue sub(double d) {
        return aritherror("sub");
    }

    public LuaValue sub(int i) {
        return aritherror("sub");
    }

    public LuaValue sub(LuaValue luaValue) {
        return arithmt(SUB, luaValue);
    }

    public LuaValue subFrom(double d) {
        return arithmtwith(SUB, d);
    }

    public LuaValue subFrom(int i) {
        return subFrom(i);
    }

    @Override // org.luaj.vm2.Varargs
    public Varargs subargs(int i) {
        if (i == 1) {
            return this;
        }
        return i > 1 ? NONE : argerror(1, "start must be > 0");
    }

    public boolean testfor_b(LuaValue luaValue, LuaValue luaValue2) {
        return luaValue2.gt_b(0) ? lteq_b(luaValue) : gteq_b(luaValue);
    }

    @Override // org.luaj.vm2.Varargs
    public String toString() {
        return tojstring();
    }

    public boolean toboolean() {
        return true;
    }

    public byte tobyte() {
        return (byte) 0;
    }

    public char tochar() {
        return (char) 0;
    }

    public double todouble() {
        return 0.0d;
    }

    public float tofloat() {
        return 0.0f;
    }

    public int toint() {
        return 0;
    }

    @Override // org.luaj.vm2.Varargs
    public String tojstring() {
        return new StringBuffer().append(typename()).append(": ").append(Integer.toHexString(hashCode())).toString();
    }

    public long tolong() {
        return 0L;
    }

    public LuaValue tonumber() {
        return NIL;
    }

    public short toshort() {
        return (short) 0;
    }

    public LuaValue tostring() {
        return NIL;
    }

    public Object touserdata() {
        return null;
    }

    public Object touserdata(Class cls) {
        return null;
    }

    public abstract int type();

    public abstract String typename();

    protected LuaValue typerror(String str) {
        throw new LuaError(new StringBuffer().append(str).append(" expected, got ").append(typename()).toString());
    }

    protected LuaValue unimplemented(String str) {
        throw new LuaError(new StringBuffer(FormatHelper.QUOTE).append(str).append("' not implemented for ").append(typename()).toString());
    }
}
