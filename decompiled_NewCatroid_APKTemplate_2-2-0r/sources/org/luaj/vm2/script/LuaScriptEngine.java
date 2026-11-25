package org.luaj.vm2.script;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/* JADX INFO: loaded from: classes6.dex */
public class LuaScriptEngine extends AbstractScriptEngine implements ScriptEngine, Compilable {
    private static final String __ARGV__ = "arg";
    private static final String __ENGINE_VERSION__ = "Luaj-jse 3.0.1";
    private static final String __FILENAME__ = "?";
    private static final String __LANGUAGE_VERSION__ = "5.2";
    private static final String __LANGUAGE__ = "lua";
    private static final String __NAME__ = "Luaj";
    private static final String __SHORT_NAME__ = "Luaj";
    private static final ScriptEngineFactory myFactory = new LuaScriptEngineFactory();
    private LuajContext context;

    static class BindingsMetatable extends LuaTable {
        BindingsMetatable(final Bindings bindings) {
            rawset(LuaValue.INDEX, new TwoArgFunction() { // from class: org.luaj.vm2.script.LuaScriptEngine.BindingsMetatable.1
                @Override // org.luaj.vm2.lib.TwoArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
                public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
                    return luaValue2.isstring() ? LuaScriptEngine.toLua(bindings.get(luaValue2.tojstring())) : rawget(luaValue2);
                }
            });
            rawset(LuaValue.NEWINDEX, new ThreeArgFunction() { // from class: org.luaj.vm2.script.LuaScriptEngine.BindingsMetatable.2
                @Override // org.luaj.vm2.lib.ThreeArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
                public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
                    if (luaValue2.isstring()) {
                        String str = luaValue2.tojstring();
                        Object java2 = LuaScriptEngine.toJava(luaValue3);
                        if (java2 == null) {
                            bindings.remove(str);
                        } else {
                            bindings.put(str, java2);
                        }
                    } else {
                        rawset(luaValue2, luaValue3);
                    }
                    return LuaValue.NONE;
                }
            });
        }
    }

    class LuajCompiledScript extends CompiledScript {
        final Globals compiling_globals;
        final LuaFunction function;

        LuajCompiledScript(LuaFunction luaFunction, Globals globals) {
            this.function = luaFunction;
            this.compiling_globals = globals;
        }

        public Object eval() throws ScriptException {
            return eval(LuaScriptEngine.this.getContext());
        }

        public Object eval(Bindings bindings) throws ScriptException {
            return eval(((LuajContext) LuaScriptEngine.this.getContext()).globals, bindings);
        }

        public Object eval(ScriptContext scriptContext) throws ScriptException {
            return eval(((LuajContext) scriptContext).globals, scriptContext.getBindings(100));
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
        Object eval(Globals globals, Bindings bindings) throws ScriptException {
            LuaValue luaClosure;
            globals.setmetatable(new BindingsMetatable(bindings));
            LuaFunction luaFunction = this.function;
            if (luaFunction.isclosure()) {
                luaClosure = new LuaClosure(luaFunction.checkclosure().p, globals);
            } else {
                try {
                    luaClosure = (LuaFunction) luaFunction.getClass().newInstance();
                    luaClosure.initupvalue1(globals);
                } catch (Exception e) {
                    throw new ScriptException(e);
                }
            }
            return LuaScriptEngine.toJava(luaClosure.invoke(LuaValue.NONE));
        }

        public ScriptEngine getEngine() {
            return LuaScriptEngine.this;
        }
    }

    private final class Utf8Encoder extends InputStream {
        private final int[] buf;
        private int n;
        private final Reader r;

        private Utf8Encoder(Reader reader) {
            this.buf = new int[2];
            this.r = reader;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int i = this.n;
            if (i > 0) {
                int[] iArr = this.buf;
                int i2 = i - 1;
                this.n = i2;
                return iArr[i2];
            }
            int i3 = this.r.read();
            if (i3 < 128) {
                return i3;
            }
            this.n = 0;
            if (i3 < 2048) {
                int[] iArr2 = this.buf;
                this.n = 1;
                iArr2[0] = (i3 & 63) | 128;
                return ((i3 >> 6) & 31) | 192;
            }
            int[] iArr3 = this.buf;
            this.n = 1;
            iArr3[0] = (i3 & 63) | 128;
            this.n = 1 + 1;
            iArr3[1] = 128 | ((i3 >> 6) & 63);
            return ((i3 >> 12) & 15) | 224;
        }
    }

    public LuaScriptEngine() {
        LuajContext luajContext = new LuajContext();
        this.context = luajContext;
        luajContext.setBindings(createBindings(), 100);
        setContext(this.context);
        put("javax.script.language_version", __LANGUAGE_VERSION__);
        put("javax.script.language", __LANGUAGE__);
        put("javax.script.engine", "Luaj");
        put("javax.script.engine_version", "Luaj-jse 3.0.1");
        put("javax.script.argv", "arg");
        put("javax.script.filename", "?");
        put("javax.script.name", "Luaj");
        put("THREADING", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object toJava(LuaValue luaValue) {
        int iType = luaValue.type();
        if (iType == 0) {
            return null;
        }
        if (iType == 7) {
            return luaValue.checkuserdata(Object.class);
        }
        if (iType != 3) {
            return iType != 4 ? luaValue : luaValue.tojstring();
        }
        return luaValue.isinttype() ? new Integer(luaValue.toint()) : new Double(luaValue.todouble());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object toJava(Varargs varargs) {
        int iNarg = varargs.narg();
        if (iNarg == 0) {
            return null;
        }
        if (iNarg == 1) {
            return toJava(varargs.arg1());
        }
        Object[] objArr = new Object[iNarg];
        int i = 0;
        while (i < iNarg) {
            int i2 = i + 1;
            objArr[i] = toJava(varargs.arg(i2));
            i = i2;
        }
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LuaValue toLua(Object obj) {
        if (obj == null) {
            return LuaValue.NIL;
        }
        return obj instanceof LuaValue ? (LuaValue) obj : CoerceJavaToLua.coerce(obj);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    public CompiledScript compile(Reader reader) throws ScriptException {
        try {
            Utf8Encoder utf8Encoder = new Utf8Encoder(reader);
            try {
                try {
                    Globals globals = this.context.globals;
                    LuajCompiledScript luajCompiledScript = new LuajCompiledScript(globals.load(reader, "script").checkfunction(), globals);
                    utf8Encoder.close();
                    return luajCompiledScript;
                } catch (LuaError e) {
                    throw new ScriptException(e.getMessage());
                }
            } catch (Throwable th) {
                utf8Encoder.close();
                throw th;
            }
        } catch (Exception e2) {
            throw new ScriptException("eval threw " + e2.toString());
        }
    }

    public CompiledScript compile(String str) throws ScriptException {
        return compile(new StringReader(str));
    }

    public Bindings createBindings() {
        return new SimpleBindings();
    }

    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        return ((LuajCompiledScript) compile(reader)).eval(this.context.globals, bindings);
    }

    public Object eval(Reader reader, ScriptContext scriptContext) throws ScriptException {
        return compile(reader).eval(scriptContext);
    }

    public Object eval(String str, Bindings bindings) throws ScriptException {
        return eval(new StringReader(str), bindings);
    }

    public Object eval(String str, ScriptContext scriptContext) throws ScriptException {
        return eval(new StringReader(str), scriptContext);
    }

    public ScriptEngineFactory getFactory() {
        return myFactory;
    }

    protected ScriptContext getScriptContext(Bindings bindings) {
        throw new IllegalStateException("LuajScriptEngine should not be allocating contexts.");
    }
}
