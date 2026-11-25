package org.luaj.vm2;

import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes6.dex */
public class LuaClosure extends LuaFunction {
    private static final UpValue[] NOUPVALUES = new UpValue[0];
    final Globals globals;
    public final Prototype p;
    public UpValue[] upValues;

    public LuaClosure(Prototype prototype, LuaValue luaValue) {
        this.p = prototype;
        if (prototype.upvalues == null || prototype.upvalues.length == 0) {
            this.upValues = NOUPVALUES;
        } else {
            UpValue[] upValueArr = new UpValue[prototype.upvalues.length];
            this.upValues = upValueArr;
            upValueArr[0] = new UpValue(new LuaValue[]{luaValue}, 0);
        }
        this.globals = luaValue instanceof Globals ? (Globals) luaValue : null;
    }

    private UpValue findupval(LuaValue[] luaValueArr, short s, UpValue[] upValueArr) {
        int length = upValueArr.length;
        for (int i = 0; i < length; i++) {
            UpValue upValue = upValueArr[i];
            if (upValue != null && upValue.index == s) {
                return upValueArr[i];
            }
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (upValueArr[i2] == null) {
                UpValue upValue2 = new UpValue(luaValueArr, s);
                upValueArr[i2] = upValue2;
                return upValue2;
            }
        }
        error("No space for upvalue");
        return null;
    }

    private void processErrorHooks(LuaError luaError, Prototype prototype, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        LuaString luaString = prototype.source;
        String strValueOf = TypeDescription.Generic.OfWildcardType.SYMBOL;
        StringBuffer stringBufferAppend = stringBuffer.append(luaString != null ? prototype.source.tojstring() : TypeDescription.Generic.OfWildcardType.SYMBOL).append(":");
        if (prototype.lineinfo != null && i >= 0 && i < prototype.lineinfo.length) {
            strValueOf = String.valueOf(prototype.lineinfo[i]);
        }
        luaError.fileline = stringBufferAppend.append(strValueOf).toString();
        luaError.traceback = errorHook(luaError.getMessage(), luaError.level);
    }

    @Override // org.luaj.vm2.LuaValue
    public final LuaValue call() {
        LuaValue[] luaValueArr = new LuaValue[this.p.maxstacksize];
        for (int i = 0; i < this.p.numparams; i++) {
            luaValueArr[i] = NIL;
        }
        return execute(luaValueArr, NONE).arg1();
    }

    @Override // org.luaj.vm2.LuaValue
    public final LuaValue call(LuaValue luaValue) {
        LuaValue[] luaValueArr = new LuaValue[this.p.maxstacksize];
        System.arraycopy(NILS, 0, luaValueArr, 0, this.p.maxstacksize);
        for (int i = 1; i < this.p.numparams; i++) {
            luaValueArr[i] = NIL;
        }
        if (this.p.numparams != 0) {
            luaValueArr[0] = luaValue;
            luaValue = NONE;
        }
        return execute(luaValueArr, luaValue).arg1();
    }

