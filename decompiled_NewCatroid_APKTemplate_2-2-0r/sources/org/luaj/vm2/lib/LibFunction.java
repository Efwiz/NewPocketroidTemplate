package org.luaj.vm2.lib;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/* JADX INFO: loaded from: classes6.dex */
public abstract class LibFunction extends LuaFunction {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f1059name;
    protected int opcode;

    protected LibFunction() {
    }

    protected static LuaValue[] newupe() {
        return new LuaValue[1];
    }

    protected static LuaValue[] newupl(LuaValue luaValue) {
        return new LuaValue[]{luaValue};
    }

    protected static LuaValue[] newupn() {
        return new LuaValue[]{NIL};
    }

    protected void bind(LuaValue luaValue, Class cls, String[] strArr) {
        bind(luaValue, cls, strArr, 0);
    }

    protected void bind(LuaValue luaValue, Class cls, String[] strArr, int i) {
        try {
            int length = strArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                LibFunction libFunction = (LibFunction) cls.newInstance();
                libFunction.opcode = i + i2;
                String str = strArr[i2];
                libFunction.f1059name = str;
                luaValue.set(str, libFunction);
            }
        } catch (Exception e) {
            throw new LuaError(new StringBuffer("bind failed: ").append(e).toString());
        }
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue call() {
        return argerror(1, "value");
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue) {
        return call();
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return call(luaValue);
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return call(luaValue, luaValue2);
    }

    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
        return call(luaValue, luaValue2, luaValue3);
    }

    @Override // org.luaj.vm2.LuaValue
    public Varargs invoke(Varargs varargs) {
        int iNarg = varargs.narg();
        if (iNarg == 0) {
            return call();
        }
        if (iNarg == 1) {
            return call(varargs.arg1());
        }
        if (iNarg != 2) {
            return iNarg != 3 ? call(varargs.arg1(), varargs.arg(2), varargs.arg(3), varargs.arg(4)) : call(varargs.arg1(), varargs.arg(2), varargs.arg(3));
        }
        return call(varargs.arg1(), varargs.arg(2));
    }

    @Override // org.luaj.vm2.LuaFunction, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
    public String tojstring() {
        String str = this.f1059name;
        return str != null ? str : super.tojstring();
    }
}
