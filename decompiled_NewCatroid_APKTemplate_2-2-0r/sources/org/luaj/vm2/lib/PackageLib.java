package org.luaj.vm2.lib;

import java.io.IOException;
import java.io.InputStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.lang3.SystemProperties;
import org.koin.core.instance.InstanceFactory;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

/* JADX INFO: loaded from: classes6.dex */
public class PackageLib extends TwoArgFunction {
    public static String DEFAULT_LUA_PATH;
    private static final String FILE_SEP;
    private static final LuaString _LOADED;
    private static final LuaString _LOADLIB;
    private static final LuaString _PATH;
    private static final LuaString _PRELOAD;
    private static final LuaString _SEARCHERS;
    private static final LuaString _SEARCHPATH;
    private static final LuaString _SENTINEL;
    Globals globals;
    public java_searcher java_searcher;
    public lua_searcher lua_searcher;
    LuaTable package_;
    public preload_searcher preload_searcher;

    public class java_searcher extends VarArgFunction {
        public java_searcher() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            StringBuffer stringBufferAppend;
            String classname = PackageLib.toClassname(varargs.checkjstring(1));
            try {
                LuaValue luaValue = (LuaValue) Class.forName(classname).newInstance();
                if (luaValue.isfunction()) {
                    ((LuaFunction) luaValue).initupvalue1(PackageLib.this.globals);
                }
                return varargsOf(luaValue, PackageLib.this.globals);
            } catch (ClassNotFoundException unused) {
                stringBufferAppend = new StringBuffer("\n\tno class '").append(classname).append(FormatHelper.QUOTE);
                return valueOf(stringBufferAppend.toString());
            } catch (Exception e) {
                stringBufferAppend = new StringBuffer("\n\tjava load failed on '").append(classname).append("', ").append(e);
                return valueOf(stringBufferAppend.toString());
            }
        }
    }

    public static class loadlib extends VarArgFunction {
        public Varargs loadlib(Varargs varargs) {
            varargs.checkstring(1);
            return varargsOf(NIL, valueOf("dynamic libraries not enabled"), valueOf("absent"));
        }
    }

    public class lua_searcher extends VarArgFunction {
        public lua_searcher() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            LuaValue luaValue = PackageLib.this.package_.get(PackageLib._PATH);
            if (!luaValue.isstring()) {
                return valueOf("package.path is not a string");
            }
            Varargs varargsInvoke = PackageLib.this.package_.get(PackageLib._SEARCHPATH).invoke(varargsOf(luaStringCheckstring, luaValue));
            if (!varargsInvoke.isstring(1)) {
                return varargsInvoke.arg(2).tostring();
            }
            LuaString luaStringStrvalue = varargsInvoke.arg1().strvalue();
            LuaValue luaValueLoadfile = PackageLib.this.globals.loadfile(luaStringStrvalue.tojstring());
            return luaValueLoadfile.arg1().isfunction() ? LuaValue.varargsOf(luaValueLoadfile.arg1(), luaStringStrvalue) : varargsOf(NIL, valueOf(new StringBuffer(FormatHelper.QUOTE).append(luaStringStrvalue).append("': ").append(luaValueLoadfile.arg(2).tojstring()).toString()));
        }
    }

    public class preload_searcher extends VarArgFunction {
        public preload_searcher() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            LuaString luaStringCheckstring = varargs.checkstring(1);
            LuaValue luaValue = PackageLib.this.package_.get(PackageLib._PRELOAD).get(luaStringCheckstring);
            return luaValue.isnil() ? valueOf(new StringBuffer("\n\tno field package.preload['").append(luaStringCheckstring).append("']").toString()) : luaValue;
        }
    }

    public class require extends OneArgFunction {
        public require() {
        }

        @Override // org.luaj.vm2.lib.OneArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public LuaValue call(LuaValue luaValue) {
            Varargs varargsInvoke;
            LuaString luaStringCheckstring = luaValue.checkstring();
            LuaValue luaValue2 = PackageLib.this.package_.get(PackageLib._LOADED);
            LuaValue luaValue3 = luaValue2.get(luaStringCheckstring);
            if (luaValue3.toboolean()) {
                if (luaValue3 == PackageLib._SENTINEL) {
                    error(new StringBuffer("loop or previous error loading module '").append(luaStringCheckstring).append(FormatHelper.QUOTE).toString());
                }
                return luaValue3;
            }
            LuaTable luaTableChecktable = PackageLib.this.package_.get(PackageLib._SEARCHERS).checktable();
            StringBuffer stringBuffer = new StringBuffer();
            int i = 1;
            while (true) {
                LuaValue luaValue4 = luaTableChecktable.get(i);
                if (luaValue4.isnil()) {
                    error(new StringBuffer("module '").append(luaStringCheckstring).append("' not found: ").append(luaStringCheckstring).append((Object) stringBuffer).toString());
                }
                varargsInvoke = luaValue4.invoke(luaStringCheckstring);
                if (varargsInvoke.isfunction(1)) {
                    break;
                }
                if (varargsInvoke.isstring(1)) {
                    stringBuffer.append(varargsInvoke.tojstring(1));
                }
                i++;
            }
            luaValue2.set(luaStringCheckstring, PackageLib._SENTINEL);
            LuaValue luaValueCall = varargsInvoke.arg1().call(luaStringCheckstring, varargsInvoke.arg(2));
            if (luaValueCall.isnil()) {
                luaValueCall = luaValue2.get(luaStringCheckstring);
                if (luaValueCall == PackageLib._SENTINEL) {
                    luaValueCall = LuaValue.TRUE;
                    luaValue2.set(luaStringCheckstring, luaValueCall);
                }
            } else {
                luaValue2.set(luaStringCheckstring, luaValueCall);
            }
            return luaValueCall;
        }
    }

    public class searchpath extends VarArgFunction {
        public searchpath() {
        }

        @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
        public Varargs invoke(Varargs varargs) {
            String strCheckjstring = varargs.checkjstring(1);
            String strCheckjstring2 = varargs.checkjstring(2);
            String strOptjstring = varargs.optjstring(3, ".");
            String strOptjstring2 = varargs.optjstring(4, PackageLib.FILE_SEP);
            int length = strCheckjstring2.length();
            String strReplace = strCheckjstring.replace(strOptjstring.charAt(0), strOptjstring2.charAt(0));
            int i = -1;
            StringBuffer stringBuffer = null;
            while (i < length) {
                int i2 = i + 1;
                int iIndexOf = strCheckjstring2.indexOf(59, i2);
                if (iIndexOf < 0) {
                    iIndexOf = strCheckjstring2.length();
                }
                String strSubstring = strCheckjstring2.substring(i2, iIndexOf);
                int iIndexOf2 = strSubstring.indexOf(63);
                if (iIndexOf2 >= 0) {
                    strSubstring = new StringBuffer().append(strSubstring.substring(0, iIndexOf2)).append(strReplace).append(strSubstring.substring(iIndexOf2 + 1)).toString();
                }
                InputStream inputStreamFindResource = PackageLib.this.globals.finder.findResource(strSubstring);
                if (inputStreamFindResource != null) {
                    try {
                        inputStreamFindResource.close();
                    } catch (IOException unused) {
                    }
                    return valueOf(strSubstring);
                }
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer.append(new StringBuffer(InstanceFactory.ERROR_SEPARATOR).append(strSubstring).toString());
                i = iIndexOf;
            }
            return varargsOf(NIL, valueOf(stringBuffer.toString()));
        }
    }

    static {
        try {
            DEFAULT_LUA_PATH = System.getProperty("luaj.package.path");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (DEFAULT_LUA_PATH == null) {
            DEFAULT_LUA_PATH = "?.lua";
        }
        _LOADED = valueOf("loaded");
        _LOADLIB = valueOf("loadlib");
        _PRELOAD = valueOf("preload");
        _PATH = valueOf("path");
        _SEARCHPATH = valueOf("searchpath");
        _SEARCHERS = valueOf("searchers");
        _SENTINEL = valueOf("\u0001");
        FILE_SEP = System.getProperty(SystemProperties.FILE_SEPARATOR);
    }

    private static final boolean isClassnamePart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9') || c == '$' || c == '.' || c == '_');
    }

    public static final String toClassname(String str) {
        int length = str.length();
        int i = str.endsWith(".lua") ? length - 4 : length;
        for (int i2 = 0; i2 < i; i2++) {
            char cCharAt = str.charAt(i2);
            if (!isClassnamePart(cCharAt) || cCharAt == '/' || cCharAt == '\\') {
                StringBuffer stringBuffer = new StringBuffer(i);
                for (int i3 = 0; i3 < i; i3++) {
                    char cCharAt2 = str.charAt(i3);
                    if (!isClassnamePart(cCharAt2)) {
                        cCharAt2 = (cCharAt2 == '/' || cCharAt2 == '\\') ? '.' : '_';
                    }
                    stringBuffer.append(cCharAt2);
                }
                return stringBuffer.toString();
            }
        }
        return length == i ? str : str.substring(0, i);
    }

    @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        Globals globalsCheckglobals = luaValue2.checkglobals();
        this.globals = globalsCheckglobals;
        globalsCheckglobals.set("require", new require());
        LuaTable luaTable = new LuaTable();
        this.package_ = luaTable;
        LuaString luaString = _LOADED;
        luaTable.set(luaString, new LuaTable());
        this.package_.set(_PRELOAD, new LuaTable());
        this.package_.set(_PATH, LuaValue.valueOf(DEFAULT_LUA_PATH));
        this.package_.set(_LOADLIB, new loadlib());
        this.package_.set(_SEARCHPATH, new searchpath());
        LuaValue luaTable2 = new LuaTable();
        preload_searcher preload_searcherVar = new preload_searcher();
        this.preload_searcher = preload_searcherVar;
        luaTable2.set(1, preload_searcherVar);
        lua_searcher lua_searcherVar = new lua_searcher();
        this.lua_searcher = lua_searcherVar;
        luaTable2.set(2, lua_searcherVar);
        java_searcher java_searcherVar = new java_searcher();
        this.java_searcher = java_searcherVar;
        luaTable2.set(3, java_searcherVar);
        this.package_.set(_SEARCHERS, luaTable2);
        this.package_.get(luaString).set("package", this.package_);
        luaValue2.set("package", this.package_);
        this.globals.package_ = this;
        return luaValue2;
    }

    public void setIsLoaded(String str, LuaTable luaTable) {
        this.package_.get(_LOADED).set(str, luaTable);
    }

    public void setLuaPath(String str) {
        this.package_.set(_PATH, LuaValue.valueOf(str));
    }

    @Override // org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaFunction, org.luaj.vm2.LuaValue, org.luaj.vm2.Varargs
    public String tojstring() {
        return "package";
    }
}