    @Override // org.luaj.vm2.LuaValue
    public final LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        Varargs varargsVarargsOf;
        Varargs varargsExecute;
        LuaValue[] luaValueArr = new LuaValue[this.p.maxstacksize];
        for (int i = 2; i < this.p.numparams; i++) {
            luaValueArr[i] = NIL;
        }
        int i2 = this.p.numparams;
        if (i2 == 0) {
            if (this.p.is_vararg != 0) {
                varargsVarargsOf = varargsOf(luaValue, luaValue2);
            }
            varargsExecute = execute(luaValueArr, varargsVarargsOf);
            return varargsExecute.arg1();
        }
        if (i2 != 1) {
            luaValueArr[0] = luaValue;
            luaValueArr[1] = luaValue2;
        } else {
            luaValueArr[0] = luaValue;
            varargsExecute = execute(luaValueArr, luaValue2);
        }
        return varargsExecute.arg1();
        varargsVarargsOf = NONE;
        varargsExecute = execute(luaValueArr, varargsVarargsOf);
        return varargsExecute.arg1();
    }

    @Override // org.luaj.vm2.LuaValue
    public final LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        Varargs varargsVarargsOf;
        Varargs varargsExecute;
        LuaValue[] luaValueArr = new LuaValue[this.p.maxstacksize];
        for (int i = 3; i < this.p.numparams; i++) {
            luaValueArr[i] = NIL;
        }
        int i2 = this.p.numparams;
        if (i2 != 0) {
            if (i2 == 1) {
                luaValueArr[0] = luaValue;
                if (this.p.is_vararg != 0) {
                    varargsVarargsOf = varargsOf(luaValue2, luaValue3);
                }
                varargsExecute = execute(luaValueArr, varargsVarargsOf);
            } else if (i2 != 2) {
                luaValueArr[0] = luaValue;
                luaValueArr[1] = luaValue2;
                luaValueArr[2] = luaValue3;
            } else {
                luaValueArr[0] = luaValue;
                luaValueArr[1] = luaValue2;
                varargsExecute = execute(luaValueArr, luaValue3);
            }
            varargsExecute = execute(luaValueArr, varargsVarargsOf);
        } else {
            varargsVarargsOf = this.p.is_vararg != 0 ? varargsOf(luaValue, luaValue2, luaValue3) : NONE;
            varargsExecute = execute(luaValueArr, varargsVarargsOf);
        }
        return varargsExecute.arg1();
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaClosure checkclosure() {
        return this;
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    String errorHook(String str, int i) {
        Globals globals = this.globals;
        if (globals == null) {
            return str;
        }
        LuaThread luaThread = globals.running;
        if (luaThread.errorfunc == null) {
            return this.globals.debuglib != null ? new StringBuffer().append(str).append("\n").append(this.globals.debuglib.traceback(i)).toString() : str;
        }
        LuaValue luaValue = luaThread.errorfunc;
        luaThread.errorfunc = null;
        try {
            String str2 = luaValue.call(LuaValue.valueOf(str)).tojstring();
            luaThread.errorfunc = luaValue;
            return str2;
        } catch (Throwable unused) {
            luaThread.errorfunc = luaValue;
            return "error in error handling";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:250:0x045b, code lost:
    
        if (r3 != r8) goto L251;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:217:0x0365. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected org.luaj.vm2.Varargs execute(org.luaj.vm2.LuaValue[] r20, org.luaj.vm2.Varargs r21) {
        /*
            Method dump skipped, instruction units count: 2148
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.luaj.vm2.LuaClosure.execute(org.luaj.vm2.LuaValue[], org.luaj.vm2.Varargs):org.luaj.vm2.Varargs");
    }

    protected LuaValue getUpvalue(int i) {
        return this.upValues[i].getValue();
    }

    @Override // org.luaj.vm2.LuaFunction, org.luaj.vm2.LuaValue
    public LuaValue getmetatable() {
        return s_metatable;
    }

    @Override // org.luaj.vm2.LuaValue
    public final Varargs invoke(Varargs varargs) {
        return onInvoke(varargs).eval();
    }

    @Override // org.luaj.vm2.LuaValue
    public boolean isclosure() {
        return true;
    }

    @Override // org.luaj.vm2.LuaFunction
    public String name() {
        return new StringBuffer("<").append(this.p.shortsource()).append(":").append(this.p.linedefined).append(">").toString();
    }

    @Override // org.luaj.vm2.LuaValue
    public final Varargs onInvoke(Varargs varargs) {
        LuaValue[] luaValueArr = new LuaValue[this.p.maxstacksize];
        int i = 0;
        while (i < this.p.numparams) {
            int i2 = i + 1;
            luaValueArr[i] = varargs.arg(i2);
            i = i2;
        }
        return execute(luaValueArr, this.p.is_vararg != 0 ? varargs.subargs(this.p.numparams + 1) : NONE);
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaClosure optclosure(LuaClosure luaClosure) {
        return this;
    }

    protected void setUpvalue(int i, LuaValue luaValue) {
        this.upValues[i].setValue(luaValue);
    }

    @Override // org.luaj.vm2.LuaFunction, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
    public String tojstring() {
        return new StringBuffer("function: ").append(this.p.toString()).toString();
    }
}
