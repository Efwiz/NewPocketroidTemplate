package org.luaj.vm2.lib;

import java.io.IOException;
import java.io.InputStream;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class BaseLib extends TwoArgFunction implements ResourceFinder {
    Globals globals;

    private static class StringInputStream extends InputStream {
        byte[] bytes;
        final LuaValue func;
        int offset;
        int remaining = 0;

        StringInputStream(LuaValue luaValue) {
            this.func = luaValue;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this.remaining <= 0) {
                LuaValue luaValueCall = this.func.call();
                if (luaValueCall.isnil()) {
                    return -1;
                }
                LuaString luaStringStrvalue = luaValueCall.strvalue();
                this.bytes = luaStringStrvalue.m_bytes;
                this.offset = luaStringStrvalue.m_offset;
                int i = luaStringStrvalue.m_length;
                this.remaining = i;
                if (i <= 0) {
                    return -1;
                }
            }
            this.remaining--;
            byte[] bArr = this.bytes;
            int i2 = this.offset;
            this.offset = i2 + 1;
            return bArr[i2];
        }
    }

    static final class _assert extends VarArgFunction {
        _assert() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            if (!varargs.arg1().toboolean()) {
                error(varargs.narg() > 1 ? varargs.optjstring(2, "assertion failed!") : "assertion failed!");
            }
            return varargs;
        }
    }

    static final class collectgarbage extends VarArgFunction {
        collectgarbage() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            String strOptjstring = varargs.optjstring(1, "collect");
            if ("collect".equals(strOptjstring)) {
                System.gc();
                return ZERO;
            }
            if ("count".equals(strOptjstring)) {
                Runtime runtime = Runtime.getRuntime();
                long jFreeMemory = runtime.totalMemory() - runtime.freeMemory();
                return varargsOf(valueOf(jFreeMemory / 1024.0d), valueOf(jFreeMemory % 1024));
            }
            if ("step".equals(strOptjstring)) {
                System.gc();
                return LuaValue.TRUE;
            }
            argerror("gc op");
            return NIL;
        }
    }

    final class dofile extends VarArgFunction {
        dofile() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            Varargs varargsLoadFile;
            varargs.argcheck(varargs.isstring(1) || varargs.isnil(1), 1, "filename must be string or nil");
            if ((varargs.isstring(1) ? varargs.tojstring(1) : null) == null) {
                BaseLib baseLib = BaseLib.this;
                varargsLoadFile = baseLib.loadStream(baseLib.globals.STDIN, "=stdin", "bt", BaseLib.this.globals);
            } else {
                varargsLoadFile = BaseLib.this.loadFile(varargs.checkjstring(1), "bt", BaseLib.this.globals);
            }
            return varargsLoadFile.isnil(1) ? error(varargsLoadFile.tojstring(2)) : varargsLoadFile.arg1().invoke();
        }
    }

    static final class error extends TwoArgFunction {
        error() {
        }

        @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            if (luaValue.isnil()) {
                throw new LuaError(null, luaValue2.optint(1));
            }
            if (luaValue.isstring()) {
                throw new LuaError(luaValue.tojstring(), luaValue2.optint(1));
            }
            throw new LuaError(luaValue);
        }
    }

    static final class getmetatable extends LibFunction {
        getmetatable() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return argerror(1, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            LuaValue luaValue2 = luaValue.getmetatable();
            return luaValue2 != null ? luaValue2.rawget(METATABLE).optvalue(luaValue2) : NIL;
        }
    }

    static final class inext extends VarArgFunction {
        inext() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return varargs.checktable(1).inext(varargs.arg(2));
        }
    }

    static final class ipairs extends VarArgFunction {
        inext inext = new inext();

        ipairs() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return varargsOf(this.inext, varargs.checktable(1), ZERO);
        }
    }

    final class load extends VarArgFunction {
        load() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValueArg1 = varargs.arg1();
            varargs.argcheck(luaValueArg1.isstring() || luaValueArg1.isfunction(), 1, "ld must be string or function");
            return BaseLib.this.loadStream(luaValueArg1.isstring() ? luaValueArg1.strvalue().toInputStream() : new StringInputStream(luaValueArg1.checkfunction()), varargs.optjstring(2, luaValueArg1.isstring() ? luaValueArg1.tojstring() : "=(load)"), varargs.optjstring(3, "bt"), varargs.optvalue(4, BaseLib.this.globals));
        }
    }

    final class loadfile extends VarArgFunction {
        loadfile() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            varargs.argcheck(varargs.isstring(1) || varargs.isnil(1), 1, "filename must be string or nil");
            String str = varargs.isstring(1) ? varargs.tojstring(1) : null;
            String strOptjstring = varargs.optjstring(2, "bt");
            LuaValue luaValueOptvalue = varargs.optvalue(3, BaseLib.this.globals);
            if (str != null) {
                return BaseLib.this.loadFile(str, strOptjstring, luaValueOptvalue);
            }
            BaseLib baseLib = BaseLib.this;
            return baseLib.loadStream(baseLib.globals.STDIN, "=stdin", strOptjstring, luaValueOptvalue);
        }
    }

    static final class next extends VarArgFunction {
        next() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return varargs.checktable(1).next(varargs.arg(2));
        }
    }

    static final class pairs extends VarArgFunction {
        final next next;

        pairs(next nextVar) {
            this.next = nextVar;
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            return varargsOf(this.next, varargs.checktable(1), NIL);
        }
    }

    final class pcall extends VarArgFunction {
        pcall() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValueCheckvalue = varargs.checkvalue(1);
            if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                BaseLib.this.globals.debuglib.onCall(this);
            }
            try {
                return varargsOf(TRUE, luaValueCheckvalue.invoke(varargs.subargs(2)));
            } catch (LuaError e) {
                LuaValue messageObject = e.getMessageObject();
                LuaBoolean luaBoolean = FALSE;
                if (messageObject == null) {
                    messageObject = NIL;
                }
                return varargsOf(luaBoolean, messageObject);
            } catch (Exception e2) {
                String message = e2.getMessage();
                LuaBoolean luaBoolean2 = FALSE;
                if (message == null) {
                    message = e2.toString();
                }
                return varargsOf(luaBoolean2, valueOf(message));
            } finally {
                if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                    BaseLib.this.globals.debuglib.onReturn();
                }
            }
        }
    }

    final class print extends VarArgFunction {
        final BaseLib baselib;

        print(BaseLib baseLib) {
            this.baselib = baseLib;
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValue = BaseLib.this.globals.get("tostring");
            int iNarg = varargs.narg();
            for (int i = 1; i <= iNarg; i++) {
                if (i > 1) {
                    BaseLib.this.globals.STDOUT.print('\t');
                }
                BaseLib.this.globals.STDOUT.print(luaValue.call(varargs.arg(i)).strvalue().tojstring());
            }
            BaseLib.this.globals.STDOUT.println();
            return NONE;
        }
    }

    static final class rawequal extends LibFunction {
        rawequal() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return argerror(1, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return argerror(2, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return valueOf(luaValue.raweq(luaValue2));
        }
    }

    static final class rawget extends LibFunction {
        rawget() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call() {
            return argerror(1, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return argerror(2, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return luaValue.checktable().rawget(luaValue2);
        }
    }

    static final class rawlen extends LibFunction {
        rawlen() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return valueOf(luaValue.rawlen());
        }
    }

    static final class rawset extends LibFunction {
        rawset() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return argerror(2, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return argerror(3, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
            LuaTable luaTableChecktable = luaValue.checktable();
            luaTableChecktable.rawset(luaValue2.checknotnil(), luaValue3);
            return luaTableChecktable;
        }
    }

    static final class select extends VarArgFunction {
        select() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            int iNarg = varargs.narg() - 1;
            if (varargs.arg1().equals(valueOf("#"))) {
                return valueOf(iNarg);
            }
            int iCheckint = varargs.checkint(1);
            if (iCheckint == 0 || iCheckint < (-iNarg)) {
                argerror(1, "index out of range");
            }
            return varargs.subargs(iCheckint < 0 ? iNarg + iCheckint + 2 : iCheckint + 1);
        }
    }

    static final class setmetatable extends LibFunction {
        setmetatable() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return argerror(2, "value");
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            LuaValue luaValue3 = luaValue.checktable().getmetatable();
            if (luaValue3 != null && !luaValue3.rawget(METATABLE).isnil()) {
                error("cannot change a protected metatable");
            }
            return luaValue.setmetatable(luaValue2.isnil() ? null : luaValue2.checktable());
        }
    }

    static final class tonumber extends LibFunction {
        tonumber() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return luaValue.tonumber();
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            if (luaValue2.isnil()) {
                return luaValue.tonumber();
            }
            int iCheckint = luaValue2.checkint();
            if (iCheckint < 2 || iCheckint > 36) {
                argerror(2, "base out of range");
            }
            return luaValue.checkstring().tonumber(iCheckint);
        }
    }

    static final class tostring extends LibFunction {
        tostring() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            LuaValue luaValueMetatag = luaValue.metatag(TOSTRING);
            if (!luaValueMetatag.isnil()) {
                return luaValueMetatag.call(luaValue);
            }
            LuaValue luaValue2 = luaValue.tostring();
            return !luaValue2.isnil() ? luaValue2 : valueOf(luaValue.tojstring());
        }
    }

    static final class type extends LibFunction {
        type() {
        }

        @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            return valueOf(luaValue.typename());
        }
    }

    final class xpcall extends VarArgFunction {
        xpcall() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThread = BaseLib.this.globals.running;
            LuaValue luaValue = luaThread.errorfunc;
            luaThread.errorfunc = varargs.checkvalue(2);
            try {
                if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                    BaseLib.this.globals.debuglib.onCall(this);
                }
                try {
                    try {
                        Varargs varargsVarargsOf = varargsOf(TRUE, varargs.arg1().invoke(varargs.subargs(3)));
                        if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                            BaseLib.this.globals.debuglib.onReturn();
                        }
                        luaThread.errorfunc = luaValue;
                        return varargsVarargsOf;
                    } catch (Throwable th) {
                        if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                            BaseLib.this.globals.debuglib.onReturn();
                        }
                        throw th;
                    }
                } catch (LuaError e) {
                    LuaValue messageObject = e.getMessageObject();
                    LuaBoolean luaBoolean = FALSE;
                    if (messageObject == null) {
                        messageObject = NIL;
                    }
                    Varargs varargsVarargsOf2 = varargsOf(luaBoolean, messageObject);
                    if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                        BaseLib.this.globals.debuglib.onReturn();
                    }
                    luaThread.errorfunc = luaValue;
                    return varargsVarargsOf2;
                } catch (Exception e2) {
                    String message = e2.getMessage();
                    LuaBoolean luaBoolean2 = FALSE;
                    if (message == null) {
                        message = e2.toString();
                    }
                    Varargs varargsVarargsOf3 = varargsOf(luaBoolean2, valueOf(message));
                    if (BaseLib.this.globals != null && BaseLib.this.globals.debuglib != null) {
                        BaseLib.this.globals.debuglib.onReturn();
                    }
                    luaThread.errorfunc = luaValue;
                    return varargsVarargsOf3;
                }
            } catch (Throwable th2) {
                luaThread.errorfunc = luaValue;
                throw th2;
            }
        }
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        Globals globalsCheckglobals = luaValue2.checkglobals();
        this.globals = globalsCheckglobals;
        globalsCheckglobals.finder = this;
        this.globals.baselib = this;
        luaValue2.set("_G", luaValue2);
        luaValue2.set("_VERSION", Lua._VERSION);
        luaValue2.set(PsiKeyword.ASSERT, new _assert());
        luaValue2.set("collectgarbage", new collectgarbage());
        luaValue2.set("dofile", new dofile());
        luaValue2.set("error", new error());
        luaValue2.set("getmetatable", new getmetatable());
        luaValue2.set("load", new load());
        luaValue2.set("loadfile", new loadfile());
        luaValue2.set("pcall", new pcall());
        luaValue2.set("print", new print(this));
        luaValue2.set("rawequal", new rawequal());
        luaValue2.set("rawget", new rawget());
        luaValue2.set("rawlen", new rawlen());
        luaValue2.set("rawset", new rawset());
        luaValue2.set("select", new select());
        luaValue2.set("setmetatable", new setmetatable());
        luaValue2.set("tonumber", new tonumber());
        luaValue2.set("tostring", new tostring());
        luaValue2.set(ModuleXmlParser.TYPE, new type());
        luaValue2.set("xpcall", new xpcall());
        next nextVar = new next();
        luaValue2.set(ES6Iterator.NEXT_METHOD, nextVar);
        luaValue2.set("pairs", new pairs(nextVar));
        luaValue2.set("ipairs", new ipairs());
        return luaValue2;
    }

    @Override // org.luaj.vm2.lib.ResourceFinder
    public InputStream findResource(String str) {
        Class<?> cls = getClass();
        if (!str.startsWith("/")) {
            str = new StringBuffer("/").append(str).toString();
        }
        return cls.getResourceAsStream(str);
    }

    public Varargs loadFile(String str, String str2, LuaValue luaValue) {
        InputStream inputStreamFindResource = this.globals.finder.findResource(str);
        if (inputStreamFindResource == null) {
            return varargsOf(NIL, valueOf(new StringBuffer("cannot open ").append(str).append(": No such file or directory").toString()));
        }
        try {
            return loadStream(inputStreamFindResource, new StringBuffer(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT).append(str).toString(), str2, luaValue);
        } finally {
            try {
                inputStreamFindResource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Varargs loadStream(InputStream inputStream, String str, String str2, LuaValue luaValue) {
        try {
            return inputStream == null ? varargsOf(NIL, valueOf(new StringBuffer("not found: ").append(str).toString())) : this.globals.load(inputStream, str, str2, luaValue);
        } catch (Exception e) {
            return varargsOf(NIL, valueOf(e.getMessage()));
        }
    }
}
