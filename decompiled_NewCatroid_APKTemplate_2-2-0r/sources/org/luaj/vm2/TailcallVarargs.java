package org.luaj.vm2;

/* JADX INFO: loaded from: classes6.dex */
public class TailcallVarargs extends Varargs {
    private Varargs args;
    private LuaValue func;
    private Varargs result;

    public TailcallVarargs(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        this.func = luaValue.get(luaValue2);
        this.args = LuaValue.varargsOf(luaValue, varargs);
    }

    public TailcallVarargs(LuaValue luaValue, Varargs varargs) {
        this.func = luaValue;
        this.args = varargs;
    }

    @Override // org.luaj.vm2.Varargs
    public LuaValue arg(int i) {
        if (this.result == null) {
            eval();
        }
        return this.result.arg(i);
    }

    @Override // org.luaj.vm2.Varargs
    public LuaValue arg1() {
        if (this.result == null) {
            eval();
        }
        return this.result.arg1();
    }

    @Override // org.luaj.vm2.Varargs
    public Varargs eval() {
        Varargs varargs;
        while (true) {
            Varargs varargs2 = this.result;
            if (varargs2 != null) {
                return varargs2;
            }
            Varargs varargsOnInvoke = this.func.onInvoke(this.args);
            if (varargsOnInvoke.isTailcall()) {
                TailcallVarargs tailcallVarargs = (TailcallVarargs) varargsOnInvoke;
                this.func = tailcallVarargs.func;
                varargs = tailcallVarargs.args;
            } else {
                this.result = varargsOnInvoke;
                varargs = null;
                this.func = null;
            }
            this.args = varargs;
        }
    }

    @Override // org.luaj.vm2.Varargs
    public boolean isTailcall() {
        return true;
    }

    @Override // org.luaj.vm2.Varargs
    public int narg() {
        if (this.result == null) {
            eval();
        }
        return this.result.narg();
    }

    @Override // org.luaj.vm2.Varargs
    public Varargs subargs(int i) {
        if (this.result == null) {
            eval();
        }
        return this.result.subargs(i);
    }
}
