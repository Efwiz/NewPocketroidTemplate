package org.luaj.vm2.lib.jse;

import net.bytebuddy.description.method.ParameterDescription;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.CoroutineLib;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;

/* JADX INFO: loaded from: classes6.dex */
public class JsePlatform {
    public static Globals debugGlobals() {
        Globals globalsStandardGlobals = standardGlobals();
        globalsStandardGlobals.load(new DebugLib());
        return globalsStandardGlobals;
    }

    public static void luaMain(LuaValue luaValue, String[] strArr) {
        Globals globalsStandardGlobals = standardGlobals();
        int length = strArr.length;
        LuaValue[] luaValueArr = new LuaValue[strArr.length];
        for (int i = 0; i < length; i++) {
            luaValueArr[i] = LuaValue.valueOf(strArr[i]);
        }
        LuaTable luaTableListOf = LuaValue.listOf(luaValueArr);
        luaTableListOf.set("n", length);
        globalsStandardGlobals.set(ParameterDescription.NAME_PREFIX, luaTableListOf);
        luaValue.initupvalue1(globalsStandardGlobals);
        luaValue.invoke(LuaValue.varargsOf(luaValueArr));
    }

    public static Globals standardGlobals() {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new LuajavaLib());
        LoadState.install(globals);
        LuaC.install(globals);
        return globals;
    }
}
