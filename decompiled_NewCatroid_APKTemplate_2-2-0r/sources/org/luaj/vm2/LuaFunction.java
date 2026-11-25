package org.luaj.vm2;

/* JADX INFO: loaded from: classes6.dex */
public abstract class LuaFunction extends LuaValue {
    public static LuaValue s_metatable;

    @Override // org.luaj.vm2.LuaValue
    public LuaFunction checkfunction() {
        return this;
    }

    public String classnamestub() {
        String name2 = getClass().getName();
        return name2.substring(Math.max(name2.lastIndexOf(46), name2.lastIndexOf(36)) + 1);
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaValue getmetatable() {
        return s_metatable;
    }

    @Override // org.luaj.vm2.LuaValue
    public boolean isfunction() {
        return true;
    }

    public String name() {
        return classnamestub();
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaFunction optfunction(LuaFunction luaFunction) {
        return this;
    }

    @Override // org.luaj.vm2.LuaValue
    public LuaString strvalue() {
        return valueOf(tojstring());
    }

    @Override // org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
    public String tojstring() {
        return new StringBuffer("function: ").append(classnamestub()).toString();
    }

    @Override // org.luaj.vm2.LuaValue
    public int type() {
        return 6;
    }

    @Override // org.luaj.vm2.LuaValue
    public String typename() {
        return "function";
    }
}
